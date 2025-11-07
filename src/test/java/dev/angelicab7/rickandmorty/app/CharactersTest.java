package dev.angelicab7.rickandmorty.app;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

public class CharactersTest {
    private static final Logger logger = LoggerFactory.getLogger(CharactersTest.class);
    private static final String BASE_URL = "https://rickandmortyapi.com/api";
    private static final int CHARACTER_ID = 183;
    private static final String CHARACTER_NAME = "Izzy";
    private static final String CHARACTER_TYPE = "Cat";


    @BeforeClass
    public void setup() {
        logger.info("Setting up Characters API tests");
        RestAssured.baseURI = BASE_URL;
        logger.info("Base URI set to: {}", BASE_URL);
    }

    @Test
    public void testGetCharactersHealthcheck() {
        logger.info("Starting testGetCharactersHealthcheck");
        Response response = get("/character/");
        logger.debug("Response status code: {}", response.getStatusCode());
        assertEquals(response.getStatusCode(), 200);
        logger.info("testGetCharactersHealthcheck passed");
    }
    @Test
    public void testGetSingleCharacter() {
        logger.info("Starting testGetSingleCharacter with ID: {}", CHARACTER_ID);
        Response response = get("/character/" + CHARACTER_ID);

        logger.debug("Response status for Single Character: {}", response.getStatusCode());
        assertEquals(response.getStatusCode(), 200);
        response.then()
                .statusCode(200)
                .body("id", equalTo(183))
                .body("name", notNullValue());
        logger.debug("Retrieved character - ID: {}", CHARACTER_ID);
        logger.info("testGetSingleCharacter passed");

    }

    @Test
    public void testGetMultipleCharacters() {
        logger.info("Starting testGetMultipleCharacters");
        Response response = get("/character/8,7,3");

        logger.debug("Response status for Multiple Characters: {}", response.getStatusCode());
        assertEquals(response.getStatusCode(), 200);
        response.then()
                .statusCode(200)
                .body("id", hasItems(8, 7, 3))
                .body("name", notNullValue());
        logger.info("testGetMultipleCharacters passed");
    }

    @Test
    public void testFilterCharacters() {
        logger.info("Starting testFilterCharacters with name: {}, status: {}:", CHARACTER_NAME, CHARACTER_TYPE);
        Response response = get("/character?name=" + CHARACTER_NAME + "&type=" +CHARACTER_TYPE);

        response.then().
                body("info.count", equalTo(1)).
                body("results", hasSize(1)).
                statusCode(200);
        logger.debug("Retrieved character with name: {}, status: {}:", CHARACTER_NAME, CHARACTER_TYPE);

        logger.info("testFilterCharacters passed");
    }

}
