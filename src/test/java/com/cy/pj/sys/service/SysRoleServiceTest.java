package com.cy.pj.sys.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.pj.common.vo.PageObject;
import com.cy.pj.common.vo.SysRoleMenuVo;
import com.cy.pj.sys.entity.SysRole;

@SpringBootTest
public class SysRoleServiceTest {

	@Autowired
	private SysRoleService sysRoleService;
	
	@Test
	public void testFindPageObjects() {
		PageObject<SysRole> pageObjects = sysRoleService.findPageObjects("", 2);
		System.out.println(pageObjects);
	}
	
	@Test
	public void testFindObjectById() {
		while(true) {
		SysRoleMenuVo objectById = sysRoleService.findMenuIdsByRoleId(48);
		System.out.println(objectById);
	}
	}
	
}
