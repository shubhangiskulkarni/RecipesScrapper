package tests;

import base.TestBase;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HighBloodPressure;
import utils.ExcelUtils;
import utils.LoggerLoad;
import utils.excelTemp;

public class HighBloodPressureTest extends TestBase {


    HighBloodPressure highBloodPressure;

    public HighBloodPressureTest() {
        super();

    }

    @BeforeClass
    public void setup() {

        // driver loaded and web page is called...
        initialization();
        highBloodPressure = new HighBloodPressure();
    }

    @AfterClass
    public void tearDown(){
//        driver.quit();
    }

    @Test(priority = 1)
    public void validateUserLandingHomePageTest() throws InterruptedException {

        LoggerLoad.info("Executing test to validate Home Page title...");
        String HomePageTitle = driver.getTitle();

        LoggerLoad.info("Title of the home page is: " + HomePageTitle);
//        Assert.assertEquals(HomePageTitle, "Indian Recipes | Indian Vegetarian Recipes | Top Indian Veg Dishes");
        Assert.assertEquals(HomePageTitle, "High Blood Pressure Recipes, Low Salt Recipes, Veg Low Sodium");


        Assert.assertTrue(highBloodPressure.mouseoverRecipes());
        highBloodPressure.setHighBloodPressure();

        highBloodPressure.scrapHBPRecipes();



    }

    @Test
    public void excel() {

        String[] data = {"Data1", "Data2", "Data3"};
        //Create Excel file
        ExcelUtils excelUtils = new ExcelUtils();
//        excelUtils.createFile("HighBloodPressureRecipes", prop.getProperty("OutputLocation"));

        excelUtils.WriteToExcelFile("HighBloodPressureRecipes", prop.getProperty("OutputLocation"), data, 1);



    }


}
