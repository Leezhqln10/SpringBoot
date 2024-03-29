package com.cy.pj.common.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class SysRoleMenuVo implements Serializable {

	private static final long serialVersionUID = -8736534314621626350L;
	
	private Integer id;
	private String name;
	private String note;
	private List<Integer> menuIds;
	
}
