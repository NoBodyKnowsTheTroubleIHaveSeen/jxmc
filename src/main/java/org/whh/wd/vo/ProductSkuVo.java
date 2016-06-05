package org.whh.wd.vo;

public class ProductSkuVo {
	private String id;// 型号的唯一id
	private String title;// 型号名称
	private String price;// 价格
	private int stock;// 库存
	private String sku_merchant_code;// 型号编码（目前只能通过‘微店网页版’和‘商品API’设置此编码）

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getSku_merchant_code() {
		return sku_merchant_code;
	}

	public void setSku_merchant_code(String sku_merchant_code) {
		this.sku_merchant_code = sku_merchant_code;
	}

}
