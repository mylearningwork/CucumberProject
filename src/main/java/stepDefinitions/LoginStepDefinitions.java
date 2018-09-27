/**
 * 
 */
package stepDefinitions;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Utils.Log;
import Utils.TestBase;
import Utils.Utils;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class LoginStepDefinitions extends TestBase {

	@FindBy(xpath = "//*[@name=\"username\"]")
	WebElement userNameEle;
	@FindBy(xpath = "//*[@name=\"password\"]")
	WebElement passwordEle;
	@FindBy(xpath="//*[@value=\"Login\"]") WebElement btnSubmit;
	
	 //By xpath="//*[@name=\"username\"]"= WebElement username;

	public LoginStepDefinitions () {
		
		super();
		
	}
	
	@Before
	public void setUp() throws Exception {

		launchBrowser();
	}

	@Given("^user is on base url$")
	public void user_is_on_base_url() throws Exception {
		
		Log.info("Successfully Opened link");

	}

	@Then("^user enters user id$")
	public void user_enters_user_id() throws Exception {
		
		Utils.sendKeys(userNameEle, "FRIEND9831");
		driver.findElement(By.xpath("//*[@name=\"username\"]")).sendKeys("FRIEND9831");
	}

	@Then("^user enters password$")
	public void user_enters_password() throws Throwable {
		
		Utils.sendKeys(passwordEle, "Lotus12!@");
	}

	@Then("^then clicks on login button$")
	public void then_clicks_on_login_button() throws Throwable {
		
		Utils.click(btnSubmit);
	}

	@After
	public void tearDownTest() {

		tearDown();

	}

}
