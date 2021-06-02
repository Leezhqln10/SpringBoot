package com.cy.pj.sys.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.pj.sys.bo.SysDept;

@SpringBootTest
public class SysDeptServiceTest {

	@Autowired
	private SysDeptService sysDeptService;
	
	@Test
	public void testFindObjects() {
		List<SysDept> list = sysDeptService.findObject("");
		System.out.println(list);
	}
}
