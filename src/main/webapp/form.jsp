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
            <input type="text" name="first" id="first" value="${fn:escapeXml(person.first)}" class="form-control" required />
        </div>

        <div class="form-group">
            <label for="last">Last</label>
            <input type="text" name="last" id="last" value="${fn:escapeXml(person.last)}" class="form-control" required />
        </div>

        <div class="form-group">
            <label for="title">Title</label>
            <input type="text" name="title" id="title" value="${fn:escapeXml(person.title)}" class="form-control" required />
        </div>

        <div class="form-group">
            <label for="introduction">Introduction</label>
            <textarea name="introduction" id="introduction" class="form-control" height="100">${fn:escapeXml(person.introduction)}</textarea>
        </div>

        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" name="email" id="email" value="${fn:escapeXml(person.email)}" class="form-control" />
        </div>

        <div class="form-group">
            <label for="phone">Phone</label>
            <input type="text" name="phone" id="phone" value="${fn:escapeXml(person.phone)}" class="form-control" />
        </div>

        <div class="form-group">
            <label for="address">Address</label>
            <input type="text" name="address" id="address" value="${fn:escapeXml(person.address)}" class="form-control" />
        </div>

        <div class="form-group">
            <label for="category">Category</label>
            <input type="text" name="category" id="category" value="${fn:escapeXml(person.category)}" class="form-control" />
        </div>

        <div class="form-group">
            <label for="type">Type</label>
            <input type="text" name="type" id="type" value="${fn:escapeXml(person.type)}" class="form-control" />
        </div>

        <div class="form-group">
            <label for="linkedin">Linkedin</label>
            <input type="url" name="linkedin" id="linkedin" value="${fn:escapeXml(person.linkedin)}" class="form-control" />
        </div>

        <div class="form-group">
            <label for="facebook">Facebook</label>
            <input type="url" name="facebook" id="facebook" value="${fn:escapeXml(person.facebook)}" class="form-control" />
        </div>

        <div class="form-group">
            <label for="twitter">Twitter</label>
            <input type="url" name="twitter" id="twitter" value="${fn:escapeXml(person.twitter)}" class="form-control" />
        </div>

        <div class="form-group">
            <label for="instagram">Instagram</label>
            <input type="url" name="instagram" id="instagram" value="${fn:escapeXml(person.instagram)}" class="form-control" />
        </div>

        <div class="form-group">
            <label for="youtube">Youtube</label>
            <input type="url" name="youtube" id="youtube" value="${fn:escapeXml(person.youtube)}" class="form-control" />
        </div>

        <div class="form-group">
            <label for="website">Website</label>
            <input type="url" name="website" id="website" value="${fn:escapeXml(person.website)}" class="form-control" />
        </div>

        <div class="form-group">
            <label for="description">Description</label>
            <textarea name="description" id="description" class="form-control" height="500">${fn:escapeXml(person.description)}</textarea>
        </div>

        <div class="form-group ${isCloudStorageConfigured ? '' : 'hidden'}">
            <label for="file">Cover Image</label>
            <input type="file" name="file" id="file" class="form-control image-uploader" />
            <img class="image-uploader-reviewer" src="${fn:escapeXml(person.website)}" alt="" />
        </div>

        <div class="form-group hidden">
            <label for="imageUrl">Cover Image URL</label>
            <input type="hidden" name="id" value="${person.id}" />
        </div>

        <button type="submit" class="btn btn-success">Add</button>
    </form>
</div>
<!-- [END form] -->
