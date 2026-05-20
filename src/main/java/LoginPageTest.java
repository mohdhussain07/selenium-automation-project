import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginPageTest {

    WebDriver driver;

    // Ye method har test se PEHLE chalega
    // Browser open karta hai aur website pe jaata hai
    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com");
    }

    // Valid login test
    // Sahi username/password se login hona chahiye
    @Test
    public void validLoginTest() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Verify karo ki login hua — URL change hoti hai
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("inventory"),
                "Login failed! URL mein inventory nahi aaya.");
    }

    // Invalid login test
    // Galat password se error message aana chahiye
    @Test
    public void invalidLoginTest() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("wrongpassword");
        driver.findElement(By.id("login-button")).click();

        // Verify karo ki error message dikh raha hai
        String errorMessage = driver.findElement(
                By.cssSelector("[data-test='error']")).getText();
        Assert.assertTrue(errorMessage.contains("Username and password do not match"),
                "Error message nahi aaya!");
    }

    // Ye method har test ke BAAD chalega
    // Browser band karta hai
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}