package org.wso2.carbon.solution.deployer.database.mysql;

import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.deployer.database.DatabaseDeployer;
import org.wso2.carbon.solution.installer.impl.DatabaseServerInstaller;

/**
 * Created by harshat on 11/24/17.
 */
public class MYSQLDeployer extends DatabaseDeployer{
    @Override
    public boolean canHandle(String databaseType) {
        return false;
    }

    @Override
    public void doDeploy(DatabaseServerInstaller.DatabaseServerArtifact databaseServerArtifact)
            throws CarbonSolutionException {

    }
}
