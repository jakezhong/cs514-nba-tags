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
									<h3>${fn:escapeXml(profile.first)} ${fn:escapeXml(profile.last)}</h3>
									<p><span>${fn:escapeXml(profile.title)}</span></p>
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
													<a href="/post/read?id=${post.id}"><img alt="ahhh" src="${fn:escapeXml(not empty post.imageUrl?post.imageUrl:'http://placekitten.com/g/300/200')}"></a>
												</div>
												<div class="epi-sec">
													<h3><a href="/post/read?id=${post.id}"><c:if test="${not empty post.status}">${post.status=='private'?'Private: ':''}</c:if>${fn:escapeXml(post.title)}</a></h3>
													<ul class="post-info">
														<li><img src="${pageContext.request.contextPath}/ui/images/icon8.png" alt=""><span>${fn:escapeXml(post.createdBy)}</span></li>
														<li><img src="${pageContext.request.contextPath}/ui/images/clock.png" alt=""><span>${fn:escapeXml(post.publishedDate)}</span></li>
													</ul>
												</div>
												<div class="job_descp">
													<ul class="job-dt">
														<li><a href="#" title="">${fn:escapeXml(post.category)}</a></li>
													</ul>
													<p>${fn:escapeXml(post.introduction)} <a href="/post/read?id=${post.id}" title="">view more</a></p>
													<c:if test="${not empty post.postTags}">
														<ul class="skill-tags">
														<c:forEach items="${post.postTags}" var="tag">
															<c:if test="${tag['class'].name == 'com.example.getstarted.objects.Group'}">
																<li><a href="/group/read?id=${tag.id}">${tag.name}</a></li>
															</c:if>
															<c:if test="${tag['class'].name == 'com.example.getstarted.objects.Person'}">
																<li><a href="/person/read?id=${tag.id}">${tag.first} ${tag.last}</a></li>
															</c:if>
														</c:forEach>
														</ul>
													</c:if>
												</div>
												<div class="job-status-bar">
													<ul class="like-com">
														<li><span class="com"><i class="la la-heart"></i>Like ${fn:length(post.like)}</li>
														<li><a href="/post/read?id=${post.id}" title="" class="com"><img src="${pageContext.request.contextPath}/ui/images/com.png" alt=""> Comment ${post.commentNum}</a></li>
													</ul>
												</div>
											</div><!--post-bar end-->
										</c:forEach>
									</div><!--posts-section end-->
									<c:if test="${not empty cursor}">
										<div class="read-more">
											<a href="?cursor=${fn:escapeXml(cursor)}&id=${profile.id}"  class="btn btn-primary btn-sm">
												<i class="glyphicon glyphicon-edit"></i>Load More
											</a>
										</div>
									</c:if>
								</c:when>
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
