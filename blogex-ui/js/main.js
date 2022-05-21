const apiBaseUrl = "http://127.0.0.1:20010"

const axiosInstance = axios.create({
    baseURL: apiBaseUrl
});

function isEmptyStr(s) {
    if (s == undefined || s == null || s == '') {
        return true
    }
    return false
}

function axiosDownloadFunction(url, carryToken) {
    let token = getToken();
    let headerData = {timeout: 12000, "Access-Control-Allow-Origin": "*", responseType: "blob",}
    if (carryToken === true) {
        headerData.headers = {"Authorization": token};
    }
    axiosInstance.get(url, headerData).then(function (response) {
        let blob = new Blob([response.data], {
            type: response.data.type,
        });
        let downloadElement = document.createElement("a");
        let href = window.URL.createObjectURL(blob); /*创建下载链接*/
        let contentDisposition = response.headers["content-disposition"];
        console.log(contentDisposition);
        let fileName = contentDisposition ? contentDisposition.split(";")[1].split("=")[1] : new Date().getTime();
        downloadElement.href = href;
        downloadElement.download = decodeURIComponent(fileName); /*解码*/
        document.body.appendChild(downloadElement);
        downloadElement.click();
        document.body.removeChild(downloadElement);
        window.URL.revokeObjectURL(href); /*释放掉blob对象*/
    }).catch(function (err) {
        console.warn(err)
    });
}

async function axiosUploadFile(url, file, fileName) {
    let param = new FormData();
    let token = getToken();
    param.append('file', file, fileName);
    let config = {
        headers: {
            "Authorization": token,
            'Content-Type': 'multipart/form-data'
        },
        timeout: 1000 * 10,
    };
    let response = await axiosInstance.post(url, param, config);
    let respData = response.data.data;
    let respCode = response.data.code;
    let message = response.data.message;
    let showErrorMessage = null;
    if (respCode === 200) {
        return respData;
    } else if (respCode === 400) {
        showErrorMessage = "客户端错误：" + respCode + ":" + message;
    } else if (respCode === 401 || respCode === 403 || respCode === 500) {
        showErrorMessage = 'msg：' + respCode + ":" + message;
    } else if (respCode === 402) {
        showErrorMessage = '认证失败，msg：' + respCode + ":" + message;
    } else if (respCode === 302) {
        showErrorMessage = '登录信息过期，请重新登录，msg：' + respCode + ":" + message;
    } else {
        showErrorMessage = message;
        console.log(response);
    }
    if (showErrorMessage) {
        layer.msg(showErrorMessage, {icon: 5, time: 4000});
    }
    return null;
}

async function axiosPutFunction(url, queryBody, carryToken) {
    let token = getToken();
    let responseData = null;
    let headerData = {timeout: 12000}
    if (carryToken === true) {
        headerData.headers = {"Authorization": token};
    }
    let response = await axiosInstance.put(url, queryBody, headerData);
    let respData = response.data.data;
    let respCode = response.data.code;
    let message = response.data.message;
    let showErrorMessage = null;
    if (respCode === 200) {
        responseData = respData;
    } else if (respCode === 400) {
        showErrorMessage = "客户端错误：" + respCode + ":" + message;
    } else if (respCode === 401 || respCode === 403 || respCode === 500) {
        showErrorMessage = 'msg：' + respCode + ":" + message;
    } else if (respCode === 402) {
        showErrorMessage = '认证失败，msg：' + respCode + ":" + message;
    } else if (respCode === 302) {
        showErrorMessage = '登录信息过期，请重新登录，msg：' + respCode + ":" + message;
    } else {
        showErrorMessage = message;
        console.log(response);
    }
    if (showErrorMessage) {
        layer.msg(showErrorMessage, {icon: 5, time: 4000});
    }
    return responseData;
}

async function axiosPostFunction(url, queryBody, carryToken) {
    let token = getToken();
    let responseData = null;
    let headerData = {timeout: 12000}
    if (carryToken === true) {
        headerData.headers = {"Authorization": token};
    }
    let response = await axiosInstance.post(url, queryBody, headerData);
    let respData = response.data.data;
    let respCode = response.data.code;
    let message = response.data.message;
    let showErrorMessage = null;
    if (respCode === 200) {
        responseData = respData;
    } else if (respCode === 400) {
        showErrorMessage = "客户端错误：" + respCode + ":" + message;
    } else if (respCode === 401 || respCode === 403 || respCode === 500) {
        showErrorMessage = 'msg：' + respCode + ":" + message;
    } else if (respCode === 402) {
        showErrorMessage = '认证失败，msg：' + respCode + ":" + message;
    } else if (respCode === 302) {
        showErrorMessage = '登录信息过期，请重新登录，msg：' + respCode + ":" + message;
    } else {
        showErrorMessage = message;
        console.log(response);
    }
    if (showErrorMessage) {
        layer.msg(showErrorMessage, {icon: 5, time: 4000});
    }
    return responseData;
}

async function axiosGetFunction(url, params, carryToken) {
    let token = getToken();
    let headerData = {timeout: 12000}
    if (params) {
        headerData.params = params;
    }
    if (carryToken === true) {
        headerData.headers = {"Authorization": token};
    }
    let response = await axiosInstance.get(url, headerData);
    let respData = response.data.data;
    let respCode = response.data.code;
    let message = response.data.message;
    let showErrorMessage = null;
    if (respCode === 200) {
        return respData;
    } else if (respCode === 400) {
        showErrorMessage = "客户端错误：" + respCode + ":" + message;
    } else if (respCode === 401 || respCode === 403 || respCode === 500) {
        showErrorMessage = 'msg：' + respCode + ":" + message;
    } else if (respCode === 402) {
        showErrorMessage = '认证失败，msg：' + respCode + ":" + message;
    } else if (respCode === 302) {
        showErrorMessage = 'msg：' + respCode + ":" + message;
    } else {
        showErrorMessage = message;
        console.log(response);
    }
    if (showErrorMessage) {
        layer.msg(showErrorMessage, {icon: 5, time: 4000});
    }
    return null;
}

async function axiosDeleteFunction(url, queryBody, carryToken) {
    let token = getToken();
    let headerData = {timeout: 12000}
    if (carryToken === true) {
        headerData.headers = {"Authorization": token};
    }
    if (queryBody) {
        headerData.data = queryBody;
    }
    let response = await axiosInstance.delete(url, headerData);
    let respData = response.data.data;
    let respCode = response.data.code;
    let message = response.data.message;
    let showErrorMessage = null;
    if (respCode === 200) {
        return respData;
    } else if (respCode === 400) {
        showErrorMessage = "客户端错误：" + respCode + ":" + message;
    } else if (respCode === 401 || respCode === 403 || respCode === 500) {
        showErrorMessage = 'msg：' + respCode + ":" + message;
    } else if (respCode === 402) {
        showErrorMessage = '认证失败，msg：' + respCode + ":" + message;
    } else if (respCode === 302) {
        showErrorMessage = 'msg：' + respCode + ":" + message;
    } else {
        showErrorMessage = message;
        console.log(response);
    }
    if (showErrorMessage) {
        layer.msg(showErrorMessage, {icon: 5, time: 4000});
    }
    return null;
}

Date.prototype.format = function (format) {
    /*eg:format="yyyy-MM-dd hh:mm:ss";*/
    if (!format) {
        format = "yyyy-MM-dd hh:mm:ss";
    }
    let o = {
        "M+": this.getMonth() + 1,  /*month*/
        "d+": this.getDate(),       /*day*/
        "H+": this.getHours(),      /*hour*/
        "h+": this.getHours(),      /*hour*/
        "m+": this.getMinutes(),    /*minute*/
        "s+": this.getSeconds(),    /*second*/
        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
        "S": this.getMilliseconds()
    };
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "")
            .substr(4 - RegExp.$1.length));
    }
    for (let k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1
                ? o[k]
                : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};

function getToken() {
    return localStorage.getItem("Authorization");
}

function storeToken(token) {
    localStorage.setItem("Authorization", token);
}

function deleteToken() {
    localStorage.removeItem("Authorization");
}


function getParams(params) {
    let url = location.href;
    let paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");
    let paraObj = {};
    let j;
    for (let i = 0; j = paraString[i]; i++) {
        paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=") + 1, j.length);
    }
    let returnValue = paraObj[params.toLowerCase()];
    if (typeof (returnValue) == "undefined") {
        return "";
    } else {
        return returnValue;
    }
}

function scrollArticle() {
    window.addEventListener('scroll', this.scrollHandle, true);
    $("html, body").scrollTop(1); /*模拟鼠标滚动，触发鼠标中键滚动监听事件，让文章显示旋转特效*/
}

function scrollHandle() {
    /*滚动条高度+视窗高度 = 可见区域底部高度*/
    let visibleBottom = window.scrollY + document.documentElement.clientHeight;
    /*可见区域顶部高度*/
    let visibleTop = window.scrollY;
    $('article').each(function () {
        let $node = $(this);
        let centerY = $node.offset().top;
        if (centerY > visibleTop && centerY < visibleBottom) {
            $node.addClass('item--active');
        } else {
            $node.removeClass('item--active');
        }
    })
}

function isMobile() {
    let browser = {
        versions: function () {
            let u = navigator.userAgent,
                app = navigator.appVersion;
            return { /*移动终端浏览器版本信息*/
                trident: u.indexOf('Trident') > -1, /*IE内核*/
                presto: u.indexOf('Presto') > -1, /*opera内核*/
                webKit: u.indexOf('AppleWebKit') > -1, /*苹果、谷歌内核*/
                gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
                mobile: !!u.match(/AppleWebKit.*Mobile.*/) || !!u.match(/AppleWebKit/), //是否为移动终端
                ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), /*ios终端*/
                android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, /*android终端或者uc浏览器*/
                iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, /*是否为iPhone或者QQHD浏览器*/
                iPad: u.indexOf('iPad') > -1, /*是否iPad 允许ipad加载*/
                webApp: u.indexOf('Safari') == -1 /*是否web应该程序，没有头部与底部*/
            };
        }(),
        language: (navigator.browserLanguage || navigator.language).toLowerCase()
    };
    let flag = false;
    /*1.是否mobile，否，肯定不是。*/
    if (browser.versions.mobile) {
        /*2.是否ios或android终端，有一个是*/
        if (browser.versions.android || browser.versions.ios) {
            flag = true;
        }

    }
    return flag;
}

function getExplorer() {
    let explorer = window.navigator.userAgent;
    if (explorer.indexOf("MSIE") >= 0) {
        return "IE";
    }
    else if (explorer.indexOf("Firefox") >= 0) {
        return "Firefox";
    }
    else if (explorer.indexOf("Chrome") >= 0) {
        return "Chrome";
    }
    else if (explorer.indexOf("Opera") >= 0) {
        return "Opera";
    }
    else if (explorer.indexOf("Safari") >= 0) {
        return "Safari";
    }
}
