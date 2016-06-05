package org.whh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GoodsController
{
	@RequestMapping("/goods")
	public String index()
	{
		return "goods/goods";
	}
}
