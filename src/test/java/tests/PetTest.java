package tests;

import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static api.PetStoreAPI.getPet;
import static io.qameta.allure.util.ResultsUtils.TAG_LABEL_NAME;

public class PetTest extends AbstractTest{

    @DataProvider
    public static Object[][] dataPCr12(){
        return new Object[][] {{1},{2},{3}};
    }

    @Test(testName = "Create pet", dataProvider = "dataPCr12")
    @Parameters({"ID"})
    public void TC_PCr12(Integer id) {
        Allure.description("Add pet (valid non-existing id) and check it doesn't exist.");
        Allure.epic("Pet");
        Allure.feature("Pet create");
        Allure.story("Create pet (valid non-existing id)");
        Allure.label(TAG_LABEL_NAME, "positive");
        Object pet = getPet(id, 200);
        System.out.println(pet);
    }
}
