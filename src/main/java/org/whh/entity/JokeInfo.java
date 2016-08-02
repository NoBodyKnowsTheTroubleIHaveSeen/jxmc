package org.whh.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "joke_info")
public class JokeInfo extends EntityBase {
	
	public static final int AUDIT_STATUS_UNAUDIT = 1;
	
	public static final int AUDIT_STATUS_SUCCESS = 2;

	public static final int AUDIT_STATUS_FAIL = 3;
	
	public String jokeId;// 笑话ID

	public Integer agreeCount;// 赞数

	public Integer commnetCount;// 评论数
	@Lob
	public String content;// 内容
	@Lob
	public String thumb;// 图片信息

	public Integer auditStatus;// 状态：1.未审核 ,2.审核通过,3.审核不通过

	public Boolean isUsed;// 是否已被使用

	public Date useDate;// 使用时的日期

	public String getJokeId() {
		return jokeId;
	}

	public void setJokeId(String jokeId) {
		this.jokeId = jokeId;
	}

	public Integer getAgreeCount() {
		return agreeCount;
	}

	public void setAgreeCount(Integer agreeCount) {
		this.agreeCount = agreeCount;
	}

	public Integer getCommnetCount() {
		return commnetCount;
	}

	public void setCommnetCount(Integer commnetCount) {
		this.commnetCount = commnetCount;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Boolean getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}

	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}
}
