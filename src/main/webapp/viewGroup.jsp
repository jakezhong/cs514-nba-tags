<!-- [START view] -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:url value="/ui" var="main_ui" />
<div class="cover-sec" style="background-image: url(${main_ui}/images/banner1.jpeg)"></div>
<main class="main">
    <div class="container">
        <h3>Group</h3>
        <div class="btn-group">
            <a href="/updateGroup?id=${group.id}" class="btn btn-primary btn-sm">
                <i class="glyphicon glyphicon-edit"></i>
                Edit group
            </a>
            <a href="/deleteGroup?id=${group.id}" class="btn btn-danger btn-sm">
                <i class="glyphicon glyphicon-trash"></i>
                Delete group
            </a>
            <a href="/association/create?id=${group.id}" class="btn btn-danger btn-sm">
                <i class="glyphicon glyphicon-edit"></i>
                Join group
            </a>
        </div>

        <div class="media">
            <div class="media-left">
                <img class="group-image" height="200" src="${fn:escapeXml(not empty group.imageUrl?group.imageUrl:'http://placekitten.com/g/128/192')}">
            </div>

            <div class="media-body">
                <h3>Name</h3>
                <h4 class="person-name">
                    ${fn:escapeXml(person.first)} ${fn:escapeXml(not empty person.last?person.last:'Unknown')}
                </h4>
                <p>
                    <small class="person-added-by">Added by
                        ${fn:escapeXml(not empty person.createdBy?person.createdBy:'Anonymous')}
                    </small>
                </p>
                <h3>Added On</h3>
                <p>
                    <small class="person-publishedDate">Added on
                        ${fn:escapeXml(not empty person.publishedDate?person.publishedDate:'')}
                    </small>
                </p>
                <h3>Introduction</h3>
                <p class="person-introduction">${fn:escapeXml(person.introduction)}</p>
                <h3>Category</h3>
                <p class="person-category">${fn:escapeXml(person.category)}</p>
                <h3>Type</h3>
                <p class="person-type">${fn:escapeXml(person.type)}</p>
                <h3>Linkedin</h3>
                <p class="person-linkedin">${fn:escapeXml(person.linkedin)}</p>
                <h3>Facebook</h3>
                <p class="person-facebook">${fn:escapeXml(person.facebook)}</p>
                <h3>Twitter</h3>
                <p class="person-twitter">${fn:escapeXml(person.twitter)}</p>
                <h3>Instagram</h3>
                <p class="person-instagram">${fn:escapeXml(person.instagram)}</p>
                <h3>Youtube</h3>
                <p class="person-youtube">${fn:escapeXml(person.youtube)}</p>
                <h3>Website</h3>
                <p class="person-website">${fn:escapeXml(person.website)}</p>
                <h3>Description</h3>
                <p class="person-description">${fn:escapeXml(person.description)}</p>
                <h3>Added By</h3>
            </div>
        </div>

        <c:choose>
            <c:when test="${empty persons}">
                <p>No persons found</p>
            </c:when>
            <c:otherwise>
                <div class="companies-list">
                    <div class="row">
                        <c:forEach items="${persons}" var="person">
                            <div class="col-lg-3 col-md-4 col-sm-6 col-12">
                                <div class="company_profile_info">
                                    <div class="company-up-info">
                                        <img alt="ahhh" height="200"src="${fn:escapeXml(not empty person.imageUrl?person.imageUrl:'http://placekitten.com/g/128/192')}">
                                        <h3>${fn:escapeXml(person.first)} ${fn:escapeXml(person.last)}</h3>
                                        <h4>${fn:escapeXml(person.title)}</h4>
                                    </div>
                                    <a href="/read?id=${person.id}" title="" class="view-more-pro">View Profile</a>
                                </div><!--company_profile_info end-->
                            </div>
                        </c:forEach>
                        <div class="process-comm">
                            <a href="?cursor=${fn:escapeXml(cursor)}" title=""><img src="images/process-icon.png" alt=""></a>
                        </div>
                        <c:if test="${not empty cursor}">
                            <nav>
                                <ul class="pager">
                                    <li><a href="?cursor=${fn:escapeXml(cursor)}&id=${group.id}">More</a></li>
                                </ul>
                            </nav>
                            <div class="process-comm">
                                <a href="?cursor=${fn:escapeXml(cursor)}&id=${group.id}" title=""><img src="images/process-icon.png" alt=""></a>
                            </div>
                        </c:if>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<!-- [END view] -->
