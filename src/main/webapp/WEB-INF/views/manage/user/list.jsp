<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="p" uri="/WEB-INF/tag/page" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>人员管理</title>
    <script type="text/javascript">
        $(function(){
            direction("id");
            direction("username");
            direction("email");

            checkAll("all", "ids");

            var action = '<c:url value="/manage/user/batchDelete" />';
            checkBeforeBatchDelete("batchDeleteButton", "form1", action, "ids");

        });
    </script>
</head>
<body>

<ul class="breadcrumb" style="border-top:none; border-left: none; padding-left: 25px;">
    <li>
        <a href="#">用户管理</a> <span class="divider">/</span>
    </li>
    <li class="active">人员管理</li>
</ul>


<div class="wrapper">
    <c:url var="action" value="/manage/user/list"/>
    <form:form modelAttribute="userCommand" action="${action}" method="post" cssClass="well form-inline">
        <form:input path="searchKey1" cssClass="input-medium search-query" placeholder="用户名" />
        <form:input path="startTime" cssClass="input-medium search-query datepicker" placeholder="开始时间" />


        <button type="submit" class="btn btn-success">
            <i class="icon-search icon-white"></i>搜索
        </button>
    </form:form>


    <c:if test="${dataTable.hasContent}">
        <c:url var="sort" value="${order}"/>
        <form:form modelAttribute="userCommand" action="${action}" method="post" id="form1">
            <table class="table table-bordered table-striped table-condensed">
                <thead>
                <tr>
                    <shiro:hasPermission name="user:delete">
                        <th><input type="checkbox" id="all"/></th>
                    </shiro:hasPermission>
                    <th>序号</th>
                    <th>
                        <a href="#" id="id" class="${sort}" onclick="sort(this);">用户ID<i></i></a>
                    </th>
                    <th>
                        <a href="#" id="username" class="${sort}" onclick="sort(this);">用户名<i></i></a>
                    </th>
                    <th>
                        <a href="#" id="email" class="${sort}" onclick="sort(this);">邮箱<i></i></a>
                    </th>

                    <shiro:hasPermission name="user:update">
                        <th>更新</th>
                    </shiro:hasPermission>

                    <shiro:hasPermission name="user:delete">
                        <th>删除</th>
                    </shiro:hasPermission>

                    <shiro:hasPermission name="user:assignRoles">
                        <th>分配角色</th>
                    </shiro:hasPermission>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${dataTable.content}" var="entry" varStatus="st">
                    <tr>
                        <shiro:hasPermission name="user:delete">
                            <td><form:checkbox path="ids" value="${entry.id}"/></td>
                        </shiro:hasPermission>
                        <td>${index+st.count}</td>
                        <td>${entry.id}</td>
                        <td>${entry.username}</td>
                        <td>${entry.email}</td>

                        <shiro:hasPermission name="user:update">
                        <td>
                            <spring:url var="updateLink" value="/manage/user/edit/{id}">
                                <spring:param name="id" value="${entry.id}"/>
                            </spring:url>
                            <a href="${updateLink}" class="btn btn-info">修改</a>
                        </td>
                        </shiro:hasPermission>

                        <shiro:hasPermission name="user:delete">
                        <td>
                            <spring:url var="deleteLink" value="/manage/user/delete/{id}">
                                <spring:param name="id" value="${entry.id}"/>
                            </spring:url>
                            <a href="${deleteLink}" class="btn btn-danger" onclick="return del();">
                                删除
                            </a>
                        </td>
                        </shiro:hasPermission>

                        <shiro:hasPermission name="user:assignRoles">
                        <td>
                            <spring:url var="assignRolesLink" value="/manage/user/assignRolesPre/{id}">
                                <spring:param name="id" value="${entry.id}" />
                            </spring:url>
                            <a href="${assignRolesLink}" class="btn btn-success">分配角色</a>
                        </td>
                        </shiro:hasPermission>
                    </tr>
                </c:forEach>

                <shiro:hasPermission name="user:delete">
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
