package org.whh.flush;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whh.dao.JokeInfoDao;
import org.whh.dao.JokeRecordDao;
import org.whh.entity.JokeInfo;
import org.whh.entity.JokeRecord;

@Component
public class QiuBaiBrowser extends DriverBase {

	@Autowired
	JokeRecordDao jokeRecordDao;
	@Autowired
	JokeInfoDao jokeInfoDao;

	private static Logger logger = LoggerFactory.getLogger(QiuBaiBrowser.class);

	public static String url = "http://www.qiushibaike.com/history/";

	private String lastUuid = null;

	private String lastDate = null;

	public QiuBaiBrowser() {
		super(false, true);
	}

	public void grab() {
		init(true);
		driver.navigate().to(url);
		WebElement nextPage = driver.findElement(By.xpath("//div[contains(@class, 'history-nv')]/a"));
		String nextUrl = nextPage.getAttribute("href");
		String[] urls = nextUrl.split("/");
		lastUuid = urls[urls.length - 1];
		nextPage.click();
		for (int i = 0; i < 5365; i++) {
			try {
				getInfos();
			} catch (Exception e) {

			}
		}
		closeDriver();
	}

	private void getInfos() {
		JokeRecord oldRecord = jokeRecordDao.findByJokeIdAndJokeType(lastUuid, JokeRecord.JOKE_TYPE_QIUBAI);
		if (oldRecord != null) {
			goToNextPage();
			return;
		}
		logger.info("正在获取下一个页面地址");
		WebElement dateElement = driver
				.findElement(By.xpath("//div[contains(@class, 'history-nv')]/*[contains(@class,'date')]"));
		lastDate = dateElement.getText();
		logger.info("日期" + lastDate);

		String regex = "\\d*";
		Pattern p = Pattern.compile(regex);

		Matcher m = p.matcher(lastDate);
		Calendar calendar = Calendar.getInstance();
		int i = 0;
		while (m.find()) {
			if (!"".equals(m.group())) {
				switch (i) {
				case 0:
					calendar.set(Calendar.YEAR, Integer.parseInt(m.group()));
					break;
				case 1:
					calendar.set(Calendar.MONTH, Integer.parseInt(m.group()));
					break;
				case 2:
					calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, Integer.parseInt(m.group()));
					break;
				}
			}
			i++;
		}

		JokeRecord record = new JokeRecord();
		record.setCreateTime(new Date());
		record.setJokeId(lastUuid);
		record.setJokeUploadDate(calendar.getTime());
		record.setJokeType(JokeRecord.JOKE_TYPE_QIUBAI);
		jokeRecordDao.save(record);

		List<WebElement> articals = driver.findElements((By.xpath("//div[contains(@class, 'article')]")));
		for (WebElement artical : articals) {
			String id = artical.getAttribute("id");
			String contentXpath = "//div[@id='" + id + "']/*[contains(@class,'content')]";
			String thumbXpath = "//div[@id='" + id + "']/*[contains(@class,'thumb')]/a/img";
			String voteXpath = "//div[@id='" + id + "']//*[contains(@class,'stats-vote')]/*[contains(@class,'number')]";
			String commentsXpath = "//div[@id='" + id
					+ "']//*[contains(@class,'stats-comments')]//*[contains(@class,'number')]";
			WebElement contentElement = artical.findElement(By.xpath(contentXpath));
			String content = contentElement.getText();
			String thumb = null;
			try {
				WebElement thumbElement = artical.findElement(By.xpath(thumbXpath));
				thumb = thumbElement.getAttribute("src");
				System.out.println(thumb);
			} catch (Exception e) {
				logger.equals("未找到图片信息" + id);
			}
			WebElement voteElement = artical.findElement(By.xpath(voteXpath));
			String vote = voteElement.getText();
			WebElement commentElement = artical.findElement(By.xpath(commentsXpath));
			String comment = commentElement.getText();

			JokeInfo info = new JokeInfo();
			info.setContent(content);
			info.setAgreeCount(Integer.parseInt(vote));
			info.setCommnetCount(Integer.parseInt(comment));
			info.setJokeId(lastUuid);
			info.setThumb(thumb);
			info.setAuditStatus(JokeInfo.AUDIT_STATUS_UNAUDIT);
			jokeInfoDao.save(info);
		}
		goToNextPage();
	}

	public void goToNextPage() {
		WebElement nextPage = driver.findElement(By.xpath("//div[contains(@class, 'history-nv')]/a"));
		String nextUrl = nextPage.getAttribute("href");
		String[] urls = nextUrl.split("/");
		lastUuid = urls[urls.length - 1];
		nextPage.click();
	}

}
