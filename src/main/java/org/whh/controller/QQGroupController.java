package org.whh.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.whh.dao.QQGroupInfoDao;
import org.whh.entity.QQGroupInfo;
import org.whh.util.ProcessCarInfo;
import org.whh.vo.GroupInfo;
import org.whh.vo.Labels;
import org.whh.vo.Root;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Controller 负责注册一个bean 到spring 上下文中，bean 的ID 默认为类名称开头字母小写,你也可以自己指定
 */
@Controller
public class QQGroupController
{

	@Autowired
	QQGroupInfoDao qqGroupInfoDao;

	@Autowired
	ProcessCarInfo processCarInfo;

	/**
	 * @RequestMapping 用来定义访问的URL，你可以为整个类定义一个
	 *                 完整参数@RequestMapping(value="",method
	 *                 ={"",""},headers={},params={"",""})
	 * 
	 *                 value :String[] 设置访问地址
	 *
	 *                 method:
	 *                 RequestMethod[]设置访问方式，字符数组，查看RequestMethod类，包括GET, HEAD,
	 *                 POST, PUT, DELETE, OPTIONS,
	 *                 TRACE,常用:RequestMethod.GET，RequestMethod.POST
	 *
	 *                 headers:String[] headers一般结合method = RequestMethod.POST使用
	 *
	 *                 params: String[] 访问参数设置，字符数组 例如：userId=id
	 *                 可以通过设置参数条件来限制访问地址，例如params="myParam=myValue"表达式，访问地址中参数只有
	 *                 包含了该规定的值"myParam=myValue"才能匹配得上
	 *                 类似"myParam"之类的表达式也是支持的，表示当前请求的地址必须有该参数(参数的值可以是任意)
	 */

	@RequestMapping("/qqGroup")
	@ResponseBody
	public String index(String param,int level)
	{
		ObjectMapper mapper = new ObjectMapper();
		try
		{
			Root root = mapper.readValue(param, Root.class);
			if (root.getRetcode() != 0)
			{
				System.out.println("此次返回的结果代码为：" + root.getRetcode());
				return "fail";
			}
			List<GroupInfo> lists = root.getResult().getGroup().getGroup_list();
			for (GroupInfo l : lists)
			{
				try
				{
					QQGroupInfo info = new QQGroupInfo();
					info.setGroupCode(l.getCode());
					info.setGroupName(l.getName());
					info.setGroupMemo(l.getRichfingermemo());
					info.setGroupMemberNum(l.getMember_num());
					info.setGroupMaxMemberNum(l.getMax_member_num());
					info.setGroupOwnerNumber(l.getOwner_uin());
					info.setGeo(l.getGeo());
					info.setCityid(l.getCityid());
					info.setLevel(level);
					List<Labels> lables = l.getLabels();
					if (lables != null)
					{
						StringBuffer labelsBuffer = new StringBuffer();
						for (int i = 0; i < lables.size(); i++)
						{
							if (i == lables.size() - 1)
							{
								labelsBuffer.append(lables.get(i).getLabel());
							} else
							{
								labelsBuffer.append(lables.get(i).getLabel()
										+ ",");
							}
						}
						info.setLabels(labelsBuffer.toString());
					}
					List<String> qaddrs = l.getQaddr();
					if (qaddrs != null)
					{
						for (int i = 0; i < qaddrs.size(); i++)
						{
							if (i == 0)
							{
								info.setProvince(qaddrs.get(i));
							} else if (i == 1)
							{
								info.setCity(qaddrs.get(i));
							} else
							{
								break;
							}
						}
					}
					List<String> gcates = l.getGcate();
					if (gcates != null)
					{
						StringBuffer gcatesBuffer = new StringBuffer();
						for (int i = 0; i < gcates.size(); i++)
						{
							if (i == gcates.size() - 1)
							{
								gcatesBuffer.append(gcates.get(i));
							} else
							{
								gcatesBuffer.append(gcates.get(i) + ",");
							}
						}
						info.setGcate(gcatesBuffer.toString());
					}
					QQGroupInfo oldInfo = qqGroupInfoDao.findByGroupCode(info
							.getGroupCode());
					if (oldInfo == null)
					{
						qqGroupInfoDao.save(info);
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			System.out.println("------------end------------");
		} catch (JsonParseException e)
		{
			e.printStackTrace();
		} catch (JsonMappingException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return "succses";
	}

	@RequestMapping("/addCarInfo")
	@ResponseBody
	public String addCarInfo()
	{
		System.out.println("statrt");
		processCarInfo.getCarInfo("C:\\Users\\acer\\Desktop\\car-info");
		System.out.println("end");
		return "success";
	}
}
