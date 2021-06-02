package com.cy.pj.sys.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.common.vo.SysRoleMenuVo;
import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.service.SysRoleService;

//@Controller
//@ResponseBody
@RestController //@Controller+@ResponseBody
@RequestMapping("/role/")
public class SysRoleController {

	
	@Autowired
	private SysRoleService sysRoleService;
	
	
	@RequestMapping("doFindPageObjects")
	public JsonResult doFindPageObjects(String name, Integer pageCurrent) {
		PageObject<SysRole> pageObjects = sysRoleService.findPageObjects(name, pageCurrent);
		return new JsonResult(pageObjects);
	}
	
	@RequestMapping("doDeleteObject")
	public JsonResult doDeleteObject(Integer id) {
		sysRoleService.deleteObject(id);
		return new JsonResult("delete ok!");
	}
	
	@RequestMapping("doSaveObject")
	public JsonResult doSaveObject( SysRole entity, Integer[] menuIds) {
		sysRoleService.insertObject(entity, menuIds);
		return new JsonResult("Save ok!");
	}
	
	@RequestMapping("doFindObjectById")
	public JsonResult doFindObjectById( Integer id ) {
		SysRoleMenuVo sysRoleMenuVo = sysRoleService.findMenuIdsByRoleId(id);
		return new JsonResult(sysRoleMenuVo);
	}
	
	@RequestMapping("doUpdateObject")
//	public JsonResult doUpdateObject(SysRoleMenuVo entity) {
	public JsonResult doUpdateObject(SysRole entity, Integer[] menuIds) {
		//System.out.println(entity+"="+Arrays.toString(menuIds));
		sysRoleService.updateObject(entity, menuIds);
		return new JsonResult("Update Ok!");
	}
	
	/**
	 * 用于在用户页面的用户信息的修改增加时数据的呈现
	 * @return
	 */
	@RequestMapping("doFindRoles")
	public JsonResult doFindRoles() {
		return new JsonResult(sysRoleService.findCheckRole());
	}
}
