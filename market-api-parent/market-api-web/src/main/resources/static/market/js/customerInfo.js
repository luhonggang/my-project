var currPageVMoney = 0.00;
var currPageNMoney = 0.00;
var totalMoney = 0.00;
var pcRate = 0.60;
var pcType = '1';

// 设置默认的div内容
var clickDivId = "pcll_nmrw";

// 设置当前用户的会员等级和折扣
function showVipRate($id,flag,totalMoney,discount){
    var $firstEle = $("#"+$id+" em:first-child");
    $firstEle.find('span').eq(0).html($('#levelName').html());
    $firstEle.find('span').eq(1).html($('#discount').val()+'折');
    if(flag){
        $("#"+$id+"").find('em').eq(1).show();
        var $lastEle = $("#"+$id+" em").last();
        // 实际价格
        $lastEle.find('span').eq(0).html(totalMoney);
        // 折扣价格
        $lastEle.find('span').eq(1).html(discount);
    }
}

/**
 *  校验是否为正整数
 **/
function checkIsNumber(val){
    return (/(^[1-9]\d*$)/.test(val));
}

// 依据比例rate计算当前的金额
function calculateMoney(currentVal,rate,type){
    var tvMoney = 0;
    if(currentVal.indexOf(',') != -1){
        var vList = currentVal.split(',');
        var currenVal = 0;
        for (var i = 0; i < vList.length; i++){
            currenVal = vList[i];
            if(checkIsNumber(currenVal)){
                tvMoney += Number(currenVal*rate)
            }
        }
    }
    if(currentVal.indexOf('，') != -1){
        var vList = currentVal.split('，');
        var currenVal = 0;
        for (var i = 0; i < vList.length; i++){
            currenVal = vList[i];
            if(checkIsNumber(currenVal)){
                tvMoney += Number(currenVal*rate);
            }
        }
    }
    if('vs' === type){
        // 说明用户输入的数值没有特殊符号
        if(tvMoney === 0){
            currPageVMoney = currentVal;
        }else{
            currPageVMoney = tvMoney.toFixed(2);
        }
    }else if('nm' === type){
        if(tvMoney === 0){
            currPageNMoney = currentVal;
        }else{
            currPageNMoney = tvMoney.toFixed(2);
        }
    }
}

/**
 * 格式校验
 * */
function checkValFormat($id,val,tips){
    var arrayNumber = new Array();
    var falg = true;
    var wordName = $("#"+$id+" input[name='wordList']").val();
    console.log("关键词 ："+wordName)
    var prefix = val.substr(0,2);
    var suffix = val.charAt(val.length - 1);
    if(prefix == '，' || prefix == ','){
        falg == false
        return;
    }
    if(suffix == '，' || suffix == ','){
        falg == false
        return;
    }
//
//        if(currentVal.indexOf(',') != -1){
//            var vList = currentVal.split(',');
//            var currenVal = 0;
//            for (var i = 0; i < vList.length; i++){
//                currenVal = vList[i];
//                if(checkIsNumber(currenVal)){
//                    tvMoney += Number(currenVal*rate)
//                }
//            }
//        }
//        if(currentVal.indexOf('，') != -1){
//            var vList = currentVal.split('，');
//            var currenVal = 0;
//            for (var i = 0; i < vList.length; i++){
//                currenVal = vList[i];
//                if(checkIsNumber(currenVal)){
//                    tvMoney += Number(currenVal*rate);
//                }
//            }
//        }
//        if('vs' === type){
//            // 说明用户输入的数值没有特殊符号
//            if(tvMoney === 0){
//                currPageVMoney = currentVal;
//            }else{
//                currPageVMoney = tvMoney.toFixed(2);
//            }
//        }else if('nm' === type){
//            if(tvMoney === 0){
//                currPageNMoney = currentVal;
//            }else{
//                currPageNMoney = tvMoney.toFixed(2);
//            }
//        }

    // 都不包含
    if(val.indexOf(',') != -1 && val.indexOf('，') != -1){
        console.log("结果 ： "+checkIsNumber(val));
        if(checkIsNumber(val)){
            console.log("总的金额 ： "+totalMoney+" "+additiveNumber(totalMoney,Number(val)));
            totalMoney = additiveNumber(totalMoney,Number(val));
            console.log("计算的访客量和浏览量的总和 ： "+totalMoney);
        }else {
            return false;
        }
    }
    if(val.indexOf(',') != -1){
        // 此时说明检查 录入的关键词是否符合 存在逗号分隔
        if(wordName.indexOf(',') != -1 || wordName.indexOf('，') != -1){
            checkvsAndBvNum(',',val,falg);
        }else{
            checkFirstIsNum(',',val,falg);
        }
    }
    if(val.indexOf('，') != -1){
        if(wordName.indexOf(',') != -1 || wordName.indexOf('，') != -1){
//                arrayNumber = val.split('，');
//                for (i = 0; i < arrayNumber.length; i++) {
//                    console.log("当前数据c ："+arrayNumber[i]);
//                    if(!checkIsNumber(arrayNumber[i])){
//                        console.log(tips+'中文格式有误');
//                        falg == false
//                        return;
//                    }
//                }
            checkvsAndBvNum('，',val,falg);
        }else{
            checkFirstIsNum('，',val,falg);
        }
    }
    console.log("数据 ： "+falg);
    totalMoney = Number(totalMoney.pcRate).toFixed(2);
    console.log("当前计算的总的比率金额 ： "+totalMoney);
    return falg;
}

function  checkvsAndBvNum(ct,val,falg) {
    var arrayNumber = val.split(ct);
    for (i = 0; i < arrayNumber.length; i++) {
        console.log("当前数据获取数据 ："+arrayNumber[i]);
        if(checkIsNumber(arrayNumber[i])){
            totalMoney = additiveNumber(totalMoney,Number(arrayNumber[i]));
        }else{
            falg = false;
            return;
        }
    }
}
// 判断第一个参数的格式是否为数字
function  checkFirstIsNum(ct,val,falg) {
    var arrayNumber = val.split(ct);
    if(checkIsNumber(arrayNumber[0])){
        console.log("totalMoney "+Number(arrayNumber[i]+" 计算 ：" +
                " "+additiveNumber(totalMoney,Number(arrayNumber[i]))));
        totalMoney = additiveNumber(totalMoney,Number(arrayNumber[i]));
    }else{
        falg = false;
        return;
    }
}

// 宝贝链接校验
function changeGoodUrl(){
    var $thisGood = $("#"+clickDivId+" input[name='goodLinkUrl']");
    var goodLinkUrl = $thisGood.val();
    console.log("div :"+clickDivId+" 数据"+goodLinkUrl);
    // 数组
    if('' == goodLinkUrl || goodLinkUrl.length == 0){
        console.log("请输入关键词");
        return;
    }
    if(!isRightURL(goodLinkUrl)){
        alert("宝贝链接格式有误");
        $thisGood.val("");
        return;
    }
}
// 关键词校验
function changeWordName(){
    console.log("div id ： "+clickDivId)
    var $thisWord = $("#"+clickDivId+" input[name='wordList']");
    var wordList = $thisWord.val();
    console.log("关键词 ： "+wordList);
    // 数组
    if('' == wordList || wordList.length == 0){
        console.log("请输入关键词");
        return;
    }
}
// 校验访客量是否为空并计算金额
function changeVistor(){
    var $thisVistor = $("#"+clickDivId+" input[name='totalVisitor']");
    var totalVistor = $thisVistor.val().trim();
    // 数组
    if(totalVistor.length == 0){
        console.log("请输入访客量");
        return;
    }

    if(!checkValFormat(clickDivId,totalVistor,"访客量")){
        console.log("访客量格式有误");
        $thisVistor.val("");
        return;
    };
    // 计算访客量
    //calculateMoney(currentId,totalVistor,pcRate,"vs");
}
// $('#nmrw_total_visitor').on('blur',function () {
// });

function isPositiveInteger(s){//是否为正整数
    var re = /^[0-9]{1,2}$/ ;
    return re.test(s);
}

// 校验浏览量是否为空并计算金额
function changeTotalNumber(){
    var $thisNumber = $("#"+clickDivId+" input[name='totalNumber']");
    var totalNumber = $thisNumber.val().trim();
    // 数组
    if(totalNumber.length == 0){
        console.log("请输入浏览量");
        return;
    }
    if(!checkValFormat(clickDivId,totalNumber,"浏览量")){
        console.log("浏览量格式有误");
        $thisNumber.val("");
        return;
    };

    //calculateMoney(totalNumber,pcRate,"nm");
    //console.log("总的访客量 : "+currPageVMoney+" 浏览量 : "+currPageNMoney);
    // 总的价格
    //totalMoney = additiveNumber(currPageVMoney,currPageNMoney);
    console.log("总的价格 : "+totalMoney);
    // 当前实际折扣价
    var discount = totalMoney*parseFloat($('#discount').val());
    console.log("当前折扣价 : "+totalMoney);
    discount = discount.toFixed(2);
    console.log("折扣价格 ："+discount +" 总的价格 ： "+totalMoney);
    showVipRate('nmfw_last_tip',true,totalMoney,discount);

}
// $('#nmrw_total_number').on('blur',function () {
// });

// function checkWord(word){
//     var re = /[,，]/gi;
//     if (re.test(word)) {
//         return true;
//     }else {
//         return false;
//     }
// }
// 任务发布按钮事件
function savaTask(){
    alert('点击');
    var goodUrl = $("#pcll_nmrw input[name='goodLinkUrl']").val();
    var wordList = $("#pcll_nmrw input[name='wordList']").val();
    var totalVisitor = $("#pcll_nmrw input[name='totalVisitor']").val();
    var totalNumber = $("#pcll_nmrw input[name='totalNumber']").val();
    var taskTime = $("#pcll_nmrw input[name='taskTime']").val();
    var taskBeginHour = $("#pcll_nmrw input[name='taskBeginHour']").val();
    var taskEndHour = $("#pcll_nmrw input[name='taskEndHour']").val();
    var taskBeginMiunte = $("#pcll_nmrw input[name='taskBeginMiunte']").val();
    var taskEndMiunte = $("#pcll_nmrw input[name='taskEndMiunte']").val();
    var taskSearchScope = $('#pcll_nmrw input[name="taskSearchScope"]:checked').val();
    console.log("选择的搜索范围 : "+taskSearchScope);
    // 模板ID组合 多选框的值
    var templateId = '5,';
    $("#pcll_nmrw input[type='checkbox']").each(function () {
        if ($(this).is(":checked")) {
            if('on' === $(this).val()){
                return;
            }else{
                templateId +=$(this).val()+",";
                console.log($(this).val())
            }
        }
    });

    if(null != templateId && templateId != ''){
        templateId = templateId.substring(0,templateId.length);
    }

    // 任务保存
    saveTask(goodUrl,'1',wordList,totalVisitor,totalNumber,taskTime,taskBeginHour,taskEndHour,taskBeginMiunte,
        taskEndMiunte,taskSearchScope,templateId);
}

/*******聚划算业务*******/
// 宝贝链接校验
$('#jhsrw_good_url').on('blur',function () {
    var goodLinkUrl = $("#pcll_jhsrw input[name='goodLinkUrl']").val();
    // 数组
    if('' == goodLinkUrl || goodLinkUrl.length == 0){
        console.log("请输入关键词");
        return;
    }
    // 淘宝天猫链接 校验
    if(!isRightURL(goodLinkUrl)){
        console.log("宝贝链接格式有误");
        $("#pcll_jhsrw input[name='goodLinkUrl']").val("");
        return;
    }
});

// 任务数量校验
$('#jhsrw_task').on('blur',function () {
    var taskNumber = $("#pcll_jhsrw input[name='taskTotalNumber']").val();
    // 数组
    if('' == taskNumber || taskNumber.length == 0){
        console.log("请输入关键词");
        return;
    }
    if(!isPositiveInteger(taskNumber)){
        console.log("请输入任务量");
        return;
    }
});

// 聚划算发布任务
$('.jhsrw_fbrw').on('click',function () {
    var goodUrl = $("#pcll_jhsrw input[name='goodLinkUrl']").val();
    var wordList = $("#pcll_jhsrw input[name='taskTotalNumber']").val();
    // var totalVisitor = $("#pcll_jhsrw input[name='totalVisitor']").val();
    // var totalNumber = $("#pcll_jhsrw input[name='totalNumber']").val();
    var taskTime = $("#pcll_jhsrw input[name='taskTime']").val();
    var taskBeginHour = $("#pcll_jhsrw input[name='taskBeginHour']").val();
    var taskEndHour = $("#pcll_jhsrw input[name='taskEndHour']").val();
    var taskBeginMiunte = $("#pcll_jhsrw input[name='taskBeginMiunte']").val();
    var taskEndMiunte = $("#pcll_jhsrw input[name='taskEndMiunte']").val();
    // var taskSearchScope = $('#pcll_jhsrw input[name="taskSearchScope"]:checked').val();
    // console.log("选择的搜索范围 : "+taskSearchScope);

    // 模板ID组合 多选框的值
    var templateId = '5,';
    $("#pcll_jhsrw input[type='checkbox']").each(function () {
        if ($(this).is(":checked")) {
            if('on' === $(this).val()){
                return;
            }else{
                templateId +=$(this).val()+",";
                console.log($(this).val())
            }
        }
    });

    if(null != templateId && templateId != ''){
        templateId = templateId.substring(0,templateId.length);
    }

    var wordList = '';
    var totalVisitor = '';
    var totalNumber = '';
    var taskSearchScope = '';
    // 任务保存
    saveTask(goodUrl,'4',wordList,totalVisitor,totalNumber,taskTime,taskBeginHour,taskEndHour,taskBeginMiunte,
        taskEndMiunte,taskSearchScope,templateId);
});

function checkIsEmpty(val) {
    // if(typeof val=='string' && val.length == 0){
    //     console.log("参数为空请校验");
    //     return false;
    // }
    if('' == val || val.length == 0){
        console.log("参数为空请校验");
        return false;
    }
}
// 任务保存
function saveTask(goodUrl,taskType,wordList,totalVisitor,totalNumber,taskTime,taskBeginHour,taskEndHour,taskBeginMiunte,
                  taskEndMiunte,taskSearchScope,templateId) {

    console.log("taskBeginHour : "+taskBeginHour+" isNaN "+isNaN(taskBeginHour) +" 结果 "+isNaN("2@"));
    var bh = parseInt(taskBeginHour);
    var eh = parseInt(taskEndHour);
    // 公共参数校验 !isPositiveInteger(taskBeginHour)
    if(isNaN(bh)){
        console.log("开始时段为空或格式有误");
        return;
    }
    if(isNaN(eh)){
        console.log("结束时段为空或格式有误");
        return;
    }
    // todo 开始时段 小于结束时段
    var bm = parseInt(taskBeginMiunte);
    var em = parseInt(taskEndMiunte);
    if(isNaN(bm)){
        console.log("开始分钟为空或格式有误");
        return;
    }
    if(isNaN(em)){
        console.log("结束分钟为空或格式有误");
        return;
    }
    if( bm > em|| bm > 60 || em > 60 ){
        console.log("录入的分钟数有误,请检查");
        return;
    }
    var taskParams = {"customerId":customerId,"customerStateId":$('#customerStateId').val(),"goodLinkUrl":goodUrl,"taskType":taskType,"wordList":wordList,
        "totalVisitor":totalVisitor,"totalNumber":totalNumber,"taskTime":taskTime,"totalMoney":totalMoney,"accountMoney":$('#accountMoney').html().split('￥')[1],
        "taskBeginHour":taskBeginHour,"taskEndHour":taskEndHour,"taskBeginMiunte":taskBeginMiunte,
        "taskEndMiunte":taskEndMiunte,"taskSearchScope":taskSearchScope,"templateId":templateId};
    var jsonParams = JSON.stringify(taskParams);
    console.log("格式化的参数是 ： "+jsonParams);

    console.log("当前用户的ID ： "+customerId+ " 当前用户的token ： "+token);

    $.ajax({
        url:baseUrl+'task/saveCustomerTask.json',
        type:'POST',
        beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("token", token);
        },
        async:"true", //默认是true,异步
        dataType:"json",
        data:jsonParams,
        contentType:"application/json",
        success:function(result){
            console.log(result);
            if(result.code=="200"){

            }else if(result.code=="500"){
                alert("系统繁忙稍后再试");
            }else if(result.code == '401' || result.code == '402'){
                // token 失效 直接跳转登录页
                window.location.href = baseUrl+"page/login.html";
            }
        }
    });
}

/*PC端流量的任务栏弹框隐藏显示的js*/
$("#pc_btn1").click(function(){
    pcRate = 0.60;
    var  html = $('#pcll_nmrw');
    if(html.html() == "" || html.length == 1){
        if("pcll_nmrw" != clickDivId){
            $('#pcll_nmrw').html($('#'+clickDivId+'').html());
            // 将点击事件前的div内容清除
            $('#'+clickDivId+'').html("");
        }

    }
    $('#task_tips').html('PC匿名流量创建-基本信息');
    $("#pcll_nmrw").show();
    $("#pcll_smrw").hide();
    $("#pcll_ztcrw").hide();
    $("#pcll_jhsrw").hide();
    showVipRate('nmfw_last_tip',false,0,0);
    checkClickDivId('pcll_nmrw');
});

$("#pc_btn2").click(function(){
    pcRate = 0.60;
    var  html = $('#pcll_smrw');
    if(html.html() == "" || html.length == 1){
        if("pcll_smrw" != clickDivId){
            console.log("长度2 ："+html.length);
            $('#pcll_smrw').html($('#'+clickDivId+'').html());
            // 将点击事件前的div内容清除
            $('#'+clickDivId+'').html("");
            console.log("div点击： "+clickDivId);
        }
    }
    $('#task_tips').html('PC实名流量创建-基本信息');
    $("#pcll_smrw").show();
    $("#pcll_nmrw").hide();
    $("#pcll_ztcrw").hide();
    $("#pcll_jhsrw").hide();
    showVipRate('nmfw_last_tip',false,0,0);
    // 记录当前点击的div
    checkClickDivId('pcll_smrw');
});

$("#pc_btn3").click(function(){
    pcRate = 0.73;
    var  html = $('#pcll_ztcrw');
    if(html.html() == "" || html.length == 1){
        if("pcll_ztcrw" != clickDivId){
            console.log(" 3点击的div : "+clickDivId);
            $('#pcll_ztcrw').html($('#'+clickDivId+'').html());
            // 将点击事件前的div内容清除
            $('#'+clickDivId+'').html("");
        }
    }
    $('#task_tips').html('PC端直通车流量创建-基本信息');
    $("#pcll_ztcrw").show();
    $("#pcll_smrw").hide();
    $("#pcll_nmrw").hide();
    $("#pcll_jhsrw").hide();
    showVipRate('nmfw_last_tip',false,0,0);
    // 记录当前点击的div
    checkClickDivId('pcll_ztcrw');
});

// 聚划算点击事件
$("#pc_btn4").click(function(){
    showVipRate('jhsrw_last_tip',false,0,0);
    $("#pcll_jhsrw").show();
    $("#pcll_ztcrw").hide();
    $("#pcll_smrw").hide();
    $("#pcll_nmrw").hide();
});


/*手机端流量的任务栏弹框隐藏显示的js*/
$("#sj_btn1").click(function(){
    $("#sjll_nmrw").show();
    $("#sjll_smrw").hide();
    $("#sjll_ztcrw").hide();
    $("#sjll_tkl").hide();
});

$("#sj_btn2").click(function(){
    $("#sjll_smrw").show();
    $("#sjll_nmrw").hide();
    $("#sjll_tkl").hide();
    $("#sjll_tmapp").hide();
});

$("#sj_btn3").click(function(){
    $("#sjll_tmapp").show();
    $("#sjll_smrw").hide();
    $("#sjll_nmrw").hide();
    $("#sjll_tkl").hide();
});


/*其他流量的任务栏弹框隐藏显示的js*/
$("#qt_btn2").click(function(){
    $("#qtll_jdapp").show();
    $("#qtll_cnxh").hide();
    $("#qtll_sjztc").hide();
});

$("#qt_btn3").click(function(){
    $("#qtll_cnxh").show();
    $("#qtll_sjztc").hide();
    $("#qtll_jdapp").hide();
});

$("#qt_btn4").click(function(){
    $("#qtll_sjztc").show();
    $("#qtll_cnxh").hide();
    $("#qtll_jdapp").hide();
});

/*收藏的任务栏弹框隐藏显示的js*/
$("#scjg_pc_btn1").click(function(){
    $("#scjg_pc").show();
    $("#scjg_sj").hide();
});

$("#scjg_sj_btn2").click(function(){
    $("#scjg_sj").show();
    $("#scjg_pc").hide();
});

// 记录当前点击的div
function checkClickDivId(val){
    clickDivId = val;
}
