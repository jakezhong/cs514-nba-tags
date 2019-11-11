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
                           <div class="form-group">
                               <label for="name">Group Name</label>
                               <input type="text" name="name" id="name" value="${fn:escapeXml(group.name)}" class="form-control" />
                           </div>

                           <div class="form-group">
                               <label for="introduction">Introduction</label>
                               <textarea name="introduction" id="introduction" class="form-control" height="100">${fn:escapeXml(person.introduction)}</textarea>
                           </div>

                           <div class="form-group">
                               <label for="description">Description</label>
                               <textarea name="description" id="description" class="form-control">${fn:escapeXml(group.description)}</textarea>
                           </div>

                            <div class="form-group ${isCloudStorageConfigured ? '' : 'hidden'}">
                                <label for="file">Cover Image</label>
                                <div class="fileupload">
                                    <input type="file" name="file" id="file" class="form-control image-uploader">
                                    <img class="image-uploader-reviewer" src="" alt="" />
                                </div>
                            </div>

                           <div class="form-group hidden">
                               <label for="imageUrl">Cover Image URL</label>
                               <input type="hidden" name="id" value="${group.id}" />
                           </div>
                        </div>
                        <div class="col-lg-4">
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
