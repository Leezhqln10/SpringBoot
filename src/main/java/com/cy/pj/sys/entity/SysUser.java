package com.cy.pj.sys.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class SysUser implements Serializable {
	 
	private static final long serialVersionUID = -3574736526508453285L;

	private Integer id;
	private String username;
	private String password;
	private String salt;
	private String email;
	private String mobile;
	private Short valid = 1;
	private Integer deptId;
	private Date createdTime;
	private Date modifiedTime;
	private String createdUser;
	private String modifiedUser;
}
