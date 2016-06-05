package org.whh.vo;


/**
 * 在网页上登陆qq或访问空间、邮件，留下cookie等消息。访问以下url
 * http://find.qq.com/index.html?version=1&im_version=5425&width=910&height=610&search_target=0
 * 通过js代码模拟点击查询群信息，之后获得qq群json信息，并传回本网站。
 * 反序列化得到的对象即Root对象。
 * @author acer
 *
 */
public class Root
{
	private int retcode;

	private Result result;

	public void setRetcode(int retcode)
	{
		this.retcode = retcode;
	}

	public int getRetcode()
	{
		return this.retcode;
	}

	public void setResult(Result result)
	{
		this.result = result;
	}

	public Result getResult()
	{
		return this.result;
	}

}
