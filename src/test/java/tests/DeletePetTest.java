package tests;

import dataModels.ApiResponse;
import dataModels.Pet;
import dataModels.Tag;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Arrays;

import static api.PetStoreAPI.*;
import static api.RArequest.validateData;

public class DeletePetTest extends AbstractTest{
    final String epic = "Pet";
    final String feature = "Delete pet";

    @Test(testName = feature + " - id", dataProvider = "invalidNumberIds")
    @Parameters({"ID"})
    public void TC_PDel01(Object id) {
        testDoc(epic, feature, false,
                "Delete pet (with invalid number id - zero/negative/float/etc.)",
                "Delete pet with invalid number id and validate the error.");
        Object apiResponseFromPost = deletePet(id, 404, false);
        if (id instanceof Double) {
            ApiResponse expectedResponse = new ApiResponse(404,"unknown", "java.lang.NumberFormatException: For input string: \""+ id + "\"");
            validateData(expectedResponse, apiResponseFromPost);
        }
    }

    @Test(testName = feature + " - id", dataProvider = "invalidStrings")
    @Parameters({"ID"})
    public void TC_PDel02(Object id) {
        testDoc(epic, feature, false,
                "Delete pet (with invalid string id)",
                "Delete pet with invalid string id and validate the error.");
        ApiResponse expectedResponse = new ApiResponse(404,"unknown", "java.lang.NumberFormatException: For input string: \""+ id + "\"");
        Object apiResponseFromPost = deletePet(id, 404,false);
        validateData(expectedResponse, apiResponseFromPost);
    }

    @Test(testName = feature + " - id", dataProvider = "invalidNull")
    @Parameters({"ID"})
    public void TC_PDel03(Object id) {
        testDoc(epic, feature, false,
                "Delete pet (with invalid id - null)",
                "Delete pet with invalid id and validate the error.");
        ApiResponse expectedResponse = new ApiResponse(404,"unknown", "java.lang.NumberFormatException: For input string: \""+ id + "\"");
        Object apiResponseFromPost = deletePet(id, 404,false);
        validateData(expectedResponse, apiResponseFromPost);
    }

    @Test(testName = feature + " - id", dataProvider = "invalidEmpty")
    @Parameters({"ID"})
    public void TC_PDel04(Object id) {
        testDoc(epic, feature, false,
                "Delete pet (with invalid id - empty)",
                "Delete pet with invalid id and validate the error.");
        ApiResponse expectedResponse = new ApiResponse(null,"unknown", null);
        Object apiResponseFromPost = deletePet(id, 405,true);
    }

    @Test(testName = feature + " - id", dataProvider = "validIds")
    @Parameters({"ID"})
    public void TC_PDel05(Object id) {
        testDoc(epic, feature, true,
                "Delete pet (valid existing ids)",
                "Delete pet with valid existing ids and check that it is deleted.");
        Pet petToSend = Pet.defaultPet();
        petToSend.id = id;
        Object existing = addPet(petToSend, 200);

        ApiResponse expectedResponse = new ApiResponse(200,"unknown", id.toString());
        Object apiResponseReceivedFromPost = deletePet(id, 200, false);
        validateData(expectedResponse, apiResponseReceivedFromPost);

        ApiResponse expectedGetResponse = new ApiResponse(1,"error", "Pet not found");
        Object apiResponseReceivedFromGet = getPetById(petToSend.id, 404);
        validateData(expectedGetResponse, apiResponseReceivedFromGet);
    }

    @Test(testName = feature + " - id", dataProvider = "validIds")
    @Parameters({"ID"})
    public void TC_PDel06(Object id) {
        testDoc(epic, feature, false,
                "Delete pet (valid non-existing ids)",
                "Delete pet with valid, but non-existing id.");
        Pet petToSend = Pet.defaultPet();
        petToSend.id = id;
        Object existing = addPet(petToSend, 200);
        Object notExisting = deletePet(id, 200, false);

        Object apiResponseReceivedFromPost = deletePet(id, 404, false);
    }
}
