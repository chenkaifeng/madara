//生成菜单
var menuItem = Vue.extend({
    name: 'menu-item',
    props:{item:{}},
    template:[
        '<li>',
        '	<a v-if="item.type == \'CATALOG\'" href="javascript:;">',
        '		<i v-if="item.icon != null" :class="item.icon"></i>',
        '		<span>{{item.name}}</span>',
        '		<i class="fa fa-angle-left pull-right"></i>',
        '	</a>',
        '	<ul v-if="item.type == \'CATALOG\'" class="treeview-menu">',
        '		<menu-item :item="item" v-for="item in item.list"></menu-item>',
        '	</ul>',
        '	<a v-if="item.type == \'MENU\'" :href="\'#\'+item.url"><i v-if="item.icon != null" :class="item.icon"></i><i v-else class="fa fa-circle-o"></i> {{item.name}}</a>',
        '</li>'
    ].join('')
});

//iframe自适应
$(window).on('resize', function() {
    var $content = $('.content');
    $content.height($(this).height() - 120);
    $content.find('iframe').each(function() {
        $(this).height($content.height());
    });
}).resize();

//注册菜单组件
Vue.component('menuItem',menuItem);

//jquery ajax全局配置
$.ajaxSetup({
    dataType: "json",
    cache: false,
    layerIndex:-1,
    beforeSend: function () {
        this.layerIndex = layer.load(0, { shade: [0.5, '#393D49'] });
    },
    complete: function () {
        layer.close(this.layerIndex);
    },
});

$(document).ajaxError(function( event, request, settings ) {
    if (request.status == 401) {
        layer.msg('登录失效，请重新登录', {
            icon: 5,
            time: 500
        }, function(){
            top.location.href = "login.html";
        });
    }
});

layer.config({
    extend: 'myskin/style.css', //自定义弹出框样式
    skin: 'my-green-skin'
});

var vm = new Vue({
    el:'#app',
    data:{
        userinfo:{},
        menuList:{},
        mainPage:"dashboard",
        oldPassword:'',
        newPassword:'',
        newPasswordConfirm:'',
        navCatalogTitle:'dashboard',
        navTitle:'dashboard',
        curUserInfo:{},
        userType:{
            "ADMIN": "中心管理员",
            "MANAGER": "中心业务主管",
            "OPERATOR": "中心操作员",
            "ENT_ADMIN": "企业管理员",
            "ENT_MANAGER": "企业业务主管",
            "ENT_OPT": "企业操作员"
        },
    },
    methods: {
        getMenuList: function (event) {
            $.getJSON("sys/menu/navigate", function(r){
                vm.menuList = r.menuList;
            });
        },
        getUserInfo: function (event) {
            $.getJSON("sys/user/myInfo", function(r){
                vm.userinfo = r.info;
            });
        },
        logout: function (event) {
            $.ajax({
                type: "POST",
                url: "logout",
                success: function(result){
                    if(result.code == "00"){
                        parent.location.href = 'login.html';
                    }else{
                        layer.alert(result.msg);
                    }
                }
            });
        },
        updatePassword: function(){
            layer.open({
                type: 1,
                skin: 'my-green-skin',
                title: "修改密码",
                area: ['570px', '270px'],
                shadeClose: false,
                content: jQuery("#passwordLayer"),
                btn: ['修改','取消'],
                btn1: function (index) {
                    var data = "oldPassword="+vm.oldPassword+"&newPassword="+vm.newPassword+"&newPasswordConfirm="+vm.newPasswordConfirm;
                    $.ajax({
                        type: "POST",
                        url: "sys/user/updatePassword",
                        data: data,
                        dataType: "json",
                        success: function(result){
                            if(result.code == "00"){
                                layer.close(index);
                                layer.alert('修改成功', function(index){
                                    location.reload();
                                });
                            }else{
                                layer.alert(result.msg);
                            }
                        }
                    });
                }
            });
        }
    },
    created: function(){
        this.getMenuList();
        this.getUserInfo();
    },
    updated: function(){
        //路由
        var router = new Router();
        routerList(router, vm.menuList);
        router.start();
    }
});



function routerList(router, menuList){
    for(var key in menuList){
        var menu = menuList[key];
        if(menu.type == 'CATALOG'){
            routerList(router, menu.list);
        }else if(menu.type == 'MENU'){
            router.add('#'+menu.url, function() {
                var url = window.location.hash;

                //替换iframe的url
                vm.mainPage = url.replace('#', '');

                //导航菜单展开
                $(".treeview-menu li").removeClass("active");
                $("a[href='"+url+"']").parents("li").addClass("active");

                vm.navCatalogTitle = $("a[href='"+url+"']").parents("li").children("a[href='javascript:;']").text();
                vm.navTitle = $("a[href='"+url+"']").text();
            });
        }
    }
}
