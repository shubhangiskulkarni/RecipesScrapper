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

public class HypothyroidismRecAfterElimntn extends TestBase {
	// private static WebDriver driver;
	List<String> hypothyroidism_EliminateItem = new ArrayList<String>();
	String ingre_List = "";
	String fud_Category;
	String rec_Category;
	String morbid_Condition;

	public HypothyroidismRecAfterElimntn(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public void readExcel() {
		ExcelReaderCode reader = new ExcelReaderCode(
				"src/test/resources/Valid&InvalidFudItemsForHypothyroidism.xlsx");

		for (int i = 3; i <= 36; i++) {
			String testData = reader.getCellData("Diabetes-Hypothyroidism-Hyperte", 3, i);
			hypothyroidism_EliminateItem.add(testData);
			// System.out.println(testData);
		}
	}

	public void openURL() {
		driver.get("https://www.tarladalal.com/");
	}

	public void clickRecipesMenu() {
		driver.findElement(By.xpath("//div[text()='RECIPES']")).click();
	}

	public void gettingHypoThyroidRec() throws InterruptedException, IOException {
		driver.findElement(By.id("ctl00_cntleftpanel_ttlhealthtree_tvTtlHealtht207")).click();
		int noOfPages = driver.findElements(By.xpath("//div[@id='pagination']//a")).size();
		List<List<String>> recipesdetailedList = new ArrayList<>();
		List<String> headers = Arrays.asList("Recipe Name", "Recipe ID", "Nutrition Value", "Recipe Category",
				"Food Category", "Ingredients", "Preparation Time", "Cooking Time", "Preparation Method", "URL");
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
			// System.out.println(rec_Size);

			for (int i = 1; i <= rec_Size; i++) {
				List<String> recipesHypothyroid = new ArrayList<>();
				String indexNo = Integer.toString(i);
				WebElement recipeName = driver.findElement(By.xpath("//article[" + indexNo + "]//div[3]//span[1]//a"));
				String rec_Name = recipeName.getText();
				System.out.println(rec_Name);
				recipesHypothyroid.add(rec_Name);
				WebElement recipeId = driver.findElement(By.xpath("//article[" + indexNo + "]//div[2]/span"));
				String rec_Id = recipeId.getText().substring(8, 12);
				System.out.println(rec_Id);
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
				}
				rec_Category = "Lunch";
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
					}
				}
				fud_Category = "Vegan";
				System.out.println("fud_Category");
				recipesHypothyroid.add(fud_Category);
				List<WebElement> ingredients = driver.findElements(By.xpath("//span[@itemprop='recipeIngredient']/a"));
				ingre_List = "";
				for (WebElement selectIngre : ingredients) {
					ingre_List = ingre_List + "," + selectIngre.getText();
				}
				// System.out.println(ingre_List);
				recipesHypothyroid.add(ingre_List);

				WebElement preparatnMethod = driver.findElement(By.xpath("//div[@id='recipe_small_steps']//span//ol"));
				WebElement preparatnTime = driver.findElement(By.xpath("//p[2]//time[1]"));
				String prep_Time = preparatnTime.getText();
				System.out.println(prep_Time);
				recipesHypothyroid.add(prep_Time);
				WebElement cookingTime = driver.findElement(By.xpath("//p[2]//time[2]"));
				String cook_Time = cookingTime.getText();
				System.out.println(cook_Time);
				recipesHypothyroid.add(cook_Time);
				String prep_Method = preparatnMethod.getText();
				System.out.println(prep_Method);
				recipesHypothyroid.add(prep_Method);
				for (WebElement recInfo : recipeTag) {
					morbid_Condition = recInfo.getText();
					if (morbid_Condition.contains("Diabetes")) {
						morbid_Condition = "Diabetes";
						break;
					} else if (morbid_Condition.contains("PCOS")) {
						morbid_Condition = "PCOS";
						break;
					} else if (morbid_Condition.contains("Hypertension")) {
						morbid_Condition = "Hypertension";
						break;
					}
					morbid_Condition = "Hypothyroidism";
				}

				System.out.println(morbid_Condition);
				recipesHypothyroid.add(morbid_Condition);
				String URL = driver.getCurrentUrl();
				System.out.println(URL);
				recipesHypothyroid.add(URL);
				driver.navigate().back();
				driver.navigate().back();
				Thread.sleep(3000);
				Boolean validRecipe = isValidRecipe(hypothyroidism_EliminateItem, ingre_List);
				if (validRecipe) {
					recipesdetailedList.add(recipesHypothyroid);
				}
			}
		}
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Hypothyroid Diet Recipes After Elimination");
		int rowCount = 0;

		for (List<String> recipesList : recipesdetailedList) {
			XSSFRow row = sheet.createRow(rowCount++);
			int columnCount = 0;
			for (String list : recipesList) {
				XSSFCell cell = row.createCell(columnCount++);
				cell.setCellValue(list);
			}

		}
		String filePath = ".\\target\\hypothyroidism1.xlsx";
		FileOutputStream outstream = new FileOutputStream(filePath);
		workbook.write(outstream);
		System.out.println("Excel created");
	}

	public boolean isValidRecipe(List<String> hypothyroidism_EliminateItem, String ingr_List) {
		// System.out.println(ingr_List);
		String[] ingredients = ingr_List.split(",");
		for (String ingr : ingredients) {
			ingr = ingr.trim();
			if (ingr.length() > 0 && hypothyroidism_EliminateItem.contains(ingr)) {
				System.out.println(ingr);
				System.out.println("Invalid Item");
				return false;
			}
		}
		System.out.println("Valid Items");
		return true;

	}
}