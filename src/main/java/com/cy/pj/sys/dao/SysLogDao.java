package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cy.pj.sys.entity.SysLog;
/**
 * 日志模块数据层接口：定义数据访问规范
 */
@Mapper
public interface SysLogDao {
/*
 * 说明：
1>当DAO中方法参数多余一个时尽量使用@Param注解进行修饰并指定名字，然后在Mapper文件中便可以通过类似#{username}方式进行获取，否则只能通过#{arg0}，#{arg1}或者#{param1}，#{param2}等方式进行获取。
2>当DAO方法中的参数应用在动态SQL中时无论多少个参数，尽量使用@Param注解进行修饰并定义。
3>jdk8后可以不用，可变参数时需要用到,如果没有使用@Param，则需要在映射的生sql文件中需要用array来接收
*/
	/**
	 * @param username 查询条件(例如查询哪个用户的日志信息)
	 * @return 总记录数(基于这个结果可以计算总页数)
	 */
	int getRowCount(@Param("username") String username);
	
	/**
	 * @param username  查询条件(例如查询哪个用户的日志信息)
	 * @param startIndex 当前页的起始位置
	 * @param pageSize 当前页的页面大小
	 * @return 当前页的日志记录信息
	 * 数据库中每条日志信息封装到一个SysLog对象中
	 */
	List<SysLog> findPageObjects(
			@Param("username") String username, 
			@Param("startIndex")Integer startIndex,
		    @Param("pageSize")Integer pageSize);
	
	/**
	 * 基于id执行日志删除
	 * @param ids 为参数id，（可变参数，相当于数组）
	 * @return
	 */
	int deleteObjects( @Param("ids")Integer... ids);
	
	 /**
	  * 将日志信息写入到数据库
	  * @param entity
	  * @return
	  */
	 int insertObject(SysLog entity);

	
}
