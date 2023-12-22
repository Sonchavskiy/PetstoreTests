package tests;

import org.testng.annotations.BeforeSuite;

import static api.PetStoreAPI.setURL;

public abstract class AbstractTest {

    @BeforeSuite
    public void setBaseURL(){
        setURL();
        System.out.println("URL is set");
    }
}
