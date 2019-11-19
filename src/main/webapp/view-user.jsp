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
                                <div class="star-descp">
                                    <p><span>${fn:escapeXml(user.userName)}</span></p>
                                    <p>
                                        <small>Email
                                            ${fn:escapeXml(not empty user.email?user.email:'')}
                                        </small>
                                    </p>
                                    <p>
                                        <small>Joined on
                                            ${fn:escapeXml(not empty user.createdDate?user.createdDate:'')}
                                        </small>
                                    </p>
                                </div><!--star-descp end-->
                            </div><!--user-tab-sec end-->
                            <div class="post-bar">
                                <div class="detail_descp">
                                    <h3>Introduction</h3>
                                    <p>${fn:escapeXml(user.introduction)}</p>
                                </div>
                            </div>
                            <div class="post-bar">
                                <div class="detail_descp">
                                    <h3>Description</h3>
                                    <div class="content-main">
                                        <p>${fn:escapeXml(user.description)}</p>
                                    </div>
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
