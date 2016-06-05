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
import org.whh.dao.EmailDao;
import org.whh.entity.Email;

@Service
public class EmailService extends ServiceBase
{
	@Autowired
	private EmailDao emailDao;
	public Page<Email> findAllByPage(Email email,Date startTime,Date endTime,Pageable pageable)
	{
		Page<Email> result= emailDao.findAll(new Specification<Email>()
				{
					public Predicate toPredicate(Root<Email> root,
							CriteriaQuery<?> query, CriteriaBuilder cb)
					{
						List<Predicate> list = new ArrayList<Predicate>();  
				          
					    if(!isNull(email.getSender())){  
					        list.add(cb.equal(root.get("sender").as(Integer.class), email.getSender()));  
					    }
					    if (!isNull(startTime))
						{
					    	list.add(cb.greaterThan(root.get("createTime").as(Date.class), startTime));
						}
					    if (!isNull(endTime))
						{
					    	list.add(cb.lessThan(root.get("createTime").as(Date.class), endTime));
						}
					    Predicate[] p = new Predicate[list.size()];  
					    return cb.and(list.toArray(p)); 
					}
				}, pageable);
		return result;
	}
}
