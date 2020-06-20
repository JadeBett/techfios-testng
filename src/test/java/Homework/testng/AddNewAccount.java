package Homework.testng;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AddNewAccount {
	WebDriver driver;
	   @BeforeMethod
		public void LaunchBrowser() {

			System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
		     driver=new ChromeDriver();
			 
			 driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
			  driver.get("http://www.techfios.com/ibilling/");	
			  

	}



	   @Test
	   public void UserShouldBeAbleToAddNewAccount() throws InterruptedException {
		   driver.findElement(By.xpath("//input[@id='username']")).sendKeys("demo@techfios.com");
		
			driver.findElement(By.xpath("//input[contains(@id,'passwor')]")).sendKeys("abc123");
			
			driver.findElement(By.xpath("//button[text()='Sign in']")).click();
			Thread.sleep(3000);
			By BANKANDCASH_MENU_LOCATOR=By.xpath("//ul[@id='side-menu']/descendant::span[text()='Bank & Cash']");
			By NEWACCOUNT_LOCATOR=By.xpath("//ul[@id='side-menu']/descendant::a[text()='New Account']");
			driver.findElement(BANKANDCASH_MENU_LOCATOR).click();
			driver.findElement(NEWACCOUNT_LOCATOR).click();
			Thread.sleep(3000);
			//randomize
			String characters="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			   String randomstring="";
			   //int length=10;
			   
			   Random rand=new Random();
			   int length=rand.nextInt(10);
			   char[] text=new char[length];
			   for(int i=0;i<length;i++) {
				   text[i]=characters.charAt(rand.nextInt(characters.length()));
				   
			   }
			   for(int i=0;i<text.length;i++) {
				   randomstring += text[i];
				   
			   }
			   System.out.println(randomstring);

           driver.findElement(By.id("account")).sendKeys(randomstring);
           //driver.findElement(By.id("description")).sendKeys("Testing");
           String expectedbalance="$" + new Random().nextInt(100000);
           driver.findElement(By.id("balance")).sendKeys(expectedbalance);
           Thread.sleep(5000);
           //driver.findElement(By.id("account_number")).sendKeys("A565556");
          // driver.findElement(By.id("contact_person")).sendKeys("Andrew");
          // driver.findElement(By.id("contact_phone")).sendKeys("443-443-443");
           //driver.findElement(By.id("ib_url")).sendKeys("http://www.techfios.com/ibilling/");
           driver.findElement(By.xpath("//button[@class='btn btn-search']")).click();
           Thread.sleep(10000);
           String account="//div[@class='alert alert-success fade in']";
           String Expectedalert="Account Created Successfully";
           String actualalert=driver.findElement(By.xpath("//div[@class='alert alert-success fade in']")).getText();
           Assert.assertEquals(actualalert, Expectedalert, "Alert did not display");
           WebDriverWait wait=new  WebDriverWait(driver,10);
 	      
 	      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(account)));
	   }
	@AfterMethod
	   public void closeEverything() {
		   driver.close();
		   driver.quit();
	   }
}
