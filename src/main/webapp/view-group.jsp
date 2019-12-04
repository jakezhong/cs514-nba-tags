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
								<div class="user-info center">
									<h3>${fn:escapeXml(group.name)}</h3>
									<p class="category"><a href="/groups?category=${group.category}">${fn:escapeXml(group.category)}</a></p>
									<c:if test="${login != false}">
									<div class="user_pro_status">
										<a href="/association/create?id=${group.id}" class="btn btn-primary btn-sm">
											<i class="glyphicon glyphicon-edit"></i>
											Add Member
										</a>
									</div><!--user_pro_status end-->
									</c:if>
								</div>
                            </div><!--user_profile end-->
                        </div><!--main-left-sidebar end-->
                    </div>
                    <div class="col-lg-9">
                        <div class="main-ws-sec">
                            <div class="user-tab-sec">
								<c:if test="${login != false}">
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
								</c:if>
                                <div class="star-descp">
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
                                        <p>${group.description}</p>
                                    </div>
                                </div>
                            </div>
							<c:if test="${not empty persons}">
								<div class="post-bar">
									<form method ="POST" action ="/association/delete" class="detail_descp">
										<h3>Members</h3>
										<c:if test="${login != false}">
										<button type="submit" class="btn btn-danger btn-sm">
											<i class="glyphicon glyphicon-edit"></i>
											Remove Members
										</button>
										</c:if>
										<input type="text" name="groupId" value="${group.id}" class="hidden">
										<div class="companies-list">
											<div class="row">
												<c:forEach items="${persons}" var="person">
													<div class="col-lg-4 col-md-6  col-sm-12 col-12">
														<div class="company_profile_info">
															<div class="company-up-info">
																<div class="grid-image">
																	<a href="/person/read?id=${person.id}" title="">
																		<img alt="ahhh" src="${fn:escapeXml(not empty person.imageUrl?person.imageUrl:'http://placekitten.com/g/128/192')}">
																	</a>
																</div>
																<h3><a href="/person/read?id=${person.id}" title="">${fn:escapeXml(person.first)} ${fn:escapeXml(person.last)}</a></h3>
																<h4>${fn:escapeXml(person.title)}</h4>
																<h5 class="category"><a href="/persons?category=${person.category}">${fn:escapeXml(person.category)}</a></h5>
															</div>
															<div class="view-more-pro">
																<a href="/person/read?id=${person.id}" title="">View Profile</a>
															</div>
															<c:if test="${login != false}">
															<input type="checkbox" name="members" value=${person.id} />
															</c:if>
														</div><!--company_profile_info end-->
													</div>
												</c:forEach>
											</div>
											<c:if test="${not empty cursor_person}">
												<div class="read-more">
													<a href="?cursor_person=${fn:escapeXml(cursor_person)}&id=${group.id}"  class="btn btn-primary btn-sm">
														<i class="glyphicon glyphicon-edit"></i>Load More
													</a>
												</div>
											</c:if>
										</div>
									</form><!--deleteGroupMembers-->
								</div>
							</c:if>
							<c:if test="${not empty posts}">
								<div class="post-bar">
									<div class="detail_descp">
										<h3>Posts</h3>
										<div class="row posts-section">
											<c:forEach items="${posts}" var="post">
												<div class="col-lg-6 col-sm-12 company_profile_info post">
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
															<li><a href="/posts?category=${fn:escapeXml(post.category)}" title="">${fn:escapeXml(post.category)}</a></li>
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
												</div>
											</c:forEach>
										</div>
										<c:if test="${not empty cursor_post}">
											<div class="read-more">
												<a href="?cursor_post=${fn:escapeXml(cursor_post)}&id=${group.id}"  class="btn btn-primary btn-sm">
													<i class="glyphicon glyphicon-edit"></i>Load More
												</a>
											</div>
										</c:if>
									</div>
								</div>
							</c:if>
                        </div><!--main-ws-sec end-->
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</main>
<!-- [END view] -->
