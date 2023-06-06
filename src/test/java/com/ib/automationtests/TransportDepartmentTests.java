package com.ib.automationtests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static com.ib.automationtests.in.gov.telangana.transport.SiteConstants.pageNames.HOMEPAGE;
import static com.ib.automationtests.in.gov.telangana.transport.SiteConstants.pageNames.NONE;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static com.ib.automationtests.in.gov.telangana.transport.SiteConstants.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

@SpringBootTest
public class TransportDepartmentTests {
	
	private static final Logger logger = LogManager.getLogger(TransportDepartmentTests.class);
    private static WebDriver driver ;

	private static pageNames currentPage = NONE ;

	@BeforeAll
	public static void initDriver() {
		logger.info("-----------------------Instantiating Driver-----------------------");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(options);
		logger.info("-----------------------Driver instantiated-----------------------");
	}

	private void openHomePage() {
		logger.info("-----------------------------------------------------");
		// logger.info(this.hashCode());
		// logger.info(this.currentPage);
		logger.info("Opening page '{}'", BaseURL);
		driver.get(BaseURL);
		currentPage = HOMEPAGE;
		logger.info("Page Opened '{}'", BaseURL);
		logger.info("-----------------------------------------------------");
	}

	@Test
	@Order(1)
	void validateTitle()  {

		logger.info("Begin of test 1: "+System.nanoTime());
		// Step 1: Open the website.
		// Note: Below two lines are equivalent
		// driver.get(BaseURL);
		if(!currentPage.equals(HOMEPAGE)) openHomePage();

		// Step 2: Get the title of the website from the browser
		String title = driver.getTitle();
		logger.info("Page title = '{}'", title);

		// Step 3: Check the title is matching the expected title
		assertThat(title).isEqualTo(HomePageTitle);
		logger.info("End of test 1: "+System.nanoTime());
	}

	@Test
	@Order(2)
	void validateWelcomeSplashAndClose() {
		logger.info("Begin of test 2: "+System.nanoTime());
		if(!currentPage.equals(HOMEPAGE)) openHomePage();

		// Step 4: Find welcome splash. (Wait until it appears as it is loading slow)
		WebElement welcomeSplash = waitAndFindBy(By.id("welcomemsg"),driver,10);
		assertThat(welcomeSplash).isNotNull();

		//Step 5: Find the close icon on it
		// Locate close Icon
		WebElement closeButton = welcomeSplash.findElement(By.className("mfpcclose"));
		assertThat(closeButton).isNotNull();


		// Step 6: Click the close icon
		closeButton.click();
		logger.info("End of test 2: "+System.nanoTime());

	}

	// Utility method to implement waited find
	public static WebElement waitAndFindBy(By by, WebDriver driver, int waitTime) {
		WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(waitTime));
		ExpectedCondition<WebElement> welcomeMessageIsVisible = elementToBeClickable(by);
		WebElement element = wait.until(welcomeMessageIsVisible);
		return element;
	}


	@AfterAll
	public static void quitDriver() throws InterruptedException {
		Thread.sleep(2500);
		driver.close();
		driver.quit();
	}

}
