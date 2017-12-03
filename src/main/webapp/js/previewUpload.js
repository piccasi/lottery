 
 
                //图片上传预览    IE是用了滤镜。
        function previewImage(file)
        {
          var MAXWIDTH  = 560; 
          var MAXHEIGHT = 300;
          var div = document.getElementById('preview');
          if (file.files && file.files[0])
          {
              div.innerHTML ='<img id=imghead>';
              var img = document.getElementById('imghead');
              img.onload = function(){
                var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
                img.width  =  rect.width;
                img.height =  rect.height;
//                 img.style.marginLeft = rect.left+'px';
                img.style.marginTop = rect.top+'px';
              }
              var reader = new FileReader();
              reader.onload = function(evt){img.src = evt.target.result;}
              reader.readAsDataURL(file.files[0]);
          }
          else //兼容IE
          {
            var sFilter='filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
            file.select();
            var src = document.selection.createRange().text;
            div.innerHTML = '<img id=imghead>';
            var img = document.getElementById('imghead');
            img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
            var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
            status =('rect:'+rect.top+','+rect.left+','+rect.width+','+rect.height);
            div.innerHTML = "<div id=divhead style='width:"+rect.width+"px;height:"+rect.height+"px;margin-top:"+rect.top+"px;"+sFilter+src+"\"'></div>";
          }
          
          var obj = $("#img-upload").val();
          /*
          $.ajax({
              type: "POST",
              url: "analysis",
              data: { imgPath: $("#img-upload").val() },
              cache: false,
              success: function(data) {
                  alert("上传成功");
                  //$("#imgDiv").empty();
                  //$("#imgDiv").html(data);
                  //$("#imgDiv").show();
              },
              error: function(XMLHttpRequest, textStatus, errorThrown) {
                  alert("上传失败，请检查网络后重试");
              }
          });
          */
          
          $.ajaxFileUpload  
          (    
               {   
                   url: 'analysis', //你处理上传文件的服务端  
                   secureuri:false,  
                   fileElementId:'img-upload',  
                   dataType: 'json',  
                   success: function(data, status){  
                       //alert(data.words_result);
                       //alert(JSON.stringify(data));
                       var analysis_result = $('#analysis_result');
                       analysis_result.text("");
                       analysis_result.append(JSON.stringify(data.result));
                       //analysis_result.append(data);
                       
                   },
                   error: function(data, status, e){ 
                       alert(e);
                   } 
               }  
           ); 
          
        }
        function clacImgZoomParam( maxWidth, maxHeight, width, height ){
            var param = {top:0, left:0, width:width, height:height};
            if( width>maxWidth || height>maxHeight )
            {
                rateWidth = width / maxWidth;
                rateHeight = height / maxHeight;
                 
                if( rateWidth > rateHeight )
                {
                    param.width =  maxWidth;
                    param.height = Math.round(height / rateWidth);
                }else
                {
                    param.width = Math.round(width / rateHeight);
                    param.height = maxHeight;
                }
            }
             
            param.left = Math.round((maxWidth - param.width) / 2);
            param.top = Math.round((maxHeight - param.height) / 2);
            return param;
        }