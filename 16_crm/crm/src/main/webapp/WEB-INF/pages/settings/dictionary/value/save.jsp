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

			$("#create-dicValue").blur(function () {
				fun1();
			});

			$("#saveCreateDicValueBtn").click(function () {
				var dicValue = $("#create-dicValue").val();

				if (fun1()) {
					var dicTypeCode = $("#create-dicTypeCode").val();
					var text = $("#create-text").val();
					var orderNo = $("#create-orderNo").val();

					//ture证明可以添加
					$.ajax({
						url:"settings/dictionary/value/insertDicValue.do",
						type:"post",
						data:{
							typeCode:dicTypeCode,
							value:dicValue,
							text:text,
							orderNo:orderNo
						},
						dataType:"json",
						success:function (returnObject) {
							if (returnObject.condition == "1"){
								alert("添加成功");
								window.location.href = "settings/dictionary/value/index.do";
							} else {
								alert("添加失败");
							}
						}
					});

				}else {
					//不可添加
					if (dicValue ==""){

						alert("字典值不能为空");
					}else {
						alert("字典值不能重复");
					}
				}
			});


			function fun1() {

				var sub = false;

				var $dicValue = $("#create-dicValue");
				var dicValue = $dicValue.val();

				if (dicValue == ""){
					$("#codeMsg").text("字典值不能为空");
					return sub;
				}

				$.ajax({
					url:"settings/dictionary/value/selectRepetition.do",
					type:"post",
					data:{
						dicValue:dicValue
					},
					dataType:"json",
					async:false,
					success:function (returnObject) {
						if (returnObject.condition == "1"){
							//没有重复
							sub = true;
							$("#codeMsg").text("");
						}else {
							// 重复
							$("#codeMsg").text("字典值不能重复");
							sub = false;
						}
					}

				});
				return sub;
			}

		});
	</script>
</head>
<body>

	<div style="position:  relative; left: 30px;">
		<h3>新增字典值</h3>
	  	<div style="position: relative; top: -40px; left: 70%;">
			<button id="saveCreateDicValueBtn" type="button" class="btn btn-primary">保存</button>
			<button type="button" class="btn btn-default" onclick="window.history.back();">取消</button>
		</div>
		<hr style="position: relative; top: -40px;">
	</div>
	<form class="form-horizontal" role="form">
					
		<div class="form-group">
			<label for="create-dicTypeCode" class="col-sm-2 control-label">字典类型编码<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-dicTypeCode" style="width: 200%;">
				  <!--遍历dicTypeList，显示select的所有选项-->
					<c:forEach items="${dicTypeList}" var="dt">
						<option value="${dt.code}">${dt.name}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-dicValue" class="col-sm-2 control-label">字典值<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-dicValue" style="width: 200%;">
				<span id="codeMsg" style="color: red"></span>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-text" class="col-sm-2 control-label">文本</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-text" style="width: 200%;">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-orderNo" class="col-sm-2 control-label">排序号</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-orderNo" style="width: 200%;">
			</div>
		</div>
	</form>
	
	<div style="height: 200px;"></div>
</body>
</html>