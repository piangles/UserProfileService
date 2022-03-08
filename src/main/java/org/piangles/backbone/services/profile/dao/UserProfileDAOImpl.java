/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
 
 
package org.piangles.backbone.services.profile.dao;

import org.piangles.backbone.services.config.DefaultConfigProvider;
import org.piangles.backbone.services.profile.BasicUserProfile;
import org.piangles.backbone.services.profile.UserProfileService;
import org.piangles.core.dao.DAOException;
import org.piangles.core.dao.rdbms.AbstractDAO;
import org.piangles.core.resources.ResourceManager;

public class UserProfileDAOImpl extends AbstractDAO implements UserProfileDAO
{
	private static final String COMPONENT_ID = "c4c1c234-4cd4-45df-b610-3239be4803af";
	private static final String SAVE_PROFILE_SP = "users.save_user_profile";
	private static final String SEARCH_PROFILE_SP = "users.search_user_profile";
	private static final String RETRIEVE_PROFILE_SP = "users.retrieve_user_profile";

	private static final int USER_ID_INDEX = 3;
	private static final String FIRST_NAME = "first_name";
	private static final String LAST_NAME = "last_name";
	private static final String EMAIL_ID = "email_id";
	private static final String EMAIL_ID_VERIFIED = "email_id_verified";
	private static final String PHONE_NO = "phone_no";
	private static final String PHONE_NO_VERIFIED = "phone_no_verified";
	private static final String MFA_ENABLED = "mfa_enabled";

	public UserProfileDAOImpl() throws Exception
	{
		super.init(ResourceManager.getInstance().getRDBMSDataStore(new DefaultConfigProvider(UserProfileService.NAME, COMPONENT_ID)));
	}

	@Override
	public void insertUserProfile(String userId, BasicUserProfile profile) throws DAOException
	{
		super.executeSP(SAVE_PROFILE_SP, 8, (stmt) -> {
			stmt.setString(1, userId);
			stmt.setString(2, profile.getFirstName());
			stmt.setString(3, profile.getLastName());
			stmt.setString(4, profile.getEMailId());
			stmt.setBoolean(5, profile.isEmailIdVerified());
			stmt.setString(6, profile.getPhoneNo());
			stmt.setBoolean(7, profile.isPhoneNoVerified());
			stmt.setBoolean(8, profile.isMFAEnabled());
		});
	}

	@Override
	public String searchUserProfile(BasicUserProfile profile) throws DAOException
	{
		String userId = super.executeSPQuery(SEARCH_PROFILE_SP, 3, (stmt) -> {
			stmt.setString(1, profile.getEMailId());
			stmt.setString(2, profile.getPhoneNo());
			stmt.registerOutParameter(3, java.sql.Types.VARCHAR);
		}, (rs, call)->{
			return call.getString(USER_ID_INDEX);
		});
		
		return userId;
	}
	
	@Override
	public BasicUserProfile retrieveUserProfile(String userId) throws DAOException
	{
		BasicUserProfile profile = super.executeSPQuery(RETRIEVE_PROFILE_SP, 1, (stmt) -> {
			stmt.setString(1, userId);
		}, (rs, call) -> {
			return new BasicUserProfile(userId,
										rs.getString(FIRST_NAME), 
										rs.getString(LAST_NAME), 
										rs.getString(EMAIL_ID),
										rs.getBoolean(EMAIL_ID_VERIFIED),
										rs.getString(PHONE_NO),
										rs.getBoolean(PHONE_NO_VERIFIED),
										rs.getBoolean(MFA_ENABLED));
		});

		return profile;
	}

	public void updateUserProfile(String userId, BasicUserProfile profile) throws DAOException
	{
		super.executeSP(SAVE_PROFILE_SP, 8, (stmt) -> {
			stmt.setString(1, userId);
			stmt.setString(2, profile.getFirstName());
			stmt.setString(3, profile.getLastName());
			stmt.setString(4, profile.getEMailId());
			stmt.setBoolean(5, profile.isEmailIdVerified());
			stmt.setString(6, profile.getPhoneNo());
			stmt.setBoolean(7, profile.isPhoneNoVerified());
			stmt.setBoolean(8, profile.isMFAEnabled());
		});
	}
}
