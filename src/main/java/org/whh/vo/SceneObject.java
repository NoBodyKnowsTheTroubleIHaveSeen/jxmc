package org.whh.vo;

/**
 * 生成二维码的参数值
 */
public class SceneObject {

	public static final int TYPE_RECOMMEND = 1;// 好友推荐

	public static final int TYPE_SCAN_MATERIAL = 2;// 扫描微信素材

	public static final int TYPE_SCAN_URL = 3;// 扫描URL

	/**
	 * 类型
	 */
	public int type;

	public String value;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
