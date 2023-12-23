package tests;

import io.qameta.allure.Allure;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import static api.PetStoreAPI.setURL;
import static io.qameta.allure.util.ResultsUtils.TAG_LABEL_NAME;

public abstract class AbstractTest {
    @BeforeSuite
    public void setBaseURL(){
        setURL();
        System.out.println("URL is set");
    }

    @DataProvider
    public static Object[][] invalidNumberIds(){
        return new Object[][] {{-1},{0},{1.5}};
    }

    @DataProvider
    public static Object[][] invalidStrings(){
        return new Object[][] {{"abcD"},{"!@#$%^&*("},{"®✉§©☯"}};
    }

    @DataProvider
    public static Object[][] invalidNullAndEmpty(){
        return new Object[][] {{null},{""}};
    }

    @DataProvider
    public static Object[][] validIds(){
        return new Object[][] {{4},{8},{15},{16},{23},{42}};
    }

    @DataProvider
    public static Object[][] validStatuses(){
        return new Object[][] {{"available"},{"pending"},{"sold"}};
    }

    public void testDoc(String epic, String feature, Boolean positive, String story, String description){
        Allure.epic(epic);
        Allure.feature(feature);
        Allure.story(story);
        Allure.description(description);
        Allure.label(TAG_LABEL_NAME, (positive)? "positive" : "negative");
    }
}
