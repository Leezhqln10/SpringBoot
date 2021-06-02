package com.cy.pj.sys.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.pj.common.vo.JsonResult;

@SpringBootTest
public class SysMenuControllerTest {

	@Autowired
	private SysMenuController sysMenuController;
	
	@Test
	public void testdoFindObjects() {
		JsonResult doFindObjects = sysMenuController.doFindObjects();
		System.out.println(doFindObjects);
	}
}
