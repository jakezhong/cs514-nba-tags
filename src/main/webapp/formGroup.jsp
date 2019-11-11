<!-- [START form] -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="container">
    <h3>
        <c:out value="${action}" /> group
    </h3>

    <form method="POST" action="${destination}" enctype="multipart/form-data">

        <div class="form-group">
            <label for="name">Group Name</label>
            <input type="text" name="name" id="name" value="${fn:escapeXml(group.name)}" class="form-control" />
        </div>


        <div class="form-group">
            <label for="description">Description</label>
            <textarea name="description" id="description" class="form-control">${fn:escapeXml(group.description)}</textarea>
        </div>

        <div class="form-group ${isCloudStorageConfigured ? '' : 'hidden'}">
            <label for="file">Cover Image</label>
            <input type="file" name="file" id="file" class="form-control image-uploader" />
            <img class="image-uploader-reviewer" src="" alt="" />
        </div>

        <div class="form-group hidden">
            <label for="imageUrl">Cover Image URL</label>
            <input type="hidden" name="id" value="${group.id}" />
        </div>

        <button type="submit" class="btn btn-success">Save</button>
    </form>
</div>
<!-- [END form] -->
