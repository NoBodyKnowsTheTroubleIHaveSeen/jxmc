package org.whh.base;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.whh.dao.CodeDao;

public class ControllerBase extends ObjectBase
{
	@Autowired
	public CodeDao codeDao;
	@InitBinder
	public void dataBinding(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	public CodeDao getCodeDao()
	{
		return codeDao;
	}
	public void setCodeDao(CodeDao codeDao)
	{
		this.codeDao = codeDao;
	}
	
}
