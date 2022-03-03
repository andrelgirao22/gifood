package br.com.alg.giraofoodapi.api.controller;

import br.com.alg.giraofoodapi.api.assembler.FormaPagamentoInputDisassembler;
import br.com.alg.giraofoodapi.api.assembler.FormaPagamentoModelAssembler;
import br.com.alg.giraofoodapi.api.model.dto.FormaPagamentoModel;
import br.com.alg.giraofoodapi.api.model.input.FormaPagamentoInput;
import br.com.alg.giraofoodapi.domain.model.FormaPagamento;
import br.com.alg.giraofoodapi.domain.repository.FormaPagamentoRepository;
import br.com.alg.giraofoodapi.domain.service.CadastroFormaPagamentoService;
import br.com.alg.giraofoodapi.openapi.controller.FormasPagamentoControllerOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController implements FormasPagamentoControllerOpenApi {

    @Autowired
    private CadastroFormaPagamentoService service;

    @Autowired
    private FormaPagamentoRepository repository;

    @Autowired
    private FormaPagamentoModelAssembler assembler;

    @Autowired
    private FormaPagamentoInputDisassembler disassembler;

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest request) {

        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";

        OffsetDateTime dataUltimaAtualizacao = repository.getDataUltimaAtualizacao();

        if(dataUltimaAtualizacao != null) {
            eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
        }

        //já temos condicoes de saber se continua ou nao o processamento
        if(request.checkNotModified(eTag)) {
            return null;
        }

        CollectionModel<FormaPagamentoModel> formasPagamentoDto = assembler.toCollectionModel(service.lista());

        return ResponseEntity.ok()
                //.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
                .eTag(eTag)
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
                //.cacheControl(CacheControl.noCache())
                .body(formasPagamentoDto);

    }

    @GetMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormaPagamentoModel> buscar(ServletWebRequest request, @PathVariable Long id) {

        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";

        OffsetDateTime dataUltimaAtualizacao = repository.getDataUltimaAtualizacao(id);

        if(dataUltimaAtualizacao != null) {
            eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
        }

        //já temos condicoes de saber se continua ou nao o processamento
        if(request.checkNotModified(eTag)) {
            return null;
        }

        FormaPagamentoModel formaPagamento = assembler.toModel(service.buscar(id));
        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(formaPagamento);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoModel salvar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        return assembler.toModel(service.salvar(disassembler.toDomainObject(formaPagamentoInput)));
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public FormaPagamentoModel alterar(@RequestBody FormaPagamentoInput formaPagamentoInput, @PathVariable Long id) {
        FormaPagamento formaPagamentoExistente = service.buscar(id);
        disassembler.copyToDomainInObject(formaPagamentoInput, formaPagamentoExistente);
        return assembler.toModel(service.salvar(formaPagamentoExistente));
    }

    @DeleteMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.delete(id);
    }
}