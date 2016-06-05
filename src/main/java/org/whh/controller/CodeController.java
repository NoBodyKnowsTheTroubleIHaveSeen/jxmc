package org.whh.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.whh.base.ControllerBase;
import org.whh.dao.CodeDao;

@Controller
public class CodeController extends ControllerBase
{
	Logger logger = LoggerFactory.getLogger(CodeController.class);
	
	@Autowired
	CodeDao codeDao;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getCode")
	@ResponseBody
	public List getCode(String codeGroup)
	{
		return codeDao.findByCodeGroup(codeGroup);
	}
}
