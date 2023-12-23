package tests;

import dataModels.ApiResponse;
import dataModels.Pet;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static api.PetStoreAPI.addPet;
import static api.PetStoreAPI.getPetById;
import static api.RArequest.validateData;

public class CreatePetTest extends AbstractTest{
    final String epic = "Pet";
    final String feature = "Create pet";

    @Test(testName = feature, dataProvider = "invalidNumberIds")
    @Parameters({"ID"})
    public void TC_PCr01(Object id) {
        testDoc(epic, feature, false,
                "Create pet (with invalid number id - zero/negative/float/etc.)",
                "Add pet with invalid number id and check that it is created, id is assigned automatically and the rest of data matches initial request.");
        Pet petToSend = Pet.defaultPet();
        petToSend.id = id;
        Object petReceivedFromPost = addPet(petToSend, 200);
        Object assignedId = ((Pet)petReceivedFromPost).id;
        petToSend.id = assignedId;
        validateData(petToSend, petReceivedFromPost);
        Object petReceivedFromGet = getPetById(assignedId, 200);
        validateData(petToSend, petReceivedFromGet);
    }

    @Test(testName = feature, dataProvider = "invalidStrings")
    @Parameters({"ID"})
    public void TC_PCr02(Object id) {
        testDoc(epic, feature, false,
                "Create pet (with invalid string id)",
                "Add pet with invalid string id and check that it does not get created in the system, corresponding error code is returned and get method returns similar error.");
        Pet petToSend = Pet.defaultPet();
        petToSend.id = id;
        ApiResponse expectedResponse = new ApiResponse(500,"unknown", "something bad happened");
        Object apiResponseFromPost = addPet(petToSend, 500);
        validateData(expectedResponse, apiResponseFromPost);
        ApiResponse expectedGetResponse = new ApiResponse(404,"unknown", "java.lang.NumberFormatException: For input string: \""+ id + "\"");
        Object responseReceivedFromGet = getPetById(id, 404);
        validateData(expectedGetResponse, responseReceivedFromGet);
    }

    @Test(testName = feature, dataProvider = "invalidNullAndEmpty")
    @Parameters({"ID"})
    public void TC_PCr03(Object id) {
        testDoc(epic, feature, false,
                "Create pet (with invalid id - null/empty)",
                "Add pet with invalid id and check that it is created, id is assigned automatically and the rest of data matches initial request.");
        Pet petToSend = Pet.defaultPet();
        petToSend.id = id;
        Object petReceivedFromPost = addPet(petToSend, 200);
        Object assignedId = ((Pet)petReceivedFromPost).id;
        petToSend.id = assignedId;
        validateData(petToSend, petReceivedFromPost);
        Object petReceivedFromGet = getPetById(assignedId, 200);
        validateData(petToSend, petReceivedFromGet);
    }

    @Test(testName = feature, dataProvider = "validIds")
    @Parameters({"ID"})
    public void TC_PCr04(Object id) {
        testDoc(epic, feature, true,
                "Create pet (valid non-existing id)",
                "Add pet with valid non-existing id and check that it is created and matches initial request.");
        Pet petToSend = Pet.defaultPet();
        petToSend.id = id;
        Object petReceivedFromPost = addPet(petToSend, 200);
        validateData(petToSend, petReceivedFromPost);
        Object petReceivedFromGet = getPetById(id, 200);
        validateData(petToSend, petReceivedFromGet);
    }
}
