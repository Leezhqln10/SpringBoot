package com.cy.pj.sys.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.vo.CheckRoleVo;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.common.vo.SysRoleMenuVo;
import com.cy.pj.sys.dao.SysRoleDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.service.SysRoleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
//@Transactional(timeout = 60,
//			   readOnly = false,
//			   isolation = Isolation.READ_COMMITTED,
//			   rollbackFor = Throwable.class,
//			   propagation = Propagation.REQUIRED)
@Service
public class SysRoleServiceImpl implements SysRoleService {
	
	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@Override
	public List<CheckRoleVo> findCheckRole() {
		List<CheckRoleVo> checkRole_list = sysRoleDao.findCheckRole();
		return checkRole_list;
	}
	
	@Override
	public int updateObject(SysRole entity, Integer[] menuIds) {
		//1.数据检验
		if(entity==null) throw new IllegalArgumentException("更新的数据不能为空！");
		if(entity.getId()==null) throw new IllegalArgumentException("输入的ID不能为空！");
//		if(entity.getName()==null || entity.getName()=="") throw new IllegalArgumentException("角色名不能为空");
		if(StringUtils.isEmpty(entity.getName())) throw new IllegalArgumentException("角色名不能为空");
		if(menuIds==null || menuIds.length==0) throw new IllegalArgumentException("必须为角色指定一个权限");
		
		int rows = sysRoleDao.updateObject(entity);
		if(rows==0) throw new ServiceException("对象可能已经不存在");
		 sysRoleMenuDao.deleteObjectsByRoleId(entity.getId());
		 sysRoleMenuDao.insertObjects(entity.getId(), menuIds);
		return rows;
	}
	
//	@Transactional(readOnly = true)
	@Override
	@Async
	public SysRoleMenuVo findMenuIdsByRoleId(Integer id) {
		String name = Thread.currentThread().getName();
		System.out.println("当前线程名字---->"+name);
		//1.参数校验
		if(id==null||id<1)
			throw new IllegalArgumentException("id值无效");
		//2.查询角色自身信息
		SysRoleMenuVo rm = sysRoleDao.findMenuIdsByRoleId(id);//id,name,note
		if(rm.getMenuIds()==null) throw new ServiceException("查无此对象！");
		//3.查询角色对应的菜单id
		//List<Integer> menuIds=sysRoleMenuDao.findMenuIdsByRoleId(id);
		//4.封装两次查询结果并返回
		//rm.setMenuIds(menuIds);
		return rm;
	}
	
	@Override
	public int deleteObject(Integer id) {
		if( id == null || id < 0 ) throw new IllegalArgumentException("id无效");
		//2.删除关系数据
		//2.1基于role_id删除角色菜单关系数据
		sysRoleMenuDao.deleteObjectsByRoleId(id);
		//2.2基于role_id删除角色和用户关系数据
		sysUserRoleDao.deleteObjectsByRoleId(id);
		//3.删除自身信息
		int rows = sysRoleDao.deleteObject(id);
		if( rows == 0 ) throw new ServiceException("此记录可能已经不存在");
		return rows;
	}

	@Override
	public int insertObject(SysRole entity, Integer[] menuIds) {
		//1.参数校验
		if( entity == null ) 
			throw new IllegalArgumentException("保存对象不能为空！");
		if( StringUtils.isEmpty(entity.getName()))//StringUtils用的是spring框架的一个工具类
			throw new IllegalArgumentException("名字不能为空！");
		//2.保存角色自身信息
		int rows = sysRoleDao.insertObject(entity);
		//3.保存角色和菜单关系数据
		sysRoleMenuDao.insertObjects(entity.getId(), menuIds);
		return rows;
	}
	
	// 利用pagehelper插件进行分页
	@Transactional(readOnly = true)
	@Override
	public PageObject<SysRole> findPageObjects(String name, Integer pageCurrent) {
		if(pageCurrent==null || pageCurrent < 1 ) throw new IllegalArgumentException("当前页码不正确!");
		//2.查询当前页角色记录
		//2.1设置查询参数
		Integer pageSize = 2;
		Page<SysRole> page = PageHelper.startPage(pageCurrent,pageSize);
		//2.2启动查询操作
		List<SysRole> records = sysRoleDao.findPageObjects(name);
		//System.out.println("=====");
		//System.out.println(records);
		//System.out.println("=====");
		//3.封装查询结果
		return new PageObject<>(pageCurrent, pageSize, (int)page.getTotal(), (int)page.getPages(), records);
		/* page.getTotal()---总行数 ，page.getPages()---总页数 */
	}
	/*@Override
	public PageObject<SysRole> findPageObjects(String name, Integer pageCurrent) {
		
		if(pageCurrent==null || pageCurrent < 1 ) throw new IllegalArgumentException("当前页码不正确!");
		
		Integer rowCount = sysRoleDao.getRowCount(name);
		if(rowCount==0) throw new ServiceException("对应查询系统无记录");
		
		Integer pageSize = 2;
		Integer startIndex = (pageCurrent-1)*pageSize;
		List<SysRole> records = sysRoleDao.findPageObjects(name, startIndex, pageSize);
		PageObject<SysRole> pageObject = new PageObject<SysRole>(pageCurrent, pageSize, rowCount, records);
		return pageObject;
	}*/
}
