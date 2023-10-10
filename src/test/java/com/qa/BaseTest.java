package com.qa;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.Status;
import com.qa.reports.ExtentReport;
import com.qa.utils.TestUtils;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;

public class BaseTest {
	protected static ThreadLocal <AppiumDriver> driver = new ThreadLocal<AppiumDriver>();
	protected static ThreadLocal <Properties> props = new ThreadLocal<Properties>();
	protected static ThreadLocal <HashMap<String, String>> strings = new ThreadLocal<HashMap<String, String>>();
	protected static ThreadLocal <String> platform = new ThreadLocal<String>();
	protected static ThreadLocal <String> dateTime = new ThreadLocal<String>();
	protected static ThreadLocal <String> deviceName = new ThreadLocal<String>();
	TestUtils utils = new TestUtils();
	private static AppiumDriverLocalService server;
	private static final Logger log = LogManager.getLogger(BaseTest.class.getName());
	
	 public AppiumDriver getDriver() {
		  return driver.get();
	  }
	 
	 public void setDriver(AppiumDriver driver2) {
		 driver.set(driver2);
	 }
	 
	 public Properties getProps() {
		  return props.get();
	  }
	  
	  public void setProps(Properties props2) {
		  props.set(props2);
	  }
	  
	  public HashMap<String, String> getStrings() {
		  return strings.get();
	  }
	  
	  public void setStrings(HashMap<String, String> strings2) {
		  strings.set(strings2);
	  }
	  
	  public String getPlatform() {
		  return platform.get();
	  }
	  
	  public void setPlatform(String platform2) {
		  platform.set(platform2);
	  }
	  
	  public String getDateTime() {
		  return dateTime.get();
	  }
	  
	  public void setDateTime(String dateTime2) {
		  dateTime.set(dateTime2);
	  }
	  
	  public String getDeviceName() {
		  return deviceName.get();
	  }
	  
	  public void setDeviceName(String deviceName2) {
		  deviceName.set(deviceName2);
	  }
	
	public BaseTest() {
		PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
	}
	
	@BeforeSuite
	public void beforeSuite() {
		server = getAppiumServerDefault();
		server.start();
		server.clearOutPutStreams(); //this will not show server logs in console
		System.out.println("Appium server started");
	}
	
	@AfterSuite (alwaysRun = true)
	public void afterSuite() {
		  if(server.isRunning()){
			  server.stop();
			 System.out.println("Appium Server Stopped");;
		  }
	}
	
	public AppiumDriverLocalService getAppiumServerDefault() {
		return AppiumDriverLocalService.buildDefaultService();
	}
  
  @Parameters({"platformName","deviceName","udid","systemPort","chromeDriverPort"})
  @BeforeTest
  public void beforeTest(String platformName,String deviceName, String udid,String systemPort,String chromeDriverPort) throws Exception {
	  
	  log.info("This is info message");
	  log.error("This is error message");
	  log.debug("This is debug message");
	  log.warn("This is warn message");
	  
//	  setDateTime(utils.dateTime());
	  URL url;
	  setPlatform(platformName);
	  InputStream inputStream = null;
	  InputStream stringsIs = null;
	  Properties props = new Properties();
	  AppiumDriver driver = null;
	  
	  try {
		  props =new Properties();
		  String propFileName = "config.properties";
		  String xmlFileName = "strings/strings.xml";
		  
		  //loading property file
		  inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		  props.load(inputStream);
		  setProps(props);
		  
		  stringsIs = getClass().getClassLoader().getResourceAsStream(xmlFileName);
		  utils = new TestUtils();
		  setStrings(utils.parseStringXML(stringsIs));
		  
		  	DesiredCapabilities caps = new DesiredCapabilities();
		  	caps.setCapability("platformName", platformName);
			caps.setCapability("deviceName", deviceName);
			caps.setCapability("udid", udid);
			caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 300);
			
			switch(platformName) {
			case "Android":
				
				caps.setCapability("automationName", props.getProperty("androidAutomationName"));	
				caps.setCapability("appPackage", props.getProperty("androidAppPackage"));
				caps.setCapability("appActivity", props.getProperty("androidAppActivity"));
				caps.setCapability("avd", deviceName);
				caps.setCapability("avdLaunchTimeout", 12000);
				caps.setCapability("autoGrantPermissions",true);
				caps.setCapability("systemPort", systemPort.toString());
				caps.setCapability("chromeDriverPort", chromeDriverPort.toString());
	/*		  	
				caps.setCapability("platformName", props.getProperty("platformName"));
//			  	caps.setCapability(MobileCapabilityType.PLATFORM_NAME,platformName);
//				caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, 13);
				caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 300);
				caps.setCapability("deviceName", props.getProperty("deviceName"));
//				caps.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
				caps.setCapability("automationName",props.getProperty("androidAutomationName"));
//				caps.setCapability(MobileCapabilityType.AUTOMATION_NAME,props.getProperty("androidAutomationName"));
				caps.setCapability(MobileCapabilityType.UDID, udid);
				caps.setCapability("autoGrantPermissions",true);
//				caps.setCapability("avd","pixel7");
				caps.setCapability("appPackage", props.getProperty("androidAppPackage"));
				caps.setCapability("appActivity", props.getProperty("androidAppActivity"));
				caps.setCapability("avdLaunchTimeout",18000);
	*/			
				
				// 3 ways to get the app url
	            // 1. try using one of them, whichever works for you, use user.dir and File.separator combo
				String appUrl = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" +
						File.separator + "resources" + File.separator + "app" + File.separator + "Android.SauceLabs.Mobile.Sample.app.2.7.1.apk";
	            // 2. Or, use the below, Read from config file way
//				String appUrl = getClass().getResource(props.getProperty("androidAppLocation")).getFile();
		
	            // 3. And if both ways fail, then try using FileInputstream. just search on the internet and you will find many solutions
				//	NOTE: always print the url to make sure that it's not null			
//				System.out.println(appUrl);
				// use below for first time installation of app or updating the app versions
				caps.setCapability("app", appUrl);
				
				url = new URL(props.getProperty("appiumURL") + "4723");
				
				driver = new AndroidDriver(url, caps);
				break;
				
			case "iOS":
			
				// provide iOS code here
				break;
				
			default:
				throw new Exception("Invalid platform! " + platformName);
			}
			setDriver(driver);
		  
	  } catch (Exception e) {
		  e.printStackTrace();
		  throw e;
	  }finally {
			if(inputStream != null) {
				inputStream.close();
			}
			if(stringsIs != null) {
				stringsIs.close();
			}
	  }
  }

  public void waitForVisibility(WebElement e) {
	  WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(TestUtils.WAIT));
	  wait.until(ExpectedConditions.visibilityOf(e));
  }
  
  public void clear(WebElement e) {
	  waitForVisibility(e);
	  e.clear();
  }
  
  public void click(WebElement e) {
	  waitForVisibility(e);
	  e.click();
  }
  
  public void click(WebElement e, String msg) {
	  waitForVisibility(e);
	  ExtentReport.getTest().log(Status.INFO, msg);
	  e.click();
  }
  
  public void sendKeys(WebElement e, String txt) {
	  waitForVisibility(e);
	  e.sendKeys(txt);
  }
  
  public void sendKeys(WebElement e, String txt, String msg) {
	  waitForVisibility(e);
	  ExtentReport.getTest().log(Status.INFO, msg);
	  e.sendKeys(txt);
  }
  
  public String getAtrribute(WebElement e, String attribute) {
	  waitForVisibility(e);
	  return e.getAttribute(attribute);
  }
  
  public String getText(WebElement e, String msg) {
	  String txt = null;;
	  switch(getPlatform()){
	  case "Android":
		  txt = getAtrribute(e, "text");
		  break;
	  case "iOS":
		  txt = getAtrribute(e, "text");
		  break;
	  }
	  ExtentReport.getTest().log(Status.INFO, msg);
	  return txt;
  }
  
  public void closeApp() {
	  ((InteractsWithApps)getDriver()).terminateApp(getProps().getProperty("androidAppPackage"));
  }
  
  public void launchApp() {
	  ((InteractsWithApps)getDriver()).activateApp(getProps().getProperty("androidAppPackage"));
  }
  
  public WebElement scrollToElement() {
	  return getDriver().findElement(AppiumBy.androidUIAutomator(
			  "new UiScrollable(new UiSelector()" + ".scrollable(true)).scrollIntoView("
					  + "new UiSelector().description(\"test-Price\"));"));
  }

  @AfterTest
  public void afterTest() {
	  getDriver().quit();
  }

}
