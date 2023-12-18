package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.safari.SafariDriver;
import utils.LoggerLoad;
import utils.TestUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {

    public static Properties prop;
    protected static WebDriver driver;

    public TestBase() {

        prop = new Properties();

        try {

            // reading property file...
            InputStream stream = TestBase.class.getClassLoader().getResourceAsStream("config.properties");
            prop.load(stream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // WebDriver initialization...
    @SuppressWarnings("deprecation")
    public static void initialization() {

        LoggerLoad.info("Inside init");
        LoggerLoad.info("The url is: " + prop.getProperty("url"));

        String browserName = prop.getProperty("browser");

        // TODO REPLACE THIS WITH SWITCH STATEMENTS in case of multi browser testing
        if (browserName.equalsIgnoreCase("chrome")) {

            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();

        } else if (browserName.equalsIgnoreCase("edge")) {

            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else if (browserName.equalsIgnoreCase("safari")) {

            WebDriverManager.safaridriver().setup();
            driver = new SafariDriver();

        }

        //setting driver properties...
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
        driver.get(prop.getProperty("url"));

    }

    //few reusable methods...
    public String getTitleOfCurrentPage() {

        return driver.getTitle();

    }

    public String getURLOfCurrentPage() {

        return driver.getCurrentUrl();

    }


}
