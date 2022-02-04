package br.com.alg.giraofoodapi.domain.service;

import br.com.alg.giraofoodapi.domain.filter.VendaDiariaFilter;

public interface VendaReportService {
    byte [] emitirVendasDiarias(VendaDiariaFilter filtro);
}
