package Utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.LogStatus;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class Utils extends TestBase {

	private static Workbook book;
	private static String filePath = System.getProperty("user.dir") + "\\screenshots\\";
	public static JavascriptExecutor js = (JavascriptExecutor) driver;
	// protected final static Logger log =
	// Logger.getLogger(GenericUtilities.class.getName());

	private static Sheet sheet;
	static Log log = new Log();

	static int windowCount = 0;

	public Utils() {

		PageFactory.initElements(driver, this);
	}

	/*************************************************************
	 * @author :Alok
	 * @Method_Name: acceptAlertPopup.
	 * @Description :This method will accept alert pop up.
	 * 
	 ***********************************************************/
	public static boolean acceptAlertPopup() {

		boolean b = false;
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
			b = true;
		} catch (NoAlertPresentException e) {
			Log.error("Error....." + e.getStackTrace());
			b = false;
		}
		return b;
	}

	/*************************************************************
	 * @author :Alok
	 * @Method_Name: assertIfEqual.
	 * @Description :This method will compare two strings and return true if equal or false if not equal.
	 * @param: strActual-
	 *             Actual string.
	 * @param: strExpected-
	 *             Expected string.
	 ***********************************************************/
	protected static boolean assertIfEqual(String strActual, String strExpected) {
		boolean b = false;
		try {
			Assert.assertEquals(strActual, strExpected);
			Log.info("Actual string: [ " + strActual + " ] is matching with Expected string: [ " + strExpected + " ]");
			b = true;

		} catch (AssertionError e) {
			Log.error("Actual string: [ " + strActual + " ] does not match with Expected string: [ " + strExpected
					+ " ]");
			b = false;
		}
		return b;
	}

	/**
	 * @author Alok Rai
	 * @param Actual
	 * @param Expected
	 * @return Returns true if Actual and Ecpected objects are same. Returns false if Actual and Expected are not same.
	 */

	public static boolean assertTrue(Object Actual, Object Expected) {

		SoftAssert sAssert = new SoftAssert();
		try {
			sAssert.assertTrue(Actual == Expected);

			// sAssert.assertAll();

			System.out.println("Assertion passed : " + Actual + " is equal to " + Expected);

			return true;
		}

		catch (Exception e) {

			System.out.println("Assertion failed : " + Actual + " is not equal to " + Expected);

			return false;
		}

	}

	/*************************************************************
	 * @author :Alok
	 * @Method_Name: collectBrowserJSerrorMessages
	 * @Description : This method will collect all JavaScript error messages present in the opened URL.
	 * 
	 ***********************************************************/
	public static void collectBrowserJSerrorMessages() {

		// Below code will collect all f12 broswe console errors and flush to
		// log file.
		Log.info(" Method to collect Browser console erros called...........");
		LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
		for (LogEntry entry : logEntries) {

			Log.info(entry.getTimestamp() + " " + entry.getLevel() + " " + entry.getMessage());
		}
	}

	/*************************************************************
	 * @author :Alok
	 * @Method_Name: dismissAlertPopup
	 * @Description : This method will dismiss the alert pop up.
	 ***********************************************************/
	public static void dismissAlertPopup() {
		try {
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
		} catch (NoAlertPresentException e) {
			Log.error("Error....." + e.getStackTrace());
		}
	}

	public void setImplicitWait(long timeOutInSeconds, TimeUnit unit) {

		Log.info("Waiting for " + timeOutInSeconds + " seconds.");
		driver.manage().timeouts().implicitlyWait(timeOutInSeconds, unit);

	}

	/*************************************************************
	 * @author :Alok
	 * @Method_Name: getAlertPopupMessage.
	 * @Description :This method will retutn the alert pop up text message
	 * 
	 ***********************************************************/
	public static String getAlertPopupMessage() {
		try {
			Alert alert = driver.switchTo().alert();
			return alert.getText();
		} catch (NoAlertPresentException e) {
			Log.error("Error....." + e.getStackTrace());
			return null;
		}
	}

	/*************************************************************
	 * @author :Alok
	 * @Method_Name: getCurrentSystemDate.
	 * @Description :This method will returm current system date to the user.
	 * 
	 ***********************************************************/
	public static String getCurrentSystemDate() {
		DateFormat df = new SimpleDateFormat("MM/dd/YYYY");
		Date dateobj = new Date();
		return df.format(dateobj);
	}

	/*************************************************************
	 * @author :Alok
	 * @Method_Name: getTestDataFromExcel.
	 * @Description :This method will read data from excel sheet.
	 * @param: strSheetName-
	 *             Excel sheet name.
	 * @param: strSheetPath-
	 *             Absolute path of excel sheet.
	 ***********************************************************/
	public static Object[][] getTestDataFromExcel(String strSheetName, String strSheetPath) {

		Log.info("********* Reading data from sheet: " + strSheetName);
		FileInputStream file = null;
		try {
			file = new FileInputStream(strSheetPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		sheet = book.getSheet(strSheetName);
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
				data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
				System.out.println(data[i][k]);
			}
		}
		return data;
	}

	/*************************************************************
	 * @author :Alok
	 * @Method_Name: switchToFrameByElement
	 * @Description : This method will be used to switch the driver to an iframe using some element.
	 * @param: element-
	 *             Element present in iframe.
	 ***********************************************************/
	public static boolean switchToFrameByElement(WebElement element) {
		boolean b = false;
		try {
			driver.switchTo().frame(element);
			b = true;
		} catch (Exception e) {
			Log.error("Error....." + e.getStackTrace());
			b = false;
		}
		return b;
	}

	/*************************************************************
	 * @author :Alok
	 * @Method_Name: switchToFrameByIndex
	 * @Description : This method will switch to an iframe using its index.
	 * @param: nIframeIndex-
	 *             Index of the iframe.
	 ***********************************************************/
	public static boolean switchToFrameByIndex(WebDriver driver, int nIframeIndex) {

		boolean b = false;
		try {
			driver.switchTo().frame(nIframeIndex);
			b = true;
		} catch (Exception e) {
			Log.error("Error....." + e.getStackTrace());
			b = false;
		}
		return b;
	}

	/*************************************************************
	 * @author :Alok
	 * @Method_Name: switchToFrameByNameOrID
	 * @Description : This method can switch the driver to an iframes using either frame name of id.
	 * @param: strFrameIDOrName-
	 *             iframe id or name.
	 ***********************************************************/
	public static boolean switchToFrameByNameOrID(String strFrameIDOrName) {
		boolean b = false;
		try {
			driver.switchTo().frame(strFrameIDOrName);
			Log.info("Switched to frame: " + strFrameIDOrName);
			b = true;
		} catch (Exception e) {

			Log.error("Unable to switch to iframe ....." + strFrameIDOrName + " " + e.getStackTrace());
			b = false;
		}
		return b;
	}

	/*************************************************************
	 * @author :Alok
	 * @Method_Name: waitForElemewntToBeClickable
	 * @Description : Waits for an element to be clickable.
	 * @param: element-
	 *             element to wait for.
	 ***********************************************************/
	public static boolean waitForElemewntToBeClickable(WebElement element) {
		boolean b = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.elementToBeClickable(element));
		}

		catch (Exception e) {
			Log.error("Unable to wait for element : " + e.getStackTrace());
			b = false;
		}
		return b;
	}

	/*************************************************************
	 * @author :Alok
	 * @Method_Name: FileWrite
	 * @Description : Property File Write
	 * @param: FileName-
	 *             Complete path of file
	 * @param: ObName-
	 *             Object to be write into the file
	 * @param: ObValue
	 *             - Value of the Object written to the file
	 ***********************************************************/
	public void FileWrite(String FileName, String ObName, String ObValue) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

			BufferedWriter out = new BufferedWriter(new FileWriter("c://output.txt"));

			String inputLine = null;
			do {
				inputLine = in.readLine();
				out.write(inputLine);
				out.newLine();
			} while (!inputLine.equalsIgnoreCase("eof"));
			System.out.print("Write Successful");
			out.close();
			in.close();
		} catch (IOException e1) {
			System.out.println("Error during reading/writing");
		}

	}

	public static boolean waitForVisibilityOfElement(WebElement element) {
		boolean b = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOf(element));
			b = true;
		}

		catch (Exception e) {

			Log.error("Visibility of element failed : " + e.getStackTrace());
			b = false;
		}

		return b;
	}

	public void scrollToElement(WebDriver driver, By elem) {
		// waitForElementVisibility(driver, elem);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(elem));
	}

	public void scrollDown(WebDriver driver) {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0,200)");
	}

	public void scrollToBottom(WebDriver driver) throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Thread.sleep(2000);
	}

	/***
	 * @author Alok
	 * @Description This method will wait for a given seconds while polling every 2 seconds.
	 * @param timeOutInSeconds
	 */
	public static void waitForSeconds(int timeOutInSeconds) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
			wait.pollingEvery(2, TimeUnit.SECONDS);
			wait.ignoring(NoSuchElementException.class);
			wait.ignoring(ElementNotVisibleException.class);
			wait.ignoring(StaleElementReferenceException.class);
			wait.ignoring(NoSuchFrameException.class);
			Log.info("Waiting for " + timeOutInSeconds + " seconds");
		} catch (Exception e) {
			Log.error("Unable to wait ... Error : " + e.getStackTrace());
		}
	}

	/***
	 * @author Alok
	 * @Description This method wait for an element for 30 seconds polling every 2 seconds. can be used for those elements which appear/disappear on the UI frequently.
	 * @param element
	 * @return
	 */
	public boolean waitForVisibilityOfEle(WebElement element) {
		boolean bResVal = false;
		try {
			FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(30, TimeUnit.SECONDS)
					.pollingEvery(2, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
			wait.until(ExpectedConditions.visibilityOf(element));
			bResVal = true;
		} catch (Exception e) {
			Log.error("Element " + element + " was not visible :" + e.getStackTrace());
			bResVal = false;
		}
		return bResVal;
	}

	/***
	 * @author Alok
	 * @Description This method will wait for a page to load for a given seconds of time.
	 * @param pageLoadTimeOutInSeconds
	 * @return True- If page is loaded in given time. False- If page is not loaded within given time.
	 */
	public boolean waitForPageToLoad(int pageLoadTimeOutInSeconds) {
		boolean bResultVal = false;
		Log.info("Waiting for page to load within " + pageLoadTimeOutInSeconds + " seconds.");
		try {
			driver.manage().timeouts().pageLoadTimeout(pageLoadTimeOutInSeconds, TimeUnit.SECONDS);
			Log.info("Page loaded successfully");
			bResultVal = true;
		} catch (Exception e) {
			Log.error("Failed to load page within " + pageLoadTimeOutInSeconds + " seconds :" + e.getStackTrace());
			bResultVal = false;
		}
		return bResultVal;

	}

	/**
	 * @author Alok
	 * @Description This method will wait for the visibility of the given element for a given time.
	 * @param element
	 * @param timeOutInSeconds
	 * @return Returns true if element is visible. Returns false if element is not visible.
	 */
	public boolean waitUntilElementVisible(WebElement element, int timeOutInSeconds) {

		boolean bResultVal = false;
		Log.info("Waiting for : " + element.toString() + " to be visible in " + timeOutInSeconds + " seconds");
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
			wait.pollingEvery(2, TimeUnit.SECONDS);
			wait.ignoring(NoSuchElementException.class);
			wait.ignoring(ElementNotVisibleException.class);
			wait.ignoring(StaleElementReferenceException.class);
			wait.ignoring(NoSuchFrameException.class);
			wait.until(ExpectedConditions.visibilityOf(element));
			Log.info(" Element " + element + " is visible");
			bResultVal = true;
		} catch (Exception e) {
			Log.error(" Error occured while waiting for element :" + element + " " + e.getStackTrace());
			bResultVal = true;
		}
		return bResultVal;
	}

	/***
	 * @author Alok
	 * @Description This method will press ENTER key OR will thor exception if error occurs.
	 * 
	 */
	public void pressEnter() {
		try {
			Thread.sleep(2000);
			Actions ac = new Actions(driver);
			ac.sendKeys(Keys.ENTER).build().perform();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	/***
	 * @author Alok
	 * @Description This method will generate a random number
	 * @return
	 */
	public static String generateNumber() {
		long i = (long) (Math.random() * 100000 + 3333300000L);
		return String.valueOf(i);
	}

	public static void setClipboardData(String string) {
		StringSelection stringSelection = new StringSelection(string);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
	}

	/***
	 * @author Alok
	 * @Description This method will switch the driver to new window from the old one.
	 * @throws InterruptedException
	 */
	public static boolean waitForNewWindowAndSwitchToIt() throws InterruptedException {
		String cHandle = driver.getWindowHandle();
		String newWindowHandle = null;
		boolean bResVal = false;
		Set<String> allWindowHandles = driver.getWindowHandles();
		try {
			// Wait for 20 seconds for the new window and throw exception if not found
			for (int i = 0; i < 20; i++) {
				if (allWindowHandles.size() > 1) {
					for (String allHandlers : allWindowHandles) {
						if (!allHandlers.equals(cHandle))
							newWindowHandle = allHandlers;
					}
					driver.switchTo().window(newWindowHandle);
					bResVal = true;
					break;
				}
			}
			if (cHandle == newWindowHandle) {
				bResVal = false;
				throw new RuntimeException("Time out - No window found");
			}
		} catch (RuntimeException e) {
			Log.error(" No such window to switch to :" + e.getStackTrace());
			bResVal = false;
		}
		return bResVal;
	}

	/***
	 * @author Alok
	 * @Description This method will return the current window title;
	 * @param driver
	 * @return
	 */
	public static String getCurrentWindowTitle(WebDriver driver) {
		return driver.getTitle();
	}

	// below code will perform broken links test. All links which are not
	// working
	// will be caught here.

	/***
	 * @author Alok
	 * @Description This method will perform broken links test. All URL links which are not working will be caught here.
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void brokenLinksTest() throws MalformedURLException, IOException, InterruptedException {
		
		Log.info(" Broken links test started for URL : " + driver.getCurrentUrl());
		List<WebElement> list = driver.findElements(By.tagName("a"));
		List<WebElement> ActiveLinks = new ArrayList<WebElement>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getAttribute("href") != null) {
				ActiveLinks.add(list.get(i));
			}
		}
		Log.info(" Active links across the whole application are: " + ActiveLinks.size());
		for (int j = 0; j < ActiveLinks.size(); j++) {
			try {
				HttpURLConnection connection = (HttpURLConnection) new URL(ActiveLinks.get(j).getAttribute("href"))
						.openConnection();
				connection.connect();
				connection.disconnect();
				Log.info("Opening URL: " + ActiveLinks.get(j).getAttribute("href") + ".....>"
						+ connection.getResponseMessage());
				Log.pass("Opening URL: " + ActiveLinks.get(j).getAttribute("href") + ".....>"
						+ connection.getResponseMessage());
			}
			catch (Exception e) {
				Log.fail(".......Broken links test failed for URL : " + ActiveLinks.get(j).getAttribute("href") + " \n "
						+ e + " ..........");
				eTest.log(LogStatus.FAIL, ".......Broken links test failed for URL : "
						+ ActiveLinks.get(j).getAttribute("href") + " \n " + e + " ..........");
			}
		}
	}

	/***
	 * @author Alok
	 * @Description This method will click on a given element.
	 * @param element
	 * @return True- If element was found and clicked successfully. False- If element was not clicked due to exception.
	 */
	public static boolean click(WebElement element) {
		boolean b = false;
		try {
			scrollIntoViewbyJS(element);
			waitForElemewntToBeClickable(element);
			highlighElement(element);
			element.click();
			Log.info("Clicked element-- " + element.toString());
			b = true;
		} catch (Exception E) {
			E.printStackTrace();
			Log.error("Unable to click element :" + element + E.getStackTrace());
			b = false;
		}
		return b;
	}

	/***
	 * @author Alok
	 * @description This method will show a custom alert to the user with the given text and alert will be closed after 2 seconds.
	 * @param alertMessage
	 * @throws InterruptedException
	 */
	public static void customAlertByJS(String alertMessage) throws InterruptedException {
		js.executeScript("alert('" + alertMessage + "')");
		Thread.sleep(2000);
		driver.switchTo().alert().accept();
	}

	/***
	 * @author Alok
	 * @description This method will draw blue border around a given element.
	 * @param element
	 */
	public void drawborder(WebElement element) {
		js.executeScript("arguments[o].style.border='3px solid blue'", element);

	}

	/***
	 * @author Alok
	 * @description This method will get text of an element.
	 * @param element
	 * @return
	 */
	public static String getText(WebElement element) {
		try {
			if (element == null) {
				Log.error("Element is null.... unable to get text");
				return null;
			} else {
				waitForVisibilityOfElement(element);
				element.getText();
				Log.info("Element text is--" + element.getText());
				return element.getText();
			}
		} catch (Exception e) {
			Log.error("Unable to get text of element : " + element.toString() + " " + e.getStackTrace());
			return null;
		}
	}

	/***
	 * @author Alok
	 * @description This method will highlight the given element in gold color.
	 * @param element
	 */
	public static void highlighElement(WebElement element) {
		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.background='gold'", element);

	}

	/***
	 * @author Alok
	 * @description This method will check whether and element is displayed on UI or not.
	 * @param element
	 * @return True- If element is displayed. False- If element is not displayed on UI.
	 */
	public boolean isElementDisplayed(WebElement element) {

		return element.isDisplayed();

	}

	/***
	 * @author Alok
	 * @description This method will assert whether an element is displayed on screen or not.
	 * @param element
	 * @return True- If element is displayed. False- If element is not displayed.
	 */
	public static boolean assertDisplayed(WebElement element) {
		boolean bResVal = false;
		try {
			Utils.highlighElement(element);
			Assert.assertTrue(element.isDisplayed());
			String fullString = element.toString();
			String subString = fullString.substring(fullString.indexOf("->") + 1);
			Log.info(subString.trim() + " is displayed on screen");
			bResVal = true;
		} catch (Exception e) {
			Log.error(element + " is not displayed on screen " + e.getLocalizedMessage());
			bResVal = false;
		}
		return bResVal;
	}

	/***
	 * @author Alok
	 * @description This method will check whether a given file is downloaded into a given directory or not.
	 * @param downloadPath,fileName.
	 * @param fileName
	 * @return True- If file is downloaded. False- If file is not downloaded.
	 */
	public static boolean isFileDownloaded(String downloadPath, String fileName) {
		boolean b = false;
		try {

			File dir = new File(downloadPath);
			File[] dir_contents = dir.listFiles();

			for (int i = 0; i < dir_contents.length; i++) {
				if (dir_contents[i].getName().contains(fileName) == true) {
					b = true;

					Log.info("File " + fileName + " downloaded successfully");
					break;
				}

			}
			b = false;

			Log.error("File " + fileName + " download failed");

		} catch (Exception e) {

			b = false;

			Log.error("File " + fileName + " download failed");
		}

		return b;
	}

	/***
	 * @author Alok
	 * @description Thi smethod will click on a given element using javascript.
	 * @param driver
	 * @param element
	 */
	public static void clickByJS(WebElement element) {
		try {
			if (element.isEnabled() && element.isDisplayed()) {
				((JavascriptExecutor) driver).executeScript("arguements[0].click(, arg1);", element);
			}
		} catch (Exception e) {
			Log.error("Error....." + e.getStackTrace());
		}
	}

	/***
	 * @author Alok
	 * @descriptiopn Thi smethod will scroll to a given element using javascript.
	 * @param element
	 */
	public static void scrollIntoViewbyJS(WebElement element) {

		js.executeScript("arguments[0].scrollIntoView(true);", element);
		Log.info(" Scrolled to...." + element);
	}

	public static void scrollIntoViewAndClick(WebElement element) throws Exception {
		try {
			js.executeScript("arguments[0].scrollIntoView(true)", element);
			Thread.sleep(500);
			element.click();
			Log.info(" Scrolled to  " + element + " and clicked");
		}

		catch (Exception e) {

			Log.error(" Unable to click " + element + " " + e);
		}

	}

	public static void scrollToElement(WebDriver driver, WebElement element) {

		Point location = element.getLocation();
		js.executeScript("javascript:window.scrollBy(0," + location.y + ")");

	}

	public static boolean selectByIndex(int index, WebElement element) {
		boolean b = false;
		try {
			Select s = new Select(element);
			s.selectByIndex(index);
			Log.info("Selected element by index :" + index);
		} catch (Exception e) {

			Log.error("Unable to select the element by index : " + element.toString());
			b = false;
		}
		return b;
	}

	public static boolean selectByName(String name, WebElement element) {
		boolean b = false;
		try {
			Select s = new Select(element);
			s.selectByVisibleText(name);
			Log.info("Selected element --" + name);
			b = true;
		}

		catch (Exception e) {

			Log.error("Unable to select the element : " + element.toString());
			b = false;
		}
		return b;
	}

	/**
	 * @author Alok
	 * @Description This method will run auto IT script present at a given path.
	 * @param strAutoItExePath
	 * @return true if script is run successfully. False if script threw some error.
	 */
	public static boolean runAutoITScript(String strAutoItExePath) {
		boolean bResVal = false;
		try {
			Thread.sleep(2000);
			Runtime.getRuntime().exec(strAutoItExePath);
			Log.info("Auto IT script run successfully ");
			bResVal = true;
		} catch (Exception e) {
			Log.error("Failed to run auto IT script : " + strAutoItExePath + " " + e.getStackTrace());
			bResVal = false;
		}
		return bResVal;
	}
/***
 * @author Alok
 * @description This method will clear the text present in an input field.
 * @param element
 * @return True- If clear field is successfull. False- If clear field process encounters some exception.
 */
	public static boolean clearField(WebElement element) {

		try {
			scrollIntoViewbyJS(element);
			waitForVisibilityOfElement(element);
			element.click();
			element.clear();
			Log.info("Cleared field " + element.toString() + " " + "successfully");
			return true;
		}

		catch (Exception e) {
			Log.error("Unable to clear field : " + element.toString() + " " + e.getStackTrace());
			return false;
		}

	}
	/***
	 * @author Alok
	 * @description This method will upload a selected file using robot class.To be used for uploading files.
	 * @param path
	 * @return True- If upload is successfull. False- If upload process encounters some exception.
	 * @throws AWTException
	 */
	public static boolean selectUploadFilebyRobot(String path) throws AWTException {

		try {
			Log.info(" Selecting file using robot class");
			Robot robot = new Robot();
			StringSelection selection = new StringSelection(path);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(selection, selection);

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);

			Log.info("File selected using Robot from the path :- " + path.toString().toUpperCase());
			return true;
		}

		catch (Exception e) {

			Log.error("Unable to upload file ...." + e.getStackTrace());
			return false;
		}

	}
/**
 * @author Alok
 * @deprecated
 * @description This method should no longer be used.
 * @param xpath
 * @param Data
 * @throws Exception
 */
	public static void sendDataTOLastOfSameElementPresentMultipleTimes(String xpath, String Data) throws Exception {

		List<WebElement> elements = driver.findElements(By.xpath(xpath));

		int count = elements.size();

		WebElement element = driver.findElement(By.xpath(xpath + "[position()=" + count + "]"));

		sendKeys(element, Data);

	}

	/**
	 * @author Alok Rai
	 * @param element
	 *            - Element to which data needs to be entered.
	 * @param keyword-
	 *            The data which needs to be sent to the element.
	 * @throws Exception-
	 *             Throws exception when any of the step fails.
	 */
	public static boolean sendKeys(WebElement element, String keyword) throws Exception {

		boolean b = false;

		try {
			if (element == null) {

				Log.error(" Element is null.......... ");
				return b;
			} else {
				scrollIntoViewbyJS(element);
				waitForVisibilityOfElement(element);
				element.click();
				Thread.sleep(1000);
				element.clear();
				highlighElement(element);
				element.sendKeys(keyword);
				element.sendKeys(Keys.TAB);

				String fullString = element.toString();
				String subString = fullString.substring(fullString.indexOf("->") + 1);

				Log.info(" Send data [ " + keyword + " ] to element : " + subString.trim());
				b = true;
			}
		} catch (Exception e) {
			Log.error("Unable to send data to element : " + e.getLocalizedMessage());
			b = false;
		}
		return b;
	}
	/***
	 * @author Alok
	 * @description This method will send given string to a given element using actions class. To be used when normal selenium sendkeys is not working.
	 * @param element
	 * @param keyword
	 * @return
	 */
	public static boolean sendkeysActions(WebElement element, String keyword) {
		boolean b = false;
		try {
			Actions action = new Actions(driver);
			action.moveToElement(element);
			action.click(element);
			// element.clear();
			action.sendKeys(keyword);
			action.build().perform();

			String fullString = element.toString();
			String subString = fullString.substring(fullString.indexOf("->") + 1);

			Log.info(" Send data [ " + keyword + " ] to element : " + subString.trim());
			b = true;
		}

		catch (Exception e) {

			Log.error("Unable to send data to field : " + element.toString());
			b = false;
		}
		return b;
	}

	/***
	 * @author Alok
	 * @description This method will switch the driver default content.
	 * @return
	 */
	public static boolean switchToDefaultContent() {
		try {
			driver.switchTo().defaultContent();
			Log.info(" Switching to default window ");
			return true;
		} catch (Exception e) {
			Log.error("Unable to switch to default content :" + e.getStackTrace());
			return false;
		}
	}

	/***
	 * @author Alok
	 * @description This method will swicth the driver focus to a given frame by its id.
	 * @param id
	 * @return True- If switch is successfull. False- If switch is not successfull.
	 */
	public boolean switchToFrameById(String id) {

		boolean b = false;
		try {

			driver.switchTo().frame(id);
			Log.info("Switching to frame : " + id);
			b = true;

		} catch (Exception e) {
			Log.error("Error....." + e.getStackTrace());
			b = false;
		}

		return b;
	}

	/***
	 * @author Alok
	 * @description This method will switch the driver focus to most recently opened browser tab/window.Useful where a test case execution causes various links to be clicked and tabs to be opened as a consequence.
	 * @return True- If switch is succesful. False- If switch is not successful.
	 */
	public static boolean switchToNextTab() {

		boolean bResVal = false;
		try {
			Set<String> windows = driver.getWindowHandles();
			Iterator<String> itr = windows.iterator();
			ArrayList<String> ids = new ArrayList<String>();
			while (itr.hasNext()) {
				ids.add(itr.next());
			}
			windowCount++;
			driver.switchTo().window(ids.get(windowCount));
			Log.info("Switched to tab/window : " + ids.get(windowCount));
			bResVal = true;
		} catch (Exception e) {
			Log.error("unable to switch to new tab/window : " + e.getStackTrace());
			bResVal = false;
		}
		return bResVal;
	}

	/***
	 * @author Alok
	 * @description This method will take screenshot of the screen only for a given height and width for a given element.
	 * @param element
	 * @throws IOException
	 */
	public static void takePartialScreenShot(WebElement element) throws IOException {

		String screenShot = System.getProperty("user.dir") + "\\screenShot.png";

		File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		Point p = element.getLocation();

		int width = element.getSize().getWidth();
		int height = element.getSize().getHeight();

		BufferedImage img = ImageIO.read(screen);

		BufferedImage dest = img.getSubimage(p.getX(), p.getY(), width, height);

		ImageIO.write(dest, "png", screen);

		FileUtils.copyFile(screen, new File(screenShot));
	}

	/***
	 * @author Alok
	 * @description This method will take screenshot when called.Usually used by extent report for adding screenshots to report after a test method is executed.
	 * @param methodName
	 * @return True- If screenshot is taken and saved successfully.False- If some exception occurs while taking and copying screenshot..
	 */
	public static String takeScreenShot(String methodName) {

		String DestFileDir = null;
		try {
			driver.manage().window().maximize();
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
			String destFileName = formater.format(calendar.getTime());
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			DestFileDir = filePath + methodName + destFileName + ".png";

			FileUtils.copyFile(scrFile, new File(DestFileDir));
			Log.info("***Placed screen shot in " + filePath + " ***");
		} catch (IOException e) {
			Log.error("Unable to take screenshot :" + e.getStackTrace());
		}
		return DestFileDir;
	}

	/***
	 * @author Alok
	 * @description This method will take screenshot of pages where there is vertical scroll bar or page height is such that in one screen it will not fit. Hence this method will keep scrolling the page downwards and taking screenshot.e.g shopping websites like amazon and flipkart.
	 * @param methodName
	 *            - Current executing method name.
	 * @return 
	 * @throws IOException
	 * @return True- If screenshot is successfully taken and copied to goven directory. False- If some exception occurs during the entire process.
	 */
	public static boolean takeScreenShotByAShotUtility(String methodName) throws IOException {
		boolean bResVal = false;
		try {
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
			String destFileNameTime = formater.format(calendar.getTime());
			String DestFileName = methodName + destFileNameTime + ".png";
			// screen will be scrolled and captured
			Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100))
					.takeScreenshot(driver);
			// To save the screenshot in desired location
			ImageIO.write(screenshot.getImage(), "PNG",
					new File(System.getProperty("user.dir") + "\\screenshots\\" + DestFileName));
			Log.info("***Placed screen shot in " + filePath + " ***");
			bResVal = true;
		} catch (Exception e) {
			Log.error("Unable to take screenshot :" + e.getStackTrace());
			bResVal = false;
		}
		return bResVal;

	}

}