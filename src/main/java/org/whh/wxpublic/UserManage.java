package org.whh.wxpublic;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whh.dao.WxPublicUserDao;
import org.whh.entity.WxPublicUser;
import org.whh.util.HttpClientHelper;
import org.whh.util.WxPublicUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Component
public class UserManage {
	
	@Autowired
	WxPublicUserDao wxPublicUserDao;
	/**
	 * 同步微信公众号账号列表
	 * @return
	 */
	public String syncWxUser() {
		int userCountGeted = 0;
		int total = 1;
		String nextOpenId = null;
		while (userCountGeted < total) {
			JSONObject param = WxPublicUtil.getPublicParam();
			if (nextOpenId != null){
				param.put("next_openid", nextOpenId);
			}
			String response = HttpClientHelper.post("https://api.weixin.qq.com/cgi-bin/user/get?access_token="+WxPublicUtil.getAccessToken(), param);
			JSONObject object = JSONObject.parseObject(response);
			total = object.getInteger("total");
			int count = object.getIntValue("count");
			userCountGeted += count;
			JSONObject data = object.getJSONObject("data");
			JSONArray openIds = data.getJSONArray("openid");
			for (Object openId : openIds) {
				String id= (String)openId;
				WxPublicUser user = wxPublicUserDao.getByOpenId(id);
				if (user == null) {
					user = new WxPublicUser();
					user.setCreateTime(new Date());
					user.setOpenId(id);
				}else
				{
					user.setUpdateTime(new Date());
				}
				JSONObject userDetailParam = new JSONObject();
				String userDetail = HttpClientHelper.post("https://api.weixin.qq.com/cgi-bin/user/info?access_token="+WxPublicUtil.getAccessToken()+"&openid="+id, userDetailParam);
				JSONObject detail = JSONObject.parseObject(userDetail);
				user.setNickname(detail.getString("nickname"));
				Integer sex = detail.getInteger("sex");
				if (sex == 1 ) {
					user.setSex("男");
				}else if(sex == 2)
				{
					user.setSex("女");
				}
				user.setCity(detail.getString("city"));
				user.setCountry(detail.getString("country"));
				user.setProvince(detail.getString("province"));
				user.setHeadImgUrl(detail.getString("headimgurl"));
				user.setSubscribeTime(new Date(detail.getLong("subscribe_time")));
				user.setUnionId(detail.getString("unionid"));
				user.setRemark(detail.getString("remark"));
				user.setGroupId(detail.getString("groupid"));
				user.setTagIdList(detail.getString("tagid_list"));
				Integer subscribe = detail.getInteger("subscribe");
				if(subscribe == 0)
				{
					user.setSubscribe(false);
				}else{
					user.setSubscribe(true);
				}
				wxPublicUserDao.save(user);
			}
			nextOpenId = object.getString("next_openid");
			System.out.println("total :" + total + ",count:" + count + ",nextOpenId:" + nextOpenId);
		}
		return null;
	}
}

