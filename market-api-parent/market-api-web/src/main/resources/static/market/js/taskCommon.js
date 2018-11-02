 $(function () {
    // 加载数据
    // $.ajax({
    //     url:baseUrl+'task/queryCustomerMsg.json?customerId='+customerId,
    //     type:'GET',
    //     beforeSend: function (XMLHttpRequest) {
    //         XMLHttpRequest.setRequestHeader("token", token);
    //     },
    //     async:"true", //默认是true,异步
    //     dataType:"json",
    //     contentType:"application/json",
    //     success:function(result){
    //         console.log(result);
    //         if(result.code=="200"){
    //             console.log(result.data.customerState);
    //             $("#topCustomerPhone").html(result.data.phone+"&nbsp;");
    //             $("#customerPhone").html(result.data.phone+"，欢迎您");
    //             $("#accountMoney").html("￥"+result.data.customerState.totalMoney);
    //             $("#levelName").html(result.data.customerLevel.levelName);
    //             $("#expiredTime").html("到期时间："+result.data.customerState.expiredTime);
    //             $("#completeTask").html(result.data.taskIsComplete);
    //             $("#taskIsDoing").html(result.data.taskIsDoing);
    //             // 当前用户的折扣率
    //             $('#discount').val(result.data.customerState.discount);
    //             $('#customerStateId').val(result.data.customerState.customerStateId);
    //             //showVipRate('nmfw_last_tip',false,0,0);
    //         }else if(result.code=="500"){
    //             alert("系统繁忙稍后再试");
    //         }else if(result.code == '401' || result.code == '402'){
    //             // token 失效 直接跳转登录页
    //             window.location.href = baseUrl+"page/login.html";
    //         }
    //     }
    // });

    /*PC端流量的任务栏弹框隐藏显示的js*/
    $("#pc_btn1").click(function(){
        pcRate = 0.60;
        var  html = $('#pcll_nmrw');
        if(html.html() == "" || html.length == 1){
            // 将点击事件前的div内容清除
            $('#pcll_nmrw').html($('#pc_rwfb_div').html());
        }
        $("#pcll_smrw").html("");
        $("#pcll_ztcrw").html("");
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
            console.log("html1 : "+$('#pc_rwfb_div').html());
            // 将点击事件前的div内容清除
            $('#pcll_smrw').html($('#pc_rwfb_div').html());
        }
        $("#pcll_nmrw").html("");
        $("#pcll_ztcrw").html("");
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
            // 将点击事件前的div内容清除
            $('#pcll_ztcrw').html($('#pc_rwfb_div').html());
        }
        $("#pcll_nmrw").html("");
        $("#pcll_smrw").html("");
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

    // 记录当前点击的div
    function checkClickDivId(val){
        clickDivId = val;
    }

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

    // 计算的总的访客量金额,总的浏览量金额,总的金额,发布任务单价
    var currPageVMoney = 0.00;
    var currPageNMoney = 0.00;
    var totalMoney = 0.00;
    var pcRate = 0.60;

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
        if(val.indexOf(',') != -1 && val.indexOf('，') != -1){
            falg == false
            return;
        }
        if(val.indexOf(',') != -1){
            // 此时说明检查 录入的关键词是否符合 存在逗号分隔
            if(wordName.indexOf(',') != -1 || wordName.indexOf('，') != -1){
                arrayNumber = val.split(',');
                for (i = 0; i < arrayNumber.length; i++) {
                    console.log("当前数据u ："+arrayNumber[i]);
                    if(!checkIsNumber(arrayNumber[i])){
                        alert(tips+'英文格式有误');
                        falg == false;
                        return;
                    }
                }
            }else{
                falg == false;
            }
        }

        if(val.indexOf('，') != -1){
            if(wordName.indexOf(',') != -1 || wordName.indexOf('，') != -1){
                arrayNumber = val.split('，');
                for (i = 0; i < arrayNumber.length; i++) {
                    console.log("当前数据c ："+arrayNumber[i]);
                    if(!checkIsNumber(arrayNumber[i])){
                        console.log(tips+'中文格式有误');
                        falg == false
                        return;
                    }
                }
            }else{
                falg == false;
            }
        }
        if(falg){
            if(!checkIsNumber(val)){
                console.log("当前参数格式有误");
                return false;
            }else {
                return true;
            }
        }
    }
    // 宝贝链接校验
    $('#nmrw_good_url').on('blur',function () {
        var goodLinkUrl = $("#pcll_nmrw input[name='goodLinkUrl']").val();
        // 数组
        if('' == goodLinkUrl || goodLinkUrl.length == 0){
            console.log("请输入关键词");
            return;
        }
        if(!isRightURL(goodLinkUrl)){
            console.log("宝贝链接格式有误");
            $("#pcll_nmrw input[name='goodLinkUrl']").val("");
            return;
        }
    });

    // 关键词校验
    $('#nmrw_word_name').on('blur',function () {
        var wordList = $("#pcll_nmrw input[name='wordList']").val();
        // 数组
        if('' == wordList || wordList.length == 0){
            console.log("请输入关键词");
            return;
        }
    });

    // 校验访客量是否为空并计算金额
    $('#nmrw_total_visitor').on('blur',function () {
        var totalVisitor = $("#pcll_nmrw input[name='totalVisitor']").val();
        // 数组
        if('' == totalVisitor && totalVisitor.length == 0){
            console.log("请输入访客量");
            return;
        }

        if(!checkValFormat("pcll_nmrw",totalVisitor.trim(),"访客量")){
            console.log("访客量格式有误");
            $("#pcll_nmrw input[name='totalVisitor']").val("");
            return;
        };
        // 计算访客量
        calculateMoney(totalVisitor.trim(),pcRate,"vs");
    });

    function isPositiveInteger(s){//是否为正整数
        var re = /^[0-9]{1,2}$/ ;
        return re.test(s)
    }

    // 校验浏览量是否为空并计算金额
    $('#nmrw_total_number').on('blur',function () {
        var totalNumber = $("#pcll_nmrw input[name='totalNumber']").val();
        // 数组
        if('' === totalNumber && totalNumber.length == 0){
            console.log("请输入浏览量");
            return;
        }
        if(!checkValFormat("pcll_nmrw",totalNumber.trim(),"浏览量")){
            console.log("浏览量格式有误");
            $("#pcll_nmrw input[name='totalNumber']").val("");
            return;
        };

        calculateMoney(totalNumber.trim(),pcRate,"nm");
        console.log("总的访客量 : "+currPageVMoney+" 浏览量 : "+currPageNMoney);
        // 总的价格
        totalMoney = additiveNumber(currPageVMoney,currPageNMoney);
        console.log("总的价格 : "+totalMoney);
        // 当前实际折扣价
        var discount = totalMoney*parseFloat($('#discount').val());
        console.log("当前折扣价 : "+totalMoney);
        discount = discount.toFixed(2);
        console.log("折扣价格 ："+discount +" 总的价格 ： "+totalMoney);
        showVipRate('nmfw_last_tip',true,totalMoney,discount);
    });

    // function checkWord(word){
    //     var re = /[,，]/gi;
    //     if (re.test(word)) {
    //         return true;
    //     }else {
    //         return false;
    //     }
    // }
    // 任务发布按钮事件
    $('.nmrw_fbrw').on('click',function () {
        alert('点击了');
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
    });

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
        if('' == val || val.length == 0){
            console.log("参数为空请校验");
            return false;
        }
    }
    // 任务保存
    function saveTask(goodUrl,taskType,wordList,totalVisitor,totalNumber,taskTime,taskBeginHour,taskEndHour,taskBeginMiunte,
                      taskEndMiunte,taskSearchScope,templateId) {
        // if(!checkIsEmpty(taskTime) || !checkIsEmpty(taskBeginHour) || !checkIsEmpty(taskEndHour) || !checkIsEmpty(taskBeginMiunte)
        //    || !checkIsEmpty(taskEndMiunte) || !checkIsEmpty(taskSearchScope) || !checkIsEmpty(templateId)){
        //     console.log("参数为空请校验");
        //     return;
        // }

        // 公共参数校验
        if(!checkIsEmpty(taskBeginHour) || !isPositiveInteger(taskBeginHour)){
            console.log("开始时段为空或格式有误");
            return;
        }
        if(!checkIsEmpty(taskEndHour) || !isPositiveInteger(taskEndHour)){
            console.log("结束时段为空或格式有误");
            return;
        }
        // todo 开始时段 小于结束时段

        if(!checkIsEmpty(taskBeginMiunte) || !isPositiveInteger(taskBeginMiunte)){
            console.log("开始分钟为空或格式有误");
            return;
        }
        if(!checkIsEmpty(taskEndMiunte) || !isPositiveInteger(taskEndMiunte)){
            console.log("结束分钟为空或格式有误");
            return;
        }
        var bm = parseInt(taskBeginMiunte);
        var em = parseInt(taskEndMiunte);
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
 });