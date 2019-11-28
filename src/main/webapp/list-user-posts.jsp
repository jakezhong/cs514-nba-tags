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
										<li><a href="#" title=""><i class="la la-globe"></i> ${fn:escapeXml(profile.website)}</a></li>
										<li><a href="#" title=""><i class="fa fa-facebook-square"></i> ${fn:escapeXml(profile.facebook)}</a></li>
										<li><a href="#" title=""><i class="fa fa-twitter"></i> ${fn:escapeXml(profile.twitter)}</a></li>
										<li><a href="#" title=""><i class="fa fa-instagram"></i> ${fn:escapeXml(profile.instagram)}</a></li>
										<li><a href="#" title=""><i class="fa fa-youtube"></i> ${fn:escapeXml(profile.youtube)}</a></li>
									</ul>
								</div><!--user_profile end-->
							</div><!--main-left-sidebar end-->
							</c:when>
						</c:choose>
                    </div>
                    <div class="col-lg-9">
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
                            <div class="user-tab-sec">
								<div class="landing-header">
									<a href="/post/create" class="btn btn-success btn-sm">
										<i class="glyphicon glyphicon-plus"></i>
										Add Post
									</a>
								</div>
                            </div><!--user-tab-sec end-->
							<c:choose>
								<c:when test="${not empty posts}">
									<div class="posts-section">
										<c:forEach items="${posts}" var="post">
											<div class="post-bar">
												<div class="post_topbar">
													<img alt="ahhh" src="${fn:escapeXml(not empty post.imageUrl?post.imageUrl:'http://placekitten.com/g/300/200')}">
												</div>
												<div class="epi-sec">
													<h3><a href="/post/read?id=${post.id}">${fn:escapeXml(post.title)}</a></h3>
													<ul>
														<li><img src="${pageContext.request.contextPath}/ui/images/icon8.png" alt=""><span>${fn:escapeXml(post.createdBy)}</span></li>
														<li><img src="${pageContext.request.contextPath}/ui/images/clock.png" alt=""><span>${fn:escapeXml(post.publishedDate)}</span></li>
													</ul>
												</div>
												<div class="job_descp">
													<ul class="job-dt">
														<li><a href="#" title="">${fn:escapeXml(post.category)}</a></li>
													</ul>
													<p>${fn:escapeXml(post.introduction)} <a href="/post/read?id=${post.id}" title="">view more</a></p>
													<ul class="skill-tags">
														<li><a href="#" title="">${fn:escapeXml(post.category)}</a></li>
													</ul>
												</div>
												<div class="job-status-bar">
													<ul class="like-com">
														<li><a href="/post/read?id=${post.id}" title="" class="com"><img src="${pageContext.request.contextPath}/ui/images/com.png" alt=""> Comment 15</a></li>
													</ul>
												</div>
											</div><!--post-bar end-->
										</c:forEach>
										<c:if test="${not empty cursor}">
											<div class="process-comm">
												<a href="?cursor=$	{fn:escapeXml(cursor)}" title=""><img src="${pageContext.request.contextPath}/ui/images/process-icon.png" alt=""></a>
											</div><!--process-comm end-->
										</c:if>
									</div><!--posts-section end-->
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
