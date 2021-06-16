<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">

    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css"
          rel="stylesheet"/>

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#create-code").blur(function () {
                fun1();
            });

            $("#saveCreateDicTypeBtn").click(function () {

                //判断 能否保存
                if (fun1()) {
                    var code = $("#create-code").val();
                    var name = $("#create-name").val();
                    var description = $("#create-description").val();

                    $.ajax({
                        url: "settings/dictionary/type/insertType.do",
                        type: "get",
                        data: {
                            code: code,
                            name: name,
                            description: description
                        },
                        dataType: "json",
                        success: function (returnObject) {
                            if (returnObject.condition == "1") {
                                alert("添加成功");
                                window.location.href = "settings/dictionary/type/index.do";
                            } else {
                                alert("添加失败");
                            }
                        }
                    });
                } else {
                    var code = $("#create-code").val();
                    if (code == "") {
                        alert("编码不能为空");
                        return;
                    }
                    alert("编码重复");
                }

            });
        });


        function fun1() {
            //定义一个标记来判断 页面能否保存
            var ret = false;

            //code 不能为空判断
            var code = $("#create-code").val();
            if (code == "") {
                $("#codeMsg").text("编码不能为空");
                //返回false 表示不能提交
                //返回true 表示能提交
                return ret;
            }


            //通过调用controller判断code是否重复
            $.ajax({
                url: "settings/dictionary/type/estimateCode.do",
                type: "post",
                data: {
                    code: code
                },
                dataType: "json",
                async: false, // 不能写字符串
                success: function (returnObject) {
                    //success return 结束的仅仅只是 自己的异步方法
                    if (returnObject.condition == "0") {
                        // 不可以使用此编码
                        $("#codeMsg").text("编码重复");
                        ret = false;
                    } else {
                        // == 1 证明可以使用此编码
                        $("#codeMsg").text("");
                        ret = true;
                    }
                }
            });
            return ret;
        }

    </script>
</head>
<body>

<div style="position:  relative; left: 30px;">
    <h3>新增字典类型</h3>
    <div style="position: relative; top: -40px; left: 70%;">
        <button id="saveCreateDicTypeBtn" type="button" class="btn btn-primary">保存</button>
        <button type="button" class="btn btn-default" onclick="window.history.back();">取消</button>
    </div>
    <hr style="position: relative; top: -40px;">
</div>
<form class="form-horizontal" role="form">

    <div class="form-group">
        <label for="create-code" class="col-sm-2 control-label">编码<span
                style="font-size: 15px; color: red;">*</span></label>
        <div class="col-sm-10" style="width: 300px;">
            <input type="text" class="form-control" id="create-code" style="width: 200%;">
            <span id="codeMsg" style="color: red"></span>
        </div>
    </div>

    <div class="form-group">
        <label for="create-name" class="col-sm-2 control-label">名称</label>
        <div class="col-sm-10" style="width: 300px;">
            <input type="text" class="form-control" id="create-name" style="width: 200%;">
        </div>
    </div>

    <div class="form-group">
        <label for="create-description" class="col-sm-2 control-label">描述</label>
        <div class="col-sm-10" style="width: 300px;">
            <textarea class="form-control" rows="3" id="create-description" style="width: 200%;"></textarea>
        </div>
    </div>
</form>

<div style="height: 200px;"></div>
</body>
</html>