<!-- [START form] -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<main class="main">
    <div class="container">
        <div class="post-project">
            <h3><c:out value="${action}" /> Group</h3>
            <div class="post-project-fields">
                <form method="POST" action="${destination}" enctype="multipart/form-data" class="main-form">
                    <div class="row">
                        <div class="col-lg-8">
							<div class="form-group hidden">
							   <input type="hidden" name="id" value="${group.id}" />
							</div>

							<div class="form-group">
							   <label for="name">Group Name</label>
							   <input type="text" name="name" id="name" value="${fn:escapeXml(group.name)}" class="form-control" placeholder="Required" required />
							</div>

							<div class="form-group">
							   <label for="introduction">Introduction</label>
							   <textarea name="introduction" id="introduction" class="form-control" rows="3">${fn:escapeXml(person.introduction)}</textarea>
							</div>

							<div class="form-group">
							   <label for="description">Description</label>
							   <textarea name="description" id="description" class="form-control" rows="12">${fn:escapeXml(group.description)}</textarea>
							</div>

							<div class="form-group ${isCloudStorageConfigured ? '' : 'hidden'}">
								<label for="file">Cover Image</label>
								<div class="fileupload">
									<input type="file" name="file" id="file" class="form-control image-uploader">
									<img class="image-uploader-reviewer" src="" alt="" />
								</div>
							</div>
                        </div>
                        <div class="col-lg-4">
                            <div class="form-group">
                                <label for="category">Category</label>
                                <div class="inp-field">
                                    <select name="category" id="category">
                                        <option value="" selected disabled>Choose Category</option>
                                        <option value="basketball" ${group.category=='basketball'?"selected" : ""}>Basketball</option>
                                        <option value="football" ${group.category=='football'?"selected" : ""}>Football</option>
                                        <option value="baseball" ${group.category=='baseball'?"selected" : ""}>Baseball</option>
                                        <option value="soccer" ${group.category=='soccer'?"selected" : ""}>Soccer</option>
                                        <option value="hiking" ${group.category=='hiking'?"selected" : ""}>Hiking</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="category">Status</label>
                                <div class="inp-field">
                                    <select name="status" id="status">
                                        <option value="" selected disabled>Choose Status</option>
                                        <option value="public" ${group.status=='public'?'selected' : ''}>Public</option>
                                        <option value="private" ${group.status=='private'?'selected' : ''}>Private</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-footer">
                        <button type="submit" class="btn btn-success">Save</button>
                    </div>
               </form>
            </div>
        </div>
    </div>
</main>
<!-- [END form] -->
