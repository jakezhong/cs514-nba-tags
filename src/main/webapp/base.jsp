<!-- [START base] -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Personshelf - Java on Google Cloud Platform</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="" />
	<c:url value="/" var="home_url" />
	<c:url value="/ui" var="main_ui" />
    <link rel="stylesheet" href="${main_ui}/css/style.css"/>
</head>
<body>
	<div class="wrapper">
		<header>
			<div class="container">
				<div class="header-data">
					<div class="header-left">
						<div class="logo">
							<a href="${home_url}" title=""><img src="${main_ui}/images/logo.png" alt=""></a>
						</div><!--logo end-->
						<div class="search-bar">
						<form>
							<input type="text" name="search" placeholder="Search...">
							<button type="submit"><i class="la la-search"></i></button>
						</form>
					</div><!--search-bar end-->
					</div>
					<div class="header-right">
						<nav>
							<ul>
								<li>
									<a href="${home_url}" title="">
										<span><img src="${main_ui}/images/icon1.png" alt=""></span>
										Home
									</a>
								</li>
								<li>
									<a href="/groups" title="">
										<span><img src="${main_ui}/images/icon2.png" alt=""></span>
										Groups
									</a>
								</li>
								<li>
									<a href="/persons" title="Persons">
										<span><img src="${main_ui}/images/icon4.png" alt=""></span>
										Persons
									</a>
								</li>
							</ul>
						</nav><!--nav end-->
						<c:choose>
							<c:when test="${not empty userEmail}">
								<div class="user-account">
									<!-- using pageContext requires jsp-api artifact in pom.xml -->
									<div class="user-info">
										<c:if test="${not empty userImageUrl}">
											<img class="img-circle" src="${fn:escapeXml(userImageUrl)}" width="24">
										</c:if>
										<c:if test="${not empty userEmail}">
										    <a href="#" title="">${fn:escapeXml(userEmail)}</a>
										</c:if>
										<i class="la la-sort-down"></i>
									</div>
									<div class="user-account-settingss">
										<ul class="us-links">
											<li><a href="/persons/mine" title="">My Persons</a></li>
											<li><a href="/groups/mine" title="">My Groups</a></li>
										</ul>
										<h3 class="tc"><a href="/logout" title="">Logout</a></h3>
									</div><!--user-account-settingss end-->
								</div>
								<div class="menu-btn">
									<a href="#" title=""><i class="fa fa-bars"></i></a>
								</div><!--menu-btn end-->
							</c:when>
							<c:otherwise>
								<div class="user-account">
									<div class="user-info">
										<a href="/login" title="">Login</a>
									</div>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
				</div><!--header-data end-->
			</div>
        </header><!--header end-->
        <c:import url="/${page}.jsp" />
	</div>
	<script type="text/javascript" src="${main_ui}/js/jquery.js"></script>
	<script type="text/javascript" src="${main_ui}/js/jquery.plugins.js"></script>
	<script type="text/javascript" src="${main_ui}/js/script.js"></script>
</body>
</html>
<!-- [END base]-->