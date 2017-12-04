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

import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.deployer.tomcat.WebAppDeployer;
import org.wso2.carbon.solution.installer.Installer;
import org.wso2.carbon.solution.model.server.Server;
import org.wso2.carbon.solution.model.server.tomcat.TomcatServerArtifact;
import org.wso2.carbon.solution.util.ResourceManager;

public class TomcatInstaller extends Installer {

    @Override
    public void install(String path) throws CarbonSolutionException {
        WebAppDeployer webAppDeployer = new WebAppDeployer();
        TomcatServerArtifact tomcatServerArtifact = new TomcatServerArtifact(path);
        Server serverConfig = ResourceManager
                .getServerConfig(getInstallerName(), tomcatServerArtifact.getInstanceName());
        webAppDeployer.deploy(tomcatServerArtifact, serverConfig);
    }

    @Override
    protected String getInstallerName() {
        return "tomcat";
    }
}
