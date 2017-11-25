package org.wso2.carbon.solution.deployer.iam;


import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.deployer.iam.impl.IdentityProviderDeployer;
import org.wso2.carbon.solution.deployer.iam.impl.ServiceProviderDeployer;
import org.wso2.carbon.solution.deployer.iam.impl.UserStoreDeployer;

import java.util.ArrayList;
import java.util.List;

public class IdentityServerDeployerFactory {

    private static IdentityServerDeployerFactory identityServerDeployerFactory = new IdentityServerDeployerFactory();
    private List<IdentityServerDeployer> identityServerDeployerRegistry = new ArrayList<>();

    private IdentityServerDeployerFactory() {
        identityServerDeployerRegistry.add(new ServiceProviderDeployer());
        identityServerDeployerRegistry.add(new IdentityProviderDeployer());
        identityServerDeployerRegistry.add(new UserStoreDeployer());
    }

    public static IdentityServerDeployerFactory getInstance() {
        return IdentityServerDeployerFactory.identityServerDeployerFactory;
    }

    public IdentityServerDeployer getDeployer(String artifactType)
            throws CarbonSolutionException {

        for (IdentityServerDeployer identityServerDeployer : identityServerDeployerRegistry) {
            if (identityServerDeployer.canHandle(artifactType)) {
                return identityServerDeployer;
            }
        }

        throw new CarbonSolutionException(
                "No deployer found to the given artifact type : " + artifactType);
    }
}
