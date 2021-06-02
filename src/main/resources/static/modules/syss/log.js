//JS 问题如何解决? console.log(),debugger,排除法
	$(function(){//页面加载完成后执行
		$("#pageId").load("doPageUI",function(){
			//console.log(result)
			doGetObjects();//获取页面记录的对象s
		});
		$(".input-group-btn").on("click",".btn-search",doQueryObjects);
		$(".input-group-btn").on("click",".btn-delete",doDeleteObjects);
		
		$("#checkAll").click(doChangeTBodyCheckBoxState);
		$("#tbodyId").on("click",".cBox",doChangeTHeadCheckBoxState)
	})
   
   function doRefreshAfterDeleteOK(){
	   let pageCurrent=$("#pageId").data("pageCurrent");
	   let pageCount=$("#pageId").data("pageCount");
	   let checked=$("#checkAll").prop("checked");
	   if(pageCurrent>1&&pageCount==pageCurrent&&checked){
		   pageCurrent--;
		   $("#pageId").data("pageCurrent",pageCurrent);
	   }
	   doGetObjects();
   }
   
   function doChangeTHeadCheckBoxState(){
		let flag = true;
		$("#tbodyId input:checkbox").each(function(){
			//alert(flag);
			flag=$(this).prop("checked")&&flag;
		});
		//alert(flag);
	   
		$("#checkAll").prop("checked",flag);
   }
   
   
   function doChangeTBodyCheckBoxState(){
		//1.获取当前点击对象的checked属性的值
	   let flag = $(this).prop("checked");
	   //alert(flag);
	   //2.将tbody中所有checkbox元素的值都修改为flag对应的值。
	   //第一种方案
	   $("#tbodyId input:checkbox").each(function(){
		   $(this).prop("checked",flag);
		   //alert(flag);
	   })
	   //第二种方案
	   //$("#tbodyId input:checkbox").prop("checked",flag);
	  /*  $("#tbodyId input[type='checkbox']")
	   .prop("checked",flag); */
   }
   
   
   function doGetCheckedIds(){
	   let IdArray=[];
	   $("#tbodyId input:checkbox:checked").each(function(){
		   IdArray.push($(this).val());
	   });
	   return IdArray;
   }
   
   
   function doDeleteObjects(){
		var ids=doGetCheckedIds();
		if( ids.length == 0 ){
			alert("请选择要删除的数据！")
			return;
		}
		if(!confirm("确定删除？")) return;
		var url="log/doDeleteObjects"
		let params={"ids":ids.toString()} ;
		$.post(url,params,function(result){
			if(result.state==1){
				alert(result.message);
				doRefreshAfterDeleteOK();
				//doGetObjects();
			}else{
				alert(result.message);
			}
		})
   }
   
   function doQueryObjects(){
	   	//alert("触发了")
		//为什么要在此位置初始化pageCurrent的值为1?
		//数据查询时页码的初始位置也应该是第一页
		$("#pageId").data("pageCurrent",1);
		//为什么要调用doGetObjects函数？
		//重用js代码，简化jS代码编写。
		doGetObjects();
   }
   
   function doGetObjects(){
	    $("#checkAll").prop("checked",false);//查询前将全选设置为不选中，因为这样可以在全选后下一页等的操作可以避免全选还是勾选的情况
	    const url="log/doFindPageObjects";
	    var pageCurrent = $("#pageId").data("pageCurrent")
	    if(!pageCurrent) pageCurrent=1;
	    let parmas={"pageCurrent":pageCurrent};
	    var username = $("#searchNameId").val();
	    if(username) parmas.username=username;
	    //var parmas={"pageCurrent":1};
	    //var parmas = "pageCurrent=2";
	    //console.log(parmas);
	    $.getJSON(url, parmas, function(result){//ajax的函数gerJSON（）
	    	doHandleQueryResponseResult(result);//回调函数名称可以自定义
	    });
   }
   
   function doHandleQueryResponseResult(result){
	   //console.log("resulmjhmhjmhjmt",result);
	   if( result.state == 1 ){
			//console.log("resulmjhmhjmhjmt",result.data.records);
			doSetTableBodyRows(result.data.records);
			doSetPagination(result.data)
	   }else{
		   //alert(result.message);
			doSetQueryErrors(result.message);
	   }
   }
   
   function doSetQueryErrors(message){
	   $("#tbodyId").html(`<tr><td colspan="7">${message}</td><tr>`)
   }
   
   function doSetTableBodyRows(records){
	   var tBody=$("#tbodyId");
	   tBody.empty();
	 /*   for( var i in records){
		   //var tr = $("<tr></tr>");//在下面的函数中，增加了tr标签，所以这里不用
		   var tr=doCreateTds(records[i]);
		   //tr.append(tds);//在下面的函数中，增加了tr标签，所以这里不用
		   tBody.append(tr);
	   }  */
	  
	   records.forEach(record=>tBody.append(doCreateTds(record)));
   }
   
   function doCreateTds(data){
	   return `<tr>
	   <td><input type='checkbox' class='cBox' name='cItem' value=${data.id}></td>
	     <td>${data.username}</td>
	     <td>${data.operation}</td>
	     <td>${data.method}</td>
	     <td>${data.params}</td>
	     <td>${data.ip}</td>
	     <td>${data.time}</td>	
	     </tr>
	   `
   }
   