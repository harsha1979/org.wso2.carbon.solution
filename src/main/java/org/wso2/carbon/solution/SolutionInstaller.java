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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.solution.installer.Installer;
import org.wso2.carbon.solution.installer.InstallerFactory;
import org.wso2.carbon.solution.model.solution.ExecutionStep;
import org.wso2.carbon.solution.model.solution.SolutionConfig;
import org.wso2.carbon.solution.util.Constant;
import org.wso2.carbon.solution.util.ResourceManager;

import java.nio.file.Paths;
import java.util.List;

/**
 * SolutionInstaller initiate the installation of te given artifacts.
 */
public class SolutionInstaller {
    private static Log log = LogFactory.getLog(SolutionInstaller.class);

    /**
     * Installing solutions for given solution lists.
     *
     * @param solutionList
     * @throws CarbonSolutionException
     */
    public static void install(List<String> solutionList) throws CarbonSolutionException {
        for (String solution : solutionList) {
            log.info("Solution installer : " + solution);
            SolutionConfig solutionConfig = ResourceManager.getSolutionConfig(solution);
            List<ExecutionStep> executionSteps = solutionConfig.getExecutionSteps();
            if (executionSteps != null) {
                for (ExecutionStep executionStep : executionSteps) {
                    String path = executionStep.getPath();
                    if (StringUtils.isNotEmpty(path)) {
                        log.info("Solution installer resource path: " + path);
                        //Relative path to the resource home.
                        String solutionPath = Paths.get(solution, path).toString();
                        Installer installer = InstallerFactory.getInstance().getInstaller(solutionPath);

                        if (executionStep.getAction().equals(Constant.Solution.SOLUTION_EXECUTION_STATUS_INSTALL)) {
                            log.info("Solution installer action: install");
                            installer.install(solutionPath);
                        } else if (executionStep.getAction()
                                .equals(Constant.Solution.SOLUTION_EXECUTION_STATUS_UNINSTALL)) {
                            installer.uninstall(solutionPath);
                        }
                    }
                }
            }
        }
    }
}
