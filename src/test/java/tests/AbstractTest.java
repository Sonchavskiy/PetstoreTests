package tests;

import io.qameta.allure.Allure;
import org.testng.annotations.BeforeSuite;

import static api.PetStoreAPI.setURL;
import static io.qameta.allure.util.ResultsUtils.TAG_LABEL_NAME;

public abstract class AbstractTest {
    @BeforeSuite
    public void setBaseURL(){
        setURL();
        System.out.println("URL is set");
    }

    public void testDoc(String epic, String feature, Boolean positive, String story, String description){
        Allure.epic(epic);
        Allure.feature(feature);
        Allure.story(story);
        Allure.description(description);
        Allure.label(TAG_LABEL_NAME, (positive)? "positive" : "negative");
    }
}
