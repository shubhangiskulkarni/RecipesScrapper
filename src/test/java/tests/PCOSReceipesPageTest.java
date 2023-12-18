package tests;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.TestBase;
import pages.PCOSReceipesPage;

public class PCOSReceipesPageTest extends TestBase {

	private WebDriver driver;
	PCOSReceipesPage pcosreceipespage;
	
	public PCOSReceipesPageTest() {
		super();
	}
	
	@BeforeClass
	public void setup() {
	      initialization();
	      pcosreceipespage = new PCOSReceipesPage();
	}
	
	@Test (priority = 2)
	
	public void PCOSReceipe() throws InterruptedException, IOException {
		
		pcosreceipespage.openURL();
		pcosreceipespage.clickRecipesMenu();
		pcosreceipespage.readExcel();
		
		pcosreceipespage.gettingPCOSRec();
		
	}
	
	
	
	
	
}
