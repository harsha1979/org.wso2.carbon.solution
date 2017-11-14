package org.wso2.carbon.solution;


import org.apache.commons.lang3.StringUtils;
import org.wso2.carbon.solution.util.Utility;

import java.util.Arrays;


public class SolutionInstallationApplication {


    public static void main(String[] args) {


        //#TODO must changes these..
        /*if (StringUtils.isNotEmpty(args[0])) {
            Utility.RESOURCE_BASE = args[0];
        }*/
        Utility.RESOURCE_BASE = "/home/harshat/wso2/demo-suite/project/org.wso2.carbon.solution/demo-resources";
        try {
            Utility.setKeyStoreProperties();
        } catch (Exception e) {
        }

        SolutionInstaller solutionInstaller = new SolutionInstaller();
        solutionInstaller.install(Arrays.asList(new String[] { "solution-02" }));
    }
}
