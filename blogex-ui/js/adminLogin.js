/**
 * 登录逻辑实现
 */

var xPos = '';
var codeUuid = '';
var encrypt = new JSEncrypt();

/*显示验证码*/
function createCode() {
    layui.use(['layer', 'jquery'], function () {
        let layer = layui.layer;
        let $ = layui.jquery;
        $.ajax({
            type: "GET",
            url: apiBaseUrl + "/verify/sliderCode",
            success: function (resp, status) {
                let resultData = resp.data;
                let respCode = resp.code;
                let message = resp.message;
                if (respCode == 200) {
                    codeUuid = resultData.codeUuid;
                    $(".code-front-img").attr('src', 'data:image/jpg;base64,' + resultData
                        .slidingImage);
                    $(".code-back-img").attr('src', 'data:image/png;base64,' + resultData
                        .originalImage);
                    /*console.log(data.yheight);*/
                    $(".code-mask").css("margin-top", resultData.yHeight + "px");
                    styleDealWith();
                } else if (respCode == 401 || respCode == 403 || respCode == 500) {
                    layer.closeAll();
                    layer.msg('获取验证码失败，msg：' + respCode + ":" + message, {icon: 5, time: 4000});
                } else {
                    console.log(resp);
                }
            },
            error: function (xhr, errorText, errorType) {
                if (xhr.status == 401) {
                    //do something
                }
                layer.closeAll();
                layer.msg('获取验证码失败，msg：' + xhr.status + ":" + errorText + ':' + errorType, {
                    icon: 5,
                    time: 4000 //2秒关闭（如果不配置，默认是3秒）
                });
            },
            complete: function () {

            }
        });
    });
}

/*刷新验证码*/
function refreshCallback() {
    createCode();
}

/*操作验证码信息  msg: 用户操作信息*/
function submitLogin() {
    let username = $("#inUserName").val();
    let password = $("#inPassWord").val();
    /*判断账号是否为空*/
    if (username === '') {
        layui.use('layer', function () {
            let layer = layui.layer;
            layer.alert('账号为空', {icon: 2, skin: 'layui-layer-molv', closeBtn: 0, anim: 6})
        });
        return;
    }
    /*判断密码*/
    if (password === '') {
        layui.use('layer', function () {
            let layer = layui.layer;
            layer.alert('密码为空', {icon: 2, skin: 'layui-layer-molv', closeBtn: 0, anim: 6})
        });
        return;
    }

    layui.use(['layer', 'jquery'], function () {
        let layer = layui.layer;
        let $ = layui.jquery;
        layer.alert(
            '<div id="imgscode">\n' +
            '      <div class="code-k-div">\n' +
            '        <div class="code_bg"></div>\n' +
            '        <div class="code-con">\n' +
            '          <div class="code-img">\n' +
            '            <div class="code-img-con">\n' +
            '              <div class="code-mask">\n' +
            '                <img class="code-front-img" src="">\n' +
            '              </div>\n' +
            '              <img class="code-back-img" src="">\n' +
            '            </div>\n' +
            '            <div class="code-push">\n' +
            '              <i class="icon-login-bg icon-w-25 icon-push">刷新</i>\n' +
            '              <span class="code-tip"></span>\n' +
            '            </div>\n' +
            '          </div>\n' +
            '          <div class="code-btn">\n' +
            '            <div class="code-btn-img code-btn-m"></div>\n' +
            '            <span class="code-span">按住滑块，拖动完成上方拼图</span>\n' +
            '          </div>\n' +
            '        </div>\n' +
            '      </div>\n' +
            '    </div>', {
                skin: "layui-layer-molv",
                closeBtn: 1,
                anim: 1,
                title: '二次验证',
                success: function () {
                    createCode();
                },
            },
            function (codeLayer) {
                /*判断验证码*/
                if (xPos === '') {
                    checkCodeResult(0, "滑块没有拖动！");
                    return;
                }
                layer.close(codeLayer);
                let loading = layer.msg('登录中...', {icon: 16, shade: 0.3});
                let rememberMe = $("#rememberMe").is(":checked");
                axiosGetFunction('/api/rsa/openApi/getRsaPublicKey', null, false).then((response) => {
                    if (response) {
                        encrypt.setPublicKey(response);
                        /*参数*/
                        let reqData = {
                            "codeUuid": codeUuid,
                            "verifyCode": xPos,
                            "username": encrypt.encrypt(username),
                            "password": encrypt.encrypt(password),
                            "remember": rememberMe
                        };
                        $.ajax({
                            type: 'POST',
                            url: apiBaseUrl + "/auth/login",
                            dataType: "json",
                            contentType: "application/json",
                            data: JSON.stringify(reqData),
                            success: function (response, status, xhr) {
                                let resultData = response.data;
                                let respCode = response.code;
                                let message = response.message;
                                layer.closeAll();
                                if (respCode == 200) {
                                    storeToken(resultData.token);
                                    storeUsername(resultData.username);
                                    location.href = "./adminIndex.html";
                                } else if (respCode == 400) {
                                    layer.msg(respCode + ":" + message, {icon: 5, time: 4000});
                                } else if (respCode == 401 || respCode == 403 || respCode == 500) {
                                    layer.msg('登录失败，msg：' + respCode + ":" + message, {icon: 5, time: 4000});
                                } else if (respCode == 402) {
                                    layer.msg('认证失败，msg：' + respCode + ":" + message, {icon: 5, time: 4000});
                                } else {
                                    console.log(response);
                                }
                            },
                            error: function (xhr, errorText, errorType) {
                                let resultDate = JSON.parse(xhr.responseText)
                                let msgText = resultDate.message;
                                if (undefined == msgText) {
                                    console.log(msgText);
                                    msgText = xhr.responseText;
                                }
                                console.log(msgText);
                                layer.closeAll();
                                layer.msg('登录失败，msg：' + msgText, {
                                    icon: 5,
                                    time: 4000 /*2秒关闭（如果不配置，默认是3秒）*/
                                });
                            }
                        });
                    }
                });
            }
        );
    });

}

/*样式处理*/
function styleDealWith() {
    let $this = $("#imgscode");
    /*定义拖动参数*/
    let $divMove = $($this).find(".code-btn-img"); /*拖动按钮*/
    let $divWrap = $($this).find(".code-btn"); /*鼠标可拖拽区域*/
    let mX = 0,
        mY = 0; /*定义鼠标X轴Y轴*/
    let dX = 0,
        dY = 0; /*定义滑动区域左、上位置*/
    let isDown = false; /*mousedown标记*/
    if (document.attachEvent) {
        /*ie的事件监听，拖拽div时禁止选中内容，firefox与chrome已在css中设置过-moz-user-select:
        none; -webkit-user-select:
        none;*/
        $divMove[0].attachEvent('onselectstart', function () {
            return false;
        });
    }

    /*按钮拖动事件*/
    $divMove.unbind('mousedown').on({
        mousedown: function (e) {
            /*清除提示信息*/
            $this.find(".code-tip").html("");
            var event = e || window.event;
            mX = event.pageX;
            dX = $divWrap.offset().left;
            dY = $divWrap.offset().top;
            isDown = true; /*鼠标拖拽启*/
            $($this).addClass("active");
            /*修改按钮阴影*/
            $divMove.css({
                "box-shadow": "0 0 8px #666"
            });
        }
    });

    /*按钮拖动事件*/
    $divMove.unbind('touchstart').on({
        touchstart: function (e) {
            /*清除提示信息*/
            $this.find(".code-tip").html("");
            var event = e || window.event;
            mX = event.pageX;
            dX = $divWrap.offset().left;
            dY = $divWrap.offset().top;
            isDown = true; /*鼠标拖拽启*/
            $($this).addClass("active");
            /*修改按钮阴影*/
            $divMove.css({
                "box-shadow": "0 0 8px #666"
            });
        }
    });

    /*点击背景关闭*/
    $this.find(".code_bg").unbind('click').click(
        function () {
            $this.html("");
        })

    /*刷新code码*/
    $this.find(".icon-push").unbind('click').click(function () {
        refreshCallback();
    });

    /*鼠标点击松手事件*/
    $divMove.unbind('mouseup').mouseup(function (e) {
        var lastX = $this.find(".code-mask").offset().left - dX - 1;
        isDown = false;
        /*鼠标拖拽启*/
        $divMove.removeClass("active");
        /*还原按钮阴影*/
        $divMove.css({
            "box-shadow": "0 0 3px #ccc"
        });
        xPos = lastX;
    });
    /*鼠标点击松手事件*/
    $divMove.unbind('touchend').mouseup(function (e) {
        var lastX = $this.find(".code-mask")
                .offset().left -
            dX - 1;
        isDown = false; /*鼠标拖拽启*/
        $divMove.removeClass("active");
        /*还原按钮阴影*/
        $divMove.css({
            "box-shadow": "0 0 3px #ccc"
        });
        xPos = lastX;
    });

    /*滑动事件*/
    $divWrap.mousemove(function (event) {
        var event = event || window.event;
        var x = event.pageX;
        /*鼠标滑动时的X轴*/
        if (isDown) {
            if (x > (dX + 30) &&
                x < dX + $(this).width() - 20) {
                $divMove.css({
                    "left": (x - dX - 20) + "px"
                });
                /*div动态位置赋值*/
                $this.find(".code-mask").css({
                    "left": (x - dX - 30) + "px"
                });
            }
        }
    });

    /*滑动事件*/
    $divWrap.on('touchmove', function (event) {
        var event = event || window.event;
        var touch = event.originalEvent.targetTouches[0];
        var x = event.pageX || touch.pageX; /*鼠标滑动时的X轴*/
        if (isDown) {
            if (x > (dX + 30) &&
                x < dX + $(this).width() - 20) {
                $divMove.css({
                    "left": (x - dX - 20) + "px"
                });
                /*div动态位置赋值*/
                $this.find(".code-mask").css({
                    "left": (x - dX - 30) + "px"
                });
            }
        }
    });

}

/*验证成功*/
/*function getSuccess() {
    checkCodeResult(1, "验证通过");
    setTimeout(function () {
        $("#imgscode").find(".code-k-div").remove();
    }, 800);
}*/

/*验证结果*/
function checkCodeResult(i, txt) {
    if (i === 0) {
        $("#imgscode").find(".code-tip").addClass("code-tip-red");
    } else {
        $("#imgscode").find(".code-tip").addClass("code-tip-green");
    }

    $("#imgscode").find(".code-tip").html(txt);
}

/*验证失败*/
/*function getFailure(txt) {

    var $divMove = $("#imgscode").find(".code-btn-img"); /!*拖动按钮*!/
    $divMove.addClass("error");
    checkCodeResult(0, txt);
    setTimeout(function () {
        $divMove.removeClass("error");
        $("#imgscode").find(".code-mask").animate({
            "left": "0px"
        }, 200);
        $divMove.animate({
            "left": "10px"
        }, 200);
    }, 400);
}*/

/*$(function () {
    createCode();
}());*/
