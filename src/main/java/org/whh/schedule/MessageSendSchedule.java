package org.whh.schedule;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.whh.dao.MaterialDao;
import org.whh.dao.WxPublicUserDao;
import org.whh.entity.Material;
import org.whh.entity.WxPublicUser;
import org.whh.util.CalendarUtil;
import org.whh.wxpublic.MessageSendService;

/**
 * 每日定时向最近有互动的用户推送内容
 * 
 * @author ASUS-PC
 *
 */
@Component
public class MessageSendSchedule {

	private Logger logger = LoggerFactory.getLogger(MessageSendSchedule.class);

	@Autowired
	MaterialDao materialDao;

	@Autowired
	WxPublicUserDao wxPublicUserDao;

	@Scheduled(cron = "0 0 17-21 * * ?")
	public void sendMessage() {

//		Iterable<WxPublicUser> users = wxPublicUserDao.findAll();
//		Iterator<WxPublicUser> iterator = users.iterator();
//		Date dayTimeStart = CalendarUtil.getDayTimeStart(new Date());
//		List<Material> material = materialDao.findByMaterialStatus(Material.MATERIAL_STATUS_CURRENT_JOKE);
//		while (iterator.hasNext()) {
//			try {
//				WxPublicUser user = iterator.next();
//				Date date = user.getLastSendTime();
//				if (date != null && date.getTime() > dayTimeStart.getTime()) {
//					continue;
//				}
//				new MessageSendService().sendNews(user.getOpenId(), material.get(0).getMediaId());
//				user.setLastSendTime(new Date());
//				wxPublicUserDao.save(user);
//			} catch (Exception e) {
//				logger.error("定时发送每日推荐内容失败", e);
//			}
//		}
	}
}
