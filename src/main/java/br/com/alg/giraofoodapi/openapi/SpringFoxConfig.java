package br.com.alg.giraofoodapi.openapi;

import br.com.alg.giraofoodapi.api.exceptionhandler.Problem;
import br.com.alg.giraofoodapi.api.model.dto.CozinhaDTO;
import br.com.alg.giraofoodapi.api.model.dto.PedidoDTO;
import br.com.alg.giraofoodapi.openapi.model.CozinhasModelOpenApi;
import br.com.alg.giraofoodapi.openapi.model.PageableModelOpenApi;
import br.com.alg.giraofoodapi.openapi.model.PedidoModelOpenApi;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.*;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelSpecification;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig  {

    @Bean
    public Docket apiDocket() {
        var typeResolver = new TypeResolver();
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.alg.giraofoodapi.api.controller"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, globalGetResponse())
                .globalResponses(HttpMethod.POST, globalPostResponse())
                .globalResponses(HttpMethod.PUT, globalPutResponse())
                .globalResponses(HttpMethod.DELETE, globalDeleteResponse())
//                .globalRequestParameters(Arrays.asList(
//                        new RequestParameterBuilder()
//                                .name("campos")
//                                .description("Nomes das propriedades para filtrar na resposta, separados por vírgula")
//                                .in(ParameterType.QUERY)
//                                .required(true)
//                                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
//                                .build()
//                        )
//                )
                .additionalModels(typeResolver.resolve(Problem.class))
                .ignoredParameterTypes(ServletWebRequest.class)
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(Page.class, CozinhaDTO.class),
                        CozinhasModelOpenApi.class
                ))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(Page.class, PedidoDTO.class),
                        PedidoModelOpenApi.class
                ))
                .apiInfo(apiInfo())
                .tags(new Tag("Cidades", "Gerencia as cidades"),
                        new Tag("Grupos", "Gerencia os grupos"),
                        new Tag("Cozinhas", "Gerencia as cozinhas"),
                        new Tag("Formas Pagamento", "Gerencia as formas de pagamento"),
                        new Tag("Pedidos", "Gerencia os pedidos"));
    }

    private List<Response> globalGetResponse() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Erro interno do servidor")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                        .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
                        .build()
        );
    }

    private List<Response> globalPostResponse() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Erro interno do servidor")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getProblemaModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getProblemaModelReference())
                        .description("Requisição inválida")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                        .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
                        .description("Tipo de Mídia não suportada")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getProblemaModelReference())
                        .build()
        );
    }

    private List<Response> globalPutResponse() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Erro interno do servidor")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                        .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
                        .build()
        );
    }

    private List<Response> globalDeleteResponse() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Erro interno do servidor")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .description("Requisição inválida")
                        .build()
        );
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Gifood")
                .description("API aberta para clientes e restaurantes")
                .version("1")
                .contact(new Contact("Alg", "http://www.algconsulting.com.br", "andrelgirao29@gmail.com"))
                .build();
    }

    @Bean
    public JacksonModuleRegistrar springFoxJacksonConfig() {
        return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
    }

    private Consumer<RepresentationBuilder> getProblemaModelReference() {
        return r -> r.model(m -> m.name("Problema")
                .referenceModel(ref -> ref.key(k -> k.qualifiedModelName(
                        q -> q.name("Problema").namespace("br.com.alg.giraofoodapi.api.exceptionhandler")))));
    }
}
