<!-- [START form] -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<main class="main">
    <div class="container">
        <div class="post-project">
            <h3><c:out value="${action}" /> Tags</h3>
            <div class="post-project-fields">
                <form method="POST" action="${destination}" >
                    <input type="text" name="postId" value="${postId}" class="hidden">
                    <div class="form-group">
                        <label for="persons">Tag Person</label>
                        <select name="personId" class="form-control" id="persons">
                            <option value="" selected disabled>Choose Person</option>
                            <c:forEach items="${persons}" var="person">
                            <option value="${fn:escapeXml(person.id)}">${fn:escapeXml(person.first)} ${fn:escapeXml(person.last)}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="group">Tag Group</label>
                        <select name="groupId" class="form-control" id="groups">
                            <option value="" selected disabled>Choose Group</option>
                            <c:forEach items="${groups}" var="group">
                            <option value="${fn:escapeXml(group.id)}">${fn:escapeXml(group.name)}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-success">Save</button>
                </form>
            </div>
        </div>
    </div>
</main>
<!-- [END form] -->
