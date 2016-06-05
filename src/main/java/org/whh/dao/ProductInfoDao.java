package org.whh.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.whh.entity.ProductInfo;

public interface ProductInfoDao
		extends PagingAndSortingRepository<ProductInfo, Long>, JpaSpecificationExecutor<ProductInfo> {
	ProductInfo findByItemId(String itemId);
	
	List<ProductInfo> findByAppInfoId(Long appInfoId);
	
	ProductInfo findByAppInfoIdAndSrcProductInfoId(Long appInfoId,Long srcProductInfoId);
}