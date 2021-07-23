package com.cy.pj.sys.service;

import java.util.List;

import com.cy.pj.common.vo.Node;

public interface SysDeptService {

	List<Long> findObject(String name);
	
	List<Node> findZtreeObject();
}
