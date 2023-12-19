package tests;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.TestBase;
import pages.*;

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


	@Test(priority=1)
	public void hypothyroidismAftrElimnatnRecipes() throws InterruptedException, IOException {
		HypothyroidismRecAfterElimntn scrappingHypoThyroidRec = new HypothyroidismRecAfterElimntn(TestBase.getDriver());
		scrappingHypoThyroidRec.readExcel();
		scrappingHypoThyroidRec.clickRecipesMenu();
		scrappingHypoThyroidRec.gettingHypoThyroidRec();
	}

	@Test(priority=2)
	public void hypothyroidAddOnRec() throws InterruptedException, IOException {
		HypothyroidismAddOnRec scrappingHypoThyroidAddOnRec = new HypothyroidismAddOnRec(TestBase.getDriver());
		scrappingHypoThyroidAddOnRec.readExcel();
		scrappingHypoThyroidAddOnRec.clickRecipesMenu();
		scrappingHypoThyroidAddOnRec.gettingRecipes();
	}

	@Test(priority=3)
	public void hypothyroidAftrElimnateAllrgies() throws InterruptedException, IOException {
		HypothyroidismAfterElimnatngAllergies HypothyroidNoAllergis = new HypothyroidismAfterElimnatngAllergies(
				TestBase.getDriver());
		HypothyroidNoAllergis.readExcel();
		HypothyroidNoAllergis.clickRecipesMenu();
		HypothyroidNoAllergis.gettingRecipes();
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
