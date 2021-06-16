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

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(function () {
            //给"创建"按钮添加单击事件
            $("#createDicTypeBtn").click(function () {
                //发送请求
                window.location.href = "settings/dictionary/type/toSave.do";
            });

            //实现全选，反选
            $("#chkedAll").click(function () {
                // alert("a")
                $("input[name='sonCheck']").prop("checked", this.checked);
            });

            //全选反选细节实现
            $("input[name='sonCheck']").click(function () {
                if ($("input[name='sonCheck']").size() == $("input[name='sonCheck']:checked").size()) {

                    $("#chkedAll").prop("checked", true);
                } else {

                    $("#chkedAll").prop("checked", false);
                }

            });

            //给"编辑"按钮添加单击事件
            $("#editDicTypeBtn").click(function () {
                // 有两种情况 1，客户填选checked 2，客户没填选checked
                var checked = $("input[name='sonCheck']:checked");
                var checkedSize = checked.size();

                if (checkedSize == 1) {
                    //   1，客户填选 1个 checked

                    var value = checked[0].value;
                    window.location.href = "settings/dictionary/type/redactDicType.do?code=" + value;
                } else if (checkedSize == 0) {
                    //2，客户 没填选checked
                    alert("请选择一个想编辑的字典类型");
                } else {
                    //3，客户 填选多个checked
                    alert("只能编辑一个内容");
                }
            });

            //给"删除"按钮添加单击事件
            $("#deleteDicTypeBtn").click(function () {


                //得到checked 数量判断是否选中
                var $sonCheck = $("input[name = 'sonCheck']:checked");

                if ($sonCheck.size() > 0){
                    //选中大于0个
                    var coeds = new Array();
                    // 把值取出存放到数组中
                    $.each($sonCheck ,function (i,item) {
                        coeds[i] = item.value;
                    });

                    $.ajax({
                        url:"settings/dictionary/type/deleteBatchType.do",
                        data:{
                            coeds:coeds
                        },
                        type:"post",
                        dataType:"json",
                        // traditional:true,
                        success:function (returnObject) {

                            if (returnObject.condition == 1) {
                                alert("删除成功");
                                window.location.href = "settings/dictionary/type/index.do";
                            }else {
                                alert("删除失败");
                                window.location.href = "settings/dictionary/type/index.do";
                            }

                        }
                    });
                }else {
                    alert("至少选择一个删除");
                }
            });
        });
    </script>
</head>
<body>

<div>
    <div style="position: relative; left: 30px; top: -10px;">
        <div class="page-header">
            <h3>字典类型列表</h3>
        </div>
    </div>
</div>
<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;left: 30px;">
    <div class="btn-group" style="position: relative; top: 18%;">
        <button id="createDicTypeBtn" type="button" class="btn btn-primary"><span
                class="glyphicon glyphicon-plus"></span> 创建
        </button>
        <button id="editDicTypeBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-edit"></span>
            编辑
        </button>
        <button id="deleteDicTypeBtn" type="button" class="btn btn-danger"><span
                class="glyphicon glyphicon-minus"></span> 删除
        </button>
    </div>
</div>
<div style="position: relative; left: 30px; top: 20px;">
    <table class="table table-hover">
        <thead>
        <tr style="color: #B3B3B3;">
            <td><input type="checkbox" id="chkedAll"/></td>
            <td>序号</td>
            <td>编码</td>
            <td>名称</td>
            <td>描述</td>
        </tr>
        </thead>
        <tbody id="tBody">
        <!--从request获取dicTypeList，遍历dicTypeList，显示所有的数据-->
        <c:forEach items="${dicTypeList}" var="dt" varStatus="vs">
            <c:if test="${vs.count%2==0}">
                <tr class="active">
                    <td><input name="sonCheck" type="checkbox" value="${dt.code}"/></td>
                    <td>${vs.count}</td>
                    <td>${dt.code}</td>
                    <td>${dt.name}</td>
                    <td>${dt.description}</td>
                </tr>
            </c:if>
            <c:if test="${vs.count%2!=0}">
                <tr>
                    <td><input name="sonCheck" type="checkbox" value="${dt.code}"/></td>
                    <td>${vs.count}</td>
                    <td>${dt.code}</td>
                    <td>${dt.name}</td>
                    <td>${dt.description}</td>
                </tr>
            </c:if>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>