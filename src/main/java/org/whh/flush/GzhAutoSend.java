package org.whh.flush;

import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whh.dao.GzhSendRecordDao;
import org.whh.entity.GzhSendRecord;

@Component
public class GzhAutoSend extends DriverBase {

	private static Logger logger = LoggerFactory.getLogger(GzhAutoSend.class);
	private String startPage = "https://mp.weixin.qq.com/";
	@Autowired
	GzhSendRecordDao sendRecordDao;

	public GzhAutoSend() {
		super(false, true);
	}

	public GzhAutoSend(Boolean isInit, Boolean isShow) {
		super(isInit, isShow);
	}

	public void send(String page, String id, String userName, String pwd) {
		if (page == null || id == null) {
			logger.error("未设置page或id");
			return;
		}
		init();
		driver.navigate().to(startPage);
		WebElement account = driver.findElement(By.xpath("//input[@name='account']"));
		account.sendKeys(userName);
		WebElement password = driver.findElement(By.xpath("//input[@name='password']"));
		password.sendKeys(pwd);
		WebElement loginBtn = driver.findElement(By.xpath("//a[@id='loginBt']"));
		loginBtn.click();
		WebDriverWait wait = new WebDriverWait(driver, 300);
		WebElement yhgl = wait.until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver d) {
				return d.findElement(By.xpath("//a[@data-id='10013']"));
			}
		});
		yhgl.click();
		List<WebElement> users = driver.findElements(By.xpath("//a[@class='remark_name']"));
		for (WebElement user : users) {
			try {
				sendMessage(user, page, id, wait);
			} catch (Exception ex) {
				logger.error("发送信息失败", ex);
			}
		}
	}

	public void sendMessage(WebElement user, String page, String id, WebDriverWait wait) throws InterruptedException {
		String userText = user.getText();
		String fakeId = user.getAttribute("data-fakeid");
		GzhSendRecord sendRecord = sendRecordDao.findByMsgIdAndFakeId(id, fakeId);
		if (sendRecord != null) {
			return;
		}
		user.click();
		switchToNewWindow();
		WebElement titleBtn = wait.until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver d) {
				return d.findElement(By.xpath("//span[@class='msg_tab_title']"));
			}
		});
		// 图文消息
		titleBtn.click();
		Thread.sleep(300);
		// 素材库
		WebElement sckBtn = driver.findElement(By.xpath("//a[@class='add_gray_wrp jsMsgSenderPopBt']"));
		sckBtn.click();
		WebElement gotoText = wait.until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver d) {
				return d.findElement(By.xpath("//span[@class='goto_area']/input"));
			}
		});
		gotoText.sendKeys(page);
		WebElement gotoBtn = driver.findElement(By.xpath("//a[@class='btn page_go']"));
		gotoBtn.click();
		//素材
		WebElement sc = wait.until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver d) {
				return d.findElement(By.xpath("//div[@id='"+id+"']"));
			}
		});
		movoTo(sc);
		WebElement title = wait.until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver d) {
				return d.findElement(By.xpath("//div[@id='"+id+"']//a[@data-msgid='"+id.replace("appmsg", "")+"']"));
			}
		});
		String titleText = title.getText();
		Thread.sleep(300);
		sc.click();
		// 确定按钮
		WebElement confirmBtn = driver.findElement(By.xpath("//button[@data-index='0']"));
		confirmBtn.click();
		Thread.sleep(300);
		// 发送按钮
		WebElement sendBtn = driver.findElement(By.xpath("//span[@id='js_submit']/button"));
		sendBtn.click();
		Thread.sleep(100);
		GzhSendRecord record = new GzhSendRecord();
		record.setFakeId(fakeId);
		record.setMsgId(id);
		record.setRemarkName(userText);
		record.setSendDate(new Date());
		record.setCreateTime(new Date());
		record.setTitle(titleText);
		sendRecordDao.save(record);
		Thread.sleep(100);
		closeNewWindow();
		switchToOldWindow();
		Thread.sleep(1000);
	}
}
