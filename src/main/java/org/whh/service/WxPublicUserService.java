package org.whh.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.whh.base.ServiceBase;
import org.whh.dao.WxPublicUserDao;
import org.whh.entity.WxPublicUser;

@Service
public class WxPublicUserService extends ServiceBase {

	@Autowired
	private WxPublicUserDao wxPublicUserDao;

	public Page<WxPublicUser> findAllByPage(WxPublicUser user, Date startTime, Date endTime, Pageable pageable) {
		Page<WxPublicUser> result = wxPublicUserDao.findAll(new Specification<WxPublicUser>() {
			public Predicate toPredicate(Root<WxPublicUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (!isNull(user.getOpenId())) {
					list.add(cb.like(root.get("openId").as(String.class), user.getOpenId()));
				}
				if (!isNull(user.getRecommendOpenId())) {
					list.add(cb.like(root.get("recommendOpenId").as(String.class), user.getRecommendOpenId()));
				}
				if (!isNull(user.getNickname())) {
					list.add(cb.like(root.get("nickname").as(String.class), user.getNickname()));
				}
				if (!isNull(user.getSubscribe())) {
					list.add(cb.equal(root.get("subscribe").as(Boolean.class), user.getSubscribe()));
				}
				if (!isNull(user.getRecommendCount())) {
					list.add(cb.ge(root.get("recommendCount").as(Integer.class), user.getRecommendCount()));
				}
				if (!isNull(startTime)) {
					list.add(cb.greaterThan(root.get("createTime").as(Date.class), startTime));
				}
				if (!isNull(endTime)) {
					list.add(cb.lessThan(root.get("createTime").as(Date.class), endTime));
				}
				Predicate[] p = new Predicate[list.size()];
				return cb.and(list.toArray(p));
			}
		}, pageable);
		return result;
	}
}
