package org.whh.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whh.dao.CustomerDao;
import org.whh.dao.QQGroupInfoDao;
import org.whh.entity.Customer;
import org.whh.entity.QQGroupInfo;

@Component
public class DataMigrate
{
	@Autowired
	QQGroupInfoDao qqGroupInfoDao;
	@Autowired
	CustomerDao customerDao;
	public void migrate()
	{
		Iterable<QQGroupInfo> infos = qqGroupInfoDao.findAll();
		int i = 0;
		int j = 1; 
		List<Customer> customers = new ArrayList<Customer>();
		Iterator<QQGroupInfo> iterator = infos.iterator();
		while (iterator.hasNext())
		{
			i++;
			QQGroupInfo info = iterator.next();
			Customer customer = new Customer();
			customer.setCity(info.getCity());
			customer.setProvince(info.getProvince());
			customer.setQq(info.getGroupOwnerNumber()+"");
			customer.setIsRecommend(false);
			customer.setConsumeLevel(info.getLevel());
			customer.setQqGroupCode(info.getGroupCode());
			customer.setTag(info.getGroupName());
			customer.setIsGive(false);
			customers.add(customer);
			if (i % 100 == 0)
			{
				System.out.println("保存"+ j++ * 100 + "条数据");
				customerDao.save(customers);
				customers = new ArrayList<Customer>();
			}
		}
		if (customers.size() > 0 )
		{
			customerDao.save(customers);
		}
	}
}