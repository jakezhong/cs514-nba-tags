<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<main class="main">
    <div class="container">
        <div class="company-title">
            <h3>The Group that enjoyed!</h3>
        </div>
        <c:choose>
            <c:when test="${empty groups}">
                <div class="no-result center">
                    <h3>No groups found</h3>
                </div>
            </c:when>
            <c:otherwise>
                <div class="companies-list">
                    <div class="row">
                        <c:forEach items="${groups}" var="group">
                            <div class="col-lg-3 col-md-4 col-sm-6 col-12">
                                <div class="company_profile_info">
                                    <div class="company-up-info">
                                        <img alt="ahhh" height="200"src="${fn:escapeXml(not empty group.imageUrl?group.imageUrl:'http://placekitten.com/g/128/192')}">
                                        <h3>${fn:escapeXml(group.name)}</h3>
                                    </div>
                                    <a href="/readGroup?id=${group.id}" title="" class="view-more-pro">View Detail</a>
                                </div><!--company_profile_info end-->
                            </div>
                        </c:forEach>
                        <div class="process-comm">
                            <a href="?cursor=${fn:escapeXml(cursor)}" title=""><img src="images/process-icon.png" alt=""></a>
                        </div>
                        <c:if test="${not empty cursor}">
                            <nav>
                                <ul class="pager">
                                    <li><a href="?cursor=${fn:escapeXml(cursor)}">More</a></li>
                                </ul>
                            </nav>
                        </c:if>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</main>
