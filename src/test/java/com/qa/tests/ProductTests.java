package com.qa.tests;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductDetailsPage;
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

public class ProductTests extends BaseTest {
	LoginPage loginPage;
	ProductsPage productsPage;
	SettingsPage settingsPage;
	ProductDetailsPage productDetailsPage;
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
	public void validateProductOnProductsPage()  {
		SoftAssert sa =new SoftAssert();
		productsPage = loginPage.login(loginusers.getJSONObject("validUser").getString("username"), 
				loginusers.getJSONObject("validUser").getString("userpassword"));
		
		String SLBTitle = productsPage.getSLBTitle();
		sa.assertEquals(SLBTitle, getStrings().get("product_page_slb_title"));
		
		String SLBPrice = productsPage.getSLBPrice();
		sa.assertEquals(SLBPrice, getStrings().get("product_page_slb_price"));
		
		settingsPage = productsPage.pressSettingsBtn();
		loginPage = settingsPage.pressLogoutLink();
		
		sa.assertAll(); 
	}
	
	@Test
	public void validateProductOnProductDetailsPage()  {
		SoftAssert sa =new SoftAssert();
		productsPage = loginPage.login(loginusers.getJSONObject("validUser").getString("username"), 
				loginusers.getJSONObject("validUser").getString("userpassword"));
		
		productDetailsPage = productsPage.pressSLBTitle();
		
		String SLBTitle = productDetailsPage.getSLBTitle();
		sa.assertEquals(SLBTitle, getStrings().get("product_details_page_slb_title"));
		
		String SLBTxt = productDetailsPage.getSLBTxt();
		sa.assertEquals(SLBTxt, getStrings().get("product_details_page_slb_description"));
		
		productDetailsPage.scrollToSLBPrice();
		
		String SLBPrice = productDetailsPage.getSLBPrice();
		sa.assertEquals(SLBPrice, getStrings().get("product_details_page_slb_price"));
		
		productsPage = productDetailsPage.pressProductsBckBtn();
		
		settingsPage = productsPage.pressSettingsBtn();
		loginPage = settingsPage.pressLogoutLink();
		
		sa.assertAll(); 
	}

}
