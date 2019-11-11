<!-- [START form] -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="container">
  <h3>
    <c:out value="${action}" /> person
  </h3>

  <form method="POST" action="${destination}" >
    <input type="text" name="groupId" value="${groupId}" class="hidden">
    <div class="form-group">
        <label for="persons">Example multiple select</label>
        <select name="personId" class="form-control" id="persons">
            <c:forEach items="${persons}" var="person">
                <option value="${fn:escapeXml(person.id)}">${fn:escapeXml(person.first)} - ${fn:escapeXml(person.last)}</option>
            </c:forEach>
        </select>
    </div>

    <button type="submit" class="btn btn-success">Save</button>
  </form>
</div>
<!-- [END form] -->
