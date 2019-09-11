$(function() {
	loadEchart1();
	loadEchart2();
});

function loadEchart1() {
	var myChart1 = echarts.init(document.getElementById('chart1'),
			'infographic');
	myChart1.setOption({
		title : {
			text : '订单笔数统计',
			x : 'left'
		},
		color : [ '#6699CC', '#006699' ],
		tooltip : {
			trigger : 'axis'
		},
		legend : {
			data : [ '充值', '提现' ]
		},
		toolbox : {
			show : true,
			feature : {
				mark : {
					show : true
				},
				dataView : {
					show : true,
					readOnly : false
				},
				magicType : {
					show : true,
					type : [ 'line', 'bar', 'stack', 'title' ]
				},
				restore : {
					show : true
				},
				saveAsImage : {
					show : true
				}
			}
		},
		calculable : true,
		xAxis : [ {
			type : 'category',
			data : [ '1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月',
					'10月', '11月', '12月' ]
		} ],
		yAxis : [ {
			type : 'value',
			splitArea : {
				show : true
			}
		} ],
		series : [
				{
					name : '充值',
					type : 'bar',
					data : [ 120, 249, 370, 164, 133, 326, 200, 232, 256, 767,
							356, 622 ]
				},
				{
					name : '提现',
					type : 'bar',
					data : [ 26, 59, 90, 264, 287, 307, 256, 322, 387, 188,
							160, 123 ]
				} ]
	});
}

function loadEchart2() {
	var myChart2 = echarts.init(document.getElementById('chart2'),
			'infographic');
	myChart2.setOption({
		color : ['#CCCC00', '#CC9999', '#CCCC99', '#CC3333', '#666666' ],
		title : {
			text : 'APP用户来源',
			subtext : '纯属虚构',
			x : 'center'
		},
		tooltip : {
			trigger : 'item',
			formatter : "{a} <br/>{b} : {c} ({d}%)"
		},
		legend : {
			orient : 'vertical',
			x : 'left',
			data : [ '官网下载', '搜索引擎', '邮件营销', '视频广告', '应用商店' ]
		},
		toolbox : {
			show : true,
			feature : {
				mark : {
					show : true
				},
				dataView : {
					show : true,
					readOnly : false
				},
				magicType : {
					show : true,
					type : [ 'pie', 'funnel' ],
					option : {
						funnel : {
							x : '25%',
							width : '50%',
							funnelAlign : 'left',
							max : 1548
						}
					}
				},
				restore : {
					show : true
				},
				saveAsImage : {
					show : true
				}
			}
		},
		calculable : true,
		series : [ {
			name : '访问来源',
			type : 'pie',
			radius : '55%',
			center : [ '50%', '60%' ],
			data : [ {
				value : 335,
				name : '官网下载'
			}, {
				value : 310,
				name : '搜索引擎'
			}, {
				value : 234,
				name : '邮件营销'
			}, {
				value : 135,
				name : '视频广告'
			}, {
				value : 1548,
				name : '应用商店'
			} ]
		} ]
	});
}