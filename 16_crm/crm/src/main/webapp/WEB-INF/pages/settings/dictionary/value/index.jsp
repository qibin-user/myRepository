<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(function () {
			//给"创建"按钮添加单击事件
			$("#createDicValueBtn").click(function () {
				//发送同步请求
				window.location.href="settings/dictionary/value/toSave.do";
			});

            //实现全选和取消全选(作业)
			$("#mainCheckbox").on("click",function () {
				$("input[name='subCheckbox']").prop("checked",this.checked);
			});

			$("input[name='subCheckbox']").click(function () {
				if ($("input[name='subCheckbox']:checked").size() == $("input[name='subCheckbox']").size()) {
					$("#mainCheckbox").prop("checked",true);
				}else {
					$("#mainCheckbox").prop("checked",false);
				}
			});



			//给"编辑"按钮添加单击事件
            $("#editDicValueBtn").click(function () {
            	//获取checked的数量
                var $subCheckbox = $("input[name='subCheckbox']:checked");
                var size = $subCheckbox.size();

				//判断个数
                if (size == 0 ){
                	alert("请选择一项编辑");
				} else if (size == 1){
					// 可以编辑
					// 跳转到编辑界面
					var id = $subCheckbox.val();
					window.location.href = "settings/dictionary/value/toEdit.do?id="+id;
				}else {
                	//不可编辑
					alert("只能选择一项编辑");
				}
            });

            //给“删除”按钮添加单击事件
			$("#deleteDicValueBtn").click(function () {

				var $subChecked = $("input[name='subCheckbox']:checked");
				var size = $subChecked.size();

				var array = new Array();

				if (size == 0){
					alert("至少选择一个");
				} else{

					$.each($subChecked,function (i, item) {
						array[i] = item.value;
					});


					$.ajax({
						url:"settings/dictionary/value/deleteBatchValue.do",
						data:{
							ids:array
						},
						type:"post",
						dataType:"json",
						// traditional:true,
						success:function (returnObject) {

							if (returnObject.condition == 1) {
								alert("删除成功");
								window.location.href = "settings/dictionary/value/index.do";
							}else {
								alert("删除失败");
								window.location.href = "settings/dictionary/value/index.do";
							}

						}
					});

				}


			})








		});
	</script>
</head>
<body>

	<div>
		<div style="position: relative; left: 30px; top: -10px;">
			<div class="page-header">
				<h3>字典值列表</h3>
			</div>
		</div>
	</div>
	<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;left: 30px;">
		<div class="btn-group" style="position: relative; top: 18%;">
		  <button id="createDicValueBtn" type="button" class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span> 创建</button>
		  <button id="editDicValueBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-edit"></span> 编辑</button>
		  <button id="deleteDicValueBtn" type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
		</div>
	</div>
	<div style="position: relative; left: 30px; top: 20px;">
		<table class="table table-hover">
			<thead>
				<tr style="color: #B3B3B3;">
					<td><input id="mainCheckbox" type="checkbox" /></td>
					<td>序号</td>
					<td>字典值</td>
					<td>文本</td>
					<td>排序号</td>
					<td>字典类型编码</td>
				</tr>
			</thead>
			<tbody id="tBody">
				<c:forEach items="${dicValueList}" var="dv" varStatus="vs">
					<c:if test="${vs.count%2==0}">
						<tr class="active">
					</c:if>
					<c:if test="${vs.count%2!=0}">
						<tr>
					</c:if>
						<td><input name="subCheckbox" type="checkbox" value="${dv.id}"/></td>
						<td>${vs.count}</td>
						<td>${dv.value}</td>
						<td>${dv.text}</td>
						<td>${dv.orderNo}</td>
						<td>${dv.typeCode}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
</body>
</html>