package org.whh.browse;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.whh.driver.DriverBase;

public class WDBrowser extends DriverBase
{
	public static void main(String[] args)
	{
		WDBrowser browser = new WDBrowser();
		browser.browse();
	}

	/**
	 * 商品id列表
	 */
	ArrayList<String> itemIds = null;

	public void browse()
	{
		try
		{

			navigate();
			intoIndexPage();
			getItemIds();
			for (int i = 0; i < 10; i++)
			{
				randomClickIntoItem();
				randomBrowser();
				backToIndex();
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			closeDriver();
		}
	}

	/**
	 * 进入入口
	 */
	private void navigate()
	{
		driver.navigate()
				.to("http://weidian.com/?userid=839209114&wfr=qfriend");
	}

	/**
	 * 进入首页
	 */
	private void intoIndexPage()
	{
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement enterBtn = wait.until(new ExpectedCondition<WebElement>()
		{
			@Override
			public WebElement apply(WebDriver d)
			{
				return d.findElement(By.className("enterShopTarget"));
			}
		});
		enterBtn.click();
	}

	/**
	 * 获取商品id
	 */
	private void getItemIds()
	{
		itemIds = new ArrayList<String>();
		List<WebElement> elements = driver.findElements((By
				.xpath("//a[contains(@href,'item.html?itemID')]")));
		for (WebElement webElement : elements)
		{
			String href = webElement.getAttribute("href");
			int startIndex = href.lastIndexOf("itemID=");
			href = href.substring(startIndex + 7, href.length() - 1);
			String[] tmps = href.split("&");
			if (!itemIds.contains(tmps[0]))
			{
				itemIds.add(tmps[0]);
			}
		}
	}

	private String getRandomItemId()
	{
		return itemIds.get(getRandom(itemIds.size()));
	}

	/**
	 * 随机点击进入一件商品
	 */
	private void randomClickIntoItem()
	{
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement itemA = wait.until(new ExpectedCondition<WebElement>()
		{
			@Override
			public WebElement apply(WebDriver d)
			{
				String xpath = "//a[contains(@href, '" + getRandomItemId()
						+ "')]";
				return d.findElement(By.xpath(xpath));
			}
		});
		Actions action = new Actions(driver);
		action.moveToElement(itemA);
		try
		{
			Thread.sleep(getRandom(2000, 500));
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		itemA.click();
	}

	private void backToIndex()
	{
		WebDriverWait wait = new WebDriverWait(driver, 10);
		((JavascriptExecutor) driver).executeScript("scroll(0,0);");
		WebElement backA = wait.until(new ExpectedCondition<WebElement>()
		{
			@Override
			public WebElement apply(WebDriver d)
			{
				return d.findElement(By.id("hd_back"));
			}
		});
		System.out.println(backA.getAttribute("href"));
		try
		{
			Thread.sleep(getRandom(2000, 500));
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		backA.click();
	}

}
