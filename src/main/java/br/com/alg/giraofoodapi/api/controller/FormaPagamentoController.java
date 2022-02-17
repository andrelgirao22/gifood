package br.com.alg.giraofoodapi.api.controller;

import br.com.alg.giraofoodapi.api.assembler.FormaPagamentoInputDisassembler;
import br.com.alg.giraofoodapi.api.assembler.FormaPagamentoModelAssembler;
import br.com.alg.giraofoodapi.api.model.dto.FormaPagamentoDTO;
import br.com.alg.giraofoodapi.api.model.input.FormaPagamentoInput;
import br.com.alg.giraofoodapi.domain.model.FormaPagamento;
import br.com.alg.giraofoodapi.domain.repository.FormaPagamentoRepository;
import br.com.alg.giraofoodapi.domain.service.CadastroFormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
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
public class FormaPagamentoController {

    @Autowired
    private CadastroFormaPagamentoService service;

    @Autowired
    private FormaPagamentoRepository repository;

    @Autowired
    private FormaPagamentoModelAssembler assembler;

    @Autowired
    private FormaPagamentoInputDisassembler disassembler;

    @GetMapping
    public ResponseEntity<List<FormaPagamentoDTO>> listar(ServletWebRequest request) {

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

        List<FormaPagamentoDTO> formasPagamentoDto = assembler.toCollectionDTO(service.lista());

        return ResponseEntity.ok()
                //.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
                .eTag(eTag)
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
                //.cacheControl(CacheControl.noCache())
                .body(formasPagamentoDto);

    }

    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamentoDTO> buscar(ServletWebRequest request, @PathVariable Long id) {

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

        FormaPagamentoDTO formaPagamento = assembler.toDTO(service.buscar(id));
        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(formaPagamento);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoDTO salvar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        return assembler.toDTO(service.salvar(disassembler.toDomainObject(formaPagamentoInput)));
    }

    @PutMapping("/{id}")
    public FormaPagamentoDTO alterar(@RequestBody FormaPagamentoInput formaPagamentoInput, @PathVariable Long id) {
        FormaPagamento formaPagamentoExistente = service.buscar(id);
        disassembler.copyToDomainInObject(formaPagamentoInput, formaPagamentoExistente);
        return assembler.toDTO(service.salvar(formaPagamentoExistente));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.delete(id);
    }
}