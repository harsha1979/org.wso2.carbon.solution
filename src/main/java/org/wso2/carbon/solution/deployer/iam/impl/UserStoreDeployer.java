package org.wso2.carbon.solution.deployer.iam.impl;


import org.apache.commons.lang.StringUtils;
import org.wso2.carbon.identity.user.store.configuration.stub.UserStoreConfigAdminServiceIdentityUserStoreMgtException;
import org.wso2.carbon.identity.user.store.configuration.stub.UserStoreConfigAdminServiceStub;
import org.wso2.carbon.identity.user.store.configuration.stub.dto.PropertyDTO;
import org.wso2.carbon.identity.user.store.configuration.stub.dto.UserStoreDTO;
import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.deployer.iam.IdentityServerDeployer;
import org.wso2.carbon.solution.endpoint.iam.IdentityServerAdminClient;
import org.wso2.carbon.solution.installer.impl.IdentityServerInstaller;
import org.wso2.carbon.solution.model.config.datasource.Datasource;
import org.wso2.carbon.solution.model.config.server.Server;
import org.wso2.carbon.solution.model.config.solution.SolutionConfig;
import org.wso2.carbon.solution.model.iam.sp.ServiceProviderEntity;
import org.wso2.carbon.solution.model.iam.userstore.UserStore;
import org.wso2.carbon.solution.model.iam.userstore.UserStoreConfigEntity;
import org.wso2.carbon.solution.util.Constant;
import org.wso2.carbon.solution.util.ResourceLoader;
import org.wso2.carbon.solution.util.Utility;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class UserStoreDeployer extends IdentityServerDeployer {
    private static final String ARTIFACT_TYPE_USER_STORES = "user-stores";

    @Override
    public boolean canHandle(String artifactType) {
        if (ARTIFACT_TYPE_USER_STORES.equals(artifactType)) {
            return true;
        }
        return false;
    }

    @Override
    protected void doDeploy(IdentityServerInstaller.IdentityServerArtifact identityServerArtifact, Server server)
            throws CarbonSolutionException {
        Path resourcesPathObj = Paths
                .get(Utility.getSolutionHome(), identityServerArtifact.getPath());

        UserStoreConfigEntity userStoreConfigEntity = ResourceLoader.loadResource(resourcesPathObj,
                                                                                  UserStoreConfigEntity.class);
        if(userStoreConfigEntity != null && userStoreConfigEntity.getUserStore() != null) {
            UserStore userStore = userStoreConfigEntity.getUserStore();
            if(StringUtils.isNotEmpty(userStore.getDataSourceName())){
                JDBCUserStoreDeployer jdbcUserStoreDeployer = new JDBCUserStoreDeployer();
                jdbcUserStoreDeployer.deploy(identityServerArtifact, userStore, server);
            }
        }


    }
    public static class JDBCUserStoreDeployer{

        public void deploy(IdentityServerInstaller.IdentityServerArtifact identityServerArtifact, UserStore userStore_source,
                           Server server) throws CarbonSolutionException {
            UserStoreConfigAdminServiceStub userStoreConfigAdminService = IdentityServerAdminClient
                    .getUserStoreConfigAdminService(server);
            List<Datasource> dataSourceConfig = ResourceLoader
                    .getDataSourceConfig(identityServerArtifact.getSolution());
            Datasource datasource = null ;
            for (Datasource ds : dataSourceConfig) {
                if(ds.getName().equals(userStore_source.getDataSourceName())){
                    datasource = ds ;
                    break;
                }
            }
            if( datasource == null ){
                throw new CarbonSolutionException("No datasource found for given name, " + userStore_source
                        .getDataSourceName());
            }
            UserStoreDTO userStore_dest = new UserStoreDTO();
            userStore_dest.setDomainId(userStore_source.getUserStoreDomain());
            userStore_dest.setClassName("org.wso2.carbon.user.core.jdbc.JDBCUserStoreManager");

            List<PropertyDTO> propertyDTOList_dest = new ArrayList<>();
            updateSensibleDefault(propertyDTOList_dest);
            Properties properties_source = userStore_source.getProperties();
            for (Map.Entry<Object, Object> entry : properties_source.entrySet()) {
                String key = (String)entry.getKey();
                String value = (String)entry.getValue();
                PropertyDTO propertyDTO_dest = new PropertyDTO();
                propertyDTO_dest.setName(key);
                propertyDTO_dest.setValue(value);
                propertyDTOList_dest.add(propertyDTO_dest);
            }

            Properties ds_properties_source = datasource.getProperties();
            updatePropertyDTO("url", (String)ds_properties_source.get("url"), propertyDTOList_dest);
            updatePropertyDTO("userName", (String)ds_properties_source.get("username"), propertyDTOList_dest);
            updatePropertyDTO("password", (String)ds_properties_source.get("password"), propertyDTOList_dest);
            updatePropertyDTO("driverName", (String)ds_properties_source.get("driverClassName"), propertyDTOList_dest);

            userStore_dest.setProperties(propertyDTOList_dest.toArray(new PropertyDTO[propertyDTOList_dest.size()]));
            try {
                userStoreConfigAdminService.addUserStore(userStore_dest);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (UserStoreConfigAdminServiceIdentityUserStoreMgtException e) {
                e.printStackTrace();
            }
        }

        private void updatePropertyDTO(String key, String value, List<PropertyDTO> propertyDTOList){
            if(StringUtils.isNotEmpty(value)) {
                boolean isUpdated = false;
                for (PropertyDTO propertyDTO : propertyDTOList) {
                    if (propertyDTO.getName().equals(key)) {
                        propertyDTO.setName(value);
                        break;
                    }
                }
                if (!isUpdated) {
                    PropertyDTO propertyDTO = new PropertyDTO();
                    propertyDTO.setName(key);
                    propertyDTO.setValue(value);
                    propertyDTOList.add(propertyDTO);
                }
            }
        }

        private void updateSensibleDefault(List<PropertyDTO> propertyDTOList){
            //propertyDTOList.add(buildPropertyDTO("driverName", "com.mysql.jdbc.Driver"));
            propertyDTOList.add(buildPropertyDTO("Disabled", "false"));
            propertyDTOList.add(buildPropertyDTO("ReadOnly", "false"));
            propertyDTOList.add(buildPropertyDTO("ReadGroups", "true"));
            propertyDTOList.add(buildPropertyDTO("WriteGroups", "true"));
            propertyDTOList.add(buildPropertyDTO("UsernameJavaRegEx", "^[\\S]{5,30}$"));
            propertyDTOList.add(buildPropertyDTO("UsernameJavaScriptRegEx", "^[\\S]{5,30}$"));
            propertyDTOList.add(buildPropertyDTO("UsernameJavaRegExViolationErrorMsg", "Username pattern policy violated."));
            propertyDTOList.add(buildPropertyDTO("PasswordJavaRegEx", "^[\\S]{5,30}$"));
            propertyDTOList.add(buildPropertyDTO("PasswordJavaScriptRegEx", "^[\\S]{5,30}$"));
            propertyDTOList.add(buildPropertyDTO("PasswordJavaRegExViolationErrorMsg", "Password pattern policy violated."));
            propertyDTOList.add(buildPropertyDTO("RolenameJavaRegEx", "^[\\S]{5,30}$"));
            propertyDTOList.add(buildPropertyDTO("RolenameJavaScriptRegEx", "^[\\S]{5,30}$"));
            propertyDTOList.add(buildPropertyDTO("CaseInsensitiveUsername", "true"));
            propertyDTOList.add(buildPropertyDTO("SCIMEnabled", "false"));
            propertyDTOList.add(buildPropertyDTO("IsBulkImportSupported", "false"));
            propertyDTOList.add(buildPropertyDTO("PasswordDigest", "SHA-256"));
            propertyDTOList.add(buildPropertyDTO("MultiAttributeSeparator", ","));
            propertyDTOList.add(buildPropertyDTO("StoreSaltedPassword", "true"));
            propertyDTOList.add(buildPropertyDTO("MaxUserNameListLength", "100"));
            propertyDTOList.add(buildPropertyDTO("MaxRoleNameListLength", "100"));
            propertyDTOList.add(buildPropertyDTO("UserRolesCacheEnabled", "true"));
            propertyDTOList.add(buildPropertyDTO("UserNameUniqueAcrossTenants", "false"));
            propertyDTOList.add(buildPropertyDTO("validationQuery",""));
            propertyDTOList.add(buildPropertyDTO("validationInterval",""));
            propertyDTOList.add(buildPropertyDTO("CountRetrieverClass", "org.wso2.carbon.identity.user.store.count.jdbc.JDBCUserStoreCountRetriever"));
            propertyDTOList.add(buildPropertyDTO("SelectUserSQL", "SELECT * FROM UM_USER WHERE UM_USER_NAME=? AND UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("SelectUserSQLCaseInsensitive", "SELECT * FROM UM_USER WHERE LOWER(UM_USER_NAME)=LOWER(?) AND UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("GetRoleListSQL", "SELECT UM_ROLE_NAME, UM_TENANT_ID, UM_SHARED_ROLE FROM UM_ROLE WHERE UM_ROLE_NAME LIKE ? AND UM_TENANT_ID=? AND UM_SHARED_ROLE ='0' ORDER BY UM_ROLE_NAME"));
            propertyDTOList.add(buildPropertyDTO("GetSharedRoleListSQL", "SELECT UM_ROLE_NAME, UM_TENANT_ID, UM_SHARED_ROLE FROM UM_ROLE WHERE UM_ROLE_NAME LIKE ? AND UM_SHARED_ROLE ='1' ORDER BY UM_ROLE_NAME"));
            propertyDTOList.add(buildPropertyDTO("UserFilterSQL", "SELECT UM_USER_NAME FROM UM_USER WHERE UM_USER_NAME LIKE ? AND UM_TENANT_ID=? ORDER BY UM_USER_NAME"));
            propertyDTOList.add(buildPropertyDTO("UserFilterSQLCaseInsensitive", "SELECT UM_USER_NAME FROM UM_USER WHERE LOWER(UM_USER_NAME) LIKE LOWER(?) AND UM_TENANT_ID=? ORDER BY UM_USER_NAME"));
            propertyDTOList.add(buildPropertyDTO("UserRoleSQL", "SELECT UM_ROLE_NAME FROM UM_USER_ROLE, UM_ROLE, UM_USER WHERE UM_USER.UM_USER_NAME=? AND UM_USER.UM_ID=UM_USER_ROLE.UM_USER_ID AND UM_ROLE.UM_ID=UM_USER_ROLE.UM_ROLE_ID AND UM_USER_ROLE.UM_TENANT_ID=? AND UM_ROLE.UM_TENANT_ID=? AND UM_USER.UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("UserRoleSQLCaseInsensitive", "SELECT UM_ROLE_NAME FROM UM_USER_ROLE, UM_ROLE, UM_USER WHERE LOWER(UM_USER.UM_USER_NAME)=LOWER(?) AND UM_USER.UM_ID=UM_USER_ROLE.UM_USER_ID AND UM_ROLE.UM_ID=UM_USER_ROLE.UM_ROLE_ID AND UM_USER_ROLE.UM_TENANT_ID=? AND UM_ROLE.UM_TENANT_ID=? AND UM_USER.UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("UserSharedRoleSQL", "SELECT UM_ROLE_NAME, UM_ROLE.UM_TENANT_ID, UM_SHARED_ROLE FROM UM_SHARED_USER_ROLE INNER JOIN UM_USER ON UM_SHARED_USER_ROLE.UM_USER_ID = UM_USER.UM_ID INNER JOIN UM_ROLE ON UM_SHARED_USER_ROLE.UM_ROLE_ID = UM_ROLE.UM_ID WHERE UM_USER.UM_USER_NAME = ? AND UM_SHARED_USER_ROLE.UM_USER_TENANT_ID = UM_USER.UM_TENANT_ID AND UM_SHARED_USER_ROLE.UM_ROLE_TENANT_ID = UM_ROLE.UM_TENANT_ID AND UM_SHARED_USER_ROLE.UM_USER_TENANT_ID = ? "));
            propertyDTOList.add(buildPropertyDTO("UserSharedRoleSQLCaseInsensitive", "SELECT UM_ROLE_NAME, UM_ROLE.UM_TENANT_ID, UM_SHARED_ROLE FROM UM_SHARED_USER_ROLE INNER JOIN UM_USER ON UM_SHARED_USER_ROLE.UM_USER_ID = UM_USER.UM_ID INNER JOIN UM_ROLE ON UM_SHARED_USER_ROLE.UM_ROLE_ID = UM_ROLE.UM_ID WHERE LOWER(UM_USER.UM_USER_NAME) = LOWER(?) AND UM_SHARED_USER_ROLE.UM_USER_TENANT_ID = UM_USER.UM_TENANT_ID AND UM_SHARED_USER_ROLE.UM_ROLE_TENANT_ID = UM_ROLE.UM_TENANT_ID AND UM_SHARED_USER_ROLE.UM_USER_TENANT_ID = ? "));
            propertyDTOList.add(buildPropertyDTO("IsRoleExistingSQL", "SELECT UM_ID FROM UM_ROLE WHERE UM_ROLE_NAME=? AND UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("GetUserListOfRoleSQL", "SELECT UM_USER_NAME FROM UM_USER_ROLE, UM_ROLE, UM_USER WHERE UM_ROLE.UM_ROLE_NAME=? AND UM_USER.UM_ID=UM_USER_ROLE.UM_USER_ID AND UM_ROLE.UM_ID=UM_USER_ROLE.UM_ROLE_ID AND UM_USER_ROLE.UM_TENANT_ID=? AND UM_ROLE.UM_TENANT_ID=? AND UM_USER.UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("GetUserListOfSharedRoleSQL", "SELECT UM_USER_NAME FROM UM_SHARED_USER_ROLE INNER JOIN UM_USER ON UM_SHARED_USER_ROLE.UM_USER_ID = UM_USER.UM_ID INNER JOIN UM_ROLE ON UM_SHARED_USER_ROLE.UM_ROLE_ID = UM_ROLE.UM_ID WHERE UM_ROLE.UM_ROLE_NAME= ? AND UM_SHARED_USER_ROLE.UM_USER_TENANT_ID = UM_USER.UM_TENANT_ID AND UM_SHARED_USER_ROLE.UM_ROLE_TENANT_ID = UM_ROLE.UM_TENANT_ID"));
            propertyDTOList.add(buildPropertyDTO("IsUserExistingSQL", "SELECT UM_ID FROM UM_USER WHERE UM_USER_NAME=? AND UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("IsUserExistingSQLCaseInsensitive", "SELECT UM_ID FROM UM_USER WHERE LOWER(UM_USER_NAME)=LOWER(?) AND UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("GetUserPropertiesForProfileSQL", "SELECT UM_ATTR_NAME, UM_ATTR_VALUE FROM UM_USER_ATTRIBUTE, UM_USER WHERE UM_USER.UM_ID = UM_USER_ATTRIBUTE.UM_USER_ID AND UM_USER.UM_USER_NAME=? AND UM_PROFILE_ID=? AND UM_USER_ATTRIBUTE.UM_TENANT_ID=? AND UM_USER.UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("GetUserPropertiesForProfileSQLCaseInsensitive", "SELECT UM_ATTR_NAME, UM_ATTR_VALUE FROM UM_USER_ATTRIBUTE, UM_USER WHERE UM_USER.UM_ID = UM_USER_ATTRIBUTE.UM_USER_ID AND LOWER(UM_USER.UM_USER_NAME)=LOWER(?) AND UM_PROFILE_ID=? AND UM_USER_ATTRIBUTE.UM_TENANT_ID=? AND UM_USER.UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("GetUserPropertyForProfileSQL", "SELECT UM_ATTR_VALUE FROM UM_USER_ATTRIBUTE, UM_USER WHERE UM_USER.UM_ID = UM_USER_ATTRIBUTE.UM_USER_ID AND UM_USER.UM_USER_NAME=? AND UM_ATTR_NAME=? AND UM_PROFILE_ID=? AND UM_USER_ATTRIBUTE.UM_TENANT_ID=? AND UM_USER.UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("GetUserPropertyForProfileSQLCaseInsensitive", "SELECT UM_ATTR_VALUE FROM UM_USER_ATTRIBUTE, UM_USER WHERE UM_USER.UM_ID = UM_USER_ATTRIBUTE.UM_USER_ID AND LOWER(UM_USER.UM_USER_NAME)=LOWER(?) AND UM_ATTR_NAME=? AND UM_PROFILE_ID=? AND UM_USER_ATTRIBUTE.UM_TENANT_ID=? AND UM_USER.UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("GetUserLisForPropertySQL", "SELECT UM_USER_NAME FROM UM_USER, UM_USER_ATTRIBUTE WHERE UM_USER_ATTRIBUTE.UM_USER_ID = UM_USER.UM_ID AND UM_USER_ATTRIBUTE.UM_ATTR_NAME =? AND UM_USER_ATTRIBUTE.UM_ATTR_VALUE LIKE ? AND UM_USER_ATTRIBUTE.UM_PROFILE_ID=? AND UM_USER_ATTRIBUTE.UM_TENANT_ID=? AND UM_USER.UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("GetProfileNamesSQL", "SELECT DISTINCT UM_PROFILE_ID FROM UM_USER_ATTRIBUTE WHERE UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("GetUserProfileNamesSQL", "SELECT DISTINCT UM_PROFILE_ID FROM UM_USER_ATTRIBUTE WHERE UM_USER_ID=(SELECT UM_ID FROM UM_USER WHERE UM_USER_NAME=? AND UM_TENANT_ID=?) AND UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("GetUserProfileNamesSQLCaseInsensitive", "SELECT DISTINCT UM_PROFILE_ID FROM UM_USER_ATTRIBUTE WHERE UM_USER_ID=(SELECT UM_ID FROM UM_USER WHERE LOWER(UM_USER_NAME)=LOWER(?) AND UM_TENANT_ID=?) AND UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("GetUserIDFromUserNameSQL", "SELECT UM_ID FROM UM_USER WHERE UM_USER_NAME=? AND UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("GetUserIDFromUserNameSQLCaseInsensitive", "SELECT UM_ID FROM UM_USER WHERE LOWER(UM_USER_NAME)=LOWER(?) AND UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("GetUserNameFromTenantIDSQL", "SELECT UM_USER_NAME FROM UM_USER WHERE UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("GetTenantIDFromUserNameSQL", "SELECT UM_TENANT_ID FROM UM_USER WHERE UM_USER_NAME=?"));
            propertyDTOList.add(buildPropertyDTO("GetTenantIDFromUserNameSQLCaseInsensitive", "SELECT UM_TENANT_ID FROM UM_USER WHERE LOWER(UM_USER_NAME)=LOWER(?)"));
            propertyDTOList.add(buildPropertyDTO("AddUserSQL", "INSERT INTO UM_USER (UM_USER_NAME, UM_USER_PASSWORD, UM_SALT_VALUE, UM_REQUIRE_CHANGE, UM_CHANGED_TIME, UM_TENANT_ID) VALUES (?, ?, ?, ?, ?, ?)"));
            propertyDTOList.add(buildPropertyDTO("AddUserToRoleSQL", "INSERT INTO UM_USER_ROLE (UM_USER_ID, UM_ROLE_ID, UM_TENANT_ID) VALUES ((SELECT UM_ID FROM UM_USER WHERE UM_USER_NAME=? AND UM_TENANT_ID=?),(SELECT UM_ID FROM UM_ROLE WHERE UM_ROLE_NAME=? AND UM_TENANT_ID=?), ?)"));
            propertyDTOList.add(buildPropertyDTO("AddUserToRoleSQLCaseInsensitive", "INSERT INTO UM_USER_ROLE (UM_USER_ID, UM_ROLE_ID, UM_TENANT_ID) VALUES ((SELECT UM_ID FROM UM_USER WHERE LOWER(UM_USER_NAME)=LOWER(?) AND UM_TENANT_ID=?),(SELECT UM_ID FROM UM_ROLE WHERE UM_ROLE_NAME=? AND UM_TENANT_ID=?), ?)"));
            propertyDTOList.add(buildPropertyDTO("AddRoleSQL", "INSERT INTO UM_ROLE (UM_ROLE_NAME, UM_TENANT_ID) VALUES (?, ?)"));
            propertyDTOList.add(buildPropertyDTO("AddSharedRoleSQL", "UPDATE UM_ROLE SET UM_SHARED_ROLE = ? WHERE UM_ROLE_NAME = ? AND UM_TENANT_ID = ?"));
            propertyDTOList.add(buildPropertyDTO("AddRoleToUserSQL", "INSERT INTO UM_USER_ROLE (UM_ROLE_ID, UM_USER_ID, UM_TENANT_ID) VALUES ((SELECT UM_ID FROM UM_ROLE WHERE UM_ROLE_NAME=? AND UM_TENANT_ID=?),(SELECT UM_ID FROM UM_USER WHERE UM_USER_NAME=? AND UM_TENANT_ID=?), ?)"));
            propertyDTOList.add(buildPropertyDTO("AddSharedRoleToUserSQL", "INSERT INTO UM_SHARED_USER_ROLE (UM_ROLE_ID, UM_USER_ID, UM_USER_TENANT_ID, UM_ROLE_TENANT_ID) VALUES ((SELECT UM_ID FROM UM_ROLE WHERE UM_ROLE_NAME=? AND UM_TENANT_ID=?),(SELECT UM_ID FROM UM_USER WHERE UM_USER_NAME=? AND UM_TENANT_ID=?), ?, ?)"));
            propertyDTOList.add(buildPropertyDTO("AddSharedRoleToUserSQLCaseInsensitive", "INSERT INTO UM_SHARED_USER_ROLE (UM_ROLE_ID, UM_USER_ID, UM_USER_TENANT_ID, UM_ROLE_TENANT_ID) VALUES ((SELECT UM_ID FROM UM_ROLE WHERE UM_ROLE_NAME=? AND UM_TENANT_ID=?), (SELECT UM_ID FROM UM_USER WHERE LOWER(UM_USER_NAME)=LOWER(?) AND UM_TENANT_ID=?), ?, ?)"));
            propertyDTOList.add(buildPropertyDTO("RemoveUserFromSharedRoleSQL", "DELETE FROM UM_SHARED_USER_ROLE WHERE   UM_ROLE_ID=(SELECT UM_ID FROM UM_ROLE WHERE UM_ROLE_NAME=? AND UM_TENANT_ID=?) AND UM_USER_ID=(SELECT UM_ID FROM UM_USER WHERE UM_USER_NAME=? AND UM_TENANT_ID=?) AND UM_USER_TENANT_ID=? AND UM_ROLE_TENANT_ID = ?"));
            propertyDTOList.add(buildPropertyDTO("RemoveUserFromRoleSQLCaseInsensitive", "DELETE FROM UM_USER_ROLE WHERE UM_USER_ID=(SELECT UM_ID FROM UM_USER WHERE LOWER(UM_USER_NAME)=LOWER(?) AND UM_TENANT_ID=?) AND UM_ROLE_ID=(SELECT UM_ID FROM UM_ROLE WHERE UM_ROLE_NAME=? AND UM_TENANT_ID=?) AND UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("RemoveUserFromRoleSQL", "DELETE FROM UM_USER_ROLE WHERE UM_USER_ID=(SELECT UM_ID FROM UM_USER WHERE UM_USER_NAME=? AND UM_TENANT_ID=?) AND UM_ROLE_ID=(SELECT UM_ID FROM UM_ROLE WHERE UM_ROLE_NAME=? AND UM_TENANT_ID=?) AND UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("RemoveUserFromRoleSQLCaseInsensitive", "DELETE FROM UM_USER_ROLE WHERE UM_USER_ID=(SELECT UM_ID FROM UM_USER WHERE LOWER(UM_USER_NAME)=LOWER(?) AND UM_TENANT_ID=?) AND UM_ROLE_ID=(SELECT UM_ID FROM UM_ROLE WHERE UM_ROLE_NAME=? AND UM_TENANT_ID=?) AND UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("RemoveRoleFromUserSQL", "DELETE FROM UM_USER_ROLE WHERE UM_ROLE_ID=(SELECT UM_ID FROM UM_ROLE WHERE UM_ROLE_NAME=? AND UM_TENANT_ID=?) AND UM_USER_ID=(SELECT UM_ID FROM UM_USER WHERE UM_USER_NAME=? AND UM_TENANT_ID=?) AND UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("RemoveRoleFromUserSQLCaseInsensitive", "DELETE FROM UM_USER_ROLE WHERE UM_ROLE_ID=(SELECT UM_ID FROM UM_ROLE WHERE UM_ROLE_NAME=? AND UM_TENANT_ID=?) AND UM_USER_ID=(SELECT UM_ID FROM UM_USER WHERE LOWER(UM_USER_NAME)=LOWER(?) AND UM_TENANT_ID=?) AND UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("DeleteRoleSQL", "DELETE FROM UM_ROLE WHERE UM_ROLE_NAME = ? AND UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("OnDeleteRoleRemoveUserRoleMappingSQL", "DELETE FROM UM_USER_ROLE WHERE UM_ROLE_ID=(SELECT UM_ID FROM UM_ROLE WHERE UM_ROLE_NAME=? AND UM_TENANT_ID=?) AND UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("DeleteUserSQL", "DELETE FROM UM_USER WHERE UM_USER_NAME = ? AND UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("DeleteUserSQLCaseInsensitive", "DELETE FROM UM_USER WHERE LOWER(UM_USER_NAME) = LOWER(?) AND UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("OnDeleteUserRemoveUserRoleMappingSQL", "DELETE FROM UM_USER_ROLE WHERE UM_USER_ID=(SELECT UM_ID FROM UM_USER WHERE UM_USER_NAME=? AND UM_TENANT_ID=?) AND UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("OnDeleteUserRemoveUserAttributeSQL", "DELETE FROM UM_USER_ATTRIBUTE WHERE UM_USER_ID=(SELECT UM_ID FROM UM_USER WHERE UM_USER_NAME=? AND UM_TENANT_ID=?) AND UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("OnDeleteUserRemoveUserAttributeSQLCaseInsensitive", "DELETE FROM UM_USER_ATTRIBUTE WHERE UM_USER_ID=(SELECT UM_ID FROM UM_USER WHERE LOWER(UM_USER_NAME)=LOWER(?) AND UM_TENANT_ID=?) AND UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("UpdateUserPasswordSQL", "UPDATE UM_USER SET UM_USER_PASSWORD= ?, UM_SALT_VALUE=?, UM_REQUIRE_CHANGE=?, UM_CHANGED_TIME=? WHERE UM_USER_NAME= ? AND UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("UpdateUserPasswordSQLCaseInsensitive", "UPDATE UM_USER SET UM_USER_PASSWORD= ?, UM_SALT_VALUE=?, UM_REQUIRE_CHANGE=?, UM_CHANGED_TIME=? WHERE LOWER(UM_USER_NAME)= LOWER(?) AND UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("UpdateRoleNameSQL", "UPDATE UM_ROLE set UM_ROLE_NAME=? WHERE UM_ROLE_NAME = ? AND UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("AddUserPropertySQL", "INSERT INTO UM_USER_ATTRIBUTE (UM_USER_ID, UM_ATTR_NAME, UM_ATTR_VALUE, UM_PROFILE_ID, UM_TENANT_ID) VALUES ((SELECT UM_ID FROM UM_USER WHERE UM_USER_NAME=? AND UM_TENANT_ID=?), ?, ?, ?, ?)"));
            propertyDTOList.add(buildPropertyDTO("UpdateUserPropertySQL", "UPDATE UM_USER_ATTRIBUTE SET UM_ATTR_VALUE=? WHERE UM_USER_ID=(SELECT UM_ID FROM UM_USER WHERE UM_USER_NAME=? AND UM_TENANT_ID=?) AND UM_ATTR_NAME=? AND UM_PROFILE_ID=? AND UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("UpdateUserPropertySQLCaseInsensitive", "UPDATE UM_USER_ATTRIBUTE SET UM_ATTR_VALUE=? WHERE UM_USER_ID=(SELECT UM_ID FROM UM_USER WHERE LOWER(UM_USER_NAME)=LOWER(?) AND UM_TENANT_ID=?) AND UM_ATTR_NAME=? AND UM_PROFILE_ID=? AND UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("DeleteUserPropertySQL", "UPDATE UM_USER_ATTRIBUTE SET UM_ATTR_VALUE=? WHERE UM_USER_ID=(SELECT UM_ID FROM UM_USER WHERE UM_USER_NAME=? AND UM_TENANT_ID=?) AND UM_ATTR_NAME=? AND UM_PROFILE_ID=? AND UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("DeleteUserPropertySQLCaseInsensitive", "DELETE FROM UM_USER_ATTRIBUTE WHERE UM_USER_ID=(SELECT UM_ID FROM UM_USER WHERE LOWER(UM_USER_NAME)=LOWER(?) AND UM_TENANT_ID=?) AND UM_ATTR_NAME=? AND UM_PROFILE_ID=? AND UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("UserNameUniqueAcrossTenantsSQL", "SELECT UM_ID FROM UM_USER WHERE UM_USER_NAME=?"));
            propertyDTOList.add(buildPropertyDTO("UserNameUniqueAcrossTenantsSQLCaseInsensitive", "SELECT UM_ID FROM UM_USER WHERE LOWER(UM_USER_NAME)=LOWER(?)"));
            propertyDTOList.add(buildPropertyDTO("IsDomainExistingSQL", "SELECT UM_DOMAIN_ID FROM UM_DOMAIN WHERE UM_DOMAIN_NAME=? AND UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("AddDomainSQL", "INSERT INTO UM_DOMAIN (UM_DOMAIN_NAME, UM_TENANT_ID) VALUES (?, ?)"));
            propertyDTOList.add(buildPropertyDTO("AddUserToRoleSQL-mssql", "INSERT INTO UM_USER_ROLE (UM_USER_ID, UM_ROLE_ID, UM_TENANT_ID) SELECT (SELECT UM_ID FROM UM_USER WHERE UM_USER_NAME=? AND UM_TENANT_ID=?),(SELECT UM_ID FROM UM_ROLE WHERE UM_ROLE_NAME=? AND UM_TENANT_ID=?),(?)"));
            propertyDTOList.add(buildPropertyDTO("AddRoleToUserSQL-mssql", "INSERT INTO UM_USER_ROLE (UM_ROLE_ID, UM_USER_ID, UM_TENANT_ID) SELECT (SELECT UM_ID FROM UM_ROLE WHERE UM_ROLE_NAME=? AND UM_TENANT_ID=?),(SELECT UM_ID FROM UM_USER WHERE UM_USER_NAME=? AND UM_TENANT_ID=?), (?)"));
            propertyDTOList.add(buildPropertyDTO("AddUserPropertySQL-mssql", "INSERT INTO UM_USER_ATTRIBUTE (UM_USER_ID, UM_ATTR_NAME, UM_ATTR_VALUE, UM_PROFILE_ID, UM_TENANT_ID) SELECT (SELECT UM_ID FROM UM_USER WHERE UM_USER_NAME=? AND UM_TENANT_ID=?), (?), (?), (?), (?)"));
            propertyDTOList.add(buildPropertyDTO("AddUserToRoleSQLCaseInsensitive-mssql", "INSERT INTO UM_USER_ROLE (UM_USER_ID, UM_ROLE_ID, UM_TENANT_ID) SELECT (SELECT UM_ID FROM UM_USER WHERE LOWER(UM_USER_NAME)=LOWER(?) AND UM_TENANT_ID=?),(SELECT UM_ID FROM UM_ROLE WHERE UM_ROLE_NAME=? AND UM_TENANT_ID=?),(?)"));
            propertyDTOList.add(buildPropertyDTO("AddRoleToUserSQLCaseInsensitive-mssql", "INSERT INTO UM_USER_ROLE (UM_ROLE_ID, UM_USER_ID, UM_TENANT_ID) SELECT (SELECT UM_ID FROM UM_ROLE WHERE UM_ROLE_NAME=? AND UM_TENANT_ID=?),(SELECT UM_ID FROM UM_USER WHERE LOWER(UM_USER_NAME)=LOWER(?) AND UM_TENANT_ID=?), (?)"));
            propertyDTOList.add(buildPropertyDTO("AddUserPropertySQLCaseInsensitive-mssql", "INSERT INTO UM_USER_ATTRIBUTE (UM_USER_ID, UM_ATTR_NAME, UM_ATTR_VALUE, UM_PROFILE_ID, UM_TENANT_ID) SELECT (SELECT UM_ID FROM UM_USER WHERE LOWER(UM_USER_NAME)=LOWER(?) AND UM_TENANT_ID=?), (?), (?), (?), (?)"));
            propertyDTOList.add(buildPropertyDTO("AddUserToRoleSQL-openedge", "INSERT INTO UM_USER_ROLE (UM_USER_ID, UM_ROLE_ID, UM_TENANT_ID) SELECT UU.UM_ID, UR.UM_ID, ? FROM UM_USER UU, UM_ROLE UR WHERE UU.UM_USER_NAME=? AND UU.UM_TENANT_ID=? AND UR.UM_ROLE_NAME=? AND UR.UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("AddRoleToUserSQL-openedge", "INSERT INTO UM_USER_ROLE (UM_ROLE_ID, UM_USER_ID, UM_TENANT_ID) SELECT UR.UM_ID, UU.UM_ID, ? FROM UM_ROLE UR, UM_USER UU WHERE UR.UM_ROLE_NAME=? AND UR.UM_TENANT_ID=? AND UU.UM_USER_NAME=? AND UU.UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("AddUserPropertySQL-openedge", "INSERT INTO UM_USER_ATTRIBUTE (UM_USER_ID, UM_ATTR_NAME, UM_ATTR_VALUE, UM_PROFILE_ID, UM_TENANT_ID) SELECT UM_ID, ?, ?, ?, ? FROM UM_USER WHERE UM_USER_NAME=? AND UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("AddUserToRoleSQLCaseInsensitive-openedge", "INSERT INTO UM_USER_ROLE (UM_USER_ID, UM_ROLE_ID, UM_TENANT_ID) SELECT UU.UM_ID, UR.UM_ID, ? FROM UM_USER UU, UM_ROLE UR WHERE LOWER(UU.UM_USER_NAME)=LOWER(?) AND UU.UM_TENANT_ID=? AND UR.UM_ROLE_NAME=? AND UR.UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("AddRoleToUserSQLCaseInsensitive-openedge", "INSERT INTO UM_USER_ROLE (UM_ROLE_ID, UM_USER_ID, UM_TENANT_ID) SELECT UR.UM_ID, UU.UM_ID, ? FROM UM_ROLE UR, UM_USER UU WHERE UR.UM_ROLE_NAME=? AND UR.UM_TENANT_ID=? AND LOWER(UU.UM_USER_NAME)=LOWER(?) AND UU.UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("AddUserPropertySQLCaseInsensitive-openedge", "INSERT INTO UM_USER_ATTRIBUTE (UM_USER_ID, UM_ATTR_NAME, UM_ATTR_VALUE, UM_PROFILE_ID, UM_TENANT_ID) SELECT UM_ID, ?, ?, ?, ? FROM UM_USER WHERE LOWER(UM_USER_NAME)=LOWER(?) AND UM_TENANT_ID=?"));
            propertyDTOList.add(buildPropertyDTO("ClaimOperationsSupported", "true"));
            //propertyDTOList.add(buildPropertyDTO("DomainName", "ddd");
            //propertyDTOList.add(buildPropertyDTO("Description"/>
        }
        private PropertyDTO buildPropertyDTO(String key, String value){
            PropertyDTO propertyDTO_dest = new PropertyDTO();
            propertyDTO_dest.setName(key);
            propertyDTO_dest.setValue(value);
            return propertyDTO_dest;

        }
    }

}
