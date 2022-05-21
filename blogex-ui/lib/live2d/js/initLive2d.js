var currIndex = -1;
var live2dInfoList = [];
var playingAudio = false;
const app = new PIXI.Application({
    view: document.getElementById('live2d'),
    autoStart: true,
    width: 250,
    height: 250,
    transparent: true, /*画布透明*/
});

function canvasToImageUpload() {
    let base64Img = app.renderer.plugins.extract.base64(app.stage);
    /**返回截图路径  截图是base64的**/
    let blob = base64ToBlob(base64Img, 'image/png');
    let downloadElement = document.createElement("a");
    let href = window.URL.createObjectURL(blob); /*创建下载链接*/
    let fileName = new Date().getTime() + "_live2d.png";
    downloadElement.href = href;
    downloadElement.download = decodeURIComponent(fileName); /*解码*/
    document.body.appendChild(downloadElement);
    downloadElement.click();
    document.body.removeChild(downloadElement);
    window.URL.revokeObjectURL(href); /*释放掉blob对象*/
}

function base64ToBlob(urlData, type) {
    let arr = urlData.split(',');
    let mime = arr[0].match(/:(.*?);/)[1] || type;
    // 去掉url的头，并转化为byte
    let bytes = window.atob(arr[1]);
    // 处理异常,将ascii码小于0的转换为大于0
    let ab = new ArrayBuffer(bytes.length);
    // 生成视图（直接针对内存）：8位无符号整数，长度1个字节
    let ia = new Uint8Array(ab);
    for (let i = 0; i < bytes.length; i++) {
        ia[i] = bytes.charCodeAt(i);
    }
    return new Blob([ab], {
        type: mime
    });
}

$('.live2d-tool .fui-photo').click(function () {
    showMessage('照好了嘛，是不是很可爱呢？', 5000, true);
    canvasToImageUpload();
});

function showMessage(text, timeout) {
    if (Array.isArray(text)) text = text[Math.floor(Math.random() * text.length + 1) - 1];
    let live2dTips = $('#live2d-tips');
    live2dTips.stop();
    live2dTips.html(text).fadeTo(200, 1);
    if (timeout === null) timeout = 5000;
    hideMessage(timeout);
}

function hideMessage(timeout) {
    let live2dTips = $('#live2d-tips');
    live2dTips.stop().css('opacity', 1);
    if (timeout === null) timeout = 5000;
    live2dTips.delay(timeout).fadeTo(200, 0);
}

/**
 * 销毁当前正在演示的模型，防止内存泄露
 */
function destroyCurrentModel() {
    /**
     * 单纯的移除容器里面的孩子不销毁它就有可能会发生内存泄露，所以在项目中调用removeChildren方法时，
     * 推荐把移除的孩子也销毁。销毁它很简单，只需要给移除的孩子也调用destroy方法即可
     */
    let removedChildren = app.stage.removeChildren(0, app.stage.children.length);
    removedChildren.forEach((c) => {
        c.destroy({
            children: true,
            texture: false,
            baseTexture: false,
        });
    });
}

window.onbeforeunload = () => {
    destroyCurrentModel();
    /**在离开页面时需要手动清理内存，否则无法释放WebGL内存**/
    app.destroy(true);
};

app.view.addEventListener('pointerdown', event => {
    /*点击的时候触发随机表情*/
    if (app.stage.children.length > 0) {
        let model = app.stage.children[0];
        if (model.internalModel.motionManager.motionGroups) {
            let keys = Object.keys(model.internalModel.motionManager.motionGroups);
            let keyItem = keys[Math.floor(Math.random() * keys.length)];
            if (keyItem == 'emotion') {
                return;
            }
            model.motion(keyItem);
            showRandomMessage();
        }
    }
});

async function runModel(live2dInfo) {
    destroyCurrentModel();
    const model = await PIXI.live2d.Live2DModel.from(live2dInfo.modelJsonPath, {
        onError: console.warn
    });
    app.stage.addChild(model);
    model.expression(0);
    model.x = live2dInfo.x;
    model.y = live2dInfo.y;
    model.scale.set(live2dInfo.scale);
    model.anchor.set(live2dInfo.anchorx, live2dInfo.anchory);
    /**锚点，以画布中心下方为中心点,x，y（单位：倍）**/
    model.internalModel.motionManager.on('motionStart', (group, index, audio) => {
        /**console.log(group);
         console.log(index);**/
        if (audio) {
            playingAudio = true;
            try {
                /*如果模型的json文件里面有存有音频的译文，将它展示出来*/
                let motionItem = model.internalModel.settings.json.FileReferences.Motions[group][index];
                if (undefined != motionItem && null != motionItem && undefined != motionItem['Text']) {
                    showMessage(motionItem['Text'], 8000);
                }
            } catch (error) {
                console.warn(error);
            }
            audio.addEventListener('ended', () => {playingAudio =false;});
        }
    });
    let backgroundPath = live2dInfo.backgroundPath;
    let landlordSelector = $("#landlord");
    if (undefined != backgroundPath && null != backgroundPath && '' != backgroundPath) {
        landlordSelector.css({"background-image": "url('" + backgroundPath + "')"});
        landlordSelector.addClass("bgDiv");
    } else {
        landlordSelector.css({"background-image": ""});
        landlordSelector.removeClass("bgDiv");
    }
}

$('.fui-user').click(function() {
    changeModel();/*切换模型*/
});

$('.live2d-tool .fui-chat').click(function() {
    showRandomMessage();/*随机说一段话*/
});

$('.live2d-tool .fui-cross').click(function() {
    destroyCurrentModel();
    /**在离开页面时需要手动清理内存，否则无法释放WebGL内存**/
    app.destroy(true);
    $('#landlord').css('display','none');
});

function showRandomMessage() {
    if (playingAudio){
        return;
    }
    let messageText = messageJson[Math.floor(Math.random() * messageJson.length)].text;
    showMessage(messageText, 5000);
}

function changeModel() {
    currIndex = currIndex + 1;
    if (currIndex >= live2dInfoList.length) {
        currIndex = 0;
    }
    let live2dInfo = live2dInfoList[currIndex];
    destroyCurrentModel();
    runModel(live2dInfo);
}
function initModel(){
    axiosGetFunction('/uploads/live2d/openApi/getRandomTenList',null,false).then((response)=>{
        if (response){
            live2dInfoList = response;
            changeModel();
        }
    });
    axiosGetFunction('/uploads/live2d/openApi/getModelMessage',null,false).then((response)=>{
        if (response){
            messageJson = JSON.parse(response);
            /*$('#live2d').click(()=>{
                showRandomMessage();
            });*/
        }
    });
}

(function() {
    let text;
    let now = (new Date()).getHours();
    if (now > 23 || now <= 5) {
        text = '你是夜猫子呀？这么晚还不睡觉，明天起的来嘛？';
    } else if (now > 5 && now <= 7) {
        text = '早上好！一日之计在于晨，美好的一天就要开始了！';
    } else if (now > 7 && now <= 11) {
        text = '上午好！工作顺利嘛，不要久坐，多起来走动走动哦！';
    } else if (now > 11 && now <= 14) {
        text = '中午了，工作了一个上午，现在是午餐时间！';
    } else if (now > 14 && now <= 17) {
        text = '午后很容易犯困呢，今天的运动目标完成了吗？';
    } else if (now > 17 && now <= 19) {
        text = '傍晚了！窗外夕阳的景色很美丽呢，最美不过夕阳红~~';
    } else if (now > 19 && now <= 21) {
        text = '晚上好，今天过得怎么样？';
    } else if (now > 21 && now <= 23) {
        text = '已经这么晚了呀，早点休息吧，晚安~~';
    } else {
        text = '嗨~ 快来逗我玩吧！';
    }
    showMessage(text, 12000);
})();