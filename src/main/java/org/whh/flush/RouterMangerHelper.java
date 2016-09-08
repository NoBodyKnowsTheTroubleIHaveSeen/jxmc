package org.whh.flush;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *路由器管理辅助类 
 *
 */
public class RouterMangerHelper
{
	private static WebDriver driver;

	/**
	 * 登陆路由器
	 */
	private static void login()
	{
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		driver = new ChromeDriver();
		driver.navigate().to("http://192.168.1.1/");
		driver.manage().window().maximize();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement pwdInput = wait.until(new ExpectedCondition<WebElement>()
		{
			@Override
			public WebElement apply(WebDriver d)
			{
				return d.findElement(By.id("pcPassword"));
			}
		});
		pwdInput.sendKeys("xlszj77@s");
		WebElement loginBtn = driver.findElement(By.id("logIn"));
		loginBtn.click();
	}
	/**
	 * 重连网络
	 */
	private static void realReConnect()
	{
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement netA = wait.until(new ExpectedCondition<WebElement>()
		{
			@Override
			public WebElement apply(WebDriver d)
			{
				return d.findElement(By.id("menu_net"));
			}
		});
		Actions action = new Actions(driver);
		action.moveToElement(netA);
		netA.click();
		WebElement disConnBtn = wait.until(new ExpectedCondition<WebElement>()
		{
			@Override
			public WebElement apply(WebDriver d)
			{
				return d.findElement(By.id("disConn"));
			}
		});
		action.moveToElement(disConnBtn);
		try
		{
			Thread.sleep(100);
		} catch (InterruptedException e1)
		{
			e1.printStackTrace();
		}
		disConnBtn.click();
		for (int i = 0; i < 10; i++)
		{
			try
			{
				WebElement connBtn = driver.findElement(By.id("conn"));
				if (i == 9|| connBtn.isEnabled())
				{
					action.moveToElement(connBtn);
					connBtn.click();
					break;
				}
				try
				{
					Thread.sleep(1000);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			
		}
	}
	/**
	 * 关闭drive
	 */
	private static void closeDriver()
	{
		driver.close();
	}
	/**
	 * 整个重连动作，包括登陆，重连，关闭drive
	 */
	public static void reConnect()
	{
		
		try
		{
			login();
			realReConnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		closeDriver();
	}
}
