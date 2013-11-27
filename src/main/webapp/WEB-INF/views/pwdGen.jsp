<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>密码生成器</title>

    <script type="text/javascript">
        $(function () {
            $("#form1").submit(function () {
                var value = $("#rawPassword").val().trim();
                $("#result").html("");
                if (value != "") {
                    $.get('<c:url value="/generatePassword" />',
                            {rawPassword:value},
                            function (data) {
                                $("#result").html(data);
                            }

                    );
                }

                return false;
            });
        });
    </script>
</head>
<body>


<ul class="breadcrumb" style="border-top:none; border-left: none; padding-left: 25px;">
    <li>
        <a href="#">杂项</a> <span class="divider">/</span>
    </li>

    <li class="active">
        <a href="#">生成密码</a>
    </li>

</ul>

<div class="wrapper">
    <form class="form-horizontal" id="form1">
        <div class="control-group">
            <label class="control-label">请输入密码</label>

            <div class="controls">
                <input type="text" name="rawPassword" id="rawPassword"/>
                <button class="btn btn-inverse">生成</button>
            </div>
        </div>

        <div class="control-group">
            <div class="controls">
               <strong id="result"></strong>
            </div>
        </div>
    </form>
</div>

</body>
</html>
