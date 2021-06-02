package com.cy.pj.sys.dao;


import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.pj.common.vo.SysRoleMenuVo;
import com.cy.pj.sys.entity.SysRole;



@SpringBootTest
public class SysRoleDaoTest {

	@Autowired
	private SysRoleDao sysRoleDao;
	
//	@Test
//	public void testGetRowCount() {
//		int rows = sysRoleDao.getRowCount("超级管理员");
//		System.out.println(rows);
//	}
	
	@Test
	public void testFindPageObjects() {
		List<SysRole> list = sysRoleDao.findPageObjects("");
		System.out.println(list);
	}
	
	@Test
	public void testFindObjectById() {
		for(int i =0;i<100;i++){
			SysRoleMenuVo findObjectById = sysRoleDao.findMenuIdsByRoleId(50);
			System.out.println(findObjectById);
		}
//		String[] s=new String[0];
//		System.out.println(s);
//		System.out.println(s.length);
	}
}
