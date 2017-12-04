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


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.deployer.database.h2.H2Deployer;

import java.util.ArrayList;
import java.util.List;


/**
 * All the database deployers should register under this factory.
 */
public class DatabaseDeployerFactory {
    private static DatabaseDeployerFactory databaseDeployerFactory = new DatabaseDeployerFactory();
    private Log log = LogFactory.getLog(DatabaseDeployerFactory.class);
    private List<DatabaseDeployer> databaseDeployerList = new ArrayList<>();

    private DatabaseDeployerFactory() {
        databaseDeployerList.add(new H2Deployer());
    }

    /**
     * @return
     */
    public static DatabaseDeployerFactory getInstance() {
        return DatabaseDeployerFactory.databaseDeployerFactory;
    }

    /**
     * Get the deployer which is possible to handle the resource path.
     *
     * @param databaseType
     * @return
     * @throws CarbonSolutionException
     */
    public DatabaseDeployer getDeployer(String databaseType)
            throws CarbonSolutionException {
        for (DatabaseDeployer databaseDeployer : databaseDeployerList) {
            if (databaseDeployer.canHandle(databaseType)) {
                log.debug("Deployer can handle this resource path, " + databaseDeployer);
                return databaseDeployer;
            }
        }
        throw new CarbonSolutionException(
                "No deployer found to the given database type : " + databaseType);
    }
}
