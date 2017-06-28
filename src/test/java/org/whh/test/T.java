package org.whh.test;


import org.whh.util.HttpClientHelper;

public class T
{
	public static void main(String[] args)
	{
		String response = HttpClientHelper.get("http://www.jianshu.com/p/974727a1f47b", null);
		System.out.println(response);

//		Socket socket;
//		try
//		{
//			socket = new Socket("192.168.1.125",52231);
//			socket.getOutputStream().write("cao dan".getBytes());
//		} catch (IOException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
