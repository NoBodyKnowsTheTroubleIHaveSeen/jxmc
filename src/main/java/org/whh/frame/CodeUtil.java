package org.whh.frame;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whh.dao.CodeDao;
import org.whh.dao.SystemPropertyDao;
import org.whh.entity.Code;
import org.whh.entity.SystemProperty;
import org.whh.util.XMLHelper;


@Component
public class CodeUtil implements Init
{
	private Logger logger = LoggerFactory.getLogger(CodeUtil.class);
	@Autowired
	SystemPropertyDao systemPropertyDao;
	
	@Autowired
	CodeDao codeDao;
	/**
	 * 初始化code
	 */
	@SuppressWarnings("unchecked")
	public void init()
	{
		logger.info("初始化code...");
		Document document = XMLHelper.parse(this.getClass().getResource("/").getFile() +"config/code.xml");
		Element root = document.getRootElement();
		Element versionElement = root.element("version");
		Element deleteVersionElement = root.element("deleteVersion");
		Long version = Long.parseLong(versionElement.getText());
		Long deleteVersion = Long.parseLong(deleteVersionElement.getText());
		SystemProperty property = systemPropertyDao.findOne(1L);
		Long oldVersion = property.getCodeVersion();
		Long oldDeleteVersion = property.getCodeDeleteVersion();
		if (version.equals(oldVersion))
		{
			logger.info("code版本未更新，不进行操作，code初始化完成.");
			return;
		}
		if (!deleteVersion.equals(oldDeleteVersion))
		{
			logger.info("code删除版已更新，删除旧code.");
			codeDao.deleteAll();
		}
		List<Element> codeGroups = root.elements("codeGroup");
		for (Element codeGroup : codeGroups)
		{
			List<Element> codes = codeGroup.elements("code");
			for (Element code : codes)
			{
				Code c = new Code();
				c.setCodeGroup(codeGroup.attributeValue("name"));
				c.setCodeKey(code.attributeValue("key"));
				c.setCodeValue(code.attributeValue("value"));
				c.setId(Long.parseLong(code.attributeValue("id")));
				codeDao.save(c);
			}
		}
		property.setCodeDeleteVersion(deleteVersion);
		property.setCodeVersion(version);
		systemPropertyDao.save(property);
		logger.info("初始化code完成.");
	}
}
