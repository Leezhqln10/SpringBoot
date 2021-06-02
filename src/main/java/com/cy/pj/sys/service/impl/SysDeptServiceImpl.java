package com.cy.pj.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.bo.SysDept;
import com.cy.pj.sys.dao.SysDeptDao;
import com.cy.pj.sys.service.SysDeptService;

@Service
public class SysDeptServiceImpl implements SysDeptService {
	
	@Autowired
	private SysDeptDao sysDeptDao;

	@Override
	public List<SysDept> findObject(String name) {
		
			List<SysDept> list = sysDeptDao.findObjects(name);
			if( list == null || list.size() == 0) throw new ServiceException("对应查询不存在！");
		return list;
	}

	@Override
	public List<Node> findZtreeObject() {
		List<Node> ztreeObject_list = sysDeptDao.findZtreeObject();
		return ztreeObject_list;
	}
}
