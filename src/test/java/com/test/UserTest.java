package com.test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import static com.test.TestUtils.getCars;
import static com.test.TestUtils.getPeople;
import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsd;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * @author Denys Ovcharuk (DOV) / WorldTicket A/S
 * @since 2017-09-08
 */
public class UserTest extends TestBase {

    @Test
    public void getUserDetailsTest() throws Exception{
        given().spec(spec).
                expect()
                .statusCode(200)
                .body(
                        "email", equalTo("test@hascode.com"),
                        "firstName", equalTo("Tim"),
                        "lastName", equalTo("Testerman"),
                        "id", equalTo("1")
                )
                .when()
                .get("single-user");
    }

    @Test
    public void getUserDetailsWithObjectMapperTest() {

        Person actualPerson = given().spec(spec)
                .expect()
                .statusCode(200)
                .when()
                .get("single-user").as(Person.class);

        assertEquals(actualPerson, getPeople().get(0));
    }

    @Test
    public void getUserDetailsWithObjectMapperXMLTest() {

        Person actualPerson = given().spec(spec)
                .expect()
                .statusCode(200)
                .when()
                .get("single-user/xml").as(Person.class);

        assertEquals(actualPerson, getPeople().get(0));

    }


    @Test
    public void findListOfPersonsTest() {

        List<Person> personsExpected = getPeople();
        String personsActualString = given().spec(spec).expect().statusCode(200).when().get("persons/json").asString();

        JsonPath personsActualJson = new JsonPath(personsActualString);
        List<Person> personsActual = personsActualJson.getList("person",Person.class);

        assertEquals(personsExpected, personsActual);
    }

    @Test
    public void findListOfPersonsJAXBTest() {

        List<Person> personsExpected = getPeople();
        People people = given().spec(spec).get("persons/json").body().as(People.class);

        assertEquals(personsExpected, people.getPeople());
    }

    @Test
    public void findListOfPersonsXMLTest() {

        List<Person> personsExpected = getPeople();
        People people = given().spec(spec).get("persons/xml").body().as(People.class);

        assertEquals(personsExpected, people.getPeople());
    }

    @Test
    public void findListOfCarsXMLTest() {

        List<Car> personsExpected = getCars();
        Cars cars = given().spec(spec).get("cars/xml").body().as(Cars.class);

        assertEquals(personsExpected, cars.getCar());
    }

    @Test
    public void getJsonMapKeyAndValueTest() {

        MyPairRS pairRS = given().spec(spec)
                .expect()
                .statusCode(200)
                .when()
                .get("detail/json/map").as(MyPairRS.class);

        HashMap<MyPair, String> expectedMap = new HashMap<>();
        expectedMap.put(new MyPair("Abbott", "Costello"),"Comedy");
        assertTrue(pairRS.getMap().containsValue("Comedy"));
        assertTrue(pairRS.getMap().containsKey(new MyPair("Abbott", "Costello")));
        assertTrue(pairRS.getMap().equals(expectedMap));

    }

    @Test
    public void getJsonMapKeyAndValueCarsTest() {

        CarsPairRS pairRS = given().spec(spec)
                .expect()
                .statusCode(200)
                .when()
                .get("detail/json/cars/map").as(CarsPairRS.class);

        HashMap<MyPair, String> expectedMap = new HashMap<>();
        expectedMap.put(new MyPair("AUDI", "100"),"AUDI");
        expectedMap.put(new MyPair("BMW", "100"),"BMW");
//        assertTrue(pairRS.getMap().containsValue("Comedy"));
//        assertTrue(pairRS.getMap().containsKey(new MyPair("Abbott", "Costello")));
        assertTrue(pairRS.getCarsMap().equals(expectedMap));

    }


    @Test
    public void basicAuthTest() {
        expect()
                .statusCode(401)
                .when()
                .get("service/secure/person");

        expect()
                .statusCode(200)
                .given().auth().basic("admin","admin")
                .get("service/secure/person");
    }

    //    Setting HTTP Headers
    @Test
    public void testSetRequestHeaders() {
        given()
                .spec(spec)
                .expect()
                .body(equalTo("TEST")).with().header("myparam", "TEST").
                get("header/print");

    }

    //    Verifying HTTP Headers
    @Test
    public void testReturnedHeaders() {
        given()
                .spec(spec)
                .expect()
                .headers("customHeader1", "foo", "anotherHeader", "bar").
                when().
                get("header/multiple");
    }

    //    The following example shows how to set cookies. The REST service sends a 403
    // / Forbidden until a cookie with name=authtoken and value=abcdef is send.
    @Test
    public void testAccessSecuredByCookie() {
        given().spec(spec).
                expect().
                statusCode(403).
                when().
                get("access/cookie-token-secured");

        given().spec(spec).
                cookie("authtoken", "abcdef").
                expect().
                statusCode(200).
                when().
                get("access/cookie-token-secured");
    }

    //    File Uploads
    //    The following example shows how to handle file uploads. We’re sending
//    a text file to the REST service and the service returns the file content as a string in the response body.
    @Test
    public void testFileUpload() {
        final File file = new File(getClass().getClassLoader()
                .getResource("input.txt").getFile());
        assertNotNull(file);
        assertTrue(file.canRead());
        given().spec(spec).
                multiPart(file).
                expect().
                body(equalTo("This is an uploaded test file.")).
                when().
                post("file/upload");
    }

    //    XML verification vs a Schema
    @Test
    public void testGetSingleUserAgainstSchema() {
        InputStream xsd = getClass().getResourceAsStream("/user.xsd");
        assertNotNull(xsd);
        given().spec(spec).
        expect().
                statusCode(200).
                body(
                        matchesXsd(xsd)).
                when().
                get("single-user/xml");
    }

    @Test
    public void testFindtPerson() {
        Person person = getResource("single-user", Person.class);
        assertEquals(person.getFirstNameElem(), "Tim");
    }
}
