package org.whh.wd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whh.dao.ProductCateDao;
import org.whh.dao.ProductImagesDao;
import org.whh.dao.ProductInfoDao;
import org.whh.dao.WdAppInfoDao;
import org.whh.entity.ProductCate;
import org.whh.entity.ProductImages;
import org.whh.entity.ProductInfo;
import org.whh.entity.WdAppInfo;
import org.whh.util.HttpClientHelper;
import org.whh.wd.vo.ProductCateVo;
import org.whh.wd.vo.ProductInfoVo;
import org.whh.wd.vo.ProductSkuVo;
import org.whh.wd.vo.StatusResult;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Component
public class ProductHelper extends WdInterfaceBase {

	public static final int ADD = 1;
	public static final int UPDATE = 2;
	private Logger logger = LoggerFactory.getLogger(ProductHelper.class);
	@Autowired
	ProductInfoDao productInfoDao;

	@Autowired
	ProductCateDao productCateDao;

	@Autowired
	ProductImagesDao productImagesDao;
	@Autowired
	WdAppInfoDao wdAppInfoDao;

	/**
	 * 获取商品id列表
	 * 
	 * @param appInfoId
	 * @return
	 */
	private List<String> getAllItemIds(Long appInfoId) {
		JSONObject params = new JSONObject();
		params.put("page_size", 200);
		params.put("page_num", 0);
		params.put("status", 1);
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("param", params.toJSONString()));
		pairs.add(new BasicNameValuePair("public", getPublicParam(appInfoId, "vdian.item.list.get")));
		String response = post(appInfoId, pairs);
		JSONObject object = JSONObject.parseObject(response);
		JSONObject res = object.getJSONObject("result");
		JSONArray array = res.getJSONArray("items");
		List<String> itemIds = new ArrayList<String>();
		for (Object arr : array) {
			JSONObject item = (JSONObject) arr;
			String itemId = item.getString("itemid");
			itemIds.add(itemId);
		}
		return itemIds;
	}

	/**
	 * 获取单个商品
	 * 
	 * @param appInfoId
	 * @param itemId
	 */
	public ProductInfoVo getItem(Long appInfoId, String itemId) {
		JSONObject params = new JSONObject();
		params.put("itemid", itemId);
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("param", params.toJSONString()));
		pairs.add(new BasicNameValuePair("public", getPublicParam(appInfoId, "vdian.item.get")));
		String response = post(appInfoId, pairs);
		JSONObject result = JSONObject.parseObject(response);
		String info = result.getString("result");
		ProductInfoVo productInfo = JSONObject.parseObject(info, ProductInfoVo.class);
		return productInfo;
	}

	/**
	 * 同步商品到数据库
	 * 
	 * @param appInfoId
	 */
	public void syncItemToDatabase(Long appInfoId) {
		try {
			List<String> itemIds = getAllItemIds(appInfoId);
			for (String itemId : itemIds) {
				try {
					ProductInfoVo vo = getItem(appInfoId, itemId);
					ProductInfo info = productInfoDao.findByItemId(itemId);
					if (info == null) {
						info = new ProductInfo();
						info.setCreateTime(new Date());
					}
					info.setCates(JSONObject.toJSONString(vo.getCates()));
					info.setFreeDelivery(vo.getFree_delivery());
					info.setFxFeeRate(vo.getFx_fee_rate());
					info.setImgs(JSONObject.toJSONString(vo.getImgs()));
					info.setIstop(vo.getIstop());
					info.setItemName(vo.getItem_name());
					info.setItemDesc(vo.getItem_desc());
					info.setItemId(vo.getItemid());
					info.setMerchantCode(vo.getMerchant_code());
					info.setPrice(vo.getPrice());
					info.setRemoteFreeDelivery(vo.getRemote_free_delivery());
					info.setSellerId(vo.getSeller_id());
					info.setSkus(JSONObject.toJSONString(vo.getSkus()));
					info.setSold(vo.getSold());
					info.setStatus(vo.getStatus());
					info.setStock(vo.getStock());
					info.setThumbImgs(JSONObject.toJSONString(vo.getThumb_imgs()));
					info.setTitles(JSON.toJSONString(vo.getTitles()));
					info.setAppInfoId(appInfoId);
					productInfoDao.save(info);
				} catch (Exception e) {
					logger.error("获取商品失败", e);
				}
			}
		} catch (Exception e) {
			logger.error("获取商品列表失败", e);
		}
	}

	/**
	 * 上传图片
	 * 
	 * @param appInfoId
	 * @param copyedInfo
	 */
	public void uploadFile(Long appInfoId, ProductInfo copyedInfo) {
		List<String> srcImgs = JSONObject.parseArray(copyedInfo.getImgs(), String.class);
		for (String srcImgUrl : srcImgs) {
			/**
			 * 图片是否能在已上传的纪录中找到，找到则返回
			 */
			ProductImages image = productImagesDao.findByAppInfoIdAndSourceFileName(appInfoId, srcImgUrl);
			if (image != null) {
				continue;
			}
			String localImageName = srcImgUrl.substring(srcImgUrl.indexOf("-") + 1, srcImgUrl.indexOf("?"));
			File file = new File(this.getClass().getResource("/").getFile() + "wdImages/" + localImageName);
			/**
			 * 源图片文件不在本地目录中，下载
			 */
			if (!file.exists()) {
				byte[] imges = HttpClientHelper.getAndGetByte(srcImgUrl, new ArrayList<NameValuePair>());
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(file);
					fos.write(imges);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						fos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			pairs.add(new BasicNameValuePair("access_token", getAccessToken(appInfoId)));
			String response = HttpClientHelper.uploadWdFile("http://api.vdian.com/media/upload", pairs, file);
			JSONObject imgUrl = JSONObject.parseObject(response);
			String wdUrl = imgUrl.getString("result");
			image = new ProductImages();
			image.setAppInfoId(appInfoId);
			image.setSrcProductInfoId(copyedInfo.getId());
			image.setWdUrl(wdUrl);
			image.setSourceFileName(srcImgUrl);
			productImagesDao.save(image);
		}
	}

	public StatusResult addOrUpdateItem(Long appInfoId, ProductInfo copyedInfo, Integer type) {
		JSONObject params = new JSONObject();
		params.put("itemName", copyedInfo.getItemName());
		params.put("stock", copyedInfo.getStock());
		String skus = copyedInfo.getSkus();
		List<ProductSkuVo> vos = JSONObject.parseArray(skus, ProductSkuVo.class);
		String price = null;
		for (ProductSkuVo productSkuVo : vos) {
			productSkuVo.setId(null);
			productSkuVo.setSku_merchant_code(null);
			price = productSkuVo.getPrice();
		}
		try {
			Double.parseDouble(copyedInfo.getPrice());
			params.put("price", copyedInfo.getPrice());
		} catch (Exception e) {
			params.put("price", price);
		}
		params.put("sku", vos);
		uploadFile(appInfoId, copyedInfo);
		List<String> images = JSONObject.parseArray(copyedInfo.getImgs(), String.class);
		List<String> imgUrls = new ArrayList<String>();
		for (String srcImgUrl : images) {
			ProductImages image = productImagesDao.findByAppInfoIdAndSourceFileName(appInfoId, srcImgUrl);
			imgUrls.add(image.getWdUrl());
		}
		params.put("bigImgs", imgUrls);
		List<String> titles = JSONObject.parseArray(copyedInfo.getTitles(), String.class);
		params.put("titles", titles);
		List<ProductCateVo> cates = JSONObject.parseArray(copyedInfo.getCates(), ProductCateVo.class);
		StringBuffer buffer = new StringBuffer();
		for (ProductCateVo productCate : cates) {
			ProductCate cate = productCateDao.findByAppInfoIdAndCateNameAndIsRemove(appInfoId,
					productCate.getCate_name(), false);
			buffer.append(cate.getCateId() + ",");
		}
		if (buffer.length() > 0) {
			params.put("cate_id", buffer.substring(0, buffer.length() - 1));
		}
		params.put("free_delivery", copyedInfo.getFreeDelivery().toString());
		params.put("remote_free_delivery", copyedInfo.getRemoteFreeDelivery().toString());
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		if (type == ADD) {
			pairs.add(new BasicNameValuePair("param", params.toJSONString()));
			pairs.add(new BasicNameValuePair("public", getPublicParam(appInfoId, "vdian.item.add", "1.1")));
		}else if (type == UPDATE) {	
			params.put("itemID", copyedInfo.getItemId());
			pairs.add(new BasicNameValuePair("param", params.toJSONString()));
			pairs.add(new BasicNameValuePair("public", getPublicParam(appInfoId, "vdian.item.update", "1.1")));
		}
		String response = post(appInfoId, pairs);
		StatusResult statusResult = JSONObject.parseObject(response, StatusResult.class);
		JSONObject obj = JSONObject.parseObject(statusResult.getResult());
		ProductInfo info = new ProductInfo();
		info.setItemId(obj.getString("item_id"));
		info.setAppInfoId(appInfoId);
		info.setSrcProductInfoId(info.getId());
		productInfoDao.save(info);
		return statusResult;
	}

	/**
	 * 从一个网店的商品同步到另外一个网店
	 * 
	 * @param fromAppId
	 * @param toAppId
	 */
	public void syncItemToAnother(Long fromAppId, Long toAppId) {
		List<ProductInfo> iterator = productInfoDao.findByAppInfoId(fromAppId);
		for (ProductInfo productInfo : iterator) {
			Long productId = productInfo.getId();
			ProductInfo oldProduct = productInfoDao.findByAppInfoIdAndSrcProductInfoId(toAppId, productId);
			if (oldProduct != null) {
				boolean isSame = true;
				String cates = productInfo.getCates();
				List<ProductCateVo> vos = JSONObject.parseArray(cates, ProductCateVo.class);
				String oldCates = productInfo.getCates();
				List<ProductCateVo> oldVos = JSONObject.parseArray(oldCates, ProductCateVo.class);
				boolean isCateSame = false;
				for (ProductCateVo productCateVo : vos) {
					isCateSame = false;
					for (ProductCateVo oldVo : oldVos) {
						if (oldVo.getCate_name().equals(productCateVo.getCate_name())) {
							isCateSame = true;
							break;
						}
					}
					if (!isCateSame) {
						break;
					}
				}
				if (!isCateSame) {
					isSame = false;
					List<ProductCateVo> newVos = new ArrayList<ProductCateVo>();
					for (ProductCateVo productCateVo : vos) {
						String cateName = productCateVo.getCate_name();
						ProductCate cate = productCateDao.findByAppInfoIdAndCateNameAndIsRemove(toAppId, cateName,
								false);
						ProductCateVo newVo = new ProductCateVo();
						newVo.setCate_id(cate.getCateId());
						newVo.setCate_name(cate.getCateName());
						newVo.setSort_num(cate.getSortNum());
						newVos.add(newVo);
					}
					oldProduct.setCates(JSONObject.toJSONString(newVos));
				}
				String imgs = productInfo.getImgs();
				List<String> imgList = JSONObject.parseArray(imgs, String.class);
				String oldImgs = oldProduct.getImgs();
				List<String> oldImgList = JSONObject.parseArray(oldImgs, String.class);
				boolean isImgSame = false;
				for (String img : imgList) {
					isImgSame = false;
					for (String oldImg : oldImgList) {
						ProductImages productImages = productImagesDao.findByAppInfoIdAndSourceFileName(toAppId, img);
						if (productImages.getWdUrl().equals(oldImg)) {
							isCateSame = true;
							break;
						}
					}
					if (!isImgSame) {
						break;
					}
				}
				if (!isImgSame) {
					isSame = false;
					uploadFile(toAppId, productInfo);
				}
				List<String> newImgs = new ArrayList<String>();
				for (String img : imgList) {
					ProductImages images = productImagesDao.findByAppInfoIdAndSourceFileName(toAppId, img);
					newImgs.add(images.getWdUrl());
				}
				oldProduct.setImgs(JSONObject.toJSONString(newImgs));
				String skus = productInfo.getSkus();
				List<ProductSkuVo> skuList = JSONObject.parseArray(skus, ProductSkuVo.class);
				String oldSkus = oldProduct.getSkus();
				List<ProductSkuVo> oldSkuList = JSONObject.parseArray(oldSkus, ProductSkuVo.class);
				boolean isSkuSame = false;
				for (ProductSkuVo sku : skuList) {
					isSkuSame = false;
					String str = sku.getTitle() + sku.getPrice();
					for (ProductSkuVo oldSku : oldSkuList) {
						String oldStr = oldSku.getTitle() + oldSku.getPrice();
						if (str.equals(oldStr)) {
							isSkuSame = true;
							break;
						}
					}
					if (!isSkuSame) {
						break;
					}
				}
				if (!isSkuSame) {
					isSame = false;
					for (ProductSkuVo skuVo : skuList) {
						skuVo.setId(null);
						skuVo.setSku_merchant_code(null);
					}
					oldProduct.setCates(JSONObject.toJSONString(skuList));
				}
				if (!oldProduct.getFreeDelivery().equals(productInfo.getFreeDelivery())) {
					isSame = false;
					oldProduct.setFreeDelivery(productInfo.getFreeDelivery());
				}
				if (!oldProduct.getIstop().equals(productInfo.getIstop())) {
					isSame = false;
					oldProduct.setIstop(productInfo.getIstop());
				}
				if (!oldProduct.getItemDesc().equals(productInfo.getItemDesc())) {
					isSame = false;
					oldProduct.setItemDesc(productInfo.getItemDesc());
				}

				if (!oldProduct.getItemName().equals(productInfo.getItemName())) {
					isSame = false;
					oldProduct.setItemName(productInfo.getItemName());
				}
				if (!oldProduct.getPrice().equals(productInfo.getPrice())) {
					isSame = false;
					oldProduct.setPrice(productInfo.getPrice());
				}
				if (!oldProduct.getRemoteFreeDelivery().equals(productInfo.getRemoteFreeDelivery())) {
					isSame = false;
					oldProduct.setRemoteFreeDelivery(productInfo.getRemoteFreeDelivery());
				}
				if (!oldProduct.getStatus().equals(productInfo.getStatus())) {
					isSame = false;
					oldProduct.setStatus(productInfo.getStatus());
				}
				if (!oldProduct.getTitles().equals(productInfo.getTitles())) {
					isSame = false;
					oldProduct.setTitles(productInfo.getTitles());
				}
				/**
				 * 不检查FxFeeRate,MerchantCode字段
				 */
				// oldProduct.getFxFeeRate();
				// oldProduct.getMerchantCode();

				if (isSame) {
					continue;
				}
				addOrUpdateItem(toAppId, oldProduct,UPDATE);
			} else {
				addOrUpdateItem(toAppId, productInfo,ADD);
			}
		}
		syncItemToDatabase(toAppId);
	}

	public void sync(Long appInfoId) {

		syncItemToDatabase(appInfoId);
		List<WdAppInfo> infos = wdAppInfoDao.getBySrcWdAppInfoId(appInfoId);
		for (WdAppInfo wdAppInfo : infos) {
			syncItemToAnother(appInfoId, wdAppInfo.getId());
		}
	}
}
