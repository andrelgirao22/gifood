package br.com.alg.giraofoodapi.api.controller;

import br.com.alg.giraofoodapi.api.assembler.FormaPagamentoInputDisassembler;
import br.com.alg.giraofoodapi.api.assembler.FormaPagamentoModelAssembler;
import br.com.alg.giraofoodapi.domain.model.FormaPagamento;
import br.com.alg.giraofoodapi.api.model.dto.FormaPagamentoDTO;
import br.com.alg.giraofoodapi.api.model.input.FormaPagamentoInput;
import br.com.alg.giraofoodapi.domain.service.CadastroFormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

    @Autowired
    private CadastroFormaPagamentoService service;

    @Autowired
    private FormaPagamentoModelAssembler assembler;

    @Autowired
    private FormaPagamentoInputDisassembler disassembler;

    @GetMapping
    public List<FormaPagamentoDTO> listar() {
        return assembler.toCollectionDTO(service.lista());
    }

    @GetMapping("/{id}")
    public FormaPagamentoDTO buscar(@PathVariable Long id) {
        return assembler.toDTO(service.buscar(id));
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