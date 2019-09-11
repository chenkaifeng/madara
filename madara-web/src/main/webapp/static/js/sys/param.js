$(function () {
    $("#jqGrid").jqGrid({
        url: '../sys/param/list',
        datatype: "json",
        colModel: [			
            { label: '参数ID', name: 'id', hidden:true, key: true },
			{ label: '参数分组', name: 'paramGroup', index: "param_group", width: 250 },
			{ label: '参数编码', name: 'paramCode', index: "param_code", width: 350 },
			{ label: '参数值', name: 'paramValue', index: "param_value",width: 255},
			{ label: '描述', name: 'description', width: 323},
			{ label: '是否可改', name: 'modifyFlag', index: "modify_flag", width: 80, formatter: function(val) {
				switch(val) {
					case 'T': return '是'; break;
					case 'F': return '否'; break;
					default: '未知参数';
				}
			}},
			{ label: '创建时间', name: 'gmtCreate', index: "gmt_create", width: 150},
			{ label: '修改时间', name: 'gmtUpdate', index: "gmt_update", width: 150}
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
        sortname: 'gmt_create',
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

var vm = new Vue({
	el:'#app',
	data:{
		q:{
			paramGroup: null,
			paramCode: null
		},
		showList: true,
		title:null,
		param:{},
        isReadOnly: false
	},
	methods: {
		query: function () {
			vm.reload();
		},
		reset : function() {
			vm.q.paramGroup = null;
			vm.q.paramCode = null;
			vm.query();
		},
		add: function(){
			vm.showList = false;
			vm.isReadOnly = false;
			vm.title = "新增";
			vm.param = {};
			
			$("#addModifyFlag").selectpicker({noneSelectedText:'请选择是否可改'});
			
			vm.addFormValidator();
		},
		update: function () {
			var paramId = getSelectedRow();
			if(paramId == null){
				return ;
			}
			
			vm.getParam(paramId);
            if('F' == vm.param.modifyFlag){
            	alert('选中的项不可改');
            	return;
            }
            
			vm.showList = false;
			vm.isReadOnly = true;
            vm.title = "修改";
            
            $('#addModifyFlag').selectpicker('val',(vm.param.modifyFlag));
          
           vm.addFormValidator();
		},
		del: function () {
			var paramIds = getSelectedRows();
			if(paramIds == null){
				return ;
			}
			
			confirm('确定要删除该参数？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/param/delete",
                    contentType: "application/json",
				    data: JSON.stringify(paramIds),
				    success: function(r){
						if(r.code == "00"){
							alert('操作成功', function(index){
								vm.reload();
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getParam: function(paramId) {
			$.ajaxSettings.async = false;
			$.get("../sys/param/info?paramId="+paramId, function(r){
				vm.param = r.param;
			});
			$.ajaxSettings.async = true;
		},
		saveOrUpdate: function () {
            if(!vm.validator()){
                return ;
            }

			//获取选择的菜单
			var url = vm.param.id == null ? "sys/param/add" : "sys/param/update";
			$.ajax({
				type: "POST",
			    url: "../" + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.param),
			    success: function(r){
			    	if(r.code == "00"){
						alert('操作成功', function(){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
	    reload: function () {
	    	vm.showList = true;
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'paramGroup': vm.q.paramGroup, 'paramCode': vm.q.paramCode},
                page:1
            }).trigger("reloadGrid");
		},
        validator: function () {
        	$("#form").bootstrapValidator('validate');//提交验证  
            if ($("#form").data('bootstrapValidator').isValid()) {
                return true; 
            }else {
            	return false;
            }            
        },
        addFormValidator: function() {
        	
        	var formValidator = $("#form").data('bootstrapValidator');
			if(formValidator) {
				formValidator.resetForm();
				formValidator.destroy();
		        $('#form').data('bootstrapValidator', null);
			}
			
        	$("#form").bootstrapValidator({
		　　　　　　　　message: 'This value is not valid',
		            　feedbackIcons: {
		                　　　　　　　　valid: 'glyphicon glyphicon-ok',
		                　　　　　　　　invalid: 'glyphicon glyphicon-remove',
		                　　　　　　　　validating: 'glyphicon glyphicon-refresh'
		            　　　　　　　　   },
		            fields: {		            	
		            	addParamGroup: {
		                	message: '参数分组校验失败',
		                	validators: {
		                		notEmpty: {
		                			message: '参数分组不能为空'
		                		},
		                		stringLength: {
		                            max: 32,
		                            message: '参数分组长度不能超过32位'
		                        },
		                		regexp: {
		                			regexp: /^[A-Za-z0-9_]+$/,  
                                    message: '参数分组格式不正确'
		                		}
		                	}
		                },
		                addParamCode: {
		                	validators: {
		                		notEmpty: {
		                			message: '参数编码不能为空'
		                		},
		                		stringLength: {
		                            max: 64,
		                            message: '参数编码长度不能超过64位'
		                        },
		                		regexp: {
		                			regexp: /^[A-Za-z0-9_]+$/,  
                                    message: '参数编码格式不正确'
		                		}
		                	}
		                },
		                addParamValue: {
		                	message: '参数值校验失败',
		                	validators: {		                		
		                		notEmpty: {
		                			message: '参数值不能为空'
		                		},
		                		stringLength: {
		                            max: 128,
		                            message: '参数值长度不能超过128位'
		                        },
		                		regexp: {
		                			regexp: /^[A-Za-z0-9_\[\]\{\}\(\),]+$/,  
                                    message: '参数值格式不正确'
		                		}
		                	}
		                },
		                addModifyFlag: {
		                	message: '可改标识验证失败',
		                	validators: {		                		
		                		notEmpty: {
		                			message: '可改标识不能为空'
		                		}
		                	}
		                },
		                addDescription: {
		                	message: '描述验证失败',
		                	validators: {		                		
		                		notEmpty: {
		                			message: '描述不能为空'
		                		},
		                		stringLength: {
		                            max: 256,
		                            message: '描述长度不能超过256位'
		                        },
		                		regexp: {
		                			regexp: /^[\u4e00-\u9fa5a-zA-Z0-9,:_，：-]+$/,
		                			message: '描述格式不正确'
		                		}
		                	}
		                }
		            }
		        });
        }
	}
});