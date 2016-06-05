package org.whh.util;

import java.util.Random;

public class RandomHelper
{
	private static Random random = new Random();

	public static boolean getRandomBoolean()
	{
		return random.nextBoolean();
	}

	public static int getRandom(int bound)
	{
		return random.nextInt(bound);
	}

	public static int getRandom(int bound, int base)
	{
		return base + random.nextInt(bound);
	}
}
