package org.whh.wd.vo;

import java.util.List;

public class ProductInfoVo {
	
	private String itemid;
	private String item_name;
	private String item_desc;
	private Integer stock;
	private String price;
	private int sold;// 商品销量
	private List<String> titles;// 图片描述
	private int free_delivery;// 是否包邮 1 不包邮，0包邮
	private int remote_free_delivery;
	private String seller_id;// 卖家id
	private int istop;// 是否店长推荐：1-是，0-不是
	private String merchant_code;// 商品编号（目前只能通过‘微店网页版’和‘商品API’设置此编码）
	private String fx_fee_rate;// 分销分成比例
	private String status;// onsale ：销售中 ,instock：已下架,delete：已删除
	private List<ProductSkuVo> skus;
	private List<ProductCateVo> cates;
	private List<String> imgs;
	private List<String> thumb_imgs;

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getItem_desc() {
		return item_desc;
	}

	public void setItem_desc(String item_desc) {
		this.item_desc = item_desc;
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

	public int getSold() {
		return sold;
	}

	public void setSold(int sold) {
		this.sold = sold;
	}

	public String getSeller_id() {
		return seller_id;
	}

	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}

	public int getIstop() {
		return istop;
	}

	public void setIstop(int istop) {
		this.istop = istop;
	}

	public String getMerchant_code() {
		return merchant_code;
	}

	public void setMerchant_code(String merchant_code) {
		this.merchant_code = merchant_code;
	}

	public String getFx_fee_rate() {
		return fx_fee_rate;
	}

	public void setFx_fee_rate(String fx_fee_rate) {
		this.fx_fee_rate = fx_fee_rate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ProductSkuVo> getSkus() {
		return skus;
	}

	public void setSkus(List<ProductSkuVo> skus) {
		this.skus = skus;
	}

	public List<String> getImgs() {
		return imgs;
	}

	public void setImgs(List<String> imgs) {
		this.imgs = imgs;
	}

	public List<String> getThumb_imgs() {
		return thumb_imgs;
	}

	public void setThumb_imgs(List<String> thumb_imgs) {
		this.thumb_imgs = thumb_imgs;
	}

	public List<ProductCateVo> getCates() {
		return cates;
	}

	public void setCates(List<ProductCateVo> cates) {
		this.cates = cates;
	}

	public List<String> getTitles() {
		return titles;
	}

	public void setTitles(List<String> titles) {
		this.titles = titles;
	}

	public int getFree_delivery() {
		return free_delivery;
	}

	public void setFree_delivery(int free_delivery) {
		this.free_delivery = free_delivery;
	}

	public int getRemote_free_delivery() {
		return remote_free_delivery;
	}

	public void setRemote_free_delivery(int remote_free_delivery) {
		this.remote_free_delivery = remote_free_delivery;
	}

}
