package org.whh.browse;

import java.util.Random;


public class WebdriverTest
{
	public static void main(String[] args)
	{
		for (int i = 0; i < 100; i++)
		{
//			RouterManger.reConnect();
			WDBrowser browser = new WDBrowser();
			browser.browse();
			try
			{
				Thread.sleep(new Random().nextInt(1000*60*new Random().nextInt(9)));
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
