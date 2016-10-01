package org.whh.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 关键词和素材的映射
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "wx_keyword_map")
public class WxKeywordMap extends EntityBase {
	private String keyword;// 关键词

	private String msgType;// 回复的消息类型
	
	
	private String content;// 回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
	private String meidaId;// 素材Id
	private String title;
	private String description;
	private String musicUrl;// 音乐链接
	private String hqMusicUrl;// 高质量音乐链接，WIFI环境优先使用该链接播放音乐
	private String thumbMediaId;// 缩略图的媒体id，通过素材管理接口上传多媒体文件，得到的id
	private String picUrl;// 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
	private String url;// 点击图文消息跳转链接

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMeidaId() {
		return meidaId;
	}

	public void setMeidaId(String meidaId) {
		this.meidaId = meidaId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMusicUrl() {
		return musicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}

	public String getHqMusicUrl() {
		return hqMusicUrl;
	}

	public void setHqMusicUrl(String hqMusicUrl) {
		this.hqMusicUrl = hqMusicUrl;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
