<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>体检报告在线分析-海豚医生</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="keywords" content="体检报告分析">
    <meta name="description" content="依托全网海量优质数据和业界领先的深度学习技术 ，专门对体检报告进行识别优化，支持更多字体和复杂背景下的文字识别。">
    <!-- <meta name="baidu-site-verification" content="GOPjfm49Yc" /> -->
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/modernizr/2.8.3/modernizr.min.js"></script>
    <![endif]-->
    <!-- <link rel="shortcut icon" href="//ai.bdstatic.com/dist/ai_images/favicon-32.ico"> -->
    <link rel="shortcut icon" href="favicon.ico" />

    <!--[if IE 9]>
    <link rel="stylesheet" href="//ai.bdstatic.com/dist/1497527218/css/ie9.style.css">
    <![endif]-->
    <link rel="stylesheet" href="//ai.bdstatic.com/dist/1497527218/css/base.style.css">
    <link rel="stylesheet" href="//ai.bdstatic.com/dist/1497527218/css/technology/ocr-webimage.style.css">
    <!--  
    <script>
        var _hmt = _hmt || [];
        (function() {
            var hm = document.createElement("script");
            hm.src = "https://hm.baidu.com/hm.js?fdad4351b2e90e0f489d7fbfc47c8acf";
            var s = document.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(hm, s);
        })();
    </script>
    -->
    <style type="text/css">

	#imghead {
	filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);
	}
</style>
<script type="text/javascript" src="../../js/previewUpload.js"></script> 
 </head>   
<div class="ai-platform page-content">

<!-- <section class="tech-section">
    <div class="ai-container">
        <div class="tech-catalog-title">功能简介</div>
        <div class="tech-catalog-info">
            能够快速准确识别各种网络图片中的文字，在复杂字体和复杂背景的情况下仍能保持非常高的识别准确率。
        </div>
        <div class="tech-web-detail tech-clear">
            <div class="tech-web-original-card"></div>
            <div class="tech-web-arrow"></div>
            <div class="tech-web-scan-result"></div>
        </div>
    </div>
</section> -->
<div class="tech-section tech-demo" id="tech-demo">
    <div class="ai-container">
        <div class="tech-demo-title">
            体检报告在线分析
        </div>
        <!-- <div id="preview"></div> -->
        <div class="tech-demo-container">
            <div class="tech-demo-data-view tech-demo-origin" data-label="体检报告" id="preview">
            <img class="tech-demo-origin-img" id="imghead" src='<%=request.getContextPath()%>/image/timg.jpg'>
            </div>
            <!-- <div class="tech-demo-data-view" data-label="分析结果" id="demo-result"> -->
<!--             <div class="tech-demo-data-view tech-demo-json"
                 id="demo-json"
                 data-label="分析结果">
                <p>{
					"words": " mcv 9 2 fl"
					}
				</p>
            </div> -->
            </div>
            <div class="tech-demo-choose">
                <div class="tech-demo-upload tech-clear">
                    <label>
                        <input type="text" class="ocr-demo-photo-url" id="demo-photo-url" placeholder="请输入网络图片URL">
                    </label>
                    <button type="button" class="btn-primary" id="scan-url">检测</button>
                    <div class="ocr-demo-huo">或</div>
                    <label class="ocr-demo-local" id="demo-photo-upload">
                        <input type="file" id="img-upload" name="img-upload" onchange="previewImage(this)">本地上传
                    </label>
                </div>
                <div class="tech-demo-desc">
                    提示：可支持PNG,JPG,JPEG,BMP,GIF（首帧），图片大小不超过4M，长边不大于4096像素。请保证需要识别的部分为图片主体部分
                </div>

            </div>
            <div class="tech-demo-data-view tech-demo-json"
                 id="demo-json"
                 data-label="分析结果">
                <p id="analysis_result">

                </p>
            </div>
        </div>
    </div>
</div>
</div>

<footer class="footer-nav">
    <div class="container">
        <p class="copyright" style="text-align:center;">©2017 海豚医生 <a href="/" target="_blank">海豚医生</a></p>
    </div>
</footer>
<!-- <script src="//ai.bdstatic.com/dist/1497527218/js/common.bundle.js"></script>
<script src="//ai.bdstatic.com/dist/1497527218/js/technology/ocr-webimage.js"></script> -->
 
<!-- <script>
    (function () {
        var bp = document.createElement('script');
        var curProtocol = window.location.protocol.split(':')[0];
        if (curProtocol === 'https') {
            bp.src = 'https://zz.bdstatic.com/linksubmit/push.js';
        }
        else {
            bp.src = 'http://push.zhanzhang.baidu.com/push.js';
        }
        var s = document.getElementsByTagName('script')[0];
        s.parentNode.insertBefore(bp, s);
    })();
</script> -->
	<script type="text/javascript" src="../../js/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="../../js/ajaxfileupload.js"></script>
</body>
</html>
<!-- <script> var _trace_page_logid = 3409521361; </script> -->