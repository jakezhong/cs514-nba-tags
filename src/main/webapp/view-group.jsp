<!-- [START view] -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="cover-sec" style="background-image: url(${pageContext.request.contextPath}/ui/images/banner1.jpeg)"></div>
<main class="main">
    <div class="main-section">
        <div class="container">
            <div class="main-section-data">
                <div class="row">
                    <div class="col-lg-3">
                        <div class="main-left-sidebar">
                            <div class="user_profile">
                                <div class="user-pro-img">
                                    <img class="group-image" height="200" src="${fn:escapeXml(not empty group.imageUrl?group.imageUrl:'http://placekitten.com/g/128/192')}">
                                </div><!--user-pro-img end-->
                                <div class="user_pro_status">
                                    <a href="/association/create?id=${group.id}" class="btn btn-primary btn-sm">
                                        <i class="glyphicon glyphicon-edit"></i>
                                        Add Member
                                    </a>
                                </div><!--user_pro_status end-->
                                <ul class="social_links">
                                    <li><a href="#" title=""><i class="la la-globe"></i> ${fn:escapeXml(group.website)}</a></li>
                                    <li><a href="#" title=""><i class="fa fa-facebook-square"></i> ${fn:escapeXml(group.facebook)}</a></li>
                                    <li><a href="#" title=""><i class="fa fa-twitter"></i> ${fn:escapeXml(group.twitter)}</a></li>
                                    <li><a href="#" title=""><i class="fa fa-instagram"></i> ${fn:escapeXml(group.instagram)}</a></li>
                                    <li><a href="#" title=""><i class="fa fa-youtube"></i> ${fn:escapeXml(group.youtube)}</a></li>
                                </ul>
                            </div><!--user_profile end-->
                        </div><!--main-left-sidebar end-->
                    </div>
                    <div class="col-lg-9">
                        <div class="main-ws-sec">
                            <div class="user-tab-sec">
                                <ul class="flw-hr">
                                    <li>
                                        <a href="update?id=${group.id}" class="btn btn-primary btn-sm">
                                            <i class="glyphicon glyphicon-edit"></i>
                                            Edit group
                                        </a>
                                    </li>
                                    <li>
                                        <a href="delete?id=${group.id}" class="btn btn-danger btn-sm">
                                            <i class="glyphicon glyphicon-trash"></i>
                                            Delete group
                                        </a>
                                    </li>

                                </ul>
                                <h3>${fn:escapeXml(group.name)}</h3>
                                <div class="star-descp">
                                    <p class="category"><span>${fn:escapeXml(group.category)}</span></p>
                                    <p>
                                        <small>Added by
                                            ${fn:escapeXml(not empty group.createdBy?group.createdBy:'Anonymous')}
                                        </small>
                                    </p>
                                    <p>
                                        <small>Added on
                                            ${fn:escapeXml(not empty group.createdDate?group.createdDate:'')}
                                        </small>
                                    </p>
                                </div><!--star-descp end-->
                            </div><!--user-tab-sec end-->
                            <div class="post-bar">
                                <div class="detail_descp">
                                    <h3>Introduction</h3>
                                    <p>${fn:escapeXml(group.introduction)}</p>
                                </div>
                            </div>
                            <div class="post-bar">
                                <div class="detail_descp">
                                    <h3>Description</h3>
                                    <div class="content-main">
                                        <p>${fn:escapeXml(group.description)}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="post-bar">
                                <div class="detail_descp">
                                    <h3>Group Members</h3>
                                    <c:choose>
                                        <c:when test="${empty persons}">
                                            <p>No persons found</p>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="companies-list">
                                                <div class="row">
                                                    <c:forEach items="${persons}" var="person">
                                                        <div class="col-lg-4 col-md-6  col-sm-12 col-12">
                                                            <div class="company_profile_info">
                                                                <a href="/person/read?id=${person.id}" title="">
                                                                    <div class="company-up-info">
                                                                        <img alt="ahhh" height="200"src="${fn:escapeXml(not empty person.imageUrl?person.imageUrl:'http://placekitten.com/g/128/192')}">
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
                                                        <a href="?cursor=${fn:escapeXml(cursor)}&id=${group.id}"  class="btn btn-primary btn-sm">
                                                            <i class="glyphicon glyphicon-edit"></i>More
                                                        </a>
                                                    </div>
                                                </c:if>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div><!--main-ws-sec end-->
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- [END view] -->
