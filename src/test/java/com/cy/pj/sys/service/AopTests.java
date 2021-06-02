package com.cy.pj.sys.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysLog;


@SpringBootTest
public class AopTests {

	@Autowired
	private SysLogService sysLogService;
	
	@Test
	public void testSysLogService() {
		PageObject<SysLog> pageObjects = sysLogService.findPageObjects("", 1);
		System.out.println("rowCount="+pageObjects.getRowCount());
	}
	
}
