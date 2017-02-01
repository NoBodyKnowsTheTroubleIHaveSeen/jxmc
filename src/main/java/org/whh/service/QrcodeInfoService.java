package org.whh.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.whh.base.ServiceBase;
import org.whh.dao.QrCodeInfoDao;
import org.whh.entity.QrCodeInfo;
import org.whh.vo.SceneObject;
import org.whh.wxpublic.QrCodeMange;
import com.alibaba.fastjson.JSONObject;

@Service
public class QrcodeInfoService extends ServiceBase {

	@Autowired
	QrCodeInfoDao qrCodeInfoDao;

	/**
	 * 创建/获取QrcodeInfo,如果已存在，则直接获取
	 * 
	 * @param originUser
	 * @param object
	 */
	public QrCodeInfo createAndGetQrCodeInfo(SceneObject object) {
		QrCodeInfo info = qrCodeInfoDao.findByActionNameAndSceneStr(QrCodeInfo.ACTION_NAME_QR_LIMIT_STR_SCENE,
				JSONObject.toJSONString(object));
		if (info == null) {
			JSONObject code = QrCodeMange.generateLimitSrcCode(object);
			if (code.getString("ticket") == null) {
				throw new RuntimeException("生成ticket失败!");
			}
			info = new QrCodeInfo();
			info.setActionName(QrCodeInfo.ACTION_NAME_QR_LIMIT_STR_SCENE);
			info.setCreateTime(new Date());
			info.setSceneStr(JSONObject.toJSONString(object));
			info.setTicket(code.getString("ticket"));
			info.setUrl(code.getString("url"));
			qrCodeInfoDao.save(info);
		}
		return info;
	}
}
