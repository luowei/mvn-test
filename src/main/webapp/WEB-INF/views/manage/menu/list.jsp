<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="p" uri="/WEB-INF/tag/page" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title></title>

    <script type="text/javascript">
        $(function () {
            checkAll("all", "ids");

            var action = '<c:url value="/manage/menu/batchDelete" />';
            checkBeforeBatchDelete("batchDeleteButton", "menuForm", action, "menuIds");
        });

        $(function () {
            direction("id");
            direction("name");
            direction("sequence");
            direction("createTime");
        });


    </script>

</head>
<body>

<ul class="breadcrumb" style="border-top:none; border-left: none; padding-left: 25px;">
    <li>
        <a href="#">用户管理</a> <span class="divider">/</span>
    </li>
    <li class="active">菜单管理</li>
</ul>


<div class="wrapper">

    <c:url var="action" value="/manage/menu/list"/>
    <form:form modelAttribute="menuCommand" action="${action}" method="post" cssClass="well form-inline" id="form1">
        <form:input path="searchKey1" cssClass="input-medium search-query" placeholder="菜单名称"/>
        <form:input path="startTime" cssClass="input-medium search-query datepicker" placeholder="开始时间"/>


        <button type="submit" class="btn btn-success">
            <i class="icon-search icon-white"></i>搜索
        </button>

        <a href="<c:url value="/manage/menu/add" />" class="btn btn-primary">添加新菜单</a>
    </form:form>


    <c:if test="${dataTable.hasContent}">
        <c:url var="sort" value="${order}"/>
        <form:form modelAttribute="menuCommand" action="${action}" method="post" id="menuForm">
            <table class="table table-bordered table-striped table-condensed">
                <thead>
                <tr>
                    <shiro:hasPermission name="menu:delete">
                        <th><input type="checkbox" id="all"/></th>
                    </shiro:hasPermission>

                    <th>序号</th>
                    <th>
                        <a href="#" id="id" class="${sort}" onclick="sort(this);">菜单ID<i></i></a>
                    </th>
                    <th>
                        <a href="#" id="name" class="${sort}" onclick="sort(this);">菜单名<i></i></a>
                    </th>
                    <th>
                        <a href="#" id="sequence" class="${sort}" onclick="sort(this);">菜单排序<i></i></a>
                    </th>
                    <th>
                        <a href="#" id="createTime" class="${sort}" onclick="sort(this);">菜单创建时间<i></i></a>
                    </th>
                    <shiro:hasPermission name="menu:update">
                        <th>更新</th>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="menu:delete">
                        <th>删除</th>
                    </shiro:hasPermission>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${dataTable.content}" var="entry" varStatus="st">
                    <tr>
                        <shiro:hasPermission name="menu:delete">
                            <td><form:checkbox path="menuIds" value="${entry.id}" /></td>
                        </shiro:hasPermission>

                        <td>${index+st.count}</td>
                        <td>${entry.id}</td>
                        <td>${entry.name}</td>
                        <td>${entry.sequence}</td>
                        <td>${entry.createTime}</td>

                        <shiro:hasPermission name="menu:update">
                            <td>
                                <s:url var="updateLink" value="/manage/menu/edit/{id}">
                                    <s:param name="id" value="${entry.id}"/>
                                </s:url>
                                <a href="${updateLink}" class="btn btn-info">修改</a>
                            </td>
                        </shiro:hasPermission>

                        <shiro:hasPermission name="menu:delete">
                            <td>
                                <s:url var="deleteLink" value="/manage/menu/delete/{id}">
                                    <s:param name="id" value="${entry.id}"/>
                                </s:url>
                                <a href="${deleteLink}" class="btn btn-danger" onclick="return del();">
                                    删除
                                </a>
                            </td>
                        </shiro:hasPermission>
                    </tr>
                </c:forEach>
                <shiro:hasPermission name="menu:delete">
                    <tr>
                        <td colspan="8">
                            <button id="batchDeleteButton" class="btn btn-danger">
                                批量删除
                            </button>
                        </td>
                    </tr>
                </shiro:hasPermission>
                </tbody>
            </table>

            <p:page dataTable="${dataTable}"/>
        </form:form>
    </c:if>

    <c:if test="${not dataTable.hasContent}">
        <strong>没有相应的记录</strong>
    </c:if>
</div>

</body>
</html>
