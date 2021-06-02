package com.cy.pj.sys.service.impl;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.bo.SysMenu;
import com.cy.pj.sys.dao.SysMenuDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.service.SysMenuService;

import io.micrometer.core.instrument.util.StringUtils;

@Service
public class SysMenuServiceImpl implements SysMenuService {
	
	@Autowired
	private SysMenuDao sysMenuDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;

	//@Cacheable(value="menuCache")
	//@RequiresPermissions("sys:menu:view")
	@Override
	public List<SysMenu> findObjects() {
		List<SysMenu> list = sysMenuDao.findObjects();
		if(list==null||list.size()==0)
			throw new ServiceException("没有找到对应的菜单");
		return list;
	}

	@Override
	public List<Node> findZtreeMenuNodes() {
		return sysMenuDao.findZtreeMenuNodes();
	}
	
	@CacheEvict(value="menuCache",allEntries=true)
	@Override
	public int insertObject(SysMenu entity) {
		//1.合法验证
		if(entity==null)
			throw new ServiceException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getName()))
			throw new ServiceException("菜单名不能为空");
		int rows;
		//2.保存数据
		try{
			rows=sysMenuDao.insertObject(entity);
		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		//3.返回数据
		return rows;

	}
	
	@CacheEvict(value="menuCache",allEntries=true)
	@Override
	public int deleteObject(Integer id) {
		//1.验证数据的合法性
		if(id==null||id<=0)
			throw new IllegalArgumentException("请先选择");
		//2.基于id进行子元素查询
		int count=sysMenuDao.getChildCount(id);
		if(count>0)
			throw new ServiceException("请先删除子菜单");
		//3.删除角色-菜单关系数据
		sysRoleMenuDao.deleteObjectsByMenuId(id);
		//4.删除菜单元素
		int rows=sysMenuDao.deletObject(id);
		if(rows==0)
			throw new ServiceException("此菜单可能已经不存在");
		//5.返回结果
		return rows;
	}

	@CacheEvict(value="menuCache",allEntries=true)
	@Override
	public int updateObject(SysMenu entity) {
		int rows = sysMenuDao.updateObject(entity);
		return rows;
	}


}
