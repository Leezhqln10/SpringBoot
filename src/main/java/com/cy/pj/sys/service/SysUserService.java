package com.cy.pj.sys.service;



import com.cy.pj.common.vo.PageObject;
import com.cy.pj.common.vo.SysUserDeptVo;
import com.cy.pj.common.vo.UserDeptRoleVo;
import com.cy.pj.sys.entity.SysUser;

public interface SysUserService {
	
	int updatePassword(String oldPassword,String newPassword,String cfgPassword);
	
	int updateObjectById(SysUser entity, Integer[] roleIds);
	
	/**
	 * 根据id修改用户信息
	 * @param id
	 * @return
	 */
	UserDeptRoleVo findObjectBId(Integer id);
	
	/**
	 * 增加用户信息
	 * @param entity
	 * @param roleIds
	 * @return
	 */
	int saveObject(SysUser entity, Integer[] roleIds);

	/**
	 * user页面的呈现的数据查询
	 * @param username
	 * @param pageCurrent
	 * @return
	 */
	PageObject<SysUserDeptVo> findObjects(String username, Integer pageCurrent);
	
	/**
	 * 更改权限--启用、禁止
	 * @param id
	 * @param valid
	 * @return
	 */
	int validById(Integer id, Integer valid);
	
}
