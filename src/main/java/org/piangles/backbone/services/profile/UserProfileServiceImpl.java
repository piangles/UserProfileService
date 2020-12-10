package org.piangles.backbone.services.profile;

import org.piangles.backbone.services.Locator;
import org.piangles.backbone.services.id.IdException;
import org.piangles.backbone.services.id.IdService;
import org.piangles.backbone.services.id.Identifier;
import org.piangles.backbone.services.logging.LoggingService;
import org.piangles.backbone.services.profile.dao.UserProfileDAO;
import org.piangles.backbone.services.profile.dao.UserProfileDAOImpl;
import org.piangles.core.dao.DAOException;

public final class UserProfileServiceImpl implements UserProfileService
{
	private static final String USER_PROFILE_IDTYPE = "UserProfile";	
	private LoggingService logger = Locator.getInstance().getLoggingService();
	private IdService idService	 = Locator.getInstance().getIdService();
	private UserProfileDAO userProfileDAO = null; 

	public UserProfileServiceImpl() throws Exception
	{
		userProfileDAO = new UserProfileDAOImpl();
	}
	
	@Override
	public String createProfile(BasicUserProfile profile) throws UserProfileException
	{
		Identifier id = null;
		try
		{
			id = idService.getNextIdentifier(USER_PROFILE_IDTYPE);
			logger.info("Creating a new UserProfile for id:" + id.getValue());
			userProfileDAO.insertUserProfile(id.getValue(), profile);
		}
		catch (IdException | DAOException e)
		{
			String message = "Failed creating UserProfile for : " + id + " because of : " + e.getMessage();
			logger.error(message, e);
			throw new UserProfileException(message);
		}
		
		return id.getValue();
	}

	@Override
	public String searchProfile(BasicUserProfile profile) throws UserProfileException
	{
		String userId = null;
		try
		{
			logger.info("Searching for UserProfile.");
			userId = userProfileDAO.searchUserProfile(profile);
		}
		catch (DAOException e)
		{
			String message = "Failed searching for UserProfile because of : " + e.getMessage();
			logger.error(message, e);
			throw new UserProfileException(message);
		}
		
		return userId;
	}
	
	@Override
	public BasicUserProfile getProfile(String userId) throws UserProfileException
	{
		BasicUserProfile profile = null;
		try
		{
			logger.info("Retriving UserProfile for: " + userId);
			profile = userProfileDAO.retrieveUserProfile(userId);
		}
		catch (DAOException e)
		{
			String message = "Failed retriving UserProfile for : " + userId + " because of : " + e.getMessage();
			logger.error(message, e);
			throw new UserProfileException(message);
		}

		return profile;
	}

	@Override
	public void updateProfile(String userId, BasicUserProfile profile) throws UserProfileException
	{
		try
		{
			logger.info("Updating UserProfile for: " + userId);
			userProfileDAO.updateUserProfile(userId, profile);
		}
		catch (DAOException e)
		{
			String message = "Failed updating UserProfile for : " + userId + " because of : " + e.getMessage();
			logger.error(message, e);
			throw new UserProfileException(message);
		}
	}
}
