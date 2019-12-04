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
									<div class="user-info center">
										<h3>${fn:escapeXml(profile.first)} ${fn:escapeXml(profile.last)}</h3>
										<p><span>${fn:escapeXml(profile.title)}</span></p>
									</div>
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
									<a href="/group/create" class="btn btn-success btn-sm">
										<i class="glyphicon glyphicon-plus"></i>
										Add group
									</a>
								</div>
							</div><!--user-tab-sec end-->
							<c:if test="${not empty groups}">
								<div class="post-bar">
									<div class="detail_descp">
										<div class="companies-list">
											<div class="row">
												<c:forEach items="${groups}" var="group">
													<div class="col-lg-4 col-md-6  col-sm-12 col-12">
														<div class="company_profile_info">
															<a href="/group/read?id=${group.id}" title="">
																<div class="company-up-info">
																	<div class="grid-image">
																		<a href="/group/read?id=${group.id}" title="">
																			<img alt="ahhh" src="${fn:escapeXml(not empty group.imageUrl?group.imageUrl:'http://placekitten.com/g/128/192')}">
																		</a>
																	</div>
																	<h3>
																		<a href="/group/read?id=${group.id}" title="">${fn:escapeXml(group.name)}</a>
																	</h3>
																	<h5 class="category"><a href="/persons?category=${group.category}">${fn:escapeXml(group.category)}</a></h5>
																</div>
																<div class="view-more-pro"><a href="/group/read?id=${group.id}" title="">View Detail</a></div>
															</a>
														</div><!--company_profile_info end-->
													</div>
												</c:forEach>
											</div>
											<c:if test="${not empty cursor}">
												<div class="read-more">
													<a href="?cursor=${fn:escapeXml(cursor)}"  class="btn btn-primary btn-sm">
														<i class="glyphicon glyphicon-edit"></i>Load More
													</a>
												</div>
											</c:if>
										</div>
									</div>
								</div>
							</c:if><!--post-bar end-->
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
