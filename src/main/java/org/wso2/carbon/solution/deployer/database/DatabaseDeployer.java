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

package org.wso2.carbon.solution.deployer.database;

import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.model.server.db.DatabaseServerArtifact;

/**
 * Database deployers can deploy this class and implement BD specific implementation.
 */
public abstract class DatabaseDeployer {

    /**
     * Implement canHandle to check the possibility handle this resource.
     *
     * @param databaseType
     * @return
     */
    public abstract boolean canHandle(String databaseType);

    /**
     * Deploy implementation.
     *
     * @param databaseServerArtifact
     * @throws CarbonSolutionException
     */
    public void deploy(DatabaseServerArtifact databaseServerArtifact)
            throws CarbonSolutionException {
        doDeploy(databaseServerArtifact);
    }

    /**
     * Implementation specific deploy method.
     *
     * @param databaseServerArtifact
     * @throws CarbonSolutionException
     */
    public abstract void doDeploy(DatabaseServerArtifact databaseServerArtifact)
            throws CarbonSolutionException;
}
