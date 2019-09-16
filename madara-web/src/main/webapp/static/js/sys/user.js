$(function () {
    $("#jqGrid").jqGrid({
        url: '../sys/user/list',
        datatype: "json",
        colModel: [			
            { label: '用户ID', name: 'id', hidden:true, key: true },
			{ label: '用户账号', name: 'userCode', index:'user_code', width: 200 },
			{ label: '用户名', name: 'name', width: 200 },
			{ label: '角色', name: 'roleName', width: 200 },
			{ label: '状态', name: 'status', width: 100, formatter: function(value, options, row){
	            return getStatus_Style(value);
	        }},
			{ label: '创建人', name: 'createUserCode', index:'create_user_code', width: 80 },
			{ label: '创建时间', name: 'gmtCreate', index:'gmt_create', width: 220, formatter:"date", formatoptions: {srcformat:'Y-m-d H:i:s',newformat:'Y-m-d H:i:s'}},
			{ label: '更新时间', name: 'gmtUpdate', index:'gmt_update', width: 220, formatter:"date", formatoptions: {srcformat:'Y-m-d H:i:s',newformat:'Y-m-d H:i:s'}},
			{ label: '备注', name: 'remark', width: 500 },
        ],
		viewrecords: true,
        height: "100%",
        width: "100%",
        shrinkToFit:false,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 45, 
        autowidth:true,
        multiselect: true,
        sortname: 'gmt_create',//表格加载时根据创建时间降序排序
        sortorder: 'desc',
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.pageNum",
            total: "page.pages",
            records: "page.total"
        },
        prmNames : {
            page:"pageNum", 
            rows:"pageSize", 
            order: "order"
        }
    });
})

var vm = new Vue({
	el:'#app',
	data:{
		q:{
			name: null,
			status: null
		},
		showList: true,
		title:null,
		roleList:{},
		user:{
			roleIdList:[],
			roleName: []
		},
        isReadOnly: false,
        code:null
	},
	methods: {
		query: function () {
			vm.reload();
		},
		reset : function() {
			vm.q.name = null;
			vm.q.status = '';
			$('.selectpicker').selectpicker('val', '');
			vm.query();
		},
		detail: function () {
			var userId = getSelectedRow();
			if(userId == null){
				return ;
			}
			
            //获取该用户信息(包括其角色信息)
			vm.getUser(userId);
			
			
			layer.open({
                type: 1,
                skin: 'my-green-skin',
                title: "用户详情",
                area: ['800px', '400px'],
                shadeClose: false,
                content: jQuery("#userDetailLayer")
			})
                
		},
		add: function(){
			vm.showList = false;
			vm.isReadOnly = false;
			vm.title = "新增";
			vm.roleList = {};
			vm.user = {roleIdList:[]};
			
			//获取所有角色列表
			this.getRoleList();
		},
		update: function () {
			var userId = getSelectedRow();
			if(userId == null){
				return ;
			}
			if(getSelectedRowValue('status') == getStatus_Style('CLOSED')){
				alert('已注销用户不允许修改');
				return ;
			}
			
			vm.showList = false;
			vm.isReadOnly = true;
            vm.title = "修改";
			
            //获取该用户信息(包括其角色信息)
			vm.getUser(userId);
			//获取所有角色列表
			this.getRoleList();
		},
		close: function () {
			var userIdList = getSelectedRows();
			if(userIdList == null){
				return ;
			}
			
			confirm('确定要注销所选用户？', function(){
				$.ajax({
					type: "POST",
				    url: "../sys/user/close",
                    contentType: "application/json",
				    data: JSON.stringify(userIdList),
				    success: function(r){
						if(r.code == "00"){
							alert('操作成功', function(){
                                vm.reload();
							});
						}else{
							alert('操作失败：' + r.msg);
						}
					}
				});
			});
		},
		unfreeze: function () {
			var userIdList = getSelectedRows();
			if(userIdList == null){
				return ;
			}
			
			confirm('确定要解冻所选用户？', function(){
				$.ajax({
					type: "POST",
				    url: "../sys/user/unfreeze",
                    contentType: "application/json",
				    data: JSON.stringify(userIdList),
				    success: function(r){
						if(r.code == "00"){
							alert('操作成功', function(){
                                vm.reload();
							});
						}else{
							alert('操作失败：' + r.msg);
						}
					}
				});
			});
		},
		resetPassword: function () {
			var userId = getSelectedRow();
			if(userId == null){
				return ;
			}
			
			confirm('确定要重置该用户的密码？', function(){
				$.ajax({
					type: "POST",
				    url: "../sys/user/resetPassword",
				    dataType: "json",
				    data: "userId=" +userId,
				    success: function(r){
						if(r.code == "00"){
							alert('操作成功', function(){
                                vm.reload();
							});
						}else{
							alert('操作失败：' + r.msg);
						}
					}
				});
			});
		},
		addOrUpdate: function () {
            if(vm.validator()){
                return ;
            }

			var url = vm.user.id == null ? "sys/user/add" : "sys/user/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.user),
			    success: function(r){
			    	if(r.code == "00"){
						alert('操作成功', function(){
							vm.reload();
						});
					}else{
						alert('操作失败：' + r.msg);
					}
				}
			});
		},
		getUser: function(userId){
			$.get("../sys/user/info?userId="+userId, function(r){
				vm.user = r.user;
			});
		},
		getRoleList: function(){
			$.get("../sys/role/select", function(r){
				vm.roleList = r.roleList;
			});
		},
		reload: function () {
			vm.showList = true;
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'name': vm.q.name, 'status': vm.q.status},
                page:1
            }).trigger("reloadGrid");
		},
		validator: function () {
            if(isBlank(vm.user.userCode)){
                alert("用户编码不能为空");
                return true;
            }
            if(isBlank(vm.user.name)){
                alert("用户名不能为空");
                return true;
            }
            if(isBlank(vm.user.roleIdList)){
                alert("角色不能为空");
                return true;
            }
        }
	}
});

/**
 * 获取状态中文描述
 * @param value
 * @returns
 */
function getStatus_CN(value){
	switch(value) {
	case 'NORMAL': return '正常';
	case 'FROZEN': return '冻结';
	case 'CLOSED': return '注销';
	default: return '未知状态';
	}	
}

/**
 * 获取状态样式描述
 * @param value
 * @returns
 */
function getStatus_Style(value){
	switch(value) {
	case 'NORMAL': return '<span class="label label-success">正常</span>';
	case 'FROZEN': return '<span class="label label-warning">冻结</span>';
	case 'CLOSED': return '<span class="label label-danger">注销</span>';
	default: return '未知状态';
	}
}