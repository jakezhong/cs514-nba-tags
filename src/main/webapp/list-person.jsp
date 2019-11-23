<!-- [START list] -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<main class="main">
    <div class="container">
        <div class="company-title">
            <h3>All Persons</h3>
        </div>
        <div class="landing-header">
            <a href="/person/create" class="btn btn-success btn-sm">
                <i class="glyphicon glyphicon-plus"></i>
                Add person
            </a>
        </div>
        <c:choose>
            <c:when test="${empty persons}">
                <div class="no-result center">
                    <h3>No persons found</h3>
                </div>
            </c:when>
            <c:otherwise>
                <div class="companies-list">
                    <div class="row">
                        <c:forEach items="${persons}" var="person">
                            <div class="col-lg-3 col-md-4 col-sm-6 col-12">
                                <div class="company_profile_info">
                                    <a href="/person/read?id=${person.id}" title="">
                                        <div class="company-up-info">
                                            <img alt="ahhh" height="200" src="${fn:escapeXml(not empty person.imageUrl?person.imageUrl:'http://placekitten.com/g/128/192')}">
                                            <h3>${fn:escapeXml(person.first)} ${fn:escapeXml(person.last)}</h3>
                                            <h4>${fn:escapeXml(person.title)}</h4>
                                            <h5 class="category">${fn:escapeXml(person.category)}</h5>
                                        </div>
                                        <div class="view-more-pro">View Profile</div>
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
