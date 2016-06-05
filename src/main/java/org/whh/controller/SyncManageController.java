package org.whh.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.whh.base.ControllerBase;
import org.whh.dao.WdAppInfoDao;
import org.whh.entity.WdAppInfo;
import org.whh.wd.ProductMangeHelper;
import org.whh.web.CommonMessage;

@Controller
public class SyncManageController extends ControllerBase {

	@Autowired
	ProductMangeHelper helper;

	@Autowired
	WdAppInfoDao infoDao;

	@RequestMapping("/syncManage")
	public String syncManage(Model model) {
		Iterable<WdAppInfo> infos = infoDao.findAll();
		List<WdAppInfo> masters = new ArrayList<WdAppInfo>();
		for (WdAppInfo wdAppInfo : infos) {
			if (wdAppInfo.getSrcWdAppInfoId() == null) {
				masters.add(wdAppInfo);
			}
		}
		model.addAttribute("masters", masters);
		return "syncManage/syncManage";
	}

	@RequestMapping("/doSyncManage")
	@ResponseBody
	public CommonMessage doSyncManage(Long appInfoId) {
		helper.sync(appInfoId);
		CommonMessage message = new CommonMessage();
		message.setMessage("同步成功");
		return message;
	}
}
