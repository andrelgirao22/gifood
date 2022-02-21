package br.com.alg.giraofoodapi.api.controller;

import br.com.alg.giraofoodapi.api.assembler.CozinhaInputDisassembler;
import br.com.alg.giraofoodapi.api.assembler.CozinhaModelAssembler;
import br.com.alg.giraofoodapi.domain.model.Cozinha;
import br.com.alg.giraofoodapi.api.model.dto.CozinhaDTO;
import br.com.alg.giraofoodapi.api.model.input.CozinhaInput;
import br.com.alg.giraofoodapi.domain.repository.CozinhaRepository;
import br.com.alg.giraofoodapi.domain.service.CadastroCozinhaService;
import br.com.alg.giraofoodapi.openapi.controller.CozinhaControllerOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/cozinhas")
public class CozinhaController implements CozinhaControllerOpenApi {

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @Autowired
    private CozinhaRepository repository;

    @Autowired
    private CozinhaModelAssembler assembler;

    @Autowired
    private CozinhaInputDisassembler disassembler;

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Page<CozinhaDTO> listar(@PageableDefault(size = 10) Pageable pageable) {
        Page<Cozinha> cozinhasPage = repository.findAll(pageable);
        List<CozinhaDTO> cozinhasDTO = assembler.toCollectionDTO(cozinhasPage.getContent());
        Page<CozinhaDTO> cozinhaDTOPage = new PageImpl<>(cozinhasDTO, pageable, cozinhasPage.getTotalElements());
        return cozinhaDTOPage;
    }

    @GetMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CozinhaDTO buscar(@PathVariable Long id){
        return assembler.toDTO(this.cadastroCozinhaService.buscar(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaDTO salvar(@RequestBody @Valid CozinhaInput cozinha) {
        return assembler.toDTO(this.cadastroCozinhaService.salvar(disassembler.toDomainObject(cozinha)));
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Cozinha atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinhaExistente = this.cadastroCozinhaService.buscar(id);
        //BeanUtils.copyProperties(cozinhaInput, cozinhaExistente, "id");
        disassembler.copyToDomainInObject(cozinhaInput, cozinhaExistente);
        return this.cadastroCozinhaService.salvar(cozinhaExistente);
    }

    @DeleteMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        this.cadastroCozinhaService.excluir(id);
    }
}
