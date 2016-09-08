package org.whh.wxpublic;

import org.junit.Test;
import org.whh.util.HttpClientHelper;
import org.whh.util.WeChatUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class UserManage {
	@Test
	public void t()
	{
		getUsers();
	}
	
	public String getUsers() {
		JSONObject param = WeChatUtil.getPublicParam();
		String response = HttpClientHelper.post("https://api.weixin.qq.com/cgi-bin/user/get", param);
		JSONObject object = JSONObject.parseObject(response);
		int total = object.getInteger("total");
		int count = object.getIntValue("count");
		JSONObject data = object.getJSONObject("data");
		JSONArray openIds = data.getJSONArray("openid");
		for (Object id : openIds) {
			System.out.println(id);
		}
		String nextOpenId = data.getString("next_openid");
		System.out.println("total :" + total + ",count:" + count + ",nextOpenId:" + nextOpenId);
		return response;
	}
}
