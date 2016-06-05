package org.whh.schedule;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.whh.dao.WdAppInfoDao;
import org.whh.entity.WdAppInfo;
import org.whh.util.CalendarUtil;
import org.whh.wd.OrderHelper;

@Component
public class OrderSchedule {
	@Autowired
	WdAppInfoDao infoDao;

	@Autowired
	OrderHelper orderHelper;

	@Scheduled(fixedRate = 600000)
//	@Scheduled(fixedRate = 5000)
	private void scheduledSyncOrders() {
		Iterable<WdAppInfo> infos = infoDao.findAll();
		for (WdAppInfo wdAppInfo : infos) {
			orderHelper.syncOrders(wdAppInfo.getId(), CalendarUtil.getDayTimeStart(new Date()),
					CalendarUtil.getDayTimeEnd(new Date()));
		}
	}
}
