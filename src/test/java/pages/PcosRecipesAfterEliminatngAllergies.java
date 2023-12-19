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

public class PcosRecipesAfterEliminatngAllergies extends TestBase {

	private static WebDriver driver;
	List<String> PCOS_EliminateItem = new ArrayList<String>();
	List<String> PCOS_AllergiesItem = new ArrayList<String>();
	String ingre_List = "";
	String rec_Category;
	String fud_Category;
	String morbid_Condition;

	public PcosRecipesAfterEliminatngAllergies(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public void readExcel() {
		ExcelReaderCode reader = new ExcelReaderCode(
				"./src/test/resources/Valid&InvalidFudItemsForPCOS.xlsx");
		// Boolean sheetCheck = reader.isSheetExist("Diabetes-Hypothyroidism-Hyperte");
		// System.out.println("The sheet existance status is : " + sheetCheck);

		for (int i = 3; i <= 29; i++) {
			String elimntdItemsData = reader.getCellData("Diabetes-Hypothyroidism-Hyperte", 0, i);
			PCOS_EliminateItem.add(elimntdItemsData);
			System.out.println(elimntdItemsData);
		}

		for (int i = 3; i <= 15; i++) {
			String elimntdAllergiesData = reader.getCellData("Diabetes-Hypothyroidism-Hyperte", 2, i);
			PCOS_AllergiesItem.add(elimntdAllergiesData);
			System.out.println(elimntdAllergiesData);
		}
	}

	public void openURL() {
		driver.get("https://www.tarladalal.com/");
	}

	public void clickRecipesMenu() {
		driver.findElement(By.xpath("//div[text()='RECIPES']")).click();
	}

	public void gettingRecipes() throws InterruptedException, IOException {
		driver.findElement(By.xpath("//a[@id='ctl00_cntleftpanel_cattreecourse_tvCourset1']")).click();
		int noOfPages = driver.findElements(By.xpath("//div[@id='pagination']//a")).size();
		List<List<String>> recipeingredientsList = new ArrayList<>();
		List<String> headers = Arrays.asList("Recipe Name", "Recipe ID", "Nutrition Value", "Recipe Category",
				"Food Category", "Ingredients", "Preparation Time", "Cooking Time", "Preparation Method",
				"Targetted Morbid Condition", "URL");
		recipeingredientsList.add(headers);
		for (int p = 1; p <= noOfPages; p++) {
			String pageNo = Integer.toString(p);
			WebElement pagination = driver.findElement(By.xpath("//div[@id='pagination']//a[" + pageNo + "]"));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", pagination);
			int rec_Size = driver
					.findElements(
							By.xpath("//article[@itemprop='itemListElement']//div[3]//span[@class='rcc_recipename']"))
					.size();

			for (int i = 1; i <= rec_Size; i++) {
				List<String> recipesHypothyroid = new ArrayList<>();
				String indexNo = Integer.toString(i);
				try {
					WebElement recipeName = driver
							.findElement(By.xpath("//article[" + indexNo + "]//div[3]//span[1]//a"));
					String rec_Name = recipeName.getText();
					recipesHypothyroid.add(rec_Name);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					continue;

				}
				WebElement recipeId = driver.findElement(By.xpath("//article[" + indexNo + "]//div[2]/span"));
				String rec_Id = recipeId.getText().substring(8, 13);
				recipesHypothyroid.add(rec_Id);
				try {
					WebElement nutritionVal = driver
							.findElement(By.xpath("//article[" + indexNo + "]/div[2]/div/a/span[1]"));
					String nut_Val = nutritionVal.getText();
					System.out.println(nut_Val);
					recipesHypothyroid.add(nut_Val);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					String nut_Val = "Not Found";
					System.out.println(nut_Val);
					recipesHypothyroid.add(nut_Val);

				}

				js.executeScript("arguments[0].click();",
						driver.findElement(By.xpath("//article[" + indexNo + "]//div[3]//span[1]//a")));
				Thread.sleep(2000);
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
				recipesHypothyroid.add(rec_Category);
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
				recipesHypothyroid.add(fud_Category);
				List<WebElement> ingredients = driver.findElements(By.xpath("//span[@itemprop='recipeIngredient']/a"));
				ingre_List = "";
				for (WebElement selectIngre : ingredients) {
					ingre_List = ingre_List + "," + selectIngre.getText();
				}
				recipesHypothyroid.add(ingre_List);
				String prep_Time = driver.findElement(By.xpath("//p[2]//time[1]")).getText();
				recipesHypothyroid.add(prep_Time);
				String cook_Time = driver.findElement(By.xpath("//p[2]//time[2]")).getText();
				recipesHypothyroid.add(cook_Time);
				String prep_Method = driver.findElement(By.xpath("//div[@id='recipe_small_steps']//span//ol"))
						.getText();
				recipesHypothyroid.add(prep_Method);
				for (WebElement recInfo : recipeTag) {
					morbid_Condition = recInfo.getText();
					if (morbid_Condition.contains("Diabetes")) {
						morbid_Condition = "Diabetes";
						break;
					} else if (morbid_Condition.contains("PCOS")) {
						morbid_Condition = "Hypothyroidism";
						break;
					} else if (morbid_Condition.contains("Hypertension")) {
						morbid_Condition = "Hypertension";
						break;
					}
					morbid_Condition = "PCOS";
				}
				recipesHypothyroid.add(morbid_Condition);
				String URL = driver.getCurrentUrl();
				recipesHypothyroid.add(URL);
				driver.navigate().back();
				driver.navigate().back();
				Thread.sleep(5000);
				Boolean validRecipe = isValidRecipe(PCOS_EliminateItem, ingre_List);
				Boolean isElimntdAllrgyRecipe = isElimntdAllrgyRecipe(PCOS_AllergiesItem, ingre_List);
				if (validRecipe) {
					if (isElimntdAllrgyRecipe) {
						recipeingredientsList.add(recipesHypothyroid);
					}
				}
			}
		}
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("PCOSRec_EliminatngAllergyItems-");
		int rowCount = 0;

		for (List<String> ingredientList : recipeingredientsList) {

			XSSFRow row = sheet.createRow(rowCount++);
			int columnCount = 0;
			for (String ingredient : ingredientList) {

				XSSFCell cell = row.createCell(columnCount++);

				cell.setCellValue(ingredient);

			}

		}
		String filePath = "./target/hypothyroidismRecWithNoAllergyItems.xlsx";
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
		// System.out.println("Valid Items");
		return true;

	}

	public boolean isElimntdAllrgyRecipe(List<String> PCOS_AllergiesItem, String ingr_List) {
		// System.out.println(ingr_List);
		String[] ingredients = ingr_List.split(",");
		for (String ingr : ingredients) {
			ingr = ingr.trim();
			if (ingr.length() > 0 && PCOS_AllergiesItem.contains(ingr)) {
				System.out.println(ingr);
				System.out.println("Invalid Item");
				return false;
			}
		}
		// System.out.println("valid Items");
		return true;

	}

}
