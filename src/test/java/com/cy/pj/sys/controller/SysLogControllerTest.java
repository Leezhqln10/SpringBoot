package com.cy.pj.sys.controller;

//import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.pj.common.vo.JsonResult;
//import com.cy.pj.common.vo.PageObject;
//import com.cy.pj.sys.entity.SysLog;

@SpringBootTest
public class SysLogControllerTest {

	@Autowired
	private SysLogController sysLogController;
//	@Test
//	public void testdoFindPageObjects() {
//		JsonResult jsonResult = sysLogController.doFindPageObjects("", 1);
//		System.out.println(jsonResult);
//		PageObject<SysLog> data = (PageObject<SysLog>) jsonResult.getData();
//		System.out.println(data);
//		List<SysLog> records = data.getRecords();
//		for (SysLog sysLog : records) {
//			System.out.println(sysLog);
//		}
//	}
	
	@Test
	public void testdoDeleteObjects() {
		JsonResult doDeleteObjects = sysLogController.doDeleteObjects(13,2,2);
		System.out.println(doDeleteObjects);
	}
}
