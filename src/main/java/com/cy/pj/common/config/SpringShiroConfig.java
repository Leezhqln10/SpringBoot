package com.cy.pj.common.config;

import java.util.LinkedHashMap;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class SpringShiroConfig {
	@Bean
	public SessionManager sesionManager() {
		DefaultWebSessionManager sManager = new DefaultWebSessionManager();
		sManager.setGlobalSessionTimeout(2*60*1000);//两分钟
		return sManager;
	}

	@Bean
	public RememberMeManager rememberMeManager() {
		CookieRememberMeManager cManager = new CookieRememberMeManager();
		SimpleCookie cookie = new SimpleCookie("rememberMe");
		cManager.setCookie(cookie);
		return cManager;
	}

	/**
	 * 进行缓存操作，不用每次授权时都向数据库取数据
	 * @return
	 */
	@Bean
	public CacheManager shirocacheManager() {//这个CacheManager对象的名字不能写cacheManager,因为spring容器中已经存在一个名字为cacheManager的对象了.
		return new MemoryConstrainedCacheManager();
	}

	/**
	 * 授权操作的配置，
	 * 配置advisor对象,shiro框架底层会通过此对象的matchs方法返回值(类似切入点)决定是否创建代理对象,进行权限控制。
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor =
				new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}

	/**
	 * 配置SecurityManager(注意包名)，此对象用户实现用户身份认证和授权等功能。
	 * @Bean注解一般要结合@Configuration注解使用，用于描述方法，表示这个方法的
	 * 返回值要交给spring管理，key默认为方法名或者直接由@Bean注解指定
	 * @return
	 */
	@Bean
	public SecurityManager securityManager(Realm realm,
										   CacheManager shirocacheManager,
										   RememberMeManager rememberMe,
										   SessionManager sessionManager) {
		DefaultWebSecurityManager sManager = new DefaultWebSecurityManager();
		sManager.setRealm(realm);
		sManager.setCacheManager(shirocacheManager);
		sManager.setRememberMeManager(rememberMe);
		sManager.setSessionManager(sessionManager);
		return sManager;
	}

	@Bean
	public ShiroFilterFactoryBean shiroFilterFactory(SecurityManager securityManager) {
		//1.创建ShiroFilterFactoryBean对象
		//1.1 构建对象
		ShiroFilterFactoryBean fBean=new ShiroFilterFactoryBean();
		//1.2设置安全认证授权对象
		fBean.setSecurityManager(securityManager);
		//1.3设置登陆页面
		fBean.setLoginUrl("/doLogin");
		//2.设置过滤规则
		LinkedHashMap<String,String> map= new LinkedHashMap<>();
		//静态资源允许匿名访问(例如项目static目录下的资源):"anon"表示匿名
		map.put("/bower_components/**","anon");
		map.put("/modules/**","anon");
		map.put("/dist/**","anon");
		map.put("/plugins/**","anon");
		map.put("/user/doLogin","anon");
		map.put("/doLogout","logout");
		//除了匿名访问的资源,其它都要认证("authc")后访问
		map.put("/**","user");
		fBean.setFilterChainDefinitionMap(map);
		return fBean;
	}
}
