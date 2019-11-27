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
                            <li>
                            </li>
                        </ul>
                        <div class="row">
                            <div class="col-lg-8">
                                <div class="forum-post-view">
                                    <div class="usr-question">
                                        <div class="usr_quest">
                                            <h3>${fn:escapeXml(post.title)}</h3>
                                            <span><i class="fa fa-clock-o"></i>${fn:escapeXml(post.publishedDate)}</span>
                                            <ul class="quest-tags">
                                                <li><a href="#" title="">${fn:escapeXml(post.category)}</a></li>
                                            </ul>
											<p>
                                            	${fn:escapeXml(post.description)}
											</p>
                                        </div><!--usr_quest end-->
                                    </div><!--usr-question end-->
                                </div><!--forum-post-view end-->
                            </div>
                            <div class="col-lg-4">
                                <div class="widget widget-adver">
									<img alt="ahhh" src="${fn:escapeXml(not empty post.imageUrl?post.imageUrl:'http://placekitten.com/g/300/200')}">
                                </div><!--widget-adver end-->
								<a href="/post-tag/create?id=${post.id}" class="btn btn-primary btn-sm">
									<i class="glyphicon glyphicon-edit"></i>
									Add Tag
								</a>
								<c:choose>
									<c:when test="${not empty persons}">
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
															<p>${fn:escapeXml(person.category)}</p>
														</div><!--usr-mg-info end-->
													</div>
												</li>
												</c:forEach>
											</ul>
										</div><!--widget-user end-->
									</c:when>
								</c:choose>
								<c:choose>
									<c:when test="${not empty groups}">
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
															<p>${fn:escapeXml(group.category)}</p>
														</div><!--usr-mg-info end-->
													</div>
												</li>
												</c:forEach>
											</ul>
										</div><!--widget-user end-->
									</c:when>
								</c:choose>
                            </div>
                        </div>
                    </div><!--forum-questions-sec end-->
                </div>
            </section><!--forum-page end-->
        </div>
    </div>
</div>
<!-- [END view] -->
