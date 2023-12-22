package api;

import dataModels.ApiResponse;
import dataModels.Pet;
import io.restassured.response.ValidatableResponse;

import static api.RArequest.*;

public class PetStoreAPI {
    public static final String baseURL = "https://petstore.swagger.io/v2/";

    public static void setURL() {
        setRequestSpec(baseURL);
    }

    public static Object getPet(Object id, int expectedStatus) {
        setResponseSpec(expectedStatus);
        ValidatableResponse response = sendGetRequest("pet/" + id);
        return (expectedStatus == 200) ? getObject(response, Pet.class) : getObject(response, ApiResponse.class);
    }


}
