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
	private String sheetName="Sheet1";
	private int rowNumber=2;
	private String pinCode="Pincode";
	private String ProductName="ProductName";
	private String Quantity="Quantity";
	private StringBuffer pinCodeFromExcel;
	private String ProductNameFromExcel;
	private StringBuffer QuantityFromExcel;
	private StringBuffer sb;
	private String snapdealTitle="Online Shopping in India at Snapdeal - Buy Books, Mobiles, Laptops, Apparel, Watches, Footwear, Recharge, Bill Payments & More";
	

	


	
	@Test
	public void firstSimpleTest() {
		boolean a=true;
		assertTrue("Result not equals to 4", 2 * 2 == 4);
		Assert.assertTrue(a);
	}


	

}
