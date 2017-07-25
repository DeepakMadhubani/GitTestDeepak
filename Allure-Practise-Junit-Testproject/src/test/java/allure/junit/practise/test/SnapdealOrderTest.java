package allure.junit.practise.test;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import ru.yandex.qatools.allure.annotations.Step;

//import allure.junit.practise.test.util.ExcelReader;




public class SnapdealOrderTest {
	WebDriver driver;
	String baseUrl;
//	ExcelReader excelReader;
	static File Filepth=new File(System.getProperty("user.dir")+"\\src\\main\\java\\allure\\junit\\practise\\test\\util\\SnapdealData.xls");
	Actions action;
	WebElement dailyNeeds;
	String title="Online Shopping Site in India - Shop for Electronics, Mobile, Men & Women Clothing, Home - Snapdeal";
	
	@Before

	public void Setup()
	{
		driver=new FirefoxDriver();
		driver.manage().window().maximize();
		baseUrl = "http://www.snapdeal.com/";
		action=new Actions(driver);
	}

	
	@Test
	public void firstSimpleTest() {
		driver.get(baseUrl);
        abc();
		
	}
	
	@Test
	public void failTest()
	{
		boolean a=false;
		Assert.assertTrue(a);
	}

@Step
public void abc()
{
Assert.assertEquals(title, driver.getTitle());
}
	

}
