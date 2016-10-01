package org.whh.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtil {
	public void read()
	{
		try {
			FileInputStream	fileInputStream = new FileInputStream("e://test.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream, "utf-8"));
			String tmp = null;
			StringBuffer buffer = new StringBuffer();
			while((tmp = reader.readLine()) != null)
			{
				buffer.append(tmp);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
