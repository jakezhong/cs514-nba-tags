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
						<c:choose>
							<c:when test="${not empty profile}">
							<div class="main-left-sidebar">
								<div class="user_profile">
									<div class="user-pro-img">
										<img class="user-image" src="${fn:escapeXml(not empty profile.imageUrl?profile.imageUrl:'http://placekitten.com/g/500/500')}">
									</div><!--user-pro-img end-->
									<ul class="social_links">
										<li><a href="#" title=""><i class="la la-globe"></i></a></li>
										<li><a href="#" title=""><i class="fa fa-facebook-square"></i></a></li>
										<li><a href="#" title=""><i class="fa fa-twitter"></i></a></li>
										<li><a href="#" title=""><i class="fa fa-instagram"></i></a></li>
										<li><a href="#" title=""><i class="fa fa-youtube"></i></a></li>
									</ul>
								</div><!--user_profile end-->
							</div><!--main-left-sidebar end-->
							</c:when>
						</c:choose>
                    </div>
                    <div class="col-lg-9">
						<c:choose>
							<c:when test="${privacy == true}">
							<h3 class="center">This profile is private</h3>
							</c:when>
							<c:otherwise>
							<div class="main-ws-sec">
								<ul class="flw-hr">
									<li>
										<a href="/profile/user" class="btn btn-primary btn-sm">
											<i class="glyphicon glyphicon-edit"></i>
											About
										</a>
									</li>
									<li>
										<a href="/persons/user" class="btn btn-primary btn-sm">
											<i class="glyphicon glyphicon-edit"></i>
											My Persons
										</a>
									</li>
									<li>
										<a href="/groups/user" class="btn btn-primary btn-sm">
											<i class="glyphicon glyphicon-edit"></i>
											My Groups
										</a>
									</li>
									<li>
										<a href="/posts/user" class="btn btn-primary btn-sm">
											<i class="glyphicon glyphicon-edit"></i>
											My Posts
										</a>
									</li>
								</ul>
								<c:choose>
									<c:when test="${not empty profile}">
									<div class="user-tab-sec">
										<div class="landing-header">
											<ul class="flw-hr">
												<li>
													<a href="update?id=${profile.id}" class="btn btn-primary btn-sm">
														<i class="glyphicon glyphicon-edit"></i>
														Edit Profile
													</a>
												</li>
												<li>
													<a href="delete?id=${profile.id}" class="btn btn-danger btn-sm">
														<i class="glyphicon glyphicon-trash"></i>
														Delete Profile
													</a>
												</li>
											</ul>
										</div>
										<div class="star-descp">
											<p><span>${fn:escapeXml(profile.first)} ${fn:escapeXml(profile.last)}</span></p>
											<p><span>${fn:escapeXml(profile.title)}</span></p>
											<p>
												<small>Email
													${fn:escapeXml(not empty profile.email?profile.email:'')}
												</small>
											</p>
											<p>
												<small>Joined on
													${fn:escapeXml(not empty profile.publishedDate?profile.publishedDate:'')}
												</small>
											</p>
										</div><!--star-descp end-->
									</div><!--user-tab-sec end-->
									<div class="post-bar">
										<div class="detail_descp">
											<h3>Introduction</h3>
											<p>${fn:escapeXml(profile.introduction)}</p>
										</div>
									</div>
									<div class="post-bar">
										<div class="detail_descp">
											<h3>Description</h3>
											<div class="content-main">
												<p>${profile.description}</p>
											</div>
										</div>
									</div>
									</c:when>
									<c:otherwise>
									<div class="user-tab-sec">
										<div class="landing-header">
											<a href="/profile/create" class="btn btn-success btn-sm">
												<i class="glyphicon glyphicon-edit"></i>
												Create Profile
											</a>
										</div>
									</div>
									</c:otherwise>
								</c:choose>
							</div><!--main-ws-sec end-->
							</c:otherwise>
						</c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- [END view] -->
