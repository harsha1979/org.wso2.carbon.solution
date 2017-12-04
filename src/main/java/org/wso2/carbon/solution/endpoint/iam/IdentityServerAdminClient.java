/*
 *  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.wso2.carbon.solution.endpoint.iam;


import org.apache.axis2.client.ServiceClient;
import org.wso2.carbon.identity.application.mgt.stub.IdentityApplicationManagementServiceStub;
import org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceStub;
import org.wso2.carbon.identity.sso.saml.stub.IdentitySAMLSSOConfigServiceStub;
import org.wso2.carbon.identity.user.store.configuration.stub.UserStoreConfigAdminServiceStub;
import org.wso2.carbon.idp.mgt.stub.IdentityProviderMgtServiceStub;
import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.endpoint.iam.config.IdentityServer;
import org.wso2.carbon.solution.model.server.Server;
import org.wso2.carbon.solution.util.AuthenticationException;
import org.wso2.carbon.solution.util.ServiceAuthenticator;

public class IdentityServerAdminClient {

    /**
     * Get IdentityApplicationManagementServiceStub ;
     *
     * @param server
     * @return
     * @throws CarbonSolutionException
     */
    public static IdentityApplicationManagementServiceStub getApplicationManagementService(Server server)
            throws CarbonSolutionException {
        IdentityApplicationManagementServiceStub stub = null;
        IdentityServer identityServer = new IdentityServer(server);
        try {
            String serviceURL = identityServer.getHTTPSServerURL() + "/services/IdentityApplicationManagementService";
            stub = new IdentityApplicationManagementServiceStub(serviceURL);
            ServiceClient serviceClient = stub._getServiceClient();
            doServiceAuthenticate(identityServer, serviceClient);
        } catch (Exception e) {
            throw new CarbonSolutionException("Error occurred while getApplicationManagementService.", e);
        }
        return stub;
    }

    /**
     * Get IdentityProviderMgtServiceStub.
     *
     * @param server
     * @return
     * @throws CarbonSolutionException
     */
    public static IdentityProviderMgtServiceStub getIdentityProviderMgtService(Server server)
            throws CarbonSolutionException {
        IdentityProviderMgtServiceStub stub = null;
        IdentityServer identityServer = new IdentityServer(server);
        try {
            String serviceURL = identityServer.getHTTPSServerURL() + "/services/IdentityProviderMgtService";
            stub = new IdentityProviderMgtServiceStub(serviceURL);
            doServiceAuthenticate(identityServer, stub._getServiceClient());
        } catch (Exception e) {
            throw new CarbonSolutionException("Error occurred while getApplicationManagementService.", e);
        }
        return stub;
    }

    public static OAuthAdminServiceStub getOAuthAdminService(Server server) throws CarbonSolutionException {
        OAuthAdminServiceStub stub = null;
        IdentityServer identityServer = new IdentityServer(server);
        try {
            String serviceURL = identityServer.getHTTPSServerURL() + "/services/OAuthAdminService";
            stub = new OAuthAdminServiceStub(serviceURL);
            doServiceAuthenticate(identityServer, stub._getServiceClient());
        } catch (Exception e) {
            throw new CarbonSolutionException("Error occurred while getApplicationManagementService.", e);
        }
        return stub;
    }

    /**
     * @param server
     * @return
     * @throws CarbonSolutionException
     */
    public static IdentitySAMLSSOConfigServiceStub getSAMLSSOConfigService(Server server)
            throws CarbonSolutionException {
        IdentitySAMLSSOConfigServiceStub stub = null;
        IdentityServer identityServer = new IdentityServer(server);
        try {
            String serviceURL = identityServer.getHTTPSServerURL() + "/services/IdentitySAMLSSOConfigService";
            stub = new IdentitySAMLSSOConfigServiceStub(serviceURL);
            doServiceAuthenticate(identityServer, stub._getServiceClient());
        } catch (Exception e) {
            throw new CarbonSolutionException("Error occurred while getApplicationManagementService.", e);
        }
        return stub;
    }

    public static UserStoreConfigAdminServiceStub getUserStoreConfigAdminService(Server server)
            throws CarbonSolutionException {
        UserStoreConfigAdminServiceStub stub = null;
        IdentityServer identityServer = new IdentityServer(server);
        try {
            String serviceURL = identityServer.getHTTPSServerURL() + "/services/UserStoreConfigAdminService";
            stub = new UserStoreConfigAdminServiceStub(serviceURL);
            doServiceAuthenticate(identityServer, stub._getServiceClient());
        } catch (Exception e) {
            throw new CarbonSolutionException("Error occurred while getApplicationManagementService.", e);
        }
        return stub;
    }

    private static void doServiceAuthenticate(IdentityServer identityServer, ServiceClient serviceClient)
            throws AuthenticationException {
        ServiceAuthenticator authenticator = ServiceAuthenticator.getInstance();
        authenticator.setAccessPassword(identityServer.getUserName());
        authenticator.setAccessUsername(identityServer.getPassword());
        authenticator.authenticate(serviceClient);
    }
}
