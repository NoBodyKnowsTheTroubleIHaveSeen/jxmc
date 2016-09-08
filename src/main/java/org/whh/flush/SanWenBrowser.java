package org.whh.flush;

import java.util.Date;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whh.dao.ContentsDao;
import org.whh.entity.Contents;

@Component
public class SanWenBrowser extends DriverBase {

	private static Logger logger = LoggerFactory.getLogger(SanWenBrowser.class);

	@Autowired
	ContentsDao contentsDao;

	public SanWenBrowser() {
		super(false, true);
	}

	public SanWenBrowser(Boolean isInit, Boolean isShow) {
		super(isInit, isShow);
	}

	public Long startId = 3654819L;

	public void grab() {
		startId = 3729759L;
		init(true);
		for (int i = 0; i < 1000000; i++) {
			String url = "http://www.sanwen.net/subject/" + startId + "/";
			logger.info("当前页面地址：" + url);
			getInfos(url);
			startId = startId + 2;
		}
		closeDriver();
	}

	private void getInfos(String url) {
		try {
			driver.navigate().to(url);
			WebElement voteElement = driver.findElement(By.xpath("//span[@id='article_click']"));
			Long vote = Long.parseLong(voteElement.getText());
			if (vote > 300L) {
				WebElement contentElement = driver.findElement(By.xpath("//div[@class='content']"));
				String content = contentElement.getText();
				Contents oldContents = contentsDao.findBySrcIdAndSrcUuid(Contents.SRC_ID_SAN_WEN, startId+"");
				if (oldContents != null) {
					return ;
				}
				Contents contents = new Contents();
				contents.setContent(content);
				contents.setCreateTime(new Date());
				contents.setVote(vote);
				contents.setType(Contents.Type_ARTIACL);
				contents.setSrcUrl(url);
				contents.setSrcId(Contents.SRC_ID_SAN_WEN);
				contents.setSrcUuid(startId + "");
				contentsDao.save(contents);
				logger.info("保存一篇文章");
			}
		}catch(UnhandledAlertException ex)
		{
			Alert alt = driver.switchTo().alert();
		    alt.accept();
		}
		catch (Exception e) {
			logger.error("获取文章信息失败", e);
		}
	}
}
