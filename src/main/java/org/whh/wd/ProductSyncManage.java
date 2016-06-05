package org.whh.wd;
/**
 * 多网店间的同步
 * 
 * @author ASUS-PC
 *
 */
public interface ProductSyncManage {
	
	/**
	 * 从网店产品同步类目和网店产品
	 */
	void sync(Long appInfoId);
	/**
	 * 同步网店的类目
	 * @param appInfoId
	 */
	void syncCate(Long appInfoId);
	/**
	 * 同步产品到其他网店
	 */
	void syncProductToAll(Long appId);

}
