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

package org.wso2.carbon.solution.deployer.database.h2;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.deployer.database.DatabaseDeployer;
import org.wso2.carbon.solution.model.server.db.DatabaseServerArtifact;
import org.wso2.carbon.solution.util.ApplicationUtility;
import org.wso2.carbon.solution.util.Constant;

import java.io.File;
import java.io.IOException;

/**
 * H2 Database Deployer.
 */
public class H2Deployer extends DatabaseDeployer {
    private final String H2_DATABASE = "h2-database";
    private Log log = LogFactory.getLog(H2Deployer.class);

    @Override
    public boolean canHandle(String databaseType) {
        if (H2_DATABASE.equals(databaseType)) {
            log.debug("Can handle by this deployer.");
            return true;
        }
        return false;
    }

    @Override
    public void doDeploy(DatabaseServerArtifact databaseServerArtifact)
            throws CarbonSolutionException {
        String dbName = databaseServerArtifact.getDatabase() + ".h2.db";
        String originalDBBackup = ApplicationUtility.getCommonDataShemaHome() +
                                  File.separator + Constant.CommonResource.H2_DB;
        String schemaAbsolutePath = ApplicationUtility.getAbsolutePath(Constant.ResourceFolder.SOLUTION_HOME_FOLDER,
                                                                       databaseServerArtifact.getSolution(),
                                                                       Constant.ResourceFolder.DATABASE_SERVER_FOLDER,
                                                                       H2_DATABASE, dbName);
        try {
            //Copying clone of original database.
            log.debug("Copying " + originalDBBackup + " to the " + schemaAbsolutePath);
            FileUtils.copyFile(new File(originalDBBackup), new File(schemaAbsolutePath));
        } catch (IOException e) {
            String error = "Error occurred while copying h2 db schemas.";
            throw new CarbonSolutionException(error, e);
        }
    }
}
