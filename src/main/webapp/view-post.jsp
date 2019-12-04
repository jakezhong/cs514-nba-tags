<!-- [START view] -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="cover-sec" style="background-image: url(${pageContext.request.contextPath}/ui/images/banner1.jpeg)"></div>
<main class="main">
    <div class="main-section">
        <div class="container">
            <section class="forum-page">
                <div class="container">
                    <div class="forum-questions-sec">
						<c:if test="${login != false}">
							<ul class="flw-hr">
								<li>
									<a href="update?id=${post.id}" class="btn btn-primary btn-sm">
										<i class="glyphicon glyphicon-edit"></i>
										Edit Post
									</a>
								</li>
								<li>
									<a href="delete?id=${post.id}" class="btn btn-danger btn-sm">
										<i class="glyphicon glyphicon-trash"></i>
										Delete Post
									</a>
								</li>
							</ul>
						</c:if>
                        <div class="row">
                            <div class="col-lg-8">
                                <div class="forum-post-view">
                                    <div class="usr-question">
                                        <div class="usr_quest">
                                            <h3><c:if test="${not empty post.status}">${post.status=='private'?'Private: ':''}</c:if>${fn:escapeXml(post.title)}</h3>
                                            <span><i class="fa fa-clock-o"></i>${fn:escapeXml(post.publishedDate)}</span>
                                            <ul class="quest-tags">
                                                <li><a href="/posts?category=${post.category}" title="category">${fn:escapeXml(post.category)}</a></li>
                                            </ul>
											<div class="post-description">${post.description}</div>
											<c:choose>
												<c:when test="${not empty comments}">
												<div id="comment" class="comment-section">
													<h3>${post.commentNum} Comments</h3>
													<div class="comment-sec">
														<ul>
															<c:forEach items="${comments}" var="comment">
															<li>
																<div class="comment-list">
																	<div class="comment">
																		<h4>${fn:escapeXml(comment.value)}</h4>
																		<p>${fn:escapeXml(comment.key)}</p>
																		<a href="/post/delete-comment?postId=${post.id}&commentKey=${comment.key}"><i class="fa fa-trash-o delete-btn" style="font-size:20px"></i></a>
																	</div>
																</div><!--comment-list end-->
															</li>
															</c:forEach>
														</ul>
													</div><!--comment-sec end-->
												</div>
												</c:when>
												<c:otherwise>
												<div class="comment-section">
													<h3>This post has no comment yet</h3>
												</div>
												</c:otherwise>
											</c:choose>
                                        </div><!--usr_quest end-->
										<div class="post-comment-box">
											<h3>Leave Your Comment</h3>
											<div class="user-poster">
												<div class="post_comment_sec">
													<form method="POST" action="/post/add-comment">
														<input type="text" class="hidden" name="postId" value="${post.id}"/>
														<textarea placeholder="Your Comment" name="content"></textarea>
														<button type="submit">Submit</button>
													</form>
												</div><!--post_comment_sec end-->
											</div><!--user-poster end-->
										</div><!--post-comment-box end-->
                                    </div><!--usr-question end-->
                                </div><!--forum-post-view end-->
                            </div>
                            <div class="col-lg-4">
                                <div class="widget widget-adver">
									<img alt="ahhh" src="${fn:escapeXml(not empty post.imageUrl?post.imageUrl:'http://placekitten.com/g/300/200')}">
                                </div><!--widget-adver end-->
								<c:if test="${login != false}">
									<a href="/post-tag/create?id=${post.id}" class="btn btn-primary btn-sm">
										<i class="glyphicon glyphicon-edit"></i>
										Add Tag
									</a>
								</c:if>
								<c:if test="${not empty persons}">
									<form method ="POST" action ="/post-tag/delete" class="top-space">
										<input type="text" name="postId" value="${post.id}" class="hidden">
										<div class="widget widget-user">
											<h3 class="title-wd">Tagged Persons</h3>
											<ul>
												<c:forEach items="${persons}" var="person">
												<li>
													<div class="usr-msg-details">
														<div class="usr-ms-img">
															<img alt="ahhh" src="${fn:escapeXml(not empty person.imageUrl?person.imageUrl:'http://placekitten.com/g/128/192')}">
														</div>
														<div class="usr-mg-info">
															<h3><a href="/person/read?id=${person.id}">${fn:escapeXml(person.first)} ${fn:escapeXml(person.last)}</a></h3>
															<p><a href="/persons?category=${person.category}">${fn:escapeXml(person.category)}</a></p>
														</div><!--usr-mg-info end-->
														<c:if test="${login != false}">
														<input type="checkbox" name="persons" value=${person.id} />
														</c:if>
													</div>
												</li>
												</c:forEach>
											</ul>
										</div><!--widget-user end-->
										<c:if test="${login != false}">
										<button type="submit"  class="btn btn-danger btn-sm">
											<i class="glyphicon glyphicon-edit"></i>
											Remove Person
										</button>
										</c:if>
									</form>
								</c:if>
								<c:if test="${not empty groups}">
									<form method ="POST" action ="/post-tag/delete" class="top-space">
										<input type="text" name="postId" value="${post.id}" class="hidden">
										<div class="widget widget-user">
											<h3 class="title-wd">Tagged Groups</h3>
											<ul>
												<c:forEach items="${groups}" var="group">
												<li>
													<div class="usr-msg-details">
														<div class="usr-ms-img">
															<img alt="ahhh" src="${fn:escapeXml(not empty group.imageUrl?group.imageUrl:'http://placekitten.com/g/128/192')}">
														</div>
														<div class="usr-mg-info">
															<h3><a href="/group/read?id=${group.id}">${fn:escapeXml(group.name)}</a></h3>
															<p><a href="/groups?category=${group.category}">${fn:escapeXml(group.category)}</a></p>
														</div><!--usr-mg-info end-->
														<c:if test="${login != false}">
														<input type="checkbox" name="groups" value=${group.id} />
														</c:if>
													</div>
												</li>
												</c:forEach>
											</ul>
										</div><!--widget-user end-->
										<c:if test="${login != false}">
										<button type="submit"  class="btn btn-danger btn-sm">
											<i class="glyphicon glyphicon-edit"></i>
											Remove Group
										</button>
										</c:if>
									</form>
								</c:if>
                            </div>
                        </div>
                    </div><!--forum-questions-sec end-->
                </div>
            </section><!--forum-page end-->
        </div>
    </div>
</div>
<!-- [END view] -->
