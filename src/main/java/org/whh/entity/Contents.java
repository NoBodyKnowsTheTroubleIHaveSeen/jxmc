package org.whh.entity;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "contents")
public class Contents extends EntityBase {

	/**
	 * 源标志ID
	 */
	public static final int SRC_ID_SAN_WEN = 1;// 散文网
	public static final int SRC_ID_GUO_KER = 2;//果壳网

	public static final int Type_JOKE = 1;// 笑话
	public static final int Type_ARTIACL = 2;// 文章
	public static final int Type_RAW = 3;// 干货
	public static final int Type_LIFE = 4;// 生活

	@Lob
	public String content;
	public Integer type;// 内容所属类型，如哲文，干货
	public Boolean isUsed;
	public Long vote;
	public String srcUrl;// 源地址
	public String srcUuid;// 源唯一标识符
	public Integer srcId;// 采集源的Id

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getVote() {
		return vote;
	}

	public void setVote(Long vote) {
		this.vote = vote;
	}

	public String getSrcUrl() {
		return srcUrl;
	}

	public void setSrcUrl(String srcUrl) {
		this.srcUrl = srcUrl;
	}

	public String getSrcUuid() {
		return srcUuid;
	}

	public void setSrcUuid(String srcUuid) {
		this.srcUuid = srcUuid;
	}

	public Integer getSrcId() {
		return srcId;
	}

	public void setSrcId(Integer srcId) {
		this.srcId = srcId;
	}

}
