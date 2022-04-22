package br.com.alg.giraofoodapi.api.v1.controller;

import br.com.alg.giraofoodapi.api.v1.assembler.RestauranteInputDisassembler;
import br.com.alg.giraofoodapi.api.v1.assembler.RestauranteModelAssembler;
import br.com.alg.giraofoodapi.api.v1.model.dto.RestauranteModel;
import br.com.alg.giraofoodapi.api.v1.model.input.RestauranteInput;
import br.com.alg.giraofoodapi.api.v1.model.view.RestauranteView;
import br.com.alg.giraofoodapi.core.security.CheckSecurity;
import br.com.alg.giraofoodapi.core.validation.ValidacaoException;
import br.com.alg.giraofoodapi.domain.exception.CidadeNaoEncontradaException;
import br.com.alg.giraofoodapi.domain.exception.CozinhaNaoEncontradaException;
import br.com.alg.giraofoodapi.domain.exception.NegocioException;
import br.com.alg.giraofoodapi.domain.exception.RestauranteNaoEncontradoException;
import br.com.alg.giraofoodapi.domain.model.Restaurante;
import br.com.alg.giraofoodapi.domain.repository.RestauranteRepository;
import br.com.alg.giraofoodapi.domain.service.CadastrosRestauranteService;
import br.com.alg.giraofoodapi.api.v1.openapi.controller.RestauranteControllerOpenApi;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

//@CrossOrigin(maxAge = 20)
@RestController
@RequestMapping("/v1/restaurantes")
public class RestauranteController implements RestauranteControllerOpenApi {

    @Autowired
    private RestauranteRepository repository;

    @Autowired
    private CadastrosRestauranteService restauranteService;

    @Autowired
    private SmartValidator validator;

    @Autowired
    private RestauranteModelAssembler modelAssembler;

    @Autowired
    private RestauranteInputDisassembler restauranteInputDisassembler;

    //@JsonView(RestauranteView.Resumo.class)
    @CheckSecurity.Restaurante.PodeConsultar
    @GetMapping
    public CollectionModel<RestauranteModel> listarResumo() {
        return modelAssembler.toCollectionModel(repository.findAll());
    }

    @CheckSecurity.Restaurante.PodeConsultar
    @JsonView(RestauranteView.ApenasNome.class)
    @GetMapping(params = "projecao=apenas-nome")
    public CollectionModel<RestauranteModel> listarApenasNome() {
        return modelAssembler.toCollectionModel(repository.findAll());
    }
/*
    @GetMapping
    public ResponseEntity<MappingJacksonValue> listar(@RequestParam(defaultValue = "completo") String projecao) {
        List<Restaurante> restaurantes = repository.findAll();
        List<RestauranteDTO> restaurantesDTO = modelAssembler.toCollectionDTO(restaurantes);
        MappingJacksonValue restaurantesWrapper = new MappingJacksonValue(restaurantesDTO);

        if(projecao.equals("apenas-nome")) {
            restaurantesWrapper.setSerializationView(RestauranteView.ApenasNome.class);
        } else if(projecao.equals("resumo")) {
            restaurantesWrapper.setSerializationView(RestauranteView.Resumo.class);
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:8000")
                .body(restaurantesWrapper);
    }*/

    @CheckSecurity.Restaurante.PodeConsultar
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestauranteModel buscar(@PathVariable Long id) {
        Restaurante restaurante = this.repository.findById(id).orElseThrow(()->
                new RestauranteNaoEncontradoException(id));

        RestauranteModel restauranteDTO = modelAssembler.toModel(restaurante);
        return restauranteDTO;
    }

    @CheckSecurity.Restaurante.PodeEditar
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restaurante) {
        try {
            return modelAssembler.toModel(restauranteService.salvar(restauranteInputDisassembler.toDomainObject(restaurante)));
        } catch (CidadeNaoEncontradaException | CozinhaNaoEncontradaException  e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @CheckSecurity.Restaurante.PodeEditar
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public RestauranteModel atualizar(@PathVariable Long id,
                                      @RequestBody @Valid RestauranteInput restauranteInput) {
        Restaurante restauranteAtual = restauranteService.buscar(id);

        restauranteInputDisassembler.copyToDomainInObject(restauranteInput, restauranteAtual);

        //BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro");

        try {
            return modelAssembler.toModel(restauranteService.salvar(restauranteAtual));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @CheckSecurity.Restaurante.PodeEditar
    @PutMapping(path = "/{id}/ativo", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        restauranteService.ativar(id);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurante.PodeEditar
    @DeleteMapping(path = "/{id}/inativar", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        restauranteService.inativar(id);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurante.PodeEditar
    @PutMapping(path = "/ativacoes", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long> ids) {
        try {
            restauranteService.ativar(ids);

        }catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @CheckSecurity.Restaurante.PodeEditar
    @DeleteMapping(path = "/inativacoes", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarMultiplos(@RequestBody List<Long> ids) {
        try {
            restauranteService.inativar(ids);
        }catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @CheckSecurity.Restaurante.PodeEditar
    @PutMapping(path = "/{id}/aberto", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> abrir(@PathVariable Long id) {
        restauranteService.abrirRestaurante(id);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurante.PodeEditar
    @PutMapping(path = "/{id}/fechamento", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> fechar(@PathVariable Long id) {
        restauranteService.fecharRestaurante(id);
        return ResponseEntity.noContent().build();
    }

    /*
    @PatchMapping("/{id}")
    public RestauranteDTO alterarcaoParcial(@PathVariable Long id,
                                         @RequestBody Map<String, Object> campos, HttpServletRequest request) {
        Restaurante restauranteExistente = restauranteService.buscar(id);
        this.merge(campos, restauranteExistente, request);
        validate(restauranteExistente, "restaurante");

        RestauranteInput restauranteInput = restauranteInputDisassembler.

        return  atualizar(id, restauranteExistente);
    }*/

    private void validate(Restaurante restaurante, String objectName) {
        BeanPropertyBindingResult bindingResult =  new BeanPropertyBindingResult(restaurante, objectName);
        validator.validate(restaurante, bindingResult);
        if(bindingResult.hasErrors()) {
            throw new ValidacaoException(bindingResult);
        }
    }

    private void merge(Map<String, Object> campos, Restaurante restauranteAtual, HttpServletRequest request) {
        ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(request);
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
            Restaurante restauranteOrigem = mapper.convertValue(campos, Restaurante.class);

            campos.forEach((propriedade, valorPropriedade) -> {
                Field campo = ReflectionUtils.findField(Restaurante.class, propriedade);
                campo.setAccessible(true);
                Object novoValor = ReflectionUtils.getField(campo, restauranteOrigem);
                ReflectionUtils.setField(campo, restauranteAtual, novoValor);
            });

        } catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, servletServerHttpRequest);
        }
    }
}

