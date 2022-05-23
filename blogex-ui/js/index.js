const vueObject = new Vue({
    el: ".main",
    data: {
        classifyList: [],
        articleList: [],
        articleTOPFiveList: [],
        articleCommentTOPFiveList: [],
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
        articlePageQuery: {
            "articleStatus": 0,
            "classifyId": null,
            "commentStatus": null,
            "contentType": null,
            "endTime": "",
            "pageNo": 1,
            "pageSize": 12,
            "postType": null,
            "searchKey": "",
            "startTime": "",
            "tagId": null
        },
    },
    methods: {
        showMessage: function (text) {
            let live2dTips = $('#live2d-tips');
            live2dTips.stop();
            live2dTips.html(text).fadeTo(200, 1);
            live2dTips.stop().css('opacity', 1);
            live2dTips.delay(3000).fadeTo(200, 0);
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
        getPostType(articleItem) {
            let postType = articleItem.postType;
            if (postType === 0) {
                return "原创";
            } else if (postType === 1) {
                return "转载";
            } else if (postType === 2) {
                return "翻译";
            } else {
                return "";
            }
        },
    },
    mounted() {
        let _that = this;
        axiosPostFunction('/api/article/openApi/page/query', this.articlePageQuery, false).then((response) => {
            if (response) {
                let newList = response["list"];
                for (let i = 0; i < newList.length; i++) {
                    newList[i].createTime = new Date(newList[i].createTime);
                }
                this.articleList = newList;
                let totalCount = response["total"];
                /*使用layui分页*/
                layui.use(['laypage', 'layer', 'jquery'], function () {
                    let laypage = layui.laypage;
                    let $ = layui.$
                    let layer = layui.layer;
                    //查询条件
                    laypage.render({
                        elem: 'pagination'
                        , count: totalCount /*数据总量，这里先给个初始值10，然后用layui初始化分页*/
                        , theme: '#7266BA' /*自定义主题。支持传入：颜色值，或任意普通字符*/
                        /*, hash: 'curr'*/ /*自定义hash值 开启location.hash，并自定义 hash 值。如果开启，在触发分页时，会自动对url追加：#!hash值={curr} 利用这个，可以在页面载入时就定位到指定页*/
                        , layout: ['count', 'prev', 'page', 'next', 'limit', 'skip']
                        , limit: 12
                        , limits: [12, 24, 36, 48, 60]
                        , jump: function (obj, first) {
                            /*点击非第一页页码时的处理逻辑。比如这里调用了ajax方法，异步获取分页数据*/
                            if (!first) {
                                let loading = layer.msg('数据加载中...', {icon: 16, shade: 0.3, time: 10000});
                                _that.articlePageQuery.pageNo = obj.curr;
                                _that.articlePageQuery.pageSize = obj.limit;
                                axiosPostFunction('/api/article/openApi/page/query', _that.articlePageQuery, false).then((newResponse) => {
                                    if (newResponse) {
                                        let newList = newResponse["list"];
                                        for (let i = 0; i < newList.length; i++) {
                                            newList[i].createTime = new Date(newList[i].createTime);
                                        }
                                        _that.articleList = newList;
                                        let totalCount = newResponse["total"];
                                        /*如果数据总量发生了变化，我们将刷新总页数*/
                                        if (totalCount !== obj.count) {
                                            obj.count = totalCount;
                                            $(".layui-laypage-btn").click();
                                        }
                                        $("html, body").scrollTop(1);
                                        layer.close(loading);
                                    }
                                });
                            }
                        }
                    });
                });
                scrollArticle();
            }
        });

        axiosPutFunction('/api/count/openApi/increase', null, false).then((response) => {
        });
        axiosGetFunction('/api/bloggerInfo/openApi/getBlogInfo', null, false).then((response) => {
            if (response) {
                this.blogger = response;
                $("#bloggerIcon").attr("href", this.blogger.avatarUrl);
                let header1_HeaderTitle = $("#Header1_HeaderTitle");
                document.title = this.blogger.userName + "的个人博客"
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


        axiosGetFunction('/api/article/openApi/top/five', null, false).then((response) => {
            if (response) {
                this.articleTOPFiveList = response;
            }
        });
        axiosGetFunction('/api/article/openApi/top/sumComment/five', null, false).then((response) => {
            if (response) {
                this.articleCommentTOPFiveList = response;
            }
        });
    },
    created() {

    }
})