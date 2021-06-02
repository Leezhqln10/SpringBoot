package com.cy.pj.sys.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.bo.SysDept;
import com.cy.pj.sys.service.SysDeptService;

@RestController
@RequestMapping("/dept/")
public class SysDeptController {

	
	@Autowired
	private SysDeptService sysDeptService;
	
	@RequestMapping("doFindObjects")
	public JsonResult doFindObjects(String name ) {
		List<SysDept> object_list = sysDeptService.findObject(name);
		return new JsonResult(object_list);
	}
	
	@RequestMapping("doFindZTreeNodes")
	public JsonResult doFindZTreeNodes() {
		List<Node> ztreeObject_list = sysDeptService.findZtreeObject();
		return new JsonResult(ztreeObject_list);
	}
	
}
