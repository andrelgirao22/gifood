package br.com.alg.giraofoodapi;

import br.com.alg.giraofoodapi.domain.model.Cozinha;
import br.com.alg.giraofoodapi.domain.model.Restaurante;
import br.com.alg.giraofoodapi.domain.repository.CozinhaRepository;
import br.com.alg.giraofoodapi.domain.repository.RestauranteRepository;
import br.com.alg.giraofoodapi.util.DatabaseCleaner;
import br.com.alg.giraofoodapi.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static io.restassured.RestAssured.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteT {

    @LocalServerPort
    private  int port;

    private Restaurante restauranteExistente;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @BeforeEach
    public void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/restaurantes";

        databaseCleaner.clearTables();
        prepararDados();
    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarRestaurantes() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());

    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarRestaurante() {
        String json = ResourceUtils.getContentFromResource("/json/correto/restaurantes.json");
        given()
                .body(json)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when().post()
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void deveRetornarStatus400_QuandoCozinhaNaoExiste() {
        String json = ResourceUtils.getContentFromResource("/json/correto/restaurantes-incorretos.json");
        given()
                .body(json)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when().post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void deveRetornarRespostaEStatusCorretos_QuandoConsultarRestauranteExistente() {
        given()
                .pathParam("restauranteId", restauranteExistente.getId())
                .accept(ContentType.JSON)
            .when()
                .get("/{restauranteId}")
            .then()
                .statusCode(HttpStatus.OK.value())
                .body("nome", Matchers.equalTo(restauranteExistente.getNome()));
    }

    private void prepararDados() {
        Cozinha cozinha1 = new Cozinha();
        cozinha1.setNome("Tailandesa");
        cozinha1 = cozinhaRepository.save(cozinha1);

        Restaurante restaurante = new Restaurante();
        restaurante.setNome("Thai Gourmet");
        restaurante.setTaxaFrete(BigDecimal.valueOf(10));
        restaurante.setCozinha(cozinha1);
        restauranteExistente = restauranteRepository.save(restaurante);
    }

}
