package com.cy.pj.sys.controller;



import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.common.vo.SysUserDeptVo;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.service.SysUserService;

//@Controller
//@ResponseBody
@RestController //@Controller+@ResponseBody
@RequestMapping("/user/")
public class SysUserController {

	@Autowired
	private SysUserService sysUserService;
	
	@RequestMapping("doUpdatePassword")
	public JsonResult doUpdatePassword(String pwd,String newPwd,String cfgPwd) {
		sysUserService.updatePassword(pwd, newPwd, cfgPwd);
		return new JsonResult("update ok!");
	}
	
	@RequestMapping("doLogin")
	public JsonResult doLogin(boolean isRememberMe,String username, String password) {
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		if(isRememberMe) {
			token.setRememberMe(true);
		}
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.login(token);//将用户信息提交给securitymanager进行认证
		return new JsonResult("login ok!");
		
	}
	
	@RequestMapping("doFindPageObjects")
	public JsonResult doFindPageObjects(String username, Integer pageCurrent) {
		PageObject<SysUserDeptVo> pageObject = sysUserService.findObjects(username, pageCurrent);
		return new JsonResult(pageObject);
	}
	
	@RequestMapping("doValidById")
	public JsonResult doValidById(Integer id, Integer valid ) {
		sysUserService.validById(id, valid );
		return new JsonResult("成功修改！");
	}
	
	@RequestMapping("doSaveObject")
	public JsonResult doSaveObject(SysUser entity, Integer[] roleIds) {
		//System.out.println(entity+"==="+ Arrays.toString(roleIds));
		sysUserService.saveObject(entity, roleIds);
		//System.out.println(rows);
		return new JsonResult("添加保存成功！");
	}
	
	@RequestMapping("doFindObjectById")
	public JsonResult doFindObjectById(Integer id) {
		//System.out.println(id);
		return new JsonResult(sysUserService.findObjectBId(id));
	}
	
	@RequestMapping("doUpdateObject")
	public JsonResult doUpdateObject(SysUser entity, Integer[] roleIds) {
		//System.out.println(entity+"==="+roleIds);
		sysUserService.updateObjectById(entity, roleIds);
		return new JsonResult("更新修改成功！");
	}
}
