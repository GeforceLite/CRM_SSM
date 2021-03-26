<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>mytitle</title>
</head>
<body>
//创建时间从当前系统时间获取
String createTime = request.getParameter(DateTimeUtil.getSysTime());
//创建人也从当前session域中获取
String createBy = request.getParameter(((User)request.getSession().getAttribute("user")).getName());
        $.ajax({

            url : "",
            data : {

            },
            type : "",
            dataType : "json",
            success : function (data) {



            }

        })

        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();

        $(".time").datetimepicker({
            minView: "month",
            language:  'zh-CN',
            format: 'yyyy-mm-dd',
            autoclose: true,
            todayBtn: true,
            pickerPosition: "bottom-left"
        });
</body>
</html>
