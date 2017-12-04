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
import org.wso2.carbon.solution.deployer.database.DatabaseDeployer;
import org.wso2.carbon.solution.deployer.database.DatabaseDeployerFactory;
import org.wso2.carbon.solution.installer.Installer;
import org.wso2.carbon.solution.model.server.db.DatabaseServerArtifact;
import org.wso2.carbon.solution.util.Constant;

/**
 * Database server installer. The deployer implementation should do the installation based on the type.
 */
public class DatabaseServerInstaller extends Installer {

    @Override
    public void install(String path) throws CarbonSolutionException {
        DatabaseServerArtifact databaseServerArtifact = DatabaseServerArtifact.load(path);
        DatabaseDeployer deployer = DatabaseDeployerFactory.getInstance()
                .getDeployer(databaseServerArtifact.getDatabaseType());
        deployer.deploy(databaseServerArtifact);
    }

    @Override
    protected String getInstallerName() {
        return Constant.ResourceFolder.DATABASE_SERVER_FOLDER;
    }
}
