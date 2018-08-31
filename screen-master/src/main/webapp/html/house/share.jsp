<%--
  Created by IntelliJ IDEA.
  User: Betel
  Date: 2017/7/3
  Time: 10:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"  language="java" %>
<% String path = request.getContextPath();
request.setAttribute("path",path);%>
<html>
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0" />
<head>
    <meta charset="utf-8">
    <title>分享</title>
</head>
<!-- jquery -->
<script src="${path}/scripts/js/jquery-1.11.3.min.js"></script>
<style>
    body{ margin:0px; padding:0px; margin:0px auto; color:#414446; width:100%; font-family:"微软雅黑";}
    .divContent{ background:#fff; width: 100%;  position: absolute; top: 50%; left: 0; margin-top: -162px;}
    .divBox{ width:80%; max-width: 700px; margin:0 auto; border-radius:6px; -webkit-box-shadow:0 0 10px #d8d8d8; -moz-box-shadow:0 0 10px #d8d8d8; box-shadow:0 0 10px #d8d8d8; }
    .divBox .divBoxTitle{ padding:15px; background:#46bec8; color:#fff;  border-top-left-radius:6px;  border-top-right-radius:6px; font-size:16px; line-height:2;}
    .divBox .divBoxContent{ padding:25px 15px;}
    .divBox .divBoxContent .spanTitle{ display:block; font-size:14px; color:#1c1c1c; margin-bottom:20px;}
    .divBox .divBoxContent .divInput{ position: relative; padding-right: 104px;}
    .divBox .divBoxContent .text{ width: 100%; box-sizing: border-box; border:0; background:#f0f0f0;  border-radius:4px; border: 1px solid #f2f2f2; padding:0 10px; line-height:37px; outline:none;-webkit-appearance: none;  }
    .divBox .divBoxContent .a_btn{ position: absolute; top: 0; right: 0; background:#46bec8; color:#fff; display:inline-block; line-height:39px; text-decoration:none; border-radius:4px; padding:0 15px; margin-left:10px;}
    .divBox .divBoxContent .divError{ color: #F00; padding-top: 5px; }

    .divLoseEfficacy{ text-align: center; }
    .divLoseEfficacy .divLoseBox{ position:absolute; left: 50%; top:50%; margin-left: -94px; margin-top: -27px; display: inline-block; font-size: 18px; border-radius:4px; -webkit-box-shadow:0 0 10px #d8d8d8; -moz-box-shadow:0 0 10px #d8d8d8; box-shadow:0 0 10px #d8d8d8; color: #f00; padding:15px 40px;}
</style>
<body>
    <input type="hidden" id="code" value="${code}" />
    <input type="hidden" id="fileName" value="${fileName}">
    <input type="hidden" id="id" value="${shareId}">
    <div class="divContent">
    </div>
</body>
<script type="text/javascript">
    var code = $("#code").val().trim();
    var html = "";
    console.log(code);
    if(code == "00000"){
        html += '<div class="divBox">';
        html += '<div class="divBoxTitle">分享<span class="span_fileName">${fileName}</span>的图纸</div>';
        html += '<div class="divBoxContent">';
        html += '<span class="spanTitle">请输入提取密码：</span>';
        html += '<div class="divInput">';
        html += '<form id="formTest" action="${path}/share/downloadPicture" method="post">';
        html += '<input type="text" class="text" name="randomPassword" id="randomPassword" />';
        html += '<input type="hidden" name="shareId" id="shareId" />';
        html += '</form>';
        html += '<a href="javascript:void(0)" class="a_btn" id="extractFile">提取文件</a>';
        html += '</div>';
        html += '<div class="divError"></div>';
        html += '</div>';
        html += '</div>';
        $(".divContent").html(html);
    }else if(code == "H1025"){
        html += '<div class="divBox">';
        html += '<div class="divBoxTitle">分享<span class="span_fileName">${fileName}</span>的图纸</div>';
        html += '<div class="divBoxContent">';
        html += '<span class="spanTitle">请输入提取密码：</span>';
        html += '<div class="divInput">';
        html += '<form id="formTest" action="${path}/share/downloadPicture" method="post">';
        html += '<input type="text" class="text" name="randomPassword" id="randomPassword" />';
        html += '<input type="hidden" name="shareId" id="shareId" />';
        html += '</form>';
        html += '<a href="javascript:void(0)" class="a_btn" id="extractFile">提取文件</a>';
        html += '</div>';
        html += '<div class="divError"></div>';
        html += '</div>';
        html += '</div>';
        $(".divContent").html(html);
        $(".divError").html("提取密码错误");
    }else {
        html += '<div class="divLoseEfficacy"><div class="divLoseBox">链接已失效！</div></div>';
        $(".divContent").html(html);
    }

    $(function(){
        $("#extractFile").click(function(){
            var shareId = $("#id").val().trim() ;
            var randomPassword = $("#randomPassword").val().trim();
            $("#shareId").val(shareId);
            var data = {"shareId":shareId,"randomPassword":randomPassword};
            if(randomPassword==""){
                $(".divError").html("请输入提取密码再提交");
            }else{
                $("#formTest").submit();
            }
        });
        $("#randomPassword").click(function(){
            $(".divError").html("");
        })
    })
</script>
</html>
