package org.whh.wd;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whh.dao.ProductCateDao;
import org.whh.dao.WdAppInfoDao;
import org.whh.entity.ProductCate;
import org.whh.entity.WdAppInfo;
import org.whh.wd.vo.ProductCateVo;
import org.whh.wd.vo.StatusResult;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 类目辅助类
 * 
 * @author ASUS-PC
 *
 */
@Component
public class CateHelper extends WdInterfaceBase {

	@Autowired
	ProductCateDao dao;

	@Autowired
	WdAppInfoDao appInfoDao;

	/**
	 * 添加单个类目,仅支持非源
	 * 
	 * @param appInfoId
	 * @param vo
	 * @return
	 */

	public StatusResult addCate(Long appInfoId, ProductCateVo vo) {
		ArrayList<ProductCateVo> cates = new ArrayList<ProductCateVo>();
		cates.add(vo);
		return addCate(appInfoId, cates);
	}

	/**
	 * 添加类目,并将添加完成后的类目同步到本地
	 * 
	 * @param cates
	 * @return
	 */

	public StatusResult addCate(Long appInfoId, List<ProductCateVo> cates) {
		StatusResult statusResult = null;
		if (cates == null) {
			return null;
		}
		for (ProductCateVo productCate : cates) {
			try {
				productCate.setCate_id(null);
				JSONObject params = new JSONObject();
				JSONArray array = new JSONArray();
				array.add(productCate);
				String catesString = JSONObject.toJSONString(array);
				params.put("cates", catesString);
				List<NameValuePair> pairs = new ArrayList<NameValuePair>();
				pairs.add(new BasicNameValuePair("param", params.toJSONString()));
				pairs.add(new BasicNameValuePair("public", getPublicParam(appInfoId, "vdian.shop.cate.add")));
				String response = post(appInfoId, pairs);
				statusResult = JSONObject.parseObject(response, StatusResult.class);
			} catch (Exception e) {
			}
		}
		return statusResult;
	}

	/**
	 * 移除类目，接口一次只支持删除一个类目
	 * 
	 * @param cateId
	 * @return
	 */

	public StatusResult removeCate(Long appInfoId, int cateId) {
		JSONObject param = new JSONObject();
		param.put("cate_id", cateId);
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("param", param.toJSONString()));
		pairs.add(new BasicNameValuePair("public", getPublicParam(appInfoId, "vdian.shop.cate.delete")));
		String response = post(appInfoId, pairs);
		StatusResult statusResult = JSONObject.parseObject(response, StatusResult.class);
		return statusResult;
	}

	public StatusResult updateCate(Long appInfoId, ProductCateVo productCateVo) {
		List<ProductCateVo> vos = new ArrayList<ProductCateVo>();
		vos.add(productCateVo);
		return updateCate(appInfoId, vos);
	}

	/**
	 * 更新类目信息,仅能更新已存在的类目
	 * 
	 * @param cates
	 * @return
	 */

	public StatusResult updateCate(Long appInfoId, List<ProductCateVo> cates) {
		JSONObject params = new JSONObject();
		String catesString = JSONObject.toJSONString(cates);
		params.put("cates", catesString);
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("param", params.toJSONString()));
		pairs.add(new BasicNameValuePair("public", getPublicParam(appInfoId, "vdian.shop.cate.update")));
		String response = post(appInfoId, pairs);
		StatusResult statusResult = JSONObject.parseObject(response, StatusResult.class);
		return statusResult;
	}

	/**
	 * 通过接口获取所有类目
	 * 
	 * @return
	 */
	private List<ProductCateVo> getAllCate(Long appInfoId) {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("param", "{}"));
		pairs.add(new BasicNameValuePair("public", getPublicParam(appInfoId, "vdian.shop.cate.get")));
		String response = post(appInfoId, pairs);
		JSONObject object = JSONObject.parseObject(response);
		List<ProductCateVo> cates = JSONObject.parseArray(object.getString("result"), ProductCateVo.class);
		return cates;
	}

	/**
	 * 同步并保存类目信息到本地
	 * 
	 * @param appInfoId
	 */
	public List<ProductCateVo> syncCate(Long appInfoId, boolean isSrcCate) {
		List<ProductCateVo> vos = getAllCate(appInfoId);
		List<ProductCate> cates = new ArrayList<ProductCate>();
		Long srcAppInfoId = null;
		if (!isSrcCate) {
			List<ProductCate> oldCates = dao.findByAppInfoId(appInfoId);
			dao.delete(oldCates);
			WdAppInfo info = appInfoDao.findOne(appInfoId);
			srcAppInfoId = info.getSrcWdAppInfoId();
		}
		for (ProductCateVo productCateVo : vos) {
			ProductCate cate = dao.findByAppInfoIdAndCateId(appInfoId, productCateVo.getCate_id());
			if (cate == null) {
				cate = new ProductCate();
				cate.setCreateTime(new Date());
				cate.setAppInfoId(appInfoId);
				cate.setCateId(productCateVo.getCate_id());
			}
			cate.setCateName(productCateVo.getCate_name());
			cate.setSortNum(productCateVo.getSort_num());
			cate.setUpdateTime(new Date());
			if (!isSrcCate) {
				ProductCate srCate = dao.findByAppInfoIdAndCateNameAndIsRemove(srcAppInfoId,
						productCateVo.getCate_name(), false);
				if (srCate != null) {
					cate.setSrcProductCateId(srCate.getId());
				}
			}
			cates.add(cate);

		}
		dao.save(cates);
		return vos;
	}

	public void sync(Long srcAppInfoId)
	{
		sync(srcAppInfoId);
	}
	/**
	 * 
	 * 从远程同步类目到数据库 将已删除的类目标记为已删除，并找到其他所有未删除的类目，删除 遍历其他类目信息，如果没有找到就添加
	 * 
	 * @param srcAppInfoId
	 */
	public void sync(Long srcAppInfoId, Long toAppInfoId,Boolean isSyncAll) {
		List<ProductCate> oldCates = dao.findByAppInfoId(srcAppInfoId);
		List<String> newCateIds = new ArrayList<String>();
		List<ProductCateVo> vos = syncCate(srcAppInfoId, true);
		for (ProductCateVo productCateVo : vos) {
			newCateIds.add(productCateVo.getCate_id());
		}
		for (ProductCate oldCate : oldCates) {
			boolean isFind = false;
			String oldCateId = oldCate.getCateId();
			for (String newCateId : newCateIds) {
				if (newCateId.equals(oldCateId)) {
					isFind = true;
					break;
				}
			}
			if (!isFind) {
				oldCate.setRemove(true);
				List<ProductCate> unRemoveCates = dao.findBySrcProductCateIdAndCateNameAndIsRemove(oldCate.getId(),
						oldCate.getCateName(), false);
				for (ProductCate productCate : unRemoveCates) {
					StatusResult result = removeCate(productCate.getAppInfoId(),
							Integer.parseInt(productCate.getCateId()));
					if (result.getStatus().getStatus_code() == 0) {
						productCate.setRemove(true);
						dao.save(productCate);
					}
				}
				dao.save(oldCate);
			}
		}
		Iterable<WdAppInfo> infos = appInfoDao.getBySrcWdAppInfoId(srcAppInfoId);
		for (ProductCateVo productCateVo : vos) {
			String cateName = productCateVo.getCate_name();
			for (WdAppInfo wdAppInfo : infos) {
				if (isSyncAll || wdAppInfo.getId().equals(toAppInfoId)) {
					ProductCate cate = dao.findByAppInfoIdAndCateNameAndIsRemove(wdAppInfo.getId(), cateName, false);
					if (cate != null) {
						productCateVo.setCate_id(cate.getCateId());
						updateCate(wdAppInfo.getId(), productCateVo);
					} else {
						productCateVo.setCate_id(null);
						addCate(wdAppInfo.getId(), productCateVo);
					}

				}
			}
		}
		for (WdAppInfo wdAppInfo : infos) {
			if (isSyncAll|| wdAppInfo.getId().equals(toAppInfoId)) {
				syncCate(wdAppInfo.getId(), false);
			}
		}
	}
}
