package com.cy.pj.sys.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;



@Mapper
public interface SysUserRoleDao {
	
	/**
	 * 基于user_id查询对应的role
	 * @return
	 */
	List<Integer> findRoleByUserId(Integer id);

	/**
	 * 基于角色ID删除对应的关系数据
	 * @param roleId
	 * @return
	 */
	int deleteObjectsByRoleId(Integer roleId);
	
	/**
	 * 在添加用户时增加user_role的对应关系数据
	 * @param userId
	 * @param roleId
	 * @return
	 */
	int insertObject(Integer userId,Integer[] roleIds);
	
	/**
	 * 根据user_id删除对应的关系数据
	 * @param user_id
	 * @return
	 */
	int deleteObjectsByUserId(Integer user_id);
}
