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
package org.wso2.carbon.solution.installer.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.deployer.iam.IdentityServerDeployer;
import org.wso2.carbon.solution.deployer.iam.IdentityServerDeployerFactory;
import org.wso2.carbon.solution.installer.Installer;
import org.wso2.carbon.solution.model.server.Server;
import org.wso2.carbon.solution.model.server.iam.IdentityServerArtifact;
import org.wso2.carbon.solution.util.Constant;
import org.wso2.carbon.solution.util.ResourceManager;

import java.util.List;

/**
 * IdentityServerInstaller is an installer that is implemented to target to WSO2 Identity Server artifacts.
 */
public class IdentityServerInstaller extends Installer {
    private static Log log = LogFactory.getLog(IdentityServerInstaller.class);

    @Override
    public void install(String resourcePath) throws CarbonSolutionException {
        List<IdentityServerArtifact> identityServerArtifactList = IdentityServerArtifact.load(resourcePath);
        for (IdentityServerArtifact identityServerArtifact : identityServerArtifactList) {
            log.info("Installing artifact in IdentityServer under, " + identityServerArtifact.getResourcePath());
            if (identityServerArtifact.getTenantDomain().equals(Constant.Tenant.CARBON_SUPER)) {
                IdentityServerDeployer deployer = IdentityServerDeployerFactory.getInstance()
                        .getDeployer(identityServerArtifact.getArtifactType());
                Server serverConfig = ResourceManager
                        .getServerConfig(getInstallerName(), identityServerArtifact.getInstanceName());
                deployer.deploy(identityServerArtifact, serverConfig);
            } else {
                log.warn("Tenant implementation is not done yet, " + resourcePath);
            }
        }
    }

    @Override
    protected String getInstallerName() {
        return "identity-server";
    }
}
