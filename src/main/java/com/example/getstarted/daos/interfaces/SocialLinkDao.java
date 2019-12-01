package com.example.getstarted.daos.interfaces;

import com.example.getstarted.objects.Result;
import com.example.getstarted.objects.SocialLink;

import java.sql.SQLException;

public interface SocialLinkDao {

    Long createSocialLink(SocialLink socialLink) throws SQLException;

    SocialLink readSocialLink(Long socialLinkId) throws SQLException;

    void updateSocialLink(SocialLink socialLink,String socialLinkUrl) throws SQLException;

    void deleteSocialLink(Long socialLinkId) throws SQLException;

    Result<SocialLink> listSocialLinks(String startCursor) throws SQLException;

    Result<SocialLink> listSocialLinksByPerson(Long userId) throws SQLException;

    SocialLink findSocialLink(SocialLink socialLink) throws SQLException;
}
