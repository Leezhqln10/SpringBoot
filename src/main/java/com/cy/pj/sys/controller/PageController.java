package com.cy.pj.sys.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import com.cy.pj.common.utils.ShiroUtils;
import com.cy.pj.sys.entity.SysUser;

/**
 * 计划：所有涉及到页面返回的方法都定义在此Controller中
 * @author pc
 */
@Controller
@RequestMapping("/")
public class PageController {

	
	@RequestMapping("doIndexUI")
	public String doIndexUI(Model model) {
		SysUser user = ShiroUtils.getUser();
		model.addAttribute("user",user);
		System.out.println(model);
		System.out.println(user);
		return "starter";
	}
	
	@RequestMapping("doPageUI")
	public String doPageUI() {
		return "common/page";
	}
	
	@RequestMapping("doLogin")
	public String doLogin() {
		return "login";
	}

	@RequestMapping("/custom")
	public void add(@RequestBody Map<String,Object> re, HttpServletRequest request ) {
		StandardMultipartHttpServletRequest multipartHttpServletRequest = null;
		multipartHttpServletRequest = (StandardMultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartHttpServletRequest.getFileMap();
		for (String filename : fileMap.keySet()) {
			MultipartFile file = fileMap.get(filename);
			String fileName = file.getName();
			String originalFilename = file.getOriginalFilename();
			System.out.println("文件名： " + fileName + " 文件全名：" + originalFilename);

		}
	}


//	@RequestMapping("log/log_list")
//	public String doLogListUI() {
//		return "sys/log_list";
//	}
	
	
//	@RequestMapping("/menu/menu_list")
//	public String doMenuUI() {
//		//System.out.println(1223);
//		return "sys/menu_list";
//	}
	
	//rest风格(软件编码架构风格)的url定义
	//语法格式:/{a}/{b}/...;其中{}中的内容可以理解为一个变量
	//@PathVariable 注解可以描述方法参数，用于获取url中与方法参数相同的变量值
	@RequestMapping("/{module}/{moduleUI}")
	public String doModuleUI(@PathVariable String moduleUI) {
		//System.out.println(123);
		return "sys/"+moduleUI;
	}
	
}
