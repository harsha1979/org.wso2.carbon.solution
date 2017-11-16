package org.wso2.carbon.solution.installer.impl;

import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.deployer.iam.IdentityServerDeployer;
import org.wso2.carbon.solution.deployer.iam.IdentityServerDeployerFactory;
import org.wso2.carbon.solution.installer.Installer;
import org.wso2.carbon.solution.model.config.server.Server;
import org.wso2.carbon.solution.util.Constant;
import org.wso2.carbon.solution.util.ResourceLoader;

import java.io.File;

public class IdentityServerInstaller extends Installer {

    private static final String IDENTITY_SERVER = "identity-server";




/*    public static IdentityServer getIdentityServerConfig(String instance) throws CarbonSolutionException {
        Path tenantPath = Paths.get(Utility.RESOURCE_HOME, Constant.SERVERS_HOME, Constant.SERVER_CONFIG_FILE);
        ServerConfigEntity serverConfigEntity = ResourceLoader
                .loadResource(tenantPath, ServerConfigEntity.class);
        List<IdentityServer> identityServer = serverConfigEntity.getServerConfig().getIdentityServer();
        for (IdentityServer server : identityServer) {
            if (server.getInstance().equals(instance)) {
                return server;
            }
        }
        throw new CarbonSolutionException("No Identity Server config found for given instance, " + instance);
    }*/

    @Override
    public boolean canHandle(String path) throws CarbonSolutionException {
        String serverName = getServerName(path);
        if (IDENTITY_SERVER.equals(serverName)) {
            return true;
        }
        return false;
    }

    /*public void install1(String solution) {
        Path resourcePathObj = Paths.get(Utility.RESOURCE_HOME, Constant.SOLUTION_CONFIG, Constant.SOLUTION_HOME,
                                         solution,
                                         Constant.Server
                                                 .IDENTITY_SERVER);
        String[] tenantList = resourcePathObj.toFile().list();
        for (String tenant : tenantList) {
            if (tenant.equals(Constant.Tenant.CARBON_SUPER)) {
                Path tenantPath = Paths.get(Utility.RESOURCE_HOME, Constant.SOLUTION_CONFIG, Constant.SOLUTION_HOME,
                                            solution, Constant.Server.IDENTITY_SERVER,
                                            tenant);
                String[] artifactList = tenantPath.toFile().list();
                for (String artifact : artifactList) {
                    Path artifactPath = Paths.get(Utility.RESOURCE_HOME, Constant.SOLUTION_CONFIG, Constant
                                                          .SOLUTION_HOME, solution, Constant.Server.IDENTITY_SERVER,
                                                  tenant, artifact);
                    if (artifactPath.toFile().list().length > 0) {
                        try {
                            IdentityServer defaultServer = getIdentityServerConfig("default");
                            Deployer deployer = DeployerFactory.getInstance()
                                    .getDeployer(Constant.Server.IDENTITY_SERVER, artifact);
                            deployer.deploy(artifactPath.toString(), null);
                        } catch (CarbonSolutionException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                //#TODO: Have to create the tenant.
            }
        }
    }
*/
    @Override
    public void install(String path) throws CarbonSolutionException {
        IdentityServerArtifact identityServerArtifact = new IdentityServerArtifact(path);
        if (identityServerArtifact.getTenantDomain().equals(Constant.Tenant.CARBON_SUPER)) {
            IdentityServerDeployer deployer = IdentityServerDeployerFactory.getInstance()
                    .getDeployer(identityServerArtifact.artifactType);
            Server serverConfig = ResourceLoader
                    .getServerConfig(IDENTITY_SERVER, identityServerArtifact.getInstanceName());
            deployer.deploy(identityServerArtifact, serverConfig);
        }
    }

    public static class IdentityServerArtifact {
        private String path;
        private String solution;
        private String instanceName;
        private String tenantDomain;
        private String artifactType;
        private String artifactFile;

        public IdentityServerArtifact(String path) throws CarbonSolutionException {
            this.path = path;
            String[] split = path.split(File.separator);
            if (split != null) {
                if (split.length > 0) {
                    solution = split[0];
                }
                if (split.length > 2) {
                    instanceName = split[2];
                }
                if (split.length > 3) {
                    tenantDomain = split[3];
                }
                if (split.length > 4) {
                    artifactType = split[4];
                }
                if (split.length > 5) {
                    artifactFile = split[5];
                }
            }
        }

        public String getArtifactFile() {
            return artifactFile;
        }

        public String getArtifactType() {
            return artifactType;
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

        public String getTenantDomain() {
            return tenantDomain;
        }
    }
}
