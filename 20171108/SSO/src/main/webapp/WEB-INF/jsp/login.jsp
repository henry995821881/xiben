<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="/static/jquery/jquery-1.10.2.min.js"></script>
<link
	href="/static/bootstrap-3.3.7-dist/css/bootstrap.min.css"
	rel="stylesheet" />

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>SSO-login</title>

<script type="text/javascript">




	$(function() {
		
		
		$("#imgObj").on("click",function(){
			$(this).attr("src","/sso/validateCode?aaa="+Math.random());
		});

		$("#img_a").on("click",function(){
			
			$("#imgObj").attr("src","/sso/validateCode?aaa="+Math.random());
		});
		
	});
</script>

<style type="text/css">

/* body {
    background:url(../img/login_bg_0.jpg) #f8f6e9;
} */
.mycenter{
    margin-top: 100px;
    margin-left: auto;
    margin-right: auto;
    height: 350px;
    width:500px;
    padding: 5%;
    padding-left: 5%;
    padding-right: 5%;
}
.mycenter mysign{
    width: 440px;
}
.mycenter input,checkbox,button{
    margin-top:2%;
    margin-left: 10%;
    margin-right: 10%;
}
.mycheckbox{
    margin-top:10px;
    margin-left: 40px;
    margin-bottom: 10px;
    height: 10px;
}

</style>
</head>
<body>


 <form id="form1" action="/sso/checklogin" method="post" >

            <div class="mycenter">
            <div class="mysign">
                <div class="col-lg-11 text-center text-info">
                    <h2>SSO 登录</h2>
                </div>
                 <div class="col-lg-10">
                 <c:out value="${result.msg}"></c:out>
                   
                </div>
                <div class="col-lg-10">
                    <input type="text" class="form-control" name="username" placeholder="请输入账户名" required autofocus/>
                </div>
                <div class="col-lg-10"></div>
                <div class="col-lg-10">
                    <input type="password" class="form-control" name="password" placeholder="请输入密码" required autofocus/>
                </div>
                <!-- <div class="col-lg-10"></div>
                <div class="col-lg-10 mycheckbox checkbox">
                    <input type="checkbox" class="col-lg-1">记住密码</input>
                </div> -->
                <div class="col-lg-10"></div>
                <div class="  col-lg-10">  
                    <input type="text" id="code" name="validatecode" placeholder="请输入验证码" class="form-control" required autofocus/>  
               </div>
               <div class="  col-lg-10">
                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img id="imgObj" alt="验证码" src="/sso/validateCode" />  
                     &nbsp;&nbsp;&nbsp; <a href="#" id="img_a" >换一张</a>  
               </div>
               
               
                <div class="col-lg-10">
                    <button type="submit" class="btn btn-success col-lg-12">登录</button>
                </div>
            </div>
        </div>
        </form>

</body>

</html>