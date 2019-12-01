package com.example.getstarted.daos.interfaces;

import com.example.getstarted.objects.Profile;
import com.example.getstarted.objects.Result;
import java.sql.SQLException;

/**
 * Interface for ProfileDao Class
 */
public interface ProfileDao {
    Long createProfile(Profile profile) throws SQLException;

    Profile readProfile(Long profileId) throws SQLException;

    Result<Profile> findProfile(String userId) throws SQLException;

    void updateProfile(Profile profile) throws SQLException;

    void deleteProfile(Long profileId) throws SQLException;

    Result<Profile> listProfiles(String startCursor) throws SQLException;
}
