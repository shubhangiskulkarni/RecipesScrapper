package pages;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ExcelReaderCode;
import utils.ExcelUtils;

import java.util.ArrayList;
import java.util.List;

public class HighBloodPressure extends TestBase {

    boolean isValidRecipe = false;

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

    List<String> Hypertension_EliminateItem = new ArrayList<String>();

    List<String> Hypertension_AddOnItem = new ArrayList<String>();

    List<String> Hypertension_AllergiesItem = new ArrayList<String>();


    String ingre_List = "";
    String fud_Category;
    String rec_Category;
    String morbid_Condition;


    // constructor
    public HighBloodPressure() {
        PageFactory.initElements(driver, this);
    }

    //Method to enable the recipes menu
    public boolean mouseoverRecipes() throws InterruptedException {


        Thread.sleep(3000);

        // Perform mouse over
        actions.moveToElement(btnRecipe).build().perform();
//        System.out.println(btnRecipe.getText());


        Thread.sleep(3000);

        actions.moveToElement(RecipeCategories.get(1)).build().perform();
        System.out.println(RecipeCategories.size());


        Thread.sleep(1000);
        actions.scrollToElement(RecipeCategories.get(1));
//        System.out.println(RecipeCategories.get(1).getText());
        actions.click();

        System.out.println(driver.getTitle());

        return btnRecipe.isDisplayed();

    }


    public boolean isVisible() {
        return btnRecipe.isDisplayed();
    }


    public void setHighBloodPressure() throws InterruptedException {

        HighBloodPressure.click();

    }


    public void readExcel() {
        ExcelReaderCode reader = new ExcelReaderCode(
                "src/test/resources/IngredientsAndComorbidities-ScrapperHackathon.xlsx");

        for (int i = 3; i <= 14; i++) {
            String testData = reader.getCellData("Diabetes-Hypothyroidism-Hyperte", 4, i);
            Hypertension_EliminateItem.add(testData);
//            System.out.println("eleminate item from excel is " + testData);
        }

        for (int i = 3; i <= 20; i++) {
            String testData = reader.getCellData("Diabetes-Hypothyroidism-Hyperte", 5, i);
            Hypertension_AddOnItem.add(testData);
//            System.out.println("addon item from excel is " + testData);
        }

        for (int i = 2; i <= 14; i++) {
            String testData = reader.getCellData("Allergies", 0, i);
            Hypertension_AllergiesItem.add(testData);
//            System.out.println("Allergy item from excel is " + testData);
        }

    }


    public void scrapHBPRecipes() throws InterruptedException {

        System.out.println("HBP Recipes are: " + HBPRecipesList1.size());

        Thread.sleep(2000);

        int noOfPages = driver.findElements(By.xpath("//div[@id='pagination']//a")).size();
        //Create Excel file
        ExcelUtils excelUtils = new ExcelUtils();


        for(int currentPage = 1; currentPage <= noOfPages; currentPage++) {

            String pageNo = Integer.toString(currentPage);
            WebElement pagination = driver.findElement(By.xpath("//div[@id='pagination']//a[" + pageNo + "]"));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", pagination);

            for (int i = 1; i < HBPRecipesList1.size(); i++) {

                Thread.sleep(1000);

                String[] data = new String[11];

                WebElement rep = HBPRecipesList1.get(i);


                WebElement repId = driver.findElement(new By.ByXPath("//article[" + i + "]/div[2]/span[1]"));

                // Get all child nodes of the span element
                String spanText = repId.getAttribute("innerText");

                // Split the text by the <br> tag and extract the part before it
                String[] textParts = spanText.split("\\r?\\n");
                String recipeNumber = textParts[0].replaceAll("[^0-9]", "");

                System.out.println("recipeNumber: " + recipeNumber);

                data[0] = recipeNumber;

                WebElement recipeLink = rep.findElement(By.xpath("//article[" + i + "]/div[3]/span[1]/a"));
                data[1] = recipeLink.getText();

                try {
                    WebElement nutritionVal = driver
                            .findElement(By.xpath("//article[" + i + "]/div[2]/div/a/span[1]"));
                    String nut_Val = nutritionVal.getText();
                    System.out.println(nut_Val);
                    data[8] = nut_Val;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    String nut_Val = "Not Found";
                    System.out.println(nut_Val);
                    data[8] = nut_Val;
                }

                Thread.sleep(5000);
                recipeLink.click();

                //Extract data and write to Excel file
//            Recipe ID, Recipe Name, Recipe Category(Breakfast/lunch/snack/dinner), Food Category(Veg/non-veg/vegan/Jain)
//            Ingredients, Preparation Time, Cooking Time, Preparation method, Nutrient values, Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism)
//            Recipe URL

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
                data[2] = rec_Category;

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
                System.out.println(fud_Category);
                data[3] = fud_Category;

                List<WebElement> ingredients = driver.findElements(By.xpath("//span[@itemprop='recipeIngredient']/a"));
                ingre_List = "";
                for (WebElement selectIngre : ingredients) {
                    ingre_List = ingre_List + "," + selectIngre.getText();
                }
                System.out.println(ingre_List);
                data[4] = ingre_List;

                WebElement preparatnMethod = driver.findElement(By.xpath("//div[@id='recipe_small_steps']//span//ol"));
                WebElement preparatnTime = driver.findElement(By.xpath("//p[2]//time[1]"));
                String prep_Time = preparatnTime.getText();
                System.out.println(prep_Time);
                data[5] = prep_Time;

                WebElement cookingTime = driver.findElement(By.xpath("//p[2]//time[2]"));
                String cook_Time = cookingTime.getText();
                System.out.println(cook_Time);
                data[6] = cook_Time;


                String prep_Method = preparatnMethod.getText();
                System.out.println(prep_Method);
                data[7] = prep_Method;

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
                data[9] = morbid_Condition;


                String URL = driver.getCurrentUrl();
                System.out.println(URL);
                data[10] = URL;

                Thread.sleep(3000);

                if(isValidRecipe(Hypertension_EliminateItem, data[4])) {

                    //Recipes that do not contain eliminated ingredients
                    excelUtils.createFile("Hypertension_Recipes", prop.getProperty("OutputLocation"));
                    excelUtils.WriteToExcelFile("Hypertension_Recipes", prop.getProperty("OutputLocation"), data, i);

                    if(addRecipe(Hypertension_AddOnItem, data[4])) {

                        //Recipes that do not contain eliminated ingredients but the "To-add" ingredients
                        excelUtils.createFile("Hypertension_Recipes_AddOn", prop.getProperty("OutputLocation"));
                        excelUtils.WriteToExcelFile("Hypertension_Recipes_AddOn", prop.getProperty("OutputLocation"), data, i);

                    }

                    if (isAllergic(Hypertension_AllergiesItem, data[4])) {

                        //List of all the filtered recipes after applying allergy ingredients
                        excelUtils.createFile("Hypertension_Recipes_Allergies", prop.getProperty("OutputLocation"));
                        excelUtils.WriteToExcelFile("Hypertension_Recipes_Allergies", prop.getProperty("OutputLocation"), data, i);

                    }


                    System.out.println(i + ": " + driver.getTitle());
                }

                driver.navigate().back();
                driver.navigate().back();

            }
        }

    }


    public boolean isValidRecipe(List<String> Hypertension_EliminateItem, String ingr_List) {
        // System.out.println(ingr_List);
        String[] ingredients = ingr_List.split(",");
        for (String ingr : ingredients) {
            ingr = ingr.trim();
            if (ingr.length() > 0 && Hypertension_EliminateItem.contains(ingr)) {
                System.out.println(ingr);
                System.out.println("isValidRecipe - Invalid Item");
                return false;
            }
        }
        System.out.println("isValidRecipe - Valid Items");
        return true;

    }

    public boolean addRecipe(List<String> Hypertension_AddItem, String ingr_List) {
        // System.out.println(ingr_List);
        String[] ingredients = ingr_List.split(",");
        for (String ingr : ingredients) {
            ingr = ingr.trim();
            if (ingr.length() > 0 && Hypertension_AddItem.contains(ingr)) {
                System.out.println(ingr);
                System.out.println("addRecipe - Valid Item");
                return true;
            }
        }
        System.out.println("addRecipe - Invalid Items");
        return false;

    }


    public boolean isAllergic(List<String> Hypertension_AllergiesItem, String ingr_List) {
        // System.out.println(ingr_List);
        String[] ingredients = ingr_List.split(",");
        for (String ingr : ingredients) {
            ingr = ingr.trim();
            if (ingr.length() > 0 && Hypertension_AllergiesItem.contains(ingr)) {
                System.out.println(ingr);
                System.out.println("isAllergic - Invalid Item");
                return false;
            }
        }
        System.out.println("isAllergic - Valid Items");
        return true;

    }

}
