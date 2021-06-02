package com.cy.pj.sys.dao;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.pj.sys.entity.SysLog;

@SpringBootTest
public class SysLogDaoTest {

	@Autowired
	private SysLogDao sysLogDao;
	
	@Test
	public void testGetRowCount() {
		int rowCount = sysLogDao.getRowCount("admin");
		System.out.println(rowCount);
	}
	
	@Test
	void testFindPageObjects() {
		List<SysLog> list = sysLogDao.findPageObjects("", 0,15);
		for(SysLog log:list) {
			 System.out.println(log);
			 
		 }
	}
	
	@Test
	void testDeleteObjects() {
		int rows = sysLogDao.deleteObjects(1,4);
		System.out.println(rows);
	}
}
