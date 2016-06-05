package org.whh.wd.vo;

/**
 * 产品类目信息
 * 
 * @author ASUS-PC
 *
 */
public class ProductCateVo {

	private String cate_id;// 商品分类id
	private String cate_name;// 分类名称
	private int sort_num;// 分类的前台排序

	public String getCate_id() {
		return cate_id;
	}

	public void setCate_id(String cate_id) {
		this.cate_id = cate_id;
	}

	public String getCate_name() {
		return cate_name;
	}

	public void setCate_name(String cate_name) {
		this.cate_name = cate_name;
	}

	public int getSort_num() {
		return sort_num;
	}

	public void setSort_num(int sort_num) {
		this.sort_num = sort_num;
	}

}
