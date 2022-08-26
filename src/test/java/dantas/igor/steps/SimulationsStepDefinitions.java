package dantas.igor.steps;

import dantas.igor.models.SimulationModel;
import io.cucumber.java.Before;
import io.cucumber.java.pt.*;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.codehaus.groovy.runtime.typehandling.NumberMathModificationInfo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@CucumberContextConfiguration
public class SimulationsStepDefinitions {

    @Before
    public void setup(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.baseURI = "http://localhost:8889/";

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
    }

    private static final String SIMULATIONS_ENDPOINT = "/api/v1/simulacoes/";
    ValidatableResponse response;

    private String id;

    SimulationModel model = new SimulationModel();

    @Dado("que existem simulações")
    public void que_existem_simulações() {

    }

    @Quando("consultar todas as simulações realizadas")
    public void consultarTodasAsSimulaçõesRealizadas() {
        response =
            given()
                .relaxedHTTPSValidation()
                    .when()
                    .get(SIMULATIONS_ENDPOINT)
                    .then()
                    .assertThat();
    }

    @Entao("deve retornar lista de simulações")
    public void deveRetornarListaDeSimulações() {
        response.statusCode(is(200));
        response.body("$.size()", is(not(0)));
    }

    @Entao("retorna status {word}")
    public void retornaStatus(String status) {
        response.statusCode(Integer.valueOf(status));
    }

    @Quando("remover simulacao existente")
    public void removerId() {
        response =
                given()
                        .relaxedHTTPSValidation()
                        .log().all()
                        .when()
                        .delete(SIMULATIONS_ENDPOINT + this.id )
                        .then()
                        .assertThat();
    }

    @Quando("cadastro simulação")
    public void cadastroSimulação() {
        response =
                given()
                        .relaxedHTTPSValidation()
                        .body(model)
                        .when()
                        .post(SIMULATIONS_ENDPOINT)
                        .then()
                        .assertThat();
    }

    @E("Nome: {word}")
    public void nome(String nome) {
        model.setNome(nome);
    }

    @E("Cpf: {string}")
    public void cpf(String cpf) {
        model.setCpf(cpf);
    }

    @E("Email: {word}")
    public void email(String email) {
        model.setEmail(email);
    }

    @E("Valor: {int}")
    public void valor(Number valor) {
        model.setValor(valor);
    }

    @E("Parcelas: {int}")
    public void parcelas(Integer parcelas) {
        model.setParcelas(parcelas);
    }

    @E("Seguro: {word}")
    public void seguro(String seguro) {
        model.setSeguro(Boolean.getBoolean(seguro));
    }

    @Dado("que tenho os dados:")
    public void queTenhoOsDados() {
    }

    @E("retorna id da simulação")
    public void retornaIdDaSimulação() {
        response.body("$.id", is(not(null)));
    }

    @E("retorna {string} de erro")
    public void retornaMensagemDeErro(String msg) {
        response.body("$.mensagem", is(msg));
    }

    @Quando("consultar simulaçao por cpf")
    public void consultarSimulaçaoPorCpf() {
        response =
                given()
                        .relaxedHTTPSValidation()
                        .body(model)
                        .when()
                        .post(SIMULATIONS_ENDPOINT + model.getCpf())
                        .then()
                        .assertThat();
    }

    @Dado("que tenha simulação cadastrada")
    public void queTenhaSimulaçãoCadastrada() {
        response =
                given()
                        .relaxedHTTPSValidation()
                        .body(model)
                        .when()
                        .post(SIMULATIONS_ENDPOINT)
                        .then()
                        .assertThat();
    }

    @Quando("alterar todos os dados corretamente")
    public void alterarTodosOsDadosCorretamente() {
        model.setNome("NomeEditado");
        model.setCpf("05944187042");
        model.setEmail("NovoEmail@email.com");
        model.setValor(2500);
        model.setParcelas(10);
        model.setSeguro(false);
    }

    @Quando("editar simulação")
    public void editarSimulação() {
        response =
                given()
                        .relaxedHTTPSValidation()
                        .body(model)
                        .when()
                        .put(SIMULATIONS_ENDPOINT + model.getCpf())
                        .then()
                        .assertThat();
    }

    @Dado("que cpf {string} não tem simulação")
    public void queCpfNãoTemSimulação(String cpf) {
        model.setCpf(cpf);
    }

    @Entao("deve retornar simulação com novos dados")
    public void deveRetornarSimulaçãoComNovosDados() {
        response.statusCode(HttpStatus.SC_OK);
        response.body("nome", is(model.getNome()));
        response.body("cpf", is(model.getCpf()));
        response.body("email", is(model.getEmail()));
        response.body("valor", is(model.getValor()));
        response.body("parcelas", is(model.getParcelas()));
        response.body("seguro", is(model.getSeguro()));
    }

    @Quando("alterar para valor abaixo do minimo permitido")
    public void alterarParaValorAbaixoDoMinimoPermitido() {
        model.setValor(999);
    }

    @Quando("alterar para valor acima do máximo permitido")
    public void alterarParaValorAcimaDoMáximoPermitido() {
        model.setValor(41000);
    }

    @Entao("deve retornar mensagem {string}")
    public void deveRetornarMensagem(String mensagem) {
        response.body("mensagem", is(mensagem));
    }

    @Quando("alterar parcelas para quantidade abaixo do mínimo permitido")
    public void alterarParcelasParaQuantidadeAbaixoDoMinimoPermitido() {
        model.setParcelas(1);
    }

    @Quando("alterar parcelas para quantidade acima do máximo permitido")
    public void alterarParcelasParaQuantidadeAcimaDoMaximoPermitido() {
        model.setParcelas(49);
    }

    @Dado("que tenha simulações previamente cadastradas")
    public void queTenhaSimulaçõesPreviamenteCadastradas() {
        this.id =
                given()
                        .relaxedHTTPSValidation()
                        .body(model)
                        .when()
                        .post(SIMULATIONS_ENDPOINT)
                        .thenReturn()
                        .path("id");
    }

    @Quando("tentar remover simulacao inexistente")
    public void tentarRemoverSimulacaoInexistente() {
        response =
                given()
                        .relaxedHTTPSValidation()
                        .log().all()
                        .when()
                        .delete(SIMULATIONS_ENDPOINT + 0 )
                        .then()
                        .assertThat();
    }
}
