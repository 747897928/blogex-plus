const vueObject = new Vue({
	el: ".layuimini-container",
	data: {
		mouthCount: 0,
		todayCount: 0,
		totalCount: 0,
		weekCount: 0,
		overview: {
			"sumArticle": 0,
			"sumComment": 0,
			"sumSupport": 0,
			"sumView": 0
		},
	},
	methods: {

	},
	created: function() {
		let _that = this;
		layui.use(['layer', 'miniTab', 'echarts'], function() {
			let $ = layui.jquery,
				miniTab = layui.miniTab;
			let layer = layui.layer;
			axiosGetFunction("/api/count/authApi",null,true).then((resultData)=>{
				if (resultData){
					let echartsData = resultData['echartsData'];
					_that.mouthCount = resultData.mouthCount;
					_that.todayCount = resultData.todayCount;
					_that.totalCount = resultData.totalCount;
					_that.weekCount = resultData.weekCount;
					miniTab.listen();
					let echartsRecords = echarts.init(document.getElementById('echarts-records'), 'walden');
					let optionRecords = {
						tooltip: {
							trigger: 'axis'
						},
						legend: {
							data: ['周阅览']
						},
						grid: {
							left: '3%',
							right: '4%',
							bottom: '3%',
							containLabel: true
						},
						toolbox: {
							feature: {
								saveAsImage: {}
							}
						},
						xAxis: {
							type: 'category',
							boundaryGap: false,
							data: echartsData.xAxisData
						},
						yAxis: {
							type: 'value'
						},
						series: [{
							name: '一周阅览量',
							type: 'line',
							data: echartsData.seriesData
						}]
					};
					echartsRecords.setOption(optionRecords);
					/**echarts 窗口缩放自适应**/
					window.onresize = function() {
						echartsRecords.resize();
					}
				}
			});
			axiosGetFunction('/api/article/openApi/blog/overview', null, false).then((response) => {
				if (response) {
					_that.overview = response;
				}
			});
		});	
	}
})
