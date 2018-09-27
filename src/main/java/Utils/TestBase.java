package Utils;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.apache.log4j.Logger;
import org.apache.maven.shared.utils.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class TestBase {

	public static String log4jPropertyFilePath = System.getProperty("user.dir")
			+ "\\src\\main\\resources\\log4j.properties";
	public final static Logger logger = Logger.getLogger(TestBase.class.getName());
	public static ExtentReports extentReport;
	public static ExtentTest eTest;
	public static Properties prop;
	public FileInputStream fis = null;
	static String chromeDriverPath = System.getProperty("user.dir") + "\\drivers\\chromedriver.exe";
	static String gerkoDriverPath = System.getProperty("user.dir") + "\\drivers\\geckodriver.exe";
	static String extentReportPath = System.getProperty("user.dir") + "\\extentReports";
	// static String extentConfigFilePath = System.getProperty("user.dir") +
	// "\\src\\main\\resources\\extent-config.xml";
	// LoginPage loginPage;
	static Log log;

	public static WebDriver driver = null;
	// Latest Element which has been found and used in findAnd... Method
	public static WebElement g_eleLatest = null;
	// Default Max wait time in seconds
	public static int g_nMaxWaitTime = 30;
	// Default Min wait time in seconds
	public static int g_nMinWaitTime = 3;
	// Default No wait time in seconds
	public static int g_nNoWaitTime = 1;
	// Max wait time in seconds for Error messages
	public static int g_nMaxErrMsgWaitTime = 3;
	// Sleep time in milliseconds between steps
	public static int g_nSleepTime = 3000;
	// SeleniumUtil Globals

	public TestBase() {

		prop = new Properties();
		try {
			fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\resources\\config.properties");
			prop.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// This will initialize page factory web elements of the class who extends this
		// class.
		PageFactory.initElements(driver, this);
	}

	public static WebDriver getDriver() {
		return driver;
	}

	public static void tearDown() {

		try {
			Thread.sleep(1000);
			driver.quit();
			logger.info("*********** All test classes run. Extent report generated and put in : " + extentReportPath
					+ " .Quitting browser**********");
		}

		catch (Exception e) {

			logger.info("Unable to perform tear down .... " + e.getMessage());
		}
	}

	public static WebDriver launchBrowser() throws Exception {

		boolean bPageLoaded = false;

		try {

			String browserName = prop.getProperty("browser");
			String appURL = prop.getProperty("appURL");

			if (browserName.equalsIgnoreCase("chrome")) {
				// This chromedriver is enabled to catch browser f12 console JavaScript erros as
				// well. See method collectBrowserJSerrorMessages() in GenericUtilities as well.
				// Download setting
				HashMap<String, Object> hChromePrefsMap = new HashMap<String, Object>();
				hChromePrefsMap.put("profile.default_content_settings.popups", 0);
				ChromeOptions objChromeOptions = new ChromeOptions();
				objChromeOptions.setExperimentalOption("prefs", hChromePrefsMap);
				// To disable message " chrome is being cntrolled by Automated software..."
				objChromeOptions.addArguments("disable-infobars");
				objChromeOptions.addArguments("disable-infobars");
				objChromeOptions.addArguments("--no-sandbox");
				objChromeOptions.addArguments("--allow-insecure-localhost");
				// below code will run chrome in headless mode.
				// options.addArguments("--headless");
				objChromeOptions.addArguments("--disable-gpu");
				// Below code will start chrome in maximized mode
				// options.addArguments("--start-fullscreen");
				// Below code will remove message "Chrome is being controlled by... Software"
				DesiredCapabilities objDesiredCapabilities = DesiredCapabilities.chrome();
				objDesiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				objDesiredCapabilities.setCapability(ChromeOptions.CAPABILITY, objChromeOptions);
				objDesiredCapabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
						UnexpectedAlertBehaviour.ACCEPT);

				LoggingPreferences loggingPreferences = new LoggingPreferences();
				loggingPreferences.enable(LogType.BROWSER, Level.ALL);
				objDesiredCapabilities.setCapability(CapabilityType.LOGGING_PREFS, loggingPreferences);

				System.setProperty("webdriver.chrome.driver", chromeDriverPath);
				driver = new ChromeDriver(objDesiredCapabilities);

				// log.info(" chromedriver is enabled to collect all console JavaScript errors
				// also .");

			} else if (browserName.equalsIgnoreCase("FF")) {
				System.setProperty("webdriver.gecko.driver", gerkoDriverPath);
				driver = new FirefoxDriver();
			}

			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();

			bPageLoaded = waitForPageToLoad();
			Actions action = new Actions(driver);
			action.keyDown(Keys.CONTROL).sendKeys(String.valueOf('\u0030')).perform();
			action.keyUp(Keys.CONTROL).perform();

			if (bPageLoaded) {
				logger.info("Successfully launched the browser");
			} else {
				logger.error("Browser did not launched within max wait time :: " + g_nMaxWaitTime + " Secs.");
			}

			// logger.info("***********Window maximized and browser cookies deleted******");

			// Eventfiring WebDriver setup. Comment below line if not required.
			// driver = new EventFiringWebDriver(driver).register(new WebEventListener());

			driver.manage().timeouts().pageLoadTimeout(Integer.parseInt((String) prop.get("PAGE_LOAD_TIMEOUT")),
					TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(Integer.parseInt((String) prop.get("IMPLICIT_WAIT")),
					TimeUnit.SECONDS);

			logger.info("********* Launching " + browserName + " browser*****************");

			driver.get(appURL);// launch the cover4PM broswer
			logger.info("********* Opening URL-- " + appURL + " *****************");

			return driver;
		} catch (Exception e) {

			logger.error(" Failed to initialize driver and browser setup :" + e.getMessage());
			return null;
		}

	}

	public static boolean waitForPageToLoad(int... nMaxWaiTimeInSec) {
		boolean bResult = false;
		String strJSScript = "return document.readyState";
		String strReadtStausToWait = "complete";
		String strActualStatus = "";
		int nTimer = 0;
		int nMaxWaitTime = -1;

		if (nMaxWaiTimeInSec.length > 0)
			nMaxWaitTime = nMaxWaiTimeInSec[0];
		else
			nMaxWaitTime = g_nMaxWaitTime;

		logger.info("Waiting for page to load... Max Wait Time is :: " + nMaxWaitTime + " Secs.");

		try {
			if (driver == null) {
				logger.error("Driver object is NULL :: Failed to Wait");
				return bResult;
			}

			do {

				JavascriptExecutor jsExec = (JavascriptExecutor) driver;
				strActualStatus = jsExec.executeScript(strJSScript).toString();
				Thread.sleep(1000);
				nTimer++;
				logger.info("Current Page Status :: " + strActualStatus + " :: Waited For " + nTimer + " Seconds");
				if (strActualStatus.trim().equalsIgnoreCase(strReadtStausToWait)) {
					bResult = true;
					break;
				}

			} while (nTimer <= nMaxWaitTime);

			return bResult;

		} catch (Exception ex) {
			logger.error(" Page load failed :" + ex.getLocalizedMessage());
			ex.printStackTrace();
			return bResult;
		}
	}
}