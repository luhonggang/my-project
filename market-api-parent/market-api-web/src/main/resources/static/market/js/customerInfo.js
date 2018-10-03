$(function () {
    // 加载用户的数据
    var token = localStorage.getItem("TOKEN");
    var customerId = localStorage.getItem("customerId");
    // 加载数据
    $.ajax({
        url:baseUrl+'task/queryCustomerMsg.json?customerId='+customerId,
        type:'GET',
        beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("token", token);
        },
        async:"true", //默认是true,异步
        dataType:"json",
        contentType:"application/json",
        success:function(result){
            console.log(result);
            if(result.code=="200"){
                console.log(result.data.customerState);
                $("#topCustomerPhone").html(result.data.phone+"&nbsp;");
                $("#customerPhone").html(result.data.phone+"，欢迎您");
                $("#accountMoney").html("￥"+result.data.customerState.totalMoney);
                $("#levelName").html(result.data.customerLevel.levelName);
                $("#expiredTime").html("到期时间："+result.data.customerState.expiredTime);
                $("#completeTask").html(result.data.taskIsComplete);
                $("#taskIsDoing").html(result.data.taskIsDoing);
            }else if(result.code=="500"){
                alert("系统繁忙稍后再试");
            }else if(result.code == '401'){
                // token 失效 直接跳转登录页
                window.location.href = baseUrl+"page/login.html";
            }
        }
    });
});