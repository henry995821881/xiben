<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>

<script type="text/javascript" src="/static/jquery/jquery-1.10.2.min.js"></script>
<link href="/static/bootstrap-3.3.7-dist/css/bootstrap.min.css"
	rel="stylesheet" />
<style type="text/css">

.container {
	margin-top: 80px;
	
	
}

.panel-body{

margin:10px;
background: #F5F5F5;
height: 430px;
}
.panel{

    border:none;
}
</style>
</head>
<body>



	<div class="container">

		<div class="row">
			<div class="inmdx_loginwindow col-md-4  col-md-offset-6 ">
				<div class="panel panel-default">
					<div  class="panel-body">
					<div class="row">
					<br/>
					<div class="col-md-7 col-md-offset-3">
					<font  size="4">  登录会员账号中心</font>
					    </div>
					</div>
				     <br/>
				     <br/>
					<div class="row">
					
					
					<br/>
					
		               <div class="row" style="padding: 10px 20px 20px;">
		               
		            
                        
                        
                        <div class="form-group">
                        <div class="input-group  input-group-md col-md-10  col-md-offset-1">
                            <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                            <input type="username" class="form-control" name="username" placeholder="登录名/手机" required autofocus>
                        </div>
                        <br/>
                         <div class="input-group input-group-md col-md-10 col-md-offset-1">
                            <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
                            <input type="password" class="form-control" name="password" placeholder="密码" required autofocus>
                        </div>
                        <br/>
                       
                        
                        
                        <div class="input-group col-md-10 col-md-offset-2">
                           <button type="submit" class="btn btn-primary btn-lg col-md-10">登录</button>
                        </div>
                         
                         <div class="input-group input-group-md col-md-3 col-md-offset-6">
                           
                        <a href="#">手机登录</a>
                        </div>
                      </div>
	
	            
	            
	            
	    
		               
		               </div>
		               
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>