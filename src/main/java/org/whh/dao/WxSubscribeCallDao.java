package org.whh.dao;

import org.springframework.data.repository.CrudRepository;
import org.whh.entity.WxSubscriberCall;

public interface WxSubscribeCallDao extends CrudRepository<WxSubscriberCall, Long> {
	WxSubscriberCall findByOpenId(String openId);
}
