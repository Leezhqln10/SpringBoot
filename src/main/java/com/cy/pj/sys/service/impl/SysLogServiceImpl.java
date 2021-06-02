package com.cy.pj.sys.service.impl;


import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.dao.SysLogDao;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;

@Service
public class SysLogServiceImpl implements SysLogService {

	@Autowired
	private SysLogDao sysLogDao;

	//这里异步写日志操作，同样使用的是AOP
	//@Async描述的方法为切入点
	//这个切入点上执行的异步操作为通知(Advice)
	//由此注解描述的方法，用于告诉spring框架这个方法要运行一个异步线程上(此线程由spring线程池提供)。
	@Async 
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	@CacheEvict(value="nnn",allEntries = true)
	public void saveObject(SysLog entity) {
		String name = Thread.currentThread().getName();
		System.out.println(name);
		try {
			Thread.sleep(0);
		}catch (Exception e) {
			// TODO: handle exception
		}
		sysLogDao.insertObject(entity);
	}
	
	//@RequiresPermissions("sys:log:view")
	@Cacheable(value="nnn")
	@Override
	public PageObject<SysLog> findPageObjects(String username, Integer pageCurrent) {
		
		//1.参数校验(思考username允许为空吗？允许)
		//请问如下参数校验是否可以颠倒"||"符号两侧的顺序？(不可以)
		//非空的校验要放在第一个，因为null与其他的类型比较也会出现空指针异常
		if( pageCurrent == null || pageCurrent < 1) throw new IllegalArgumentException("当前页码不正确");
		//获取对应name的数据库的总数（name可以不写，这时查询的是总的记录数）
		Integer rowCount = sysLogDao.getRowCount(username);
		//判断是否存在数据记录
		//自定义异常ServiceException
		if(rowCount == 0) throw new ServiceException("对应查询系统无记录");
		//定义每页的记录数，也就是页面大小
		Integer pageSize = 10;
		//计算每页的起始索引
		Integer startIndex = (pageCurrent - 1)*pageSize;
		//根据索引，页面大小获取对应的相应页面的数据记录
		List<SysLog> records = sysLogDao.findPageObjects(username, startIndex, pageSize);
		//将数据封装到页面对象并返回
		PageObject<SysLog> pageObject = new PageObject<>(pageCurrent, pageSize, rowCount, records);
		return pageObject;
	}

	@Override
	@CacheEvict(value="nnn",allEntries = true)
	public int deleteObjects(Integer... ids) {
		if( ids == null || ids.length == 0 ) throw new IllegalArgumentException("您没有选择要删除的数据，请选择！");
		
		int rows;
		try {
			rows = sysLogDao.deleteObjects(ids);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("系统维护中...");
		}
		if( rows == 0 ) {
			throw new ServiceException("数据不存在...");
		}
		return rows;
	}

}
