package org.wso2.carbon.solution.installer.impl;


import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.deployer.database.DatabaseDeployer;
import org.wso2.carbon.solution.deployer.database.DatabaseDeployerFactory;
import org.wso2.carbon.solution.installer.Installer;

import java.io.File;

public class DatabaseServerInstaller extends Installer {
    private static final String DATABASE_SERVER = "database-server";

    @Override
    public boolean canHandle(String path) throws CarbonSolutionException {
        String serverName = getServerName(path);
        if (DATABASE_SERVER.equals(serverName)) {
            return true;
        }
        return false;
    }

    @Override
    public void install(String path) throws CarbonSolutionException {
        DatabaseServerArtifact databaseServerArtifact = new DatabaseServerArtifact(path);
        DatabaseDeployer deployer = DatabaseDeployerFactory.getInstance()
                .getDeployer(databaseServerArtifact.getDatabaseType());
        deployer.deploy(databaseServerArtifact);
    }

    public static class DatabaseServerArtifact {
        private String path;
        private String solution;
        private String databaseType;
        private String database;

        public DatabaseServerArtifact(String path) throws CarbonSolutionException {
            this.path = path;
            String[] split = path.split(File.separator);
            if (split != null) {
                if (split.length > 0) {
                    solution = split[0];
                }
                if (split.length > 2) {
                    databaseType = split[2];
                }
                if (split.length > 3) {
                    database = split[3];
                }
            }
        }

        public String getPath() {
            return path;
        }

        public String getSolution() {
            return solution;
        }


        public String getDatabaseType() {
            return databaseType;
        }


        public String getDatabase() {
            return database;
        }
    }
}
