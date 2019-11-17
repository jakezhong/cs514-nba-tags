<!-- [START list] -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<main class="main">
    <div class="container">
        <div class="company-title">
            <h3>All Groups</h3>
        </div>
        <div class="landing-header">
            <a href="create" class="btn btn-success btn-sm">
                <i class="glyphicon glyphicon-plus"></i>
                Add group
            </a>
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
                                    <a href="/group/read?id=${group.id}" title="">
                                        <div class="company-up-info">
                                            <img alt="ahhh" height="200"src="${fn:escapeXml(not empty group.imageUrl?group.imageUrl:'http://placekitten.com/g/128/192')}">
                                            <h3>${fn:escapeXml(group.name)}</h3>
                                            <h5 class="category">${fn:escapeXml(group.category)}</h5>
                                        </div>
                                        <div class="view-more-pro">View Detail</div>
                                    </a>
                                </div><!--company_profile_info end-->
                            </div>
                        </c:forEach>
                    </div>
                    <c:if test="${not empty cursor}">
                        <div class="read-more">
                            <a href="?cursor=${fn:escapeXml(cursor)}"  class="btn btn-primary btn-sm">
                                <i class="glyphicon glyphicon-edit"></i>More
                            </a>
                        </div>
                    </c:if>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</main>
<!-- [END list] -->
