package stepDefinitions;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.java.en.*;

public class MarkAttendanceStepDefinitions{
	WebDriver driver;
	@Given("user logs into HR One portal")
	public void user_logs_into_hr_one_portal() {
	    ChromeOptions options=new ChromeOptions();
	    options.addArguments("--headless=new","--disable-gpu","--window-size=1920,1080","--disable-blink-features=AutomationControlled");
	    driver=new ChromeDriver(options);
	    driver.manage().window().maximize();
	    driver.get("https://app.hrone.cloud/app");
	    driver.findElement(By.xpath("//input[@id='hrone-username']")).sendKeys("9599017537");
	    driver.findElement(By.xpath("//span[text()=' NEXT ']")).click();
	    
	    By ele= By.xpath("//input[@id='hrone-password']");
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    WebElement passwordInput = wait.until(ExpectedConditions.elementToBeClickable(ele));
	    
	    driver.findElement(By.xpath("//input[@id='hrone-password']")).sendKeys("Cricket@123");
	    driver.findElement(By.xpath("//span[text()=' LOG IN ']")).click();
	    
	    
	}
	@When("user clicks on Mark Attendance")
	public void user_clicks_on_mark_attendance() {
		By ele= By.xpath("//button[text()=' Mark attendance ']");
		
	    Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(25));
	    wait.until(ExpectedConditions.elementToBeClickable(ele));
	   driver.findElement(By.xpath("//button[text()=' Mark attendance ']")).click();
	}
	@When("confirms attendance in the dialog")
	public void confirms_attendance_in_the_dialog() throws InterruptedException {
		//By ele= By.xpath("//button[text()=' Cancel ']/..//following-sibling::button");
		 Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(25));
		 wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Cancel ']/..//following-sibling::button"))).click();
		//driver.findElement(By.xpath("//button[text()=' Cancel ']/..//following-sibling::button")).click();
		Thread.sleep(5000);
	}
	@Then("attendance should be marked successfully")
	public void attendance_should_be_marked_successfully() {
		 String text ="Mark attendance";
		 WebElement elem= driver.findElement(By.xpath("//button[text()=' Mark attendance ']"));
		 Assert.assertEquals(elem.getText(), text);
	}

	
}