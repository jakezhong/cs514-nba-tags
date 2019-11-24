package com.example.getstarted.daos;

import com.example.getstarted.objects.Post;
import com.example.getstarted.objects.Result;

import java.sql.SQLException;

/**
 * Interface for PostDao Class
 */
public interface PostDao {
  Long createPost(Post post) throws SQLException;

  Post readPost(Long postId) throws SQLException;

  void updatePost(Post post) throws SQLException;

  void deletePost(Long postId) throws SQLException;

  Result<Post> listPosts(String startCursor) throws SQLException;

  Result<Post> listAllPosts() throws SQLException;

  Result<Post> listPostsByUser(String userId, String startCursor) throws SQLException;

  Result<Post> listAllPostsByUser(String userId) throws SQLException;
}
