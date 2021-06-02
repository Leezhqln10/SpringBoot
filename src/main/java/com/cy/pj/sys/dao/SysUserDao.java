package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.cy.pj.common.vo.SysUserDeptVo;
import com.cy.pj.sys.entity.SysUser;
@Mapper
public interface SysUserDao {
/*
 * 说明：
1>当DAO中方法参数多余一个时尽量使用@Param注解进行修饰并指定名字，然后在Mapper文件中便可以通过类似#{username}方式进行获取，否则只能通过#{arg0}，#{arg1}或者#{param1}，#{param2}等方式进行获取。
2>当DAO方法中的参数应用在动态SQL中时无论多少个参数，尽量使用@Param注解进行修饰并定义。
3>jdk8后可以不用，可变参数时需要用到,如果没有使用@Param，则需要在映射的生sql文件中需要用array来接收
*/
	
	@Update("update sys_users set password=#{password},salt=#{salt},modifiedTime=now() where username=#{username}")
	int updatePassword(String username,String password,String salt);
	
	
	SysUser findUserByUserName(String username);
	
	/**
	 * 根据username来查询
	 * @param username
	 * @return
	 */
	int getRowCount(@Param("username")String username);
	/**
	 * 页面数据呈现的查询
	 * @param username
	 * @return
	 */
	List<SysUserDeptVo> findPageObjects(String  username);
	
	/**
	 * 启动、禁用的更改
	 * @param id
	 * @param valid
	 * @param modifiedUser
	 * @return
	 */
	int validById(@Param("id") Integer id,@Param("valid") Integer valid,@Param("modifiedUser") String modifiedUser);
	
	/**
	 * 数据的添加
	 * @param entity
	 * @return
	 */
	int insertObject(SysUser entity);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	SysUserDeptVo findObjectById(Integer id);
	
	/**
	 * 根据id更新数据
	 * @param id
	 * @return
	 */
	int updateObjectById(SysUser entity);
	
}
