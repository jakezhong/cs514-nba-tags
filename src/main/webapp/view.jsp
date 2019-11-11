<!-- [START view] -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:url value="/ui" var="main_ui" />
<div class="cover-sec" style="background-image: url(${main_ui}/images/banner1.jpeg)"></div>
<main class="main">
    <div class="main-section">
        <div class="container">
            <div class="main-section-data">
                <div class="row">
                    <div class="col-lg-3">
                        <div class="main-left-sidebar">
                            <div class="user_profile">
                                <div class="user-pro-img">
                                    <img class="person-image" height="200" src="${fn:escapeXml(not empty person.imageUrl?person.imageUrl:'http://placekitten.com/g/128/192')}">
                                </div><!--user-pro-img end-->
                                <div class="user_pro_status">
                                    <a href="/listGroupByperson?id=${person.id}" class="btn btn-primary btn-sm">
                                        <i class="glyphicon glyphicon-edit"></i>
                                        My Groups
                                    </a>
                                </div><!--user_pro_status end-->
                                <ul class="social_links">
                                    <li><a href="#" title=""><i class="la la-globe"></i> ${fn:escapeXml(person.website)}</a></li>
                                    <li><a href="#" title=""><i class="fa fa-facebook-square"></i> ${fn:escapeXml(person.facebook)}</a></li>
                                    <li><a href="#" title=""><i class="fa fa-twitter"></i> ${fn:escapeXml(person.twitter)}</a></li>
                                    <li><a href="#" title=""><i class="fa fa-instagram"></i> ${fn:escapeXml(person.instagram)}</a></li>
                                    <li><a href="#" title=""><i class="fa fa-youtube"></i> ${fn:escapeXml(person.youtube)}</a></li>
                                </ul>
                            </div><!--user_profile end-->
                        </div><!--main-left-sidebar end-->
                    </div>
                    <div class="col-lg-9">
                        <div class="main-ws-sec">
                            <div class="user-tab-sec">
                                <ul class="flw-hr">
                                    <li>
                                        <a href="/update?id=${person.id}" class="btn btn-primary btn-sm">
                                            <i class="glyphicon glyphicon-edit"></i>
                                            Edit person
                                        </a>
                                    </li>
                                    <li>
                                        <a href="/delete?id=${person.id}" class="btn btn-danger btn-sm">
                                            <i class="glyphicon glyphicon-trash"></i>
                                            Delete person
                                        </a>
                                    </li>
                                    <li>
                                    </li>
                                </ul>
                                <h3>${fn:escapeXml(person.first)} ${fn:escapeXml(not empty person.last?person.last:'Unknown')}</h3>
                                <div class="star-descp">
                                    <p><span>${fn:escapeXml(person.title)}</span></p>
                                    <p>
                                        <small class="person-added-by">Added by
                                            ${fn:escapeXml(not empty person.createdBy?person.createdBy:'Anonymous')}
                                        </small>
                                    </p>
                                    <p>
                                        <small class="person-publishedDate">Added on
                                            ${fn:escapeXml(not empty person.publishedDate?person.publishedDate:'')}
                                        </small>
                                    </p>
                                </div><!--star-descp end-->
                            </div><!--user-tab-sec end-->
                            <div class="post-bar">
                                <div class="detail_descp">
                                    <h3>Introduction</h3>
                                    <p class="person-introduction">${fn:escapeXml(person.introduction)}</p>
                                </div>
                            </div>
                            <div class="post-bar">
                                <div class="detail_descp">
                                    <h3>Description</h3>
                                    <div class="content-main">
                                        <p>${fn:escapeXml(person.description)}</p>
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
