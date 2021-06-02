package com.cy.pj.sys.service;

import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysLog;


/**
 * 业务层接口：负责定义日志业务模块规则
 * 1)查询日志业务(添加分页业务实现)
 * 2)删除日志业务(后续会进行权限控制)
 * 3)添加日志业务(学了AOP再实现)
 */

public interface SysLogService {//SysLogServiceImpl为实现类

    /**
     * 定义日志的分页查询业务
     * @param username 用户名(数据最终来源为client)
     * @param pageCurrent 当前页码值(数据最终来源为client))
     * @return 封装当前页记录和分页信息的对象(PageObject)
     */
	PageObject<SysLog> findPageObjects( String username, Integer pageCurrent);
	
	/**
	 * 基于ID删除
	 * @param ids
	 * @return
	 */
	int deleteObjects( Integer... ids);
	
	/**
	 * 保存日志自动记录
	 * @param entity
	 */
	void saveObject(SysLog entity);
	
}
