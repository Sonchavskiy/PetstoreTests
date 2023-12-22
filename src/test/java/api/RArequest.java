package api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.ValidatableResponse;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;

public class RArequest {

    public static void setRequestSpec(String url) {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(url)
                .setContentType("application/json")
                .build();
    }
    public static void setResponseSpec(int expectedStatus) {
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(expectedStatus)
                .build();
    }
    public static ValidatableResponse sendGetRequest(String url) {
        return given().when().get(url).then().log().body();
    }
    public static ValidatableResponse sendDeleteRequest(String url) {
        return given().when().delete(url).then().log().body();
    }
    public static ValidatableResponse sendPostRequest(String url, Object args) {
        return given().body(args).when().post(url).then().log().body();
    }
    public static ValidatableResponse sendPutRequest(String url, Object args) {
        return given().body(args).when().put(url).then().log().body();
    }
    public static Object getObject(ValidatableResponse response, Type dataClass) {
        return response.extract().as(dataClass);
    }
    public static Object getList(ValidatableResponse response, Type dataClass) {
        return response.extract().jsonPath().getList("", (Class<?>) dataClass);
    }
    public static Object getMap(ValidatableResponse response) {
        return response.extract().jsonPath().getMap("");
    }
}