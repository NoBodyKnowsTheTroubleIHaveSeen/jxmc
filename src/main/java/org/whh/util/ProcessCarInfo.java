package org.whh.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whh.dao.CarInfoDao;
import org.whh.entity.CarInfo;

@Component
public class ProcessCarInfo
{
	@Autowired
	CarInfoDao carInfoDao;

	public void getCarInfo(String filePath)
	{
		long starTime = System.currentTimeMillis();
		createTmpFile(filePath);
		processTempFile(filePath);
		processResultFile(filePath);
		long endTime = System.currentTimeMillis();
		System.out.println(endTime - starTime);
	}

	private void createTmpFile(String filePath)
	{
		File f = new File(filePath);
		File[] files = f.listFiles();
		BufferedReader reader = null;
		for (int i = 0; i < files.length; i++)
		{
			if (!files[i].getName().contains("txt"))
			{
				continue;
			}
			try
			{
				reader = new BufferedReader(new InputStreamReader(
						new FileInputStream(files[i]), "GBK"));
				StringBuffer buffer = new StringBuffer();
				int flag = 0;
				String temp = "";
				while ((temp = reader.readLine()) != null)
				{
					// 左侧开始栏
					if (flag == 1)
					{
						flag++;
						continue;
					}
					if (temp.equals(""))
					{
						flag = 0;
					} else if (temp.length() == 1 && temp.matches("[a-zA-Z]"))
					{
						continue;
					} else if (temp.contains("报价"))
					{
						continue;
					} else
					{
						buffer.append(temp + "\r\n");
					}
					flag++;
				}
				FileOutputStream fos = new FileOutputStream(
						files[i].getAbsolutePath() + ".tmp");
				fos.write(buffer.toString().getBytes("GBK"));
				fos.close();
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			} finally
			{
				try
				{
					if (null != reader)
					{
						reader.close();
					}
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	private void processTempFile(String filePath)
	{
		File f = new File(filePath);
		File[] files = f.listFiles();
		BufferedReader reader = null;
		for (int i = 0; i < files.length; i++)
		{
			try
			{
				if (!files[i].getName().contains("tmp"))
				{
					continue;
				}
				StringBuffer buffer = new StringBuffer();
				StringBuffer tempBufferString = new StringBuffer();
				reader = new BufferedReader(new InputStreamReader(
						new FileInputStream(files[i]), "GBK"));
				String temp = "";
				int count = 0;
				while ((temp = reader.readLine()) != null)
				{
					tempBufferString.append(temp + "\r\n");
					if (temp.contains("指导价"))
					{
						if (count % 2 != 0)
						{
							buffer.append("\r\n").append(tempBufferString);
						} else
						{
							buffer.append(tempBufferString);
						}
						tempBufferString = new StringBuffer();
						count = 0;
					}
					count++;
				}
				FileOutputStream fos = new FileOutputStream(files[i]
						.getAbsolutePath().replace(".txt.tmp", "") + ".result");
				fos.write(buffer.toString().getBytes("GBK"));
				fos.close();
				reader.close();
				System.out.println("文件删除：" + files[i].delete());
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			} finally
			{
				try
				{
					if (null != reader)
					{
						reader.close();
					}
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	private void processResultFile(String filePath)
	{
		File f = new File(filePath);
		File[] files = f.listFiles();
		BufferedReader reader = null;
		for (int i = 0; i < files.length; i++)
		{
			try
			{
				if (!files[i].getName().contains("result"))
				{
					continue;
				}
				reader = new BufferedReader(new InputStreamReader(
						new FileInputStream(files[i]), "GBK"));
				String temp = "";
				int flag = 0;
				CarInfo info = null;
				String carType = "";
				boolean isNewCarType = true;
				while ((temp = reader.readLine()) != null)
				{
					if ("".equals(temp))
					{
						isNewCarType = true;
						continue;
					}
					if (flag == 0)
					{
						info = new CarInfo();
						if (isNewCarType)
						{
							carType = temp;
							isNewCarType = false;
							flag = 1;
						} else
						{
							info.setCarName(temp);
							flag = 2;
						}
						info.setCarType(carType);

					} else if (flag == 1)
					{
						info.setCarName(temp);
						flag = 2;
					} else if (flag == 2)
					{
						info.setMinPrice(Double.parseDouble(temp.substring(temp.indexOf("：") + 1,
								temp.indexOf("-"))));
						info.setMaxPrice(Double.parseDouble(temp.substring(temp.indexOf("-") + 1,
								temp.length() - 1)));
						carInfoDao.save(info);
						flag = 0;
					}

				}
				reader.close();
				System.out.println("文件删除：" + files[i].delete());
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			} finally
			{
				try
				{
					if (null != reader)
					{
						reader.close();
					}
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
