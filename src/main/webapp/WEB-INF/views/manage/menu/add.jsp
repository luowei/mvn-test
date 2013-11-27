<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>添加菜单</title>
    <script type="text/javascript" src="<c:url value="/resources/js/validate/menu/validate.js" />"></script>
</head>
<body>


<ul class="breadcrumb" style="border-top:none; border-left: none; padding-left: 25px;">
    <li>
        <a href="#">用户管理</a> <span class="divider">/</span>
    </li>
    <li class="active">菜单管理</li>
</ul>


<div class="wrapper">
<form:form modelAttribute="menu" method="post" id="menuForm" cssClass="form-horizontal">
    <fieldset>
        <legend>添加菜单</legend>

        <div class="control-group">
            <label class="control-label" for="name">菜单名称</label>
            <div class="controls">
                <form:input path="name" id="name" cssClass="span3" />
                <form:errors path="name" cssClass="help-inline error"/>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="url">菜单url</label>
            <div class="controls">
                <form:input path="url" id="url" cssClass="span3" />
                <span class="help-inline">例如：manage/menu/list,不以/开头</span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="parentId">父级菜单</label>
            <div class="controls">
                <select id="parentId" name="parentId" class="span3">
                    <option value="0">顶级菜单</option>
                    <c:if test="${not empty topLevelMenuList}">
                        <c:forEach items="${topLevelMenuList}" var="entry">
                            <option value="${entry.id}">${entry.name}</option>
                        </c:forEach>
                    </c:if>
                </select>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="sequence">菜单次序</label>
            <div class="controls">
                <form:input path="sequence" id="sequence" cssClass="span3" />
                <span class="help-inline">例如：1,1.1,1.2.1</span>
                <form:errors path="sequence" cssClass="help-inline error"/>
            </div>
        </div>

        <div class="control-group">
            <div class="controls">
                <input type="submit" value="添加" class="btn btn-primary btn-margin-right"/>
                <a class="btn" onclick="back();">返回</a>
            </div>
        </div>
    </fieldset>
</form:form>
</div>

</body>
</html>