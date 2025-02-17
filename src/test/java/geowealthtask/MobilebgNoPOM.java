package geowealthtask;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MobilebgNoPOM {
	
//Task: Create a Selenium script that finds the number of Volkswagen Golfs with four wheel drive on sale on mobile.bg
//and writes the number in the console.
	
//Optional - output in log instead of console.
//Optional - use page objects.
//Optional - count the promoted (VIP or TOP) ads and write the number in the console/log.
	
	public static void main(String[] args) {
		int vipSalesAmount = 0;
		int topSalesAmount = 0;
		boolean visibleVipTop = true;

		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		driver.get("https://www.mobile.bg/");
		driver.manage().window().maximize();	
		
		//Decline Cookies
		driver.findElement(By.id("cookiescript_reject")).click();
		
		//Select cars/jeeps
		driver.findElement(By.cssSelector("div[class='catIcons showcats'] div[class='a a1']")).click();
		
		//Select Bulgaria
		driver.findElement(By.className("BG")).click();
		
		//Open additional criteria
		driver.findElement(By.className("moreFilters")).click();
		
		//Select 4x4 filter
		driver.findElement(By.xpath("//label[@class='option']/span[text()='4x4']")).click();
		
		//Select VW mark
		driver.findElement(By.xpath("//input[@name='marka']")).click();
		driver.findElement(By.xpath("//div/span[text()='VW']")).click();
		
		//Select Golf model
		//Waits for first dropdown element to be selected so second dropdown is usable.
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='VW']")));
		driver.findElement(By.xpath("//input[@name='model']")).click();
		driver.findElement(By.xpath("//div/span[text()='Golf']")).click();
		
		//Initiate search
		driver.findElement(By.className("SEARCH_btn")).click();
		
		//Grab the total amount of cars
		WebElement pullAmount = driver.findElement(By.xpath("//div[contains(text(),'от общо')]"));
		String amountText = pullAmount.getText();
		//System.out.println(amountText);
		//Separate the string with spaces
//		String[] words = amountText.split(" ");
//		//Target the correct word (number of cars)
//		String lastWord = words[words.length - 5];
//		System.out.println("Number of cars: "+lastWord);
		
		//Target specific string between indexes (More accurate method, works better for other scenarios)
		System.out.println("Total amount of car listings: " + amountText.substring(amountText.indexOf("общо")+5, amountText.indexOf("Обяви")-1));
		
		//Get total number of pages
		String pagesAmount = driver.findElement(By.xpath("//div[@class='a saveSlink gray']")).getText();
		int totalPages = Integer.parseInt(pagesAmount);
		//System.out.println(totalPages);
		
		//Going through the pages
		for(int p = 1; p <= totalPages; p++)
		{

			if(p > 1)
			{
				WebElement activePage = wait.until(ExpectedConditions.elementToBeClickable(
	                    By.xpath("//div[@class='pagination']//*[text()=" + p + "]")
		                ));
				
				activePage.click();
			}
			

			if(visibleVipTop == true) 
			{
				//Count the number of VIP sales
				List<WebElement> vipSalesElements = driver.findElements(By.className("VIP"));
				//Count the number of TOP Sales
				List<WebElement> topSalesElements = driver.findElements(By.className("TOP"));
				
				//Check if VIP and TOP Sales are present on the page
				if(vipSalesElements.isEmpty() && topSalesElements.isEmpty())
				{
					System.out.println("Page " + p + " has no VIP or TOP sales, skipping count...");
					visibleVipTop = false;
	                continue;
				}
				
				vipSalesAmount += vipSalesElements.size();
				topSalesAmount += topSalesElements.size();
			}
			
			

		}
		//Print the number of VIP sales
		System.out.println("VIP Sales: " + vipSalesAmount);
		//Print the number of TOP sales
		System.out.println("TOP Sales: " + topSalesAmount);
		
	}

}
