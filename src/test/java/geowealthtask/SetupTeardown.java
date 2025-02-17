package geowealthtask;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SetupTeardown {
    protected WebDriver driver;

    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driver.get("https://www.mobile.bg/");
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
