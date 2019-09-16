$(function () {
    $("#jqGrid").jqGrid({
        url: '../sys/role/list',
        datatype: "json",
        colModel: [			
            { label: '角色ID', name: 'id', hidden:true, key: true },
			{ label: '角色编码', name: 'roleCode', index: "role_code", width: 200 },
			{ label: '角色名称', name: 'name', width: 200 },
			{ label: '状态', name: 'status', width: 100, formatter: function(value, options, row){
				return getStatus_Style(value);
	        }},
			{ label: '创建人', name: 'createUserCode', index: "create_user_code", width: 200},
			{ label: '创建时间', name: 'gmtCreate', index: "gmt_create", width: 200},
			{ label: '修改人', name: 'updateUserCode', index: "update_user_code", width: 200},
			{ label: '修改时间', name: 'gmtUpdate', index: "gmt_update", width: 200},
			{ label: '备注', name: 'remark', width: 500 }
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
});

var setting = {
	data: {
		simpleData: {
			enable: true,
			idKey: "menuCode",
			pIdKey: "parentCode",
			rootPId: -1
		},
		key: {
			url:"nourl"
		}
	},
	check:{
		enable:true,
		nocheckInherit:true
	}
};
var ztree;
	
var vm = new Vue({
	el:'#app',
	data:{
		q:{
			name: null,
			status: null
		},
		showList: true,
		title:null,
		role:{},
		isReadOnly: false
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
			var roleId = getSelectedRow();
			if(roleId == null){
				return ;
			}
            //获取角色信息
			vm.getMenuTreeByRoleId(roleId);
			
			
			layer.open({
                type: 1,
                skin: 'my-green-skin',
                title: "角色详情",
                area: ['800px', '400px'],
                shadeClose: false,
                content: jQuery("#roleDetailLayer")
			})
                
		},
		add: function(){
			vm.showList = false;
			vm.isReadOnly = false;
			vm.title = "新增";
			vm.role = {};
			vm.getMenuTree(null);
		},
		update: function () {
			var roleId = getSelectedRow();
			if(roleId == null){
				return ;
			}
			
			if(getSelectedRowValue('status') == getStatus_Style('CLOSED')){
				alert('已注销角色不允许修改');
				return ;
			}
			
			vm.showList = false;
			vm.isReadOnly = true;
            vm.title = "修改";
            vm.getMenuTree(roleId);
		},
		close: function () {
			var roleIds = getSelectedRows();
			if(roleIds == null){
				return ;
			}
			
			confirm('确定要注销所选角色？', function(){
				$.ajax({
					type: "POST",
				    url: "../sys/role/close",
                    contentType: "application/json",
				    data: JSON.stringify(roleIds),
				    success: function(r){
						if(r.code == "00"){
							alert('操作成功', function(index){
								vm.reload();
							});
						}else{
							alert('操作失败：' + r.msg);
						}
					}
				});
			});
		},
		getRoleInfoAndMenu: function(roleId){
            $.get("../sys/role/info?roleId="+roleId, function(r){
            	//获取角色信息
            	vm.role = r.role;
                //勾选角色拥有的权限
    			var menuIds = vm.role.menuIdList;
    			for(var i=0; i<menuIds.length; i++) {
    				var node = ztree.getNodeByParam("id", menuIds[i]);
    				ztree.checkNode(node, true, false);
    			}
    		});
		},
		saveOrUpdate: function () {
            if(vm.validator()){
                return ;
            }

			//获取选择的菜单
			var nodes = ztree.getCheckedNodes(true);
			var menuIdList = new Array();
			for(var i=0; i<nodes.length; i++) {
				menuIdList.push(nodes[i].id);
			}
			vm.role.menuIdList = menuIdList;
			
			var url = vm.role.id == null ? "sys/role/add" : "sys/role/update";
			$.ajax({
				type: "POST",
			    url: "../" + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.role),
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
		getMenuTree: function(roleId) {
			//获取所有菜单并加载菜单树
			$.get("../sys/menu/list", function(r){
				setting.check.enable = true;
				ztree = $.fn.zTree.init($("#menuTree"), setting, r);
				
				//展开所有节点
				ztree.expandAll(true);
				
				//获取角色信息，并勾选角色拥有的权限
				if(roleId != null){
					vm.getRoleInfoAndMenu(roleId);
				}
			});
	    },
	    getMenuTreeByRoleId: function(roleId) {
			//获取所有菜单并加载菜单树
			$.get("../sys/menu/listByRole?roleId="+roleId, function(r){
				setting.check.enable = false;
				ztree = $.fn.zTree.init($("#menuTreeReadOnly"), setting, r);
				
				//展开所有节点
				ztree.expandAll(true);
			});
			
			$.get("../sys/role/info?roleId="+roleId, function(r){
            	//获取角色信息
            	vm.role = r.role;
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
            if(isBlank(vm.role.roleCode)){
                alert("角色编码不能为空");
                return true;
            }
            if(isBlank(vm.role.name)){
                alert("角色名不能为空");
                return true;
            }
            if(isBlank(ztree.getCheckedNodes(true).length)){
                alert("权限不能为空");
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
	case 'CLOSED': return '<span class="label label-danger">注销</span>';
	default: return '未知状态';
	}
}