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
import org.whh.constant.Compare;
import org.whh.dao.PartnerDao;
import org.whh.entity.Partner;

@Service
public class PartnerService extends ServiceBase
{
	@Autowired
	private PartnerDao partnerDao;

	public Page<Partner> findAllByPage(Partner partner, Pageable pageable,
			Integer deductPercentageCompare)
	{
		Page<Partner> result = partnerDao.findAll(new Specification<Partner>()
		{
			public Predicate toPredicate(Root<Partner> root,
					CriteriaQuery<?> query, CriteriaBuilder cb)
			{
				List<Predicate> list = new ArrayList<Predicate>();

				if (!isNull(partner.getPartnerLevel()))
				{
					list.add(cb.equal(root.get("partnerLevel")
							.as(Integer.class), partner.getPartnerLevel()));
				}
				if (!isNull(partner.getTag()))
				{
					list.add(cb.like(root.get("tag").as(String.class), "%"
							+ partner.getTag() + "%"));
				}
				if (!isNull(partner.getProvince()))
				{
					list.add(cb.like(root.get("province").as(String.class), "%"
							+ partner.getProvince() + "%"));
				}
				if (!isNull(partner.getCity()))
				{
					list.add(cb.like(root.get("city").as(String.class), "%"
							+ partner.getCity() + "%"));
				}
				if (!isNull(partner.getQq()))
				{
					list.add(cb.like(root.get("qq").as(String.class), "%"
							+ partner.getQq() + "%"));
				}
				if (!isNull(partner.getWeiXin()))
				{
					list.add(cb.like(root.get("weiXin").as(String.class), "%"
							+ partner.getWeiXin() + "%"));
				}
				if (!isNull(partner.getPhone()))
				{
					list.add(cb.like(root.get("phone").as(String.class), "%"
							+ partner.getPhone() + "%"));
				}
				if (!isNull(partner.getSex()))
				{
					list.add(cb.like(root.get("sex").as(String.class), "%"
							+ partner.getSex() + "%"));
				}
				if (!isNull(partner.getName()))
				{
					list.add(cb.like(root.get("name").as(String.class), "%"
							+ partner.getName() + "%"));
				}

				if (!isNull(deductPercentageCompare)&&!isNull(partner.getDeductPercentage()))
				{
					switch (deductPercentageCompare)
					{
					case Compare.EQ:
					case Compare.LIKE:
						list.add(cb.equal(
								root.get("deductPercentage").as(Double.class),
								partner.getDeductPercentage()));
						break;
					case Compare.GT:
						list.add(cb.gt(
								root.get("deductPercentage").as(Double.class),
								partner.getDeductPercentage()));
						break;
					case Compare.LT:
						list.add(cb.lt(
								root.get("deductPercentage").as(Double.class),
								partner.getDeductPercentage()));
						break;
					}
				}
				Predicate[] p = new Predicate[list.size()];
				return cb.and(list.toArray(p));
			}
		}, pageable);
		return result;
	}
}
