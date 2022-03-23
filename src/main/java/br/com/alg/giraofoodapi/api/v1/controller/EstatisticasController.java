package br.com.alg.giraofoodapi.api.v1.controller;

import br.com.alg.giraofoodapi.api.v1.GiLinksV1;
import br.com.alg.giraofoodapi.domain.filter.VendaDiariaFilter;
import br.com.alg.giraofoodapi.domain.model.VendaDiaria;
import br.com.alg.giraofoodapi.domain.service.VendaQueryService;
import br.com.alg.giraofoodapi.domain.service.VendaReportService;
import br.com.alg.giraofoodapi.api.v1.openapi.controller.EstatisticasControllerOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/estatisticas")
public class EstatisticasController implements EstatisticasControllerOpenApi {

    @Autowired
    private VendaQueryService vendaQueryService;

    @Autowired
    private VendaReportService reportService;

    @Autowired
    private GiLinksV1 giLinks;

    @GetMapping(value = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro) {
        return vendaQueryService.consultarVendasDiarias(filtro);
    }

    @GetMapping
    public EstatisticaEntryPointModel estatisticaEntryPointModel() {
        var entryPoint = new  EstatisticaEntryPointModel();

        entryPoint.add(giLinks.linkToVendasDiarias("vendas-diarias"));

        return entryPoint;
    }

    @GetMapping(value = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro) {
        byte[] bytesPdf = reportService.emitirVendasDiarias(filtro);

        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
                .body(bytesPdf);
    }

    private static class EstatisticaEntryPointModel extends RepresentationModel<EstatisticaEntryPointModel> {
    }
}
