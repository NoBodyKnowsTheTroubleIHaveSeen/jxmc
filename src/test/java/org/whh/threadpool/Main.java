package org.whh.threadpool;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		ThreadPool pool = ThreadPool.getThreadPool();
		Thread.sleep(5000);
		while (true)
		{
			Thread.sleep(20);
			pool.execute(new Task()
			{
				@Override
				public void run()
				{
					try
					{
						Thread.sleep(100);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName());
				}
			});
		}
	}
}
