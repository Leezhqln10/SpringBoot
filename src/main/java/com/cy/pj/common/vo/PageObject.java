package com.cy.pj.common.vo;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO(VO/BO/DTO/DO)中的BO对象：
 * 
   1)所有的POJO对象属性定义都用对象类型
   2)所有的POJO对象属性默认值无需指定
   3)所有的POJO对象需要提供set/get/ toString 方法
   4)所有的POJO对象都需要提供无参构造函数
   5)所有的POJO对象的构造方法不要写任何业务逻辑。
 
 * 此对象为业务层向外输出的一个BO对象，用于封装业务执行的结果
 * 泛型：类上定义的泛型用于约束类中属性，方法参数，方法返回值类型。
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageObject<T> implements Serializable {
	
	private static final long serialVersionUID = 4382188489836324755L;
	
	/**当前页的页码值*/
	private Integer pageCurrent=1;
	/**页面大小*/
	private Integer pageSize=3;
	/**总行数(通过查询获得)*/
	private Integer rowCount=0;
	/**总页数(通过计算获得)*/
	private Integer pageCount=0;
	/**当前页记录*/
	private List<T> records;
	
	//public PageObject(){}//@NoArgsConstructor无参数构造的注解

	public PageObject(Integer pageCurrent, Integer pageSize, Integer rowCount, List<T> records) {
		super();
		this.pageCurrent = pageCurrent;
		this.pageSize = pageSize;
		this.rowCount = rowCount;
		this.records = records;
		this.pageCount = (rowCount-1)/pageSize+1;
//		this.pageCount = rowCount/pageSize;
//		if( rowCount % pageSize !=0 ) pageCount++;
	}
}
