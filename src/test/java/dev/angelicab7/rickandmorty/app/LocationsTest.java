package dev.angelicab7.rickandmorty.app;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;
import static io.restassured.matcher.RestAssuredMatchers.*;

public class LocationsTest {
    private static final String BASE_URL = "https://rickandmortyapi.com/api";
    private static final int LOCATION_ID = 7;
    private static final String LOCATION_TYPE = "Planet";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void testGetLocationsHealthcheck() {
        Response response = get("/location");
        assertEquals(response.getStatusCode(), 200);
    }
    @Test
    public void testGetSingleLocation() {

        given()
                .when()
                .get("/location/" + LOCATION_ID)
                .then()
                .statusCode(200)
                .body("id", equalTo(7))
                .body("name", notNullValue());
    }

    @Test
    public void testGetMultipleLocations() {
        given()
                .when()
                .get("/location/1,2,3")
                .then()
                .statusCode(200)
                .body("id", hasItems(1,2,3))
                .body("name", notNullValue());
    }

    @Test
    public void testFilterLocations() {
        given().
            queryParam("name", LOCATION_TYPE).
        when().
            get("/location/").
        then().
            body("info.count", equalTo(11)).
            body("results", hasSize(11)).
            statusCode(200);
    }

}

