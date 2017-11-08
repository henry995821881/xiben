<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="/static/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/static/js/jquery.signalR-2.2.1.js"></script>
<script type="text/javascript" src="/static/js/hubs.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
<style type="text/css">
        .watermark {
            color: #999 !important;
        }

        .login-box {
            width: 300px;
            padding: 25px 25px 23px;
            color: #6c6c6c;
            background: #fff;
            position: relative;
            margin: 0 auto;
        }

            .login-box a {
                color: #6c6c6c;
            }

                .login-box a.light-link {
                    color: #f40;
                }

                .login-box a:hover {
                    color: #f40;
                    text-decoration: none;
                }

            .login-box.loading {
                height: 295px;
                background: #fff url(https://img.alicdn.com/tps/TB1R5zYKVXXXXb7XVXXXXXXXXXX-32-32.gif) no-repeat 50% 50%;
            }

                .login-box.loading .bd, .login-box.loading .hd {
                    display: none;
                }

        .module-quick .static-form, .module-quick .ww-login, .module-static .quick-form {
            display: none !important;
        }

        .module-quick .quick-form, .module-static .static-form {
            display: block;
        }

        .module-quick .login-tip {
            display: none;
        }

        .login-title {
            width: 245px;
            margin: 25px 0 10px 37px;
            padding-bottom: 2px;
            font-size: 18px;
            font-weight: bold;
            float: left;
            text-align: center;
        }

        .login-switch {
            width: 52px;
            height: 52px;
            line-height: 52px;
            position: absolute;
            right: 5px;
            top: 8px;
            -moz-user-select: none;
        }

            .login-switch .iconfont {
                font-size: 52px;
                cursor: pointer;
                display: none;
                color: #f40;
            }

                .login-switch .iconfont:hover {
                    color: #f52b00;
                }

        .module-quick .login-switch .static, .module-static .login-switch .quick {
            display: block;
        }

        .login-tip {
            position: absolute;
            top: 5px;
            right: 58px;
        }

        .ft-gray {
            color: #9c9c9c;
        }

        .poptip {
            border: 1px solid #f3d995;
            height: 16px;
            line-height: 16px;
            padding: 5px 20px 5px 15px;
            background: #fefcee;
            position: relative;
        }

            .poptip .poptip-content {
                color: #df9c1f;
                font-size: 12px;
                font-weight: 400;
            }

                .poptip .poptip-content .iconfont {
                    color: #df9c1f;
                    font-size: 14px;
                    margin-right: 8px;
                }

        .poptip-arrow {
            position: absolute;
            z-index: 10;
            *zoom: 1;
        }

            .poptip-arrow em, .poptip-arrow span {
                position: absolute;
                *zoom: 1;
                width: 0;
                height: 0;
                border-color: rgba(255,255,255,0);
                border-color: transparent;
                *border-color: transparent;
                _border-color: tomato;
                _filter: chroma(color=tomato);
                border-style: solid;
                overflow: hidden;
                top: 0;
                left: 0;
            }

        .poptip-arrow {
            top: 8px;
            right: 0;
        }

            .poptip-arrow em {
                top: 0;
                left: 1px;
                border-left-color: #f3d995;
                border-width: 6px 0 6px 6px;
            }

            .poptip-arrow span {
                border-left-color: #fefcee;
                border-width: 6px 0 6px 6px;
            }

        .qrcode-desc {
            width: 200px;
            margin: 180px 0 10px 40px;
            float: left;
        }

            .qrcode-desc .iconfont {
                float: left;
                color: #f40;
                font-size: 34px;
                line-height: 33px;
                margin-right: 10px;
                margin-top: 13px;
            }

            .qrcode-desc p {
                width: 144px;
                line-height: 18px;
                color: #6c6c6c;
                font-size: 12px;
                float: left;
            }


        .qrcode-main {
            width: 245px;
            margin: 80px 0 10px 60px;
            position: relative;
        }

        .qrcode-sm {
            width: 200px;
            margin: 20px 0 10px 30px;
            padding-bottom: 2px;
            position: absolute;
        }

        .qrcode-img {
            position: absolute;
            top: 0;
            z-index: 100;
            width: 156px;
            height: 156px;
            background-color: white;
        }

            .qrcode-img img {
                vertical-align: top;
                margin:8px 8px 8px 8px;
            }

        .qrcode-help {
            position: absolute;
            right: 12px;
            top: -15px;
            width: 120px;
            height: 180px;
            opacity: 0;
            display: none;
            z-index: 99;
        }

        .qrcode-help-showed {
            width: 300px;
            margin: 0 auto;
        }

            .qrcode-help-showed .qrcode-help {
                display: block;
                opacity: 1;
            }

            .qrcode-help-showed .qrcode-img, .qrcode-login .qrcode-help-showed .msg-err {
                left: 13px;
            }

        .qrcode-login .msg-err, .qrcode-login .qrcode-msg, .qrcode-login-error .qrcode-msg .msg-ok {
            display: none;
        }

        .qrcode-login-error .msg-err, .qrcode-login-ok .qrcode-msg, .qrcode-login-ok .qrcode-msg .msg-ok {
            display: block;
        }

        .qrcode-msg {
            margin: 20px 0 10px 10px;
            position: relative;
        }

            .qrcode-msg h6 {
                margin-top: 22px;
                margin-bottom: 15px;
                font-size: 12px;
                line-height: 12px;
                color: #6c6c6c;
                text-align: center;
            }

            .qrcode-msg p {
                text-align: center;
                font-size: 14px;
                line-height: 14px;
                color: #3c3c3c;
            }

            .qrcode-msg .msg-icon {
                margin: 20px 0 10px 90px;
                text-align: center;
            }

            .qrcode-msg .link {
                margin: 20px 0 10px 90px;
                text-align: center;
            }

        .msg-err {
            width: 156px;
            height: 156px;
            background: rgba(255,255,255,.95);
            position: absolute;
            top: 0;
            z-index: 9999;
        }

            .msg-err h6 {
                color: #3c3c3c;
                margin-top: 38px;
                margin-bottom: 8px;
                font-size: 12px;
                text-align: center;
            }

            .msg-err .refresh {
                width: 100px;
                height: 36px;
                line-height: 36px;
                text-align: center;
                margin: 0 auto;
                background: #f40;
                display: block;
                color: #fff;
                border-radius: 3px;
            }

                .msg-err .refresh:hover {
                    background: #f52b00;
                }
    </style>

</head>

<body onload="">
    <form method="post" action="SignIn.aspx?ReturnUrl=%2f" id="form1">
<div class="aspNetHidden">
<input type="hidden" name="__RefreshPageGuid" id="__RefreshPageGuid" value="1b0a3c52-b906-436f-a4cd-f78d5932a8e1" />
<input type="hidden" name="__RefreshHiddenField" id="__RefreshHiddenField" value="031753.63462" />
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="" />
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="" />
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="/wEPDwUKLTQ4MTExNjkyNWQYAQUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgMFDGNiUmVtZW1iZXJNZQUPY2JTTVNSZW1lbWJlck1lBQpidG5XWExvZ2luMbaUxh4VejsHwsO9acLBMdffgqY=" />
</div>




<script src="http://secure.96369.net/WebResource.axd?d=Ah2CzhSSob1YAF2Ig6n4SCuPHNiyVc6owIIJr3g3B3dDXOFqiVrWDBuzsfEyXQX5I0YTGnvsso8QCWkY00XM4wvzCro1&t=635375563334701695" type="text/javascript"></script>


<script src="http://secure.96369.net/ScriptResource.axd?d=ccyQtmoecOHN-tgZPBqnJZOK0BygWGeTSYVX7pXi_ayj1QlEwjwwz4JyaPHqMeRZdd1CAPWvwpB9KbbU3LtjLkqLM-gnxz4MwrEnQfOD-SD8IdKYOtkfCsuYmnH5sd6da5ZC9BicLf3dmU35hmqASqD2HRRCbmGckFYVyNAHQZhBLo5h0&amp;t=ffffffffb53e74b8" type="text/javascript"></script>
<script type="text/javascript">
//<![CDATA[
if (typeof(Sys) === 'undefined') throw new Error('ASP.NET Ajax 客户端框架未能加载。');
//]]>
</script>

<script src="http://secure.96369.net/ScriptResource.axd?d=bsVuG0OuTj31Vdx6dAQ53w1W6wLD-zkLZTkPOy077bStIfzqJoTLbH2aeZ3gdT513X0mcuPpOvayP3_jj8FFlO7dPgGWb8rh3H5sWFwEC7GlSPZAxlKOjHz_WY4ahWHj_5u5qmGVJNJeIB_r1Bg-bV0CUBW4GykZYDNi6fIWY5nj7_461QJQFoaDoZ-7ctmEjoC5oA2&amp;t=ffffffffb53e74b8" type="text/javascript"></script>
<div class="aspNetHidden">

	<input type="hidden" name="__VIEWSTATEGENERATOR" id="__VIEWSTATEGENERATOR" value="ECDA716A" />
	<input type="hidden" name="__EVENTVALIDATION" id="__EVENTVALIDATION" value="/wEdAA+lbGwRfYSqfNprQs3WuA5RKq8oHzQL7fVA+aej2dBLxDPSlu16Yx4QbiDU+dddK1MFhBbh/BFQQsNf28Zhb6ZQop4oRunf14dz2Zt2+QKDEPnuSvN2ujDLViqJoMSOWQceezVhT34+KOLGRxXpAnZeDSpyPlCbWiAHVgIFfCYIbLad02jeJSQENkJGU1McYjkBH9o1irERX6bPa35ExNCsfB2C9HyB3ZOFFSe5UuGt94vXlVLGzIADW5nlOz5VHlrWSctkMDbIp+ShlxmG52SDRvmwFBTtTb1onraWWYDnsZ8J+bYWPtMFtCNFe0sPisHVY0loR3gI5GmKSJMZEQLT6R4ntw==" />
</div>
        <script type="text/javascript">
//<![CDATA[
Sys.WebForms.PageRequestManager._initialize('ScriptManager1', 'form1', ['tUpdatePanel1','UpdatePanel1'], [], [], 90, '');
//]]>
</script>



        <div id="ibody">
            
            <div>
                <div id="login_result_pg">
                    <div style="float: left; margin-top: 30px;" id="appImg">
                        <img style="width: 510px; height: 322px" alt="" src="http://secure.96369.net/GetAppLoginImg.ashx?appCode=" />
                    </div>
                    <div id="login_res_banner" style="position: relative;">
                        <div class="hd" id="login-sm" style="display:none;">
                            <div class="login-switch"><a href="#" id="pass_smdl_link_back" class="pass_smdl_link_back">
                                <img src="/static/images/sm.png" width="48px;" height="48px;" /></a></div>
                            <div class="login-tip" style="padding-top: 8px;">
                                <div class="poptip">
                                    <div class="poptip-arrow">
                                        <em></em>
                                        <span></span>
                                    </div>
                                    <div class="poptip-content">
                                        扫码登录
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="hd" id="login-dn" style="display: none;">
                            <div class="login-switch"><a href="#" id="pass_dndl_link_back" class="pass_dndl_link_back">
                                <img src="/static/images/dn.png" width="48px;" height="48px;" /></a></div>
                        </div>
                        <div id="top_pg">
                        </div>
                        <div id="loading_word" style="display: none;">
                            <img src="/static/images/indicator_medium.gif" /><span style="position: relative; top: -10px;">正在努力加载用户信息</span></div>
                        <div id="cover_div" style="display: none;"></div>
                        <div id="login" class="login_banner_pg">
                            <div id="login_con">
                                登录<span id="lblTitle">会员账号中心</span>
                            </div>
                            <div id="lblMessage" style="text-align: left">
                            </div>
                            <div id="passport_login" style="display: none;">
                                <p class="sms_login_tip">原有西本账号仍可通过登录名密码登录。</p>
                                <p class="pass_login_username">
                                    <label id="passport_label_username" class="passport_label_username">登录名</label>
                                    <input name="txtUsrID" type="text" id="txtUsrID" class="passport_ipt_username pass_text_input" placeholder="登录名/手机/邮件" />
                                </p>
                                <p class="pass_login_password">
                                    <label id="passport_label_password" class="passport_label_password">密码</label>
                                    <input name="txtPwd" type="password" id="txtPwd" class="passport_ipt_password pass_text_input" placeholder="密码" />
                                </p>
                                <div class="pass_login_member" style="height: 18px;">
                                    <div id="divRememberMe" style="float: left;">
                                        <span class="pass_checkbox_member"><input id="cbRememberMe" type="checkbox" name="cbRememberMe" /></span>
                                        <label for="cbRememberMe">一周内免登录</label>
                                    </div>
                                    <div style="float: right; margin-right: 37px; padding-top: 3px;"><a href="Secure/PwdRecoverByMB.aspx" id="forgotPwdLink" class="pass_forgotpwd">忘记密码?</a></div>
                                </div>
                                <p class="pass_login_submit">
                                    <input type="submit" name="btnLogin" value="登  录" onclick="showLoading();" id="btnLogin" class="pass_button_submit" />
                                </p>
                                <p class="pass_login_back">
                                    <a title="手机登录" id="login_phone">手机登录</a>
                                </p>
                            </div>

                            <div id="sms_login" style="display: block; padding: 0;">
                                <p class="sms_login_tip">无需注册，使用手机号码接收短信即可登录。</p>
                                <p class="sms_login_phone">
                                    <label id="pass_label_smsphone" class="pass_label_smsphone">手机号</label>
                                    <input name="txtPhone" type="text" id="txtPhone" class="pass_ipt_smsphone sms_text_input" placeholder="请输入手机号" />
                                </p>
                                <p class="sms_login_verifyCode">
                                    <label id="pass_label_smsverifyCode" class="pass_label_smsverifyCode"></label>
                                    <input name="txtVerifyCode" type="text" id="txtVerifyCode" class="pass_ipt_smsverifyCode sms_text_input" placeholder="动态密码" />
                                    <button id="pass_button_smsTimer" class="pass_button_smsTimer" onclick="sendSmsCode();return false;">发送验证码</button>
                                </p>
                                <div class="pass_login_member" style="height: 18px;">
                                    <div id="div1">
                                        <span class="pass_checkbox_member"><input id="cbSMSRememberMe" type="checkbox" name="cbSMSRememberMe" /></span>
                                        <label for="cbSMSRememberMe">一周内免登录</label>
                                    </div>
                                </div>
                                <p class="sms_login_submit">
                                    <input type="submit" name="smsSubmit" value="登  录" onclick="showLoading();" id="smsSubmit" class="pass_button_submit" />
                                </p>
                                <p class="sms_login_back">
                                    <a id="pass_sms_link_back" class="pass_sms_link_back">西本账号登录</a>
                                </p>
                            </div>

                            <div id="pass_login_phoenix" class="pass_login_phoenix" style="margin-top: 0px;">
                                <div class="pass_list_login">
                                    <div style="float: right;">
                                        <input type="submit" name="btnQQLoing" value="" id="btnQQLoing" title="QQ登录" class="login_QQ" />
                                    </div>
                                    <div style="float: right; margin-right: 15px;">
                                        <input type="image" name="btnWXLogin" id="btnWXLogin" src="/static/images/wx_icon24.png" style="height:24px;" />
                                    </div>
                                </div>
                            </div>
                            <div id="divSmsVC" style="display: none; position: absolute; z-index: 999; background-color: #f6f6f6; border: 1px solid #C9C9C9; width: 300px; height: 200px; top: 100px; left: 10px;">
                                <label style="font-size: 16px; margin: 0px 5px 0px 5px;">安全验证</label><br />
                                <img id="imgVerifyCode" src="#" alt="如果图片验证码看不清,请点击刷新"
                                    title="请填写计算结果,如果图片验证码看不清,请点击刷新" style="cursor: pointer; top: 35px; left: 10px; position: relative;" />
                                <img id="imgVCClose" src="/static/images/close.png" alt="关闭" title="关闭" style="cursor: pointer; left: 135px; top: -28px; position: relative;" />
                                <input name="txtSMSValidateCode" type="text" id="txtSMSValidateCode" class="pass_ipt_smsverifyCode sms_text_input" placeholder="请输入验证码" style="position: relative; left: -15px; top: 17px;" />
                                <div style="margin: 0px 100px 0px 25px; top: 30px; position: relative;">点击图片，换一换</div>
                                <button id="vc_button_smsTimer" class="pass_button_submit" onclick="sendSmsCode();return false;" style="position: relative; left: 150px; top: 25px; width: 128px;">确  定</button>
                            </div>
                            <div class="mask"></div>
                        </div>
                        <!--扫码登录开始-->
                        <div class="login_banner_pg" style="display: none;" id="J_QRCodeLogin">
                            <div class="login-title">手机扫码，安全登录</div>
                            <div class="qrcode-main" id="qrCodeMain" style="display: block;">
                                <div class="qrcode-sm">
                                    <div id="UpdatePanel1">
	
                                            <div class="qrcode-img" id="J_QRCodeImg">
                                                <img id="qrCodeImg" alt="扫码登录" style="width: 140px; height: 140px;" /></div>
                                        
</div>
                                    <div class="msg-err" id="qrMsgErr" style="display: none;">
                                        <h6>二维码已失效</h6>
                                        <a href="javascript:;" id="J_QRCodeRefresh" class="refresh J_QRCodeRefresh">请点击刷新</a>
                                    </div>
                                </div>
                                <div class="qrcode-desc">
                                    <i class="iconfont">
                                        <img src="/static/images/sm2.png" width="34px;" height="34px;" /></i>
                                    <p>
                                        <font class="ft-gray">打开 </font><a href="http://trade.96369.net" target="_blank" class="light-link">采购经理人</a><br>
                                        <span class="ft-gray">扫一扫登录</span>
                                    </p>
                                </div>
                            </div>
                            <div class="qrcode-msg" id="qrCodeSuccess" style="display: none;">
                                <div class="msg-icon">
                                    <img src="/static/images/qrsucc.png" width="110px;" height="110px;" /></div>
                                <h6>扫描成功！</h6>
                                <p>请在手机上确认登录</p>
                                <div class="link"><a href="javascript:;" id="J_QRCodeReturn" class="light-link J_QRCodeReturn">返回二维码登录</a></div>
                                <input type="submit" name="btnQRScanOver" value="" id="btnQRScanOver" style="width:0px;display: none;" />
                            </div>
                        </div>
                        <!--扫码登录结束-->
                        <div id="bottom_pg">
                        </div>
                    </div>

                    <input name="connectionID" type="hidden" id="connectionID" />
                    <input name="userID" type="hidden" id="userID" />
                    
<div id="bottom_banner">
     西本新干线 版权所有. Copyright &copy; 1996-2017 All Rights Reserved.苏B1-20110076&nbsp;<a href="http://www.miibeian.gov.cn" target="_blank" style="color:Black;text-decoration:underline">沪ICP备05029129号</a>
</div>

                </div>
            </div>
         </div>
    </form>
    <script type="text/javascript">
        var bar = 0;
        //按秒倒计时，二维码已失效
        function qrErrCount() {
            bar = bar + 1;
            if (bar < 60)
            { setTimeout("qrErrCount()", 1000); }//按秒倒计时，十秒后执行else代码 
            else
            {
                $("#qrMsgErr").css("display", "block");
            }
        }
        var chat;
        $(function () {
            //$.connection.hub.url = "http://secure.96369.net/signalr";

            chat = $.connection.qrCodeHub;
            chat.client.createUNID = onCreateUNID;
            chat.client.QRScanSuccess = onScanSuccess;
            chat.client.QRScanOver = onQRScanOver;
            $.connection.hub.start().done(function () {
                chat.server.createUNID();
            });

        });
        function onCreateUNID(unid) {
            // Add the message to the list         
            $('#connectionID').val(unid);
            reflashQRCode();
            //$("#btnCreateCodeImage").click();
        }
        function onScanSuccess(userid) {
            //改为扫描成功状态     
            //alert(userid);
            $('#userID').val(userid);

            $("#login").css("display", "none");
            $("#J_QRCodeLogin").css("display", "block");
            $("#login-sm").css("display", "none");
            $("#login-dn").css("display", "block");
            $("#qrCodeMain").css("display", "none");
            $("#qrCodeSuccess").css("display", "block");
            bar = 0;
            qrErrCount();
        }
        function onQRScanOver(userid) {
            //依据同意还是拒绝，做相应的事情
            $('#userID').val(userid);
            if (userid == 0) {
                //二维码过期失效
                $("#login").css("display", "none");
                $("#J_QRCodeLogin").css("display", "block");
                $("#login-sm").css("display", "none");
                $("#login-dn").css("display", "block");
                $("#qrMsgErr").css("display", "block");
                $("#qrCodeMain").css("display", "block");
                $("#qrCodeSuccess").css("display", "none");
            }
            else {
                //产生新的token
                document.getElementById("btnQRScanOver").click();
            }
        }
    </script>
    <script type="text/javascript" language="javascript" src="http://secure.96369.net/script/jquery.watermark.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#txtUsrID").focus(function () { $("#passport_label_username").css("background-position", "0 -108px"); $(this).addClass("hover_border_blue") }).blur(function () { $("#passport_label_username").css("background-position", "0 -68px"); $(this).removeClass("hover_border_blue") });

            $("#txtPwd").focus(function () { $("#passport_label_password").css("background-position", "0 -187px"); $(this).addClass("hover_border_blue") }).blur(function () { $("#passport_label_password").css("background-position", "0 -147px"); $(this).removeClass("hover_border_blue") });

            $("#txtPhone").focus(function () { $("#pass_label_smsphone").css("background-position", "0 -37px"); $(this).addClass("hover_border_blue") }).blur(function () { $("#pass_label_smsphone").css("background-position", "0 3px"); $(this).removeClass("hover_border_blue") });
            $("#txtVerifyCode").focus(function () { $(this).addClass("hover_border_blue") }).blur(function () { $(this).removeClass("hover_border_blue") });
            $("#login_phone").click(function () {
                $("#passport_login").css("display", "none");
                $("#sms_login").css("display", "block");
                //$("#lblMessage").html("");
            });
            $("#pass_sms_link_back").click(function () {
                $("#passport_login").css("display", "block");
                $("#sms_login").css("display", "none");
                //$("#lblMessage").html("");
            });
            $("#pass_smdl_link_back").click(function () {
                //点击扫码登录
                //$("#btnCreateCodeImage").click();
                reflashQRCode();
                $("#login").css("display", "none");
                $("#J_QRCodeLogin").css("display", "block");
                $("#login-sm").css("display", "none");
                $("#login-dn").css("display", "block");
                $("#qrCodeMain").css("display", "block");
                $("#qrCodeSuccess").css("display", "none");

                $("#qrMsgErr").css("display", "none");
                bar = 0;
                qrErrCount();

            });
            $("#pass_dndl_link_back").click(function () {
                //点击电脑登录
                $("#login").css("display", "block");
                $("#J_QRCodeLogin").css("display", "none");
                $("#login-sm").css("display", "block");
                $("#login-dn").css("display", "none");
            });
            $("#J_QRCodeRefresh").click(function () {
                //二维码过期刷新
                //$("#btnCreateCodeImage").click();
                reflashQRCode();
                $("#login").css("display", "none");
                $("#J_QRCodeLogin").css("display", "block");
                $("#login-sm").css("display", "none");
                $("#login-dn").css("display", "block");
                $("#qrCodeMain").css("display", "block");
                $("#qrCodeSuccess").css("display", "none");

                $("#qrMsgErr").css("display", "none");
                bar = 0;
                qrErrCount();
            });
            $("#J_QRCodeReturn").click(function () {
                //返回二维码登录
                //$("#btnCreateCodeImage").click();
                reflashQRCode();
                $("#login").css("display", "none");
                $("#J_QRCodeLogin").css("display", "block");
                $("#login-sm").css("display", "none");
                $("#login-dn").css("display", "block");
                $("#qrCodeMain").css("display", "block");
                $("#qrCodeSuccess").css("display", "none");

                $("#qrMsgErr").css("display", "none");
                bar = 0;
                qrErrCount();
            });


            $("#txtSMSValidateCode").keydown(function (e) {
                if (e.keyCode == 13) {
                    sendSmsCode();
                    return false;
                }
            });

            $("#imgVerifyCode").click(function () {
                $("#imgVerifyCode").attr('src', 'http://secure.96369.net/ValidationImg2.aspx?times=' + Math.random());
            });
            $("#imgVCClose").click(function () {
                $(".mask").css("display", "none");
                $("#divSmsVC").css("display", "none");
                $("#lblMessage").html("");
                $("#pass_button_smsTimer").removeAttr("disabled");
                $("#pass_button_smsTimer").removeClass("pass_button_smsTiming");
            });
        });

        function reflashQRCode()
        {
            var connid = $("#connectionID").val();
            var appname = $("#lblTitle").val();
            $.post("ReflashQRCode.aspx", { connid: connid, appname: appname }, function (data) {
                //需要验证码，显示验证码窗口
                $("#qrCodeImg").attr('src', 'images/qr/' + connid + '.png?v=' + Math.random());
            });
        }
        function showLoading() {
            $("#cover_div").css("display", "block");
            $("#loading_word").css("display", "block");
        }
        var t;
        var intervalObj;
        function sendSmsCode() {

            var phone = $("#txtPhone").val();
            if (phone == null || typeof (phone) == "undefined") {
                $("#lblMessage").html("请输入手机号");
                return false;
            }
            var vc = $("#txtSMSValidateCode").val();
            $(".mask").css("display", "none");
            $("#divSmsVC").css("display", "none");
            t = 60;
            $("#pass_button_smsTimer").attr("disabled", "disabled");
            $("#pass_button_smsTimer").addClass("pass_button_smsTiming");
            $.post("SendSmsMessage.aspx", { p: phone, op: "sms", vc: vc }, function (data) {
                if (data.msg == "") {
                    intervalObj = window.setInterval("refer()", 1000);   //启动1秒定时 
                    $("#lblMessage").html("");
                }
                else if (data.msg.indexOf("VC:") > -1) {
                    //需要验证码，显示验证码窗口
                    $("#imgVerifyCode").attr('src', 'http://secure.96369.net/ValidationImg2.aspx?times=' + Math.random());
                        $(".mask").css("display", "block");
                        $("#divSmsVC").css("display", "block");
                    }
                    else {
                        $("#lblMessage").html(data.msg);
                        $("#pass_button_smsTimer").removeAttr("disabled");
                        $("#pass_button_smsTimer").removeClass("pass_button_smsTiming");
                    }
            });
        }
        function refer() {
            t--;
            if (t >= 0) {
                $("#pass_button_smsTimer").html("重新发送(" + t + ")");
            } else {
                window.clearInterval(intervalObj);
                $("#pass_button_smsTimer").html("重新发送");
                $("#pass_button_smsTimer").removeAttr("disabled");
                $("#pass_button_smsTimer").removeClass("pass_button_smsTiming");
            }
        }
    </script>
   


</body>
</html>