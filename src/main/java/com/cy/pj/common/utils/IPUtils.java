package com.cy.pj.common.utils;

import java.lang.reflect.AnnotatedType;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cy.pj.sys.entity.SysLog;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IPUtils {
	public static String getIpAddr() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String ip = null;
		try {
			ip = request.getHeader("x-forwarded-for");
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (StringUtils.isEmpty(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		} catch (Exception e) {
			log.error("IPUtils ERROR ", e);
		}
		return ip;
	}


	public static void main(String[] args) {
		SysLog sysLog = new SysLog();
		Class<? extends SysLog> aClass = sysLog.getClass();
		System.out.println(aClass);
		AnnotatedType[] annotatedInterfaces = aClass.getAnnotatedInterfaces();
		System.out.println(annotatedInterfaces.toString());
		AnnotatedType annotatedSuperclass = aClass.getAnnotatedSuperclass();
		System.out.println(annotatedSuperclass);
	}
}
