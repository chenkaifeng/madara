<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<title>系统参数管理</title>

</head>
<body>
<div id="app" v-cloak>
	<div v-show="showList">
		<div class="grid-btn">
			<div class="col-sm-2">
				<div class="input-group form-group">
					<span class="input-group-addon">参数分组</span>
					<input type="text" class="form-control" v-model="q.paramGroup" @keyup.enter="query" placeholder="参数分组">
				</div>
			</div>
			<div class="col-sm-2">
				<div class="input-group form-group">
					<span class="input-group-addon">参数编码</span>
					<input type="text" class="form-control" v-model="q.paramCode" @keyup.enter="query" placeholder="参数编码">
				</div>
			</div>
			
			<div class="form-group col-xs-1">
				<a class="btn btn-success" @click="query"><i class="fa fa-search"></i>&nbsp;搜索</a>
			</div>
			
			<div class="form-group col-xs-1">
				<a class="btn btn-success" @click="reset"><i class="fa fa-undo"></i>&nbsp;重置</a>
			</div>
			
			<div class="clearfix"></div>
				<div class="pull-right" >
					<shiro:hasPermission name="sys:param:add">
						<a class="btn btn-primary" @click="add"><i class="fa fa-plus"></i>&nbsp;新增</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:param:update">
						<a class="btn btn-primary" @click="update"><i class="fa fa-pencil-square-o"></i>&nbsp;修改</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:param:delete">
						<a class="btn btn-primary" @click="del"><i class="fa fa-trash-o"></i>&nbsp;删除</a>
					</shiro:hasPermission>
				</div>
			<div class="clearfix"></div>
			
		</div>
	    <table id="jqGrid"></table>
	    <div id="jqGridPager"></div>
    </div>
    
    <div v-show="!showList" class="panel panel-default">
		<div class="panel-heading">{{title}}</div>
		<form id="form" class="form-horizontal" style="width:550px;padding-top:20px;">
			<div class="form-group">
			   	<div class="col-sm-2 control-label">参数分组</div>
			   	<div class="col-sm-10">
			      <input name="addParamGroup" type="text" class="form-control" v-model="param.paramGroup" v-bind:readonly="isReadOnly"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">参数编码</div>
			   	<div class="col-sm-10">
			      <input name="addParamCode" type="text" class="form-control" v-model="param.paramCode" v-bind:readonly="isReadOnly"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">参数值</div>
			   	<div class="col-sm-10">
			      <input name="addParamValue" type="text" class="form-control" v-model="param.paramValue" />
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">描述</div>
			   	<div class="col-sm-10">
			      <textarea name="addDescription" class="form-control" rows="3" v-model="param.description" 
			      style="width:380px;height:100px;"></textarea>
			    </div>
			</div>
			<div class="form-group">
				<div class="col-sm-2 control-label">是否可改</div>
				<div class="col-sm-10">
					<select name="addModifyFlag" id='addModifyFlag' class="selectpicker" v-model="param.modifyFlag" data-live-search="true">
						<option value="">请选择是否可改</option>
						<option value="T">是</option>
						<option value="F">否</option>
					</select>
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-2 control-label"></div> 
				<input type="button" class="btn btn-primary" @click="saveOrUpdate" value="确定"/>
				&nbsp;&nbsp;<input type="button" class="btn btn-warning" @click="reload" value="返回"/>
			</div>
		</form>
	</div>
</div>

<script src="${ctx}/static/js/sys/param.js"></script>
</body>
</html>