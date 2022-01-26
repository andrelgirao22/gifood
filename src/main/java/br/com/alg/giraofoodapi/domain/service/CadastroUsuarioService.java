package br.com.alg.giraofoodapi.domain.service;

import br.com.alg.giraofoodapi.domain.exception.EntidadeEmUsoException;
import br.com.alg.giraofoodapi.domain.exception.NegocioException;
import br.com.alg.giraofoodapi.domain.exception.UsuarioNaoEncontradaException;
import br.com.alg.giraofoodapi.domain.model.Grupo;
import br.com.alg.giraofoodapi.domain.model.Restaurante;
import br.com.alg.giraofoodapi.domain.model.Usuario;
import br.com.alg.giraofoodapi.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class CadastroUsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private CadastroGrupoService grupoService;

    @Autowired
    private CadastrosRestauranteService restauranteService;


    @Transactional
    public Usuario salvar(Usuario usuario) {
        repository.detach(usuario);

        Optional<Usuario> usuarioExistente = repository.findByEmail(usuario.getEmail());
        if(usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
            throw new NegocioException(String.format("Já existe um usuário cadastrado com o email: %s", usuario.getEmail()));
        }

        return repository.save(usuario);
    }

    public Usuario buscar(Long id){
        return repository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradaException(id));
    }

    @Transactional
    public void alterarSenha(Long id, String senhaAtual, String novaSenha) {
        Usuario usuario = buscar(id);
        if(usuario.senhaNaoCoincideCom(senhaAtual)) {
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }
        usuario.setSenha(novaSenha);
    }

    @Transactional
    public void remove(Long id) {
        Usuario usuario = buscar(id);
        try {
            repository.delete(usuario);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Usuário em uso %d", id));
        }
    }

    @Transactional
    public void associarGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = buscar(usuarioId);
        Grupo grupo = grupoService.buscar(grupoId);
        usuario.associaGrupo(grupo);
    }

    @Transactional
    public void desassociarGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = buscar(usuarioId);
        Grupo grupo = grupoService.buscar(grupoId);
        usuario.desassociarGrupo(grupo);
    }

    @Transactional
    public void associarResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);
        Usuario usuario = buscar(usuarioId);
        restaurante.adicionarResponsavel(usuario);
    }

    @Transactional
    public void desassociarResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);
        Usuario usuario = buscar(usuarioId);
        restaurante.removerResponsavel(usuario);
    }
}
