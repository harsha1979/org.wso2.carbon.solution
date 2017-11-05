package org.wso2.carbon.solution;

import org.wso2.carbon.solution.installer.Installer;
import org.wso2.carbon.solution.installer.InstallerFactory;
import org.wso2.carbon.solution.util.Constant;
import org.wso2.carbon.solution.util.Utility;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Created by harshat on 11/6/17.
 */
public class SolutionInstaller {


    public void install(List<String> solutionList) {
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
        Path resourcePathObj = Paths.get(Utility.RESOURCE_BASE, Constant.SOLUTION_CONFIG, Constant.SOLUTIONS, solution);
        return Arrays.asList(resourcePathObj.toFile().list());
    }
}
