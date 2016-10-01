package org.whh.dao;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.whh.entity.WxPublicUser;


public interface WxPublicUserDao extends PagingAndSortingRepository<WxPublicUser, Long>, JpaSpecificationExecutor<WxPublicUser> {

	public WxPublicUser getByOpenId(String openId);

}