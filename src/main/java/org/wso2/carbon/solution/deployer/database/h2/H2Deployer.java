package org.wso2.carbon.solution.deployer.database.h2;

import org.apache.commons.io.FileUtils;
import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.deployer.database.DatabaseDeployer;
import org.wso2.carbon.solution.installer.impl.DatabaseServerInstaller;
import org.wso2.carbon.solution.util.Utility;

import java.io.File;
import java.io.IOException;


public class H2Deployer extends DatabaseDeployer {

    private final String H2_DATABASE = "h2-database";
    @Override
    public boolean canHandle(String databaseType) {
        if(H2_DATABASE.equals(databaseType)){
            return true;
        }
        return false;
    }

    @Override
    public void doDeploy(DatabaseServerInstaller.DatabaseServerArtifact databaseServerArtifact)
            throws CarbonSolutionException {
        String commonDBSchema = Utility.getCommonDataShemaHome() + File.separator + "WSO2CARBON_DB.h2.db";
        String newSchemaName = Utility.getSolutionHome() + File.separator + databaseServerArtifact.getSolution() +
                               File.separator + "database-server" +
                File
                .separator + H2_DATABASE + File.separator +  databaseServerArtifact.getDatabase() + ".h2.db";

        try {
            FileUtils.copyFile(new File(commonDBSchema), new File(newSchemaName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
