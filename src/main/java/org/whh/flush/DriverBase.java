package org.whh.flush;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.whh.util.RandomHelper;

public class DriverBase {
	private static Logger logger = LoggerFactory.getLogger(DriverBase.class);
	public WebDriver driver;

	private String currentWindowHandle;

	private String newWindowHandle;
	
	private WebDriver newWindow;

	public DriverBase(Boolean isInit, Boolean isShow) {
		if (isInit) {
			init(isShow);
		}
	}

	public void init() {
		init(true);
	}
	/**
	 * 初始化drive
	 * @param isShow
	 */
	public void init(Boolean isShow) {
		System.setProperty("webdriver.chrome.driver",
				this.getClass().getResource("/").getFile() + "bin/chromedriver.exe");
		driver = new ChromeDriver();
		if (!isShow) {
			driver.manage().window().setPosition(new Point(-2000, 0));
		}
	}
	/**
	 * 关闭drive
	 */
	public void closeDriver() {
		logger.info("正在关闭浏览器...");
		driver.close();
		driver.quit();
		logger.info("完成关闭浏览器");
	}

	/**
	 * 随机浏览到页面底部
	 * 
	 * @param driver
	 * @return
	 */
	public void randomBrowser() {
		logger.info("正在随机浏览到页面底部...");
		boolean isBrowseToEnd = RandomHelper.getRandomBoolean();
		int browseCount = 0;
		for (int i = 1; i < 9;) {
			i += RandomHelper.getRandom(RandomHelper.getRandom(5) + 1);
			if (i > 9) {
				i = 9;
			}
			((JavascriptExecutor) driver)
					.executeScript("scroll(0,document.body.clientHeight*0." + i + RandomHelper.getRandom(10) + ");");
			try {
				Thread.sleep(RandomHelper.getRandom(4000, 500));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (!isBrowseToEnd) {
				browseCount++;
				if (browseCount > 4) {
					break;
				}
			}
		}
		logger.info("完成浏览");
	}
	/**
	 * 切换到新窗口
	 */
	public void switchToNewWindow() {
		currentWindowHandle = driver.getWindowHandle();// 获取当前窗口句柄
		Set<String> handles = driver.getWindowHandles();// 获取所有窗口句柄
		Iterator<String> it = handles.iterator();
		while (it.hasNext()) {
			newWindowHandle = it.next();
			if (currentWindowHandle.equals(newWindowHandle)) {
				continue;
			} else {
				newWindow = driver.switchTo().window(newWindowHandle);// 切换到新窗口
				logger.debug("切换到新界面上:" + newWindow.getTitle());
				break;
			}
		}
	}
	/**
	 * 关闭新窗口
	 */
	public void closeNewWindow()
	{
		newWindow.close();
	}
	/**
	 * 切换到旧窗口
	 */
	public void switchToOldWindow() {
		driver.switchTo().window(currentWindowHandle);// 回到原来页面
	}
	/**
	 * 移动到某个元素
	 * @param element
	 */
	public void movoTo(WebElement element)
	{
		Actions action = new Actions(driver);
		action.moveToElement(element);
	}

}
