<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html lang="zh">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>HTML5 canvas画图及图片上传服务器|DEMO_jQuery之家-自由分享jQuery、html5、css3的插件库</title>
	<!--
	<link rel="stylesheet" type="text/css" href="css/normalize.css" />
	<link rel="stylesheet" type="text/css" href="css/default.css">
	<link href="css/jquery.lighter.css" rel="stylesheet" type="text/css" />
	
	<style type="text/css">
		.i-canvas ul{display: table;}
		.i-canvas ul li{display: table-cell;list-style: none;}
		.i-canvas ul li button{display: inline-block;margin: 0.5em;padding: 0.6em 1em;border: 3px solid #1d7db1;font-weight: 700;}
	</style>
	-->
	<!--[if IE]>
		<script src="http://libs.useso.com/js/html5shiv/3.7/html5shiv.min.js"></script>
	<![endif]-->
</head>
<body onload="InitThis()">
	<!--
	<div class="htmleaf-container">
		<header class="htmleaf-header">
			<h1>HTML5 canvas画图及图片上传服务器 <span>Draw a Canvas image, Upload and save it on the server</span></h1>
			<div class="htmleaf-links">
				<a class="htmleaf-icon icon-home" href="http://www.htmleaf.com/" title="jQuery之家" target="_blank"><span> jQuery之家</span></a>
				<a class="htmleaf-icon icon-arrow-right3" href="" title="返回下载页" target="_blank"><span> 返回下载页</span></a>
			</div>
		</header>
		<div class="htmleaf-content bgcolor-8">
			<form id="form1" runat="server">
               <div align="center" class="i-canvas">
                    <canvas id="myCanvas" width="500" height="300"></canvas>
                    <ul>
                    	<li><button onclick="javascript:DrawPic();return false;">开始画图</button></li>
                    	<li><button onclick="javascript:UploadPic();return false;">上传到服务器</button></li>
                    </ul>
               </div>
          	</form>
		</div>
		<div class="related">
		    <h3>如果你喜欢这个效果，那么你可能也喜欢:</h3>
		    <a href="http://www.htmleaf.com/ziliaoku/qianduanjiaocheng/201502101363.html">
		      <img src="img/1.jpg" width="300" alt="使用HTML5 CANVAS制作涂鸦画板"/>
		      <h3>使用HTML5 CANVAS制作涂鸦画板</h3>
		    </a>
		    <a href="http://www.htmleaf.com/html5/html5-canvas/201502021314.html">
		      <img src="img/2.jpg" width="300" alt="HTML5 canvas生成图片马赛克特效插件"/>
		      <h3>HTML5 canvas生成图片马赛克特效插件</h3>
		    </a>
		</div>
	</div>
	-->
	
	<div align="center">
    <canvas id="myCanvas" width="500" height="200" style="border:2px solid #6699cc"></canvas>
    <div class="control-ops">
    <button type="button" class="btn btn-primary" onclick="javascript:clearArea();return false;">清空画板</button>
    Line width : <select id="selWidth">
        <option value="1">1</option>
        <option value="3">3</option>
        <option value="5">5</option>
        <option value="7">7</option>
        <option value="9" selected="selected">9</option>
        <option value="11">11</option>
    </select>
    Color : <select id="selColor">
        <option value="black">black</option>
        <option value="blue" selected="selected">blue</option>
        <option value="red">red</option>
        <option value="green">green</option>
        <option value="yellow">yellow</option>
        <option value="gray">gray</option>
    </select>
	<button onclick="javascript:UploadPic();return false;">上传到服务器</button>
    </div>
	</div>   
	<script type="text/javascript" src="http://libs.useso.com/js/jquery/1.10.2/jquery.min.js"></script>
	<script type="text/javascript" src="js/html5-canvas-upload.js"></script>
</body>
</html>