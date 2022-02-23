package br.com.alg.giraofoodapi.api.controller;

import br.com.alg.giraofoodapi.api.assembler.GrupoInputDisassembler;
import br.com.alg.giraofoodapi.api.assembler.GrupoModelAssembler;
import br.com.alg.giraofoodapi.api.assembler.UsuarioInputDisassembler;
import br.com.alg.giraofoodapi.api.assembler.UsuarioModelAssembler;
import br.com.alg.giraofoodapi.api.model.dto.GrupoDTO;
import br.com.alg.giraofoodapi.api.model.dto.UsuarioDTO;
import br.com.alg.giraofoodapi.api.model.input.GrupoInput;
import br.com.alg.giraofoodapi.api.model.input.SenhaInput;
import br.com.alg.giraofoodapi.api.model.input.UsuarioInput;
import br.com.alg.giraofoodapi.api.model.input.UsuarioInputComSenha;
import br.com.alg.giraofoodapi.domain.model.Grupo;
import br.com.alg.giraofoodapi.domain.model.Usuario;
import br.com.alg.giraofoodapi.domain.repository.GrupoRepository;
import br.com.alg.giraofoodapi.domain.repository.UsuarioRepository;
import br.com.alg.giraofoodapi.domain.service.CadastroGrupoService;
import br.com.alg.giraofoodapi.domain.service.CadastroUsuarioService;
import br.com.alg.giraofoodapi.openapi.controller.UsuarioControllerOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController implements UsuarioControllerOpenApi {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private CadastroUsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler assembler;

    @Autowired
    private UsuarioInputDisassembler disassembler;

    @GetMapping
    public List<UsuarioDTO> listar() {
        return assembler.toCollectionDTO(repository.findAll());
    }

    @GetMapping("/{id}")
    public UsuarioDTO buscar(@PathVariable Long id) {
        return  assembler.toDTO(usuarioService.buscar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO adicionar(@RequestBody @Valid UsuarioInputComSenha usuario) {
        return assembler.toDTO(usuarioService.salvar(disassembler.toDomainObject(usuario)));
    }

    @PutMapping("/{id}")
    public UsuarioDTO atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioInput input) {

        Usuario usuarioExistente = usuarioService.buscar(id);
        disassembler.copyToDomainInObject(input, usuarioExistente);
        return assembler.toDTO(usuarioService.salvar(usuarioExistente));
    }

    @PutMapping("/{id}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long id, @RequestBody @Valid SenhaInput senhaInput) {
        usuarioService.alterarSenha(id, senhaInput.getSenhaAtual(), senhaInput.getNovaSenha());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        usuarioService.remove(id);
    }

}
