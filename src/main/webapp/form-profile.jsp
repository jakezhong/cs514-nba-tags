<!-- [START form] -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<main class="main">
    <div class="container">
        <div class="post-project">
            <h3><c:out value="${action}" /> Profile</h3>
            <div class="post-project-fields">
                <form method="POST" action="${destination}" enctype="multipart/form-data" class="main-form">
                    <div class="row">
                        <div class="col-lg-8">
                            <div class="form-group hidden">
                                <input type="hidden" name="id" value="${profile.id}" />
                            </div>
							
                            <div class="form-group">
                                <label for="first">First</label>
                                <input type="text" name="first" id="first" value="${fn:escapeXml(profile.first)}" class="form-control" placeholder="Required" required />
                            </div>

                            <div class="form-group">
                                <label for="last">Last</label>
                                <input type="text" name="last" id="last" value="${fn:escapeXml(profile.last)}" class="form-control" placeholder="Required" required />
                            </div>

                            <div class="form-group">
                                <label for="title">Title</label>
                                <input type="text" name="title" id="title" value="${fn:escapeXml(profile.title)}" class="form-control" placeholder="Required" required />
                            </div>

                            <div class="form-group">
                                <label for="introduction">Introduction</label>
                                <textarea name="introduction" id="introduction" class="form-control" rows="3">${fn:escapeXml(profile.introduction)}</textarea>
                            </div>

                            <div class="form-group">
                                <label for="description">Description</label>
                                <textarea name="description" id="description" class="form-control" rows="12">${fn:escapeXml(profile.description)}</textarea>
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
                                <label for="phone">Phone</label>
                                <input type="text" name="phone" id="phone" value="${fn:escapeXml(profile.phone)}" class="form-control" />
                            </div>

                            <div class="form-group">
                                <label for="address">Address</label>
                                <input type="text" name="address" id="address" value="${fn:escapeXml(profile.address)}" class="form-control" />
                            </div>

                            <div class="form-group">
                                <label for="linkedin">Linkedin</label>
                                <input type="url" name="linkedin" id="linkedin" value="${fn:escapeXml(profile.linkedin)}" class="form-control" />
                            </div>

                            <div class="form-group">
                                <label for="facebook">Facebook</label>
                                <input type="url" name="facebook" id="facebook" value="${fn:escapeXml(profile.facebook)}" class="form-control" />
                            </div>

                            <div class="form-group">
                                <label for="twitter">Twitter</label>
                                <input type="url" name="twitter" id="twitter" value="${fn:escapeXml(profile.twitter)}" class="form-control" />
                            </div>

                            <div class="form-group">
                                <label for="instagram">Instagram</label>
                                <input type="url" name="instagram" id="instagram" value="${fn:escapeXml(profile.instagram)}" class="form-control" />
                            </div>

                            <div class="form-group">
                                <label for="youtube">Youtube</label>
                                <input type="url" name="youtube" id="youtube" value="${fn:escapeXml(profile.youtube)}" class="form-control" />
                            </div>

                            <div class="form-group">
                                <label for="website">Website</label>
                                <input type="url" name="website" id="website" value="${fn:escapeXml(profile.website)}" class="form-control" />
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
