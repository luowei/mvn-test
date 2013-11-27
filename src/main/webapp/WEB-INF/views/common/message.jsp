<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:if test="${not empty message}">
    <div id="message" class="success" style="text-align: center;">${message}</div>
</c:if>

<c:if test="${not empty error}">
    <div id="errorMsg" class="error" style="text-align: center;">${error}</div>
</c:if>