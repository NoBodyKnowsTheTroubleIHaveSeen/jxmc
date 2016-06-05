package org.whh.base;

public class ObjectBase
{
	public boolean isNull(Object obj)
	{
		if (obj == null|| "".equals(obj))
		{
			return true;
		}
		return false;
	}
}
