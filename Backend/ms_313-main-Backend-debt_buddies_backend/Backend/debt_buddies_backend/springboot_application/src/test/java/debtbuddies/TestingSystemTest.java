package debtbuddies;

import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.JsonObject;
import debtbuddies.Users.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
//import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
//import org.springframework.boot.test.web.server.LocalServerPort;
// SBv3

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@RunWith(SpringRunner.class)
public class TestingSystemTest {

    int current_id = 109;

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void testTest(){
        assertEquals("hi", "hi");
    }

    @Test
    public void GetUsersTest(){
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                body("").
                when().
                get("/users");

        int statusCode = response.getStatusCode();
        System.out.println(response.getBody().asString());

        assertEquals(200, statusCode);
    }

    @Test
    public void GetUserTest(){
        String userString = "{\"id\":13,\"userName\":\"beans\",\"isOnline\":false,\"email\":\"soup\",\"password\":\"5432\",\"icon\":\"icon3\",\"coins\":0}";
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                body("").
                when().
                get("/users/86");

        int statusCode = response.getStatusCode();
        System.out.println(response.getBody().asString());

        assertEquals(200, statusCode);
    }

    @Test
    public void CreateUserTest() throws UnsupportedEncodingException {
        JsonObject reqparam=new JsonObject();
        reqparam.addProperty("id", current_id);
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
    }

    @Test
    public void CreateUserFailTest() throws UnsupportedEncodingException {

        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset", "utf-8").
                body("{}").
                when().
                post("/users");

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
    public void UpdateUserTest() throws InterruptedException {
        JsonObject reqparam=new JsonObject();
        reqparam.addProperty("id", 99);
        reqparam.addProperty("userName", "beans");
        reqparam.addProperty("isOnline", false);
        reqparam.addProperty("email", "123@grimace.org");
        reqparam.addProperty("password", "ronald123");
        reqparam.addProperty("coins", 50);
        reqparam.addProperty("icon", "icon3");

        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset", "utf-8").
                body(reqparam.toString().getBytes(StandardCharsets.UTF_8)).
                when().
                put("/users/"+Integer.toString(99));

        int statusCode = response.getStatusCode();

        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals(50, returnObj.get("coins"));
        } catch (JSONException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void UpdateUserFailTest(){
        JsonObject reqparam=new JsonObject();
        reqparam.addProperty("id", 99);
        reqparam.addProperty("userName", "beans");
        reqparam.addProperty("isOnline", false);
        reqparam.addProperty("email", "123@grimace.org");
        reqparam.addProperty("password", "ronald123");
        reqparam.addProperty("coins", 30);
        reqparam.addProperty("icon", "icon3");

        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset", "utf-8").
                body(reqparam.toString().getBytes(StandardCharsets.UTF_8)).
                when().
                put("/users/"+Integer.toString(0));

        int statusCode = response.getStatusCode();

        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();

        assertEquals("", returnString);
        /*
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals(current_id, returnObj.get("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

         */
    }

    @Test
    public void DeleteUserTest(){

        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset", "utf-8").
                body("").
                when().
                delete("/users/"+Integer.toString(current_id));

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
