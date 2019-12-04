<!-- [START list] -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<main class="main">
    <div class="container">
        <div class="company-title">
            <h3><c:out value="${action}" /> Person</h3>
        </div>
        <c:choose>
            <c:when test="${empty persons}">
                <div class="no-result center">
                    <h3>You already added all persons</h3>
                </div>
            </c:when>
            <c:otherwise>
            <form method ="POST" action ="${destination}">
                <input type="text" name="groupId" value="${groupId}" class="hidden"/>
                <div class="companies-list">
                    <div class="row">
                        <c:forEach items="${persons}" var="person">
                            <div class="col-lg-3 col-md-4 col-sm-6 col-12">
                                <div class="company_profile_info">
									<div class="company-up-info">
										<img alt="ahhh" src="${fn:escapeXml(not empty person.imageUrl?person.imageUrl:'http://placekitten.com/g/128/192')}">
										<h3>${fn:escapeXml(person.first)} ${fn:escapeXml(person.last)}</h3>
										<p>${fn:escapeXml(person.title)}</p>
									</div>
                                    <input type="checkbox" name="persons" value=${person.id} />
                                </div><!--company_profile_info end-->
                            </div>
                        </c:forEach>
                    </div>
                    <c:if test="${not empty cursor}">
                        <div c>
                            <a href="?cursor=${fn:escapeXml(cursor)}"  class="btn btn-primary btn-sm">
                                <i class="glyphicon glyphicon-edit"></i>Load More
                            </a>
                        </div>
                    </c:if>
                    <button type="submit" class="btn btn-success">Add</button>
                </div>
            </form>
            </c:otherwise>
        </c:choose>
    </div>
</main>
<!-- [END list] -->
