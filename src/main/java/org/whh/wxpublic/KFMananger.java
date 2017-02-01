package org.whh.wxpublic;

import org.whh.util.HttpClientHelper;
import org.whh.util.WxPublicUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class KFMananger {

	private static final String GET_ONLINE_KF_LIST = "https://api.weixin.qq.com/cgi-bin/customservice/getonlinekflist?access_token=";

	public static boolean isKfOnline() {
		JSONObject param = WxPublicUtil.getPublicParam();
		String response = HttpClientHelper.post(GET_ONLINE_KF_LIST + WxPublicUtil.getAccessToken(), param);
		JSONObject object = JSONObject.parseObject(response);
		JSONArray array = object.getJSONArray("kf_online_list");
		for (Object object2 : array) {
			JSONObject obj = (JSONObject) object2;
			String ke_account = obj.getString("kf_account");// 完整客服帐号，格式为：帐号前缀@公众号微信号
			Integer status = obj.getInteger("status");// 客服在线状态，目前为：1、web 在线
			Integer accepted_case = obj.getInteger("accepted_case");// 客服当前正在接待的会话数
			Integer kf_id = obj.getInteger("kf_id");
			System.out.println("ke_account:" + ke_account + ",accepted_case:" + accepted_case + ",kf_id:" + kf_id);
			if (status == 1) {
				return true;
			}
		}
		return false;
	}
}
