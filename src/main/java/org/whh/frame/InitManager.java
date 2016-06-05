package org.whh.frame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitManager
{
	@Autowired
	CodeUtil codeUtil;
	@Autowired
	SystemUtil systemUtil;
	//系统初始化
	public void init()
	{
		systemUtil.init();
		codeUtil.init();
	}
}
