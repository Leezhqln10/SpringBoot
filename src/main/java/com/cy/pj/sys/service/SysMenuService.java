package com.cy.pj.sys.service;

import java.util.List;

import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.bo.SysMenu;

/**
 * 此接口中定义菜单模块的业务操作标准
 */
public interface SysMenuService {//SysMenuServiceImpl为实现类
	
    /**
     * 查询所有菜单以及菜单对应的上级菜单
     * @return
     */
    List<SysMenu> findObjects();
    
    List<Node> findZtreeMenuNodes();
    
    int insertObject( SysMenu entity );
    
	/**
	 * 基于菜单id删除菜单信息以及菜单对应的关系数据
	 * @param id
	 * @return
	 */
    int deleteObject( Integer id);
    
    int updateObject( SysMenu entity );
    
}
