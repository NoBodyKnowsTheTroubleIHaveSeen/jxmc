package org.whh.service;

import java.util.ArrayList;
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
import org.whh.dao.OrdersDao;
import org.whh.entity.Orders;

@Service
public class OrdersService extends ServiceBase {
	@Autowired
	private OrdersDao ordersDao;

	public Page<Orders> findAllByPage(String nameOrPhone, String status, String startTime, String endTime,
			Pageable pageable) {
		Page<Orders> result = ordersDao.findAll(new Specification<Orders>() {
			public Predicate toPredicate(Root<Orders> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (!isNull(nameOrPhone)) {
					Predicate[] predicates = new Predicate[2];
					predicates[0] = cb.like(root.get("sellerPhone").as(String.class), "%" + nameOrPhone + "%");
					predicates[1] = cb.like(root.get("sellerName").as(String.class), "%" + nameOrPhone + "%");
					list.add(cb.or(predicates));
				}
				if (!isNull(status)) {
					list.add(cb.equal(root.get("status").as(String.class), status));
				}
				if (!isNull(startTime)) {
					list.add(cb.greaterThan(root.get("addTime").as(String.class), startTime));
				}
				if (!isNull(endTime)) {
					list.add(cb.lessThan(root.get("addTime").as(String.class), endTime));
				}
				Predicate[] p = new Predicate[list.size()];
				return cb.and(list.toArray(p));
			}
		}, pageable);
		return result;
	}
}
