package com.cy.pj.sys.dao;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.pj.common.vo.SysUserDeptVo;

@SpringBootTest
public class SysUserDaoTest {
	
	@Autowired
	private SysUserDao sysUserDao;
	
	@Test
	public void testGetRowCount() {
		int rowCount = sysUserDao.getRowCount("");
		System.out.println(rowCount);
	}
	
	@Test
	public void testFindPageObjects() {
		List<SysUserDeptVo> list = sysUserDao.findPageObjects("");
		System.out.println(list);
	}

	@Test
	public void testValidById() {
		int rows = sysUserDao.validById(20, 0, "admin");
		System.out.println(rows);
	}
	
	@Test
	public void testFindObjectById() {
		SysUserDeptVo objectById = sysUserDao.findObjectById(5);
		System.out.println(objectById);
	}
}
