package org.whh.flush;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whh.dao.ContentsDao;
import org.whh.util.HttpClientHelper;
@Component
public class GuoKerBrowser extends DriverBase {

	@Autowired
	ContentsDao contentsDao;

	public GuoKerBrowser() {
		super(false, true);
	}

	public GuoKerBrowser(Boolean isInit, Boolean isShow) {
		super(isInit, isShow);
	}

	public Long startOffest = 50L;

	public void grab() {
		String url = "http://www.kexuelife.com/apis/article.json?offset=50&limit=25";
		String response = HttpClientHelper.post(url, new ArrayList<NameValuePair>());
		System.out.println(response);
		init(true);
		closeDriver();
	}

	private void getInfos(String url) {
		/*
		 * try { driver.navigate().to(url); WebElement voteElement =
		 * driver.findElement(By.xpath("//span[@id='article_click']")); Long
		 * vote = Long.parseLong(voteElement.getText()); if (vote > 300L) {
		 * WebElement contentElement =
		 * driver.findElement(By.xpath("//div[@class='content']")); String
		 * content = contentElement.getText(); Contents oldContents =
		 * contentsDao.findBySrcIdAndSrcUuid(Contents.SRC_ID_SAN_WEN, startId +
		 * ""); if (oldContents != null) { return; } Contents contents = new
		 * Contents(); contents.setContent(content); contents.setCreateTime(new
		 * Date()); contents.setVote(vote);
		 * contents.setType(Contents.Type_ARTIACL); contents.setSrcUrl(url);
		 * contents.setSrcId(Contents.SRC_ID_SAN_WEN);
		 * contents.setSrcUuid(startId + ""); contentsDao.save(contents);
		 * logger.info("保存一篇文章"); } } catch (UnhandledAlertException ex) { Alert
		 * alt = driver.switchTo().alert(); alt.accept(); } catch (Exception e)
		 * { logger.error("获取文章信息失败", e); }
		 */
	}
}
