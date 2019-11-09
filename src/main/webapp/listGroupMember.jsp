<%--
  Created by IntelliJ IDEA.
  User: vito
  Date: 11/9/2019
  Time: 12:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <h2>Group member</h2>
    <!---to find the member of persons and display, return a lits of persons -->
    <c:choose>
        <c:when test="${empty persons}">
            <p>No persons found</p>
        </c:when>
        <c:otherwise>
            <c:forEach items="${persons}" var="person">
                <div class="media">
                    <a href="/read?id=${person.id}">
                        <div class="media-left">
                            <img alt="ahhh" height="200"src="${fn:escapeXml(not empty person.imageUrl?person.imageUrl:'http://placekitten.com/g/128/192')}">
                        </div>
                        <div class="media-body">
                            <h4>${fn:escapeXml(person.first)}</h4>
                            <p>${fn:escapeXml(person.last)}</p>
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
</head>
<body>

</body>
</html>
