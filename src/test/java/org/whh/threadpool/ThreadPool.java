package org.whh.threadpool;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool
{
	// 默认线程数
	private static final int DEFAULT_POOL_SIZE = 10;
	// 线程数
	private int threadCount;
	// 线程列表
	private ArrayList<Executor> threadList;
	// 任务列表
	private LinkedBlockingQueue<Task> taskQueue = new LinkedBlockingQueue<Task>();
	private ThreadPool()
	{
		this(DEFAULT_POOL_SIZE);
	}

	private ThreadPool(int threadCount)
	{
		this.threadCount = threadCount;
		threadList = new ArrayList<Executor>();
		init();
		start();
	}

	private void init()
	{
		for (int i = 0; i < threadCount; i++)
		{
			Executor executor = new Executor(taskQueue);
			threadList.add(executor);
		}
	}

	private void start()
	{
		for (int i = 0; i < threadCount; i++)
		{
			new Thread(threadList.get(i)).start();
		}
	}

	public static ThreadPool getThreadPool()
	{
		return new ThreadPool();
	}

	public static ThreadPool getThreadPool(int threadCount)
	{
		return new ThreadPool(threadCount);
	}

	public void execute(Task task)
	{
//		synchronized (taskQueue)
//		{
			try
			{
				taskQueue.put(task);
//				taskQueue.notify();
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//		}
	}

	public int getTaskCount()
	{
			return taskQueue.size();
	}

}
