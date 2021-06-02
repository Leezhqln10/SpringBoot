package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.bo.SysDept;
/*
 * 说明：
	1>当DAO中方法参数多余一个时尽量使用@Param注解进行修饰并指定名字，然后在Mapper文件中便可以通过类似#{username}方式进行获取，否则只能通过#{arg0}，#{arg1}或者#{param1}，#{param2}等方式进行获取。
	2>当DAO方法中的参数应用在动态SQL中时无论多少个参数，尽量使用@Param注解进行修饰并定义。
	3>jdk8后可以不用，可变参数时需要用到,如果没有使用@Param，则需要在映射的生sql文件中需要用array来接收
 */

@Mapper
public interface SysDeptDao {
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	//@Select("select id,name,parentId,sort,note,createdTime,modifiedTime,createdUser,modifiedUser from sys_depts where id=#{id}")
	SysDept findById(Integer id);
	
	List<SysDept> findObjects(String name);
	
	List<Node> findZtreeObject();
}
