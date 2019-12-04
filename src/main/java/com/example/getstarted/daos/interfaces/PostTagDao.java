package com.example.getstarted.daos.interfaces;

import com.example.getstarted.objects.PostTag;
import com.example.getstarted.objects.Result;
import com.google.appengine.api.datastore.Entity;

import java.sql.SQLException;

/**
 * Interface for PersonGroup Classes
 */
public interface PostTagDao {

    Long createPostTag(PostTag postTag) throws SQLException;

    PostTag readPostTag(Long postTagId) throws SQLException;

    void deletePostTag(Long postTagId) throws SQLException;

    void deletePostTagByPostId(Long postId) throws SQLException;

    void deletePostTagByPersonId(Long personId) throws SQLException;

    void deletePostTagByGroupId(Long personId) throws SQLException;

    void deletePostTagByPostIdPersonId(Long postId, Long personId) throws SQLException;

    void deletePostTagByPostIdGroupId(Long postId, Long groupId) throws SQLException;

    PostTag entityToPostTag(Entity entity) throws SQLException;

    Result<Long> listPostByPerson(Long personId, String startCursor) throws SQLException;

    Result<Long> listAllPostByPerson(Long personId) throws SQLException;

    Result<Long> listPostByGroup(Long groupId, String startCursor) throws SQLException;

    Result<Long> listAllPostByGroup(Long groupId) throws SQLException;

    Result<PostTag> listTagsByPost(Long postId, String startCursor) throws SQLException;

    Result<PostTag> listAllTagsByPost(Long postId) throws SQLException;

    Result<Long> listPersonByPost(Long postId, String startCursor) throws SQLException;

    Result<Long> listAllPersonByPost(Long postId) throws SQLException;

    Result<Long> listGroupByPost(Long postId, String startCursor) throws SQLException;

    Result<Long> listAllGroupByPost(Long postId) throws SQLException;

}