package com.cy.pj.common.vo;

import java.io.Serializable;
import java.util.Date;

import com.cy.pj.sys.bo.SysDept;

import lombok.Data;

@Data
public class SysUserDeptVo implements Serializable {

	private static final long serialVersionUID = 8527659769070554424L;

	private Integer id;
	private String username;
	private String password;
	private String salt;
	private String email;
	private String mobile;
	private Short valid;
	//private Integer deptId;
	private SysDept sysDept;
	private Date createdTime;
	private Date modifiedTime;
	private String createdUser;
	private String modifiedUser;
}