package org.whh.flush;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.whh.util.RandomHelper;

public class DriverBase
{
	private static Logger logger = LoggerFactory.getLogger(DriverBase.class);
	public WebDriver driver;

	public DriverBase()
	{
		System.setProperty("webdriver.chrome.driver", this.getClass().getResource("/").getFile() +"bin/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().setPosition(new Point(-2000, 0));
	}

	public void closeDriver()
	{
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
	public void randomBrowser()
	{
		logger.info("正在随机浏览到页面底部...");
		boolean isBrowseToEnd = RandomHelper.getRandomBoolean();
		int browseCount = 0;
		for (int i = 1; i < 9;)
		{
			i += RandomHelper.getRandom(RandomHelper.getRandom(5) + 1);
			if (i > 9)
			{
				i = 9;
			}
			((JavascriptExecutor) driver)
					.executeScript("scroll(0,document.body.clientHeight*0." + i
							+ RandomHelper.getRandom(10) + ");");
			try
			{
				Thread.sleep(RandomHelper.getRandom(4000, 500));
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			if (!isBrowseToEnd)
			{
				browseCount++;
				if (browseCount > 4)
				{
					break;
				}
			}
		}
		logger.info("完成浏览");
	}

}
