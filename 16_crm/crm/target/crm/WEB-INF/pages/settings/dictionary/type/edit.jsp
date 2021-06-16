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
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(function () {



			//给"更新"按钮添加单击事件

			var name1 = $("#edit-name").val();
			var description1 = $("#edit-description").val();

			$("#saveEditDicTypeBtn").click(function () {
				//获取参数
				var newCode = $("#edit-code").val();
				var newName = $("#edit-name").val();
				var newDescription = $("#edit-description").val();

				//没有修改的
				if (newName == name1 && newDescription == description1) {
					alert("您还没有修改那，不想修改请点返回");
					return;
				}

				//修改过的
				$.ajax({
					url:"settings/dictionary/type/updataDicType.do",
					type:"post",
					data:{
						code:newCode,
						name:newName,
						description:newDescription
					},
					dataType:"json",
					success:function (returnObject) {
						if (returnObject.condition == "1") {
							alert("更新成功");
							window.location.href = "settings/dictionary/type/index.do";
							//直接通过window.location.href 访问jsp 会找不到 因为jsp在 WEB-INF界面，只能通过访问 .do 跳转到 jsp 界面
						} else {
							alert("修改失败");
							window.location.href = "settings/dictionary/type/index.do";
						}
					}
				});





			});


		});
	</script>
</head>
<body>

	<div style="position:  relative; left: 30px;">
		<h3>修改字典类型</h3>
	  	<div style="position: relative; top: -40px; left: 70%;">
			<button id="saveEditDicTypeBtn" type="button" class="btn btn-primary">更新</button>
			<button type="button" class="btn btn-default" onclick="window.history.back();">取消</button>
		</div>
		<hr style="position: relative; top: -40px;">
	</div>
	<form class="form-horizontal" role="form">
					
		<div class="form-group">
			<label for="edit-code" class="col-sm-2 control-label">编码<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="edit-code" style="width: 200%;" value="${dicType.code}" disabled>
			</div>
		</div>
		
		<div class="form-group">
			<label for="edit-name" class="col-sm-2 control-label">名称</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="edit-name" style="width: 200%;" value="${dicType.name}">
			</div>
		</div>
		
		<div class="form-group">
			<label for="edit-description" class="col-sm-2 control-label">描述</label>
			<div class="col-sm-10" style="width: 300px;">
				<textarea class="form-control" rows="3" id="edit-description" style="width: 200%;">${dicType.description}</textarea>
			</div>
		</div>
	</form>
	
	<div style="height: 200px;"></div>
</body>
</html>