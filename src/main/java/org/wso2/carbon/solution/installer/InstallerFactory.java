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


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.SolutionInstaller;
import org.wso2.carbon.solution.installer.impl.DatabaseServerInstaller;
import org.wso2.carbon.solution.installer.impl.IdentityServerInstaller;
import org.wso2.carbon.solution.installer.impl.TomcatInstaller;

import java.util.ArrayList;
import java.util.List;

/**
 * InstallerFactory hold all the installer in its registry itself and
 * provide the correct one when canHandler get return true.
 */
public class InstallerFactory {

    private static Log log = LogFactory.getLog(SolutionInstaller.class);
    private static InstallerFactory installerFactory = new InstallerFactory();
    private List<Installer> installerRegistry = new ArrayList<>();

    private InstallerFactory() {
        //Register each installer. When we get a new installer, we need to register here.
        installerRegistry.add(new IdentityServerInstaller());
        installerRegistry.add(new TomcatInstaller());
        installerRegistry.add(new DatabaseServerInstaller());
    }

    /**
     * Get the instance.
     *
     * @return
     */
    public static InstallerFactory getInstance() {
        return InstallerFactory.installerFactory;
    }

    /**
     * Pick the correct installer based on canHandle impl.
     *
     * @param path
     * @return
     * @throws CarbonSolutionException
     */
    public Installer getInstaller(String path) throws CarbonSolutionException {

        for (Installer installer : installerRegistry) {
            try {
                if (installer.canHandle(path)) {
                    return installer;
                }
            } catch (Exception e) {
                log.error("Error occurred while executing the canHandle, " + e.getMessage());
            }
        }
        throw new CarbonSolutionException("No installer found to the given path : " + path);
    }
}
