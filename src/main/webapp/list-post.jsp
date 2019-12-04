<!-- [START list] -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<main class="main">
    <div class="container">
        <div class="main-section-data">
            <div class="row">
                <div class="col-lg-3">
                    <div class="filter-secs">
                        <div class="paddy">
                            <div class="filter-dd">
                                <form action="" method="GET">
									<div class="form-group">
										<label for="category">Search</label>
                                    	<input type="text" name="title" placeholder="Post title..." value="${empty title ? '' : title}">
									</div>
									<div class="form-group">
										<label for="category">Category</label>
										<div class="inp-field">
											<select name="category" id="category">
												<option value="" selected disabled>Choose Category</option>
												<option value="basketball" ${category=='basketball'?"selected" : ""}>Basketball</option>
												<option value="football" ${category=='football'?"selected" : ""}>Football</option>
												<option value="baseball" ${category=='baseball'?"selected" : ""}>Baseball</option>
												<option value="soccer" ${category=='soccer'?"selected" : ""}>Soccer</option>
												<option value="hiking" ${category=='hiking'?"selected" : ""}>Hiking</option>
											</select>
										</div>
									</div>
									<div class="form-footer">
										<button type="submit" class="btn red">Submit</button>
									</div>
                                </form>
                            </div>
                        </div>
                    </div><!--filter-secs end-->
                </div>
                <div class="col-lg-9">
                    <div class="main-ws-sec">
						<c:choose>
							<c:when test="${empty posts}">
								<div class="no-result center">
									<h3>No posts found</h3>
								</div>
							</c:when>
							<c:otherwise>
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
													<li><a href="/post/like?id=${post.id}" title="like" class="com${post.liked == true?' active':''}"><i class="la la-heart"></i>Like ${fn:length(post.like)}</a></li>
													<li><a href="/post/read?id=${post.id}#comment" title="comment" class="com"><img src="${pageContext.request.contextPath}/ui/images/com.png" alt=""> Comment ${post.commentNum}</a></li>
												</ul>
											</div>
										</div><!--post-bar end-->
									</c:forEach>
								</div><!--posts-section end-->
								<c:if test="${not empty cursor}">
									<div class="read-more">
										<a href="?cursor=${fn:escapeXml(cursor)}"  class="btn btn-primary btn-sm">
											<i class="glyphicon glyphicon-edit"></i>Load More
										</a>
									</div>
								</c:if>
							</c:otherwise>
						</c:choose>
                    </div><!--main-ws-sec end-->
                </div>
            </div>
        </div><!-- main-section-data end-->
    </div>
</main>
<!-- [END list] -->
