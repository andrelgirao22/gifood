package br.com.alg.giraofoodapi.api.v1.openapi.controller;

import br.com.alg.giraofoodapi.api.v1.model.dto.PermissaoModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Permissões")
public interface PermissaoControllerOpenApi {

    @ApiOperation("Lista as permissões")
    CollectionModel<PermissaoModel> listar();

}