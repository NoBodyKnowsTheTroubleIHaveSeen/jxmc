package org.whh.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "product_cate")
public class ProductCate extends EntityBase {
	private Long appInfoId;
	private String cateId;// 商品分类id
	private String cateName;// 分类名称
	private int sortNum;// 分类的前台排序
	private boolean isRemove;//是否被移除了
	private Long srcProductCateId;//源类目id

	public Long getAppInfoId() {
		return appInfoId;
	}

	public void setAppInfoId(Long appInfoId) {
		this.appInfoId = appInfoId;
	}

	public String getCateId() {
		return cateId;
	}

	public void setCateId(String cateId) {
		this.cateId = cateId;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public int getSortNum() {
		return sortNum;
	}

	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}

	public Long getSrcProductCateId() {
		return srcProductCateId;
	}

	public void setSrcProductCateId(Long srcProductCateId) {
		this.srcProductCateId = srcProductCateId;
	}

	public boolean isRemove() {
		return isRemove;
	}

	public void setRemove(boolean isRemove) {
		this.isRemove = isRemove;
	}
	
}
