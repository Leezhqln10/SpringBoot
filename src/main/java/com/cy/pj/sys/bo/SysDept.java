package com.cy.pj.sys.bo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;



@Data
public class SysDept implements Serializable {

	private static final long serialVersionUID = -8827144975575882337L;
	
	private Integer id;
	private String name;
	private Integer parentId;
	private String parentName;
	private String sort;
	private String note;
	private Date createdTime;
	private Date modifiedTime;
	private String createdUser;
	private String modifiedUser;
}
