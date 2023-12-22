package api;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.response.ValidatableResponseLogSpec;
import org.testng.Assert;

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
    @Step("Send GET request")
    public static ValidatableResponse sendGetRequest(String url) {
        ValidatableResponse response = given().when().get(url).then().log().body();
        logRequest("GET", url, null, response);
        return response;
    }
    @Step("Send DELETE request")
    public static ValidatableResponse sendDeleteRequest(String url) {
        ValidatableResponse response = given().when().delete(url).then().log().body();
        logRequest("DELETE", url, null, response);
        return response;
    }
    @Step("Send POST request")
    public static ValidatableResponse sendPostRequest(String url, Object args) {
        ValidatableResponse response = given().body(args).when().post(url).then().log().body();
        logRequest("POST", url, null, response);
        return response;
    }
    @Step("Send PUT request")
    public static ValidatableResponse sendPutRequest(String url, Object args) {
        ValidatableResponse response = given().body(args).when().put(url).then().log().body();
        logRequest("PUT", url, null, response);
        return response;
    }
    @Step("Parse response to DataClass")
    public static Object getObject(ValidatableResponse response, Type dataClass) {
        Object data = response.extract().as(dataClass);
        logData(data);
        return data;
    }
    @Step("Parse response to List")
    public static Object getList(ValidatableResponse response, Type dataClass) {
        Object data = response.extract().jsonPath().getList("", (Class<?>) dataClass);
        logData(data);
        return data;
    }
    @Step("Parse response to Map")
    public static Object getMap(ValidatableResponse response) {
        Object data = response.extract().jsonPath().getMap("");
        logData(data);
        return data;
    }
    @Step("Validate response code")
    public static void validateCode(int expectedCode, ValidatableResponse response) {
        Assert.assertEquals(response.extract().statusCode(), expectedCode, "Unexpected response status code:");
    }
    private static void logRequest(String method, String url, Object args, ValidatableResponse response) {
        String fullUrl = ((RequestSpecificationImpl) RestAssured.requestSpecification).getBaseUri() + url;
        String request = "\tMethod: " + method + "\n\tURL: " + fullUrl;
        if (args !=null) request += "\n\tArguments: " + args.toString();
        Allure.attachment("Request:", request);
        String resp = "\tStatus: "+response.extract().statusLine() + "\tContent: " + response.extract().body().asString();
        Allure.attachment("Response:",resp);
    }
    private static void logData(Object data) {
        System.out.println("\nParsed object: \n\t" + data.toString());
        Allure.attachment("Parsed object:", data.toString());
    }
}