package org.wso2.carbon.solution;

import org.wso2.carbon.solution.installer.Installer;
import org.wso2.carbon.solution.installer.InstallerFactory;
import org.wso2.carbon.solution.model.config.solution.ExecutionStep;
import org.wso2.carbon.solution.model.config.solution.SolutionConfig;
import org.wso2.carbon.solution.util.ResourceLoader;

import java.io.File;
import java.util.List;


public class SolutionInstaller {

    public void install(List<String> solutionList) throws CarbonSolutionException {
        for (String solution : solutionList) {
            SolutionConfig solutionConfig = ResourceLoader.getSolutionConfig(solution);
            List<ExecutionStep> executionSteps = solutionConfig.getExecutionSteps();
            for (ExecutionStep executionStep : executionSteps) {
                if (executionStep.getAction().equals("install")) {
                    String path = executionStep.getPath();
                    String solutionPath = solution + File.separator + path;
                    Installer installer = InstallerFactory.getInstance().getInstaller(solutionPath);
                    installer.install(solutionPath);
                }
            }
        }
    }
}
