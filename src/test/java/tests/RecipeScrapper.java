package tests;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import base.TestBase;
import utils.ExcelReaderCode;
//import Collections.ObjectModel;
public class RecipeScrapper extends TestBase {
	//WebElement recipesMenu = driver.findElement(By.xpath("//div[text()='RECIPES']"));
	//List<WebElement> recipeID= driver.findElements(By.xpath("//article[@itemprop='itemListElement']/div[2]/span"));
	List<WebElement> recipeURL= driver.findElements(By.xpath("//article[@itemprop='itemListElement']/div[3]/span[@class='rcc_recipename']/a"));
//	List<WebElement> recipeName= driver.findElements(By.xpath("//article[@itemprop='itemListElement']/div[3]/span[@class='rcc_recipename']/a"));
	List<String> storedRecipeName=new ArrayList<>();
	//WebElement diabeticRecipes= driver.findElement(By.linkText("Diabetic recipes "));
//	List<WebElement> ingredients=driver.findElements(By.xpath("//span[@itemprop='recipeIngredient']/a"));
	List<String> storedIngredientsList=new ArrayList<>();
	
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
	
	public RecipeScrapper(WebDriver driver) {
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

	public void goToRecipes() {
//		List<WebElement> recipeName= driver.findElements(By.xpath("//article[@itemprop='itemListElement']/div[3]/span[@class='rcc_recipename']/a"));
//	for(WebElement selectLi: recipeName)
//		{
//			String recipe_Name=selectLi.getText();
//			System.out.println(recipe_Name);
//			selectLi.click();
//			
	 driver.findElement(By.xpath("//article[@itemprop='itemListElement']/div[3]/span[@class='rcc_recipename']/a[text()='Quinoa Dosa']")).click();
			ExcelReaderCode reader=new ExcelReaderCode("C:/Users/OVI/git/RecipesScrapper/ExcelFiles/IngredientsAndComorbidities-ScrapperHackathon.xlsx");
			 Boolean sheetCheck= reader.isSheetExist("Diabetes-Hypothyroidism-Hyperte");
			 System.out.println("The sheet existance status is : "+sheetCheck);
			 List<String> Diabetic_EliminateItem=new ArrayList<String>();
			 List<String> Diabetic_addItem=new ArrayList<String>();
			 for(int i=3;i<37;i++) {
				String testData=reader.getCellData("Diabetes-Hypothyroidism-Hyperte", 0, i);
				Diabetic_EliminateItem.add(testData);
				System.out.println(testData);
			 }
			 
			 for(int i=3;i<20;i++) {
					String testData=reader.getCellData("Diabetes-Hypothyroidism-Hyperte", 1, i);
					Diabetic_addItem.add(testData);
					System.out.println(testData);
				 }
			 
			List<WebElement> ingredients=driver.findElements(By.xpath("//span[@itemprop='recipeIngredient']/a"));
			for(WebElement selectIngre: ingredients)
			{
				
				String status_Eliminate=null;
				String status_Add=null;
				String recipeIngredients=selectIngre.getText();
//				storedIngredientsList.add(recipeIngredients);
//				System.out.println(recipeIngredients);
				//Iterator iterator = Diabetic_addItem.iterator();
			      
				for(String value:Diabetic_EliminateItem ) {
					if(value.contentEquals(recipeIngredients)) {
						status_Eliminate="found";
						
					}
					
				}//end of for2
				
				for(String value:Diabetic_addItem ) {
					if(value.contentEquals(recipeIngredients)) {
						status_Add="found";
						
					}
					
				}//end of for3
				
				if(status_Eliminate=="found") {
					System.out.println("Don't add recipe in list: "+ selectIngre);
				}
				
				if(status_Add=="found") {
					System.out.println("Add recipe in list: "+ selectIngre);
				}
				 driver.navigate().to("https://www.tarladalal.com/recipes-for-indian-diabetic-recipes-370");
//			}//end of for1

		}
	}
	
}
