<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>登录</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/bootstrap/css/bootstrap.min.css' />"/>
    <link rel="stylesheet" type="text/css"
          href="<c:url value='/resources/bootstrap/css/bootstrap-responsive.min.css' />"/>

    <style type="text/css">
        body {
            background: #f5f5f5;
        }

        #login {
            background: #fff;
            width: 700px;
            margin: 0 auto;
            margin-top: 20px;
            vertical-align:middle;
        }

        .well {
            background: #fff;
        }

        .form-actions {
            margin-left: 0;
            padding-left: 18px;
            margin-top: 10px;
            margin-bottom: 10px;
        }

        #username, #password {
            padding-right: 38px;
        }

        .error {
            color: #B94A48;
        }

        span.error {
            padding: 5px;
            margin-left: 10px;
            margin-bottom: 5px;
        }
    </style>

    <script type="text/javascript" src="<c:url value="/resources/js/jquery/1.7.2/jquery.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/validate/jquery.validate.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/validate/login/validate.js" />"></script>
</head>
<body>

<div id="login" class="row">
    <c:url var="action" value="/loginProcess"/>
    <form:form modelAttribute="loginCommand" action="${action}" method="post" cssClass="well" id="loginForm">
        <fieldset>
            <legend>用户登录</legend>

            <c:if test="${not empty param.error}">
                <p class="error" style="margin-left: 90px;">用户名或密码不正确</p>
            </c:if>

            <label>用户名</label>
            <form:input path="username" cssClass="span4" placeholder="用户名"/>
            <form:errors path="username" cssClass="help-inline error"/>

            <label>密码</label>
            <form:password path="password" cssClass="span4" placeholder="密码"/>
            <form:errors path="password" cssClass="help-inline error"/>

            <label class="checkbox">
                <input type="checkbox" name="rememberMe"> Remember me
            </label>

            <div class="form-actions span4">
                <button type="submit" class="btn btn-primary">登录</button>
            </div>
        </fieldset>
    </form:form>
</div>

</body>
</html>
