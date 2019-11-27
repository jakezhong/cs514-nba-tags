<!-- [START form] -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<main class="main">
    <div class="container">
        <div class="post-project">
            <h3><c:out value="${action}" /> Post</h3>
            <div class="post-project-fields">
                <form method="POST" action="${destination}" enctype="multipart/form-data" class="main-form">
                    <div class="row">
                        <div class="col-lg-8">
							<div class="form-group hidden">
								<input type="hidden" name="id" value="${post.id}" />
							</div>
							
							<div class="form-group">
								<label for="name">Post Title</label>
								<input type="text" name="title" id="title" value="${fn:escapeXml(post.title)}" class="form-control" placeholder="Required" required />
							</div>

							<div class="form-group">
								<label for="introduction">Introduction</label>
								<textarea name="introduction" id="introduction" class="form-control" rows="3">${fn:escapeXml(post.introduction)}</textarea>
							</div>

							<div class="form-group">
								<label for="description">Description</label>
								<textarea name="description" id="description" class="form-control" rows="12">${fn:escapeXml(post.description)}</textarea>
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
                                        <option value="basketball" ${post.category=='basketball'?"selected" : ""}>Basketball</option>
                                        <option value="football" ${post.category=='football'?"selected" : ""}>Football</option>
                                        <option value="baseball" ${post.category=='baseball'?"selected" : ""}>Baseball</option>
                                        <option value="soccer" ${post.category=='soccer'?"selected" : ""}>Soccer</option>
                                        <option value="hiking" ${post.category=='hiking'?"selected" : ""}>Hiking</option>
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
