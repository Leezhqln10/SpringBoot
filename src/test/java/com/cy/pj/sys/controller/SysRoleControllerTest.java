package com.cy.pj.sys.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.pj.common.vo.JsonResult;

@SpringBootTest
public class SysRoleControllerTest {

	@Autowired
	private SysRoleController sysRoleController;
	
	@Test
	public void testDoFindPageObjects() {
		JsonResult page = sysRoleController.doFindPageObjects("", 1);
		System.out.println(page);
	}
}
