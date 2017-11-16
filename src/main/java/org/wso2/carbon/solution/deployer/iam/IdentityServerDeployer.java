package org.wso2.carbon.solution.deployer.iam;

import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.installer.impl.IdentityServerInstaller;
import org.wso2.carbon.solution.model.config.server.Server;


public abstract class IdentityServerDeployer {

    public abstract boolean canHandle(String artifactType);

    public abstract void deploy(IdentityServerInstaller.IdentityServerArtifact identityServerArtifact, Server server)
            throws CarbonSolutionException;
}
