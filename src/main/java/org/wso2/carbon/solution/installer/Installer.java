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
package org.wso2.carbon.solution.installer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.solution.CarbonSolutionException;

import java.io.File;

/**
 * Installer Abstract class that we can have to based for different installers.
 */
public abstract class Installer {
    private static Log log = LogFactory.getLog(Installer.class);

    /**
     * canHandle should return true once it ready to provide the service for given resource.
     *
     * @param path
     * @return
     * @throws CarbonSolutionException
     */
    public boolean canHandle(String path) throws CarbonSolutionException {
        String serverName = getServerName(path);
        if (getInstallerName().equals(serverName)) {
            return true;
        }
        return false;
    }

    /**
     * Should implement installation process based on the implementation.
     *
     * @param resourcePath
     * @throws CarbonSolutionException
     */
    public abstract void install(String resourcePath) throws CarbonSolutionException;

    /**
     * Should implement the implementation for uninstalling process.
     *
     * @param resourcePath
     * @throws CarbonSolutionException
     */
    public void uninstall(String resourcePath) throws CarbonSolutionException {
        log.warn("No default implementation found.");
        throw new UnsupportedOperationException("No default implementation found.");
    }

    /**
     * Read server name from the resource path.
     *
     * @param resourcePath
     * @return
     * @throws CarbonSolutionException
     */
    protected String getServerName(String resourcePath) throws CarbonSolutionException {
        if (StringUtils.isNotEmpty(resourcePath)) {
            String[] split = resourcePath.split(File.separator);
            if (split != null && split.length > 1) {
                return split[1];
            }
        }
        throw new CarbonSolutionException("No server found in the resource path, " + resourcePath);
    }

    protected abstract String getInstallerName();
}
