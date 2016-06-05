package org.whh.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.whh.entity.ProductCate;

public interface ProductCateDao
		extends PagingAndSortingRepository<ProductCate, Long>, JpaSpecificationExecutor<ProductCate> {
	ProductCate findByAppInfoIdAndCateId(Long appInfoId, String cateId);
	
	ProductCate findByAppInfoIdAndCateNameAndIsRemove(Long appInfoId,String cateName,boolean isRemove);

	List<ProductCate> findByAppInfoId(Long appInfoId);
	
	List<ProductCate> findBySrcProductCateIdAndCateNameAndIsRemove(Long srcProductCateId,String cateName,boolean isRemove);
}