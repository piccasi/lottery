// by Chtiwi Malek on CODICODE.COM
//debugger;
var mousePressed = false;
var lastX, lastY;
var ctx;
 
function InitThis() {
    ctx = document.getElementById('myCanvas').getContext("2d");
 
    $('#myCanvas').mousedown(function (e) {
        mousePressed = true;
        Draw(e.pageX - $(this).offset().left, e.pageY - $(this).offset().top, false);
    });
 
    $('#myCanvas').mousemove(function (e) {
        if (mousePressed) {
            Draw(e.pageX - $(this).offset().left, e.pageY - $(this).offset().top, true);
        }
    });
 
    $('#myCanvas').mouseup(function (e) {
        mousePressed = false;
    });
        $('#myCanvas').mouseleave(function (e) {
        mousePressed = false;
    });
}
 
function Draw(x, y, isDown) {
    if (isDown) {
        ctx.beginPath();
        ctx.strokeStyle = $('#selColor').val();
        ctx.lineWidth = $('#selWidth').val();
        ctx.lineJoin = "round";
        ctx.moveTo(lastX, lastY);
        ctx.lineTo(x, y);
        ctx.closePath();
        ctx.stroke();
    }
    lastX = x; lastY = y;
}
     
function clearArea() {
    // Use the identity matrix while clearing the canvas
    ctx.setTransform(1, 0, 0, 1, 0, 0);
    ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);
}

function DrawPic() {
    // Get the canvas element and its 2d context
    var Cnv = document.getElementById('myCanvas');
    var Cntx = Cnv.getContext('2d');
    
    // Create gradient
    var Grd = Cntx.createRadialGradient(150, 150, 20, 140, 200, 330);
    Grd.addColorStop(0, "#c96513");
    Grd.addColorStop(1, "#861d33");

    // Fill with gradient
    Cntx.fillStyle = Grd;
    Cntx.fillRect(0, 0, 500, 300);

    // Write some text
    for (i=1; i<10 ; i++)
    {
        Cntx.fillStyle = "white";
        Cntx.font = "36px Microsoft YaHei";
        Cntx.globalAlpha = (i-1) / 9;
        Cntx.fillText("jQuery之家-htmleaf.com", i * 3 , i * 30);
    }
}

function UploadPic() {
    
    // generate the image data
    var Pic = document.getElementById("myCanvas").toDataURL("image/png");
    Pic = Pic.replace(/^data:image\/(png|jpg);base64,/, "")

    // Sending the image data to Server
    $.ajax({
        type: 'post',
        url: 'image/save',
        data: {imageStr : Pic },
        //contentType: 'application/x-www-form-urlencoded; charset=utf-8',
        dataType: 'json',
        success: function (msg) {
            alert("Done, Picture Uploaded."); 
        }
    });
}
