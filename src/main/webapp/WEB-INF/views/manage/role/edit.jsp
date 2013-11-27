<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>更新角色</title>
    <script type="text/javascript" src="<c:url value="/resources/js/validate/role/validate.js" />"></script>
</head>
<body>


<ul class="breadcrumb" style="border-top:none; border-left: none; padding-left: 25px;">
    <li>
        <a href="#">用户管理</a> <span class="divider">/</span>
    </li>
    <li class="active">角色管理</li>
</ul>


<div class="wrapper">
<c:url var="action" value="/manage/role/update"/>
<form:form modelAttribute="role" action="${action}" method="post" id="roleForm" cssClass="form-horizontal">
    <fieldset>
        <legend>编辑角色</legend>
        <div class="control-group">
            <label class="control-label" for="id">角色ID</label>
            <div class="controls">
                <form:input path="id" readonly="true" id="id" cssClass="input-xlarge" />
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="name">角色名称</label>
            <div class="controls">
                <form:input path="name" id="name" cssClass="input-xlarge" />
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="description">角色描述</label>
            <div class="controls">
                <form:input path="description" id="description" cssClass="input-xlarge" />
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