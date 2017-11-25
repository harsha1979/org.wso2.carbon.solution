package org.wso2.carbon.solution.deployer.iam.impl;


import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.builder.iam.IdentityProviderBuilder;
import org.wso2.carbon.solution.deployer.iam.IdentityServerDeployer;
import org.wso2.carbon.solution.endpoint.iam.IdentityServerAdminClient;
import org.wso2.carbon.solution.installer.impl.IdentityServerInstaller;
import org.wso2.carbon.solution.model.config.server.Server;
import org.wso2.carbon.solution.model.iam.idp.IdentityProvider;
import org.wso2.carbon.solution.model.iam.idp.IdentityProviderEntity;
import org.wso2.carbon.solution.util.Constant;
import org.wso2.carbon.solution.util.ResourceLoader;
import org.wso2.carbon.solution.util.Utility;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class IdentityProviderDeployer extends IdentityServerDeployer {
    private static final String ARTIFACT_TYPE_IDENTITY_PROVIDERS = "identity-providers";


    @Override
    public boolean canHandle(String artifactType) {
        if (ARTIFACT_TYPE_IDENTITY_PROVIDERS.equals(artifactType)) {
            return true;
        }
        return false;
    }

    @Override
    public void doDeploy(IdentityServerInstaller.IdentityServerArtifact identityServerArtifact, Server server) throws
                                                                                                             CarbonSolutionException {
        Path resourcesPathObj = Paths
                .get(Utility.RESOURCE_HOME, Constant.SOLUTION_HOME, identityServerArtifact.getPath());
        /*Map<String, IdentityProviderEntity> identityProviderEntityMap = ResourceLoader
                .loadResources(resourcesPathObj, IdentityProviderEntity.class);*/
        IdentityProviderEntity identityProviderEntity_source = ResourceLoader.loadResource(resourcesPathObj,
                                                                                           IdentityProviderEntity.class);

            try {
                IdentityProvider identityProvider_source = identityProviderEntity_source.getIdentityProvider();
                org.wso2.carbon.identity.application.common.model.idp.xsd.IdentityProvider identityProvider_dest
                        = new org.wso2.carbon.identity.application.common.model.idp.xsd.IdentityProvider();
                IdentityProviderBuilder.getInstance()
                        .buildIdentityProvider(identityProvider_source, identityProvider_dest);

                try {
                    org.wso2.carbon.identity.application.common.model.idp.xsd.IdentityProvider idPByName
                            = IdentityServerAdminClient.getIdentityProviderMgtService(server)
                            .getIdPByName(identityProvider_source.getIdentityProviderName());
                    if(idPByName == null) {
                        IdentityServerAdminClient.getIdentityProviderMgtService(server).addIdP(identityProvider_dest);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                IdentityServerAdminClient.getIdentityProviderMgtService(server).updateIdP(identityProvider_dest
                                                                                                  .getIdentityProviderName(),
                                                                                          identityProvider_dest);
            } catch (Exception e) {
                e.printStackTrace();
            }

    }
}
