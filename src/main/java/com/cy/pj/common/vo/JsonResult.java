package com.cy.pj.common.vo;

import java.io.Serializable;


import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class JsonResult implements Serializable {

	private static final long serialVersionUID = 2080943344863436345L;

	/**状态码*/
	private int state=1;//1表示SUCCESS,0表示ERROR
	/**状态信息*/
	private String message="ok";
	/**正确数据*/
	private Object data;
//	private PageObject<SysLog> data;
	
	//public JsonResult() {}
	
	public JsonResult(String message){
		this.message=message;
	}
	/**一般查询时调用，封装查询结果*/
	public JsonResult(Object data) {
//	public JsonResult(PageObject<SysLog> data) {
		super();
		this.data = data;
	}
	/**
	 * 出现异常时时调用
	 * 当出现异常时，封装异常信息
	 * @param t
	 * Throwable t为异常
	 */
	public JsonResult(Throwable t) {
		this.state = 0;
		this.message = t.getMessage();
	}

	
}
