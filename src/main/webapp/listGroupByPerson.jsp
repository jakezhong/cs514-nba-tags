
<%--
  Created by IntelliJ IDEA.
  User: vito
  Date: 11/9/2019
  Time: 2:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

        <h2>The Group that enjoyed !</h2>
        <c:choose>
            <c:when test="${empty groups}">
                <p>No groups found</p>
            </c:when>
            <c:otherwise>
                <c:forEach items="${groups}" var="group">
                    <div class="media">
                        <a href="/readGroup?id=${group.id}">
                            <div class="media-left">
                                <img alt="ahhh" height="200"src="${fn:escapeXml(not empty group.imageUrl?group.imageUrl:'http://placekitten.com/g/128/192')}">
                            </div>
                            <div class="media-body">
                                <h4>${fn:escapeXml(group.name)}</h4>
                            </div>
                        </a>
                    </div>
                </c:forEach>
                <c:if test="${not empty cursor}">
                    <nav>
                        <ul class="pager">
                            <li><a href="?cursor=${fn:escapeXml(cursor)}">More</a></li>
                        </ul>
                    </nav>
                </c:if>
            </c:otherwise>
        </c:choose>
</body>
</html>
