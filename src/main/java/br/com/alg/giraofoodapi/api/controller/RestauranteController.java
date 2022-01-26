package br.com.alg.giraofoodapi.api.controller;

import br.com.alg.giraofoodapi.api.assembler.RestauranteInputDisassembler;
import br.com.alg.giraofoodapi.api.assembler.RestauranteModelAssembler;
import br.com.alg.giraofoodapi.core.validation.ValidacaoException;
import br.com.alg.giraofoodapi.domain.exception.*;
import br.com.alg.giraofoodapi.api.model.dto.RestauranteDTO;
import br.com.alg.giraofoodapi.domain.model.Restaurante;
import br.com.alg.giraofoodapi.api.model.input.RestauranteInput;
import br.com.alg.giraofoodapi.domain.repository.RestauranteRepository;
import br.com.alg.giraofoodapi.domain.service.CadastrosRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

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

    @GetMapping
    public List<RestauranteDTO> listar() {
        return modelAssembler.toCollectionDTO(repository.findAll());
    }

    @GetMapping("/{id}")
    public RestauranteDTO buscar(@PathVariable Long id) {
        Restaurante restaurante = this.repository.findById(id).orElseThrow(()->
                new RestauranteNaoEncontradoException(id));

        RestauranteDTO restauranteDTO = modelAssembler.toDTO(restaurante);
        return restauranteDTO;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDTO adicionar(@RequestBody @Valid RestauranteInput restaurante) {
        try {
            return modelAssembler.toDTO(restauranteService.salvar(restauranteInputDisassembler.toDomainObject(restaurante)));
        } catch (CidadeNaoEncontradaException | CozinhaNaoEncontradaException  e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RestauranteDTO atualizar(@PathVariable Long id,
                                     @RequestBody @Valid RestauranteInput restauranteInput) {
        Restaurante restauranteAtual = restauranteService.buscar(id);

        restauranteInputDisassembler.copyToDomainInObject(restauranteInput, restauranteAtual);

        //BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro");

        try {
            return modelAssembler.toDTO(restauranteService.salvar(restauranteAtual));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long id) {
        restauranteService.ativar(id);
    }

    @DeleteMapping("/{id}/inativar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long id) {
        restauranteService.inativar(id);
    }

    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiples(@RequestBody List<Long> ids) {
        try {
            restauranteService.ativar(ids);
        }catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/inativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarMultiples(@RequestBody List<Long> ids) {
        try {
            restauranteService.inativar(ids);
        }catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}/aberto")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abrir(@PathVariable Long id) {
        restauranteService.abrirRestaurante(id);
    }

    @PutMapping("/{id}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechar(@PathVariable Long id) {
        restauranteService.fecharRestaurante(id);
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

