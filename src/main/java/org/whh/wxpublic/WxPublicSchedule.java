package org.whh.wxpublic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WxPublicSchedule {
	
	@Autowired
	MaterailManage manage;

	/**
	 * 一天同步一次素材
	 */
//	@Scheduled(fixedRate = 1000 * 60 * 60 * 24)
	private void scheduledSynMaterial() {
		manage.batchGetMaterial(20);
	}
}
