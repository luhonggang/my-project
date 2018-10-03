$(function () {
    $('.selectUser').click(function () {
        $(this).toggleClass('domain')
    });
    $('.rememberPassword').click(function () {
        $(this).toggleClass('selected')
    });
    generateHtml();
    $('.verificationCode').click(function () {
        generateHtml();
    });

    $('.loginBtn').click(function () {
        var code = '';
        var spanArr = $('.verificationCode').find('span');
        var isRememberPassword = $('.rememberPassword').hasClass('selected');
        for(var i =0; i<spanArr.length; i++){
            var item = spanArr[i];
            code += $(item).html()
        }
        var iniputCode = $('#code').val();

        if(code.toUpperCase() !== iniputCode.toUpperCase()){
            generateHtml();
            $('.tip').html('验证码错误！').fadeOut().fadeIn();
            return false;
        }

        var parms = {};
        parms.ssoId = $.trim($('#ssoId').val());
        parms.password = $.trim($('#password').val());
        if($('.selectUser').hasClass('domain')){
            parms.userType = 'domain';
            Cookies.set('isDomain', 'true', { expires: 7 });
        } else {
            Cookies.remove('isDomain');
        }
        var queruUrl = baseUrl + 'login';
        $this.requestHttp(queruUrl,parms,function (res) {
            if(res.code === 200){
                if(isRememberPassword){
                    var ssoId = $.base64.encode(parms.ssoId);
                    var password = $.base64.encode(parms.password);
                    Cookies.set('ssoId', ssoId, { expires: 7 });
                    Cookies.set('password', password, { expires: 7 });
                    $this.Message.success(res.message);
                } else {
                    Cookies.remove('ssoId');
                    Cookies.remove('password');
                }
                // Cookies.set('TOKEN', res.data.token, { expires: 0.2 });
                $this.localstorage.set('TOKEN',res.data.token);
                $this.localstorage.set('USER',res.data.name);
                $this.localstorage.set('domainName',res.data.domainName);
                if(res.menu){
                    $this.localstorage.set('ziyunPortalMenu',JSON.stringify(res.menu));
                }
                $('.tip').hide();
                window.location.href = 'index';
            } else if (res.code ===  402 || res.code ===  404) {
                $this.Message.warning(res.message);
                $('.tip').html(res.message).fadeOut().fadeIn();
            } else {
                $this.Message.warning(res.message);
            }
        },'post')
    });

    document.onkeyup = function (event) {
        var e = event || window.event;
        var keyCode = e.keyCode || e.which;
        if(keyCode === 13){
            $(".loginBtn").click();
        }
    };


    writeUserInfo();
    function writeUserInfo() {
        /*
         * 从cookie中获取账号信息
         */
        var ssoId = Cookies.getJSON('ssoId');
        if(!ssoId){
            return false;
        }
        ssoId = $.base64.decode(ssoId);
        var password = $.base64.decode(Cookies.getJSON('password'));
        var isDomain = Cookies.getJSON('isDomain');
        if(isDomain === true || isDomain === 'true'){
            $('.selectUser').addClass('domain')
        }
        $('#ssoId').val(ssoId);
        $('#password').val(password);
    }

    /*
     * 生成验证码
     */
    function generateHtml(){
        var code = generateCode();
        var html = '';
        for(var i =0; i<4;i++){
            var r = Math.floor(Math.random()*250);
            var g = Math.floor(Math.random()*250);
            var b = Math.floor(Math.random()*250);
            var rote;
            if(Math.random()*10 > 5){
                rote = parseInt(Math.random()*30);
            } else {
                rote = parseInt(Math.random()*30) * (-1);
            }
            html += '<span style="color:rgb('+r+','+g+','+b+');transform: rotate('+rote+'deg);">'+ code[i] +'</span>';
        }
        $('.verificationCode').html(html);
    }

    /*
     * 生成验证码
     */
    function generateCode() {
        var codeArr = ['2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','k','m','n','p','q','r','s','t','u','v','w','x','y','z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];
        var code = [];
        for(var i=0; i<4;i++){
            var num = Math.floor(Math.random()*53);
            code.push(codeArr[num]);
        }
        return code;
    }
});