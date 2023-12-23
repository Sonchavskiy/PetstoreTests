package tests;

import dataModels.Category;
import dataModels.Pet;
import dataModels.Tag;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static api.PetStoreAPI.addPet;
import static api.PetStoreAPI.getPetById;
import static api.RArequest.validateData;
import static io.qameta.allure.util.ResultsUtils.TAG_LABEL_NAME;

public class CreatePetTest extends AbstractTest{
    final String epic = "Pet";
    final String feature = "Create pet";

    @DataProvider
    public static Object[][] dataPCr12(){
        return new Object[][] {{"123"},{"0"},{"abc"}};
    }

    @Test(testName = feature, dataProvider = "dataPCr12")
    @Parameters({"ID"})
    public void TC_PCr12(Object id) {
        testDoc(epic, feature, true,
                "Create pet (valid non-existing id)",
                "Add pet (with valid non-existing id) and check that it is created and matches initial request.");
        Pet petToSend = Pet.defaultPet();
        petToSend.id = id;
        Object petReceivedFromPost = addPet(petToSend, 200);
        validateData(petToSend, petReceivedFromPost);
        Object petReceivedFromGet = getPetById(id, 200);
        validateData(petToSend, petReceivedFromGet);
    }


}
