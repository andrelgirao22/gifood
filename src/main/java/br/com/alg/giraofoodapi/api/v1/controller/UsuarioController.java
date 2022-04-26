package br.com.alg.giraofoodapi.api.v1.controller;

import br.com.alg.giraofoodapi.api.v1.assembler.UsuarioInputDisassembler;
import br.com.alg.giraofoodapi.api.v1.assembler.UsuarioModelAssembler;
import br.com.alg.giraofoodapi.api.v1.model.dto.UsuarioModel;
import br.com.alg.giraofoodapi.api.v1.model.input.SenhaInput;
import br.com.alg.giraofoodapi.api.v1.model.input.UsuarioInput;
import br.com.alg.giraofoodapi.api.v1.model.input.UsuarioInputComSenha;
import br.com.alg.giraofoodapi.core.security.CheckSecurity;
import br.com.alg.giraofoodapi.domain.model.Usuario;
import br.com.alg.giraofoodapi.domain.repository.UsuarioRepository;
import br.com.alg.giraofoodapi.domain.service.CadastroUsuarioService;
import br.com.alg.giraofoodapi.api.v1.openapi.controller.UsuarioControllerOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/usuarios")
public class UsuarioController implements UsuarioControllerOpenApi {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private CadastroUsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler assembler;

    @Autowired
    private UsuarioInputDisassembler disassembler;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping
    public CollectionModel<UsuarioModel> listar() {
        CollectionModel<UsuarioModel> usuariosModel = assembler.toCollectionModel(repository.findAll());
        return usuariosModel;
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping("/{id}")
    public UsuarioModel buscar(@PathVariable Long id) {
        return  assembler.toModel(usuarioService.buscar(id));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioModel adicionar(@RequestBody @Valid UsuarioInputComSenha usuario) {
        return assembler.toModel(usuarioService.salvar(disassembler.toDomainObject(usuario)));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeAlterarUSuario
    @PutMapping("/{id}")
    public UsuarioModel atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioInput input) {

        Usuario usuarioExistente = usuarioService.buscar(id);
        disassembler.copyToDomainInObject(input, usuarioExistente);
        return assembler.toModel(usuarioService.salvar(usuarioExistente));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeAlterarPropriaSenha
    @PutMapping("/{id}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long id, @RequestBody @Valid SenhaInput senhaInput) {
        usuarioService.alterarSenha(id, senhaInput.getSenhaAtual(), senhaInput.getNovaSenha());
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        usuarioService.remove(id);
    }

}
