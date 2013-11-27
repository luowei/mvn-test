<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<font color="#FFFFFF">
    当前页:第${dataTable.currentPage}页 | 总记录数:${dataTable.totalElements}条 | 每页显示:${dataTable.pageSize}条 |
    总页数:${dataTable.totalPages}页</font>　

<c:if test="${dataTable.hasPreviousPage}">
    <c:url var="previousLink" value="/student/list?page=${dataTable.previous}"/>
    <a href="${previousLink}">上一页</a>
</c:if>

<c:if test="${dataTable.hasNextPage}">
    <c:url var="nextLink" value="/student/list?page=${dataTable.next}"/>
    <a href="${nextLink}">下一页</a>
</c:if>
<c:forEach begin="${dataTable.pageIndex.startIndex}" end="${dataTable.pageIndex.endIndex}" var="wp">
    <c:if test="${dataTable.currentPage==wp}"><b><font color="#FFFFFF">第${wp}页</font></b></c:if>
    <c:if test="${dataTable.currentPage!=wp}"><a href="javascript:topage('${wp}')" class="a03">第${wp}页</a></c:if>
</c:forEach>