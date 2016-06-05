package org.whh.test;

import java.io.IOException;
import java.net.Socket;

public class T
{
	public static void main(String[] args)
	{

		Socket socket;
		try
		{
			socket = new Socket("192.168.1.125",52231);
			socket.getOutputStream().write("cao dan".getBytes());
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
