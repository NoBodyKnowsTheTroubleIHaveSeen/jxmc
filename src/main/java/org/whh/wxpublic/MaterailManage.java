package org.whh.wxpublic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whh.dao.MaterialDao;
import org.whh.entity.Material;
import org.whh.util.HttpClientHelper;
import org.whh.util.NullUtil;
import org.whh.util.WxPublicUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Component
public class MaterailManage {
	@Autowired
	MaterialDao materialDao;
//	public static void main(String[] args) throws IOException {
//		new MaterailManage().batchGetMaterial(Integer.MAX_VALUE);
//	}
	public static String addImage(String filePath) {
		String addUrl = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token="
				+ WxPublicUtil.getAccessToken();
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("type", "image"));
		String response = HttpClientHelper.uploadFile(addUrl, pairs, new File(filePath));
		return response;
	}
	public static String delMaterial(String mediaId) {
		String delUrl = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token="
				+ WxPublicUtil.getAccessToken();
		JSONObject artical = new JSONObject();
		artical.put("media_id", mediaId);
		String response = HttpClientHelper.post(delUrl, artical);
		return response;
	}

	public void addNews() throws IOException {
		String addUrl = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token="
				+ WxPublicUtil.getAccessToken();
		JSONArray array = new JSONArray();
		JSONObject artical = new JSONObject();
		artical.put("title", "fuck you this is titile");
		artical.put("thumb_media_id", "_cTL3zLnzUlRiCf6wxYig0tHfeyaUQFCcx18sHQlEI0");
		artical.put("author", "fuck you this is author");
		artical.put("digest", "fuck you this is是是答复 digest");
		artical.put("content", "fuck you this测试是是是 is content");
		artical.put("show_cover_pic", 1);
		artical.put("content_source_url", "http://fuck.com");
		array.add(artical);
		JSONObject param = new JSONObject();
		param.put("articles", array);
		String response = HttpClientHelper.post(addUrl, param);
		System.out.println(response);
	}

	/**
	 * 加载素材
	 */
	public void batchGetMaterial(int size) {
		String materailUrl = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token="
				+ WxPublicUtil.getAccessToken();
		int offset = 0;
		int maxCount = 20;// 一次请求最大允许加载的图文数量
		int totalLoaded = 0;
		int totalCount = 1;
		while (totalLoaded < totalCount) {
			JSONObject param = new JSONObject();
			param.put("type", "news");
			param.put("offset", offset);
			param.put("count", maxCount);
			String response = HttpClientHelper.post(materailUrl, param);
			JSONObject result = JSONObject.parseObject(response);
			if (!NullUtil.isNull(result.getString("errcode"))) {
				return;
			}
			totalCount = result.getInteger("total_count");
			if (size < totalCount) {
				totalCount = size;
			}
			if (totalCount < 1) {
				return;
			}
			int itemCount = result.getInteger("item_count");
			totalLoaded += itemCount;
			JSONArray array = result.getJSONArray("item");
			for (Object object : array) {
				JSONObject obj = (JSONObject) object;
				String mediaId = obj.getString("media_id");
				JSONObject contents = obj.getJSONObject("content");
				JSONArray items = contents.getJSONArray("news_item");
				for (Object item : items) {
					JSONObject ite = (JSONObject) item;
					String title = ite.getString("title");
					String thumbMediaId = ite.getString("thumb_media_id");
					String author = ite.getString("author");
					String contentSourceUrl = ite.getString("content_source_url");
					Boolean showCoverPic = ite.getBoolean("show_cover_pic");
					String thumbUrl = ite.getString("thumb_url");
					String digest = ite.getString("digest");
					String content = ite.getString("content");
					String url = ite.getString("url");
					String name = ite.getString("name");
					Material material = materialDao.findByMediaId(mediaId);
					if (NullUtil.isNull(material)) {
						material = new Material();
						material.setCreateTime(new Date());
					}
					material.setDigest(digest);
					material.setMediaId(mediaId);
					material.setShow_cover_pic(showCoverPic);
					material.setThumb_url(thumbUrl);
					material.setTitle(title);
					material.setThumb_media_id(thumbMediaId);
					material.setAuthor(author);
					material.setUrl(url);
					material.setContent(content);
					material.setContent_source_url(contentSourceUrl);
					material.setName(name);
					material.setOffset(offset++);
					materialDao.save(material);
				}
			}
		}
	}
}
