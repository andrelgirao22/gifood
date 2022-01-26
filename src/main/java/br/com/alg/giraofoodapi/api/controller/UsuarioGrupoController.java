package br.com.alg.giraofoodapi.api.controller;

import br.com.alg.giraofoodapi.api.assembler.GrupoModelAssembler;
import br.com.alg.giraofoodapi.api.assembler.UsuarioModelAssembler;
import br.com.alg.giraofoodapi.api.model.dto.GrupoDTO;
import br.com.alg.giraofoodapi.domain.model.Usuario;
import br.com.alg.giraofoodapi.domain.repository.UsuarioRepository;
import br.com.alg.giraofoodapi.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios/{id}/grupos")
public class UsuarioGrupoController {


    @Autowired
    private CadastroUsuarioService usuarioService;

    @Autowired
    private GrupoModelAssembler assembler;

    @GetMapping
    public List<GrupoDTO> listar(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscar(id);
        return assembler.toCollectionDTO(usuario.getGrupos());
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarGrupo(@PathVariable Long id, @PathVariable Long grupoId) {
        usuarioService.desassociarGrupo(id, grupoId);
    }

    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarGrupo(@PathVariable Long id, @PathVariable Long grupoId) {
        usuarioService.associarGrupo(id, grupoId);
    }

}
