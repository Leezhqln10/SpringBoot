package com.cy.pj.common.vo;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDeptRoleVo implements Serializable {

	private static final long serialVersionUID = -4255267006721463190L;

	private SysUserDeptVo user;
	private List<Integer> roleIds;
}
