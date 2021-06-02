package com.cy.pj.sys.service;

import java.util.List;

import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.bo.SysDept;

public interface SysDeptService {

	List<SysDept> findObject(String name);
	
	List<Node> findZtreeObject();
}
