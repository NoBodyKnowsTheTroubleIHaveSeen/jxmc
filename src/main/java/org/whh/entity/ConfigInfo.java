package org.whh.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "config_info")
public class ConfigInfo {

	@Id
	private Long id;

	// 二维码图片素材路径
	private String groupQrCodeImgPath;

	// 群二维码图片素材id
	private String groupQrCodeMaterailId;

	// 群二维码图片素材url
	private String groupQrCodeMaterailUrl;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupQrCodeImgPath() {
		return groupQrCodeImgPath;
	}

	public void setGroupQrCodeImgPath(String groupQrCodeImgPath) {
		this.groupQrCodeImgPath = groupQrCodeImgPath;
	}

	public String getGroupQrCodeMaterailId() {
		return groupQrCodeMaterailId;
	}

	public void setGroupQrCodeMaterailId(String groupQrCodeMaterailId) {
		this.groupQrCodeMaterailId = groupQrCodeMaterailId;
	}

	public String getGroupQrCodeMaterailUrl() {
		return groupQrCodeMaterailUrl;
	}

	public void setGroupQrCodeMaterailUrl(String groupQrCodeMaterailUrl) {
		this.groupQrCodeMaterailUrl = groupQrCodeMaterailUrl;
	}

}
