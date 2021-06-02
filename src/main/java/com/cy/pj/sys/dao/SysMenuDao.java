package com.cy.pj.sys.dao;

import java.util.List;
//import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.bo.SysMenu;


@Mapper
public interface SysMenuDao {

    /**
     * 查询菜单表中所有的菜单记录
     * 一行菜单记录映射为一个map对象(key为字段名，值为字段对应值)
     * @return
     */
	List<SysMenu> findObjects();
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	 
	int getChildCount(Integer id);
	
	int deletObject(Integer id);
	
	List<Node> findZtreeMenuNodes();
	
	int insertObject(SysMenu entity);
	
	int updateObject(SysMenu entity);
	
	List<String> findPermissionByIds(List<Integer> ids);
}
