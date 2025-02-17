package geowealthtask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MobileBgTest extends SetupTeardown {
	private static final Logger log = LogManager.getLogger(MobileBgTest.class);

    public void testVWGolf4x4Count() {
        HomePage homePage = new HomePage(driver);
        SearchPage searchPage = new SearchPage(driver);

        // Interactions
        homePage.declineCookies();
        homePage.selectCarsJeeps();
        homePage.selectBulgaria();

        searchPage.openMoreFilters();
        searchPage.selectFourWheelDrive();
        searchPage.selectVWMark();
        searchPage.selectGolfModel();
        searchPage.startSearch();
        searchPage.goThroughPages(searchPage.getTotalPages(), true);

        // Get & print results
        String numberOfCars = searchPage.getTotalCars();
        log.info("Number of Volkswagen Golf 4x4 cars: " + numberOfCars);
        log.info("VIP Sales: " + searchPage.getVipSalesAmount());
        log.info("TOP Sales: " + searchPage.getTopSalesAmount());
    }
}
