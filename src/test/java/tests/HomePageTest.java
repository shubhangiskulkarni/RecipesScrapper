package tests;

import org.testng.annotations.Test;

import base.TestBase;

import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;


import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.HomePage;
import pages.PcosRecipesAfterEliminatngAllergies;
import pages.PCOSRecAfterElimination;
import utils.LoggerLoad;


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
        AssertJUnit.assertEquals(HomePageTitle, "Indian Recipes | Indian Vegetarian Recipes | Top Indian Veg Dishes");

    }

    @Test (priority = 2)
	
	public void PCOSAfterEliminationReceipes() throws InterruptedException, IOException {
    	PCOSRecAfterElimination pcosrecafterelimination = new PCOSRecAfterElimination(TestBase.getDriver());
    	pcosrecafterelimination.readExcel();
    	pcosrecafterelimination.clickRecipesMenu();
    	pcosrecafterelimination.gettingPCOSRec();
		
	}

    


    @Test (priority = 3)

    public void PCOSAllergyFiltered() throws InterruptedException, IOException {
    	PcosRecipesAfterEliminatngAllergies pcosrecipesafterelimonatingallergies = new PcosRecipesAfterEliminatngAllergies(TestBase.getDriver());
	
    	pcosrecipesafterelimonatingallergies.clickRecipesMenu();
    	pcosrecipesafterelimonatingallergies.readExcel();
    	pcosrecipesafterelimonatingallergies.gettingRecipes();
	
}
}
