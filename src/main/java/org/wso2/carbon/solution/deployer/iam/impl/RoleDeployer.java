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

package org.wso2.carbon.solution.deployer.iam.impl;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.deployer.iam.IdentityServerDeployer;
import org.wso2.carbon.solution.endpoint.iam.config.IdentityServer;
import org.wso2.carbon.solution.model.server.Server;
import org.wso2.carbon.solution.model.server.iam.IdentityServerArtifact;
import org.wso2.carbon.solution.util.ApplicationUtility;
import org.wso2.carbon.solution.util.Constant;
import org.wso2.carbon.solution.util.ResourceManager;
import org.wso2.charon.core.schema.SCIMConstants;

import java.io.IOException;

/**
 * Role deployer through SCIM.
 */
public class RoleDeployer extends IdentityServerDeployer {
    private Log log = LogFactory.getLog(IdentityProviderDeployer.class);

    @Override
    protected void doDeploy(IdentityServerArtifact identityServerArtifact, Server server)
            throws CarbonSolutionException {
        try {
            String resourceFilePath = ApplicationUtility.getAbsolutePath(Constant.ResourceFolder.SOLUTION_HOME_FOLDER,
                                                                         identityServerArtifact.getResourcePath());
            IdentityServer identityServer = new IdentityServer(server);
            String encodedUser = ResourceManager.getFileContent(resourceFilePath);
            log.debug("JSON request: " + encodedUser);
            PostMethod postMethod = new PostMethod(identityServer.getScimGroupEndpoint());
            //add authorization header
            String authHeader = ApplicationUtility.getBase64EncodedBasicAuthHeader(identityServer.getUserName(),
                                                                                   identityServer.getPassword());
            postMethod.addRequestHeader(SCIMConstants.AUTHORIZATION_HEADER, authHeader);
            //create request entity with the payload.
            RequestEntity requestEntity = new StringRequestEntity(encodedUser, "application/json", null);
            postMethod.setRequestEntity(requestEntity);

            //create http client
            HttpClient httpClient = new HttpClient();
            //send the request
            int responseStatus = httpClient.executeMethod(postMethod);
            String response = postMethod.getResponseBodyAsString();
            log.debug("Status :" + responseStatus + "Response :" + response);
        } catch (IOException e) {
            String errorMsg = "error occurred while executing SCIM client, " + e.getMessage();
            throw new CarbonSolutionException(errorMsg, e);
        }
    }

    @Override
    protected String getArtifactType() {
        return Constant.ResourceFolder.ROLE_PROVISION_FOLDER;
    }
}
