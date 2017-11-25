package org.wso2.carbon.solution.deployer.database;

import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.installer.impl.DatabaseServerInstaller;
import org.wso2.carbon.solution.installer.impl.IdentityServerInstaller;
import org.wso2.carbon.solution.model.config.server.Server;
import org.wso2.carbon.solution.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;


public abstract class DatabaseDeployer {

    public abstract boolean canHandle(String databaseType);

    public void deploy(DatabaseServerInstaller.DatabaseServerArtifact databaseServerArtifact)
            throws CarbonSolutionException{
        doDeploy(databaseServerArtifact);
    }

    public abstract void doDeploy(DatabaseServerInstaller.DatabaseServerArtifact databaseServerArtifact)
            throws CarbonSolutionException;
}
