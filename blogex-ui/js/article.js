const vueObject = new Vue({
    el: "#pjax",
    data: {
        overview: {
            "sumArticle": 0,
            "sumComment": 0,
            "sumSupport": 0,
            "sumView": 0
        },
        blogger: {
            id: 1,
            avatarUrl: "",
            userName: "",
            bloggerDetail: "",
            githubUrl: "",
            recordNumber: "",
            signature: "",
            createTime: new Date(),
            updateTime: new Date()
        },
        articleRandomList: [],
        commentList: [],
        classifyList: [],
        codePic: "./images/verifyCodeImg.png",
        isAlreadySupport: false,
        mailRe: /^\w+@[a-zA-Z0-9]{2,10}(?:\.[a-z]{2,4}){1,3}$/,
        tempComment: {
            "articleId": 0,
            "parentId": null,
            "commentContent": "",
            "pageType": 0,
            "userName": "",
            "userEmail": ""
        },
        articleItem: {
            "articleStatus": 0,
            "classifyId": 0,
            "classifyName": "",
            "commentStatus": 0,
            "content": "",
            "contentType": 0,
            "createTime": new Date(),
            "id": 0,
            "postType": 0,
            "priority": 0,
            "reviewImgUrl": "",
            "snippet": "",
            "sumComment": 0,
            "supportCount": 0,
            "tagList": [
                {
                    "createTime": new Date(),
                    "id": 0,
                    "tagName": "",
                    "updateTime": new Date()
                }
            ],
            "title": "",
            "updateTime": "",
            "viewCount": 0
        },
        commentPageQuery: {
            "articleId": 0,
            "pageType": 0,
            "pageNo": 1,
            "pageSize": 10
        },
        codeUuid: "",
    },
    mounted() {
        let articleId = getParams("articleId");
        if (articleId !== "") {
            articleId = parseInt(articleId, 10);
            this.articleItem.id = articleId;
            this.tempComment.articleId = articleId;
            axiosGetFunction('/api/article/openApi/increase/viewCount/' + articleId, null, false).then((response) => {});
            axiosPutFunction('/api/count/openApi/increase', null, false).then((response) => {});
            this.getPageCommentList(articleId);
            axiosGetFunction('/api/bloggerInfo/openApi/getBlogInfo', null, false).then((response) => {
                if (response) {
                    this.blogger = response;
                    $("#bloggerIcon").attr("href", this.blogger.avatarUrl);
                    let header1_HeaderTitle = $("#Header1_HeaderTitle");
                    header1_HeaderTitle.html(header1_HeaderTitle.html() + "  " + this.blogger.userName + "的个人博客");
                    $(".github-a").attr('href', this.blogger.githubUrl);
                    $("#footer_a").html(this.blogger.recordNumber);
                }
            });
            axiosGetFunction('/api/article/openApi/blog/overview', null, false).then((response) => {
                if (response) {
                    this.overview = response;
                }
            });
            axiosGetFunction('/api/classify/openApi/queryAll', null, false).then((response) => {
                if (response) {
                    this.classifyList = response;
                }
            });
            axiosGetFunction('/api/article/openApi/random/ten', null, false).then((response) => {
                if (response) {
                    this.articleRandomList = response;
                }
            });
            axiosGetFunction('/api/blogMusic/openApi/selectAll', null, false).then((response) => {
                if (response) {
                    let songList = [];
                    for (let i = 0; i < response.length; i++) {
                        let responseItem = response[i];
                        songList.push(
                            {
                                name: responseItem.musicName,
                                artist: responseItem.musicArtist,
                                url: responseItem.musicUrl,
                                cover: responseItem.musicCover,
                                lrc: responseItem.musicLrc,
                            }
                        );
                    }
                    const ap = new APlayer({
                        container: document.getElementById("aplayer"),
                        theme: '#00b5ad',
                        preload: 'none',
                        lrcType: 1,
                        audio: songList
                    });
                }
            });

            axiosGetFunction('/api/article/openApi/' + articleId, null, false).then((response) => {
                if (response) {
                    this.articleItem.createTime = new Date(response.createTime);
                    this.articleItem.title = response.title;
                    document.title = this.articleItem.title;
                    this.articleItem.classifyName = response.classifyName;
                    this.articleItem.viewCount = response.viewCount;
                    this.articleItem.supportCount = response.supportCount;
                    this.articleItem.tagList = response.tagList;
                    this.articleItem.sumComment = response.sumComment;
                    this.articleItem.contentType = response.contentType;
                    if (this.articleItem.contentType === 1) {
                        $("#divArticleContent").html(marked(response.content));
                    } else {
                        $("#divArticleContent").html(response.content);
                    }
                    $('pre').addClass('line-numbers');
                    let script = document.createElement('script');
                    script.src = "./lib/prism/prism.js";
                    document.body.appendChild(script);
                    $('#divArticleContent img').each(function () {
                        let imgElem = $(this);
                        imgElem.click(function () {
                            let height = window.screen.availHeight * 0.8;
                            let width = window.screen.availWidth * 0.8;
                            let imgHtml = "<div style=\"padding: 10px;background-color: #F2F2F2;\">\n" +
                                "   <div class=\"layui-row layui-col-space15\">\n" +
                                "    <div class=\"layui-col-md12\" style=\"text-align: center;\">\n" +
                                "     <div class=\"layui-card\">\n" +
                                "      <div class=\"layui-card-body\">\n" +
                                "       <button id=\"img-big-btn\" type=\"button\" class=\"layui-btn layui-btn-sm layui-btn-normal\" style=\"margin: 5px;\"><i class=\"layui-icon layui-icon-search\"></i>放大</button>\n" +
                                "       <button id=\"zoom-scale-img\" type=\"button\" class=\"layui-btn layui-btn-disabled layui-btn-sm layui-btn-normal\" style=\"margin: 5px;\">100%</button>" +
                                "       <button id=\"img-small-btn\" type=\"button\" class=\"layui-btn layui-btn-sm layui-btn-normal\" style=\"margin: 5px;\"><i class=\"layui-icon layui-icon-search\"></i>缩小</button>\n" +
                                "      </div>\n" +
                                "     </div>\n" +
                                "    </div>\n" +
                                "    <div class=\"layui-col-md12\">\n" +
                                "     <div class=\"layui-card\">\n" +
                                "      <div class=\"layui-card-body\" style='text-align: center;overflow-y:auto; overflow-x:auto;'>\n" +
                                "        <img id='pre-img' src='" + imgElem.attr('src') + "' />\n" +
                                "      </div>\n" +
                                "     </div>\n" +
                                "    </div>\n" +
                                "   </div>\n" +
                                "  </div>";
                            layui.use('layer', function () {
                                let layer = layui.layer;
                                layer.open({
                                    type: 1, shade: 0.8, offset: 'auto', maxmin: true,
                                    area: [width + 'px', height + 'px'],
                                    shadeClose: true,/*点击外围关闭弹窗*/
                                    scrollbar: false,/*不显示滚动条*/
                                    title: "图片预览", content: imgHtml,
                                    cancel: function () {
                                    }
                                });
                            });
                            let x = 1.0;
                            $('#img-big-btn').click(function () {
                                x = x + 0.2;
                                if (x > 3.0) {
                                    x = 3.0
                                }
                                $('#pre-img').css("transform", "scale(" + x + "," + x + ")");
                                $('#zoom-scale-img').text(Math.ceil(x * 100) + "%");
                            });
                            $('#img-small-btn').click(function () {
                                x = x - 0.2;
                                if (x < 0.2) {
                                    x = 0.2;
                                }
                                $('#pre-img').css("transform", "scale(" + x + "," + x + ")");
                                $('#zoom-scale-img').text(Math.ceil(x * 100) + "%");
                            });
                        })
                    });
                }
            });
        }
        new OwO({
            logo: '🙂表情包',
            container: $(".OwO")[0],
            target: $(".OwO-textarea")[0],
            api: './lib/emoji/OwO.json',
            position: 'up',
            width: '100%',
            maxHeight: '250px'
        });
        $('.OwO').click(function () {
            $('.text_count').text($("#commentTextarea").val().length);
        });
    },
    methods: {
        getPostType(articleItem){
            let postType = articleItem.postType;
            if (postType===0){
                return "原创";
            }else if (postType===1){
                return "转载";
            }else if (postType===2){
                return "翻译";
            }else {
                return "";
            }
        },
        getOSClass(osName) {
            let newOsName = osName.toLowerCase();
            if (newOsName.indexOf("window") !== -1) {
                return 'iconfont icon-windows';
            } else if (newOsName.indexOf("linux") !== -1) {
                return 'iconfont icon-linux';
            } else if (newOsName.indexOf("android") !== -1) {
                return 'iconfont icon-anzhuo';
            } else if (newOsName.indexOf("ios") !== -1) {
                return 'iconfont icon-ios';
            } else if (newOsName.indexOf("iphone") !== -1) {
                return 'iconfont icon-ios';
            } else if (newOsName.indexOf("mac") !== -1) {
                return 'iconfont icon-ios';
            } else {
                return 'iconfont icon-anzhuoduanliulanqidakai';
            }
        },
        getBrowserClass(browserName) {
            let newBrowserName = browserName.toLowerCase();
            if (newBrowserName.indexOf("chrome") !== -1) {
                return 'iconfont icon-liulanqi1-mianxing';
            } else if (newBrowserName.indexOf("windows") !== -1) {
                return 'iconfont icon-windows';
            } else if (newBrowserName.indexOf("360") !== -1) {
                return 'iconfont icon-chrome';
            } else if (newBrowserName.indexOf("safari") !== -1) {
                return 'iconfont icon-liulanqi1';
            } else if (newBrowserName.indexOf("ie") !== -1) {
                return 'iconfont icon-ie-browser';
            } else {
                return 'iconfont icon-anzhuoduanliulanqidakai';
            }
        },
        scriptReplace: function (str) {
            if (new RegExp(".*?script[^>]*?.*?(<\/.*?script.*?>)*", "ig").test(str)) {
                return str.replace('script', '</*script*/>')
            } else {
                return str
            }
        },
        getArticleByClassify: function (classifyItem) {
            self.location = "classify.html?id=" + classifyItem.id;
        },
        search: function () {
            let titleInput = $('#titleInput').val();
            if (isEmptyStr(titleInput)) {
                layer.msg("查询参数不能为空！", {icon: 5, time: 4000});
            } else {
                self.location = "searchItem.html?keyWord=" + titleInput;
            }
        },
        getvCode: function () {
            axiosGetFunction('/api/imageCode/openApi/verifyCode', null, false).then((response) => {
                if (response) {
                    $('#verifyImg').attr("src", 'data:image/png;base64,' + response['base64ImgStr']);
                    this.codeUuid = response['uuid'];
                }
            });
        },
        cancelReply: function () {
            this.tempComment.parentId = null;
            $('#cancel-Reply-btn').addClass('layui-btn-disabled')
            $("#comment-message").text('');
            $("#commentTextarea").val('')
        },
        listCommentPage(articleId) {
            this.commentPageQuery.articleId = articleId;
            return axiosPostFunction("/api/comment/openApi/listPage/articleId", this.commentPageQuery, false);
        },
        getPageCommentList: function (articleId) {
            let _that = this;
            _that.listCommentPage(articleId).then((respData) => {
                if (null != respData) {
                    let total = respData.total;
                    _that.commentList = respData.list;
                    /*使用layui分页*/
                    layui.use(['laypage', 'layer', 'jquery'], function () {
                        let laypage = layui.laypage;
                        let $ = layui.$
                        let layer = layui.layer;
                        //查询条件
                        laypage.render({
                            elem: 'pagination',
                            count: total /*数据总量，这里先给个初始值10，然后用layui初始化分页*/,
                            layout: ['count', 'prev', 'page', 'next', 'limit', 'skip'],
                            limit: 10,
                            jump: function (obj, first) {
                                /*点击非第一页页码时的处理逻辑。比如这里调用了ajax方法，异步获取分页数据*/
                                if (!first) {
                                    let loading = layer.msg('数据加载中...', {icon: 16, shade: 0.3});
                                    _that.commentPageQuery.pageNo = obj.curr;
                                    _that.commentPageQuery.pageSize = obj.limit;
                                    _that.listCommentPage(articleId).then((newRespData) => {
                                        if (null != respData) {
                                            let newTotal = newRespData.total;
                                            _that.commentList = newRespData.list;
                                            /*如果数据总量发生了变化，我们将刷新总页数*/
                                            if (newTotal !== obj.count) {
                                                obj.count = newTotal;
                                                $(".layui-laypage-btn").click();
                                            }
                                        }
                                        layer.close(loading);
                                    });
                                }
                            }
                        });
                    });
                }
            });
        },
        replyComment: function (parentId, userName) {
            this.tempComment.parentId = parentId;
            $("#commentTextarea").val("@" + userName + ": ")
            $("html, body").animate({
                scrollTop: $("#addComment").offset().top
            }, {duration: 500, easing: "swing"});
            $('#cancel-Reply-btn').removeClass('layui-btn-disabled')
        },
        addComment: function () {
            let commentTextareaSelector = $("#commentTextarea");
            let commentText = commentTextareaSelector.val();
            let maxLength = commentTextareaSelector.attr('maxlength');
            if (commentText.length > maxLength) {
                layui.use('layer', function () {
                    layui.layer.msg('超过最大字数限制，无法提交');
                });
                $('.text_count').text(commentText.length);
                return;
            }
            let code = $('#code').val();
            let userEmail = this.tempComment.userEmail;
            if (isEmptyStr(this.tempComment.userName) || isEmptyStr(commentText) || isEmptyStr(userEmail)) {
                layui.use('layer', function () {
                    layui.layer.alert('用户名、邮箱和评论不能为空', {icon: 2, skin: 'layui-layer-molv', closeBtn: 0, anim: 6})
                });
            } else {
                if (!this.mailRe.test(userEmail)) {
                    layui.use('layer', function () {
                        layui.layer.alert('邮箱格式不正确', {icon: 2, skin: 'layui-layer-molv', closeBtn: 0, anim: 6})
                    });
                    return;
                }
                this.tempComment.commentContent = this.scriptReplace(commentText);
                let verifyCode = {"code": code, "codeUuid": this.codeUuid};
                let commentAndVerifyCode = {comment: this.tempComment, verifyCode: verifyCode};
                let addLoading = layer.msg('添加评论中...', {icon: 16, shade: 0.3, time: 10000});
                axiosPostFunction('/api/comment/openApi/insert', commentAndVerifyCode, false).then((response) => {
                    layer.close(addLoading);
                    console.log(response);
                    if (response) {
                        layer.alert('添加评论成功！', {icon: 1, skin: 'layui-layer-molv', closeBtn: 0, anim: 4});
                        this.getPageCommentList(this.articleItem.id);
                        $("#commentTextarea").val("");
                        $('#code').val('');
                        this.getvCode();
                        $('#cancel-Reply-btn').addClass('layui-btn-disabled')
                        this.tempComment.parentId = null;
                        $("#comment-message").text('');
                        this.tempComment.userEmail = '';
                        this.tempComment.userName = '';
                        this.articleItem.sumComment = this.articleItem.sumComment + 1;
                    }
                }).catch(function (err) {
                    layer.close(addLoading);
                    layer.alert(err, {icon: 2, skin: 'layui-layer-molv', closeBtn: 0, anim: 6})
                    $('#code').val('');
                });
            }
        },
        addSupport: function () {
            if (!this.isAlreadySupport) {
                let _that = this;
                axiosGetFunction('/api/article/openApi/increase/support/' + this.articleItem.id, null, false).then((response) => {
                    if (response) {
                        this.articleItem.supportCount = this.articleItem.supportCount + 1;
                        this.isAlreadySupport = true;
                    }
                }).catch(function (err) {
                    layer.alert('点赞失败', {icon: 2, skin: 'layui-layer-molv', closeBtn: 0, anim: 6});
                })
            } else {
                layer.alert('不允许重复点赞哦', {icon: 2, skin: 'layui-layer-molv', closeBtn: 0, anim: 6});
            }
        }
    },
    created() {

    }
})

window.wordLeg = function (obj) {
    let value = $(obj).val();
    let currLength = value.length;
    let length = $(obj).attr('maxlength');
    if (currLength > length) {
        layui.use('layer', function () {
            layui.layer.msg('字数请在' + length + '字以内');
        });
    }
    $('.text_count').text(currLength);
}