package tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import base.TestBase;
import utils.ExcelReaderCode;
//import Collections.ObjectModel;
public class DiabetesRecipesOld extends TestBase {
	//WebElement recipesMenu = driver.findElement(By.xpath("//div[text()='RECIPES']"));
	//List<WebElement> recipeID= driver.findElements(By.xpath("//article[@itemprop='itemListElement']/div[2]/span"));
	List<WebElement> recipeURL= driver.findElements(By.xpath("//article[@itemprop='itemListElement']/div[3]/span[@class='rcc_recipename']/a"));
	//	List<WebElement> recipeName= driver.findElements(By.xpath("//article[@itemprop='itemListElement']/div[3]/span[@class='rcc_recipename']/a"));
	List<String> storedRecipeName=new ArrayList<>();
	//WebElement diabeticRecipes= driver.findElement(By.linkText("Diabetic recipes "));
	//	List<WebElement> ingredients=driver.findElements(By.xpath("//span[@itemprop='recipeIngredient']/a"));
	//List<String> storedIngredientsList=new ArrayList<>();

	//	List<WebElement> element= (List<WebElement>) driver.findElement(By.xpath("//article[@itemprop='itemListElement']"));
	//   Thread.sleep(3000);
	//   System.out.println(element.)
	//   List<WebElement> allOptions= ul.findElements(By.tagName("li"));
	//   for(WebElement selectLi: element)
	//   {
	//       if(selectLi.getText().equals(data)) {
	//           selectLi.click();
	//       }
	//   }

	public DiabetesRecipesOld(WebDriver driver) {
		PageFactory.initElements(driver,this);

	}

	public void clickRecipesMenu() {
		driver.findElement(By.xpath("//div[text()='RECIPES']")).click();
	}

	public void clickDiabeticRecipes() {
		driver.findElement(By.linkText("Diabetic recipes ")).click();
	}



	public void getRecipeID() {
		List<WebElement> recipeID= driver.findElements(By.xpath("//article[@itemprop='itemListElement']/div[7]/span[1]"));
		System.out.println(recipeID.size());
		for(WebElement selectLi: recipeID)
		{
			String recipe_ID=selectLi.getText();
			System.out.println(recipe_ID);

		}
	}

	public void getRecipeName() {
		List<WebElement> recipeName= driver.findElements(By.xpath("//article[@itemprop='itemListElement']/div[3]/span[@class='rcc_recipename']/a"));
		System.out.println(recipeName.size());
		for(WebElement selectLi: recipeName)
		{
			String recipe_Name=selectLi.getText();
			storedRecipeName.add(recipe_Name);
			System.out.println(recipe_Name);
		}
	}

	public void goToRecipes() throws Exception {
		//		List<WebElement> recipeName= driver.findElements(By.xpath("//article[@itemprop='itemListElement']/div[3]/span[@class='rcc_recipename']/a"));
		//	for(WebElement selectLi: recipeName)
		//		{
		//			String recipe_Name=selectLi.getText();
		//			System.out.println(recipe_Name);
		//			selectLi.click();


		driver.findElement(By.xpath("//article[@itemprop='itemListElement']/div[3]/span[@class='rcc_recipename']/a[text()='Quinoa Dosa']")).click();

		//Writing Headings in excel file	
		Workbook workbook = new XSSFWorkbook();

		FileOutputStream fos = new FileOutputStream("C:/Users/OVI/git/RecipesScrapper/src/test/resources/diabetes_recipes.xls");

		Sheet sheet = workbook.createSheet();
		String[] heading={"Recipe ID","Recipe Name","Recipe Category","Food Category","Ingredients","Preparation Time","Cooking Time","Preparation method","Nutrient values","Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism)","Recipe URL"};
		int rowNum = 0;
		Row rowh=sheet.createRow(rowNum++);
		int cellNumh = 0;
		for (String cellData :heading ) {
			Cell cell = rowh.createCell(cellNumh++);
			cell.setCellValue(cellData);
		}

		

		//---------------------------------------------------------------------------------------------------------------------------------------------	 
		//Reading Excel File
		ExcelReaderCode reader=new ExcelReaderCode("C:/Users/OVI/git/RecipesScrapper/src/test/resources/ExcelFiles/IngredientsAndComorbidities-ScrapperHackathon.xlsx");
		Boolean sheetCheck= reader.isSheetExist("Diabetes-Hypothyroidism-Hyperte");
		System.out.println("The sheet existance status is : "+sheetCheck);

		//Recipe details
		String currentPageUrl=(driver.getCurrentUrl());
		String recipe_ID=StringUtils.right(currentPageUrl, 6); 
		System.out.println("recipe_ID : "+recipe_ID);

		String recipe_name_lable=driver.findElement(By.xpath("//span[@id='ctl00_cntrightpanel_lblRecipeName']")).getText();
		System.out.println("recipe_name_lable : "+recipe_name_lable);

		String calories=driver.findElement(By.xpath("//span[@itemprop='calories']")).getText();
		System.out.println("calories : "+calories);

		String preparation_time=driver.findElement(By.xpath("//time[@itemprop='prepTime']")).getText();
		System.out.println("preparation_time : "+preparation_time);

		String cooking_time=driver.findElement(By.xpath("//time[@itemprop='cookTime']")).getText();
		System.out.println("cooking_time : "+cooking_time);

		List<WebElement> elements=driver.findElements(By.xpath("//div[@id='recipe_tags']/a/span[@itemprop='name']"));
		java.util.Iterator<WebElement> recipe_category_element = elements.iterator();
		String recipe_category=null;
		while (recipe_category_element.hasNext()) {
			String values = (recipe_category_element.next().getText()).toLowerCase();

			if(values.contains("breakfast"))
			{
				recipe_category="breakfast";
			}
			else if(values.contains("lunch"))
			{
				recipe_category="lunch";
			}
			else if(values.contains("snack")) {
				recipe_category="snack";

			}
			else if( values.contains("dinner")) {
				recipe_category="dinner";

			}

		}
		System.out.println("Recipe categary : "+recipe_category);



		List<WebElement> prepMethodElements=driver.findElements(By.xpath("//div[@id='recipe_small_steps']/span/ol/li/span[@itemprop='text']"));
		List<String> preperation_method_list=new ArrayList<String>();
		for(WebElement selectLi: prepMethodElements)
		{
			String preperation_method=selectLi.getText();
			preperation_method_list.add(preperation_method);
		}
		System.out.println(preperation_method_list);

		List<String> Diabetic_EliminateItem=new ArrayList<String>();
		List<String> Diabetic_addItem=new ArrayList<String>();
		for(int i=3;i<76;i++) {
			String testData=reader.getCellData("Diabetes-Hypothyroidism-Hyperte", 0, i);
			Diabetic_EliminateItem.add(testData.toLowerCase());
			//System.out.println("Diabetic_EliminateItem:  "+testData);
		}

		for(int i=3;i<20;i++) {
			String testData=reader.getCellData("Diabetes-Hypothyroidism-Hyperte", 1, i);
			Diabetic_addItem.add(testData.toLowerCase());
			//System.out.println("Diabetic_addItem:  "+testData);
		}
		List<WebElement> ingredients=driver.findElements(By.xpath("//span[@itemprop='recipeIngredient']/a"));
		List<String> storedIngredientsList=new ArrayList<>();
		for(WebElement selectIngre: ingredients)
		{
			String recipeIngredients=((selectIngre.getText()).toLowerCase());
			storedIngredientsList.add(recipeIngredients);
			//System.out.println("recipeIngredients:  "+recipeIngredients);
		}

		String status_Eliminate=null;
		String status_Add=null;
		for(String selectIngre: storedIngredientsList)
		{


			String recipeIngredients=selectIngre.toLowerCase();

			//System.out.println("RecipeIngredients: "+recipeIngredients);
			//Iterator iterator = Diabetic_addItem.iterator();

				Task:
				for(String value:Diabetic_EliminateItem ) {
					int check=recipeIngredients.indexOf(value);
					if(check!=-1) {
					//if(value.contentEquals(recipeIngredients)) {
						status_Eliminate="found";
						System.out.println("Don't add recipe in list: ");
						break Task;

					}
					else
						status_Eliminate="Not found";
					
				}//end of for2

			//Task:
			/*for(String value1:Diabetic_addItem ) {
				int check1=recipeIngredients.indexOf(value1);
				//System.out.println("Ingredient in recipe: "+recipeIngredients);


				if(check1!=-1) {
					//if(value.contentEquals(recipeIngredients)) {
					status_Add="found";
					//break ;

				}
			}*/
		}//end of for3


		if(status_Eliminate.equals("found") )
		{
			System.out.println("Don't add recipe in list: ");
		}
		else if(status_Eliminate.equals("Not found")) 
		{
			System.out.println("Add recipe in list: ");

			
			//			}//end of for1
			///ROW WILL SART FROM 1
			Row rowh1=sheet.createRow(rowNum++);
			int cellNumh1 = 0;
			for (String cellData1 :heading ) {
				Cell cell = rowh.createCell(cellNumh1++);
				//String[] heading={"Reciepe ID","Reciepe Name","Reciepe Category","Food Category","Ingredients","Preparation Time","Cooking Time","Preparation method","Nutrient values","Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism)","Recipe URL"};

				//
				if(cellData1=="Recipe ID")
					cell.setCellValue(recipe_ID);
				else if(cellData1=="Recipe Name")
					cell.setCellValue(recipe_name_lable);
				else if(cellData1=="Recipe Category")
					cell.setCellValue(recipe_category);
				else if(cellData1=="Food Category")
					cell.setCellValue(recipe_category);		
				else if(cellData1=="Ingredients")
					cell.setCellValue("");		
				else if(cellData1=="Preparation Time")
					cell.setCellValue("");
				else if(cellData1=="Cooking Time")
					cell.setCellValue("");
				else if(cellData1=="Preparation method")
					cell.setCellValue("");
				else if(cellData1=="Nutrient values")
					cell.setCellValue("");
				else if(cellData1=="Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism)")
					cell.setCellValue("");
				else if(cellData1=="Recipe URL")
					cell.setCellValue("");


			}
			driver.navigate().to("https://www.tarladalal.com/recipes-for-indian-diabetic-recipes-370");
		}

	}


	public void writeDiabetesData() throws Exception {


		Workbook workbook = new XSSFWorkbook();
		Sheet sheet=workbook.createSheet("Recipe-data");

		Row row = sheet.createRow(1);
		Cell cell=row.createCell(1);
		cell.setCellValue("Recipe ID");
		row.createCell(2).setCellValue("Recipe Name");

		FileOutputStream file = new FileOutputStream("C:/Users/OVI/git/RecipesScrapper/src/test/resources/ExcelFiles/Recipe-filters-ScrapperHackathon.xlsx");
		workbook.write(file);

		workbook.close();
		file.close();


		//		ExcelReaderCode reader=new ExcelReaderCode("C:/Users/OVI/git/RecipesScrapper/src/test/resources/ExcelFiles/Recipe-ScrapperHackathon.xlsx");
		//		//reader.addSheet("Recipe-data");
		//		for(int colnum=0;colnum<3; colnum++) {
		//			reader.addColumn("Recipe-data", "Recipe"+colnum);
		//			for(int rownum=0;rownum<13; rownum++) {
		//		reader.setCellData("Recipe-data","Recipe"+rownum , rownum,"abc");
		//		//System.out.println("Is new sheet created: "+status);
		//		}
		//		}
		//	}

	}
}


