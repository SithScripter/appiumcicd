package com.qa.pages;

import org.openqa.selenium.WebElement;

import com.qa.BaseTest;
import com.qa.MenuPage;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductsPage extends MenuPage {
	
	@AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Cart drop zone\"]/android.view.ViewGroup/android.widget.TextView") 
	private WebElement productTitleText;
	
	@AndroidFindBy (xpath = "(//android.widget.TextView[@content-desc=\"test-Item title\"])[1]")
	private WebElement SLBTitle;
	
	@AndroidFindBy (xpath = "(//android.widget.TextView[@content-desc=\"test-Price\"])[1]")
	private WebElement SLBPrice;
	

//lets create methods of login page

public String getTitle() {
	String title = getText(productTitleText, "product page title is - ");
	return title;
}

public String getSLBTitle() {
	String title = getText(SLBTitle, "title is - ");
	return title;
}

public String getSLBPrice() {
	String price = getText(SLBPrice, "price is - ");
	return price;
}

public ProductDetailsPage pressSLBTitle() {
	click(SLBTitle, "press SLB tile link");
	return new ProductDetailsPage();
}




}