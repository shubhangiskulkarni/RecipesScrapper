package tests;

import base.TestBase;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import utils.LoggerLoad;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.HypothyroidismAddOnRec;
import pages.HypothyroidismAfterElimnatngAllergies;
import pages.HypothyroidismRecAfterElimntn;

public class HomePageTest extends TestBase {

	HomePage homePage;
	//private WebDriver webDriver;

	public HomePageTest() {
		super();

	}

	@BeforeClass
	public void setup() {

		initialization();
		homePage = new HomePage();
	}

	@Test
	public void validateUserLandingHomePageTest() {
		LoggerLoad.info("Executing test to validate Home Page title...");
		String HomePageTitle = driver.getTitle();

		LoggerLoad.info("Title of the home page is: " + HomePageTitle);
		Assert.assertEquals(HomePageTitle, "Indian Recipes | Indian Vegetarian Recipes | Top Indian Veg Dishes");
		driver.findElement(By.xpath("//div[text()='RECIPES']")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.findElement(By.xpath("//td/a[@id='ctl00_cntleftpanel_ttlhealthtree_tvTtlHealtht46']")).click();

	}

	@Test

	public void hypothyroidismAftrElimnatnRecipes() throws InterruptedException, IOException {
		HypothyroidismRecAfterElimntn scrappingHypoThyroidRec = new HypothyroidismRecAfterElimntn(TestBase.getDriver());
		scrappingHypoThyroidRec.readExcel();
		scrappingHypoThyroidRec.clickRecipesMenu();
		scrappingHypoThyroidRec.gettingHypoThyroidRec();
	}

	@Test
	public void hypothyroidAddOnRec() throws InterruptedException, IOException {
		HypothyroidismAddOnRec scrappingHypoThyroidAddOnRec = new HypothyroidismAddOnRec(TestBase.getDriver());
		scrappingHypoThyroidAddOnRec.readExcel();
		scrappingHypoThyroidAddOnRec.clickRecipesMenu();
		scrappingHypoThyroidAddOnRec.gettingRecipes();
	}

	@Test
	public void hypothyroidAftrElimnateAllrgies() throws InterruptedException, IOException {
		HypothyroidismAfterElimnatngAllergies HypothyroidNoAllergis = new HypothyroidismAfterElimnatngAllergies(
				TestBase.getDriver());
		HypothyroidNoAllergis.readExcel();
		HypothyroidNoAllergis.clickRecipesMenu();
		HypothyroidNoAllergis.gettingRecipes();
	}

}
