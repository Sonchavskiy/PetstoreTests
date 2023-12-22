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
import static api.PetStoreAPI.getPet;
import static io.qameta.allure.util.ResultsUtils.TAG_LABEL_NAME;

public class CreatePetTest extends AbstractTest{
    final String epic = "Pet";
    final String feature = "Create pet";

    @DataProvider
    public static Object[][] dataPCr12(){
        return new Object[][] {{1}};
    }

    @Test(testName = feature, dataProvider = "dataPCr12")
    @Parameters({"ID"})
    public void TC_PCr12(Integer id) {
        testDoc(epic, feature, true,
                "Create pet (valid non-existing id)",
                "Add pet (valid non-existing id) and check it doesn't exist.");
        Pet petToSend = new Pet(id,
                new Category(1, "dog"),
                "puppy",
                Arrays.asList("some/url", "someoher/url"),
                Arrays.asList( new Tag(1, "white dog"),new Tag(2, "labrador")),
                "available");
        Object petReceivedFromPost = addPet(petToSend, 200);

        Object petReceivedFromGet = getPet(id, 200);
    }


}
