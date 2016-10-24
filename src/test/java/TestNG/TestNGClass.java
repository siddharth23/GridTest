package TestNG;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class TestNGClass {
    public WebDriver driver;
    public String URL, Node;
    protected ThreadLocal<RemoteWebDriver> threadDriver = null;

    @Parameters("browser")
    @BeforeTest
    public void launchapp(String browser) throws MalformedURLException {
        String URL = "http://materials.springer.com";

        if (browser.equalsIgnoreCase("android")) {
            System.out.println(" Executing on Android");
            String Node = "http://localhost:4723/wd/hub";
            DesiredCapabilities cap = DesiredCapabilities.android();
            cap.setCapability("version","5.1.0");
            cap.setCapability(MobileCapabilityType.PLATFORM, Platform.ANDROID);
            cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, "5.1.0");
            cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "ANDROID");
            cap.setCapability(MobileCapabilityType.BROWSER_NAME, "Browser");
            cap.setCapability(MobileCapabilityType.DEVICE_NAME, "sid");

            driver = new AndroidDriver(new URL(Node), cap);
            // Puts an Implicit wait, Will wait for 10 seconds before throwing exception
            //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            System.out.println("*****here*****");
            // Launch website
            driver.get(URL);

        } else if (browser.equalsIgnoreCase("chrome")) {

            System.out.println(" Executing on CHROME");
            DesiredCapabilities cap = DesiredCapabilities.firefox();
            cap.setBrowserName("firefox");
            String Node = "http://10.113.4.92:5558/wd/hub";
            driver = new RemoteWebDriver(new URL(Node), cap);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            // Launch website
            driver.navigate().to(URL);
            driver.manage().window().maximize();
        } else if (browser.equalsIgnoreCase("ie")) {
            System.out.println(" Executing on IE");
            DesiredCapabilities cap = DesiredCapabilities.firefox();
            cap.setBrowserName("ie");
            String Node = "http://10.113.4.92:5558/wd/hub";
            driver = new RemoteWebDriver(new URL(Node), cap);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            // Launch website
            driver.navigate().to(URL);
            driver.manage().window().maximize();
        } else {
            throw new IllegalArgumentException("The Browser Type is Undefined");
        }
    }

    @Test
    public void openHomePage() {

        driver.findElement(By.id("searchTerm")).sendKeys("benzene");
        driver.findElement(By.id("search")).click();
        WebDriverWait until = new WebDriverWait(driver, 50);
        until.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("link_sm_lbs_978-3-540-75506-7_909")));
        driver.findElement(By.id("link_sm_lbs_978-3-540-75506-7_909")).click();
        until.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("document__title")));
        // Enter value 10 in the first number of the percent Calculator
        String title = driver.findElement(By.className("document__title")).getText();
        Assert.assertEquals(title, "Dielectric constant of the mixture (1) chlorobenzene; (2) benzene");
    }

    @AfterTest
    public void closeBrowser() {
        driver.quit();
    }
}