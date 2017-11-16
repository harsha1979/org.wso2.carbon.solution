package org.wso2.carbon.solution;


import org.wso2.carbon.solution.util.Utility;

import java.util.Arrays;


public class SolutionInstallationApplication {


    public static void main(String[] args) {


        //#TODO must changes these..
        /*if (StringUtils.isNotEmpty(args[0])) {
            Utility.RESOURCE_HOME = args[0];
        }*/
        Utility.RESOURCE_HOME = "/home/harshat/wso2/demo-suite/project/org.wso2.carbon.solution/demo-resources";
        try {
            Utility.setKeyStoreProperties();
        } catch (Exception e) {
        }

        try {
            SolutionInstaller solutionInstaller = new SolutionInstaller();
            solutionInstaller.install(Arrays.asList(new String[] { "solution-02" }));
        } catch (CarbonSolutionException e) {
            e.printStackTrace();
        }
    }
}
