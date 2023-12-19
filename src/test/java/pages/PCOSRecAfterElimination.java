package pages;

import base.TestBase;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.ExcelReaderCode;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PCOSRecAfterElimination extends TestBase{
	
	//private static WebDriver driver;
	List<String> PCOS_EliminateItem = new ArrayList<String>();
	String ingre_List = "";
	String fud_Category;
	String rec_Category;
	String morbid_Condition;

	
	public PCOSRecAfterElimination(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public void readExcel() {
		ExcelReaderCode reader = new ExcelReaderCode(
				"./src/test/resources/Valid&InvalidFudItemsForPCOS.xlsx");
		Boolean sheetCheck = reader.isSheetExist("Diabetes-Hypothyroidism-Hyperte");
		// System.out.println("The sheet existance status is : " + sheetCheck);

		for (int i = 3; i <= 29; i++) {
			String testData = reader.getCellData("Diabetes-Hypothyroidism-Hyperte", 0, i);
			PCOS_EliminateItem.add(testData);
			//System.out.println("Input Test Data: "+testData);
		}
	}

	public void openURL() {
		driver.get("https://www.tarladalal.com/");
	}

	public void clickRecipesMenu() {
		driver.findElement(By.xpath("//div[text()='RECIPES']")).click();
	}

	public void gettingPCOSRec() throws InterruptedException, IOException {
		driver.findElement(By.id("ctl00_cntleftpanel_ttlhealthtree_tvTtlHealtht335")).click();
		int noOfPages = driver.findElements(By.xpath("//div[@id='pagination']//a")).size();
		List<List<String>> recipesdetailedList = new ArrayList<>();
		List<String> headers = Arrays.asList("Recipe Name", "Recipe ID", "Nutrition Value", "Recipe Category",
				"Food Category", "Ingredients", "Preparation Time", "Cooking Time", "Preparation Method",
				"Targetted Morbid Condition", "URL");
		recipesdetailedList.add(headers);
		for (int p = 1; p <= noOfPages; p++) {
			String pageNo = Integer.toString(p);
			WebElement pagination = driver.findElement(By.xpath("//div[@id='pagination']//a[" + pageNo + "]"));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", pagination);
			int rec_Size = driver
					.findElements(
							By.xpath("//article[@itemprop='itemListElement']//div[3]//span[@class='rcc_recipename']"))
					.size();
			System.out.println(rec_Size);

			for (int i = 1; i <= rec_Size; i++) {
				if (p==5 && i == 6) 
				{
					continue;
				} 
				List<String> recipesPCOS = new ArrayList<>();
				String indexNo = Integer.toString(i);
				WebElement recipeName = driver.findElement(By.xpath("//article[" + indexNo + "]//div[3]//span[1]//a"));
				String rec_Name = recipeName.getText();
				System.out.println(rec_Name);
				recipesPCOS.add(rec_Name);
				WebElement recipeId = driver.findElement(By.xpath("//article[" + indexNo + "]//div[2]/span"));
				String rec_Id = recipeId.getText().substring(8, 12);
				System.out.println(rec_Id);
				recipesPCOS.add(rec_Id);
				
				WebElement nutritionVal = driver
						.findElement(By.xpath("//article[" + indexNo + "]/div[2]/div/a/span[1]"));
				String nut_Val = nutritionVal.getText();
			
				System.out.println(nut_Val);
				recipesPCOS.add(nut_Val);
				
				
				js.executeScript("arguments[0].click();", recipeName);
				Thread.sleep(3000);
				List<WebElement> recipeTag = driver.findElements(By.id("recipe_tags"));
				for (WebElement recInfo : recipeTag) {
					rec_Category = recInfo.getText();
					if (rec_Category.contains("Breakfast")) {
						rec_Category = "Breakfast";
						break;
					} else if (rec_Category.contains("Dinner")) {
						rec_Category = "Dinner";
						break;
					} else if (rec_Category.contains("Snacks")) {
						rec_Category = "Snacks";
						break;
					} 
					    rec_Category = "Lunch";
					   
				}

				System.out.println(rec_Category);
				recipesPCOS.add(rec_Category);
				for (WebElement recInfo : recipeTag) {
					fud_Category = recInfo.getText();
					if ((fud_Category.contains("Veg")) && (!fud_Category.contains("Non veg"))) {
						fud_Category = "Vegetarian";
						break;
					} else if (fud_Category.contains("Non veg")) {
						fud_Category = "Non vegetarian";
						break;
					} else if (fud_Category.contains("Egg")) {
						fud_Category = "Eggitarian";
						break;
					} else if (fud_Category.contains("Jain")) {
						fud_Category = "Jain";
						break;
					}	
					
					fud_Category = "Vegan";
				}

				System.out.println(fud_Category);
				recipesPCOS.add(fud_Category);
				List<WebElement> ingredients = driver.findElements(By.xpath("//span[@itemprop='recipeIngredient']/a"));
				ingre_List = "";
				for (WebElement selectIngre : ingredients) {
					ingre_List = ingre_List + "," + selectIngre.getText();
				}
				// System.out.println(ingre_List);
				recipesPCOS.add(ingre_List);

				WebElement preparatnMethod = driver.findElement(By.xpath("//div[@id='recipe_small_steps']//span//ol"));
				WebElement preparatnTime = driver.findElement(By.xpath("//p[2]//time[1]"));
				String prep_Time = preparatnTime.getText();
				System.out.println(prep_Time);
				recipesPCOS.add(prep_Time);
				WebElement cookingTime = driver.findElement(By.xpath("//p[2]//time[2]"));
				String cook_Time = cookingTime.getText();
				System.out.println(cook_Time);
				recipesPCOS.add(cook_Time);
				String prep_Method = preparatnMethod.getText();
				System.out.println(prep_Method);
				recipesPCOS.add(prep_Method);
				for (WebElement recInfo : recipeTag) {
					morbid_Condition = recInfo.getText();
					if (morbid_Condition.contains("Diabetes")) {
						morbid_Condition = "Diabetes";
						break;
					} else if (morbid_Condition.contains("Hypothyroidism")) {
						morbid_Condition = "Hypothyroidism";
						break;
					} else if (morbid_Condition.contains("Hypertension")) {
						morbid_Condition = "Hypertension";
						break;
					}
					morbid_Condition = "PCOS";
					
				}

				System.out.println(morbid_Condition);
				recipesPCOS.add(morbid_Condition);
				String URL = driver.getCurrentUrl();
				System.out.println(URL);
				recipesPCOS.add(URL);
				driver.navigate().back();
				driver.navigate().back();
				Thread.sleep(3000);
				Boolean validRecipe = isValidRecipe(PCOS_EliminateItem, ingre_List);
				if (validRecipe) {
					recipesdetailedList.add(recipesPCOS);
				}
			}
		}
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("PCOS_Receipes_after_elimination");
		int rowCount = 0;

		for (List<String> recipesList : recipesdetailedList) {
			XSSFRow row = sheet.createRow(rowCount++);
			int columnCount = 0;
			for (String list : recipesList) {
				XSSFCell cell = row.createCell(columnCount++);
				cell.setCellValue(list);
			}

		}
		String filePath = "./Target/PCOS_Receipes_after_elimination.xlsx";
		FileOutputStream outstream = new FileOutputStream(filePath);
		workbook.write(outstream);
		System.out.println("Excel created");
	}

	public boolean isValidRecipe(List<String> PCOS_EliminateItem, String ingr_List) {
		// System.out.println(ingr_List);
		String[] ingredients = ingr_List.split(",");
		for (String ingr : ingredients) {
			ingr = ingr.trim();
			if (ingr.length() > 0 && PCOS_EliminateItem.contains(ingr)) {
				System.out.println(ingr);
				System.out.println("Invalid Item");
				return false;
			}
		}
		System.out.println("Valid Items");
		return true;

	}
}


