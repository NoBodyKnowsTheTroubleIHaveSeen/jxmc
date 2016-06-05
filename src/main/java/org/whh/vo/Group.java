package org.whh.vo;

import java.util.List;

public class Group
{
	private List<GroupInfo> group_list;

	private List<String> redwords;

	private int endflag;

	public void setEndflag(int endflag)
	{
		this.endflag = endflag;
	}

	public int getEndflag()
	{
		return this.endflag;
	}

	public List<GroupInfo> getGroup_list()
	{
		return group_list;
	}

	public void setGroup_list(List<GroupInfo> group_list)
	{
		this.group_list = group_list;
	}

	public List<String> getRedwords()
	{
		return redwords;
	}

	public void setRedwords(List<String> redwords)
	{
		this.redwords = redwords;
	}

}
