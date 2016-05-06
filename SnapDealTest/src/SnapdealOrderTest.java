import java.io.File;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import resources.ExcelReader;


public class SnapdealOrderTest {
	WebDriver driver;
	String baseUrl;
	ExcelReader excelReader;
	static File Filepth=new File(System.getProperty("user.dir")+"\\src\\resources\\SnapdealData.xls");
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
	

	@BeforeClass
	public void getExcelReader()
	{
		excelReader=new ExcelReader(Filepth);
		sb=new StringBuffer(excelReader.getCellDataByColumnName(sheetName,pinCode, rowNumber));
		pinCodeFromExcel=sb.delete(sb.length()-2,sb.length());
		System.out.println("Pincode from Excel sheet---->>> "+pinCodeFromExcel);
		ProductNameFromExcel=excelReader.getCellDataByColumnName(sheetName,ProductName, rowNumber);
		System.out.println("Product Name from Excel sheet---->>> "+ProductNameFromExcel);
		sb=new StringBuffer(excelReader.getCellDataByColumnName(sheetName,Quantity, rowNumber));
		QuantityFromExcel=sb.delete(sb.length()-2,sb.length());
		System.out.println("Order quantity from Excel sheet---->>> "+QuantityFromExcel);
	}


	@BeforeTest

	public void Setup()
	{
		driver=new FirefoxDriver();
		driver.manage().window().maximize();
		baseUrl = "http://www.snapdeal.com/";
		action=new Actions(driver);
	}

	@Test
	public void test()
	{

		driver.get(baseUrl);

		(new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='pincode-salience-check']")));


		driver.findElement(By.xpath("//input[@id='pincode-salience-check']")).sendKeys(pinCodeFromExcel);
		driver.findElement(By.xpath("//span[contains(.,'SUBMIT')]")).click();
		Assert.assertEquals(snapdealTitle, driver.getTitle());

		dailyNeeds=driver.findElement(By.xpath("//span[@class='catText'][contains(.,'Daily Needs')]"));
		action.moveToElement(dailyNeeds).build().perform();
		driver.findElement(By.xpath("//span[@class='linkTest'][contains(.,'Family Nutrition')]")).click();
		driver.findElement(By.xpath("//img[@class='product-image'][@title='"+ProductNameFromExcel+"']")).click();
		System.out.println("Maximum order is 3 to be applied.........looping the quantity for order field... ");
     	for(int quantNum=1;quantNum<Integer.parseInt(QuantityFromExcel.toString());quantNum++)
		{
			driver.findElement(By.xpath("//div[@id='pdpFMCGBuyBlock']//span[@class='pdp-quantity pdp-quantity-up sd-icon sd-icon-collapse-arrow']")).click();
		}
     	System.out.println("Verifying Pincode is still that what was selected....");
		Assert.assertEquals(driver.findElement(By.xpath("//strong[@id='pincode-val']")).getText(),pinCodeFromExcel.toString());
		driver.findElement(By.xpath("//div[@id='buy-button-id']/span")).click();	
	}





	@AfterTest
	public void quitDriver()
	{
		driver.quit();	
	}

}
