package org.wso2.carbon.solution;

import com.sforce.ws.*;
import org.wso2.carbon.solution.util.Utility;

import java.util.Arrays;

//Test Class
public class SolutionInstallationApplication {


    public static void main(String[] args) {
        //#TODO: solution paramters should pass as parameters.
        Utility.RESOURCE_HOME = "/home/harshat/wso2/demo-suite/project/org.wso2.carbon.solution/demo-resources";
        try {
            Utility.setKeyStoreProperties();
        } catch (Exception e) {

        }

        try {
            SolutionInstaller solutionInstaller = new SolutionInstaller();
            solutionInstaller.install(Arrays.asList(new String[] {"solution-04" }));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
