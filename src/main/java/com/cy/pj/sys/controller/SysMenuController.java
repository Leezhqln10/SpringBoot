package com.cy.pj.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.bo.SysMenu;
import com.cy.pj.sys.service.SysMenuService;

//@Controller
//@ResponseBody
@RestController //@Controller+@ResponseBody
@RequestMapping("/menu/")
public class SysMenuController {

	
	@Autowired
	private SysMenuService sysMenuService;
	
	@RequestMapping("doFindObjects")
	public JsonResult doFindObjects() {
		List<SysMenu> list_objects = sysMenuService.findObjects();
		return new JsonResult(list_objects);
	}
	
	@RequestMapping("doDeleteObject")
	public JsonResult doDeleteObject(Integer id) {
		//System.out.println(id);
		sysMenuService.deleteObject(id);
		return new JsonResult("delete ok!");
	}
	
	
	@RequestMapping("doFindZtreeMenuNodes")
	public JsonResult doFindZtreeMenuNodes() {
		List<Node> list_data = sysMenuService.findZtreeMenuNodes();
		return new JsonResult(list_data);
	}
	
	@RequestMapping("doSaveObject")
	public JsonResult doSaveObject(SysMenu entity) {
		//System.out.println(entity);
		sysMenuService.insertObject(entity);
		return new JsonResult("save ok!");
	}
	
	@RequestMapping("doUpdateObject")
	public JsonResult oUpdateObject( SysMenu entity ) {
		sysMenuService.updateObject(entity);
		return new JsonResult("update ok!");
	}
	
}
