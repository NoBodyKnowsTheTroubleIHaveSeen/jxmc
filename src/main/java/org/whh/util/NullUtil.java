package org.whh.util;

public class NullUtil {
	public static boolean isNull(String str)
	{
		if (str == null || str.equals("")) {
			return true;
		}
		return false;
	}
	public static boolean isNull(Object object)
	{
		return object == null;
	}
	
}
