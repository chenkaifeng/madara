<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="../header.jsp" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>角色管理</title>

</head>
<body>
<div id="app" v-cloak>
    <div v-show="showList">
        <div class="grid-btn">
            <div class="col-sm-4">
                <div class="input-group form-group">
                    <span class="input-group-addon">角色名称</span>
                    <input type="text" class="form-control" v-model="q.name" @keyup.enter="query" placeholder="角色名称">
                </div>
            </div>
            <div class="col-sm-4">
                <div class="input-group form-group">
                    <span class="input-group-addon pre-select-span">状态</span>
                    <vm-query-select id="status" :options="roleStatusList" v-model="q.status"
                                     data-live-search="true"></vm-query-select>
                </div>
            </div>
            <div class="form-group col-xs-1">
                <a class="btn btn-default" @click="query"><i class="fa fa-search"></i>&nbsp;搜索</a>
            </div>
            <div class="form-group col-xs-1">
                <a class="btn btn-default" @click="reset"><i class="fa fa-undo"></i>&nbsp;重置</a>
            </div>

            <div class="clearfix"></div>
            <div class="pull-left">
                <div class="form-group col-xs-12">
                    <shiro:hasPermission name="sys:role:list">
                        <a class="btn btn-success" @click="detail"><i class="fa fa fa-info-circle"></i>&nbsp;详情</a>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="sys:role:add">
                        <a class="btn btn-success" @click="add"><i class="fa fa-plus"></i>&nbsp;新增</a>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="sys:role:update">
                        <a class="btn btn-success" @click="update"><i class="fa fa-pencil-square-o"></i>&nbsp;修改</a>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="sys:role:close">
                        <a class="btn btn-success" @click="close"><i class="fa fa-trash-o"></i>&nbsp;注销</a>
                    </shiro:hasPermission>
                </div>
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
                <div class="col-sm-2 control-label">角色编码</div>
                <div class="col-sm-10">
                    <input type="text" class="form-control" v-model="role.roleCode" v-bind:readonly="isReadOnly"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">角色名称</div>
                <div class="col-sm-10">
                    <input type="text" class="form-control" v-model="role.name"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">备注</div>
                <div class="col-sm-10">
                    <input type="text" class="form-control" v-model="role.remark"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">权限</div>
                <div class="col-sm-10">
                    <ul id="menuTree" class="ztree"></ul>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label"></div>
                <input type="button" class="btn btn-success" @click="saveOrUpdate" value="确定"/>
                &nbsp;&nbsp;<input type="button" class="btn btn-warning" @click="reload" value="返回"/>
            </div>
        </form>
    </div>

    <div id="roleDetailLayer" style="display: none;" class="panel panel-default">
        <table class="table table-bordered detailtable">
            <tbody>
            <tr>
                <th class="col-xs-1">角色编码</th>
                <td class="col-xs-1"><strong>{{role.roleCode}}</strong></td>
                <th class="col-xs-1">角色名</th>
                <td class="col-xs-1"><strong>{{role.name}}</strong></td>
            </tr>
            <tr>
                <th class="col-xs-1">状态</th>
                <td class="col-xs-1"><strong>{{getLabelByValue(roleStatusList,role.status)}}</strong></td>
                <th class="col-xs-1">备注</th>
                <td class="col-xs-1"><strong>{{role.remark}}</strong></td>
            </tr>
            <tr>
                <th class="col-xs-1">创建人</th>
                <td class="col-xs-1"><strong>{{role.createUserCode}}</strong></td>
                <th class="col-xs-1">创建时间</th>
                <td class="col-xs-1"><strong>{{role.gmtCreate}}</strong></td>
            </tr>
            <tr>
                <th class="col-xs-1">修改人</th>
                <td class="col-xs-1"><strong>{{role.updateUserCode}}</strong></td>
                <th class="col-xs-1">修改时间</th>
                <td class="col-xs-1"><strong>{{role.gmtUpdate}}</strong></td>
            </tr>
            <tr>
                <th class="col-xs-1">权限</th>
                <td class="col-xs-1">
                    <ul id="menuTreeReadOnly" class="ztree"></ul>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>

<script src="${ctx}/static/js/sys/role.js"></script>
</body>
</html>