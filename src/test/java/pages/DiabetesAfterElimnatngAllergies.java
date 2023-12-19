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

public class DiabetesAfterElimnatngAllergies extends TestBase {
	//private static WebDriver driver;
	List<String> Diabetic_EliminateItem = new ArrayList<String>();
	List<String> Diabetic_AllergiesItem = new ArrayList<String>();
	String ingre_List = "";
	String food_Category;
	String rec_Category;
	String morbid_Condition;

	public DiabetesAfterElimnatngAllergies(WebDriver driver) {
		PageFactory.initElements(driver,this);
	}

	public void readExcel() {
		ExcelReaderCode reader = new ExcelReaderCode(
				"src/test/resources/IngredientsAndComorbidities-ScrapperHackathon.xlsx");
		// Boolean sheetCheck = reader.isSheetExist("Diabetes-Hypothyroidism-Hyperte");
		// System.out.println("The sheet existance status is : " + sheetCheck);

		for (int i = 3; i <= 38; i++) {
			String testData = reader.getCellData("Diabetes-Hypothyroidism-Hyperte", 0, i);
			Diabetic_EliminateItem.add(testData.toLowerCase());
			// System.out.println(testData);
		}
		
		for(int i=3;i<20;i++) {
			String testData=reader.getCellData("Allergies", 0, i);
			Diabetic_AllergiesItem.add(testData.toLowerCase());
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
				"Food Category", "Ingredients", "Preparation Time", "Cooking Time", "Preparation Method","Targetted Morbid Condition", "URL");
		recipesdetailedList.add(headers);

	
		for (int p = 1; p <= 1; p++) {
			String pageNo = Integer.toString(p);
			WebElement pagination = driver.findElement(By.xpath("//div[@id='pagination']//a[" + pageNo + "]"));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", pagination);
			int rec_Size = driver.findElements(By.xpath("//article[@itemprop='itemListElement']//div[3]//span[@class='rcc_recipename']")).size();
			System.out.println(rec_Size);

			for (int i = 1; i <= rec_Size; i++) {
				if ((p==1 && i == 13)||(p==2&& i==4)||(p==3&&i==17)||(p==4&&i==24)||(p==5&&i==2)||(p==7&&i==2)||(p==7&&i==23)||(p==7&&i==22)||(p==7&&i==17||(p==10&&i==9)||
						(p==9&&i==23)||(p==9&&i==24)||(p==10&&i==22)||(p==12&&i==6))) {
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
				try {
					WebElement nutritionVal = driver
							.findElement(By.xpath("//article[" + indexNo + "]/div[2]/div/a/span[1]"));
					String nut_Val = nutritionVal.getText();
					System.out.println(nut_Val);
					recipesDiabetes.add(nut_Val);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					String nut_Val = "Not Found";
					System.out.println(nut_Val);
					recipesDiabetes.add(nut_Val);
					
				}
				js.executeScript("arguments[0].click();", recipeName);
				Thread.sleep(3000);
				
				List<WebElement> recipeTag = driver.findElements(By.id("recipe_tags"));
				for (WebElement recInfo : recipeTag) {
					rec_Category = recInfo.getText().toLowerCase();
					if (rec_Category.contains("breakfast")) {
						rec_Category = "Breakfast";
						break;
					} else if (rec_Category.contains("dinner")) {
						rec_Category = "Dinner";
						break;
					} else if (rec_Category.contains("snacks")) {
						rec_Category = "Snacks";
						break;
					}
					rec_Category = "lunch";
				}
				System.out.println(rec_Category);
				recipesDiabetes.add(rec_Category);
				
				for (WebElement recInfo : recipeTag) {
					food_Category = recInfo.getText();
					if ((food_Category.contains("Veg")) && (!food_Category.contains("Non veg"))) {
						food_Category = "Vegetarian";
						break;
					} else if (food_Category.contains("Non veg")) {
						food_Category = "Non vegetarian";
						break;
					} else if (food_Category.contains("Egg")) {
						food_Category = "Eggitarian";
						break;
					} else if (food_Category.contains("Jain")) {
						food_Category = "Jain";
						break;
					}	
					
					food_Category = "Vegan";
				}

				System.out.println(food_Category);
				recipesDiabetes.add(food_Category);
				
				
//				WebElement recipeCategory = driver.findElement(By.xpath("//div[@id='recipe_tags']//a[2]//span[1]"));
//				String rec_Category = recipeCategory.getText();
//				System.out.println(rec_Category);
//				recipesDiabetes.add(rec_Category);
//				WebElement foodCategory = driver.findElement(By.xpath("//div[@id='recipe_tags']//a[1]//span[1]"));
//				String food_Category = foodCategory.getText();
//				System.out.println(food_Category);
//				recipesDiabetes.add(food_Category);
				
				List<WebElement> ingredients = driver.findElements(By.xpath("//span[@itemprop='recipeIngredient']/a"));
				ingre_List = "";
				for (WebElement selectIngre : ingredients) {
					ingre_List = ingre_List + "," + (selectIngre.getText()).toLowerCase();
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
				
				for (WebElement recInfo : recipeTag) {
					morbid_Condition = recInfo.getText();
					if (morbid_Condition.contains("Hypothyroidism")) {
						morbid_Condition = "Hypothyroidism";
						break;
					} else if (morbid_Condition.contains("PCOS")) {
						morbid_Condition = "PCOS";
						break;
					} else if (morbid_Condition.contains("Hypertension")) {
						morbid_Condition = "Hypertension";
						break;
					}
					morbid_Condition = "Diabetes";
				}

				System.out.println(morbid_Condition);
				recipesDiabetes.add(morbid_Condition);
				
				String URL = driver.getCurrentUrl();
				System.out.println(URL);
				recipesDiabetes.add(URL);
				driver.navigate().back();
				driver.navigate().back();
				Thread.sleep(3000);
				Boolean validRecipe = isValidRecipe(Diabetic_EliminateItem, ingre_List);
				Boolean isToAddPresent= recipeToAdd(Diabetic_AllergiesItem, ingre_List);
				if (validRecipe) {
					if(isToAddPresent) {
					recipesdetailedList.add(recipesDiabetes);
					}
				}

			}
		}
		
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Diabetic Recipes with to Add Items");
		int rowCount = 0;
		for (List<String> recipesList : recipesdetailedList) {
			XSSFRow row = sheet.createRow(rowCount++);
			int columnCount = 0;
			for (String list : recipesList) {
				XSSFCell cell = row.createCell(columnCount++);
				cell.setCellValue(list);
			}
		}
		String filePath = ".\\target\\Filtered_Diabetic_Recipes_excludingAllergies.xlsx";
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
				System.out.println("Eliminate_item found");
				return false;
			}
		}
		System.out.println("Valid Items");
		return true;

	}
	
	
	public boolean recipeToAdd(List<String> Diabetic_AllergiesItem, String ingr_List) {
		// System.out.println(ingr_List);
		String[] ingredients = ingr_List.split(",");
		for (String ingr : ingredients) {
			ingr = ingr.trim();
			if (ingr.length() > 0 && Diabetic_AllergiesItem.contains(ingr)) {
				System.out.println(ingr);
				System.out.println("Allergy item found");
				return false;

			}
		}
		System.out.println("No Allergy item found");
		return true;


	}//end of toAddMethod
}





