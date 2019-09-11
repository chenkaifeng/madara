<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<title>用户管理</title>

</head>
<body>
<div id="app" v-cloak>
	<div v-show="showList">
		<div class="grid-btn">
			<div class="col-sm-2">
				<div class="input-group form-group">
					<span class="input-group-addon">用户名</span>
					<input type="text" class="form-control" v-model="q.name" @keyup.enter="query" placeholder="用户名">
				</div>
			</div>
			<div class="col-sm-2">
				<div class="input-group form-group">
				<span class="input-group-addon" style="width:0.01%">状态</span>
				<select class="selectpicker" v-model="q.status" data-live-search="true">
				<option value="">请选择状态</option >
				<option value="NORMAL">正常</option>
				<option value="FROZEN">冻结</option>
  				<option value="CLOSED">注销</option>
				</select>
			</div>
			</div>
			<div class="form-group col-xs-1">
				<a class="btn btn-default" @click="query"><i class="fa fa-search"></i>&nbsp;搜索</a>
			</div>
			<div class="form-group col-xs-1">
				<a class="btn btn-default" @click="reset"><i class="fa fa-undo"></i>&nbsp;重置</a>
			</div>
			
			<div class="clearfix"></div>
			<div class="pull-right" >
				<shiro:hasPermission name="sys:user:list">
					<a class="btn btn-primary" @click="detail"><i class="fa fa fa-info-circle"></i>&nbsp;详情</a>
				</shiro:hasPermission>
				
				<shiro:hasPermission name="sys:user:add">
					<a class="btn btn-primary" @click="add"><i class="fa fa-plus"></i>&nbsp;新增</a>
				</shiro:hasPermission>
				
				<shiro:hasPermission name="sys:user:update">
					<a class="btn btn-primary" @click="update"><i class="fa fa-pencil-square-o"></i>&nbsp;修改</a>
				</shiro:hasPermission>
				
				<shiro:hasPermission name="sys:user:unfreeze">
					<a class="btn btn-primary" @click="unfreeze"><i class="fa fa fa-info-circle"></i>&nbsp;解冻</a>
				</shiro:hasPermission>
				
				<shiro:hasPermission name="sys:user:close">
					<a class="btn btn-primary" @click="close"><i class="fa fa-trash-o"></i>&nbsp;注销</a>
				</shiro:hasPermission>
				
				<shiro:hasPermission name="sys:user:resetPassword">
					<a class="btn btn-primary" @click="resetPassword"><i class="fa fa fa-info-circle"></i>&nbsp;重置密码</a>
				</shiro:hasPermission>
			</div>
			<div class="clearfix"></div>
		</div>
		
	    <table id="jqGrid"></table>
	    <div id="jqGridPager"></div>
    </div>
    
    <div v-show="!showList" class="panel panel-default">
		<div class="panel-heading">{{title}}</div>
		<form class="form-horizontal" style="width:550px;padding-top:20px;">
			<div class="form-group">
			   	<div class="col-sm-2 control-label">用户编码</div>
			   	<div class="col-sm-10">
			      <input type="text" class="form-control" v-model="user.userCode"  v-bind:readonly="isReadOnly"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">用户名</div>
			   	<div class="col-sm-10">
			      <input type="text" class="form-control" v-model="user.name" />
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">备注</div>
			   	<div class="col-sm-10">
			      <input type="text" class="form-control" v-model="user.remark" />
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">角色</div>
			   	<div class="col-sm-10">
				   	<label v-for="role in roleList" class="checkbox-inline">
					  <input type="checkbox" :value="role.id" v-model="user.roleIdList">{{role.name}}
					</label>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-2 control-label"></div> 
				<input type="button" class="btn btn-primary" @click="addOrUpdate" value="确定"/>
				&nbsp;&nbsp;<input type="button" class="btn btn-warning" @click="reload" value="返回"/>
			</div>
		</form>
	</div>
	
	<div id="userDetailLayer" style="display: none;"class="panel panel-default">
			<table class="table table-bordered detailtable" style="word-break:break-all; word-wrap:break-all;">
				<tbody>
					<tr>
						<th class="col-xs-1">用户编码</th>
						<td class="col-xs-1"><strong>{{user.userCode}}</strong></td>
						<th class="col-xs-1">用户名</th>
						<td class="col-xs-1"><strong>{{user.name}}</strong></td>
					</tr>
					<tr>
						<th class="col-xs-1">状态</th>
						<td class="col-xs-1"><strong>{{getStatus_CN(user.status)}}</strong></td>
						<th class="col-xs-1">角色</th>
						<td class="col-xs-1"><strong>{{strJsonArray(user.roleName)}}</strong></td>
					</tr>
					<tr>
						<th class="col-xs-1">连续登录失败次数</th>
						<td class="col-xs-1"><strong>{{user.loginFailNum}}</strong></td>
						<th class="col-xs-1">上次修改密码时间</th>
						<td class="col-xs-1"><strong>{{user.changePwdTime}}</strong></td>
					</tr>
					<tr>
						<th class="col-xs-1">创建人</th>
						<td class="col-xs-1"><strong>{{user.createUserCode}}</strong></td>
						<th class="col-xs-1">创建时间</th>
						<td class="col-xs-1"><strong>{{user.gmtCreate}}</strong></td>
					</tr>
					<tr>
						<th class="col-xs-1">更新时间</th>
						<td class="col-xs-1"><strong>{{user.gmtUpdate}}</strong></td>
						<th class="col-xs-1">上次登录时间</th>
						<td class="col-xs-1"><strong>{{user.lastLoginTime}}</strong></td>
					</tr>
					<tr>
						<th class="col-xs-1">备注</th>
						<td class="col-xs-1"><strong>{{user.remark}}</strong></td>
					</tr>
				</tbody>
			</table>
		</div>
</div>

<script src="${ctx}/static/js/sys/user.js"></script>
</body>
</html>