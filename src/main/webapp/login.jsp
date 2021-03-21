<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<script>
		$(function () {
			//加载完毕后清空文本框内容
			$("#loginAct").val("");
			//在页面加载完毕后，让用户文本框自动获得焦点
			$("#loginAct").focus();
			//为登录按钮绑定事件，执行登陆操作
			$("#submitBtn").click(function () {
				/*alert("执行验证登陆操作");*/
				login();
			});
			$(window).keydown(function (event){
				/*绑定事件敲击键盘也能提交操作*/
				//event是取得敲的是哪个键
				if (event.keyCode==13){
					//回车的码是13，判断登陆
					login();
				}
			})
		})
		//封装好登陆函数,写在外面作用域更广，一定写在$function () 的外面
		function login(){
			//验证账户密码不能为空
			//将文本中的左右空格去掉，使用$.trim(代码)方法
			var loginAct = $.trim($("#loginAct").val());
			var loginPwd = $.trim($("#loginPwd").val());
			if (loginAct=="" || loginPwd=="") {
				/*为什么用html，就是因为要在msg这个标签里面放内容了，所以要用html*/
				$("#msg").html("账号密码不能为空");
				//如果账号密码为空，则需要及时强制终止该方法
				return false;
			}
		}

	</script>
</head>
<body>
	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2017&nbsp;动力节点</span></div>
	</div>
	
	<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>登录</h1>
			</div>
			<form action="workbench/index.html" class="form-horizontal" role="form">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input class="form-control" type="text" placeholder="用户名" id="loginAct">
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input class="form-control" type="password" placeholder="密码" id="loginPwd">
					</div>
					<div class="checkbox"  style="position: relative;top: 30px; left: 10px;">
						<%--//提示消息位置--%>
							<span id="msg" style="color: red"></span>
						
					</div><%--在form标签中，按钮默认操作就是提交表单，

					注意！！！submit就不走js代码了，就直接走from提交了！！！一定要改button！！！

					提交按钮一定是button而不是submit，触发后函数由js进行决定--%>
					<button type="button" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;" id="submitBtn">登录</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>