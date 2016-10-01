package org.whh.util;

import java.io.File;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLHelper {
	public static Document parse(String filePath) {
		try {
			SAXReader reader = new SAXReader();
			File file = new File(filePath);
			System.out.println(file.getAbsolutePath());
			Document document = reader.read(file);
			return document;

		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Element getRootElementByFilePath(Document document) {
		Element root = document.getRootElement();
		return root;
	}

	public static Document parse(InputStream stream) {
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(stream);
			return document;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
