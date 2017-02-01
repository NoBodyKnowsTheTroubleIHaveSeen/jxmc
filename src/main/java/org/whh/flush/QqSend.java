package org.whh.flush;

import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whh.dao.QqSendUserInfoDao;
import org.whh.dao.QqUserInfoDao;
import org.whh.entity.QqSendUserInfo;
import org.whh.entity.QqUserInfo;

@Component
public class QqSend extends DriverBase {

	private static Logger logger = LoggerFactory.getLogger(QqSend.class);
	@Autowired
	QqUserInfoDao userInfoDao;

	@Autowired
	QqSendUserInfoDao sendUserInfoDao;

	private static String url = "http://web2.qq.com/";

	WebDriverWait wait;
	WebDriverWait shortWait;

	public QqSend() {
		super(false, true);
	}

	public QqSend(Boolean isInit, Boolean isShow) {
		super(isInit, isShow);
	}

	public void init() {
		if (driver != null) {
			return;
		}
		super.init();
		wait = new WebDriverWait(driver, 300);
		shortWait = new WebDriverWait(driver, 1);
		logger.debug("正在进入页面" + url + "等待登陆...");
		driver.navigate().to(url);
		intoPage();
	}

	/**
	 * 运行发送消息
	 */
	public void syncUsers() {
		init();
		logger.debug("正在获取用户列表...");
		getGroupUserList();
	}

	/*
	 * public void check() { init(); List<QqUserInfo> unchecks =
	 * userInfoDao.findUnCheckUser(); for (QqUserInfo qqUserInfo : unchecks) {
	 * checkUser(qqUserInfo); }
	 * 
	 * System.out.println(""); }
	 */

	public void send(String content) {
		init();
//		getGroupUserList();
		Iterable<QqUserInfo> frists = userInfoDao.findGroupFristByGroupUin();
		for (QqUserInfo groupFrist : frists) {
			List<QqUserInfo> infos = userInfoDao.findByGroupUin(groupFrist.getGroupUin());
			clickGroup(groupFrist.getGroupUin());
			WebElement session = driver.findElement(By.id("session"));
			session.click();
			System.out.println("");
			for (QqUserInfo info : infos) {
				try {
					QqSendUserInfo oldInfo = sendUserInfoDao.findByUinAndContent(info.getUin(), content);
					if (oldInfo != null) {
						continue;
					}
					createChatByUin(info.getUin());
					WebElement textEle = shortWait.until(new ExpectedCondition<WebElement>() {
						@Override
						public WebElement apply(WebDriver d) {
							return d.findElement(By.id("chat_textarea"));
						}
					});
					textEle.sendKeys(content);
					WebElement sendBtn = driver.findElement(By.id("send_chat_btn"));
					sendBtn.click();
					oldInfo = new QqSendUserInfo();
					oldInfo.setContent(content);
					oldInfo.setUin(info.getUin());
					oldInfo.setGroupUin(info.getGroupUin());
					sendUserInfoDao.save(oldInfo);
					Thread.sleep(1000);
				} catch (Exception ex) {
					logger.error("发送消息失败", ex);
				}

			}
		}

	}

	/**
	 * 进入聊天主界面
	 */
	public void intoPage() {
		wait.until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver d) {
				return d.findElement(By.xpath("//ul[@id='current_chat_list']"));
			}
		});
		System.out.println("------------");
	}

	public void clickGroup(String groupUin) {
		WebElement contact = driver.findElement(By.id("contact"));
		contact.click();
		/**
		 * 联系人上方的菜单栏
		 */
		wait.until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver d) {
				return d.findElement(By.id("memberTab"));
			}
		});
		/**
		 * 联系人上方的群 按钮
		 */
		WebElement group = wait.until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver d) {
				return d.findElement(By.xpath("//li[@cmd='clickMemberTab'][@param='group']"));
			}
		});
		group.click();
		/**
		 * 群列表
		 */
		List<WebElement> gList = wait.until(new ExpectedCondition<List<WebElement>>() {
			@Override
			public List<WebElement> apply(WebDriver d) {
				return d.findElements(By.xpath("//ul[@id='g_list']/li"));
			}
		});
		for (WebElement webElement : gList) {
			try {
				if (!webElement.getAttribute("_uin").equals(groupUin)) {
					continue;
				}
				Thread.sleep(1000);
				webElement.click();
				WebElement panelBtn = wait.until(new ExpectedCondition<WebElement>() {
					@Override
					public WebElement apply(WebDriver d) {
						return d.findElement(
								By.xpath("//div[contains(@class,'chat-panel')]//div[@cmd='clickLeftButton']"));
					}
				});
				panelBtn.click();

				WebElement viewMembers = wait.until(new ExpectedCondition<WebElement>() {
					@Override
					public WebElement apply(WebDriver d) {
						return d.findElement(By.xpath("//li[@cmd='viewMembers']/div"));
					}
				});
				Thread.sleep(1000);
				viewMembers.click();
				wait.until(new ExpectedCondition<WebElement>() {
					@Override
					public WebElement apply(WebDriver d) {
						return d.findElement(
								By.xpath("//div[contains(@class,'member-panel')][contains(@style, 'block')]"));
					}
				});

			} catch (Exception exception) {
				logger.error("获取群成员信息失败", exception);
			} finally {
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				String id = webElement.getAttribute("id");
				jse.executeScript("var ele = document.getElementById('" + id + "');ele.style.display = 'none';");
			}
		}
	}

	/**
	 * 获取群里的用户列表，并保存到数据库
	 */
	public void getGroupUserList() {
		WebElement contact = driver.findElement(By.id("contact"));
		contact.click();
		/**
		 * 联系人上方的菜单栏
		 */
		wait.until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver d) {
				return d.findElement(By.id("memberTab"));
			}
		});
		/**
		 * 联系人上方的群 按钮
		 */
		WebElement group = wait.until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver d) {
				return d.findElement(By.xpath("//li[@cmd='clickMemberTab'][@param='group']"));
			}
		});
		group.click();
		/**
		 * 群列表
		 */
		List<WebElement> gList = wait.until(new ExpectedCondition<List<WebElement>>() {
			@Override
			public List<WebElement> apply(WebDriver d) {
				return d.findElements(By.xpath("//ul[@id='g_list']/li"));
			}
		});
		for (WebElement webElement : gList) {
			try {
				String groupUin = webElement.getAttribute("_uin");
				WebElement groupNameEle = driver.findElement(By.id("userNick-" + groupUin));
				String groupName = groupNameEle.getText();
				Thread.sleep(1000);
				webElement.click();
				WebElement panelBtn = wait.until(new ExpectedCondition<WebElement>() {
					@Override
					public WebElement apply(WebDriver d) {
						return d.findElement(
								By.xpath("//div[contains(@class,'chat-panel')]//div[@cmd='clickLeftButton']"));
					}
				});
				panelBtn.click();
				WebElement viewInfo = wait.until(new ExpectedCondition<WebElement>() {
					@Override
					public WebElement apply(WebDriver d) {
						return d.findElement(By.xpath("//li[@cmd='viewInfo']/div"));
					}
				});
				Thread.sleep(1000);
				viewInfo.click();
				WebElement groupCount = wait.until(new ExpectedCondition<WebElement>() {
					@Override
					public WebElement apply(WebDriver d) {
						return d.findElement(By.xpath("//div[@cmd='viewGroupMember']/span[@class='text_right']"));
					}
				});
				Thread.sleep(1000);
				String countText = groupCount.getText();
				int count = 0;
				try {
					count = Integer.parseInt(countText.replace("人", ""));
				} catch (Exception ex) {
					logger.error("解析群用户数量出错", ex);
				}
				List<QqUserInfo> infos = userInfoDao.findByGroupUin(groupUin);
				if (infos.size() > 0) {
					if (infos.size() > count || count - infos.size() < 100) {
						continue;
					}
				}
				WebElement backGroupBtn = wait.until(new ExpectedCondition<WebElement>() {
					@Override
					public WebElement apply(WebDriver d) {
						return d.findElement(
								By.xpath("//div[contains(@class,'profile-panel')]//div[@cmd='clickLeftButton']"));
					}
				});
				Thread.sleep(500);
				backGroupBtn.click();
				panelBtn = wait.until(new ExpectedCondition<WebElement>() {
					@Override
					public WebElement apply(WebDriver d) {
						return d.findElement(
								By.xpath("//div[contains(@class,'chat-panel')]//div[@cmd='clickLeftButton']"));
					}
				});
				panelBtn.click();
				WebElement viewMembers = wait.until(new ExpectedCondition<WebElement>() {
					@Override
					public WebElement apply(WebDriver d) {
						return d.findElement(By.xpath("//li[@cmd='viewMembers']/div"));
					}
				});
				Thread.sleep(1000);
				viewMembers.click();
				wait.until(new ExpectedCondition<WebElement>() {
					@Override
					public WebElement apply(WebDriver d) {
						return d.findElement(
								By.xpath("//div[contains(@class,'member-panel')][contains(@style, 'block')]"));
					}
				});
				List<WebElement> members = driver.findElements(By.xpath("//ul[@id='member_search_result_list']/li"));
				for (WebElement member : members) {
					String uin = member.getAttribute("_uin");
					logger.debug("获取用户，uin:" + uin);
					QqUserInfo info = userInfoDao.findByUin(uin);
					if (info == null) {
						info = new QqUserInfo();
						info.setGroupUin(groupUin);
						info.setUin(uin);
						info.setCreateTime(new Date());
						info.setGroupName(groupName);
						userInfoDao.save(info);
					}
				}

			} catch (Exception exception) {
				logger.error("获取群成员信息失败", exception);
			} finally {
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				String id = webElement.getAttribute("id");
				jse.executeScript("var ele = document.getElementById('" + id + "');ele.style.display = 'none';");
			}
		}
	}

	private void createChatByUin(String uin) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("var li = document.createElement('li');li.textContent=" + uin
				+ ";li.setAttribute('id','recent-item-friend-" + uin
				+ "');li.setAttribute('class','list_item');li.setAttribute('_uin','" + uin
				+ "');li.setAttribute('_type','friend');li.setAttribute('cmd','clickMemberItem');var ele = document.getElementById('current_chat_list');ele.appendChild(li);");
		WebElement user = driver.findElement(By.id("recent-item-friend-" + uin));
		user.click();
		jse.executeScript(
				"var ele = document.getElementById('recent-item-friend-" + uin + "');ele.style.display = 'none';");

	}

	/*
	 * public void checkUser(QqUserInfo info) { try { String uin =
	 * info.getUin(); createChatByUin(uin); WebElement closeBtn =
	 * shortWait.until(new ExpectedCondition<WebElement>() {
	 * 
	 * @Override public WebElement apply(WebDriver d) { return d.findElement(
	 * By.xpath(
	 * "//div[contains(@class,'chat-panel')]//button[@cmd='clickRightButton']"))
	 * ; } }); closeBtn.click(); // String groupUin = info.getGroupUin(); //
	 * List<QqUserInfo> groupUsers = // userInfoDao.findByGroupUin(groupUin); //
	 * for (QqUserInfo qqUserInfo : groupUsers) { info.setCanSend(true);
	 * userInfoDao.save(info); // } } catch (Exception e) {
	 * info.setCanSend(false); userInfoDao.save(info); } }
	 */
}
