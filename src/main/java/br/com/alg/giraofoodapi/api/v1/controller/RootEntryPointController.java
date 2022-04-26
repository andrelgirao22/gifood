package br.com.alg.giraofoodapi.api.v1.controller;

import br.com.alg.giraofoodapi.api.v1.GiLinksV1;
import br.com.alg.giraofoodapi.core.security.GiSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

    @Autowired
    private GiLinksV1 giLinks;

    @Autowired
    private GiSecurity giSecurity;

    @GetMapping
    public RootEntryPointModel root() {
        var rootEntryPointModel = new RootEntryPointModel();

        if (giSecurity.podeConsultarCozinhas()) {
            rootEntryPointModel.add(giLinks.linkToCozinhas("cozinhas"));
        }

        if (giSecurity.podePesquisarPedidos()) {
            rootEntryPointModel.add(giLinks.linkToPedidos("pedidos"));
        }

        if (giSecurity.podeConsultarRestaurantes()) {
            rootEntryPointModel.add(giLinks.linkToRestaurantes("restaurantes"));
        }

        if (giSecurity.podeConsultarUsuariosGruposPermissoes()) {
            rootEntryPointModel.add(giLinks.linkToGrupos("grupos"));
            rootEntryPointModel.add(giLinks.linkToUsuarios("usuarios"));
            rootEntryPointModel.add(giLinks.linkToPermissoes("permissoes"));
        }

        if (giSecurity.podeConsultarFormasPagamento()) {
            rootEntryPointModel.add(giLinks.linkToFormasPagamento("formas-pagamento"));
        }

        if (giSecurity.podeConsultarEstados()) {
            rootEntryPointModel.add(giLinks.linkToEstados("estados"));
        }

        if (giSecurity.podeConsultarCidades()) {
            rootEntryPointModel.add(giLinks.linkToCidades("cidades"));
        }

        if (giSecurity.podeConsultarEstatisticas()) {
            rootEntryPointModel.add(giLinks.linkToEstatisticas("estatisticas"));
        }

        return rootEntryPointModel;
    }

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
    }
}
