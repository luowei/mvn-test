<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>更新用户信息</title>
</head>
<body>

<ul class="breadcrumb" style="border-top:none; border-left: none; padding-left: 25px;">
    <li>
        <a href="#">用户管理</a> <span class="divider">/</span>
    </li>
    <li class="active">人员管理</li>
</ul>


<div class="wrapper">
    <c:url var="updateAction" value="/manage/user/update"/>
    <form:form action="${updateAction}" modelAttribute="userCommand" cssClass="form-horizontal">
        <form:hidden path="id"/>
        <fieldset>
            <legend>编辑用户</legend>
            <div class="control-group">
                <label class="control-label" for="username">用户名</label>

                <div class="controls">
                    <form:input path="username" readonly="true" id="username" cssClass="input-xlarge"/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="email">Email</label>

                <div class="controls">
                    <form:input path="email" id="email" cssClass="input-xlarge"/>
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
