package com.qa.pages;

import org.openqa.selenium.WebElement;
import com.qa.BaseTest;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class LoginPage extends BaseTest {
	
	@AndroidFindBy (accessibility = "test-Username")
	private WebElement userNameFld;
	@AndroidFindBy (accessibility = "test-Password")
	private WebElement userPasswordFld;
	@AndroidFindBy (accessibility = "test-LOGIN")
	private WebElement loginButton;
	@AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView") 
	private WebElement errorTxt;


//lets create methods of login page

public LoginPage enterUserName(String userName) {
//	clear(userNameFld);
	sendKeys(userNameFld, userName, "login with " + userName);
	return this;
}

public LoginPage enterPassword(String password) {
//	clear(userPasswordFld);
	sendKeys(userPasswordFld, password, "login with " + password);
	return this;
}

public ProductsPage pressLoginBtn() {
	click(loginButton, "Press Login Button");
	return new ProductsPage();
}

public ProductsPage login(String username, String password) {
	enterUserName(username);
	enterPassword(password);
	return pressLoginBtn();
	
}

public String getErrTxt() {
	String err = getText(errorTxt,"error text is - ");
	return err;
	
}

}