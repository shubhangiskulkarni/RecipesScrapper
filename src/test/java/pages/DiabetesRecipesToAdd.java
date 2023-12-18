package pages;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import base.TestBase;

import utils.ExcelReaderCode;

public class DiabetesRecipesToAdd extends TestBase {

	List<String> Diabetic_EliminateItem = new ArrayList<String>();
	List<String> Diabetic_addItem = new ArrayList<String>();
	String ingre_List = "";


	public DiabetesRecipesToAdd(WebDriver driver) {
		PageFactory.initElements(driver,this);
	}

	public void readExcel() {
		ExcelReaderCode reader = new ExcelReaderCode(
				"C:/Users/OVI/git/RecipesScrapper/src/test/resources/ExcelFiles/IngredientsAndComorbidities-ScrapperHackathon.xlsx");
		Boolean sheetCheck = reader.isSheetExist("Diabetes-Hypothyroidism-Hyperte");
		System.out.println("The sheet existance status is : " + sheetCheck);

		for (int i = 3; i <= 38; i++) {
			String testData = reader.getCellData("Diabetes-Hypothyroidism-Hyperte", 0, i);
			Diabetic_EliminateItem.add(testData);
			// System.out.println(testData);
		}
		
		for(int i=3;i<20;i++) {
			String testData=reader.getCellData("Diabetes-Hypothyroidism-Hyperte", 1, i);
			Diabetic_addItem.add(testData.toLowerCase());
			//System.out.println("Diabetic_addItem:  "+testData);
		}
	}

	public void openURL() {
		driver.get("https://www.tarladalal.com/");
	}

	public void clickRecipesMenu() {
		driver.findElement(By.xpath("//div[text()='RECIPES']")).click();
	}

	public void getDiabeticRecipes() throws InterruptedException, IOException {
		driver.findElement(By.id("ctl00_cntleftpanel_ttlhealthtree_tvTtlHealtht46")).click();
		int noOfPages = driver.findElements(By.xpath("//div[@id='pagination']//a")).size();
		List<List<String>> recipesdetailedList = new ArrayList<>();
		List<String> headers = Arrays.asList("Recipe Name", "Recipe ID", "Nutrition Value", "Recipe Category",
				"Food Category", "Ingredients", "Preparation Time", "Cooking Time", "Preparation Method", "URL");
		recipesdetailedList.add(headers);

		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Diabetic diet Recipes");
		int rowCount = 0;


		for (int p = 1; p <= 1; p++) {
			String pageNo = Integer.toString(p);
			WebElement pagination = driver.findElement(By.xpath("//div[@id='pagination']//a[" + pageNo + "]"));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", pagination);
			int rec_Size = driver.findElements(By.xpath("//article[@itemprop='itemListElement']//div[3]//span[@class='rcc_recipename']")).size();
			System.out.println(rec_Size);

			for (int i = 1; i <= rec_Size; i++) {
				if (i == 13) {
					continue;
				}else if(i==29) {
					continue;
				}
					
				List<String> recipesDiabetes = new ArrayList<>();
				String indexNo = Integer.toString(i);
				WebElement recipeName = driver.findElement(By.xpath("//article[" + indexNo + "]//div[3]//span[1]//a"));
				String rec_Name = recipeName.getText();
				System.out.println(rec_Name);
				recipesDiabetes.add(rec_Name);
				WebElement recipeId = driver.findElement(By.xpath("//article[" + indexNo + "]//div[2]/span"));
				String rec_Id = recipeId.getText().substring(8, 12);
				System.out.println(rec_Id);
				recipesDiabetes.add(rec_Id);
				WebElement nutritionVal = driver
						.findElement(By.xpath("//article[" + indexNo + "]/div[2]/div/a/span[1]"));
				String nut_Val = nutritionVal.getText();
				System.out.println(nut_Val);
				recipesDiabetes.add(nut_Val);
				js.executeScript("arguments[0].click();", recipeName);
				Thread.sleep(3000);
				WebElement recipeCategory = driver.findElement(By.xpath("//div[@id='recipe_tags']//a[2]//span[1]"));
				String rec_Category = recipeCategory.getText();
				System.out.println(rec_Category);
				recipesDiabetes.add(rec_Category);
				WebElement foodCategory = driver.findElement(By.xpath("//div[@id='recipe_tags']//a[1]//span[1]"));
				String food_Category = foodCategory.getText();
				System.out.println(food_Category);
				recipesDiabetes.add(food_Category);
				List<WebElement> ingredients = driver.findElements(By.xpath("//span[@itemprop='recipeIngredient']/a"));
				ingre_List = "";
				for (WebElement selectIngre : ingredients) {
					ingre_List = ingre_List + "," + selectIngre.getText();
				}
				// System.out.println(ingre_List);
				recipesDiabetes.add(ingre_List);

				WebElement preparatnMethod = driver.findElement(By.xpath("//div[@id='recipe_small_steps']//span//ol"));
				WebElement preparatnTime = driver.findElement(By.xpath("//p[2]//time[1]"));
				String prep_Time = preparatnTime.getText();
				System.out.println(prep_Time);
				recipesDiabetes.add(prep_Time);
				WebElement cookingTime = driver.findElement(By.xpath("//p[2]//time[2]"));
				String cook_Time = cookingTime.getText();
				System.out.println(cook_Time);
				recipesDiabetes.add(cook_Time);
				String prep_Method = preparatnMethod.getText();
				System.out.println(prep_Method);
				recipesDiabetes.add(prep_Method);
				String URL = driver.getCurrentUrl();
				System.out.println(URL);
				recipesDiabetes.add(URL);
				driver.navigate().back();
				driver.navigate().back();
				Thread.sleep(3000);
				Boolean validRecipe = isValidRecipe(Diabetic_EliminateItem, ingre_List);
				Boolean isToAddPresent= recipeToAdd(Diabetic_addItem, ingre_List);
				if (validRecipe) {
					if(isToAddPresent) {
					recipesdetailedList.add(recipesDiabetes);
					}
				}

			}
		}
		for (List<String> recipesList : recipesdetailedList) {
			XSSFRow row = sheet.createRow(rowCount++);
			int columnCount = 0;
			for (String list : recipesList) {
				XSSFCell cell = row.createCell(columnCount++);
				cell.setCellValue(list);
			}
		}
		String filePath = ".\\target\\Diabetic_Recipes_after_elimination.xlsx";
		FileOutputStream outstream = new FileOutputStream(filePath);
		workbook.write(outstream);
		System.out.println("Excel created");
	}

	public boolean isValidRecipe(List<String> Diabetic_EliminateItem, String ingr_List) {
		// System.out.println(ingr_List);
		String[] ingredients = ingr_List.split(",");
		for (String ingr : ingredients) {
			ingr = ingr.trim();
			if (ingr.length() > 0 && Diabetic_EliminateItem.contains(ingr)) {
				System.out.println(ingr);
				System.out.println("Invalid Item");
				return false;
			}
		}
		System.out.println("Valid Items");
		return true;

	}
	
	
	public boolean recipeToAdd(List<String> Diabetic_addItem, String ingr_List) {
		// System.out.println(ingr_List);
		String[] ingredients = ingr_List.split(",");
		for (String ingr : ingredients) {
			ingr = ingr.trim();
			if (ingr.length() > 0 && Diabetic_addItem.contains(ingr)) {
				System.out.println(ingr);
				System.out.println("valid Item");
				return true;
			}
		}
		System.out.println("InValid Items");
		return false;

	}//end of toAddMethod
}










