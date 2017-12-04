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


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.idp.mgt.stub.IdentityProviderMgtServiceStub;
import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.builder.iam.IdentityProviderBuilder;
import org.wso2.carbon.solution.deployer.iam.IdentityServerDeployer;
import org.wso2.carbon.solution.endpoint.iam.IdentityServerAdminClient;
import org.wso2.carbon.solution.model.iam.idp.IdentityProvider;
import org.wso2.carbon.solution.model.iam.idp.IdentityProviderEntity;
import org.wso2.carbon.solution.model.server.Server;
import org.wso2.carbon.solution.model.server.iam.IdentityServerArtifact;
import org.wso2.carbon.solution.util.Constant;
import org.wso2.carbon.solution.util.ResourceManager;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * IdentityProviderDeployer is deploying identity server artifacts.
 */
public class IdentityProviderDeployer extends IdentityServerDeployer {
    private Log log = LogFactory.getLog(IdentityProviderDeployer.class);

    @Override
    public void doDeploy(IdentityServerArtifact identityServerArtifact, Server server) throws
                                                                                       CarbonSolutionException {
        Path resourcesPathObj = Paths
                .get(Constant.ResourcePath.RESOURCE_HOME_PATH, Constant.ResourceFolder.SOLUTION_HOME_FOLDER,
                     identityServerArtifact.getResourcePath());
        IdentityProviderEntity identityProviderEntity_source =
                ResourceManager.loadYAMLResource(resourcesPathObj, IdentityProviderEntity.class);

        try {
            IdentityProvider identityProvider_source = identityProviderEntity_source.getIdentityProvider();
            log.info("Deplying identity provider, " + identityProvider_source.getDisplayName());
            org.wso2.carbon.identity.application.common.model.idp.xsd.IdentityProvider identityProvider_dest
                    = new org.wso2.carbon.identity.application.common.model.idp.xsd.IdentityProvider();
            IdentityProviderBuilder.getInstance().buildIdentityProvider(identityProvider_source, identityProvider_dest);
            IdentityProviderMgtServiceStub identityProviderMgtService = IdentityServerAdminClient
                    .getIdentityProviderMgtService(server);
            org.wso2.carbon.identity.application.common.model.idp.xsd.IdentityProvider idPByName = null;
            try {
                idPByName = identityProviderMgtService.getIdPByName(identityProvider_source.getIdentityProviderName());
            } catch (Exception e) {
                log.error("Error occurred while get the idp, " + identityProvider_source.getIdentityProviderName());
            }
            if (idPByName == null) {
                log.debug("Add new IDP, " + identityProvider_source.getIdentityProviderName());
                identityProviderMgtService.addIdP(identityProvider_dest);
            } else {
                log.debug("Update existing IDP, " + identityProvider_source.getIdentityProviderName());
                identityProviderMgtService
                        .updateIdP(identityProvider_dest.getIdentityProviderName(), identityProvider_dest);
            }
        } catch (Exception e) {
            String error = "Error occurred while adding IDP, " +
                           identityProviderEntity_source.getIdentityProvider().getDisplayName();
            throw new CarbonSolutionException(error, e);
        }
    }

    @Override
    protected String getArtifactType() {
        return Constant.ResourceFolder.IDENTITY_PROVIDERS_FOLDER;
    }
}
