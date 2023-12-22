package tests;

import dataModels.Pet;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class MainTest {
//    @Step("step1")
//    public void step1(){
//        System.out.println("Hello");
//        Allure.attachment("lala", "123");
//    }
//
//    @Step("step2")
//    public void step2(){
//        System.out.println(" step 2");
//    }
//
//    @DataProvider
//    public static Object[] data(){
//        return new Object[]{"sonya", "sereja"};
//    }
//
//    @Test(testName = "Hi + name test", dataProvider = "data")
//    @Description("Print hi + name")
//    @Epic("pet")
//    @Feature("create")
//    @Story("testCase 1")
//    @Parameters({"name123"})
//    public void testMain(String name) {
//
//        System.out.println("Hi " + name);
//        step1();
//        step2();
//    }
//
//    @Test(testName = "Hi + [] test")
//    @Description("Print hi + []")
//    @Epic("pet")
//    @Feature("update")
//    @Story("testCase 2")
//    public void test2() {
//        System.out.println("Hi ");
//        step1();
//        step2();
//    }

    @DataProvider
    public static Object[] dataCreate12(){
        return new Object[]{1,2,3};
    }
    public static final String baseURL = "https://petstore.swagger.io/v2/";

    @Step("1. Submit POST request filled in accordance with test data")
    public void step1(Integer id){
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(baseURL)
                .setContentType("application/json")
                .build();
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
        Pet pet = given()
                .when()
                .get("pet/" + id)
                .then()
                .log().all()
                .extract().body().jsonPath().getObject("", Pet.class);
        validation(id);
    }

    @Step("2. Submit GET request with the predefined/assigned id")
    public void step2(Integer id){
        validation(id);
    }

    @Step("Data validation")
    public void validation(Integer id){

    }

    @Test(testName = "Create pet", dataProvider = "dataCreate12")
    @Description("Add pet and check its existence and fields.")
    @Epic("Pet")
    @Feature("Create")
    @Story("TestCase - p.create.12")
    @Parameters({"ID"})
    public void createPet(Integer id){
        step1(id);
        step2(id);
    }
}