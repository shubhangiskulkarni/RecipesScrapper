package tests;

import base.TestBase;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.LoggerLoad;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.DiabetesAfterElimnatngAllergies;
import pages.DiabetesRecipesToAdd;
import pages.DiabeticRecipes;

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
    public void validateUserLandingHomePageTest() throws Exception {
        LoggerLoad.info("Executing test to validate Home Page title...");
        String HomePageTitle = driver.getTitle();

        LoggerLoad.info("Title of the home page is: " + HomePageTitle);
        Assert.assertEquals(HomePageTitle, "Indian Recipes | Indian Vegetarian Recipes | Top Indian Veg Dishes");
//       
//        driver.findElement(By.xpath("//div[text()='RECIPES']")).click();
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
//        driver.findElement(By.xpath("//td/a[@id='ctl00_cntleftpanel_ttlhealthtree_tvTtlHealtht46']")).click();
//        driver.navigate().to("https://www.tarladalal.com/recipes-for-indian-diabetic-recipes-370");
//        DiabetesRecipes scrapper=new DiabetesRecipes(TestBase.getDriver());  
        //scrapper.clickRecipesMenu();
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        //scrapper.clickDiabeticRecipes();
       // scrapper.getRecipeID();
        //scrapper.getRecipeName();
     // scrapper.goToRecipes();
       //scrapper.writeDiabetesData();
//        
       
        
    }
    
    @Test
    
    public void diabeticRecipesTest() throws InterruptedException, IOException  {
    	DiabeticRecipes diabeticRepObj=new DiabeticRecipes(TestBase.getDriver());
    	diabeticRepObj.readExcel();
    	diabeticRepObj.clickRecipesMenu();
    	diabeticRepObj.getDiabeticRecipes();
    }
    
    @Test
    
    public void getDiabetesRecipesToAdd() throws InterruptedException, IOException  {
    	DiabetesRecipesToAdd diabeticRepToAddObj=new DiabetesRecipesToAdd(TestBase.getDriver());
    	diabeticRepToAddObj.readExcel();
    	diabeticRepToAddObj.clickRecipesMenu();
    	diabeticRepToAddObj.getDiabeticRecipes();
    }
    
    @Test
    public void getDiabetesRecipesAfterAllergyCheck() throws InterruptedException, IOException  {
    	DiabetesAfterElimnatngAllergies diabeticRepAllergyChk=new DiabetesAfterElimnatngAllergies(TestBase.getDriver());
    	diabeticRepAllergyChk.readExcel();
    	diabeticRepAllergyChk.clickRecipesMenu();
    	diabeticRepAllergyChk.getDiabeticRecipes();
    }

}
