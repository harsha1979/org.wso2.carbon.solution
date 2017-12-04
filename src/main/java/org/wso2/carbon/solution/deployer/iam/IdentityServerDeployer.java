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
package org.wso2.carbon.solution.deployer.iam;

import org.apache.commons.lang.StringUtils;
import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.model.server.Server;
import org.wso2.carbon.solution.model.server.iam.IdentityServerArtifact;
import org.wso2.carbon.solution.util.ApplicationUtility;
import org.wso2.carbon.solution.util.ResourceManager;

import java.io.File;
import java.io.IOException;

/**
 * Abstract IdentityServerDeployer.
 */
public abstract class IdentityServerDeployer {

    public boolean canHandle(String artifactType) {
        if (StringUtils.isNotEmpty(getArtifactType()) && getArtifactType().equals(artifactType)) {
            return true;
        }
        return false;
    }

    public void deploy(IdentityServerArtifact identityServerArtifact, Server server)
            throws CarbonSolutionException {

        ResourceManager.loadKeyStores(server.getServerName(), server.getInstance());
        String base = ApplicationUtility.getPropertyFilePath(identityServerArtifact);
        File outputFile = new File(base);
        if (!outputFile.exists()) {
            try {
                outputFile.createNewFile();
            } catch (IOException e) {
                throw new CarbonSolutionException("Error occurred while writing output file, ", e);
            }
        }
        doDeploy(identityServerArtifact, server);
    }

    protected abstract void doDeploy(IdentityServerArtifact identityServerArtifact, Server server)
            throws CarbonSolutionException;

    protected abstract String getArtifactType();
}
