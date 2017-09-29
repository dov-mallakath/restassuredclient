package com.test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeSuite;

import static io.restassured.RestAssured.given;

public class TestBase {

    protected RequestSpecification spec;


    @BeforeSuite
    public void setUp() {
        String baseURL = "http://localhost:8080/service/";

//        JSON using Jackson 2 (Faster Jackson (databind))
//        JSON using Jackson (databind)
//        JSON using Gson
//        XML using JAXB
//        RestAssured.config = config().objectMapperConfig(
//                new ObjectMapperConfig().jackson2ObjectMapperFactory(
//                        (aClass, s) -> new ObjectMapper()));

        spec = new RequestSpecBuilder()
                .setBaseUri(baseURL)
                .addFilter(new ResponseLoggingFilter())//log request and response for better debugging. You can also only log if a requests fails.
                .addFilter(new RequestLoggingFilter())
                .build();
    }

    protected  <T> T getResource(String location, Class<T> responseClass) {
        return given()
                .spec(spec)
                .when()
                .get(location)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(responseClass);
    }

}