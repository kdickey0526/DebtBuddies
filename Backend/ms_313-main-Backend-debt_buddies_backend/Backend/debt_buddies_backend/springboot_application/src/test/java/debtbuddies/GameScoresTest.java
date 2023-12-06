package debtbuddies;


import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.JsonObject;
import debtbuddies.GameScores.GameScoreRepository;
import debtbuddies.Users.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
//import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
//import org.springframework.boot.test.web.server.LocalServerPort;
// SBv3

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@RunWith(SpringRunner.class)
public class GameScoresTest {

    String userName = "Owen";

    @Autowired
    private GameScoreRepository myRepo;

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }


    public void GetGameScoresTest(){
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                body("").
                when().
                get("/GameScores");

        int statusCode = response.getStatusCode();
        System.out.println(response.getBody().asString());

        assertEquals(200, statusCode);
    }

    @Test
    public void GetGameScoreTest(){
        String userString = "{\n" +
                "    \"id\": 1,\n" +
                "    \"whack\": 5,\n" +
                "    \"warWon\": 4,\n" +
                "    \"warLost\": 3,\n" +
                "    \"blackJack\": 2,\n" +
                "    \"userName\": \"Owen\"\n" +
                "}";
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                body("").
                when().
                get("/GameScore/Owen");

        int statusCode = response.getStatusCode();
        System.out.println(response.getBody().asString());

        assertEquals(200, statusCode);
    }

 /*   @Test
    public void CreateUserTest() throws UnsupportedEncodingException {
        JsonObject reqparam=new JsonObject();
        reqparam.addProperty("id", userName);
        reqparam.addProperty("userName", "beans");
        reqparam.addProperty("isOnline", false);
        reqparam.addProperty("email", "123@grimace.org");
        reqparam.addProperty("password", "ronald123");
        reqparam.addProperty("coins", 8);
        reqparam.addProperty("icon", "icon3");

        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset", "utf-8").
                body(reqparam.toString().getBytes(StandardCharsets.UTF_8)).
                when().
                post("/users");

        int statusCode = response.getStatusCode();
        System.out.println(response.getBody().asString());

        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("success", returnObj.get("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/

    @Test
    public void CreateGameScoreFailTest() throws UnsupportedEncodingException {

        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset", "utf-8").
                body("{}").
                when().
                post("/GameScores");

        int statusCode = response.getStatusCode();

        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("failure", returnObj.get("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void UpdateGameScoreTest() throws InterruptedException {
        JsonObject reqparam=new JsonObject();
        reqparam.addProperty("id", 1);
        reqparam.addProperty("userName", "Owen");
        reqparam.addProperty("black_jack", 5);
        reqparam.addProperty("war_lost", "5g");
        reqparam.addProperty("war_won", "10");
        reqparam.addProperty("whack", 50);

        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset", "utf-8").
                body(reqparam.toString().getBytes(StandardCharsets.UTF_8)).
                when().
                put("/GameScore/Owen");

        int statusCode = response.getStatusCode();

        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals(50, returnObj.get("whack"));
        } catch (JSONException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void UpdateGameScoreFailTest(){
        JsonObject reqparam=new JsonObject();
        reqparam.addProperty("id", 1);
        reqparam.addProperty("userName", "Owen");
        reqparam.addProperty("black_jack", 5);
        reqparam.addProperty("war_lost", "5g");
        reqparam.addProperty("war_won", "10");
        reqparam.addProperty("whack", 50);

        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset", "utf-8").
                body(reqparam.toString().getBytes(StandardCharsets.UTF_8)).
                when().
                put("/GameScores/"+ userName);

        int statusCode = response.getStatusCode();

        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();

        assertEquals("", returnString);
        /*
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals(userName, returnObj.get("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

         */
    }

    @Test
    public void DeleteGameScoreTest(){

        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset", "utf-8").
                body("").
                when().
                delete("/GameScores/"+userName);

        int statusCode = response.getStatusCode();

        assertEquals(200, statusCode);

    }

    /*
    @Test
    public void reverseTest() {
        // Send request and receive response
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                body("hello").
                when().
                post("/reverse");


        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        try {
            JSONArray returnArr = new JSONArray(returnString);
            JSONObject returnObj = returnArr.getJSONObject(returnArr.length()-1);
            assertEquals("olleh", returnObj.get("data"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void capitalizeTest() {
        // Send request and receive response
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                body("hello").
                when().
                post("/capitalize");


        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        try {
            JSONArray returnArr = new JSONArray(returnString);
            JSONObject returnObj = returnArr.getJSONObject(returnArr.length()-1);
            assertEquals("HELLO", returnObj.get("data"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

     */
}
