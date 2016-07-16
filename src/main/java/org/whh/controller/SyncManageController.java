package org.whh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.whh.base.ControllerBase;
import org.whh.dao.WdAppInfoDao;
import org.whh.entity.WdAppInfo;
import org.whh.wd.ProductMangeHelper;
import org.whh.web.CommonException;
import org.whh.web.CommonMessage;

@Controller
public class SyncManageController extends ControllerBase {

	@Autowired
	ProductMangeHelper helper;

	@Autowired
	WdAppInfoDao infoDao;

	@RequestMapping("/syncManage")
	public String syncManage(Model model) {
		List<WdAppInfo> masters = infoDao.getByIsSrc(true);
		model.addAttribute("masters", masters);
		return "syncManage/syncManage";
	}

	@RequestMapping("/doSyncManage")
	@ResponseBody
	public CommonMessage doSyncManage(Long srcAppInfoId,Long toAppInfoId,Boolean isSyncAll) {
		if (!isNull(srcAppInfoId)) {
			helper.sync(srcAppInfoId,isSyncAll);
		}else if (!isNull(toAppInfoId)) {
			WdAppInfo info = infoDao.findOne(toAppInfoId);
			Long srcId =info.getSrcWdAppInfoId();
			helper.sync(srcId,toAppInfoId,false);
		}
		CommonMessage message = new CommonMessage();
		message.setMessage("同步成功");
		return message;
	}

	@RequestMapping("/getWdAppInfos")
	@ResponseBody
	public Page<WdAppInfo> getWdAppInfos(Pageable pageable) {
		Page<WdAppInfo> pageData = infoDao.findAll(pageable);
		return pageData;
	}

	@RequestMapping("/saveOrUpdateWdAppInfo")
	public String saveOrUpdateWdAppInfo(Long id, Model model) {
		List<WdAppInfo> masters = infoDao.getByIsSrc(true);
		model.addAttribute("masters", masters);
		if (!isNull(id)) {
			WdAppInfo info = infoDao.findOne(id);
			model.addAttribute("wdAppInfo", info);
		} else {
			model.addAttribute("wdAppInfo", new WdAppInfo());
		}
		return "syncManage/saveOrUpdateWdAppInfo";
	}

	@RequestMapping("/doSaveOrUpdateWdAppInfo")
	public String doSaveOrUpdateWdAppInfo(WdAppInfo info, Model model) throws CommonException {
		List<WdAppInfo> masters = infoDao.getByIsSrc(true);
		model.addAttribute("masters", masters);
		try {
			infoDao.save(info);
		} catch (Exception e) {
			throw new CommonException("保存app key信息失败");
		}
		return "syncManage/syncManage";
	}
}
