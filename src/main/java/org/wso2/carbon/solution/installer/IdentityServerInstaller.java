package org.wso2.carbon.solution.installer;

import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.deployer.Deployer;
import org.wso2.carbon.solution.deployer.DeployerFactory;
import org.wso2.carbon.solution.model.config.IdentityServer;
import org.wso2.carbon.solution.model.config.ServerConfigEntity;
import org.wso2.carbon.solution.util.Constant;
import org.wso2.carbon.solution.util.ResourceLoader;
import org.wso2.carbon.solution.util.Utility;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class IdentityServerInstaller implements Installer {
    public static IdentityServer getIdentityServerConfig(String instance) throws CarbonSolutionException {
        Path tenantPath = Paths.get(Utility.RESOURCE_BASE, Constant.SERVER_CONFIG, Constant.SERVER_CONFIG_FILE_NAME);
        ServerConfigEntity serverConfigEntity = ResourceLoader
                .loadResource(tenantPath, ServerConfigEntity.class);
        List<IdentityServer> identityServer = serverConfigEntity.getServerConfig().getIdentityServer();
        for (IdentityServer server : identityServer) {
            if (server.getInstance().equals(instance)) {
                return server;
            }
        }
        throw new CarbonSolutionException("No Identity Server config found for given instance, " + instance);
    }

    public void install(String solution) {
        Path resourcePathObj = Paths.get(Utility.RESOURCE_BASE, Constant.SOLUTION_CONFIG, Constant.SOLUTIONS,
                                         solution,
                                         Constant.Server
                                                 .IDENTITY_SERVER);
        String[] tenantList = resourcePathObj.toFile().list();
        for (String tenant : tenantList) {
            if (tenant.equals(Constant.Tenant.CARBON_SUPER)) {
                Path tenantPath = Paths.get(Utility.RESOURCE_BASE, Constant.SOLUTION_CONFIG, Constant.SOLUTIONS,
                                            solution, Constant.Server.IDENTITY_SERVER,
                                            tenant);
                String[] artifactList = tenantPath.toFile().list();
                for (String artifact : artifactList) {
                    Path artifactPath = Paths.get(Utility.RESOURCE_BASE, Constant.SOLUTION_CONFIG, Constant
                                                          .SOLUTIONS, solution, Constant.Server.IDENTITY_SERVER,
                                                  tenant, artifact);
                    try {
                        IdentityServer defaultServer = getIdentityServerConfig("default");
                        Deployer deployer = DeployerFactory.getInstance()
                                .getDeployer(Constant.Server.IDENTITY_SERVER, artifact);
                        deployer.deploy(artifactPath.toString(), defaultServer);
                    } catch (CarbonSolutionException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                //#TODO: Have to create the tenant.
            }
        }
    }
}
