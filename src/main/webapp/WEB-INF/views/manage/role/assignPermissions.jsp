<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>分配权限</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/js/zTree/3.2/zTreeStyle/zTreeStyle.css" />"/>
    <script type="text/javascript" src="<c:url value="/resources/js/zTree/3.2/jquery.ztree.core.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/zTree/3.2/jquery.ztree.excheck.js" />"></script>
    <script type="text/javascript">
        var setting = {
            view:{showLine: true, dblClickExpand: false},
            check: {
                enable: true,
                chkboxType:{"Y":"s", "N":"ps"}
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pId"
                }
            },
            callback:{
                onClick: onClick,
                onCheck: onCheck
            }
        };

        var zNodes = ${permissionJson};

        function onClick(e,treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("permissionTree");
            zTree.expandNode(treeNode);
        }

        function onCheck(e, treeId, treeNode){
            var zTree = $.fn.zTree.getZTreeObj("permissionTree");
            var checkedNodes = zTree.getCheckedNodes(true);
            var uncheckedCount = zTree.getCheckedNodes(false).length;
            var checkedCount = checkedNodes.length;
            var totalCount = checkedCount + uncheckedCount;

            if(checkedCount == 0 && uncheckedCount == totalCount){
                $("#unchecked").attr("value", "true");
            }

            $("input[name=ids2]").remove();


            for(var i=0; i<checkedNodes.length; i++){
                $("<input type='hidden' name='ids2'/>")
                        .attr("value", checkedNodes[i].id)
                        .appendTo("#form1");
            }

        }

        $(function(){
            $.fn.zTree.init($("#permissionTree"), setting, zNodes);
            var zTree = $.fn.zTree.getZTreeObj("permissionTree");

            var checkedNodes = zTree.getCheckedNodes(true);

            for(var i=0; i<checkedNodes.length; i++){
                $("<input type='hidden' name='ids1'/>")
                        .attr("value", checkedNodes[i].id)
                        .appendTo("#form1");
            }
        });
    </script>
</head>
<body>

<ul class="breadcrumb" style="border-top:none; border-left: none; padding-left: 25px;">
    <li>
        <a href="#">用户管理</a> <span class="divider">/</span>
    </li>
    <li class="active">角色管理</li>
</ul>

<div class="wrapper">
    <c:url var="action" value="/manage/role/assignPermissions"/>
    <form action="${action}" method="post" id="form1" class="form-horizontal">
        <input type="hidden" name="roleId" value="${role.id}"/>
        <input type="hidden" name="unchecked" id="unchecked" value="false" />

        <fieldset>
            <legend>为角色分配权限</legend>
            <div class="control-group">
                <label class="control-label">角色</label>
                <div class="controls">
                    <span class="help-inline">${role.description}</span>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">权限</label>
                <div class="controls">
                    <ul id="permissionTree" class="ztree">

                    </ul>
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
