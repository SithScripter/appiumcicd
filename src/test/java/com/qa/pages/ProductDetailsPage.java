package com.qa.pages;

import org.openqa.selenium.WebElement;

import com.qa.BaseTest;
import com.qa.MenuPage;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductDetailsPage extends MenuPage {
	
	@AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[1]")
		private WebElement SLBTitle;
	
	@AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[2]")
		private WebElement SLBTxt;
	
	@AndroidFindBy (accessibility = "test-Price")
		private WebElement SLBPrice;
	
	@AndroidFindBy (accessibility = "test-BACK TO PRODUCTS")
		private WebElement backToProductsBtn;
	
	public String getSLBTitle() {
		String title = getText(SLBTitle, "title is - ");
		return title;
	}

	public String getSLBTxt() {
		String txt = getText(SLBTxt, "txt is - ");
		return txt;
	}
	
	public String getSLBPrice() { 
		String price = getText(SLBPrice,"price is - ");
		return price; }
	
	public ProductDetailsPage scrollToSLBPrice() {
		scrollToElement();
		return this;
	}

	public ProductsPage pressProductsBckBtn() {
	click(backToProductsBtn);
	return new ProductsPage();
}




}