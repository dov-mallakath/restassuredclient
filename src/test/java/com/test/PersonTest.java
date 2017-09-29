package com.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.test.TestUtils.getPeople;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class PersonTest extends TestBase {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetSingleUserWithObjectMapper() throws IOException {
        final File userJSON = new File(getClass().getClassLoader()
                .getResource("user.json").getFile());

        Person expectedPerson  = objectMapper.readValue(userJSON, Person.class);

        Person actualPerson = given().spec(spec).
                expect().
                statusCode(200).
                when().
                get("single-user").as(Person.class);
        assertEquals(actualPerson, expectedPerson);
    }

    @Test
    public void testGetSingleUserAsListJSON() throws IOException {
        final File userJSON = new File(getClass().getClassLoader()
                .getResource("users.json").getFile());

        People expectedPerson  = objectMapper.readValue(userJSON, People.class);

        People actualPerson = given().spec(spec).
                expect().
                statusCode(200).
                when().
                get("/persons/json").as(People.class);
        assertEquals(actualPerson, expectedPerson);
    }
}