<!-- [START view] -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="container">
    <h3>Person</h3>
    <div class="btn-group">
        <a href="/update?id=${person.id}" class="btn btn-primary btn-sm">
            <i class="glyphicon glyphicon-edit"></i>
            Edit person
        </a>
        <a href="/delete?id=${person.id}" class="btn btn-danger btn-sm">
            <i class="glyphicon glyphicon-trash"></i>
            Delete person
        </a>
        <a href="/listGroupByperson?id=${person.id}" class="btn btn-primary btn-sm">
            <i class="glyphicon glyphicon-edit"></i>
            View Group
        </a>
    </div>

    <div class="media">
        <div class="media-left">
            <img class="person-image" height="200" src="${fn:escapeXml(not empty person.imageUrl?person.imageUrl:'http://placekitten.com/g/128/192')}">
        </div>
        <div class="media-body">
            <h3>Name</h3>
            <h4 class="person-name">
                ${fn:escapeXml(person.first)} ${fn:escapeXml(not empty person.last?person.last:'Unknown')}
            </h4>
            <h3>Title</h3>
            <p class="person-title">${fn:escapeXml(person.title)}</p>
            <h3>Introduction</h3>
            <p class="person-introduction">${fn:escapeXml(person.introduction)}</p>
            <h3>Email</h3>
            <p class="person-email">${fn:escapeXml(person.email)}</p>
            <h3>Phone</h3>
            <p class="person-phone">${fn:escapeXml(person.phone)}</p>
            <h3>Address</h3>
            <p class="person-address">${fn:escapeXml(person.address)}</p>
            <h3>Category</h3>
            <p class="person-category">${fn:escapeXml(person.category)}</p>
            <h3>Type</h3>
            <p class="person-type">${fn:escapeXml(person.type)}</p>
            <h3>Linkedin</h3>
            <p class="person-linkedin">${fn:escapeXml(person.linkedin)}</p>
            <h3>Facebook</h3>
            <p class="person-facebook">${fn:escapeXml(person.facebook)}</p>
            <h3>Twitter</h3>
            <p class="person-twitter">${fn:escapeXml(person.twitter)}</p>
            <h3>Instagram</h3>
            <p class="person-instagram">${fn:escapeXml(person.instagram)}</p>
            <h3>Youtube</h3>
            <p class="person-youtube">${fn:escapeXml(person.youtube)}</p>
            <h3>Website</h3>
            <p class="person-website">${fn:escapeXml(person.website)}</p>
            <h3>Description</h3>
            <p class="person-description">${fn:escapeXml(person.description)}</p>
            <h3>Added By</h3>
            <p>
                <small class="person-added-by">Added by
                    ${fn:escapeXml(not empty person.createdBy?person.createdBy:'Anonymous')}
                </small>
            </p>
            <h3>Added On</h3>
            <p>
                <small class="person-publishedDate">Added on
                    ${fn:escapeXml(not empty person.publishedDate?person.publishedDate:'')}
                </small>
            </p>
        </div>
    </div>
</div>
<!-- [END view] -->
