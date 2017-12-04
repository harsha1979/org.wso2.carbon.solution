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
package org.wso2.carbon.solution;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Arrays;

/**
 * This is the class that we initiate the installation task.
 */
public class SolutionInstallationApplication {
    private static Log log = LogFactory.getLog(SolutionInstallationApplication.class);

    public static void main(String[] args) {
        log.info("WSO2 Identity Server - Solution Installation Task.");

        if (args.length == 0) {
            log.warn("You should pass the solution pattern list. Please follow the demo-suite guide to "
                     + "know how we should use this script further.");
        }
        try {
            SolutionInstaller.install(Arrays.asList(args));
        } catch (Exception e) {
            String errorMessage = "Error occurred while executing the installation process.";
            log.error(errorMessage, e);
        }
        log.info("Installation was done.");
    }
}
