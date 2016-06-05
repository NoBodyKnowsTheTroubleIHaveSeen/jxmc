package org.whh.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.whh.entity.ProductImages;

public interface ProductImagesDao
		extends PagingAndSortingRepository<ProductImages, Long>, JpaSpecificationExecutor<ProductImages> {
	ProductImages findByAppInfoIdAndSourceFileName(Long appInfoId, String sourceFileName);
}