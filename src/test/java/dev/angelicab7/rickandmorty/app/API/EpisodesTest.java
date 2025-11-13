package dev.angelicab7.rickandmorty.app.API;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

public class EpisodesTest {
    private static final String BASE_URL = "https://rickandmortyapi.com/api";
    private static final int EPISODE_ID = 28;
    private static final String EPISODE_NAME = "Pilot";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void testGetEpisodesHealthcheck() {
        Response response = get("/episode");
        assertEquals(response.getStatusCode(), 200);
    }
    @Test
    public void testGetSingleEpisode() {
        given()
                .when()
                .get("/episode/" + EPISODE_ID)
                .then()
                .statusCode(200)
                .body("id", equalTo(28))
                .body("name", notNullValue());
    }

    @Test
    public void testGetMultipleEpisodes() {
        given()
                .when()
                .get("/episode/1,2,3")
                .then()
                .statusCode(200)
                .body("id", hasItems(1,2,3))
                .body("name", notNullValue());
    }

    @Test
    public void testFilterEpisodes() {
        given().
                queryParam("name", EPISODE_NAME).
                when().
                get("/episode/").
                then().
                body("info.count", equalTo(1)).
                body("results", hasSize(1)).
                statusCode(200);
    }

}

