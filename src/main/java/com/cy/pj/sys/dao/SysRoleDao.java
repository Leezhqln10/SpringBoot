package com.cy.pj.sys.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cy.pj.common.vo.CheckRoleVo;
import com.cy.pj.common.vo.SysRoleMenuVo;
import com.cy.pj.sys.entity.SysRole;



@Mapper
public interface SysRoleDao {
	
	
	List<CheckRoleVo> findCheckRole();
	
	int updateObject(SysRole entity);
	
	/**
	 * 基于Id进行查询
	 * @param id
	 * @return
	 */
	SysRoleMenuVo findMenuIdsByRoleId(Integer id);
	
	/**
	 * 保存角色自身信息
	 * @param entity
	 * @return
	 */
	int insertObject(SysRole entity);
	
	/**
	 * 基于角色id删除角色自身信息
	 * @param id
	 * @return
	 */
	int deleteObject(Integer id);
	
    /**
     * 基于角色名统计角色个数
     * @param name
     * @return
     */
	//int getRowCount(@Param("name") String name);
	
	 /**
	  * 基于条件查询当前页记录
	  * @param name
	  * @param startIndex
	  * @param pageSize
	  * @return
	  */
	//List<SysRole> findPageObjects(
	//					@Param("name")String name,
	//					@Param("startIndex") Integer startIndex,
	//					@Param("pageSize") Integer pageSize);  
	/**
	 * 利用pageHelper的插件来进行分页
	 * @param name
	 * @return
	 */
	List<SysRole> findPageObjects( @Param("name")String name );  
}
