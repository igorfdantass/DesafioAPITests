package dantas.igor.steps;

import io.cucumber.java.pt.*;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class RestrictionsStepDefinitions {

    private static final String RESTRICTIONS_ENDPOINT = "/api/v1/restricoes/";

    ValidatableResponse response;

    @Quando("consulto {word} com restricao")
    public void consultoCpfComRestricao(String cpf) {
        response =
            given()
                .relaxedHTTPSValidation()
                .when()
                .get(RESTRICTIONS_ENDPOINT + cpf)
                .then()
                .assertThat();
    }

    @Entao("deve retornar mensagem indicando que {word} possui restrição")
    public void deveRetornarMensagemIndicandoQueCpfPossuiRestrição(String cpf) {
        response.body("mensagem", is("O CPF "+ cpf +" tem problema"));
    }

    @E("statusCode deve ser {word}")
    public void statuscodeDeveSerStatus(String status) {
        response.statusCode(Integer.valueOf(status));
    }

    @Quando("consulto {word} sem restrição")
    public void consultoCpfSemRestrição(String cpf) {
        response =
                given()
                        .relaxedHTTPSValidation()
                        .log().all()
                        .when()
                        .log().all()
                        .get(RESTRICTIONS_ENDPOINT + cpf)
                        .then()
                        .log().all()
                        .assertThat();
    }
}
