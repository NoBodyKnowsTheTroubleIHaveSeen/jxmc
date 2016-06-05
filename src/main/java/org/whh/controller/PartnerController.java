package org.whh.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.whh.base.ControllerBase;
import org.whh.dao.PartnerDao;
import org.whh.entity.Code;
import org.whh.entity.Partner;
import org.whh.service.PartnerService;
import org.whh.web.CommonException;

@Controller
public class PartnerController extends ControllerBase
{
	private Logger logger = LoggerFactory.getLogger(PartnerController.class);
	@Autowired
	PartnerDao partnerDao;
	@Autowired
	PartnerService partnerService;

	private void getCode(Model model)
	{
		List<Code> partnerLevel = codeDao.findByCodeGroup("partnerLevel");
		List<Code> sex = codeDao.findByCodeGroup("sex");
		List<Code> compare = codeDao.findByCodeGroup("compare");
		model.addAttribute("partnerLevel", partnerLevel);
		model.addAttribute("sex", sex);
		model.addAttribute("compare", compare);
	}

	@RequestMapping("/partner")
	public String index(Model model)
	{
		getCode(model);
		return "partner/partner";
	}

	@ResponseBody
	@RequestMapping(value = "/getPartners", produces = "application/json")
	public Page<Partner> getPartner(Partner partner, Pageable pageable,Integer deductPercentageCompare)
	{
		Page<Partner> result = partnerService.findAllByPage(partner, pageable,deductPercentageCompare);
		return result;
	}

	@RequestMapping("/saveOrUpdatePartner")
	public String saveOrUpdatePartner(Long id, Model model)
	{
		getCode(model);
		if (!isNull(id))
		{
			Partner partner = partnerDao.findOne(id);
			model.addAttribute("partner", partner);
		} else
		{
			model.addAttribute("partner", new Partner());
		}
		return "partner/saveOrUpdatePartner";
	}

	@RequestMapping("/doSaveOrUpdatePartner")
	public String doSaveOrUpdatePartner(Partner partner) throws CommonException
	{
		try
		{
			partnerDao.save(partner);
		} catch (Exception e)
		{
			logger.error("保存partner失败", e);
			throw new CommonException("保存用户信息失败");
		}
		return "partner/partner";
	}

	@RequestMapping("/deletePartner")
	public String deletePartner(Long id) throws CommonException
	{
		if (isNull(id))
		{
			logger.error("非法请求，删除的用户id为空");
			throw new CommonException("非法请求!");
		}
		partnerDao.delete(id);
		return "partner/partner";
	}
}