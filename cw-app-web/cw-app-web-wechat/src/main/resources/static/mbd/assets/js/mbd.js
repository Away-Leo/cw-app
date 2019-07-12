var httpUrl = "https://www.pingxundata.com";//正式环境
// var httpUrl = "http://192.168.1.171:9999";//本地开发环境
// var httpUrl = "http://192.168.1.100:1234";//测试环境
var loginUrl = httpUrl + "/common/passwordLogin.json";
var sendSmsUrl = httpUrl + "/common/sendSmsVerify.json";
var productListUrl = httpUrl+"/front/product/findByCondition.json";
var productRecommendUrl = httpUrl +"/front/product/findRecommendProduct.json";
var findByIdUrl = httpUrl +"/front/product/findById.json";
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
        beforeSend:function () {
            $("body").mLoading();
        },
        success: function (data) {
            returnData = data;
        },
        complete:function () {
            $("body").mLoading("hide");
        },
        error: function () {
            showTipMessage("数据请求错误!",true);
        }

    });
    return returnData;
}

function get(url,data) {
    var returnData;
    data = data;
    $.ajax({
        url: url,
        contentType: "application/json;charset=utf-8",
        data: data,
        method:"get",
        dataType: "json",
        async: false,
        beforeSend:function () {
            $("body").mLoading();
        },
        success: function (data) {
            returnData = data;
        },
        complete:function () {
            $("body").mLoading("hide");
        },
        error: function () {
            showTipMessage("数据请求错误!",true);
        }
    });
    return returnData;
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
function showTipMessage(message,flag) {
    layer.alert(message,{icon:2,yes:function(index){
        if (flag) {
            window.location.href = "/static/mbd/index.html";
        }else{
            layer.close(index);
        }
    }});
}
function viewDetail(id) {
    window.location.href="/static/mbd/prodetail.html?id="+id;
}
var sendTime =60;
function leftTimer() {
    sendTime--;
    if (sendTime == 0) {
        window.clearInterval(InterValObj);
        $("#sending").css("display","none");
        $("#sendSms").css("display","");
    }
    $("#sending").text(""+sendTime+"s");
}
function getArgsFromHref(sHref, sArgName)
{   var args    = sHref.split("?");
    var retval = "";
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
function loginPost(url,data) {
    var returnData;
    data["type"]="user";
    var hrefUrl = window.location.href;
    var channelNo = getArgsFromHref(hrefUrl,"channelNo");
    data["channelNo"] = channelNo;
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
            alert("error");
        }
    });
    return returnData;
}