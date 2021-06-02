package com.cy.pj.sys.dao;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.bo.SysMenu;


@SpringBootTest
public class SysMenuDaoTest {

	
	@Autowired
	private SysMenuDao sysMenuDao;
	
	@Test
	public void testFindObjects() {
		List<SysMenu> list = sysMenuDao.findObjects();
		System.out.println(list);
	}
	
	@Test
	public void testFindZtreeMenuNodes() {
		List<Node> list = sysMenuDao.findZtreeMenuNodes();
		System.out.println(list);
	}
	
}
