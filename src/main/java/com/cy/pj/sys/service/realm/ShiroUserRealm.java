package com.cy.pj.sys.service.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.pj.sys.dao.SysMenuDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysUser;


@Service
public class ShiroUserRealm extends AuthorizingRealm {

	@Autowired
	private SysUserDao sysUserDao;

	@Autowired
	private SysUserRoleDao sysUserRoleDao;

	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;

	@Autowired
	private SysMenuDao sysMenuDao;

	/**负责获取加密凭证匹配器对象*/
	//@Override
	//public CredentialsMatcher getCredentialsMatcher() {
		//HashedCredentialsMatcher hMatcher=new HashedCredentialsMatcher();
		//hMatcher.setHashAlgorithmName("MD5");
		//hMatcher.setHashIterations(1);
		//return hMatcher;
	//}

	/**
	 * 设置凭证匹配器(与用户添加操作使用相同的加密算法)
	 */
	@Override
	public void setCredentialsMatcher(
	      CredentialsMatcher credentialsMatcher) {
		//构建凭证匹配对象
		HashedCredentialsMatcher cMatcher=
		new HashedCredentialsMatcher();
		//设置加密算法
		cMatcher.setHashAlgorithmName("MD5");
		//设置加密次数
		cMatcher.setHashIterations(1);
		super.setCredentialsMatcher(cMatcher);
	}
	/**
	 * 通过此方法完成认证数据的获取及封装,系统
	 * 底层会将认证数据传递认证管理器，由认证
	 * 管理器完成认证操作。
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token)
			throws AuthenticationException {
		//1.获取用户名(用户页面输入)
		UsernamePasswordToken upToken=
		(UsernamePasswordToken)token;
		String username=upToken.getUsername();
		//2.基于用户名查询用户信息
		SysUser user=
		sysUserDao.findUserByUserName(username);
		//3.判定用户是否存在
		if(user==null)
		throw new UnknownAccountException();
		//4.判定用户是否已被禁用。
		if(user.getValid()==0)
		throw new LockedAccountException();

		//5.封装用户信息
		ByteSource credentialsSalt=
		ByteSource.Util.bytes(user.getSalt());
		//记住：构建什么对象要看方法的返回值
		SimpleAuthenticationInfo info=
		new SimpleAuthenticationInfo(
				user,//principal (身份)
				user.getPassword(),//hashedCredentials
				credentialsSalt, //credentialsSalt
				getName());//realName
		//6.返回封装结果
		return info;//返回值会传递给认证管理器(后续
		//认证管理器会通过此信息完成认证操作)
	}
	/**
	 * 权限管理
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//1.获取登陆用户信息
		SysUser user = (SysUser)principals.getPrimaryPrincipal();
		//System.out.println(user+"1111111111");
		//2.基于用户ID获取对应的角色id
		List<Integer> roleIds = sysUserRoleDao.findRoleByUserId(user.getId());
		System.out.println(roleIds+"2222222");
		if(roleIds==null || roleIds.size()==0) throw new AuthorizationException();
		//3.基于角色id获取菜单的id信息
		List<Integer> menuIds = sysRoleMenuDao.findMenuIdsByRoleIds(roleIds);
		//System.out.println(menuIds+"3333333");
		if(menuIds==null || menuIds.size()==0) throw new AuthorizationException();
		//4.基于菜单id获取权限标识
		List<String> permissionList = sysMenuDao.findPermissionByIds(menuIds);
		//System.out.println(permissionList+"4444");
		//5.对权限标识信息进行封装并返回
		Set<String> set = new HashSet<>();
		for (String per : permissionList) {
			set.add(per);
		}
		//System.out.println(set+"55555");
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(set);
		return info;
	}


}
