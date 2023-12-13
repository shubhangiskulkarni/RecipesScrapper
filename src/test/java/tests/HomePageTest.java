package tests;

import base.TestBase;
import org.openqa.selenium.WebDriver;
import utils.LoggerLoad;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;

public class HomePageTest extends TestBase {


    HomePage homePage;
    private WebDriver webDriver;

    public HomePageTest() {
        super();

    }

    @BeforeClass
    public void setup() {

        initialization();
        homePage = new HomePage();
    }

    @Test(priority = 1)
    public void validateUserLandingHomePageTest() {
        LoggerLoad.info("Executing test to validate Home Page title...");
        String HomePageTitle = driver.getTitle();

        LoggerLoad.info("Title of the home page is: " + HomePageTitle);
        Assert.assertEquals(HomePageTitle, "NumpyNinja");

    }




}
