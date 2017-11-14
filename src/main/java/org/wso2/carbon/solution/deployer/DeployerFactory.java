package org.wso2.carbon.solution.deployer;


import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.deployer.iam.IdentityProviderDeployer;
import org.wso2.carbon.solution.deployer.iam.ServiceProviderDeployer;
import org.wso2.carbon.solution.util.Constant;

public class DeployerFactory {
    private static DeployerFactory factory = new DeployerFactory();

    public static DeployerFactory getInstance() {
        return DeployerFactory.factory;
    }

    public Deployer getDeployer(String server, String artifact) throws CarbonSolutionException {
        if (server.equals(Constant.Server.IDENTITY_SERVER)) {
            if (artifact.equals(Constant.IdentityServer.SERVICE_PROVIDER)) {
                return new ServiceProviderDeployer();
            }
            if (artifact.equals(Constant.IdentityServer.IDENTITY_PROVIDER)) {
                return new IdentityProviderDeployer();
            }
        }
        throw new CarbonSolutionException("No artifact found for " + artifact);
    }
}
