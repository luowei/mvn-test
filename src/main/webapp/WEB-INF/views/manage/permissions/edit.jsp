<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>更新角色</title>
    <script type="text/javascript" src="<c:url value="/resources/js/validate/permissions/validate.js" />"></script>
</head>
<body>


<ul class="breadcrumb" style="border-top:none; border-left: none; padding-left: 25px;">
    <li>
        <a href="#">用户管理</a> <span class="divider">/</span>
    </li>
    <li class="active">权限管理</li>
</ul>


<div class="wrapper">
<c:url var="action" value="/manage/permissions/update"/>
<form:form modelAttribute="permissions" action="${action}" method="post" id="permissionsForm" cssClass="form-horizontal">
    <fieldset>
        <legend>编辑权限</legend>
        <div class="control-group">
            <label class="control-label" for="id">权限ID</label>
            <div class="controls">
                <form:input path="id" readonly="true" id="id" cssClass="input-xlarge" />
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">权限名称</label>
            <div class="controls">
                <form:input path="permission" cssClass="input-xlarge" />
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">权限描述</label>
            <div class="controls">
                <form:input path="description" cssClass="input-xlarge" />
            </div>
        </div>


        <div class="control-group">
            <div class="controls">
                <input type="submit" value="更新" class="btn btn-primary btn-margin-right"/>
                <a class="btn" onclick="back();">返回</a>
            </div>
        </div>
    </fieldset>
</form:form>
</div>

</body>
</html>