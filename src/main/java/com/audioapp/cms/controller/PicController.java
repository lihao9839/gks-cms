package com.audioapp.cms.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.audioapp.cms.dto.PicDTO;
import com.audioapp.cms.helper.ResultHelper;
import com.audioapp.cms.mapper.PicDTOMapper;
import com.audioapp.cms.service.PicService;

/**
 * 图片控制类
 * @author Administrator
 *
 */
@Controller
public class PicController {
	
	@Autowired
	private PicDTOMapper picMapper;
	
	@Autowired
	private PicService picService;
	
	/**
	 * 跳转分享和二维码
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/goShare")
	public String goShare(HttpServletRequest request, Model model){
		String type = "0";
		PicDTO pic = picService.getPicByType(type);
		if(null == pic){
			pic = new PicDTO();
		}
		model.addAttribute("pic", pic);
		return "share";
	}
	
	/**
	 * 跳转关于我们
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/goAboutUs")
	public String goAboutUs(HttpServletRequest request, Model model){
		String type = "1";
		PicDTO pic = picService.getPicByType(type);
		if(null == pic){
			pic = new PicDTO();
		}
		model.addAttribute("pic", pic);
		return "about_us";
	}
	
	/**
	 * 新增或者更新图片
	 * @param request
	 * @return
	 */
	@RequestMapping("/addOrUpdatePic")
	@ResponseBody
	public Map<String, Object> addOrUpdatePic(HttpServletRequest request){
		PicDTO pic = new PicDTO();
		pic.setPicUrl(request.getParameter("picUrl"));
		pic.setType(request.getParameter("type"));
		String id = request.getParameter("id");
		if(!StringUtils.isEmpty(id)) {
			pic.setId(Long.valueOf(id));
		}
		if(picService.addOrUpdate(pic) > 0){
			return ResultHelper.succMap();
		}
		return ResultHelper.errMap();
	}
}
