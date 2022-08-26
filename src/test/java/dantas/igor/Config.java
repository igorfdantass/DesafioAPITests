package dantas.igor;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.DefaultDataTableCellTransformer;
import io.cucumber.java.DefaultDataTableEntryTransformer;
import io.cucumber.java.DefaultParameterTransformer;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Type;

public class Config {
    @Autowired
    ObjectMapper objectMapper;

    @DefaultParameterTransformer
    @DefaultDataTableCellTransformer
    @DefaultDataTableEntryTransformer
    public Object transform(Object from, Type to){
        return objectMapper.convertValue(from, objectMapper.constructType(to));
    }

    @Before
    public void setup(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.baseURI = "http://localhost:8889/";

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
    }
}