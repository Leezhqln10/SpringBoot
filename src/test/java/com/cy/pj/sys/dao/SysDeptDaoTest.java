package com.cy.pj.sys.dao;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.bo.SysDept;

@SpringBootTest
public class SysDeptDaoTest {

	
	@Autowired
	private SysDeptDao sysDeptDao;
	
	@Test
	public void testFindObjects() {
		List<SysDept> list = sysDeptDao.findObjects("");
		System.out.println(list);
	}
	
	@Test
	public void testFindObject() {
		List<Node> ztreeObject = sysDeptDao.findZtreeObject();
		System.out.println(ztreeObject);
	}
}
