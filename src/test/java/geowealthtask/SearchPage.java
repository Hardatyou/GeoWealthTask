package geowealthtask;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By moreFiltersButton = By.className("moreFilters");
    private By fourWheelDriveOption = By.xpath("//label[@class='option']/span[text()='4x4']");
    private By markaField = By.xpath("//input[@name='marka']");
    private By vwOption = By.xpath("//div/span[text()='VW']");
    private By modelField = By.xpath("//input[@name='model']");
    private By golfOption = By.xpath("//div/span[text()='Golf']");
    private By searchButton = By.className("SEARCH_btn");
    private By totalCarsInfo = By.xpath("//div[contains(text(),'от общо')]");
    private By pagesAmount = By.xpath("//div[@class='a saveSlink gray']");
    private By vipSalesLocator = By.className("VIP");
    private By topSalesLocator = By.className("TOP");
    
 // Sales amounts
    private int vipSalesAmount = 0;
    private int topSalesAmount = 0;

    // Constructor
    public SearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Methods
    public void openMoreFilters() {
        driver.findElement(moreFiltersButton).click();
    }

    public void selectFourWheelDrive() {
        driver.findElement(fourWheelDriveOption).click();
    }

    public void selectVWMark() {
        driver.findElement(markaField).click();
        driver.findElement(vwOption).click();
    }

    public void selectGolfModel() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='VW']"))); // Ensure dropdown loads
        driver.findElement(modelField).click();
        driver.findElement(golfOption).click();
    }

    public void startSearch() {
        driver.findElement(searchButton).click();
    }

    public String getTotalCars() {
        WebElement totalCarsElement = driver.findElement(totalCarsInfo);
        String fullText = totalCarsElement.getText();
        return fullText.substring(fullText.indexOf("общо")+5, fullText.indexOf("Обяви")-1);
    }
    
    public int getTotalPages() {
    	WebElement totalPagesAmount = driver.findElement(pagesAmount);
    	String pagesToString = totalPagesAmount.getText();
    	int pagesToInt = Integer.parseInt(pagesToString);
		return pagesToInt;
    }
    
 // Method to go through pages and count sales
    public void goThroughPages(int totalPages, boolean isTrue) 
    {
        
        for (int p = 1; p <= totalPages; p++) 
        {
            if (p > 1) {
                clickOnPage(p);  // Click on the page
            }
            
            if (isTrue) {
                // Get sales count
                int[] salesCounts = getVipAndTopSalesCounts();
                int vipSales = salesCounts[0];
                int topSales = salesCounts[1];
                
                if (vipSales == 0 && topSales == 0) 
                {
                    System.out.println("Page " + p + " has no VIP or TOP sales, skipping count...");
                    isTrue = false;
                    continue; // Skip to next page
                }
                
                vipSalesAmount += vipSales;
                topSalesAmount += topSales;
            }
        }
    }
    
 // Getter methods for sales amounts
    public int getVipSalesAmount() 
    {
        return vipSalesAmount;
    }

    public int getTopSalesAmount() 
    {
        return topSalesAmount;
    }
    
 // Method to click on a specific page
    private void clickOnPage(int pageNumber) {
        WebElement activePage = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@class='pagination']//*[text()='" + pageNumber + "']")
        ));
        activePage.click();
    }
    
 // Method to get the count of VIP and TOP sales on the page
    private int[] getVipAndTopSalesCounts() {
        List<WebElement> vipSalesElements = driver.findElements(vipSalesLocator);
        List<WebElement> topSalesElements = driver.findElements(topSalesLocator);
        
        return new int[]{vipSalesElements.size(), topSalesElements.size()};
    }

    // Method to check if VIP and TOP sales are visible
    public boolean isVipOrTopSalesVisible() {
        List<WebElement> vipSalesElements = driver.findElements(vipSalesLocator);
        List<WebElement> topSalesElements = driver.findElements(topSalesLocator);
        
        return !vipSalesElements.isEmpty() || !topSalesElements.isEmpty();
    }
}
