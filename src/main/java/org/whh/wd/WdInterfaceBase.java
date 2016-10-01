package org.whh.wd;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.apache.http.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whh.dao.WdAppInfoDao;
import org.whh.entity.WdAppInfo;
import org.whh.util.HttpClientHelper;
import org.whh.wd.vo.AccessTokenInfo;

import com.alibaba.fastjson.JSONObject;

@Component
public class WdInterfaceBase {

	private final String grantType = "client_credential";
	private final String wdApiAddress = "https://api.vdian.com/api";

	@Autowired
	private WdAppInfoDao dao;

	/**
	 * 获取token
	 * 
	 * @return
	 */
	public String getAccessToken(Long appInfoId) {
		WdAppInfo info = dao.findOne(appInfoId);
		String accessToken = info.getAccessToken();
		Date freshTime = info.getLastFreshTime();
		if (accessToken == null || info.getExpire() == null
				|| System.currentTimeMillis() - freshTime.getTime() > info.getExpire() * 1000) {
			AccessTokenInfo tokenInfo = refreshAccessToken(appInfoId);
			info.setAccessToken(tokenInfo.getAccess_token());
			info.setExpire(tokenInfo.getExpire_in());
			info.setLastFreshTime(new Date());
			dao.save(info);
			return tokenInfo.getAccess_token();
		}
		return accessToken;
	}

	/**
	 * 刷新toekn
	 * 
	 * @return
	 */
	private AccessTokenInfo refreshAccessToken(Long appInfoId) {
		WdAppInfo info = dao.findOne(appInfoId);
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("grant_type", grantType);
		params.put("appkey", info.getAppKey());
		params.put("secret", info.getSecret());
		String param = HttpClientHelper.getRequestParam(params);
		String response = HttpClientHelper.get("https://api.vdian.com/token", param);
		JSONObject object = JSONObject.parseObject(response);
		AccessTokenInfo toeknInfo = JSONObject.parseObject(object.getString("result"), AccessTokenInfo.class);
		return toeknInfo;
	}

	/**
	 * 获得公用参数
	 * 
	 * @param method
	 * @return
	 */
	public String getPublicParam(Long id, String method) {
		return getPublicParam(id, method, "1.0");
	}

	public String getPublicParam(Long id, String method, String version) {
		JSONObject params = new JSONObject();
		params.put("method", method);
		params.put("access_token", getAccessToken(id));
		params.put("format", "json");
		params.put("version", version);
		return params.toString();
	}

	/**
	 * 基础post方法
	 * 
	 * @param pairs
	 * @return
	 */
	public String post(Long appInfoId, List<NameValuePair> pairs) {
		String response = HttpClientHelper.post(wdApiAddress, pairs);
		if (response.contains("access_token无效")) {
			refreshAccessToken(appInfoId);
		}
		return response;
	}
}
