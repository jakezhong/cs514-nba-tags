<!--
Copyright 2016 Google Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
-->
<!-- [START view] -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="container">
    <h3>Group</h3>
    <div class="btn-group">
        <a href="/updateGroup?id=${group.id}" class="btn btn-primary btn-sm">
            <i class="glyphicon glyphicon-edit"></i>
            Edit group
        </a>
        <a href="/deleteGroup?id=${group.id}" class="btn btn-danger btn-sm">
            <i class="glyphicon glyphicon-trash"></i>
            Delete group
        </a>
        <a href="/association/create?id=${group.id}" class="btn btn-danger btn-sm">
            <i class="glyphicon glyphicon-edit"></i>
            Join group
        </a>
    </div>

    <div class="media">
        <div class="media-left">
            <img class="group-image" height="200" src="${fn:escapeXml(not empty group.imageUrl?group.imageUrl:'http://placekitten.com/g/128/192')}">
        </div>
        <div class="media-body">
            <h4 class="group-name">
                ${fn:escapeXml(group.name)}
            </h4>
            <h5 class="group-name">${fn:escapeXml(not empty group.name?group.name:'Unknown')}</h5>
            <p class="group-description">${fn:escapeXml(group.description)}</p>
            <small class="group-added-by">Added by
                ${fn:escapeXml(not empty group.createdBy?group.createdBy:'Anonymous')}</small>
        </div>
    </div>

	<c:choose>
		<c:when test="${empty persons}">
			<p>No persons found</p>
		</c:when>
		<c:otherwise>
			<div class="companies-list">
				<div class="row">
					<c:forEach items="${persons}" var="person">
						<div class="col-lg-3 col-md-4 col-sm-6 col-12">
							<div class="company_profile_info">
								<div class="company-up-info">
									<img alt="ahhh" height="200"src="${fn:escapeXml(not empty person.imageUrl?person.imageUrl:'http://placekitten.com/g/128/192')}">
									<h3>${fn:escapeXml(person.first)} ${fn:escapeXml(person.last)}</h3>
									<h4>NBA Player</h4>
									<ul>
										<li><a href="#" title="" class="follow">Follow</a></li>
										<li><a href="#" title="" class="message-us"><i class="fa fa-envelope"></i></a></li>
										<li><a href="#" title="" class="hire-us">Hire</a></li>
									</ul>
								</div>
								<a href="/read?id=${person.id}" title="" class="view-more-pro">View Profile</a>
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
						<div class="process-comm">
							<a href="?cursor=${fn:escapeXml(cursor)}" title=""><img src="images/process-icon.png" alt=""></a>
						</div>
					</c:if>
				</div>
			</div>
		</c:otherwise>
	</c:choose>

</div>
<!-- [END view] -->
