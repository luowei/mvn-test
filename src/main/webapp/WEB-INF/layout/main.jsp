<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title><decorator:title default="用户管理系统"/></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/js/jqueryui/1.8/jquery-ui.css' />"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/bootstrap/css/bootstrap.min.css' />"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/bootstrap/css/bootstrap-responsive.min.css' />"/>

    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css" />"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/message/messages.css" />"/>

    <script type="text/javascript" src="<c:url value="/resources/js/jquery/1.7.2/jquery.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jqueryui/1.8/ui/jquery.ui.core.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jqueryui/1.8/ui/jquery.ui.widget.js" />"></script>
    <script type="text/javascript"
            src="<c:url value="/resources/js/jqueryui/1.8/ui/jquery.ui.datepicker.js" />"></script>
    <script type="text/javascript"
            src="<c:url value="/resources/js/jqueryui/1.8/ui/i18n/jquery.ui.datepicker-zh-CN.js" />"></script>

    <script type="text/javascript" src="<c:url value="/resources/js/support.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery.cookie.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/validate/jquery.validate.min.js" />"></script>

    <!--[if lt IE 9]>
    <script type="text/javascript" src="<c:url value='/resources/js/ie/IE9.js' />"></script>
    <![endif]-->

    <script type="text/javascript">
        COOKIE_MENU_ID = "cookie_menu_id";

        //获得菜单及其子菜单
        function getMenu(url){
            $.get(url, function(data){
                if(data!=null){
                    var $dt = jQuery("<dt></dt>").attr("onclick", "addMenuSub(this);").text(data.name);
                    var $dl = jQuery("<dl></dl>").append($dt);

                    jQuery.each(data.subMenus, function(index, obj){
                        var $a = jQuery("<a></a>").attr("href", "${ctx}/" + obj.url).text(obj.name);
                        var $dd = jQuery("<dd></dd>").append($a);
                        $dl.append($dd);
                    });
                    jQuery(".menu_sub").html("").append($dl);
                }
            }, "json");
        }

        //加载主导航菜单
        $(function(){
            var url = '<c:url value="/manage/menu/topLevelMenus"/>';
            $.get(url, function(data){
                jQuery.each(data, function(index, obj){
                    var $a = jQuery("<a></a>").attr("href", "#").attr("id", obj.id).text(obj.name);
                    var $li = jQuery("<li />").append($a);
                    jQuery(".menu_main").append($li);
                });


                //加载第一个菜单
                var parentId = $.cookie(COOKIE_MENU_ID);
                if(parentId == null){
                    parentId = $(".menu_main li:first a").attr("id");
                    $.cookie(COOKIE_MENU_ID, parentId, { path: '${ctx}' });
                }

                var url = "${ctx}/manage/menu/subMenu"+"/"+parentId;
                getMenu(url);
                $("#"+parentId).parent().addClass("selected").siblings().removeClass("selected");



                //导航栏选择状态
                $(".menu_main li").click(function(){
                    $(this).addClass("selected").siblings().removeClass("selected");
                });

                //动态加载子菜单
                $(".menu_main a").click(function(){
                    var parentId = $(this).attr("id");
                    if(parentId){
                        //menuId存入cookie
                        if($.cookie(COOKIE_MENU_ID)){
                            $.cookie(COOKIE_MENU_ID, null);
                        }
                        $.cookie(COOKIE_MENU_ID, parentId, { path: '${ctx}' });

                        var url = "${ctx}/manage/menu/subMenu/" + parentId;
                        getMenu(url);
                    }
                });
            }, "json");
        });
    </script>

    <decorator:head />
</head>
<body>
<div class="header">
    <div class="header_inner">
        <h1 id="logo" style="text-indent:-9999px;">用户平台管理系统</h1>
        <div id="login_bar">
            <shiro:user>
                您好,欢迎<strong>${currentUser.username}</strong>登录<a href="<c:url value="/logout" />">注销</a>
            </shiro:user>
        </div>
        <ul class="menu_main">
            <script type="text/javascript">

            </script>
        </ul>
        <div class="show_hide">
            <a href="#" class="btn_show_hide">隐藏菜单</a>
            <a href="#" class="btn_all_show">展开</a>
            <a href="#" class="btn_all_hide">折叠</a>
        </div>
    </div>
</div>


<div class="sidebar">
    <div class="sidebar_inner">
        <div class="menu_sub">

        </div>
    </div>
</div>


<div class="content">
    <div class="content_inner">
        <div class="messagePlace">
            <c:if test="${not empty message}">
                <div id="message" class="success" style="text-align: center;">${message}</div>
            </c:if>

            <c:if test="${not empty error}">
                <div id="errorMsg" class="error" style="text-align: center;">${error}</div>
            </c:if>
        </div>

        <decorator:body />
    </div>
</div>


<div class="footer">
    <div class="footer_inner">
        <p class="copyright">&copy; rootls.com版权所有</p>
    </div>
</div>
</body>
</html>