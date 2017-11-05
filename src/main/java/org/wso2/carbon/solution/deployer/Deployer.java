package org.wso2.carbon.solution.deployer;


import org.wso2.carbon.solution.model.config.IdentityServer;

public interface Deployer {
    public void deploy(String artifactPath, IdentityServer identityServer);
}
