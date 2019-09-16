//jqGrid的配置信息
//$.jgrid.defaults.width = 1000;
$.jgrid.defaults.responsive = true;
$.jgrid.defaults.styleUI = 'Bootstrap';

layer.config({
	extend: 'myskin/style.css', //自定义弹出框样式
	skin: 'my-green-skin'
});

var baseURL = "../";

//金额正则表达式
var moneyExp = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;

//工具集合Tools
window.T = {};

// 获取请求参数
// 使用示例
// location.href = http://localhost:8080/index.html?id=123
// T.p('id') --> 123;
var url = function(name) {
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
};
T.p = url;

//全局配置
$.ajaxSetup({
    dataType: "json",
    cache: false
});

//重写alert
window.alert = function(msg, callback){
    parent.layer.alert(msg, function(index){
        parent.layer.close(index);
        if(typeof(callback) === "function"){
            callback("ok");
        }
    });
}

//重写confirm式样框
window.confirm = function(msg, callback){
    parent.layer.confirm(msg, {btn: ['确定','取消']},
        function(){//确定事件
            if(typeof(callback) === "function"){
                callback("ok");
            }
        });
}

//选择一条记录
function getSelectedRow() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
        alert("请选择一条记录");
        return ;
    }

    var selectedIDs = grid.getGridParam("selarrrow");
    if(selectedIDs.length > 1){
        alert("只能选择一条记录");
        return ;
    }

    return selectedIDs[0];
}

//获取某一条记录的某列值
function getSelectedRowValue(colName) {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
        alert("请选择一条记录");
        return ;
    }

    var selectedIDs = grid.getGridParam("selarrrow");
    if(selectedIDs.length > 1){
        alert("只能选择一条记录");
        return ;
    }

    return grid.jqGrid('getRowData', selectedIDs[0])[colName];
}

//单选情况下某一条记录的某列值
function getSingleSelectedRowValue(colName) {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
        alert("请选择一条记录");
        return ;
    }
    return grid.jqGrid('getRowData', rowKey)[colName];
}

//选择多条记录
function getSelectedRows() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
        alert("请选择一条记录");
        return ;
    }

    return grid.getGridParam("selarrrow");
}

//判断是否为空
function isBlank(value) {
    return !value || !/\S/.test(value)
}

//遍历json并拼接字符串
function strJsonArray(obj){
	var str = '';
	if(!isBlank(obj)){
		for(var i=0,l=obj.length;i<l;i++){
			if(i<l-1){
				str = str+obj[i]+',';
			}else{
				str = str+obj[i];
			}
		}
	}
	return str;
}

//时间相关
Date.prototype.Format = function(formatStr) {
    var str = formatStr;
    var Week = ['日', '一', '二', '三', '四', '五', '六'];

    str = str.replace(/yyyy|YYYY/, this.getFullYear());
    str = str.replace(/yy|YY/, (this.getYear() % 100) > 9 ? (this.getYear() % 100).toString() : '0' + (this.getYear() % 100));

    var _month = this.getMonth();
    _month++;
    str = str.replace(/MM/, _month > 9 ? _month.toString() : '0' + _month);
    str = str.replace(/M/g, _month);

    str = str.replace(/w|W/g, Week[this.getDay()]);

    str = str.replace(/dd|DD/, this.getDate() > 9 ? this.getDate().toString() : '0' + this.getDate());
    str = str.replace(/d|D/g, this.getDate());

    str = str.replace(/hh|HH/, this.getHours() > 9 ? this.getHours().toString() : '0' + this.getHours());
    str = str.replace(/h|H/g, this.getHours());
    str = str.replace(/mm/, this.getMinutes() > 9 ? this.getMinutes().toString() : '0' + this.getMinutes());
    str = str.replace(/m/g, this.getMinutes());

    str = str.replace(/ss|SS/, this.getSeconds() > 9 ? this.getSeconds().toString() : '0' + this.getSeconds());
    str = str.replace(/s|S/g, this.getSeconds());

    return str;
}

function dateFormater(date, formatStr) {
    if (isBlank(formatStr)) {
        return date.Format('yyyy-MM-dd');
    }
    return date.Format(formatStr);
}

function getTodayStr() {
    var _date = new Date();
    return dateFormater(_date);
}

//克隆JSON对象
function cloneJsonObj(obj) {
	return JSON.parse(JSON.stringify(obj));
}

//返回数字
function removeFormatMoney(s) {
    s = s.toString().replace("(","-").replace(")","");
    return parseFloat(s.replace(/[^\d\.-]/g, ""));
}

/* 
 * formatMoney(s,type) 
 * 功能：金额按千位逗号分隔，负号用括号
 * 参数：s，需要格式化的金额数值. 
 * 参数：type,判断格式化后的金额是否需要小数位. 
 * 返回：返回格式化后的数值字符串. 
 */ 
function formatMoney(s, type) {
    var result = s;
    if (s < 0)
        s = 0 - s;
    if (/[^0-9\.]/.test(s))
        return "0.00";
    if (s == null || s == "null" || s == "")
        return "0.00";
    if (type > 0)
        s = new Number(s).toFixed(type);
    s = s.toString().replace(/^(\d*)$/, "$1.");
    s = (s + "00").replace(/(\d*\.\d\d)\d*/, "$1");
    s = s.replace(".", ",");
    var re = /(\d)(\d{3},)/;
    while (re.test(s))
        s = s.replace(re, "$1,$2");
    s = s.replace(/,(\d\d)$/, ".$1");
    if (type == 0) {
        var a = s.split(".");
        if (a[1] == "00") {
            s = a[0];
        }
    }
    if (result < 0)
        result = "(" + s + ")";
    else
        result = s;
    return result;
}

/* 
 * 将后台传过来的money类型对象转换成金额格式的字符串 
 * 返回：返回格式化后的数值字符串. 
 */ 
function formatMoneyObjectToString(money) {
	return money==null?'':formatMoney(money.amount);
}
