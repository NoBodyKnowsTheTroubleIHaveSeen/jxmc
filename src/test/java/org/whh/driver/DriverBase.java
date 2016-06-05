package org.whh.driver;

import java.util.Random;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverBase
{
	public  WebDriver driver;
	
	private Random random;
	
	public DriverBase()
	{
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		random = new Random();
	}
	
	public  void closeDriver()
	{
		driver.close();
	}
	
	public boolean getRandomBoolean()
	{
		return random.nextBoolean();
	}

	public int getRandom(int bound)
	{
		return random.nextInt(bound);
	}
	public int getRandom(int bound,int base)
	{
		return base+random.nextInt(bound);
	}
	/**
	 * 随机浏览到页面底部
	 * 
	 * @param driver
	 * @return
	 */
	public void randomBrowser()
	{
		boolean isBrowseToEnd = getRandomBoolean();
		int browseCount = 0;
		for (int i = 1; i < 9;)
		{
			i += getRandom(getRandom(5) + 1);
			if (i > 9)
			{
				i = 9;
			}
			((JavascriptExecutor) driver)
					.executeScript("scroll(0,document.body.clientHeight*0." + i
							+ getRandom(10) + ");");
			try
			{
				Thread.sleep(getRandom(4000,500));
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
	}
	
}
