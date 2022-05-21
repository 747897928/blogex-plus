new Vue({
    el: "#searchArea",
    data: {
        articleList: [],
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
            "searchKey": "",
            "startTime": "",
            "tagId": null
        },
    },
    watch:{
        articleList:function(){
            this.$nextTick(function(){
                layui.use(['form'], function () {
                    let form = layui.form;
                    form.render();
                });
            });
        },
    },
    mounted() {
        axiosGetFunction('/api/bloggerInfo/openApi/getBlogInfo', null, false).then((response) => {
            if (response) {
                this.blogger = response;
                $("#bloggerIcon").attr("href", this.blogger.avatarUrl);
                $("#footer_a").html(this.blogger.recordNumber);
            }
        });
    },
    methods: {
        search: function () {
            let _that = this;
            let searchInput = $('#searchInput').val();
            if (!isEmptyStr(searchInput)) {
                this.articlePageQuery.searchKey = searchInput;
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
                    }
                });
            } else {
                //alert("查询参数不能为空！")
            }
            let newUrl = location.pathname + "?keyWord=" + searchInput;
            history.pushState(null, null, newUrl);
        },
    },
    created() {
        let _that = this;
        let searchKeyWord = getParams("keyWord");
        layui.use(['form'], function () {
            let form = layui.form;
            $('#searchInput').val(searchKeyWord);
            form.render();
            _that.search();
        });
    }
})