package br.com.alg.giraofoodapi.api.v1.controller;

import br.com.alg.giraofoodapi.domain.model.Cozinha;
import br.com.alg.giraofoodapi.domain.model.Restaurante;
import br.com.alg.giraofoodapi.domain.repository.CozinhaRepository;
import br.com.alg.giraofoodapi.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static br.com.alg.giraofoodapi.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static br.com.alg.giraofoodapi.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

@RestController
@RequestMapping("/teste")
public class TesteController {

    @Autowired
    private CozinhaRepository repository;

    @Autowired
    private RestauranteRepository restauranteRepository;


    @GetMapping("/cozinha/por-nome")
    public List<Cozinha> buscaPorNome(@RequestParam String nome) {
        List<Cozinha> list = this.repository.findByNomeContaining(nome);
        return list;
    }

    @GetMapping("/cozinha/buscar-primeira")
    public Optional<Cozinha> buscaPrimeira() {
        return repository.buscarPrimeiro();
    }

    @GetMapping("/restaurante/primeiro-por-nome")
    public Restaurante buscaPrimeiroPorNome(@RequestParam String nome) {
        Optional<Restaurante> optionalRestaurante = this.restauranteRepository.findFirstByNomeContaining(nome);
        return optionalRestaurante.orElse(null);
    }

    @GetMapping("/restaurante/top2-por-nome")
    public List<Restaurante> buscaTop2PorNome(@RequestParam String nome) {
        List<Restaurante> restaurantes = this.restauranteRepository.findTop2ByNomeContaining(nome);
        return restaurantes;
    }

    @GetMapping("/restaurante/por-nome-taxa-frete")
    public List<Restaurante> buscaPorTaxa(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return this.restauranteRepository.find(nome, taxaInicial, taxaFinal);
    }

    @GetMapping("/restaurante/por-taxa-frete")
    public List<Restaurante> buscaPorTaxa(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return this.restauranteRepository.queryByTaxaFreteBetween(taxaInicial,taxaFinal);
    }

    @GetMapping("/restaurante/por-nome-cozinha")
    public List<Restaurante> buscaPorTaxa(String nome, Long cozinhaId) {
        return this.restauranteRepository.consultarPorNomeCozinha(nome, cozinhaId);
    }

    @GetMapping("/restaurante/com-frete-gratis")
    public List<Restaurante> buscaRestauranteComFrenteGratis(String nome) {
        return restauranteRepository.findComFreteGratis(nome);
    }

    @GetMapping("/restaurante/count-cozinha")
    public int countCozinha(Long cozinhaId) {
        return this.restauranteRepository.countByCozinhaId(cozinhaId);
    }

    @GetMapping("/restaurante/buscar-primeiro")
    public Optional<Restaurante> buscarPrimeiro() {
        return restauranteRepository.buscarPrimeiro();
    }
}
