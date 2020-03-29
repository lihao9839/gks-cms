package com.audioapp.cms.controller;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import com.audioapp.cms.dto.WangDTO;
import com.audioapp.cms.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.audioapp.cms.helper.ResultHelper;
import com.audioapp.cms.service.UploadService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class CommonController {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

	@Autowired
	private UploadService uploadService;

	@Autowired
	private RedisService redisService;
	
	@PostMapping("/uploadFile")
	@ResponseBody
	public Map<String, Object> uploadFile(MultipartHttpServletRequest request, HttpServletRequest req){
		HttpSession session = req.getSession();
		MultipartFile file = request.getFile("file");
		if(file != null){
			String extensionName = this.getExtensionName(file.getOriginalFilename());
			try{
				InputStream is = file.getInputStream();
				File f = File.createTempFile("tmp", null);
				file.transferTo(f);
				f.deleteOnExit();
				String url = uploadService.uploadFile(f, extensionName, session);
				Map<String, Object> resultData = new HashMap<>();
				resultData.put("url", url);
				return ResultHelper.succMap(resultData);
			}catch(Exception e){
				logger.error("getInputStream exception. ", e);
			}
		}
		return ResultHelper.errMap();
	}

	/**
	 * 获取实时长传进度
	 * @param request
	 * @return
	 */
	@RequestMapping ("/uploadFile/percent")
	@ResponseBody
	public int getUploadPercent(HttpServletRequest request){
		HttpSession session = request.getSession();
		int percent = session.getAttribute("upload_percent") == null ? 0:  (Integer)session.getAttribute("upload_percent");
		return percent;
	}

	 /**
	 * 重置上传进度
	 * @param request
	 * @return
	 */
	@RequestMapping ("/uploadFile/percentReset")
	public void resetPercent(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.setAttribute("upload_percent",0);
	}

	@RequestMapping("/ueUploadImage")
	@ResponseBody
	public String ueUploadImage(MultipartHttpServletRequest request, HttpServletResponse response){
		MultipartFile file = request.getFile("file");
		Map<String, Object> resMap = new HashMap<>();
		if(file != null){
			String extensionName = this.getExtensionName(file.getOriginalFilename());
			try{
				InputStream is = file.getInputStream();
				return uploadService.uploadFile(is, extensionName);
			}catch(Exception e){
				logger.error("getInputStream exception. ", e);
			}
		}
		return null;
	}

	@RequestMapping("/uploadImage")
	@ResponseBody
	public WangDTO uploadImage(MultipartHttpServletRequest request, HttpServletResponse response){
		MultipartFile file = request.getFile("image");
		WangDTO result = new WangDTO();
		if(file != null){
			String extensionName = this.getExtensionName(file.getOriginalFilename());
			try{
				InputStream is = file.getInputStream();
				String url = uploadService.uploadFile(is, extensionName);
				String[] str = { url };
				result.setData(str);
				result.setErrno(0);
			}catch(Exception e){
				logger.error("getInputStream exception. ", e);
			}
		}
		return result;
	}

	/*
	 * Java文件操作 获取文件扩展名
	 *
	 *  Created on: 2011-8-2
	 *      Author: blueeagle
	 */
	private String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot >-1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}
}
