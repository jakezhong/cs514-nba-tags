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
                                <ul class="social_links">
                                    <c:choose>
                                        <c:when test="${not empty socialLinks}">
                                            <c:forEach items="${socialLinks}" var ="socialLink">
                                                <c:if test="${socialLink.key=='Facebook'}">
                                                    <li><a href="${socialLink.value}" title=""><i class="fa fa-facebook-square"></i> ${fn:escapeXml(socialLink.value)}</a>
                                                        <a href="/delete/socialLink?socialLinkName=${socialLink.key}&personId=${person.id}">
                                                            <i class="fa fa-trash-o" style="font-size:24px"></i>
                                                        </a>
                                                    </li>
                                                </c:if>
                                                <c:if test="${socialLink.key=='Twitter'}">
                                                    <li><a href="${socialLink.value}" title=""><i class="fa fa-twitter"></i> ${fn:escapeXml(socialLink.value)}</a>
                                                        <a href="/delete/socialLink?socialLinkName=${socialLink.key}&personId=${person.id}">
                                                            <i class="fa fa-trash-o" style="font-size:24px"></i>
                                                        </a></li>
                                                </c:if>
                                                <c:if test="${socialLink.key=='Instagram'}">
                                                    <li><a href="${socialLink.value}" title=""><i class="fa fa-instagram"></i> ${fn:escapeXml(socialLink.value)}</a>
                                                        <a href="/delete/socialLink?socialLinkName=${socialLink.key}&personId=${person.id}">
                                                            <i class="fa fa-trash-o" style="font-size:24px"></i>
                                                        </a></li>
                                                </c:if>
                                                <c:if test="${socialLink.key=='Youtube'}">
                                                    <li><a href="${socialLink.value}" title=""><i class="fa fa-youtube"></i> ${fn:escapeXml(socialLink.value)}</a>
                                                        <a href="/delete/socialLink?socialLinkName=${socialLink.key}&personId=${person.id}">
                                                            <i class="fa fa-trash-o" style="font-size:24px"></i>
                                                        </a></li>
                                                </c:if>
                                                <c:if test="${socialLink.key=='Linkedin'}">
                                                    <li><a href="${socialLink.value}" title=""><i class="fa fa-linkedin"></i> ${fn:escapeXml(socialLink.value)}</a>
                                                        <a href="/delete/socialLink?socialLinkName=${socialLink.key}&personId=${person.id}">
                                                            <i class="fa fa-trash-o" style="font-size:24px"></i>
                                                        </a></li>
                                                </c:if>
                                                <c:if test="${socialLink.key=='website'}">
                                                    <li><a href="${socialLink.value}" title=""><i class="la la-globe"></i> ${fn:escapeXml(socialLink.value)}</a>
                                                        <a href="/delete/socialLink?socialLinkName=${socialLink.key}&personId=${person.id}">
                                                            <i class="fa fa-trash-o" style="font-size:24px"></i>
                                                        </a></li>
                                                </c:if>
                                                <c:if test="${socialLink.key=='other'}">
                                                    <li><a href="${socialLink.value}" title=""><i class="la la-globe"></i> ${fn:escapeXml(socialLink.value)}</a>
                                                        <a href="/delete/socialLink?socialLinkName=${socialLink.key}&personId=${person.id}">
                                                            <i class="fa fa-trash-o" style="font-size:24px"></i>
                                                        </a></li>
                                                </c:if>
                                            </c:forEach>
                                        </c:when>
                                    </c:choose>
                                    <form method="POST" action="/add/socialLink" >
                                        <input type="text" name="personId" value="${person.id}" class="hidden">
                                        <div class="form-group">
                                            <label for="socialLinks">Add social links</label>
                                            <select name="socialLink" class="form-control" id="socialLink">
                                                <option value="website">Personal Website</option>
                                                <option value="Facebook">Facebook</option>
                                                <option value="Twitter">Twitter</option>
                                                <option value="Instagram">Instagram</option>
                                                <option value="Youtube">Youtube</option>
                                                <option value="Linkedin">Linkedin</option>
                                                <option value ="other">other</option>
                                                <input name= "other" type="text" style="display:none;"/>
                                            </select>
                                        </div>
                                        <div class="form-socialLinks">
                                            <label for="socialLinks">Links</label>
                                            <textarea name="socialLinkUrl" id="socialLinks" class="form-control" rows="2"></textarea>
                                        </div>
                                        <button type="submit" class="btn btn-success">Save</button>
                                    </form>
                                </ul>
                            </div><!--user_profile end-->
                        </div><!--main-left-sidebar end-->
                    </div>
                    <div class="col-lg-9">
                        <div class="main-ws-sec">
                            <div class="user-tab-sec">
                                <ul class="flw-hr">
                                    <li>
                                        <a href="update?id=${person.id}" class="btn btn-primary btn-sm">
                                            <i class="glyphicon glyphicon-edit"></i>
                                            Edit person
                                        </a>
                                    </li>
                                    <li>
                                        <a href="delete?id=${person.id}" class="btn btn-danger btn-sm">
                                            <i class="glyphicon glyphicon-trash"></i>
                                            Delete person
                                        </a>
                                    </li>
                                    <li>
                                        <a href="enjoy?personId=${person.id}" class="btn btn-primary btn-sm">
                                            <i class="glyphicon glyphicon-edit"></i>
                                            Enjoy Group
                                        </a>
                                    </li>
                                    <li>
                                    </li>
                                </ul>
                                <h3>${fn:escapeXml(person.first)} ${fn:escapeXml(not empty person.last?person.last:'Unknown')}</h3>
                                <div class="star-descp">
                                    <p><span>${fn:escapeXml(person.title)}</span></p>
                                    <p class="category"><span>${fn:escapeXml(person.category)}</span></p>
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
                                        <p>${fn:escapeXml(person.description)}</p>
                                    </div>
                                </div>
                            </div>
							<c:if test="${not empty groups}">
								<div class="post-bar">
									<div class="detail_descp">
										<form method ="POST" action ="/group/quit" class="detail_descp">
										<h3>Groups</h3>
											<button type="submit"  class="btn btn-primary btn-sm">
												<i class="glyphicon glyphicon-edit"></i>
												Quit Groups
											</button>
											<input type="text" name="personId" value="${person.id}" class="hidden">
										<div class="companies-list">
											<div class="row">
												<c:forEach items="${groups}" var="group">
													<div class="col-lg-4 col-md-6  col-sm-12 col-12">
														<div class="company_profile_info">
															<a href="/group/read?id=${group.id}" title="">
																<div class="company-up-info">
																	<img alt="ahhh" src="${fn:escapeXml(not empty group.imageUrl?group.imageUrl:'http://placekitten.com/g/128/192')}">
																	<h3>${fn:escapeXml(group.name)}</h3>
																	<h5 class="category">${fn:escapeXml(group.category)}</h5>
																</div>
																<div class="view-more-pro">View Detail</div>
															</a>
															<input type="checkbox" name="groups" value=${group.id} />
														</div><!--company_profile_info end-->
													</div>
												</c:forEach>
											</div>
											<c:if test="${not empty cursor}">
												<div class="read-more">
													<a href="?cursor=${fn:escapeXml(cursor)}&id=${group.id}"  class="btn btn-primary btn-sm">
														<i class="glyphicon glyphicon-edit"></i>More
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
												<div class="col-lg-6 col-sm-12">
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
															<li><a href="/post/read?id=${post.id}" title="" class="com"><img src="${pageContext.request.contextPath}/ui/images/com.png" alt=""> Comment 15</a></li>
														</ul>
													</div>
												</div>
											</c:forEach>
										</div>
										<c:if test="${not empty cursor}">
											<div class="process-comm">
												<a href="?cursor=$	{fn:escapeXml(cursor)}" title=""><img src="${pageContext.request.contextPath}/ui/images/process-icon.png" alt=""></a>
											</div><!--process-comm end-->
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
