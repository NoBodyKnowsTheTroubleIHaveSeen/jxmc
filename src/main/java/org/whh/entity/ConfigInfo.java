package org.whh.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "config_info")
public class ConfigInfo 
{
	
	@Id
	private Long id;
	//群二维码图片素材id
	private String groupQrCodeMaterailId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupQrCodeMaterailId() {
		return groupQrCodeMaterailId;
	}

	public void setGroupQrCodeMaterailId(String groupQrCodeMaterailId) {
		this.groupQrCodeMaterailId = groupQrCodeMaterailId;
	}
	
	
}
