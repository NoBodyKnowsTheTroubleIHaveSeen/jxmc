package org.whh.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

public class ConfigPropertyUtil {

	private static HashMap<String, HashMap<String, String>> propertyCache = new HashMap<String, HashMap<String, String>>();
	/**
	 * 默认配置文件名
	 */
	private static final String DEFAULT_CONFIG_FILE_NAME = "sys.properties";

	/**
	 * 读取指定文件目录下的指定文件的配置文件
	 * 
	 * @param filePath
	 * @param fileName
	 * @param property
	 * @return
	 */
	public static String getProperty(String filePath, String fileName, String property) {
		if (NullUtil.isNull(filePath)) {
			filePath = ConfigPropertyUtil.class.getResource("/").getFile() + "config/";
		} else if (!filePath.endsWith("/")) {
			filePath = filePath + "/";
		}
		if (NullUtil.isNull(fileName)) {
			fileName = DEFAULT_CONFIG_FILE_NAME;
		}
		String fullFilePath = filePath + fileName;
		HashMap<String, String> propertys = propertyCache.get(fullFilePath);
		if (!NullUtil.isNull(propertys)) {
			return propertys.get(property);
		}
		propertys = new HashMap<String, String>();
		propertyCache.put(fullFilePath, propertys);
		Properties p = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filePath + fileName));
			p.load(in);
			Iterator<String> it = p.stringPropertyNames().iterator();
			while (it.hasNext()) {
				String key = it.next();
				propertys.put(key, p.getProperty(key));
			}
			in.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return propertys.get(property);
	}

	/**
	 * 读取默认配置文件路径下的指定文件中配置的属性
	 * 
	 * @param fileName
	 * @param property
	 * @return
	 */
	public static String getProperty(String fileName, String property) {
		return getProperty(null, fileName, property);
	}

	/**
	 * 读取默认配置文件路径下默认配置文件中配置的属性
	 * 
	 * @param property
	 * @return
	 */
	public static String getProperty(String property) {
		return getProperty(null, property);
	}

}
