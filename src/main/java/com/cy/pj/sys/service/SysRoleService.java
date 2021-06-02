package com.cy.pj.sys.service;

import java.util.List;

import com.cy.pj.common.vo.CheckRoleVo;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.common.vo.SysRoleMenuVo;
import com.cy.pj.sys.entity.SysRole;

public interface SysRoleService {//SysRoleServiceImpl为实现类
	
	/**
	 * 用于在用户添加修改页面中呈现角色数据
	 * @return
	 */
	List<CheckRoleVo> findCheckRole();
	
	int updateObject(SysRole entity, Integer[] menuIds);
	
	/**
	 * 根据Id查找role的相关数据
	 * @param id
	 * @return
	 */
	SysRoleMenuVo  findMenuIdsByRoleId(Integer id) ;


	/**
	 * 保存角色以及角色和菜单关系数据
	 * @param entity
	 * @param menuIds
	 * @return
	 */
	int insertObject(SysRole entity, Integer[] menuIds);
	
	/**
	 * 基于角色id删除角色关系数据以及自身信息
	 * @param id
	 * @return
	 */
	int deleteObject(Integer id);
	
	PageObject<SysRole> findPageObjects(String name, Integer pageCurrent);
}

