import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LogoutTest {

    WebDriver driver;
    // WebDriver ek interface hai jo browser ko control karta hai
    // Jaise ek remote control hota hai TV ke liye
    // driver naam ka variable banaya jisme browser store hoga

    @BeforeMethod
    // Ye annotation matlab — "har test se PEHLE ye method chala"
    // Iska use isliye karte hain taaki har test fresh browser se start ho
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        // WebDriverManager automatically sahi ChromeDriver download karta hai
        // Pehle manually driver download karna padta tha — ye us pain ko hatata hai

        driver = new ChromeDriver();
        // Naya Chrome browser window khulta hai

        driver.get("https://www.saucedemo.com");
        // Browser us URL pe jaata hai — jaise address bar mein type karna
    }

    @Test
    // Ye annotation matlab — "ye ek test case hai"
    // TestNG sirf @Test wale methods ko test samajhta hai aur run karta hai
    public void logoutTest() {

        // Step 1: Pehle login karo
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        // findElement — page pe element dhundta hai
        // By.id — element ko uske ID se dhundta hai (HTML mein id attribute)
        // sendKeys — textbox mein text type karta hai
        // click — button ya link click karta hai

        // Step 2: Hamburger menu click karo (top left 3 lines wala button)
        driver.findElement(By.id("react-burger-menu-btn")).click();
        // Ye SauceDemo ka menu button hai jisme logout option hota hai

        // Step 3: Thoda wait karo menu open hone ke liye
        try {
            Thread.sleep(1000);
            // Thread.sleep matlab — "1000 milliseconds yaani 1 second ruk jao"
            // Isliye use karte hain kyunki menu open hone mein thoda time lagta hai
            // Agar wait na karo toh logout button milega hi nahi
        } catch (InterruptedException e) {
            e.printStackTrace();
            // Agar sleep mein koi error aaye toh usse handle karta hai
        }

        // Step 4: Logout button click karo
        driver.findElement(By.id("logout_sidebar_link")).click();
        // Menu mein logout ka link hai, usse click karo

        // Step 5: Verify karo ki logout hua
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("saucedemo.com"),
                "Logout nahi hua!");
        // Assert.assertTrue — verify karta hai ki condition sahi hai
        // Agar logout hua hoga toh URL wapas saucedemo.com pe aayega
        // Agar condition false hui toh test FAIL ho jayega
    }

    @AfterMethod
    // Ye annotation matlab — "har test ke BAAD ye method chala"
    // Isliye use karte hain taaki browser band ho jaye aur memory free ho
    public void tearDown() {
        driver.quit();
        // quit() — browser completely band karta hai
        // close() se sirf window band hoti hai, quit() se poora browser
    }
}
