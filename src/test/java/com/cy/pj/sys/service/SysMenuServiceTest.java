package com.cy.pj.sys.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.pj.sys.bo.SysMenu;

@SpringBootTest
public class SysMenuServiceTest {

	@Autowired
	private SysMenuService sysMenuServer;
	
	@Test
	public void testFindObject() {
		List<SysMenu> list = sysMenuServer.findObjects();
		System.out.println(list);
	}
}
