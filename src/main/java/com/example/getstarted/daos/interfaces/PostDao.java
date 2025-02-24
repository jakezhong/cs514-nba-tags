package com.example.getstarted.daos.interfaces;

import com.example.getstarted.objects.Post;
import com.example.getstarted.objects.Result;

import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map;

/**
 * Interface for PostDao Class
 */
public interface PostDao {
  Long createPost(Post post) throws SQLException;

  Post readPost(Long postId) throws SQLException;

  void updatePost(Post post) throws SQLException;

  void deletePost(Long postId) throws SQLException;

  void likePost(Post post) throws SQLException;

  Result<Post> listPosts(String startCursor) throws SQLException;

  Result<Post> listPostsBySearch(Hashtable search, String startCursor) throws SQLException;

  Result<Post> listAllPosts() throws SQLException;

  Result<Post> listPostsByUser(String userId, String startCursor) throws SQLException;

  Result<Post> listAllPostsByUser(String userId) throws SQLException;

  void addCommentInPost(long postId, String createdByString, String content )throws SQLException;

  Map<String,String> listComment(long postId) throws SQLException;

  void deleteComment( long postId,String commentKey) throws  SQLException;
}
