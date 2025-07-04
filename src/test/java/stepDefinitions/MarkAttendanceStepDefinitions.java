package stepDefinitions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import com.google.common.io.Files;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.java.en.*;
import io.cucumber.java.After ;
import io.cucumber.java.Scenario ;

import static org.testng.Reporter.log;

public class MarkAttendanceStepDefinitions{
	WebDriver driver;
	WebDriverWait wait;

	@Given("user logs into HR One portal")
	public void user_logs_into_hr_one_portal() {
	    ChromeOptions options=new ChromeOptions();
	    options.addArguments("--headless=new","--disable-gpu","--window-size=1920,1080","--disable-blink-features=AutomationControlled","--no-sandbox","--disable-dev-shm-usage");
	    driver=new ChromeDriver(options);

	    driver.manage().window().maximize();
		wait= new WebDriverWait(driver, Duration.ofSeconds(30));
	    driver.get("https://app.hrone.cloud/app");
		log("Navigated to HR One: "+driver.getCurrentUrl());

		// Wait for username field to be present and clickable
		WebElement usernameInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='hrone-username']")));
		usernameInput.sendKeys("9599017537");
		log("Entered username.");

		// Wait for NEXT button to be clickable and click it
		WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()=' NEXT ']")));
		nextButton.click();
		log("Clicked NEXT button.");


		// Wait for password field to be present and clickable
		WebElement passwordInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='hrone-password']")));
		passwordInput.sendKeys("Cricket@123");
		log("Entered password.");

		// Wait for LOG IN button to be clickable and click it
		WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()=' LOG IN ']")));
		loginButton.click();
		log("Clicked LOG IN button.");
		log("Login process initiated. Current URL after login attempt: " + driver.getCurrentUrl());

	}
	@When("user clicks on Mark Attendance")
	public void user_clicks_on_mark_attendance() {
//		By ele= By.xpath("//button[text()=' Mark attendance ']");
//
//	    Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(25));
//	    wait.until(ExpectedConditions.elementToBeClickable(ele));
//	   driver.findElement(By.xpath("//button[text()=' Mark attendance ']")).click();

		By markAttendanceBtn = By.xpath("//button[text()=' Mark attendance ']");
		WebElement markBtn = wait.until(ExpectedConditions.elementToBeClickable(markAttendanceBtn));

		if (markBtn.isDisplayed() && markBtn.isEnabled()) {
			log("Clicking Mark Attendance");
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", markBtn);
		} else {
			log("Mark Attendance button not interactable");
		}

	}
	@When("confirms attendance in the dialog")
	public void confirms_attendance_in_the_dialog() throws InterruptedException {
//		//By ele= By.xpath("//button[text()=' Cancel ']/..//following-sibling::button");
//		 Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(25));
//		 wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Cancel ']/..//following-sibling::button"))).click();
//		//driver.findElement(By.xpath("//button[text()=' Cancel ']/..//following-sibling::button")).click();
//		Thread.sleep(5000);

		By confirmBtn = By.xpath("//button[text()=' Cancel ']/..//following-sibling::button");
		WebElement confirm = wait.until(ExpectedConditions.elementToBeClickable(confirmBtn));

		((JavascriptExecutor) driver).executeScript("arguments[0].click();", confirm);
		log("Clicked Confirm in dialog");

	}
	@Then("attendance should be marked successfully")
	public void attendance_should_be_marked_successfully() {
//		 String text ="Mark attendance";
//		 WebElement elem= driver.findElement(By.xpath("//button[text()=' Mark attendance ']"));
//		 Assert.assertEquals(elem.getText(), text);


		try {
			WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()=' Mark attendance ']")));
			String text = btn.getText().trim();
			log("Final button text: " + text);
			assert text.equals("Mark attendance") : "Attendance may not be marked.";
			// Capture page source
			String pageSource = driver.getPageSource();
			String filePath = "target/pageSource.html";
			try (FileWriter fileWriter = new FileWriter(filePath)) {
				fileWriter.write(pageSource);
				log("Page source saved to: " + filePath);
			} catch (IOException e) {
				log("Failed to save page source: " + e.getMessage());
			}
		} catch (Exception e) {
			log("Failed to verify attendance: " + e.getMessage());
			throw new RuntimeException("Attendance failed", e);
		} finally {
			driver.quit();
		}

	}
	private void log(String message) {
		System.out.println("error" + message);
	}
	@After
	public void tearDown(Scenario scenario) {
		if (scenario.isFailed()) {
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			try {
				String screenshotName = scenario.getName().replaceAll(" ", "_") + ".png";
				File destFile = new File("target/screenshots/" + screenshotName);
				Files.createParentDirs(destFile);
				Files.copy(screenshot, destFile);
				log("Screenshot saved to: " + destFile.getAbsolutePath());
			} catch (IOException e) {
				log("Failed to save screenshot: " + e.getMessage());
			}
		}
		if (driver != null) {
			driver.quit();
		}

	}

}