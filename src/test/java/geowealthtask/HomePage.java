package geowealthtask;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private WebDriver driver;

    // Locators
    private By declineCookiesButton = By.id("cookiescript_reject");
    private By carsJeepsButton = By.cssSelector("div[class='catIcons showcats'] div[class='a a1']");
    private By bulgariaButton = By.className("BG");

    // Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Methods
    public void declineCookies() {
        driver.findElement(declineCookiesButton).click();
    }

    public void selectCarsJeeps() {
        driver.findElement(carsJeepsButton).click();
    }

    public void selectBulgaria() {
        driver.findElement(bulgariaButton).click();
    }
}
