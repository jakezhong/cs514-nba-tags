package com.example.getstarted.daos;

import com.example.getstarted.objects.OurUser;
import com.example.getstarted.objects.Result;
import java.sql.SQLException;

/**
 * Interface for UserDao Class
 */
public interface UserDao {
    Long createUser(OurUser ourUser) throws SQLException;

    OurUser readUser(Long userId) throws SQLException;

    OurUser findUser(String userId) throws SQLException;

    void updateUser(OurUser ourUser) throws SQLException;

    void deleteUser(Long userId) throws SQLException;

    Result<OurUser> listUsers(String startCursor) throws SQLException;
}
