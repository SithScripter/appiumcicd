package com.qa.tests;

import org.testng.annotations.Test;
import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductsPage;
import com.qa.pages.SettingsPage;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import java.io.InputStream;
import java.lang.reflect.Method;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class LoginTests extends BaseTest {
	LoginPage loginPage;
	ProductsPage productsPage;
	SettingsPage settingsPage;
	JSONObject loginusers;

	@BeforeClass
	public void beforeClass() throws Exception {
		InputStream dataIs = null;
		try {
			String dataFileName = "data/loginUsers.json";
			dataIs = getClass().getClassLoader().getResourceAsStream(dataFileName);
			JSONTokener tokener = new JSONTokener(dataIs);
			loginusers = new JSONObject(tokener);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally {
			if(dataIs != null) {
				dataIs.close();
			}
		}
		closeApp();
		launchApp();
	}

	@AfterClass
	public void afterClass() {
		settingsPage = productsPage.pressSettingsBtn();
		loginPage = settingsPage.pressLogoutLink();
	}

	@BeforeMethod
	public void beforeMethod(Method m) {
		System.out.println("\n" + "******** starting test: " + m.getName() + "*******" + "\n");
		loginPage = new LoginPage();
	}

	@AfterMethod
	public void afterMethod() {
	}

	@Test
	public void invalidUsername()  {
		loginPage.enterUserName(loginusers.getJSONObject("invalidUserName").getString("username"));
		loginPage.enterPassword(loginusers.getJSONObject("invalidUserName").getString("userpassword"));
		loginPage.pressLoginBtn();

		String actualErrorTxt = loginPage.getErrTxt() + "fffdfff";
		String expectedErrorTxt = getStrings().get("err_invalid_username_or_password");
		System.out.println("Error message is: " + actualErrorTxt + "\n" + "Expected error is: " + expectedErrorTxt);

		Assert.assertEquals(actualErrorTxt, expectedErrorTxt);  
	}

	@Test
	public void invalidUserPassword() {
		loginPage.enterUserName(loginusers.getJSONObject("invalidUserPassword").getString("username"));
		loginPage.enterPassword(loginusers.getJSONObject("invalidUserPassword").getString("userpassword"));
		loginPage.pressLoginBtn();

		String actualErrorTxt = loginPage.getErrTxt();
		String expectedErrorTxt = getStrings().get("err_invalid_username_or_password");
		System.out.println("Error message is: " + actualErrorTxt + "\n" + "Expected error is: " + expectedErrorTxt);

		Assert.assertEquals(actualErrorTxt, expectedErrorTxt);
		 
	}

	@Test
	public void successfulLogin() {
		loginPage.enterUserName(loginusers.getJSONObject("validUser").getString("username"));
		loginPage.enterPassword(loginusers.getJSONObject("validUser").getString("userpassword"));
		productsPage = loginPage.pressLoginBtn();

		String actualProductText = productsPage.getTitle();
		String expectedProductTitle = getStrings().get("product_title");

		System.out.println("Product Title is: " + actualProductText + "\n" + "Expected text is: " + expectedProductTitle);

		Assert.assertEquals(actualProductText, expectedProductTitle);  
	}





}
