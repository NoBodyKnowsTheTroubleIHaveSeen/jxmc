package org.whh.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "product_images")
public class ProductImages extends EntityBase {
	private Long appInfoId;// 所属用户id
	private Long srcProductInfoId;// 源产品id
	private String sourceFileName;//源图片文件名
	private String wdUrl;// 上传返回的地址

	public Long getAppInfoId() {
		return appInfoId;
	}

	public void setAppInfoId(Long appInfoId) {
		this.appInfoId = appInfoId;
	}

	public Long getSrcProductInfoId() {
		return srcProductInfoId;
	}

	public void setSrcProductInfoId(Long srcProductInfoId) {
		this.srcProductInfoId = srcProductInfoId;
	}

	public String getWdUrl() {
		return wdUrl;
	}

	public void setWdUrl(String wdUrl) {
		this.wdUrl = wdUrl;
	}

	public String getSourceFileName() {
		return sourceFileName;
	}

	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}

}
