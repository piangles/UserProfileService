package org.piangles.backbone.services.profile.dao;

import org.piangles.backbone.services.config.DefaultConfigProvider;
import org.piangles.backbone.services.profile.BasicUserProfile;
import org.piangles.core.dao.DAOException;
import org.piangles.core.dao.rdbms.AbstractDAO;
import org.piangles.core.resources.ResourceManager;

public class UserProfileDAOImpl extends AbstractDAO implements UserProfileDAO
{
	private static final String COMPONENT_ID = "c4c1c234-4cd4-45df-b610-3239be4803af";
	private static final String CREATE_PROFILE_SP = "Backbone.CreateUserProfile";
	private static final String RETRIEVE_PROFILE_SP = "Backbone.RetrieveUserProfile";
	private static final String UPDATE_PROFILE_SP = "Backbone.UpdateUserProfile";

	private static final String FIRST_NAME = "FirstName";
	private static final String LAST_NAME = "LastName";
	private static final String EMAIL_ID = "EMailId";

	public UserProfileDAOImpl() throws Exception
	{
		super.init(ResourceManager.getInstance().getRDBMSDataStore(new DefaultConfigProvider("UserProfileService", COMPONENT_ID)));
	}

	public void insertUserProfile(String userId, BasicUserProfile profile) throws DAOException
	{
		super.executeSP(CREATE_PROFILE_SP, 4, (stmt) -> {
			stmt.setString(1, userId);
			stmt.setString(2, profile.getFirstName());
			stmt.setString(3, profile.getLastName());
			stmt.setString(4, profile.getEMailId());
		});
	}

	public BasicUserProfile retrieveUserProfile(String userId) throws DAOException
	{
		BasicUserProfile profile = super.executeSPQuery(RETRIEVE_PROFILE_SP, 1, (stmt) -> {
			stmt.setString(1, userId);
		}, (rs, call) -> {
			return new BasicUserProfile(rs.getString(FIRST_NAME), rs.getString(LAST_NAME), rs.getString(EMAIL_ID));
		});

		return profile;
	}

	public void updateUserProfile(String userId, BasicUserProfile profile) throws DAOException
	{
		super.executeSP(UPDATE_PROFILE_SP, 4, (stmt) -> {
			stmt.setString(1, userId);
			stmt.setString(2, profile.getFirstName());
			stmt.setString(3, profile.getLastName());
			stmt.setString(4, profile.getEMailId());
		});
	}
}
