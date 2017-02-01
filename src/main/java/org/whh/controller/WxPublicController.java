package org.whh.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.whh.base.ControllerBase;
import org.whh.dao.MaterialDao;
import org.whh.dao.WxKeywordMapDao;
import org.whh.dao.WxPublicUserDao;
import org.whh.entity.Material;
import org.whh.entity.QrCodeInfo;
import org.whh.entity.WxKeywordMap;
import org.whh.entity.WxPublicUser;
import org.whh.flush.GzhAutoSend;
import org.whh.service.QrcodeInfoService;
import org.whh.service.WxPublicUserService;
import org.whh.util.NullUtil;
import org.whh.util.PropertyUtil;
import org.whh.vo.SceneObject;
import org.whh.web.CommonMessage;
import org.whh.wxpublic.MaterailManage;
import org.whh.wxpublic.MenuManage;
import org.whh.wxpublic.MessageSendService;
import org.whh.wxpublic.UserManage;

import com.alibaba.fastjson.JSONObject;

/**
 * 微信公众号本地
 * 
 * @author Administrator
 *
 */
@Controller
public class WxPublicController extends ControllerBase {

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

	@Autowired
	WxKeywordMapDao keywordMapDao;

	@Autowired
	WxPublicUserService wxPublicUserService;

	@Autowired
	WxPublicUserDao wxPublicUserDao;

	@Autowired
	QrcodeInfoService qrcodeInfoService;

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
	public Page<WxPublicUser> getWxUser(WxPublicUser user, Pageable pageable, Date startTime, Date endTime) {
		return wxPublicUserService.findAllByPage(user, startTime, endTime, pageable);
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
		String menuTitle = nowContent.getMenuTitle();
		if (menuTitle != null && menuTitle.length() <= 15) {
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
	public CommonMessage setKeyword(Long id, String menuTitle, String keywords, String inputCode, Integer action) {
		Material material = materialDao.findOne(id);
		material.setMenuTitle(menuTitle);
		material.setInputCode(inputCode);
		material.setAction(action);
		material.setKeywords(keywords);
		materialDao.save(material);
		if (material.getMaterialStatus() != null
				&& material.getMaterialStatus() == Material.MATERIAL_STATUS_CURRENT_MATERIAL) {
			menuManage.createMenu();
		}
		if (!NullUtil.isNull(keywords)) {
			String[] words = keywords.split(";|；");
			for (String word : words) {
				WxKeywordMap map = keywordMapDao.findByKeyword(word);
				if (map == null) {
					map = new WxKeywordMap();
				}
				map.setMeidaId(material.getMediaId());
				map.setMsgType(MessageSendService.MSG_TYPE_NEWS);
				map.setTitle(material.getTitle());
				map.setDescription(material.getTitle());
				map.setPicUrl(material.getThumb_url());
				map.setUrl(material.getUrl());
				map.setKeyword(word);
				keywordMapDao.save(map);
			}
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

	/**
	 * 设为已奖励
	 * 
	 * @param id
	 * @param isReward
	 * @return
	 */
	@RequestMapping("/setReward")
	@ResponseBody
	public CommonMessage setReward(Long id, Boolean isReward) {
		WxPublicUser user = wxPublicUserDao.findOne(id);
		user.setIsReward(isReward);
		if (!isNull(isReward) && isReward) {
			user.setRewardTime(new Date());
		}
		wxPublicUserDao.save(user);
		CommonMessage message = new CommonMessage("设置状态成功");
		return message;
	}

	/**
	 * 创建二维码场景
	 * 
	 * @return
	 */
	@RequestMapping("/createSecene")
	@ResponseBody
	public CommonMessage createSecene(Long mediaId, HttpServletResponse response) {
		CommonMessage message = new CommonMessage("生成成功");
//		Material materail = materialDao.findOne(mediaId);
//		Long qrCodeInfoId = materail.getQrCodeInfoId();
//		QrCodeInfo qrCodeInfo = null;
//		SceneObject object = new SceneObject();
//		object.setType(SceneObject.TYPE_SCAN_MATERIAL);
//		JSONObject contentObjet = new JSONObject();
//		contentObjet.put("mediaId", mediaId);
//		object.setValue(JSONObject.toJSONString(contentObjet));
//		if (qrCodeInfoId == null) {
//			qrCodeInfo = qrcodeInfoService.createAndGetQrCodeInfo(object);
//			materail.setQrCodeInfoId(qrCodeInfo.getId());
//			materail.setQrCodeInfoTicket(qrCodeInfo.getTicket());
//			materialDao.save(materail);
//		} else {
//			qrCodeInfo = qrcodeInfoService.createAndGetQrCodeInfo(object);
//		}
		return message;
	}

	/**
	 * 根据素材id获得素材内容
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/getMaterialContent")
	@ResponseBody
	public String getMaterialContent(Long id) {
		Material material = materialDao.findOne(id);
		return material.getContent();
	}
}
