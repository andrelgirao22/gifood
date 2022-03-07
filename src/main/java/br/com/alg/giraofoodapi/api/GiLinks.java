package br.com.alg.giraofoodapi.api;

import br.com.alg.giraofoodapi.api.controller.*;
import org.springframework.hateoas.*;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GiLinks {

    private static final TemplateVariables PROJECAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("projecao", TemplateVariable.VariableType.REQUEST_PARAM)
    );

    private static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM)
    );

    public Link linkToPedidos(String rel) {
        TemplateVariables filtroVariables = new TemplateVariables(
                new TemplateVariable("clienteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("restauranteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", TemplateVariable.VariableType.REQUEST_PARAM)
        );

        String pedidosUrl = linkTo(PedidoController.class).toUri().toString();
        return Link.of(UriTemplate.of(pedidosUrl, PAGINACAO_VARIABLES.concat(filtroVariables)),rel);
    }


    public Link linkToConfirmacaoPedido(String codigoPedido, String  rel) {
        return  linkTo(methodOn(FluxoPedidoController.class).confirmar(codigoPedido)).withRel(rel);
    }

    public Link linkToCancelarPedido(String codigoPedido, String  rel) {
        return  linkTo(methodOn(FluxoPedidoController.class).cancelar(codigoPedido)).withRel(rel);
    }

    public Link linkToEntregaPedido(String codigoPedido, String  rel) {
        return  linkTo(methodOn(FluxoPedidoController.class).entregar(codigoPedido)).withRel(rel);
    }

    public Link linkToRestaurante(Long id) {
        return linkTo(methodOn(RestauranteController.class).buscar(id)).withRel(IanaLinkRelations.SELF.value());
    }

    public Link linkToRestaurante(Long id, String relation) {
        return linkTo(methodOn(RestauranteController.class).buscar(id)).withRel(relation);
    }

    public Link linkToRestaurantes() {
        return linkTo(RestauranteController.class).withRel("restaurantes");
    }

    public Link linkToRestaurantes(String rel) {
        String url = linkTo(RestauranteController.class).toUri().toString();
        return Link.of(UriTemplate.of(url, PROJECAO_VARIABLES), rel);
    }

    public Link linkToRestaurantesFormasPagamento(Long id) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class).listar(id)).withRel("formas-pagamento");
    }

    public Link linkToRestaurantesResponsaveis(Long id) {
        return linkTo(methodOn(RestauranteUsuarioResponsavelController.class).listar(id)).withRel("responsaveis");
    }

    public Link linkToUsuario(Long id) {
        return linkTo(methodOn(UsuarioController.class).buscar(id)).withSelfRel();
    }

    public Link linkToUsuario(Long id, String relation) {
        return linkTo(methodOn(UsuarioController.class).buscar(id)).withRel(relation);
    }

    public Link linkToUsuarios(String rel) {
        return linkTo(UsuarioController.class).withRel(rel);
    }

    public Link linkToUsuarios() {
        return linkToUsuarios(IanaLinkRelations.SELF.value());
    }

    public Link linkToGruposUsuario(Long usuarioId, String rel) {
        return linkTo(methodOn(UsuarioGrupoController.class)
                .listar(usuarioId)).withRel(rel);
    }

    public Link linkToGruposUsuario(Long usuarioId) {
        return linkToGruposUsuario(usuarioId, IanaLinkRelations.SELF.value());
    }

    public Link linkToResponsaveisRestaurante(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
                .listar(restauranteId)).withRel(rel);
    }

    public Link linkToResponsaveisRestaurante(Long restauranteId) {
        return linkToResponsaveisRestaurante(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToFormaPagamento(Long formaPagamentoId, String rel) {
        return linkTo(methodOn(FormaPagamentoController.class)
                .buscar(null,formaPagamentoId)).withRel(rel);
    }

    public Link linkToFormaPagamento(Long formaPagamentoId) {
        return linkToFormaPagamento(formaPagamentoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToCidade(Long cidadeId, String rel) {
        return linkTo(methodOn(CidadeController.class)
                .buscarPorId(cidadeId)).withRel(rel);
    }

    public Link linkToCidade(Long cidadeId) {
        return linkToCidade(cidadeId, IanaLinkRelations.SELF.value());
    }

    public Link linkToCidades(String rel) {
        return linkTo(CidadeController.class).withRel(rel);
    }

    public Link linkToCidades() {
        return linkToCidades(IanaLinkRelations.SELF.value());
    }

    public Link linkToEstado(Long estadoId, String rel) {
        return linkTo(methodOn(EstadoController.class)
                .buscar(estadoId)).withRel(rel);
    }

    public Link linkToEstado(Long estadoId) {
        return linkToEstado(estadoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToEstados(String rel) {
        return linkTo(EstadoController.class).withRel(rel);
    }

    public Link linkToEstados() {
        return linkToEstados(IanaLinkRelations.SELF.value());
    }

    public Link linkToProduto(Long restauranteId, Long produtoId, String rel) {
        return linkTo(methodOn(RestauranteProdutoController.class)
                .buscar(restauranteId, produtoId))
                .withRel(rel);
    }

    public Link linkToProduto(Long restauranteId, Long produtoId) {
        return linkToProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToCozinhas(String rel) {
        return linkTo(CozinhaController.class).withRel(rel);
    }

    public Link linkToCozinhas() {
        return linkToCozinhas(IanaLinkRelations.SELF.value());
    }

    public Link linkToCozinha(Long id) {
        return linkTo(methodOn(CozinhaController.class).buscar(id)).withSelfRel();
    }

    public Link linkToRestauranteAbertura(Long id) {
        return linkTo(methodOn(RestauranteController.class).abrir(id)).withRel("abrir");
    }

    public Link linkToRestauranteFechamento(Long id) {
        return linkTo(methodOn(RestauranteController.class).fechar(id)).withRel("fechar");
    }

    public Link linkToRestauranteAtivacao(Long id) {
        return linkTo(methodOn(RestauranteController.class).ativar(id)).withRel("ativar");
    }

    public Link linkToRestauranteInativacao(Long id) {
        return linkTo(methodOn(RestauranteController.class).inativar(id)).withRel("inativar");
    }
}
