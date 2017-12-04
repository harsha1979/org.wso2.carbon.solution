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

package org.wso2.carbon.solution.model.server.db;

import org.apache.commons.lang.StringUtils;
import org.wso2.carbon.solution.CarbonSolutionException;

import java.io.File;

/**
 * Load the meta detail from the path.
 */
public class DatabaseServerArtifact {
    private String resourcePath;
    private String solution;
    private String databaseType;
    private String database;


    /**
     * Split the resource path and read the values to fill the DatabaseServerArtifact object itself.
     * <p>
     * String ex : solution-01/database-server/h2-database/solution01db
     * <p>
     * solution-01 => Solution Name.
     * h2-database => Database type.
     * solution01db => Database name.
     *
     * @param resourcePath
     * @throws CarbonSolutionException
     */
    public static DatabaseServerArtifact load(String resourcePath) throws CarbonSolutionException {
        DatabaseServerArtifact databaseServerArtifact = new DatabaseServerArtifact();
        if (StringUtils.isNotEmpty(resourcePath)) {
            String[] split = resourcePath.split(File.separator);
            if (split != null && split.length == 4) {
                databaseServerArtifact.setResourcePath(resourcePath);
                databaseServerArtifact.setSolution(split[0]);
                databaseServerArtifact.setDatabaseType(split[2]);
                databaseServerArtifact.setDatabase(split[3]);
            }
        }
        return databaseServerArtifact;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
}
