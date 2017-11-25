package org.wso2.carbon.solution.installer.impl;

import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.deployer.tomcat.TomcatWebAppDeployer;
import org.wso2.carbon.solution.installer.Installer;
import org.wso2.carbon.solution.model.config.server.Server;
import org.wso2.carbon.solution.util.ResourceLoader;

import java.io.File;

public class TomcatInstaller extends Installer {

    private static final String TOMCAT_SERVER = "tomcat";

    @Override
    public boolean canHandle(String path) throws CarbonSolutionException {
        String serverName = getServerName(path);
        if (TOMCAT_SERVER.equals(serverName)) {
            return true;
        }
        return false;
    }

    @Override
    public void install(String path) throws CarbonSolutionException {
        TomcatWebAppDeployer tomcatWebAppDeployer = new TomcatWebAppDeployer();
        TomcatServerArtifact tomcatServerArtifact = new TomcatServerArtifact(path);
        Server serverConfig = ResourceLoader
                .getServerConfig(TOMCAT_SERVER, tomcatServerArtifact.getInstanceName());
        tomcatWebAppDeployer.deploy(tomcatServerArtifact, serverConfig);
    }

    public static class TomcatServerArtifact {
        private String path;
        private String solution;
        private String instanceName;
        private String webApp;

        public TomcatServerArtifact(String path) throws CarbonSolutionException {
            this.path = path;
            String[] split = path.split(File.separator);
            if (split != null) {
                if (split.length > 0) {
                    solution = split[0];
                }
                if (split.length > 2) {
                    instanceName = split[2];
                }
                if (split.length > 4) {
                    webApp = split[4];
                }
            }
        }

        public String getInstanceName() {
            return instanceName;
        }

        public String getPath() {
            return path;
        }

        public String getSolution() {
            return solution;
        }

        public String getWebApp() {
            return webApp;
        }
    }
}
