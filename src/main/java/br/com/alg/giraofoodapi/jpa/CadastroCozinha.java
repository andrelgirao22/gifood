package br.com.alg.giraofoodapi.jpa;

import br.com.alg.giraofoodapi.domain.model.Cozinha;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class CadastroCozinha {

    @PersistenceContext
    private EntityManager manager;

    public List<Cozinha> listar() {
        TypedQuery<Cozinha> typedQuery = manager.createQuery("from Cozinha", Cozinha.class);
        return typedQuery.getResultList();
    }
}
