package org.whh.util;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLHelper
{
	public static Element getRootElement(String filePath)
	{
		try
		{
			SAXReader reader = new SAXReader();
			File file = new File(filePath);
			System.out.println(file.getAbsolutePath());
			Document document;
			document = reader.read(file);
			Element root = document.getRootElement();
			return root;
			
		} catch (DocumentException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
