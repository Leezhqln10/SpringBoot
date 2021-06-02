package com.cy.pj.sys.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysLog;

@SpringBootTest
public class SysLogServiceTest {

	
	@Autowired
	private SysLogService sysLogService;
	@Test
	public void testfindPageObjects() {
		PageObject<SysLog> pageObject = sysLogService.findPageObjects("", 1);
		System.out.println(pageObject);
		List<SysLog> sysLoglist = pageObject.getRecords();
		for (SysLog sysLog : sysLoglist) {
			System.out.println(sysLog);
		}
	}
	
	@Test
	public void testDeleteObjects() {
		int rows = sysLogService.deleteObjects(12,2,3);
		System.out.println(rows);
	}
}
