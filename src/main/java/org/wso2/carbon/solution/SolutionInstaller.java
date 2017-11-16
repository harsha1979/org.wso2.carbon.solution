package org.wso2.carbon.solution;

import org.wso2.carbon.solution.installer.Installer;
import org.wso2.carbon.solution.installer.InstallerFactory;
import org.wso2.carbon.solution.model.config.solution.ExecutionStep;
import org.wso2.carbon.solution.model.config.solution.SolutionConfig;
import org.wso2.carbon.solution.util.Constant;
import org.wso2.carbon.solution.util.ResourceLoader;
import org.wso2.carbon.solution.util.Utility;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Created by harshat on 11/6/17.
 */
public class SolutionInstaller {

    public void install(List<String> solutionList) throws CarbonSolutionException {
        for (String solution : solutionList) {
            SolutionConfig solutionConfig = ResourceLoader.getSolutionConfig(solution);
            List<ExecutionStep> executionSteps = solutionConfig.getExecutionSteps();
            for (ExecutionStep executionStep : executionSteps) {
                if (executionStep.isEnable()) {
                    String path = executionStep.getPath();
                    String solutionPath = solution + File.separator + path;
                    Installer installer = InstallerFactory.getInstance().getInstaller(solutionPath);
                    installer.install(solutionPath);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public void install1(List<String> solutionList) {
        for (String solution : solutionList) {
            List<String> servers = getServers(solution);
            for (String server : servers) {
                try {
                    Installer installer = InstallerFactory.getInstance().getInstaller(server);
                    installer.install(solution);
                } catch (CarbonSolutionException e) {

                }
            }
        }
    }

    private List<String> getServers(String solution) {
        Path resourcePathObj = Paths
                .get(Utility.RESOURCE_HOME, Constant.SOLUTION_CONFIG, Constant.SOLUTION_HOME, solution);
        return Arrays.asList(resourcePathObj.toFile().list());
    }
}
