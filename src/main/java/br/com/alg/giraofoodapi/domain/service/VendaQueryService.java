package br.com.alg.giraofoodapi.domain.service;

import br.com.alg.giraofoodapi.domain.filter.VendaDiariaFilter;
import br.com.alg.giraofoodapi.domain.model.VendaDiaria;

import java.util.List;

public interface VendaQueryService {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter);

}
