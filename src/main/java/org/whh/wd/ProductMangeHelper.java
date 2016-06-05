package org.whh.wd;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMangeHelper {

	@Autowired
	ProductHelper productHelper;
	@Autowired
	CateHelper cateHelper;

	@Autowired
	OrderHelper orderHelper;

	/**
	 * 同步类目和产品
	 * 
	 * @param appInfoId
	 */
	public void sync(Long appInfoId) {
		cateHelper.sync(appInfoId);
		productHelper.sync(appInfoId);
	}

	public void syncOrder(Long appInfoId, Date startTime, Date endTime) {
		orderHelper.syncOrders(appInfoId, startTime, endTime);
	}
}
