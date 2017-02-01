package org.whh.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "material")
public class Material extends EntityBase {

	public static final int TYPE_JOKE = 1;// 笑话
	public static final int TYPE_CONTENT = 2;// 文章
	public static final int TYPE_TEA = 3;// 茶叶相关
	public static final int TYPE_OTHER = 4;// 其他
	public static final int TYPE_LOCAL_URL = 5;//本网站url素材
	/**
	 * title 图文消息的标题 thumb_media_id 图文消息的封面图片素材id（必须是永久mediaID） show_cover_pic
	 * 是否显示封面，0为false，即不显示，1为true，即显示 author 作者 digest
	 * 图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空 content
	 * 图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS url
	 * 图文页的URL，或者，当获取的列表是图片素材列表时，该字段是图片的URL content_source_url
	 * 图文消息的原文地址，即点击“阅读原文”后的URL update_time 这篇图文消息素材的最后更新时间 name 文件名称
	 */
	private String mediaId;
	private String title;
	private String thumb_media_id;
	private Boolean show_cover_pic;
	private String author;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(columnDefinition = "longtext", nullable = true)
	private String digest;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(columnDefinition = "longtext", nullable = true)
	private String content;
	private String url;
	private String content_source_url;
	private String name;
	private String thumb_url;

	private Boolean isUsed;// 是否已使用

	private int offset;// 素材的偏移量

	private int type;// 素材类型

	private Integer materialStatus;// 素材状态，0.正常状态,1.当日笑话素材,2.本期素材
	
	private String menuTitle;//关键词,作为菜单时的关键词(本期文章/笑话)
	
	private String inputCode;//用户输入码
	
	private Integer action;//素材关联的动作
	
	private String keywords;
	
	private Long qrCodeInfoId;
	
	private String qrCodeInfoTicket;//二维码场景
	
	public static Integer ACTION_PUSH_RECOMMEND = 1;//推送推荐有礼信息

	public static int MATERIAL_STATUS_NORMAL = 0;
	
	public static int MATERIAL_STATUS_CURRENT_JOKE = 1;
	
	public static int MATERIAL_STATUS_CURRENT_MATERIAL = 2;

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getThumb_media_id() {
		return thumb_media_id;
	}

	public void setThumb_media_id(String thumb_media_id) {
		this.thumb_media_id = thumb_media_id;
	}

	public Boolean getShow_cover_pic() {
		return show_cover_pic;
	}

	public void setShow_cover_pic(Boolean show_cover_pic) {
		this.show_cover_pic = show_cover_pic;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent_source_url() {
		return content_source_url;
	}

	public void setContent_source_url(String content_source_url) {
		this.content_source_url = content_source_url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}

	public String getThumb_url() {
		return thumb_url;
	}

	public void setThumb_url(String thumb_url) {
		this.thumb_url = thumb_url;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Integer getMaterialStatus() {
		return materialStatus;
	}

	public void setMaterialStatus(Integer materialStatus) {
		this.materialStatus = materialStatus;
	}

	public String getMenuTitle() {
		return menuTitle;
	}

	public void setMenuTitle(String menuTitle) {
		this.menuTitle = menuTitle;
	}

	public String getInputCode() {
		return inputCode;
	}

	public void setInputCode(String inputCode) {
		this.inputCode = inputCode;
	}

	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Long getQrCodeInfoId() {
		return qrCodeInfoId;
	}

	public void setQrCodeInfoId(Long qrCodeInfoId) {
		this.qrCodeInfoId = qrCodeInfoId;
	}

	public String getQrCodeInfoTicket() {
		return qrCodeInfoTicket;
	}

	public void setQrCodeInfoTicket(String qrCodeInfoTicket) {
		this.qrCodeInfoTicket = qrCodeInfoTicket;
	}


}
