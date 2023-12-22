package api;

import dataModels.ApiResponse;
import dataModels.Order;
import dataModels.Pet;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static api.RArequest.*;

public class PetStoreAPI {
    public static final String baseURL = "https://petstore.swagger.io/v2/";

    public static void setURL() {
        setRequestSpec(baseURL);
    }

    @Step("Get pet by id")
    public static Object getPet(Object id, int expectedStatus) {
        ValidatableResponse response = sendGetRequest("pet/" + id);
        validateCode(expectedStatus, response);
        return (expectedStatus == 200) ? getObject(response, Pet.class) : getObject(response, ApiResponse.class);
    }

    @Step("Add pet to the store")
    public static Object addPet(Pet pet, int expectedStatus) {
        ValidatableResponse response = sendPostRequest("pet", pet);
        validateCode(expectedStatus, response);
        return (expectedStatus == 200) ? getObject(response, Pet.class) : getObject(response, ApiResponse.class);
    }

    @Step("Get order by id")
    public static Object getOrder(Object id, int expectedStatus) {
        ValidatableResponse response = sendGetRequest("order/" + id);
        validateCode(expectedStatus, response);
        return (expectedStatus == 200) ? getObject(response, Order.class) : getObject(response, ApiResponse.class);
    }
}
