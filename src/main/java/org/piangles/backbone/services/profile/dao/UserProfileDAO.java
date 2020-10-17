package org.piangles.backbone.services.profile.dao;

import org.piangles.backbone.services.profile.BasicUserProfile;
import org.piangles.core.dao.DAOException;

public interface UserProfileDAO
{
	public void insertUserProfile(String userId, BasicUserProfile profile) throws DAOException;
	public BasicUserProfile retrieveUserProfile(String userId) throws DAOException;
	public void updateUserProfile(String userId, BasicUserProfile profile) throws DAOException;
}
