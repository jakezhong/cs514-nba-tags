<!-- [START list] -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<main class="main">
    <div class="container">
        <div class="company-title">
            <h3>All Groups</h3>
        </div>
        <div class="main-section-data">
            <div class="row">
                <div class="col-lg-3">
                    <div class="filter-secs">
                        <div class="paddy">
                            <div class="filter-dd">
                                <form action="" method="GET">
									<div class="form-group">
										<label for="category">Search</label>
                                    	<input type="text" name="name" placeholder="${empty name ? 'Group name...' : name}">
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
							<c:when test="${empty groups}">
								<div class="no-result center">
									<h3>No groups found</h3>
								</div>
							</c:when>
							<c:otherwise>
								<div class="companies-list">
									<div class="row">
										<c:forEach items="${groups}" var="group">
											<div class="col-lg-4 col-md-6 col-sm-12">
												<div class="company_profile_info">
													<a href="/group/read?id=${group.id}" title="">
														<div class="company-up-info">
															<img alt="ahhh" src="${fn:escapeXml(not empty group.imageUrl?group.imageUrl:'http://placekitten.com/g/128/192')}">
															<h3>${fn:escapeXml(group.name)}</h3>
															<h5 class="category">${fn:escapeXml(group.category)}</h5>
														</div>
														<div class="view-more-pro">View Detail</div>
													</a>
												</div><!--company_profile_info end-->
											</div>
										</c:forEach>
									</div>
									<c:if test="${not empty cursor}">
										<div class="read-more">
											<a href="?cursor=${fn:escapeXml(cursor)}"  class="btn btn-primary btn-sm">
												<i class="glyphicon glyphicon-edit"></i>More
											</a>
										</div>
									</c:if>
								</div>
							</c:otherwise>
						</c:choose>
                    </div><!--main-ws-sec end-->
                </div>
            </div>
        </div><!-- main-section-data end-->
    </div>
</main>
<!-- [END list] -->
