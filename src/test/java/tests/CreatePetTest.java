package tests;

import dataModels.ApiResponse;
import dataModels.Pet;
import dataModels.Tag;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Arrays;

import static api.PetStoreAPI.addPet;
import static api.PetStoreAPI.getPetById;
import static api.RArequest.validateData;

public class CreatePetTest extends AbstractTest{
    final String epic = "Pet";
    final String feature = "Create pet";

    @Test(testName = feature + " - id", dataProvider = "invalidNumberIds")
    @Parameters({"ID"})
    public void TC_PCr01(Object id) {
        testDoc(epic, feature, true,
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

    @Test(testName = feature + " - id", dataProvider = "invalidStrings")
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

    @Test(testName = feature + " - id", dataProvider = "invalidNullAndEmpty")
    @Parameters({"ID"})
    public void TC_PCr03(Object id) {
        testDoc(epic, feature, true,
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

    @Test(testName = feature + " - id", dataProvider = "validIds")
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

    @Test(testName = feature + " - category id", dataProvider = "invalidNumberIds")
    @Parameters({"categoryID"})
    public void TC_PCr05(Object categoryId) {
        testDoc(epic, feature, true,
                "Create pet (with invalid number category id - zero/negative/float/etc.)",
                "Add pet with invalid number category id and check that it is created, id is rounded to integer.");
        Pet petToSend = Pet.defaultPet();
        petToSend.category.id = categoryId;
        Object petReceivedFromPost = addPet(petToSend, 200);
        petToSend.category.id = (int)(double)(Double.valueOf(categoryId.toString()));
        validateData(petToSend, petReceivedFromPost);
        Object petReceivedFromGet = getPetById(petToSend.id, 200);
        validateData(petToSend, petReceivedFromGet);
    }

    @Test(testName = feature + " - category id", dataProvider = "invalidStrings")
    @Parameters({"categoryID"})
    public void TC_PCr06(Object categoryId) {
        testDoc(epic, feature, false,
                "Create pet (with invalid string id)",
                "Add pet with invalid string id and check that it does not get created in the system, corresponding error code is returned.");
        Pet petToSend = Pet.defaultPet();
        petToSend.category.id = categoryId;
        ApiResponse expectedResponse = new ApiResponse(500,"unknown", "something bad happened");
        Object apiResponseFromPost = addPet(petToSend, 500);
        validateData(expectedResponse, apiResponseFromPost);
    }

    @Test(testName = feature + " - category id", dataProvider = "invalidNullAndEmpty")
    @Parameters({"categoryID"})
    public void TC_PCr07(Object categoryId) {
        testDoc(epic, feature, true,
                "Create pet (with invalid number category id - null/empty)",
                "Add pet with invalid number category id and check that it is created, id is assigned with 0 and the rest of data matches initial request.");
        Pet petToSend = Pet.defaultPet();
        petToSend.category.id = categoryId;
        Object petReceivedFromPost = addPet(petToSend, 200);
        petToSend.category.id = 0;
        validateData(petToSend, petReceivedFromPost);
        Object petReceivedFromGet = getPetById(petToSend.id, 200);
        validateData(petToSend, petReceivedFromGet);
    }

    @Test(testName = feature + " - category name", dataProvider = "invalidStringsAndEmpty")
    @Parameters({"categoryName"})
    public void TC_PCr08(String categoryName) {
        testDoc(epic, feature, true,
                "Create pet (with string category name)",
                "Add pet with string category name, check that it is created and validate the data.");
        Pet petToSend = Pet.defaultPet();
        petToSend.category.name = categoryName;
        Object petReceivedFromPost = addPet(petToSend, 200);
        validateData(petToSend, petReceivedFromPost);
        Object petReceivedFromGet = getPetById(petToSend.id, 200);
        validateData(petToSend, petReceivedFromGet);
    }

    @Test(testName = feature + " - name", dataProvider = "invalidStringsAndEmpty")
    @Parameters({"name"})
    public void TC_PCr09(String name) {
        testDoc(epic, feature, true,
                "Create pet (with string name)",
                "Add pet with string name, check that it is created and validate the data.");
        Pet petToSend = Pet.defaultPet();
        petToSend.name = name;
        Object petReceivedFromPost = addPet(petToSend, 200);
        validateData(petToSend, petReceivedFromPost);
        Object petReceivedFromGet = getPetById(petToSend.id, 200);
        validateData(petToSend, petReceivedFromGet);
    }

    @Test(testName = feature + " - photoUrls", dataProvider = "invalidStringsAndEmpty")
    @Parameters({"photoUrls"})
    public void TC_PCr10(String photoUrls) {
        testDoc(epic, feature, true,
                "Create pet (with string photoUrls)",
                "Add pet with string photoUrls, check that it is created and validate the data.");
        Pet petToSend = Pet.defaultPet();
        petToSend.photoUrls = Arrays.asList(photoUrls);
        Object petReceivedFromPost = addPet(petToSend, 200);
        validateData(petToSend, petReceivedFromPost);
        Object petReceivedFromGet = getPetById(petToSend.id, 200);
        validateData(petToSend, petReceivedFromGet);
    }

    @Test(testName = feature + " - tag id", dataProvider = "invalidNumberIds")
    @Parameters({"tagID"})
    public void TC_PCr11(Object tagId) {
        testDoc(epic, feature, true,
                "Create pet (with invalid number tag id - zero/negative/float/etc.)",
                "Add pet with invalid number tag id and check that it is created, id is rounded to integer.");
        Pet petToSend = Pet.defaultPet();
        petToSend.tags = Arrays.asList(new Tag(tagId, "white dog"));
        Object petReceivedFromPost = addPet(petToSend, 200);
        petToSend.tags = Arrays.asList(new Tag((int)(double)(Double.valueOf(tagId.toString())), "white dog"));
        validateData(petToSend, petReceivedFromPost);
        Object petReceivedFromGet = getPetById(petToSend.id, 200);
        validateData(petToSend, petReceivedFromGet);
    }

    @Test(testName = feature + " - tag id", dataProvider = "invalidStrings")
    @Parameters({"tagID"})
    public void TC_PCr12(Object tagId) {
        testDoc(epic, feature, false,
                "Create pet (with invalid string tag id)",
                "Add pet with invalid string tag id and check that it does not get created in the system, corresponding error code is returned.");
        Pet petToSend = Pet.defaultPet();
        petToSend.tags = Arrays.asList(new Tag(tagId, "white dog"));
        ApiResponse expectedResponse = new ApiResponse(500,"unknown", "something bad happened");
        Object apiResponseFromPost = addPet(petToSend, 500);
        validateData(expectedResponse, apiResponseFromPost);
    }

    @Test(testName = feature + " - tag id", dataProvider = "invalidNullAndEmpty")
    @Parameters({"tagID"})
    public void TC_PCr13(Object tagId) {
        testDoc(epic, feature, true,
                "Create pet (with invalid number tag id - null/empty)",
                "Add pet with invalid number tag id and check that it is created, id is assigned with 0 and the rest of data matches initial request.");
        Pet petToSend = Pet.defaultPet();
        petToSend.tags = Arrays.asList(new Tag(tagId, "white dog"));
        Object petReceivedFromPost = addPet(petToSend, 200);
        petToSend.tags = Arrays.asList(new Tag(0, "white dog"));
        validateData(petToSend, petReceivedFromPost);
        Object petReceivedFromGet = getPetById(petToSend.id, 200);
        validateData(petToSend, petReceivedFromGet);
    }

    @Test(testName = feature + " - tag name", dataProvider = "invalidStringsAndEmpty")
    @Parameters({"tagName"})
    public void TC_PCr14(String tagName) {
        testDoc(epic, feature, true,
                "Create pet (with string tag name)",
                "Add pet with string tag name, check that it is created and validate the data.");
        Pet petToSend = Pet.defaultPet();
        petToSend.tags = Arrays.asList(new Tag(1984, tagName));
        Object petReceivedFromPost = addPet(petToSend, 200);
        validateData(petToSend, petReceivedFromPost);
        Object petReceivedFromGet = getPetById(petToSend.id, 200);
        validateData(petToSend, petReceivedFromGet);
    }

    @Test(testName = feature + " - status", dataProvider = "validStatuses")
    @Parameters({"status"})
    public void TC_PCr15(String status) {
        testDoc(epic, feature, true,
                "Create pet (with string status)",
                "Add pet with string status, check that it is created and validate the data.");
        Pet petToSend = Pet.defaultPet();
        petToSend.status = status;
        Object petReceivedFromPost = addPet(petToSend, 200);
        validateData(petToSend, petReceivedFromPost);
        Object petReceivedFromGet = getPetById(petToSend.id, 200);
        validateData(petToSend, petReceivedFromGet);
    }

    @Test(testName = feature + " - status", dataProvider = "invalidStringsAndEmpty")
    @Parameters({"status"})
    public void TC_PCr16(String status) {
        testDoc(epic, feature, true,
                "Create pet (with string status)",
                "Add pet with string status, check that it is created and validate the data.");
        Pet petToSend = Pet.defaultPet();
        petToSend.status = status;
        Object petReceivedFromPost = addPet(petToSend, 200);
        validateData(petToSend, petReceivedFromPost);
        Object petReceivedFromGet = getPetById(petToSend.id, 200);
        validateData(petToSend, petReceivedFromGet);
    }
}
