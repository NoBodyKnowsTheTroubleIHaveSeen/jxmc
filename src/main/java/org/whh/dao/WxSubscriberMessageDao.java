package org.whh.dao;

import org.springframework.data.repository.CrudRepository;
import org.whh.entity.WxSubscriberMessage;

public interface WxSubscriberMessageDao extends CrudRepository<WxSubscriberMessage, Long> {

}
