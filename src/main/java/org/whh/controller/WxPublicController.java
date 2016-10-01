package org.whh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.whh.dao.MaterialDao;
import org.whh.dao.WxPublicUserDao;
import org.whh.entity.Material;
import org.whh.entity.WxPublicUser;
import org.whh.flush.GzhAutoSend;
import org.whh.util.PropertyUtil;
import org.whh.web.CommonMessage;
import org.whh.wxpublic.MaterailManage;
import org.whh.wxpublic.MenuManage;
import org.whh.wxpublic.UserManage;

/**
 * 微信公众号本地
 * 
 * @author Administrator
 *
 */
@Controller
public class WxPublicController {

	@Autowired
	UserManage userMange;

	@Autowired
	GzhAutoSend gzhAutoSend;
	@Autowired
	WxPublicUserDao pulicUserDao;
	@Autowired
	MaterialDao materialDao;

	@Autowired
	MaterailManage materialManage;

	@Autowired
	MenuManage menuManage;

	@RequestMapping("/common/gzhSend")
	public String gzhSend() {
		return "wxpublic/send";
	}

	@RequestMapping("/common/doGzhSend")
	@ResponseBody
	public String doGzhSend(String page, String id) {
		String userName = PropertyUtil.getProperty("wxu");
		String pwd = PropertyUtil.getProperty("wxp");
		new Thread(new Runnable() {

			@Override
			public void run() {
				gzhAutoSend.send(page, id, userName, pwd);
			}
		}).start();
		synchronized (GzhAutoSend.lock) {
			try {
				GzhAutoSend.lock.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String result = "<html><img src='" + GzhAutoSend.imageUrl + "'/></html>";
		return result;
	}

	@RequestMapping("/showWxUser")
	public String showUser() {
		return "wxpublic/showWxUser";
	}

	@ResponseBody
	@RequestMapping("/getWxUser")
	public Page<WxPublicUser> getWxUser(Pageable pageable) {
		return pulicUserDao.findAll(pageable);
	}

	@RequestMapping("/showMaterail")
	public String showMaterail() {
		return "wxpublic/showMaterail";
	}

	@RequestMapping("/getMaterail")
	@ResponseBody
	public Page<Material> getMaterail(Pageable pageable) {
		return materialDao.findAll(pageable);
	}

	@RequestMapping("/setTodayJoke")
	@ResponseBody
	public CommonMessage setTodayJoke(String mediaId) {
		List<Material> oldJoke = materialDao.findByMaterialStatus(Material.MATERIAL_STATUS_CURRENT_JOKE);
		for (Material material : oldJoke) {
			material.setMaterialStatus(Material.MATERIAL_STATUS_NORMAL);
			materialDao.save(material);
		}
		Material nowJoke = materialDao.findByMediaId(mediaId);
		nowJoke.setMaterialStatus(Material.MATERIAL_STATUS_CURRENT_JOKE);
		materialDao.save(nowJoke);
		CommonMessage message = new CommonMessage("设置今日笑话成功");
		return message;
	}

	@RequestMapping("/setCurrentContent")
	@ResponseBody
	public CommonMessage setCurrentContent(String mediaId) {
		List<Material> oldJoke = materialDao.findByMaterialStatus(Material.MATERIAL_STATUS_CURRENT_MATERIAL);
		for (Material material : oldJoke) {
			material.setMaterialStatus(Material.MATERIAL_STATUS_NORMAL);
			materialDao.save(material);
		}
		Material nowContent = materialDao.findByMediaId(mediaId);
		nowContent.setMaterialStatus(Material.MATERIAL_STATUS_CURRENT_MATERIAL);
		materialDao.save(nowContent);
		String keyword = nowContent.getKeyword();
		if (keyword != null && keyword.length() <= 15) {
			menuManage.createMenu();
		}
		CommonMessage message = new CommonMessage("设置本期文章成功");
		return message;
	}

	@RequestMapping("/syncWxUser")
	@ResponseBody
	public CommonMessage syncWxUser() {
		userMange.syncWxUser();
		CommonMessage message = new CommonMessage("同步微信用户成功");
		return message;
	}

	@RequestMapping("/syncMaterial")
	@ResponseBody
	public CommonMessage syncMaterial(Integer size) {
		materialManage.batchGetMaterial(size);
		CommonMessage message = new CommonMessage("同步素材成功");
		return message;
	}

	@RequestMapping("/setKeyword")
	@ResponseBody
	public CommonMessage setKeyword(Long id, String keyword, String inputCode,Integer action) {
		Material material = materialDao.findOne(id);
		material.setKeyword(keyword);
		material.setInputCode(inputCode);
		material.setAction(action);
		materialDao.save(material);
		if (material.getMaterialStatus() != null
				&& material.getMaterialStatus() == Material.MATERIAL_STATUS_CURRENT_MATERIAL) {
			menuManage.createMenu();
		}
		CommonMessage message = new CommonMessage("设置成功");
		return message;
	}

	@RequestMapping("/updateMenu")
	@ResponseBody
	public CommonMessage updateMenu() {
		Boolean isSuccess = menuManage.createMenu();
		CommonMessage message = null;
		if (isSuccess) {
			message = new CommonMessage("更新菜单成功");
		} else {
			message = new CommonMessage("更新菜单失败");
		}
		return message;
	}

	@RequestMapping("/setWxUsed")
	@ResponseBody
	public CommonMessage setWxUsed(String mediaId, Boolean isUsed) {
		Material material = materialDao.findByMediaId(mediaId);
		material.setIsUsed(isUsed);
		materialDao.save(material);
		String note = isUsed ? "是" : "否";
		CommonMessage message = new CommonMessage("设置使用状态为  " + note + " 成功");
		return message;
	}
}
