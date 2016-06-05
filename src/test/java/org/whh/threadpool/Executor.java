package org.whh.threadpool;

import java.util.concurrent.LinkedBlockingQueue;

public class Executor implements Runnable
{
	private LinkedBlockingQueue<Task> taskQueue;

	public Executor(LinkedBlockingQueue<Task> taskQueue)
	{
		this.taskQueue = taskQueue;
	}

	private int count = 0;

	@Override
	public void run()
	{
		System.out.println("启动线程:" + Thread.currentThread().getName());
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (true)
		{
			Task task = getNextTask();
			if (task != null)
			{
				System.out.println("线程:" + Thread.currentThread().getName()
						+ "执行了" + ++count + "个任务");
				task.run();
				System.out.println("线程:" + Thread.currentThread().getName()
						+ "完成了" + ++count + "个任务");
			}
		}
	}

	public Task getNextTask()
	{
		Task task = null;
		try
		{
//			synchronized (taskQueue)
//			{	
//				if (taskQueue.isEmpty())
//				{
//					System.out.println(Thread.currentThread().getName()+"开始等待:" + System.currentTimeMillis());
//					taskQueue.wait();
//					System.out.println(Thread.currentThread().getName()+"结束等待:" + System.currentTimeMillis());
//				}
				task = taskQueue.take();
//			}
		} catch (Exception e)
		{
		}
		return task;
	}
}
