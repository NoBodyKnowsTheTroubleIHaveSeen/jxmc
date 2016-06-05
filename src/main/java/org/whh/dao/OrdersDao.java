package org.whh.dao;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.whh.entity.Orders;

public interface OrdersDao extends PagingAndSortingRepository<Orders, Long>,JpaSpecificationExecutor<Orders> {
	public Orders getByOrderId(String orderId);
	
}