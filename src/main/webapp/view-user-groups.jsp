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
                                    <img class="user-image" height="200" src="${fn:escapeXml(not empty user.imageUrl?user.imageUrl:'http://placekitten.com/g/128/192')}">
                                </div><!--user-pro-img end-->
                                <ul class="social_links">
                                    <li><a href="#" title=""><i class="la la-globe"></i> ${fn:escapeXml(user.website)}</a></li>
                                    <li><a href="#" title=""><i class="fa fa-facebook-square"></i> ${fn:escapeXml(user.facebook)}</a></li>
                                    <li><a href="#" title=""><i class="fa fa-twitter"></i> ${fn:escapeXml(user.twitter)}</a></li>
                                    <li><a href="#" title=""><i class="fa fa-instagram"></i> ${fn:escapeXml(user.instagram)}</a></li>
                                    <li><a href="#" title=""><i class="fa fa-youtube"></i> ${fn:escapeXml(user.youtube)}</a></li>
                                </ul>
                            </div><!--user_profile end-->
                        </div><!--main-left-sidebar end-->
                    </div>
                    <div class="col-lg-9">
                        <div class="main-ws-sec">
                            <div class="user-tab-sec">
                                <ul class="flw-hr">
                                    <li>
                                        <a href="update?id=${user.id}" class="btn btn-primary btn-sm">
                                            <i class="glyphicon glyphicon-edit"></i>
                                            Edit User
                                        </a>
                                    </li>
                                    <li>
                                        <a href="delete?id=${user.id}" class="btn btn-danger btn-sm">
                                            <i class="glyphicon glyphicon-trash"></i>
                                            Delete User
                                        </a>
                                    </li>
                                </ul>
                                <ul class="flw-hr">
                                    <li><a href="/user" title="">About</a></li>
                                    <li><a href="/persons/mine" title="">My Persons</a></li>
                                    <li><a href="/groups/mine" title="">My Groups</a></li>
                                </ul>
                            </div><!--user-tab-sec end-->
                            <c:choose>
                                <c:when test="${not empty groups}">
                                    <div class="post-bar">
                                        <div class="detail_descp">
                                            <h3>Groups</h3>
                                            <div class="companies-list">
                                                <div class="row">
                                                    <c:forEach items="${groups}" var="group">
                                                        <div class="col-lg-4 col-md-6  col-sm-12 col-12">
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
                                                        <a href="?cursor=${fn:escapeXml(cursor)}&id=${group.id}"  class="btn btn-primary btn-sm">
                                                            <i class="glyphicon glyphicon-edit"></i>More
                                                        </a>
                                                    </div>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </c:when>
                            </c:choose>
                        </div><!--main-ws-sec end-->
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- [END view] -->
