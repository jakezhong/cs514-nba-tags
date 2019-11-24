<!-- [START list] -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<main class="main">
    <div class="container">
        <div class="main-section-data">
            <div class="row">
                <div class="col-lg-3">
                    <div class="filter-secs">
                        <div class="filter-heading">
                            <h3>Filters</h3>
                            <a href="#" title="">Clear all filters</a>
                        </div><!--filter-heading end-->
                        <div class="paddy">
                            <div class="filter-dd">
                                <div class="filter-ttl">
                                    <h3>Skills</h3>
                                    <a href="#" title="">Clear</a>
                                </div>
                                <form>
                                    <input type="text" name="search-skills" placeholder="Search skills">
                                </form>
                            </div>
                            <div class="filter-dd">
                                <div class="filter-ttl">
                                    <h3>Job Type</h3>
                                    <a href="#" title="">Clear</a>
                                </div>
                                <form class="job-tp">
                                    <select>
                                        <option>Select a job type</option>
                                        <option>Select a job type</option>
                                        <option>Select a job type</option>
                                        <option>Select a job type</option>
                                    </select>
                                    <i class="fa fa-ellipsis-v" aria-hidden="true"></i>
                                </form>
                            </div>
                            <div class="filter-dd">
                                <div class="filter-ttl">
                                    <h3>Experience Level</h3>
                                    <a href="#" title="">Clear</a>
                                </div>
                                <form class="job-tp">
                                    <select>
                                        <option>Select a experience level</option>
                                        <option>3 years</option>
                                        <option>4 years</option>
                                        <option>5 years</option>
                                    </select>
                                    <i class="fa fa-ellipsis-v" aria-hidden="true"></i>
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
												<div class="usy-dt">
													<img alt="ahhh" width="50" src="${fn:escapeXml(not empty post.imageUrl?post.imageUrl:'http://placekitten.com/g/50/50')}">
													<div class="usy-name">
														<h3><a href="/post/read?id=${post.id}">${fn:escapeXml(post.title)}</a></h3>
														<span><img src="${pageContext.request.contextPath}/ui/images/clock.png" alt="">${fn:escapeXml(post.publishedDate)}</span>
													</div>
												</div>
											</div>
											<div class="epi-sec">
												<ul class="descp">
													<li><img src="${pageContext.request.contextPath}/ui/images/icon8.png" alt=""><span>${fn:escapeXml(post.createdBy)}</span></li>
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
							</c:otherwise>
						</c:choose>
                    </div><!--main-ws-sec end-->
                </div>
            </div>
        </div><!-- main-section-data end-->
    </div>
</main>
<!-- [END list] -->
