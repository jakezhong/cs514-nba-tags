<!--
Copyright 2016 Google Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
-->
<!-- [START view] -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="container">
    <h3>Group</h3>
    <div class="btn-group">
        <a href="/updateGroup?id=${group.id}" class="btn btn-primary btn-sm">
            <i class="glyphicon glyphicon-edit"></i>
            Edit group
        </a>
        <a href="/deleteGroup?id=${group.id}" class="btn btn-danger btn-sm">
            <i class="glyphicon glyphicon-trash"></i>
            Delete group
        </a>
        <a href="/association/create?id=${group.id}" class="btn btn-danger btn-sm">
            <i class="glyphicon glyphicon-edit"></i>
            Join group
        </a>
        <a href="/groupMember?id=${group.id}" class="btn btn-danger btn-sm">
            <i class="glyphicon glyphicon-edit"></i>
            Group member
        </a>
    </div>

    <div class="media">
        <div class="media-left">
            <img class="group-image" height="200" src="${fn:escapeXml(not empty group.imageUrl?group.imageUrl:'http://placekitten.com/g/128/192')}">
        </div>
        <div class="media-body">
            <h4 class="group-name">
                ${fn:escapeXml(group.name)}
            </h4>
            <h5 class="group-name">${fn:escapeXml(not empty group.name?group.name:'Unknown')}</h5>
            <p class="group-description">${fn:escapeXml(group.description)}</p>
            <small class="group-added-by">Added by
                ${fn:escapeXml(not empty group.createdBy?group.createdBy:'Anonymous')}</small>

        </div>
    </div>


</div>
<!-- [END view] -->
