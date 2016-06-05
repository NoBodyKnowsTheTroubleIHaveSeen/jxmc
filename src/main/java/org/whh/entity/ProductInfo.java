package org.whh.entity;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "product_info")
public class ProductInfo extends EntityBase {
	private String itemId;
	private String itemName;
	private String itemDesc;
	private Integer stock;
	private String price;
	private Integer sold;// 商品销量
	@Lob
	private String titles;// 图片描述
	private Integer freeDelivery;// 是否包邮 1 不包邮，0包邮
	private Integer remoteFreeDelivery;
	private String sellerId;// 卖家id
	private Integer istop;// 是否店长推荐：1-是，0-不是
	private String merchantCode;// 商品编号（目前只能通过‘微店网页版’和‘商品API’设置此编码）
	private String fxFeeRate;// 分销分成比例
	private String status;// onsale ：销售中 ,instock：已下架,delete：已删除
	@Lob
	private String skus;
	@Lob
	private String cates;
	@Lob
	private String imgs;
	@Lob
	private String thumbImgs;

	private Long srcProductInfoId; // 源产品id

	private Long appInfoId;
	
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Integer getSold() {
		return sold;
	}

	public void setSold(Integer sold) {
		this.sold = sold;
	}

	public String getTitles() {
		return titles;
	}

	public void setTitles(String titles) {
		this.titles = titles;
	}

	public Integer getFreeDelivery() {
		return freeDelivery;
	}

	public void setFreeDelivery(Integer freeDelivery) {
		this.freeDelivery = freeDelivery;
	}

	public Integer getRemoteFreeDelivery() {
		return remoteFreeDelivery;
	}

	public void setRemoteFreeDelivery(Integer remoteFreeDelivery) {
		this.remoteFreeDelivery = remoteFreeDelivery;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public Integer getIstop() {
		return istop;
	}

	public void setIstop(Integer istop) {
		this.istop = istop;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getFxFeeRate() {
		return fxFeeRate;
	}

	public void setFxFeeRate(String fxFeeRate) {
		this.fxFeeRate = fxFeeRate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSkus() {
		return skus;
	}

	public void setSkus(String skus) {
		this.skus = skus;
	}

	public String getCates() {
		return cates;
	}

	public void setCates(String cates) {
		this.cates = cates;
	}

	public String getImgs() {
		return imgs;
	}

	public void setImgs(String imgs) {
		this.imgs = imgs;
	}

	public String getThumbImgs() {
		return thumbImgs;
	}

	public void setThumbImgs(String thumbImgs) {
		this.thumbImgs = thumbImgs;
	}

	public Long getSrcProductInfoId() {
		return srcProductInfoId;
	}

	public void setSrcProductInfoId(Long srcProductInfoId) {
		this.srcProductInfoId = srcProductInfoId;
	}

	public Long getAppInfoId() {
		return appInfoId;
	}

	public void setAppInfoId(Long appInfoId) {
		this.appInfoId = appInfoId;
	}

}
