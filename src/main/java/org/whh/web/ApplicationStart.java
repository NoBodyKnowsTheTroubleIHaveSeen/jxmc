package org.whh.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.whh.frame.InitManager;
@Component
public class ApplicationStart implements
		ApplicationListener<ContextRefreshedEvent>
{
	@Autowired
	InitManager initManager;
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event)
	{
		initManager.init();
		return ;
	}
}
