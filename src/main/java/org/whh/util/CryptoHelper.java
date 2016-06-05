package org.whh.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptoHelper
{
	public static String getMd5(String str)
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());

			byte byteData[] = md.digest();

			// convert the byte to hex format method
			// 将byte转化成16进制显示（方式一）
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++)
			{
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			return sb.toString();
			// （方式二）
			// StringBuffer hexString = new StringBuffer();
			// for (int i = 0; i < byteData.length; i++)
			// {
			// String hex = Integer.toHexString(0xff & byteData[i]);
			// if (hex.length() == 1)
			// hexString.append('0');
			// hexString.append(hex);
			// }

		} catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
