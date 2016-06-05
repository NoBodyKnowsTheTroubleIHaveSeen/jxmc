package org.whh.test;

import java.io.IOException;
import java.net.Socket;

public class ATest
{
	public static void main(String[] args)
	{
		System.out.println(finallyTest1());
		System.out.println(finallyTest2());
	}

	public static void switchTest()
	{
		/**
		 * switch只允许int,string,枚举
		 */
		int i = 5;
		switch (i)
		{
		case 5:
			break;
		default:
			break;
		}
	}

	/**
	 * finally的计算不返回到结果里
	 * 
	 * @return
	 */
	static int finallyTest1()
	{
		int x = 1;
		try
		{
			return x;

		} finally
		{
			x = 5;
			++x;
		}
	}

	static int finallyTest2()
	{
		try
		{
			return 1;
		}
		finally
		{
			return 2;
		}
	}
	/**
	 * 运行时异常
	 * NullPointerException、ArrayIndexOutOfBoundsException、ClassCastException
	 */
	/**
	 *  wait是Object类的方法，对此对象调用wait方法导致本线程放弃对象锁，进入等待此对象的等待锁定池，
	 *  只有针对此对象发出notify方法（或notifyAll）后本线程才进入对象锁定池准备获得对象锁进入运行状态。
	 */
	/**
	 * sleep就是正在执行的线程主动让出cpu，cpu去执行其他线程，在sleep指定的时间过后，cpu才会回到这个线程上继续往下执行，
	 * 如果当前线程进入了同步锁，sleep方法并不会释放锁，即使当前线程使用sleep方法让出了cpu，
	 * 但其他被同步锁挡住了的线程也无法得到执行。wait是指在一个已经进入了同步锁的线程内，让自己暂时让出同步锁，
	 * 以便其他正在等待此锁的线程可以得到同步锁并运行，只有其他线程调用了notify方法
	 */
}
