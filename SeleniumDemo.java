import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SeleniumDemo {
	
	WebDriver driver = null;
	String actualName = null;
	String actualEmail = null;
	String expectedName = "Example Name";
	String expectedEmail = "examplename@gmail.com";
	String password = "PASSWORD";
	String driverDir = "Path\\to\\geckodriver.exe"; //Here Firefox Gecko Driver is used
	
	//The method to run before the Selenium Test
	@BeforeTest 
	public void setUpTest() {
		System.setProperty("webdriver.gecko.driver", driverDir);
		driver = new FirefoxDriver(); //Use the Firefox driver to use the Firefox browser
	}

	//The method to run as the Selenium Test
	@Test
	public void seleniumTest() throws InterruptedException {
		
		WebDriverWait wait = new WebDriverWait(driver, 20);
		
		driver.manage().window().maximize(); //Maximise the Firefox window
		
		driver.get("https://www.google.com"); //Go to https://www.google.com
		
		
		WebElement element = driver.findElement(By.name("q"));
	    element.sendKeys("GMAIL");
	    element.submit();
		
	    Thread.sleep(2000);
		// Click on the Gmail link
		driver.findElement(By.xpath("//*[@class='r']//*[text()='Gmail - Google']")).click();
		
		Thread.sleep(5000);
		// Click Sign In
		WebElement clickElement = driver.findElement(By.xpath("//*[contains(@href,'https://mail.google.com/mail/')]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", clickElement);
		
		Thread.sleep(2000);
		ArrayList<String> tabList = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabList.get(0)); // Switch to the tab one
		driver.close(); // Close the tab one
		driver.switchTo().window(tabList.get(1)); // Switch to the tab two
		
		Thread.sleep(2000);
		
		driver.findElement(By.id("identifierId")).sendKeys(this.expectedEmail); //Enter the email in the login page
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id=\"identifierNext\"]/span/span")).click(); //Click the next button
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		WebElement password = driver.findElement(
				By.xpath("//*[@id=\"password\"]/div[1]/div/div[1]/input")
				); 
        wait.until(ExpectedConditions.elementToBeClickable(password));
        password.sendKeys(this.password); //Enter password in the login page
        driver.findElement(By.xpath("//*[@id=\"passwordNext\"]/span/span")).click(); //Click next button
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		//Get the value of the Email address
		
		WebElement userName = driver.findElement(
				By.xpath(
				"//div[@class='gb_lb']" //Relative XPath is used here
				));
		actualEmail = userName.getAttribute("innerHTML");

		
		//Get the value of the Name of the user
		
		WebElement nameOfUser = driver.findElement(
				By.xpath(
				"//div[@class='gb_jb gb_kb']" //Relative XPath is used here   
				));
		actualName = nameOfUser.getAttribute("innerHTML");

	}
	
	//Method to run after the main test is done
	@AfterTest
	public void tearDownTest() {
		
		if (actualEmail.equals(expectedEmail) && actualName.equals(expectedName)) {
			System.out.println("Test Successful!\n"); //If the test is successful then print out "Test Successful!"
		}else {
			System.out.println("Test Failed!\n"); //If the test is failed then print out "Test Failed!"
		}
		driver.close();
	}

}
