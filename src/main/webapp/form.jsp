<!-- [START form] -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="container">
  <h3>
    <c:out value="${action}" /> person
  </h3>

  <form method="POST" action="${destination}" enctype="multipart/form-data">

    <div class="form-group">
      <label for="first">First</label>
      <input type="text" name="first" id="first" value="${fn:escapeXml(person.first)}" class="form-control" />
    </div>

    <div class="form-group">
      <label for="last">Last</label>
      <input type="text" name="last" id="last" value="${fn:escapeXml(person.last)}" class="form-control" />
    </div>

    <div class="form-group">
      <label for="description">Description</label>
      <textarea name="description" id="description" class="form-control">${fn:escapeXml(person.description)}</textarea>
    </div>

    <div class="form-group ${isCloudStorageConfigured ? '' : 'hidden'}">
        <label for="file">Cover Image</label>
		<input type="file" name="file" id="file" class="form-control image-uploader" />
		<img class="image-uploader-reviewer" src="" alt="" />
    </div>

    <div class="form-group hidden">
        <label for="imageUrl">Cover Image URL</label>
        <input type="hidden" name="id" value="${person.id}" />
    </div>

    <button type="submit" class="btn btn-success">Save</button>
  </form>
</div>
<!-- [END form] -->
