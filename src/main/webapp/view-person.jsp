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
                                    <img class="person-image" height="200" src="${fn:escapeXml(not empty person.imageUrl?person.imageUrl:'http://placekitten.com/g/128/192')}">
                                </div><!--user-pro-img end-->
								<div class="user-info center">
                                	<h3>${fn:escapeXml(person.first)} ${fn:escapeXml(person.last)}</h3>
                                    <p><span>${fn:escapeXml(person.title)}</span></p>
									<h5 class="category"><a href="/persons?category=${person.category}">${fn:escapeXml(person.category)}</a></h5>
								</div>
                                <ul class="social_links">
									<c:if test="${not empty socialLinks}">
										<c:forEach items="${socialLinks}" var ="socialLink">
											<li>
												<a href="${socialLink.value}" title="social" target="_blank"><i class="fa fa-${fn:escapeXml(socialLink.key)}"></i> ${fn:escapeXml(socialLink.value)}</a>
												<a href="/person/delete-social?socialLinkName=${socialLink.key}&personId=${person.id}" class="delete-btn">
													<i class="fa fa-trash-o"></i>
												</a>
											</li>
										</c:forEach>
									</c:if>
									<c:if test="${login != false}">
									<form method="POST" action="/person/add-social" class="social-form">
										<input type="text" name="personId" value="${person.id}" class="hidden">
										<div class="form-group" id="social-other-trigger">
											<label for="socialLinks">Social Type</label>
											<div class="inp-field">
												<select name="socialLink" class="form-control" id="socialLink">
													<option value="website">Website</option>
													<option value="facebook">Facebook</option>
													<option value="twitter">Twitter</option>
													<option value="instagram">Instagram</option>
													<option value="youtube">Youtube</option>
													<option value="linkedin">Linkedin</option>
													<option value ="other">other</option>
												</select>
											</div>
										</div>
										<div class="form-group">
											<label for="socialLinks">Social Link</label>
											<input type="url" name="socialLinkUrl" id="socialLinks" class="form-control">
										</div>
										<button type="submit" class="btn btn-success">Add</button>
									</form>
									</c:if>
                                </ul>
                            </div><!--user_profile end-->
                        </div><!--main-left-sidebar end-->
                    </div>
                    <div class="col-lg-9">
                        <div class="main-ws-sec">
                            <div class="user-tab-sec">
								<c:if test="${login != false}">
                                <ul class="flw-hr">
                                    <li>
                                        <a href="/person/update?id=${person.id}" class="btn btn-primary btn-sm">
                                            <i class="glyphicon glyphicon-edit"></i>
                                            Edit person
                                        </a>
                                    </li>
                                    <li>
                                        <a href="/person/delete?id=${person.id}" class="btn btn-danger btn-sm">
                                            <i class="glyphicon glyphicon-trash"></i>
                                            Delete person
                                        </a>
                                    </li>
                                    <li>
                                        <a href="/person/join?personId=${person.id}" class="btn btn-primary btn-sm">
                                            <i class="glyphicon glyphicon-edit"></i>
                                            Join Group
                                        </a>
                                    </li>
                                    <li>
                                    </li>
                                </ul>
								</c:if>
                                <div class="star-descp">
                                    <p>
                                        <small>Added by
                                            ${fn:escapeXml(not empty person.createdBy?person.createdBy:'Anonymous')}
                                        </small>
                                    </p>
                                    <p>
                                        <small>Added on
                                            ${fn:escapeXml(not empty person.publishedDate?person.publishedDate:'')}
                                        </small>
                                    </p>
                                </div><!--star-descp end-->
                            </div><!--user-tab-sec end-->
                            <div class="post-bar">
                                <div class="detail_descp">
                                    <h3>Introduction</h3>
                                    <p>${fn:escapeXml(person.introduction)}</p>
                                </div>
                            </div>
                            <div class="post-bar">
                                <div class="detail_descp">
                                    <h3>Description</h3>
                                    <div class="content-main">
                                        <p>${person.description}</p>
                                    </div>
                                </div>
                            </div>
							<c:if test="${not empty groups}">
								<div class="post-bar">
									<div class="detail_descp">
										<form method ="POST" action ="/group/quit" class="detail_descp">
											<h3>Groups</h3>
											<c:if test="${login != false}">
											<button type="submit"  class="btn btn-primary btn-sm">
												<i class="glyphicon glyphicon-edit"></i>
												Quit Groups
											</button>
											</c:if>
											<input type="text" name="personId" value="${person.id}" class="hidden">
											<div class="companies-list">
												<div class="row">
													<c:forEach items="${groups}" var="group">
														<div class="col-lg-4 col-md-6  col-sm-12 col-12">
															<div class="company_profile_info">
																<div class="company-up-info">
																	<div class="grid-image">
																		<a href="/group/read?id=${group.id}" title="">
																			<img alt="ahhh" src="${fn:escapeXml(not empty group.imageUrl?group.imageUrl:'http://placekitten.com/g/128/192')}">
																		</a>
																	</div>
																	<h3>
																		<a href="/group/read?id=${group.id}" title="">${fn:escapeXml(group.name)}</a>
																	</h3>
																	<h5 class="category"><a href="/groups?category=${group.category}">${fn:escapeXml(group.category)}</a></h5>
																</div>
																<div class="view-more-pro"><a href="/group/read?id=${group.id}" title="">View Detail</a></div>
																<c:if test="${login != false}">
																<input type="checkbox" name="groups" value=${group.id} />
																</c:if>
															</div><!--company_profile_info end-->
														</div>
													</c:forEach>
												</div>
												<c:if test="${not empty cursor_group}">
													<div class="read-more">
														<a href="?cursor_group=${fn:escapeXml(cursor_group)}&id=${person.id}"  class="btn btn-primary btn-sm">
															<i class="glyphicon glyphicon-edit"></i>Load More
														</a>
													</div>
												</c:if>
											</div>
										</form>
									</div>
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
															<li><a href="/post/read?id=${post.id}#comment" title="" class="com"><img src="${pageContext.request.contextPath}/ui/images/com.png" alt=""> Comment ${post.commentNum}</a></li>
														</ul>
													</div>
												</div>
											</c:forEach>
										</div>
										<c:if test="${not empty cursor_post}">
											<div class="read-more">
												<a href="?cursor_post=${fn:escapeXml(cursor_post)}&id=${person.id}"  class="btn btn-primary btn-sm">
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
