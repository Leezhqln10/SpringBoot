package com.cy.pj.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;

@RequestMapping("/log/")
@Controller
public class SysLogController {

	@Autowired
	private SysLogService sysLogSrevice;
	
	/**
	 * 分页处理的方法doFindPageObjects 
	 * @param username 基于用户名进行查询
	 * @param pageCurrent 当前页面
	 * @return 控制层值对象，如果是正常数据，则返回数据，如果有异常，就封装异常并返回
	 */
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(String username, Integer pageCurrent) {
		PageObject<SysLog> pageObject = sysLogSrevice.findPageObjects(username, pageCurrent);
		return new JsonResult(pageObject);
	}
	
	@RequestMapping("doDeleteObjects")
	@ResponseBody
	public JsonResult doDeleteObjects(Integer...ids) {
		sysLogSrevice.deleteObjects(ids);
		return new JsonResult("delete ok!");
	}
	
}
