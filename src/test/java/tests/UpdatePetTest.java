package tests;

import dataModels.ApiResponse;
import dataModels.Pet;
import dataModels.Tag;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Arrays;

import static api.PetStoreAPI.*;
import static api.RArequest.validateData;

public class UpdatePetTest extends AbstractTest{
    final String epic = "Pet";
    final String feature = "Update pet";

    @Test(testName = feature + " - id", dataProvider = "invalidNumberIds")
    @Parameters({"ID"})
    public void TC_PUpd01(Object id) {
        testDoc(epic, feature, true,
                "Update pet (with invalid number id - zero/negative/float/etc.)",
                "Update pet with invalid number id and check that it gets created, id is assigned automatically and the rest of data matches initial request.");
        Pet petToSend = Pet.defaultPet();
        petToSend.id = id;
        Object petReceivedFromPost = updatePet(petToSend, 200);
        Object assignedId = ((Pet)petReceivedFromPost).id;
        petToSend.id = assignedId;
        validateData(petToSend, petReceivedFromPost);
        Object petReceivedFromGet = getPetById(assignedId, 200);
        validateData(petToSend, petReceivedFromGet);
    }

    @Test(testName = feature + " - id", dataProvider = "invalidStrings")
    @Parameters({"ID"})
    public void TC_PUpd02(Object id) {
        testDoc(epic, feature, false,
                "Update pet (with invalid string id)",
                "Update pet with invalid string id and check that it does not get created in the system, corresponding error code is returned and get method returns similar error.");
        Pet petToSend = Pet.defaultPet();
        petToSend.id = id;
        ApiResponse expectedResponse = new ApiResponse(500,"unknown", "something bad happened");
        Object apiResponseFromPost = updatePet(petToSend, 500);
        validateData(expectedResponse, apiResponseFromPost);
        ApiResponse expectedGetResponse = new ApiResponse(404,"unknown", "java.lang.NumberFormatException: For input string: \""+ id + "\"");
        Object responseReceivedFromGet = getPetById(id, 404);
        validateData(expectedGetResponse, responseReceivedFromGet);
    }

    @Test(testName = feature + " - id", dataProvider = "invalidNullAndEmpty")
    @Parameters({"ID"})
    public void TC_PUpd03(Object id) {
        testDoc(epic, feature, true,
                "Update pet (with invalid id - null/empty)",
                "Update pet with invalid id and check that it is created, id is assigned automatically and the rest of data matches initial request.");
        Pet petToSend = Pet.defaultPet();
        petToSend.id = id;
        Object petReceivedFromPost = updatePet(petToSend, 200);
        Object assignedId = ((Pet)petReceivedFromPost).id;
        petToSend.id = assignedId;
        validateData(petToSend, petReceivedFromPost);
        Object petReceivedFromGet = getPetById(assignedId, 200);
        validateData(petToSend, petReceivedFromGet);
    }

    @Test(testName = feature + " - id", dataProvider = "validIds")
    @Parameters({"ID"})
    public void TC_PUpd04(Object id) {
        testDoc(epic, feature, true,
                "Update pet (valid id)",
                "Update pet with valid id and check that it is created and matches initial request.");
        Pet petToSend = Pet.defaultPet();
        petToSend.id = id;
        Object petReceivedFromPost = updatePet(petToSend, 200);
        validateData(petToSend, petReceivedFromPost);
        Object petReceivedFromGet = getPetById(id, 200);
        validateData(petToSend, petReceivedFromGet);
    }

    @Test(testName = feature + " - category id", dataProvider = "invalidNumberIds")
    @Parameters({"categoryID"})
    public void TC_PUpd05(Object categoryId) {
        testDoc(epic, feature, true,
                "Update existing pet (with invalid number category id - zero/negative/float/etc.)",
                "Update existing pet with invalid number category id and check that it is updated, id is rounded to integer.");
        Pet anotherPet = Pet.anotherPet();
        Object existing = addPet(anotherPet, 200);

        Pet petToSend = Pet.defaultPet();
        petToSend.id = anotherPet.id;
        petToSend.category.id = categoryId;
        Object petReceivedFromPost = updatePet(petToSend, 200);
        petToSend.category.id = (int)(double)(Double.valueOf(categoryId.toString()));
        validateData(petToSend, petReceivedFromPost);
        Object petReceivedFromGet = getPetById(petToSend.id, 200);
        validateData(petToSend, petReceivedFromGet);
    }

    @Test(testName = feature + " - category id", dataProvider = "invalidStrings")
    @Parameters({"categoryID"})
    public void TC_PUpd06(Object categoryId) {
        testDoc(epic, feature, false,
                "Update existing pet (with invalid string id)",
                "Update existing pet with invalid string id and check that it does not get created in the system, corresponding error code is returned.");
        Pet anotherPet = Pet.anotherPet();
        Object existing = addPet(anotherPet, 200);

        Pet petToSend = Pet.defaultPet();
        petToSend.id = anotherPet.id;
        petToSend.category.id = categoryId;
        ApiResponse expectedResponse = new ApiResponse(500,"unknown", "something bad happened");
        Object apiResponseFromPost = updatePet(petToSend, 500);
        validateData(expectedResponse, apiResponseFromPost);
    }

    @Test(testName = feature + " - category id", dataProvider = "invalidNullAndEmpty")
    @Parameters({"categoryID"})
    public void TC_PUpd07(Object categoryId) {
        testDoc(epic, feature, true,
                "Update existing pet (with invalid number category id - null/empty)",
                "Update existing pet with invalid number category id and check that it is updated, id is assigned with 0 and the rest of data matches initial request.");
        Pet anotherPet = Pet.anotherPet();
        Object existing = addPet(anotherPet, 200);

        Pet petToSend = Pet.defaultPet();
        petToSend.id = anotherPet.id;
        petToSend.category.id = categoryId;
        Object petReceivedFromPost = updatePet(petToSend, 200);
        petToSend.category.id = 0;
        validateData(petToSend, petReceivedFromPost);
        Object petReceivedFromGet = getPetById(petToSend.id, 200);
        validateData(petToSend, petReceivedFromGet);
    }

    @Test(testName = feature + " - category name", dataProvider = "invalidStringsAndEmpty")
    @Parameters({"categoryName"})
    public void TC_PUpd08(String categoryName) {
        testDoc(epic, feature, true,
                "Update existing pet (with string category name)",
                "Update existing pet with string category name, check that it is updated and validate the data.");
        Pet anotherPet = Pet.anotherPet();
        Object existing = addPet(anotherPet, 200);

        Pet petToSend = Pet.defaultPet();
        petToSend.id = anotherPet.id;
        petToSend.category.name = categoryName;
        Object petReceivedFromPost = updatePet(petToSend, 200);
        validateData(petToSend, petReceivedFromPost);
        Object petReceivedFromGet = getPetById(petToSend.id, 200);
        validateData(petToSend, petReceivedFromGet);
    }

    @Test(testName = feature + " - name", dataProvider = "invalidStringsAndEmpty")
    @Parameters({"name"})
    public void TC_PUpd09(String name) {
        testDoc(epic, feature, true,
                "Update existing pet (with string name)",
                "Update existing pet with string name, check that it is updated and validate the data.");
        Pet anotherPet = Pet.anotherPet();
        Object existing = addPet(anotherPet, 200);

        Pet petToSend = Pet.defaultPet();
        petToSend.id = anotherPet.id;
        petToSend.name = name;
        Object petReceivedFromPost = updatePet(petToSend, 200);
        validateData(petToSend, petReceivedFromPost);
        Object petReceivedFromGet = getPetById(petToSend.id, 200);
        validateData(petToSend, petReceivedFromGet);
    }

    @Test(testName = feature + " - photoUrls", dataProvider = "invalidStringsAndEmpty")
    @Parameters({"photoUrls"})
    public void TC_PUpd10(String photoUrls) {
        testDoc(epic, feature, true,
                "Update existing pet (with string photoUrls)",
                "Update existing pet with string photoUrls, check that it is updated and validate the data.");
        Pet anotherPet = Pet.anotherPet();
        Object existing = addPet(anotherPet, 200);

        Pet petToSend = Pet.defaultPet();
        petToSend.id = anotherPet.id;
        petToSend.photoUrls = Arrays.asList(photoUrls);
        Object petReceivedFromPost = updatePet(petToSend, 200);
        validateData(petToSend, petReceivedFromPost);
        Object petReceivedFromGet = getPetById(petToSend.id, 200);
        validateData(petToSend, petReceivedFromGet);
    }

    @Test(testName = feature + " - tag id", dataProvider = "invalidNumberIds")
    @Parameters({"tagID"})
    public void TC_PUpd11(Object tagId) {
        testDoc(epic, feature, true,
                "Update existing pet (with invalid number tag id - zero/negative/float/etc.)",
                "Update existing pet with invalid number tag id and check that it is updated, id is rounded to integer.");
        Pet anotherPet = Pet.anotherPet();
        Object existing = addPet(anotherPet, 200);

        Pet petToSend = Pet.defaultPet();
        petToSend.id = anotherPet.id;
        petToSend.tags = Arrays.asList(new Tag(tagId, "white dog"));
        Object petReceivedFromPost = updatePet(petToSend, 200);
        petToSend.tags = Arrays.asList(new Tag((int)(double)(Double.valueOf(tagId.toString())), "white dog"));
        validateData(petToSend, petReceivedFromPost);
        Object petReceivedFromGet = getPetById(petToSend.id, 200);
        validateData(petToSend, petReceivedFromGet);
    }

    @Test(testName = feature + " - tag id", dataProvider = "invalidStrings")
    @Parameters({"tagID"})
    public void TC_PUpd12(Object tagId) {
        testDoc(epic, feature, false,
                "Update existing pet (with invalid string tag id)",
                "Update existing pet with invalid string tag id and check that it does not get updated in the system, corresponding error code is returned.");
        Pet anotherPet = Pet.anotherPet();
        Object existing = addPet(anotherPet, 200);

        Pet petToSend = Pet.defaultPet();
        petToSend.id = anotherPet.id;
        petToSend.tags = Arrays.asList(new Tag(tagId, "white dog"));
        ApiResponse expectedResponse = new ApiResponse(500,"unknown", "something bad happened");
        Object apiResponseFromPost = updatePet(petToSend, 500);
        validateData(expectedResponse, apiResponseFromPost);
    }

    @Test(testName = feature + " - tag id", dataProvider = "invalidNullAndEmpty")
    @Parameters({"tagID"})
    public void TC_PUpd13(Object tagId) {
        testDoc(epic, feature, true,
                "Update existing pet (with invalid number tag id - null/empty)",
                "Update existing pet with invalid number tag id and check that it is updated, id is assigned with 0 and the rest of data matches initial request.");
        Pet anotherPet = Pet.anotherPet();
        Object existing = addPet(anotherPet, 200);

        Pet petToSend = Pet.defaultPet();
        petToSend.id = anotherPet.id;
        petToSend.tags = Arrays.asList(new Tag(tagId, "white dog"));
        Object petReceivedFromPost = updatePet(petToSend, 200);
        petToSend.tags = Arrays.asList(new Tag(0, "white dog"));
        validateData(petToSend, petReceivedFromPost);
        Object petReceivedFromGet = getPetById(petToSend.id, 200);
        validateData(petToSend, petReceivedFromGet);
    }

    @Test(testName = feature + " - tag name", dataProvider = "invalidStringsAndEmpty")
    @Parameters({"tagName"})
    public void TC_PUpd14(String tagName) {
        testDoc(epic, feature, true,
                "Update existing pet (with string tag name)",
                "Update existing pet with string tag name, check that it is updated and validate the data.");
        Pet anotherPet = Pet.anotherPet();
        Object existing = addPet(anotherPet, 200);

        Pet petToSend = Pet.defaultPet();
        petToSend.id = anotherPet.id;
        petToSend.tags = Arrays.asList(new Tag(1984, tagName));
        Object petReceivedFromPost = updatePet(petToSend, 200);
        validateData(petToSend, petReceivedFromPost);
        Object petReceivedFromGet = getPetById(petToSend.id, 200);
        validateData(petToSend, petReceivedFromGet);
    }

    @Test(testName = feature + " - status", dataProvider = "validStatuses")
    @Parameters({"status"})
    public void TC_PUpd15(String status) {
        testDoc(epic, feature, true,
                "Update existing pet (with string status)",
                "Update existing pet with string status, check that it is updated and validate the data.");
        Pet anotherPet = Pet.anotherPet();
        Object existing = addPet(anotherPet, 200);

        Pet petToSend = Pet.defaultPet();
        petToSend.id = anotherPet.id;
        petToSend.status = status;
        Object petReceivedFromPost = updatePet(petToSend, 200);
        validateData(petToSend, petReceivedFromPost);
        Object petReceivedFromGet = getPetById(petToSend.id, 200);
        validateData(petToSend, petReceivedFromGet);
    }

    @Test(testName = feature + " - status", dataProvider = "invalidStringsAndEmpty")
    @Parameters({"status"})
    public void TC_PUpd16(String status) {
        testDoc(epic, feature, true,
                "Update existing pet (with string status)",
                "Update existing pet with string status, check that it is updated and validate the data.");
        Pet anotherPet = Pet.anotherPet();
        Object existing = addPet(anotherPet, 200);

        Pet petToSend = Pet.defaultPet();
        petToSend.id = anotherPet.id;
        petToSend.status = status;
        Object petReceivedFromPost = updatePet(petToSend, 200);
        validateData(petToSend, petReceivedFromPost);
        Object petReceivedFromGet = getPetById(petToSend.id, 200);
        validateData(petToSend, petReceivedFromGet);
    }
}
