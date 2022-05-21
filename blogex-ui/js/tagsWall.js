const vueObject = new Vue({
    el: ".main",
    data: {
        classifyList: [],
        overview: {
            "sumArticle": 0,
            "sumComment": 0,
            "sumSupport": 0,
            "sumView": 0
        },
        tagList:[],
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
    },
    methods: {
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
    },
    mounted() {
        let _that = this;
        axiosGetFunction('/api/tag/openApi/queryAll', null, false).then((response) => {
            if (response) {
                this.tagList = response;
            }
        });

        axiosPutFunction('/api/count/openApi/increase', null, false).then((response) => {});
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
    created(){

    }
})