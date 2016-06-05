package org.whh.flush;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.whh.util.RandomHelper;

public class WDBrowser extends DriverBase
{
	private static Logger logger = LoggerFactory.getLogger(WDBrowser.class);

	private List<String> entrances = new ArrayList<String>();
	
	public WDBrowser ()
	{
		entrances.add("http://weidian.com/?userid=839209114&wfr=wx");
		entrances.add("http://weidian.com/s/839209114?wfr=qfriend");
		entrances.add("http://weidian.com/?userid=839209114&wfr=wx_profile");
		entrances.add("http://weidian.com/?userid=839209114&wfr=wb");
		entrances.add("http://weidian.com/?userid=839209114&wfr=wxp&from=timeline&isappinstalled=1");
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
			/*
			 * 下载图片
			 * for (int i = 0; i < itemIds.size(); i++)
			{
				
				WebDriverWait wait = new WebDriverWait(driver, 10);
				final int j = i;
				WebElement itemA = wait.until(new ExpectedCondition<WebElement>()
				{
					@Override
					public WebElement apply(WebDriver d)
					{
						String xpath = "//a[contains(@href, 'itemID=" + itemIds.get(j)
								+ "')]";
						return d.findElement(By.xpath(xpath));
					}
				});
				Actions action = new Actions(driver);
				action.moveToElement(itemA);
				try
				{
					Thread.sleep(RandomHelper.getRandom(100, 10));
				} catch (InterruptedException e)
				{
					logger.error("sleep异常",e);
				}
				itemA.click();
				try
				{
					Thread.sleep(RandomHelper.getRandom(1000, 1000));
				} catch (InterruptedException e)
				{
					logger.error("sleep异常",e);
				}
				
				List<WebElement> elements = driver.findElements((By
						.xpath("//img[contains(@data-src,'1080')]")));
				for (WebElement webElement : elements)
				{
					String href = webElement.getAttribute("data-src");
					String filepath = "d:/"+itemIds.get(i)+"";
					Application.downloadPicture(filepath,href);
				}
				
			}*/
			int randomCount = RandomHelper.getRandom(10);
			logger.info("本次启动浏览器将浏览"+randomCount+"个商品");
			for (int i = 0; i < randomCount; i++)
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
				.to(entrances.get(RandomHelper.getRandom(entrances.size())));
	}
	

	/**
	 * 进入首页
	 */
	private void intoIndexPage()
	{
		logger.info("正在进入微店首页...");
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
		logger.info("进入微店首页成功");
	}

	/**
	 * 获取商品id
	 */
	private void getItemIds()
	{
		logger.info("正在获取商品列表...");
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
		logger.info("获取商品列表完成");
	}

	private String getRandomItemId()
	{
		return itemIds.get(RandomHelper.getRandom(itemIds.size()));
	}
	/**
	 * 随机点击进入一件商品
	 */
	private void randomClickIntoItem()
	{
		logger.info("正在随机进入一件商品...");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement itemA = wait.until(new ExpectedCondition<WebElement>()
		{
			@Override
			public WebElement apply(WebDriver d)
			{
				String xpath = "//a[contains(@href, 'itemID=" + getRandomItemId()
						+ "')]";
				return d.findElement(By.xpath(xpath));
			}
		});
		logger.info("正在进入商品："+itemA.getAttribute("data-for-gaq"));
		Actions action = new Actions(driver);
		action.moveToElement(itemA);
		try
		{
			Thread.sleep(RandomHelper.getRandom(2000, 500));
		} catch (InterruptedException e)
		{
			logger.error("sleep异常",e);
		}
		itemA.click();
	}

	private void backToIndex()
	{
		logger.info("正在等待返回首页...");
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
		try
		{
			Thread.sleep(RandomHelper.getRandom(2000, 500));
		} catch (InterruptedException e)
		{
			logger.error("sleep异常",e);
		}
		backA.click();
	}

}
