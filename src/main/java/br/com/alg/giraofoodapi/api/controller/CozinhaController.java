package br.com.alg.giraofoodapi.api.controller;

import br.com.alg.giraofoodapi.api.assembler.CozinhaInputDisassembler;
import br.com.alg.giraofoodapi.api.assembler.CozinhaModelAssembler;
import br.com.alg.giraofoodapi.domain.model.Cozinha;
import br.com.alg.giraofoodapi.api.model.dto.CozinhaDTO;
import br.com.alg.giraofoodapi.api.model.input.CozinhaInput;
import br.com.alg.giraofoodapi.domain.repository.CozinhaRepository;
import br.com.alg.giraofoodapi.domain.service.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @Autowired
    private CozinhaRepository repository;

    @Autowired
    private CozinhaModelAssembler assembler;

    @Autowired
    private CozinhaInputDisassembler disassembler;

    @GetMapping
    public ResponseEntity<List<CozinhaDTO>> listar() {
        return ResponseEntity.status(HttpStatus.OK).body(assembler.toCollectionDTO(repository.findAll()));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CozinhaDTO buscar(@PathVariable Long id){
        return assembler.toDTO(this.cadastroCozinhaService.buscar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaDTO salvar(@RequestBody @Valid CozinhaInput cozinha) {
        return assembler.toDTO(this.cadastroCozinhaService.salvar(disassembler.toDomainObject(cozinha)));
    }

    @PutMapping("/{id}")
    public Cozinha atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinhaExistente = this.cadastroCozinhaService.buscar(id);
        //BeanUtils.copyProperties(cozinhaInput, cozinhaExistente, "id");
        disassembler.copyToDomainInObject(cozinhaInput, cozinhaExistente);
        return this.cadastroCozinhaService.salvar(cozinhaExistente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        this.cadastroCozinhaService.excluir(id);
    }
}
