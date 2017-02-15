package org.whh.dao;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.whh.entity.WxPublicUser;


public interface WxPublicUserDao extends PagingAndSortingRepository<WxPublicUser, Long>, JpaSpecificationExecutor<WxPublicUser> {

	public WxPublicUser getByOpenId(String openId);
	
	@Transactional
    @Modifying
    @Query("update WxPublicUser a set a.subscribe = 0")
	public  int setWxPublicUserUnsubscribe();

}