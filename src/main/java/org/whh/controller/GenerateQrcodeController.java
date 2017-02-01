package org.whh.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.whh.dao.MaterialDao;
import org.whh.dao.QrCodeInfoDao;
import org.whh.entity.Material;
import org.whh.entity.QrCodeInfo;
import org.whh.service.QrcodeInfoService;
import org.whh.vo.SceneObject;
import org.whh.web.CommonMessage;

import com.alibaba.fastjson.JSONObject;

@Controller
public class GenerateQrcodeController {
	@Autowired
	QrcodeInfoService qrcodeInfoService;
	@Autowired
	QrCodeInfoDao qrcodeInfoDao;
	@Autowired
	MaterialDao materialDao;

	@RequestMapping("/generateQrcode")
	public String generateQrcode(Model model) {
		return "content/qrcode";
	}

	@RequestMapping("/doGenerateQrcode")
	@ResponseBody
	public CommonMessage doGenerateQrcode(String url, String title, HttpServletResponse response) {
		CommonMessage message = new CommonMessage("生成成功");
		SceneObject object = new SceneObject();
		object.setType(SceneObject.TYPE_SCAN_URL);
		JSONObject contentObjet = new JSONObject();
		String materailUrl = "https://whhwkm.xicp.net/common/artical?title=" + title + "&chapter=1";
		Material material = materialDao.findByUrl(materailUrl);
		if (material != null && material.getQrCodeInfoId() != null) {
			return message;	
		}
		material = new Material();
		material.setUrl(materailUrl);
		material.setType(Material.TYPE_LOCAL_URL);
		material.setTitle(title);
		materialDao.save(material);
		contentObjet.put("mediaId", material.getId());
		object.setValue(JSONObject.toJSONString(contentObjet));
		QrCodeInfo info = qrcodeInfoService.createAndGetQrCodeInfo(object);
		material.setQrCodeInfoTicket(info.getTicket());
		material.setQrCodeInfoId(info.getId());
		materialDao.save(material);
		return message;
	}

	@ResponseBody
	@RequestMapping("/getGenerateQrcode")
	public Page<QrCodeInfo> getGenerateQrcode(Pageable pageable) {
		PageRequest request = new PageRequest(pageable.getPageNumber(), pageable.getPageSize());
		Page<QrCodeInfo> result = qrcodeInfoDao.findAll(request);
		return result;
	}
}
