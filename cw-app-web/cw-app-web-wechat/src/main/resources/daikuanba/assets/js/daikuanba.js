// var httpUrl = "http://192.168.1.126:9999";
var httpUrl = "https://www.pingxundata.com";
// var httpUrl = "https://www.lelejh.com";
var loginUrl = httpUrl + "/common/passwordLogin.json";
var logoutUrl = httpUrl + "/common/logout.json";
var uploadUrl = httpUrl + "/common/upload.json";
var findByType = httpUrl + "/common/findByType.json";
var sendSmsUrl = httpUrl + "/common/sendSmsVerify.json";
var productListUrl = httpUrl + "/front/product/findByCondition.json";
var findByIdUrl = httpUrl + "/front/product/findById.json";
var findApplyListUrl = httpUrl + "/front/product/findApplyList.json";
var findParameterUrl = httpUrl + "/front/parameter/findParameter.json";
var loadBannerUrl = httpUrl + "/common/findBanner.json";
var loadRecommendUrl = httpUrl + "/front/creditCard/findRecommend.json";
var loadCardUrl = httpUrl + "/front/creditCard/findByCondition.json";
var applyLoan = httpUrl + "/front/product/applyLoan.json";
function post(url,data) {
    var returnData;
    var json = JSON.stringify(data);
    json = json.replace(/\r\n/g,"\n");
    $.ajax({
        url: url,
        contentType: "application/json;charset=utf-8",
        data: json,
        method:"post",
        dataType: "json",
        async: false,
        success: function (data) {
            returnData = data;
        },
        error: function () {
            alert("网络请求错误");
        }
    });
    return returnData;
}

function get(url,data) {
    var returnData;
    $.ajax({
        url: url,
        contentType: "application/json;charset=utf-8",
        data: data,
        method:"get",
        dataType: "json",
        async: false,
        success: function (data) {
            returnData = data;
        },
        error: function () {
            alert("网络请求错误");
        }
    });
    return returnData;
}

function uploadFile(id){
    var result="";
    $.ajaxFileUpload({
        url:uploadUrl,//服务器端程序
        secureuri: false,
        fileElementId:id,//input框的ID
        method:"post",
        async: false,
        dataType: 'json',//返回数据类型
        success: function (data){//上传成功
            $("#"+id+"Img").attr("src",data.data);
            $("#"+id+"Hide").val(data.data);
        },
        error: function (data, status, e)//服务器响应失败处理函数
        {
            alert(e);
        }
    });
    return result;
}
function backProductList() {
    window.location.href="/static/productlist.html";
}
function logout() {
    var param = "1=1";
    var result =  get(logoutUrl,param);
    if(result.success)
    {
        $.cookie("userName",null,{
            path:"/"
        });
        window.location.href="/static/login.html";
    }
}
function showMessage(message) {
    dialog({
        type: null,
        message: message,
        delay: 1000,
        maskOpacity: 0.01
    });
    return false;
}
function getLocation(x, y) {
    var bbox = canvas.getBoundingClientRect();
    return {
        x: (x - bbox.left) * (canvas.width / bbox.width),
        y: (y - bbox.top) * (canvas.height / bbox.height)

        /*
         * 此处不用下面两行是为了防止使用CSS和JS改变了canvas的高宽之后是表面积拉大而实际
         * 显示像素不变而造成的坐标获取不准的情况
         x: (x - bbox.left),
         y: (y - bbox.top)
         */
    };
}
function getAngle(px,py,mx,my){
    var x = Math.abs(px-mx);
    var y = Math.abs(py-my);
    var z = Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
    var cos = y/z;
    var radina = Math.acos(cos);//用反三角函数求弧度
    var angle = Math.floor(180/(Math.PI/radina));//将弧度转换成角度
    if(mx>px&&my>py){//鼠标在第四象限
        angle = 180 - angle;
    }
    if(mx==px&&my>py){//鼠标在y轴负方向上
        angle = 180;
    }
    if(mx>px&&my==py){//鼠标在x轴正方向上
        angle = 90;
    }
    if(mx<px&&my>py){//鼠标在第三象限
        angle = 180+angle;
    }
    if(mx<px&&my==py){//鼠标在x轴负方向
        angle = 270;
    }
    if(mx<px&&my<py){//鼠标在第二象限
        angle = 360 - angle;
    }
    return angle;
}
function checkLogin() {
    try{
        var storage = window.localStorage;
        var loginUser = storage["loginUser"];
        if(loginUser!=null&&loginUser!="null")
        {
            $("#userName").text(loginUser);
            $("#logout").css("display","");
        }else{
            window.location.href="/borrow/login.html";
        }
    }catch (e){}
}
function getArgsFromHref(sHref, sArgName)
{   var args    = sHref.split("?");
    var retval = 0;
    if(args[0] == sHref) /*参数为空*/
    {
        return retval; /*无需做任何处理*/
    }
    var str = args[1];
    args = str.split("&");
    for(var i = 0; i < args.length; i ++)
    {
        str = args[i];
        var arg = str.split("=");
        if(arg.length <= 1) continue;
        if(arg[0] == sArgName) retval = arg[1];
    }
    return retval;
}
var sendTime =60;
function leftTimer() {
    sendTime--;
    if (sendTime == 0) {
        window.clearInterval(InterValObj);
        $("#sending").css("display","none")
        $("#sendSms").css("display","")
    }
    $("#sending").text("倒计时"+sendTime+"s, 剩余2次")
};