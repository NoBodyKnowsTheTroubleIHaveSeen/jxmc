package org.whh.dao;


import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.whh.entity.Material;

public interface MaterialDao extends PagingAndSortingRepository<Material, Long> {
	Material findByUrl(String url);
	
	Material findByMediaId(String mediaId);
	
	@Query("select max(offset) from Material")
	int getMaxOffset();
	
	List<Material> findByTypeAndIsUsed(Integer type,Boolean isUsed);
	
	List<Material> findByMaterialStatus(Integer materialStatus);
	
	@Query("from Material m where m.isUsed = 1 and type != 4 and type != 5")
	List<Material> findAllUsed();
	
	
	@Query("from Material m where m.materialStatus = 2")
	Material findCurrentContent();
	
	Material findByInputCode(String inputCode);
	
}