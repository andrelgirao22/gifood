package br.com.alg.giraofoodapi.infrastructure.service;

import br.com.alg.giraofoodapi.domain.filter.VendaDiariaFilter;
import br.com.alg.giraofoodapi.domain.model.Pedido;
import br.com.alg.giraofoodapi.domain.model.VendaDiaria;
import br.com.alg.giraofoodapi.domain.service.VendaQueryService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(VendaDiaria.class);
        var root = query.from(Pedido.class);

        var functionDateDataCriacao = builder.function(
                "date_trunc", Date.class,
                builder.literal("day"), root.get("dataCriacao"));

        var selection = builder.construct(VendaDiaria.class,
                functionDateDataCriacao,
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal")));

        query.select(selection);
        query.groupBy(functionDateDataCriacao);

        TypedQuery<VendaDiaria> typedQuery = manager.createQuery(query);

        return typedQuery.getResultList();
    }
}
