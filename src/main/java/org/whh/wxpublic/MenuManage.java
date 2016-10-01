package org.whh.wxpublic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whh.dao.MaterialDao;
import org.whh.entity.Material;
import org.whh.util.HttpClientHelper;
import org.whh.util.WxPublicUtil;

import com.alibaba.fastjson.JSONObject;

@Component
public class MenuManage {

	@Autowired
	MaterialDao materialDao;

	private boolean create() {
		String deleteUrl = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="
				+ WxPublicUtil.getAccessToken();
		String delResponse = HttpClientHelper.post(deleteUrl, new JSONObject());
		System.out.println(delResponse);
		String createUrl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="
				+ WxPublicUtil.getAccessToken();
		ArrayList<WxButton> buttons = new ArrayList<WxButton>();
		WxButton xdBtn = new WxButton();
		xdBtn.setName("小店");

		WxButton dpRecommendBtn = new WxButton();
		dpRecommendBtn.setName("店铺推荐");
		dpRecommendBtn.setType("view");
		dpRecommendBtn.setUrl("https://weidian.com/item.html?itemID=1690101947&p=0");

		WxButton mcBtn = new WxButton();
		mcBtn.setName("铁观音蜜茶");
		mcBtn.setType("view");
		mcBtn.setUrl("http://weidian.com/item_classes.html?userid=839209114&c=78292631");

		WxButton dpBtn = new WxButton();
		dpBtn.setName("进入店铺");
		dpBtn.setType("view");
		dpBtn.setUrl("http://weidian.com/?userid=839209114");

		ArrayList<WxButton> xdBtnList = new ArrayList<WxButton>();
		xdBtnList.add(mcBtn);
		xdBtnList.add(dpRecommendBtn);
		xdBtnList.add(dpBtn);
		xdBtn.setSub_button(xdBtnList);
		/**
		 * 茶·生活菜单
		 */

		WxButton liftBtn = new WxButton();
		liftBtn.setName("茶·生活");

		WxButton cpBtn = new WxButton();
		cpBtn.setName("铁观音冲泡视频");
		cpBtn.setType("media_id");
		cpBtn.setMedia_id("90hC4K9jq_d_P3Ywgl9I2VFCMcnt85MyHx0I4hHHXQs");

		WxButton bqBtn = new WxButton();
		Material current = materialDao.findCurrentContent();
		if (current.getKeyword() != null && current.getKeyword().length() <= 15) {
			bqBtn.setName(current.getKeyword());
		} else {
			bqBtn.setName("本期精彩");
		}
		bqBtn.setType("media_id");
		bqBtn.setMedia_id(current.getMediaId());

		WxButton randomBtn = new WxButton();
		randomBtn.setName("随机推荐");
		randomBtn.setType("click");
		randomBtn.setKey("3");

		WxButton historyBtn = new WxButton();
		historyBtn.setName("往期精彩");
		historyBtn.setType("view");
		historyBtn.setUrl(
				"http://mp.weixin.qq.com/mp/getmasssendmsg?__biz=MzAwMjk0MDYxNA==#wechat_webview_type=1&wechat_redirect");

		ArrayList<WxButton> liftBtnList = new ArrayList<WxButton>();
		liftBtnList.add(cpBtn);
		liftBtnList.add(historyBtn);
		liftBtnList.add(randomBtn);
		liftBtnList.add(bqBtn);
		liftBtn.setSub_button(liftBtnList);
		/**
		 * 服务
		 */
		WxButton serviceBtn = new WxButton();
		serviceBtn.setName("服务");

		WxButton helpBtn = new WxButton();
		helpBtn.setName("帮助");
		helpBtn.setType("media_id");
		helpBtn.setMedia_id("90hC4K9jq_d_P3Ywgl9I2XQIqtnHGvTiIVNZaXCIVOA");

		WxButton kdBtn = new WxButton();
		kdBtn.setName("快递查询");
		kdBtn.setType("view");
		kdBtn.setUrl("http://www.kuaidi100.com/");

		WxButton contactBtn = new WxButton();
		contactBtn.setName("关于我们");
		contactBtn.setType("media_id");
		contactBtn.setMedia_id("90hC4K9jq_d_P3Ywgl9I2YZeXnEtJEHM7dn_HWGD3_o");

		WxButton recommendBtn = new WxButton();
		recommendBtn.setName("推荐有礼");
		recommendBtn.setType("click");
		recommendBtn.setKey("4");

		ArrayList<WxButton> serviceBtnList = new ArrayList<WxButton>();
		serviceBtnList.add(helpBtn);
		serviceBtnList.add(kdBtn);
		serviceBtnList.add(contactBtn);
		serviceBtnList.add(recommendBtn);
		serviceBtn.setSub_button(serviceBtnList);

		/**
		 * 菜单添加
		 */
		buttons.add(xdBtn);
		buttons.add(liftBtn);
		buttons.add(serviceBtn);
		JSONObject param = new JSONObject();
		param.put("button", buttons);
		String response = HttpClientHelper.post(createUrl, param);
		JSONObject result = JSONObject.parseObject(response);
		int errorCode = result.getInteger("errcode");
		System.out.println(response);
		return errorCode == 0;
	}

	public boolean createMenu() {
		boolean firstSuccess = create();
		boolean secondSuccess = create();
		return firstSuccess || secondSuccess;
	}

}

class WxButton {
	String name;// 菜单名称

	/**
	 * 1、click：点击推事件 用户点击click类型按钮后，微信服务器会通过消息接口推送消息类型为event
	 * 的结构给开发者（参考消息接口指南），并且带上按钮中开发者填写的key值，开发者可以通过自定义的key值与用户进行交互； 2、view：跳转URL
	 * 用户点击view类型按钮后，微信客户端将会打开开发者在按钮中填写的网页URL，可与网页授权获取用户基本信息接口结合，获得用户基本信息。
	 * 3、scancode_push：扫码推事件
	 * 用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后显示扫描结果（如果是URL，将进入URL），且会将扫码的结果传给开发者，
	 * 开发者可以下发消息。 4、scancode_waitmsg：扫码推事件且弹出“消息接收中”提示框
	 * 用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后，将扫码的结果传给开发者，同时收起扫一扫工具，然后弹出“消息接收中”提示框，
	 * 随后可能会收到开发者下发的消息。 5、pic_sysphoto：弹出系统拍照发图
	 * 用户点击按钮后，微信客户端将调起系统相机，完成拍照操作后，会将拍摄的相片发送给开发者，并推送事件给开发者，同时收起系统相机，
	 * 随后可能会收到开发者下发的消息。 6、pic_photo_or_album：弹出拍照或者相册发图
	 * 用户点击按钮后，微信客户端将弹出选择器供用户选择“拍照”或者“从手机相册选择”。用户选择后即走其他两种流程。
	 * 7、pic_weixin：弹出微信相册发图器
	 * 用户点击按钮后，微信客户端将调起微信相册，完成选择操作后，将选择的相片发送给开发者的服务器，并推送事件给开发者，同时收起相册，
	 * 随后可能会收到开发者下发的消息。 8、location_select：弹出地理位置选择器
	 * 用户点击按钮后，微信客户端将调起地理位置选择工具，完成选择操作后，将选择的地理位置发送给开发者的服务器，同时收起位置选择工具，
	 * 随后可能会收到开发者下发的消息。 9、media_id：下发消息（除文本消息）
	 * 用户点击media_id类型按钮后，微信服务器会将开发者填写的永久素材id对应的素材下发给用户，永久素材类型可以是图片、音频、视频、图文消息。
	 * 请注意：永久素材id必须是在“素材管理/新增永久素材”接口上传后获得的合法id。 10、view_limited：跳转图文消息URL
	 * 用户点击view_limited类型按钮后，微信客户端将打开开发者在按钮中填写的永久素材id对应的图文消息URL，永久素材类型只支持图文消息。
	 * 请注意：永久素材id必须是在“素材管理/新增永久素材”接口上传后获得的合法id。
	 */
	String type;// 类型

	String key;

	String url;

	String media_id;

	List<WxButton> sub_button;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}

	public List<WxButton> getSub_button() {
		return sub_button;
	}

	public void setSub_button(List<WxButton> sub_button) {
		this.sub_button = sub_button;
	}

}
