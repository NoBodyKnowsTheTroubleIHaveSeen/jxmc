package org.whh.frame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whh.dao.SystemPropertyDao;
import org.whh.entity.SystemProperty;

@Component
public class SystemUtil implements Init
{
	private Logger logger = LoggerFactory.getLogger(CodeUtil.class);
	@Autowired
	SystemPropertyDao systemPropertyDao;
	@Override
	public void init()
	{
		logger.info("初始化系统参数...");
		SystemProperty property = systemPropertyDao.findOne(1L);
		if (property == null)
		{
			property = new SystemProperty();
			property.setId(1L);
			property.setCodeVersion(0L);
			property.setCodeDeleteVersion(0L);
		}
		systemPropertyDao.save(property);
		logger.info("初始化系统参数完成.");
	}
}
