package test;


import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class NewDepositTest {
	
   WebDriver driver;
   @BeforeMethod
	public void LaunchBrowser() {

		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
	     driver=new ChromeDriver();
		 
		 driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		  driver.get("http://www.techfios.com/ibilling/");	

}
   @Test
   public void UserShouldBeAbleToAddDeposit() throws InterruptedException {
	   driver.findElement(By.xpath("//input[@id='username']")).sendKeys("demo@techfios.com");
	
		driver.findElement(By.xpath("//input[contains(@id,'passwor')]")).sendKeys("abc123");
		
		driver.findElement(By.xpath("//button[text()='Sign in']")).click();
		String ExpectedTitle="Dashboard- iBilling";
		String ActualTitle=driver.getTitle();
		Assert.assertEquals(ActualTitle, ExpectedTitle,"title did not display");
		By TRANSACTION_MENU_LOCATOR=By.xpath("//ul[@id='side-menu']/descendant::span[text()='Transactions']");
		By NEW_DEPOSIT_PAGE_LOCATOR=By.linkText("New Deposit");
		driver.findElement(TRANSACTION_MENU_LOCATOR).click();
		driver.findElement(NEW_DEPOSIT_PAGE_LOCATOR).click();
		WaitForElement(NEW_DEPOSIT_PAGE_LOCATOR,driver,20);
		driver.findElement(NEW_DEPOSIT_PAGE_LOCATOR).click();
		
		
		//USING DROPDOWN(selecting an account from drop down)
		Select select= new Select(driver.findElement(By.cssSelector("select#account")));
		select.selectByVisibleText("Mean885");
		Thread.sleep(5000);
		 //description
		//all this syntaxes up to the } will add description with a random number and amount
		String ExpectedDescription="Automation Test" + new Random().nextInt(999);
		System.out.println(ExpectedDescription);
		driver.findElement(By.id("description")).sendKeys(ExpectedDescription);
		driver.findElement(By.id("amount")).sendKeys("100,000");
		driver.findElement(By.id("submit")).click();
		Thread.sleep(10000);
		
		
		//put an explicit.wait to the invisible wait after hitting he submit  button
		new WebDriverWait(driver,20).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains[@class,'blockUI']")));
		//putting the items in the in a list
		List<WebElement>DescriptionElements=driver.findElements(By.xpath("//table/descendant::a"));
		System.out.println(DescriptionElements.get(0).getText());
		
		
		//scroll up/down yo must intatiate javascript.to scroll down more put a bigger no than 400
		//to scroll up put a negative before 400
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("scroll(0,400)");
		
		Assert.assertTrue(isDescriptionmatch(ExpectedDescription,DescriptionElements),"Deposit unsuccessful");
   }
   
   private boolean isDescriptionmatch(String expectedDescription, List<WebElement> descriptionElements) {
	//for you to compare the one string and the actual whole list you will need a for loop
	//when copy pasting remember for the loop remember to copy paste items from the above method   
	   for(int i=0;i<descriptionElements.size();i++)  {
		   if(expectedDescription.equalsIgnoreCase(descriptionElements.get(i).getText())) {
			   return true;
			   
		   }
		   
	   }
	return false;
}
private void WaitForElement(By locator, WebDriver driver1, int time) {
   new WebDriverWait(driver1,time).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	
}
@AfterMethod
   public void closeEverything() {
	   driver.close();
	   driver.quit();
   }
}
