package com.example.getstarted.daos;

import com.example.getstarted.objects.User;
import com.example.getstarted.objects.Result;
import java.sql.SQLException;

/**
 * Interface for UserDao Class
 */
public interface UserDao {
    Long createUser(User user) throws SQLException;

    User readUser(Long userId) throws SQLException;

    void updateUser(User user) throws SQLException;

    void deleteUser(Long userId) throws SQLException;

    Result<User> listUsers(String startCursor) throws SQLException;
}
