package com.test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.List;

import static com.test.TestUtils.getArticlesFromJson;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

/**
 * @author Denys Ovcharuk (DOV) / WorldTicket A/S
 * @since 2017-09-08
 */
public class JsonplaceholderTest {

    private RequestSpecification spec;

    @BeforeSuite
    public void setUp() {
        String baseUrl = "http://jsonplaceholder.typicode.com";

        spec = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
    }

    @Test
    public void getArticleDetails() throws Exception {

        String jsonExpected = new FileOperations().readFile("articles-list.json");
        List<Article> articlesExpected = getArticlesFromJson(jsonExpected);

        Article actualArtice = given().spec(spec)
                .expect()
                .statusCode(200)
                .when()
                .get("/posts/1").as(Article.class);

        assertEquals(actualArtice, articlesExpected.get(0));

    }

    @Test
    public void getAllArticleDetails() throws Exception {

        String jsonExpected = new FileOperations().readFile("articles-list.json");
        List<Article> articlesExpected = getArticlesFromJson(jsonExpected);

        String jsonActual = given().spec(spec).get("/posts").asString();
        List<Article> articlesActual = new JsonPath(jsonActual).getList("", Article.class);

        assertEquals(articlesExpected,articlesActual);

    }


}
