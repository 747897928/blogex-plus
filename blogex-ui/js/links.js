const vueObject = new Vue({
    el: ".main",
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
            updateTime: new Date(),
            updateFullTime: new Date(),
        },
        linksList:[],
        commentList: [],
        classifyList: [],
        codePic: "./images/verifyCodeImg.png",
        mailRe: /^\w+@[a-zA-Z0-9]{2,10}(?:\.[a-z]{2,4}){1,3}$/,
        tempComment: {
            "articleId": null,
            "parentId": null,
            "commentContent": "",
            "pageType": 2,
            "userName": "",
            "userEmail": ""
        },
        commentPageQuery: {
            "articleId": null,
            "pageType": 2,
            "pageNo": 1,
            "pageSize": 10
        },
        linksPageQuery: {
            "pageNo": 1,
            "pageSize": 10
        },
        codeUuid: "",
    },
    mounted() {
        axiosPutFunction('/api/count/openApi/increase', null, false).then((response) => {
        });
        this.getPageCommentList();
        this.getPageLinksList();
        axiosGetFunction('/api/bloggerInfo/openApi/getBlogInfo', null, false).then((response) => {
            if (response) {
                this.blogger = response;
                this.blogger.updateFullTime = new Date(this.blogger.updateTime);
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
    },
    methods: {
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
        listCommentPage() {
            return axiosPostFunction("/api/comment/openApi/listPage/pageType", this.commentPageQuery, false);
        },
        getPageCommentList: function () {
            let _that = this;
            _that.listCommentPage().then((respData) => {
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
                                    _that.listCommentPage().then((newRespData) => {
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
        listLinksPage() {
            return axiosPostFunction("/api/blogLink/openApi/listPage", this.linksPageQuery, false);
        },
        getPageLinksList: function () {
            let _that = this;
            _that.listLinksPage().then((respData) => {
                if (null != respData) {
                    let total = respData.total;
                    _that.linksList = respData.list;
                    /*使用layui分页*/
                    layui.use(['laypage', 'layer', 'jquery'], function () {
                        let laypage = layui.laypage;
                        let $ = layui.$
                        let layer = layui.layer;
                        //查询条件
                        laypage.render({
                            elem: 'links-pagination',
                            count: total /*数据总量，这里先给个初始值10，然后用layui初始化分页*/,
                            layout: ['count', 'prev', 'page', 'next', 'limit', 'skip'],
                            limit: 10,
                            jump: function (obj, first) {
                                /*点击非第一页页码时的处理逻辑。比如这里调用了ajax方法，异步获取分页数据*/
                                if (!first) {
                                    let loading = layer.msg('数据加载中...', {icon: 16, shade: 0.3});
                                    _that.linksPageQuery.pageNo = obj.curr;
                                    _that.linksPageQuery.pageSize = obj.limit;
                                    _that.listLinksPage().then((newRespData) => {
                                        if (null != respData) {
                                            let newTotal = newRespData.total;
                                            _that.linksList = newRespData.list;
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
                        this.getPageCommentList();
                        $("#commentTextarea").val("");
                        $('#code').val('');
                        this.getvCode();
                        $('#cancel-Reply-btn').addClass('layui-btn-disabled')
                        this.tempComment.parentId = null;
                        $("#comment-message").text('');
                        this.tempComment.userEmail = '';
                        this.tempComment.userName = '';
                    }
                }).catch(function (err) {
                    layer.close(addLoading);
                    layer.alert(err, {icon: 2, skin: 'layui-layer-molv', closeBtn: 0, anim: 6})
                    $('#code').val('');
                });
            }
        },
    },
    created() {

    }
});