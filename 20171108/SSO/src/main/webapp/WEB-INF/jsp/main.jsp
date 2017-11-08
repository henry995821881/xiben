<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SSO-main</title>
<script type="text/javascript" src="/static/jquery/jquery-1.10.2.min.js"></script>

<!-- 新 Bootstrap 核心 CSS 文件 -->
<link
	href="/static/bootstrap-3.3.7-dist/css/bootstrap.min.css"
	rel="stylesheet" />

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script
	src="/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>
<body>
<a href="/sso/logout">退出</a>
<div class="container">

<table class="table table-striped">
	<caption>用户基本信息</caption>
   <thead>
      <tr>
         <th></th>
         <th></th>
      </tr>
   </thead>
   <tbody>
   
   
      <tr>
       <td>nickname</td>
        <td> <c:out value="${loginUser.nickname} " escapeXml="true" default=""></c:out></td>
       </tr> 
       
       
       <tr>
       <td>username</td>
        <td> <c:out value="${loginUser.username} " escapeXml="true" default=""></c:out></td>
       </tr>
            
        <tr>
       <td>phone</td>
        <td> <c:out value="${loginUser.phone} " escapeXml="true" default=""></c:out></td>
       </tr>  
       
       <tr>
       <td>email</td>
        <td> <c:out value="${loginUser.email} " escapeXml="true" default=""></c:out></td>
       </tr> 
       
        
       
      
   </tbody>
</table>
</div>


</body>
</html>