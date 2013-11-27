<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>分配角色</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/doubleSelect.css" />"/>
    <script type="text/javascript" src="<c:url value="/resources/js/doubleSelect.js" />"></script>
    <script type="text/javascript">
        $(function () {
            $("#form1").submit(function () {
                var $select1Option = $("#select1 option");
                var $select2Option = $("#select2 option");

                if ($select1Option.size() > 0) {
                    $select1Option.attr("selected", "selected");
                }

                if ($select2Option.size() > 0) {
                    $select2Option.attr("selected", "selected");
                }

                return true;
            });
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
    <c:url var="action" value="/manage/user/assignRoles"/>
    <form action="${action}" method="post" id="form1" class="form-horizontal">
        <input type="hidden" name="userId" value="${user.id}"/>

        <fieldset>
            <legend>为用户分配角色</legend>
            <div class="control-group">
                <label class="control-label">用户</label>

                <div class="controls">
                    <span class="help-inline">${user.username}</span>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">角色</label>

                <div class="controls">
                    <div class="doubleSelect">
                        <div class="selectContent">
                            <label>待分配角色</label>
                            <select name="ids1" multiple="multiple" id="select1">
                                <c:forEach items="${roleList1}" var="entry">
                                    <option value="${entry.id}">${entry.description}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="selectContent doubleSelectButtons">
                            <span id="add" class="ui-corner-all" title="选中添加到右边">&nbsp;</span>
                            <span id="addAll" class="ui-corner-all" title="全部添加到右边">&nbsp;</span>
                            <span id="remove" class="ui-corner-all" title="选中删除到左边">&nbsp;</span>
                            <span id="removeAll" class="ui-corner-all" title="全部删除到左边">&nbsp;</span>
                        </div>

                        <div class="selectContent">
                            <label>已分配角色</label>
                            <select name="ids2" multiple="multiple" id="select2">
                                <c:forEach items="${roleList2}" var="entry">
                                    <option value="${entry.id}">${entry.description}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
            </div>

            <div class="control-group">
                <div class="controls">
                    <input type="submit" value="保存" class="btn btn-primary" style="margin-right: 50px;"/>
                    <a class="btn" onclick="back();">返回</a>
                </div>
            </div>

        </fieldset>
    </form>
</div>
</body>
</html>
