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
import org.whh.dao.CustomerDao;
import org.whh.entity.Customer;

@Service
public class CustomerService extends ServiceBase
{
	@Autowired
	private CustomerDao customerDao;
	public Page<Customer> findAllByPage(Customer customer,Date startTime,Date endTime,Pageable pageable)
	{
		Page<Customer> result= customerDao.findAll(new Specification<Customer>()
				{
					public Predicate toPredicate(Root<Customer> root,
							CriteriaQuery<?> query, CriteriaBuilder cb)
					{
						List<Predicate> list = new ArrayList<Predicate>();  
				          
					    if(!isNull(customer.getConsumeLevel())){  
					        list.add(cb.equal(root.get("consumeLevel").as(Integer.class), customer.getConsumeLevel()));  
					    }
					    if (!isNull(customer.getIsRecommend()))
						{
							list.add(cb.equal(root.get("isRecommend").as(Boolean.class), customer.getIsRecommend()));
						}
					    if (!isNull(customer.getTag()))
						{
					    	list.add(cb.like(root.get("tag").as(String.class), "%"+customer.getTag()+"%"));
						}
					    if (!isNull(startTime))
						{
					    	list.add(cb.greaterThan(root.get("createTime").as(Date.class), startTime));
						}
					    if (!isNull(endTime))
						{
					    	list.add(cb.lessThan(root.get("createTime").as(Date.class), endTime));
						}
					    if (!isNull(customer.getProvince()))
						{
					    	list.add(cb.like(root.get("province").as(String.class), "%"+customer.getProvince()+"%"));
						}
					    if (!isNull(customer.getCity()))
					    {
					    	list.add(cb.like(root.get("city").as(String.class), "%"+customer.getCity()+"%"));
					    }
					    if (!isNull(customer.getQq()))
					    {
					    	list.add(cb.like(root.get("qq").as(String.class), "%"+customer.getQq()+"%"));
					    }
					    if (!isNull(customer.getWeiXin()))
					    {
					    	list.add(cb.like(root.get("weiXin").as(String.class), "%"+customer.getWeiXin()+"%"));
					    }
					    if (!isNull(customer.getPhone()))
					    {
					    	list.add(cb.like(root.get("phone").as(String.class), "%"+customer.getPhone()+"%"));
					    }
					    if (!isNull(customer.getIsAdd()))
					    {
					    	list.add(cb.equal(root.get("isAdd").as(Boolean.class),customer.getIsAdd()));
					    }
					    if (!isNull(customer.getQqIsWeiXin()))
					    {
					    	list.add(cb.equal(root.get("qqIsWeiXin").as(Boolean.class),customer.getQqIsWeiXin()));
					    }
					    if (!isNull(customer.getName()))
					    {
					    	list.add(cb.like(root.get("name").as(String.class),"%"+customer.getName()+"%"));
					    }
					    Predicate[] p = new Predicate[list.size()];  
					    return cb.and(list.toArray(p)); 
					}
				}, pageable);
		return result;
	}
}
