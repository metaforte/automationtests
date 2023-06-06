package com.ib.automationtests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static com.ib.automationtests.in.gov.telangana.transport.SiteConstants.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

@SpringBootTest
public class TransportDepartmentTests {
	
	private static final Logger logger = LogManager.getLogger(TransportDepartmentTests.class);
    private static WebDriver driver ;

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
		logger.info("Opening page '{}'", BaseURL);
		driver.get(BaseURL);
		logger.info("Page Opened '{}'", BaseURL);
	}

	@Test
	@Order(1)
	void validateTitle()  {
		openHomePage();
		String title = driver.getTitle();
		logger.info("Page title = '{}'", title);
		assertThat(title).isEqualTo(HomePageTitle);
	}

	@Test
	@Order(2)
	void validateWelcomeSplashAndClose() {
		//  Uncomment the below if running only one test.
		// openHomePage();

		// Here, we need to wait for the welcome pop up to appear and then close it
		// Wait for the welcome splash to load. Wait for a maximum of 10 seconds`
		WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(10));
		ExpectedCondition<WebElement> welcomeMessageIsVisible = elementToBeClickable(By.id("welcomemsg"));
		WebElement welcomeSplash = wait.until(welcomeMessageIsVisible);

		assertThat(welcomeSplash).isNotNull();

		// Locate close Icon
		WebElement closeButton = welcomeSplash.findElement(By.className("mfpcclose"));
		assertThat(closeButton).isNotNull();

		// Click the close icon
		closeButton.click();

	}


	@AfterAll
	public static void quitDriver() throws InterruptedException {
		Thread.sleep(2500);
		driver.close();
		driver.quit();
	}
}
