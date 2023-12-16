package pages;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HighBloodPressure extends TestBase {


    @FindBy(css = "#nav > li:nth-child(1) > a > div")
    WebElement btnRecipe;

    @FindBy(xpath = "//div[text()='RECIPES']")
    WebElement RecipeBtn;


    @FindBy(css = "#nav > li:nth-child(1) > ul > li")
    List<WebElement> RecipeCategories;

    @FindBy(partialLinkText = "High Blood Pressure")
    WebElement HighBloodPressure;

    @FindBy(xpath = "//article[@itemprop='itemListElement']/div[7]/span[1]")
    List<WebElement> HBPRecipesList;

    @FindBy(css = "#maincontent > div:nth-child(1) > div.recipelist > article")
    List<WebElement> HBPRecipesList1;


    @FindBy(css = "#nav > li:nth-child(1) > ul > li:nth-child(1) > ul > li:nth-child(1) > a")
    WebElement pubjai;

    @FindBy(xpath = "//*[@id=\"nav\"]/li[1]/ul")
    WebElement RecipeSpeciality;

    // Instantiate Actions class
    Actions actions = new Actions(driver);


    public HighBloodPressure() {
        PageFactory.initElements(driver, this);
    }

    public boolean mouseoverRecipes() throws InterruptedException {


        Thread.sleep(3000);

        // Perform mouse over
        actions.moveToElement(btnRecipe).build().perform();
        System.out.println(btnRecipe.getText());

//        RecipeBtn.click();


//        Thread.sleep(3000);
//        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", RecipeBtn);

        Thread.sleep(3000);

        actions.moveToElement(RecipeCategories.get(1)).build().perform();
        System.out.println(RecipeCategories.size());


        Thread.sleep(1000);
        actions.scrollToElement(RecipeCategories.get(1));
        System.out.println(RecipeCategories.get(1).getText());
        actions.click();

        System.out.println(driver.getTitle());

        return btnRecipe.isDisplayed();

    }

    public boolean isVisible() {
        return btnRecipe.isDisplayed();
    }

    public void setHighBloodPressure() throws InterruptedException {

//        Thread.sleep(2000);


        HighBloodPressure.click();

//        Thread.sleep(7000);


    }


    public void scrapHBPRecipes() throws InterruptedException {

        System.out.println("HBP Recipes are: " + HBPRecipesList1.size());

        Thread.sleep(2000);

//          Stale element error
//        for(WebElement rep: HBPRecipesList1) {
//
//
//            By elementLocator = By.id("elementId");
//
//            Thread.sleep(4000);
//            //click on the recipe url
//            rep.findElement(new By.ByXPath("//article[@itemprop='itemListElement']/div[3]/span[1]/a")).click();
//
//            Thread.sleep(4000);
//
//            System.out.println(driver.getTitle());
//
//            driver.navigate().back();
//            driver.navigate().back();
//
//        }


        //This is printing same page
//        for(int i = 0; i <= HBPRecipesList1.size(); i++)  {
//
//            WebElement rep_item = HBPRecipesList1.get(i).findElement(new By.ByXPath("//article[@itemprop='itemListElement']/div[3]/span[1]/a"));
//            By elementLocator = new By.ByXPath("//article[@itemprop='itemListElement']/div[3]/span[1]/a");
//
//
//
//            Thread.sleep(4000);
//            //click on the recipe url
//            rep_item.click();
//
//            Thread.sleep(4000);
//
//            System.out.println(driver.getTitle());
//
//            driver.navigate().back();
//            driver.navigate().back();
//
////            WebElement elementAfterNavigation = driver.findElement(elementLocator);
//            rep_item = driver.findElement(elementLocator);
//
//
//        }


//        Same as above
//        for (int i = 0; i < HBPRecipesList1.size(); i++) {
//
//            WebElement rep = HBPRecipesList1.get(i);
//
//            // Re-find the elements in each iteration
//            WebElement recipeLink = rep.findElement(By.xpath("//article[@itemprop='itemListElement']/div[3]/span[1]/a"));
//            recipeLink.click();
//
//            System.out.println(driver.getTitle());
//
//            driver.navigate().back();
//
//            // Re-fetch the list to avoid staleness
//            HBPRecipesList1 = driver.findElements(new By.ByCssSelector("#maincontent > div:nth-child(1) > div.recipelist > article"));
//
//        }

        for(int i = 1; i < HBPRecipesList1.size(); i++) {

            WebElement rep = HBPRecipesList1.get(i);

            WebElement recipeLink = rep.findElement(By.xpath("//article[" + i + "]/div[3]/span[1]/a"));
            recipeLink.click();

            //Extract data and write to Excel file
//            Recipe ID
//            Recipe Name
//            Recipe Category(Breakfast/lunch/snack/dinner)
//            Food Category(Veg/non-veg/vegan/Jain)
//            Ingredients
//            Preparation Time
//            Cooking Time
//            Preparation method
//            Nutrient values
//            Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism)
//            Recipe URL





            System.out.println(driver.getTitle());

            driver.navigate().back();
        }










    }


}
