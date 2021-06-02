package com.cy.pj.sys.service.impl;


import java.util.List;
import java.util.UUID;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.cy.pj.common.annotation.RequiredLog;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.utils.ShiroUtils;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.common.vo.SysUserDeptVo;
import com.cy.pj.common.vo.UserDeptRoleVo;
import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.service.SysUserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {
	
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@Override
	public int updateObjectById(SysUser entity, Integer[] roleIds) {
		if(entity.getId()==null || entity.getId()<1) throw new IllegalArgumentException("id输入不合法");
		if(roleIds==null || roleIds.length==0) throw new IllegalArgumentException("必须为其指定角色");
		if(StringUtils.isEmpty(entity.getUsername())) throw new IllegalArgumentException("用户名不能为空");
		
		int rows = sysUserDao.updateObjectById(entity);
		sysUserRoleDao.deleteObjectsByUserId(entity.getId());
		sysUserRoleDao.insertObject(entity.getId(), roleIds);
		return rows;
	}
	
	@Override
	public UserDeptRoleVo findObjectBId(Integer id) {
		if( id == null || id <=0 ) throw new ServiceException("输入的参数id有误，id="+id);
		SysUserDeptVo sysUserDeptVo = sysUserDao.findObjectById(id);
		if(sysUserDeptVo==null) throw new ServiceException("对应查询不存在！");
		List<Integer> roleIds = sysUserRoleDao.findRoleByUserId(id);
		return new UserDeptRoleVo(sysUserDeptVo, roleIds);
	}
	
	@Override
	public int saveObject(SysUser entity, Integer[] roleIds) {
		long start=System.currentTimeMillis();
    	log.info("start:"+start);
    	//1.参数校验
    	if(entity==null)
    	throw new IllegalArgumentException("保存对象不能为空");
    	if(StringUtils.isEmpty(entity.getUsername()))
    	throw new IllegalArgumentException("用户名不能为空");
    	if(StringUtils.isEmpty(entity.getPassword()))
    	throw new IllegalArgumentException("密码不能为空");
    	if(roleIds==null || roleIds.length==0)
    	throw new IllegalArgumentException("至少要为用户分配角色");
    	//2.保存用户自身信息
        //2.1对密码进行加密
    	String source=entity.getPassword();
    	String salt=UUID.randomUUID().toString();
    	SimpleHash sh=new SimpleHash(//Shiro框架
    			"MD5",//algorithmName 算法
    			 source,//原密码
    			 salt, //盐值
    			 1);//hashIterations表示加密次数
    	entity.setSalt(salt);
    	entity.setPassword(sh.toHex());
    	int rows=sysUserDao.insertObject(entity);
    	//3.保存用户角色关系数据
    	sysUserRoleDao.insertObject(entity.getId(), roleIds);
    	long end=System.currentTimeMillis();
    	log.info("end:"+end);
    	log.info("total time :"+(end-start));
    	//4.返回结果
    	return rows;
	}
	@Transactional(readOnly = true)
	@RequiredLog(operation = "分页查询")
	@Override
	public PageObject<SysUserDeptVo> findObjects(String username, Integer pageCurrent) {
		String name = Thread.currentThread().getName();
		System.out.println(name+"=======");
		if( pageCurrent == null || pageCurrent < 1 ) throw new IllegalArgumentException("输入的页码有误！");
		/* 利用pageHelper来进行分页设计 */
		Integer pageSize = 2;
		Page<SysUserDeptVo> page = PageHelper.startPage(pageCurrent, pageSize);
		sysUserDao.findPageObjects(username);
		//return new PageObject<>(pageCurrent, pageSize, (int)page.getTotal(), page.getPages(), records);
		return new PageObject<>(pageCurrent, pageSize, (int)page.getTotal(), page.getPages(), page.getResult());
		
	}

	//@RequiresPermissions("sys:user:update")
	@Override
	public int validById(Integer id, Integer valid) {
		String modifiedUser = "admin";//后续修改为登录的用户名
		//1.参数检验
		if( id == null || id <=0 ) throw new ServiceException("输入的参数id有误，id="+id);
		if( valid != 1 && valid != 0 ) throw new ServiceException("输入的参数有误，valid="+valid);
		if( StringUtils.isEmpty(modifiedUser)) throw new ServiceException("修改用户不能为空");
		
		int rows = sysUserDao.validById(id, valid, modifiedUser);
		if( rows == 0 ) throw new ServiceException("操作的数据可能不存在！");
		return rows;
	}

	@Override
	public int updatePassword(String oldPassword, String newPassword, String cfgPassword) {
		if(StringUtils.isEmpty(oldPassword)) throw new IllegalArgumentException("原密码不能为空！");
		if(StringUtils.isEmpty(newPassword)) throw new IllegalArgumentException("新密码不能为空！");
		if(!newPassword.equals(cfgPassword)) throw new IllegalArgumentException("两次密码输入不一致！");
		//校验输入的原密码是否正确
		SysUser user = ShiroUtils.getUser();
		String oldSalt = user.getSalt();
		String sourceHashedPassword = user.getPassword();
		SimpleHash sh = new SimpleHash("MD5", oldPassword, oldSalt, 1);
		String hashInputPassword = sh.toHex();
		if(!sourceHashedPassword.equals(hashInputPassword)) throw new ServiceException("输入的原密码不正确！");
		
		//更新密码
		String newsalt = UUID.randomUUID().toString();
		sh = new SimpleHash("MD5",newPassword,newsalt,1);
		String newHashPassword = sh.toHex();
		int row = sysUserDao.updatePassword(user.getUsername(), newHashPassword, newsalt);
		if(row==0)throw new ServiceException("用户可能不存在！");
		return row;
	}
}
