package com.cy.pj.sys.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;



@Mapper
public interface SysRoleMenuDao {
	
	
	/**
	 * 根据roleId增加role-menu的对应关系
	 * @param role_id
	 * @param menuIds
	 * @return
	 */
	int insertObjects(@Param("roleId") Integer role_id, @Param("menuIds") Integer[] menuIds);
	
	/**
	 * 基于roleId删除role-menu的对应关系
	 * @param menuId
	 * @return
	 */
	int deleteObjectsByRoleId(Integer roleId);
	
	/**
	 * 基于menuId删除role-menu的对应关系
	 * @param menuId
	 * @return
	 */
	int deleteObjectsByMenuId(Integer menuId);
	
	@Select("select menu_id from sys_role_menus where role_id=#{id}")
	List<Integer> findMenuIdsByRoleId(Integer id);
	
	List<Integer> findMenuIdsByRoleIds(List<Integer> ids);

	
}
