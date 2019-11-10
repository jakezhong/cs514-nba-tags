<!-- [START list] -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="container">
    <h3>Groups</h3>
    <a href="/createGroup" class="btn btn-success btn-sm">
        <i class="glyphicon glyphicon-plus"></i>
        Add group
    </a>
	<c:choose>
		<c:when test="${empty groups}">
			<p>No groups found</p>
		</c:when>
		<c:otherwise>
			<div class="companies-list">
				<div class="row">
					<c:forEach items="${groups}" var="group">
						<div class="col-lg-3 col-md-4 col-sm-6 col-12">
							<div class="company_profile_info">
								<div class="company-up-info">
									<img alt="ahhh" height="200"src="${fn:escapeXml(not empty group.imageUrl?group.imageUrl:'http://placekitten.com/g/128/192')}">
									<h3>${fn:escapeXml(group.name)}</h3>
									<ul>
										<li><a href="#" title="" class="follow">Follow</a></li>
										<li><a href="#" title="" class="message-us"><i class="fa fa-envelope"></i></a></li>
										<li><a href="#" title="" class="hire-us">Hire</a></li>
									</ul>
								</div>
								<a href="/readGroup?id=${group.id}" title="" class="view-more-pro">View Detail</a>
							</div><!--company_profile_info end-->
						</div>
					</c:forEach>
					<div class="process-comm">
						<a href="?cursor=${fn:escapeXml(cursor)}" title=""><img src="images/process-icon.png" alt=""></a>
					</div>
					<c:if test="${not empty cursor}">
						<nav>
							<ul class="pager">
								<li><a href="?cursor=${fn:escapeXml(cursor)}">More</a></li>
							</ul>
						</nav>
					</c:if>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</div>
<!-- [END list] -->
