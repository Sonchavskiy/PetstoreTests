package api;

import dataModels.ApiResponse;
import dataModels.Order;
import dataModels.Pet;
import dataModels.User;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.List;

import static api.RArequest.*;

public class PetStoreAPI {
    public static final String baseURL = "https://petstore.swagger.io/v2/";

    public static void setURL() {
        setRequestSpec(baseURL);
    }

    @Step("Add pet to the store")
    public static Object addPet(Pet pet, int expectedStatus) {
        ValidatableResponse response = sendPostRequest("pet", pet);
        validateCode(expectedStatus, response);
        return (expectedStatus == 200) ? getObject(response, Pet.class) : getObject(response, ApiResponse.class);
    }

    @Step("Get pet by id")
    public static Object getPetById(Object id, int expectedStatus) {
        ValidatableResponse response = sendGetRequest("pet/" + id);
        validateCode(expectedStatus, response);
        return (expectedStatus == 200) ? getObject(response, Pet.class) : getObject(response, ApiResponse.class);
    }

    @Step("Get pet by status")
    public static Object getPetByStatus(Object status, int expectedStatus) {
        ValidatableResponse response = sendGetRequest("pet/findByStatus?status=" + status);
        validateCode(expectedStatus, response);
        return (expectedStatus == 200) ? getObject(response, Pet.class) : getObject(response, ApiResponse.class);
    }

    @Step("Update pet")
    public static Object updatePet(Pet pet, int expectedStatus) {
        ValidatableResponse response = sendPutRequest("pet", pet);
        validateCode(expectedStatus, response);
        return (expectedStatus == 200) ? getObject(response, Pet.class) : getObject(response, ApiResponse.class);
    }

    @Step("Delete pet")
    public static Object deletePet(Object id, int expectedStatus, Boolean noParse) {
        ValidatableResponse response = sendDeleteRequest("pet/" + id);
        validateCode(expectedStatus, response);
        return (response.extract().body().asString()=="" || noParse)? null : getObject(response, ApiResponse.class);
    }

    @Step("Get order by id")
    public static Object getOrder(Object id, int expectedStatus) {
        ValidatableResponse response = sendGetRequest("order/" + id);
        validateCode(expectedStatus, response);
        return (expectedStatus == 200) ? getObject(response, Order.class) : getObject(response, ApiResponse.class);
    }

    @Step("Place an order")
    public static Object addOrder(Order order, int expectedStatus) {
        ValidatableResponse response = sendPostRequest("order", order);
        validateCode(expectedStatus, response);
        return (expectedStatus == 200) ? getObject(response, Order.class) : getObject(response, ApiResponse.class);
    }

    @Step("Delete order")
    public static Object deleteOrder(Object id, int expectedStatus) {
        ValidatableResponse response = sendDeleteRequest("order/" + id);
        validateCode(expectedStatus, response);
        return (expectedStatus == 200) ? getObject(response, Order.class) : getObject(response, ApiResponse.class);
    }

    @Step("Create user")
    public static Object addUser(User user, int expectedStatus) {
        ValidatableResponse response = sendPostRequest("user", user);
        validateCode(expectedStatus, response);
        return (expectedStatus == 200) ? getObject(response, User.class) : getObject(response, ApiResponse.class);
    }

    @Step("Get user by username")
    public static Object getUser(Object username, int expectedStatus) {
        ValidatableResponse response = sendGetRequest("user/" + username);
        validateCode(expectedStatus, response);
        return (expectedStatus == 200) ? getObject(response, User.class) : getObject(response, ApiResponse.class);
    }

    @Step("Update user")
    public static Object updateUser(Object username, User user, int expectedStatus) {
        ValidatableResponse response = sendPutRequest("user/" + username, user);
        validateCode(expectedStatus, response);
        return (expectedStatus == 200) ? getObject(response, User.class) : getObject(response, ApiResponse.class);
    }

    @Step("Delete order")
    public static Object deleteUser(Object username, int expectedStatus) {
        ValidatableResponse response = sendDeleteRequest("user/" + username);
        validateCode(expectedStatus, response);
        return (expectedStatus == 200) ? getObject(response, User.class) : getObject(response, ApiResponse.class);
    }

    @Step("Logs user into system")
    public static Object logUser(Object username, Object password, int expectedStatus) {
        ValidatableResponse response = sendGetRequest("user/login?username=" + username + "&password=" + password);
        validateCode(expectedStatus, response);
        return (expectedStatus == 200) ? getObject(response, User.class) : getObject(response, ApiResponse.class);
    }

    @Step("Logs out user")
    public static Object logoutUser( int expectedStatus) {
        ValidatableResponse response = sendGetRequest("user/logout");
        validateCode(expectedStatus, response);
        return (expectedStatus == 200) ? getObject(response, User.class) : getObject(response, ApiResponse.class);
    }

    @Step("Create user list")
    public static Object addUserList(List<User> users, int expectedStatus) {
        ValidatableResponse response = sendPostRequest("user/createWithList", users);
        validateCode(expectedStatus, response);
        return (expectedStatus == 200) ? getList(response, User.class) : getObject(response, ApiResponse.class);
    }

    @Step("Create user array")
    public static Object addUserArray(User[] users, int expectedStatus) {
        ValidatableResponse response = sendPostRequest("user/createWithArray", users);
        validateCode(expectedStatus, response);
        return (expectedStatus == 200) ? getList(response, User.class) : getObject(response, ApiResponse.class);
    }
}
