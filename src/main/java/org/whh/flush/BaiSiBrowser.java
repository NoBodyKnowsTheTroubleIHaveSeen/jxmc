package org.whh.flush;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
public class BaiSiBrowser extends DriverBase {

	@Autowired
	JokeRecordDao jokeRecordDao;
	@Autowired
	JokeInfoDao jokeInfoDao;

	private static Logger logger = LoggerFactory.getLogger(BaiSiBrowser.class);

	public static String url = "http://www.budejie.com/old-text/";

	private String lastDate = null;

	public BaiSiBrowser() {
		super(false, true);
	}

	public void grab() {
		init(true);
		driver.navigate().to(url);
		for (int i = 0; i < 5365; i++) {
			try {
				getInfos();
				Thread.sleep(1000 * 60 * 2);
			} catch (Exception e) {

			}
		}
		closeDriver();
	}

	private void getInfos() throws ParseException {
		WebElement nextPage = driver.findElement(By.xpath("//a[@href='/old-text/']"));
		nextPage.click();
		WebElement dateElement = driver.findElement(By.xpath("//*[contains(@class,'u-time')]"));
		lastDate = dateElement.getText();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse(lastDate);
		logger.info("日期" + date);
		JokeRecord oldRecord = jokeRecordDao.findByJokeIdAndJokeType(lastDate, JokeRecord.JOKE_TYPE_BAISI);
		if (oldRecord != null) {
			return;
		}
		JokeRecord record = new JokeRecord();
		record.setCreateTime(new Date());
		record.setJokeId(lastDate);
		record.setJokeUploadDate(date);
		record.setJokeType(JokeRecord.JOKE_TYPE_BAISI);
		jokeRecordDao.save(record);

		List<WebElement> articals = driver.findElements((By.xpath("//*[contains(@class,'j-r-list-c-desc')]")));
		List<WebElement> numbers = driver.findElements((By.xpath("//li[@class='j-r-list-tool-l-up']/span")));
		int i = 0;
		for (WebElement artical : articals) {
			String content = artical.getText();
			WebElement voteElement = numbers.get(i++);
			String thumb = null;
			String vote = voteElement.getText();
			JokeInfo info = new JokeInfo();
			info.setContent(content);
			info.setAgreeCount(Integer.parseInt(vote));
			info.setJokeId(lastDate);
			info.setThumb(thumb);
			info.setAuditStatus(JokeInfo.AUDIT_STATUS_UNAUDIT);
			info.setCreateTime(new Date());
			jokeInfoDao.save(info);
		}
	}
}
