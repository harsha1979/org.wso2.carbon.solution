/*
 *  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.wso2.carbon.solution.deployer.iam.impl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.common.model.xsd.ServiceProvider;
import org.wso2.carbon.identity.application.mgt.stub.IdentityApplicationManagementServiceStub;
import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.builder.iam.ServiceProviderBuilder;
import org.wso2.carbon.solution.deployer.iam.IdentityServerDeployer;
import org.wso2.carbon.solution.deployer.iam.impl.oauth2.OAuth2ConfigDeployer;
import org.wso2.carbon.solution.deployer.iam.impl.saml.SAMLConfigDeployer;
import org.wso2.carbon.solution.endpoint.iam.IdentityServerAdminClient;
import org.wso2.carbon.solution.model.iam.sp.InboundAuthenticationConfig;
import org.wso2.carbon.solution.model.iam.sp.InboundAuthenticationRequestConfig;
import org.wso2.carbon.solution.model.iam.sp.ServiceProviderEntity;
import org.wso2.carbon.solution.model.server.Server;
import org.wso2.carbon.solution.model.server.iam.IdentityServerArtifact;
import org.wso2.carbon.solution.util.Constant;
import org.wso2.carbon.solution.util.ResourceManager;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * ServiceProviderDeployer for all the different inbound protocols.
 * SAML, OAuth2 both has its own deployers.
 */
public class ServiceProviderDeployer extends IdentityServerDeployer {
    private Log log = LogFactory.getLog(ServiceProviderDeployer.class);

    @Override
    public void doDeploy(IdentityServerArtifact identityServerArtifact, Server server) throws
                                                                                       CarbonSolutionException {

        Path resourcesPathObj = Paths.get(
                Constant.ResourcePath.RESOURCE_HOME_PATH, Constant.ResourceFolder.SOLUTION_HOME_FOLDER,
                identityServerArtifact.getResourcePath());
        ServiceProviderEntity serviceProviderEntity_source =
                ResourceManager.loadYAMLResource(resourcesPathObj, ServiceProviderEntity.class);
        log.info(
                "Deployment start for Service provide, " + serviceProviderEntity_source.getServiceProvider().getName());
        try {
            org.wso2.carbon.solution.model.iam.sp.ServiceProvider serviceProvider_source
                    = serviceProviderEntity_source
                    .getServiceProvider();
            ServiceProvider application = null;
            IdentityApplicationManagementServiceStub applicationManagementService = IdentityServerAdminClient
                    .getApplicationManagementService(server);
            try {
                application = applicationManagementService.getApplication(serviceProvider_source.getName());
                if (application != null) {
                    applicationManagementService.deleteApplication(serviceProvider_source.getName());
                    application = null;
                }
            } catch (Exception e) {
                log.error("Error occurred while getting the application for, " + serviceProvider_source.getName());
            }

            if (availableInboundProtocol(SAMLConfigDeployer.PROTOCOL_SAML, serviceProvider_source)) {
                SAMLConfigDeployer.deploy(serviceProvider_source, identityServerArtifact, server);
            }
            if (availableInboundProtocol(OAuth2ConfigDeployer.PROTOCOL_OAUTH2, serviceProvider_source)) {
                OAuth2ConfigDeployer.deploy(serviceProvider_source, identityServerArtifact, server);
            }
            updateSensibleDefaults(serviceProvider_source);
            ServiceProvider serviceProvider_dest
                    = ServiceProviderBuilder.getInstance().buildServiceProvider(serviceProvider_source);
            if (application == null) {
                applicationManagementService.createApplication(serviceProvider_dest);
                try {
                    application = applicationManagementService.getApplication(serviceProvider_source.getName());
                } catch (Exception e) {
                    log.error("Error occurred while getting the application for, " + serviceProvider_source.getName());
                }
            }
            if (application != null) {
                serviceProvider_dest.setApplicationID(application.getApplicationID());
                applicationManagementService.updateApplication(serviceProvider_dest);
            }
            log.info("Deployment done for Service provide, " + serviceProviderEntity_source.getServiceProvider().getName
                    ());
        } catch (Exception e) {
            String errorMsg = "Error occurred while installing the service provider, " +
                              serviceProviderEntity_source.getServiceProvider().getName();
            throw new CarbonSolutionException(errorMsg, e);
        }
    }

    /**
     * Get the availableInboundProtocol.
     *
     * @param authType
     * @param serviceProvider
     * @return
     */
    private boolean availableInboundProtocol(String authType, org.wso2.carbon.solution.model.iam.sp.ServiceProvider
            serviceProvider) {
        if (serviceProvider != null) {
            InboundAuthenticationConfig inboundAuthenticationConfig = serviceProvider.getInboundAuthenticationConfig();
            if (inboundAuthenticationConfig != null) {
                List<InboundAuthenticationRequestConfig> inboundAuthenticationRequestConfigs
                        = inboundAuthenticationConfig
                        .getInboundAuthenticationRequestConfigs();
                if (inboundAuthenticationRequestConfigs != null) {
                    for (InboundAuthenticationRequestConfig inboundAuthenticationRequestConfig :
                            inboundAuthenticationRequestConfigs) {
                        if (authType.equals(inboundAuthenticationRequestConfig.getInboundAuthType())) {
                            log.debug("Found inbound protocol for, " + authType);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    protected String getArtifactType() {
        return Constant.ResourceFolder.SERVICE_PROVIDERS_FOLDER;
    }

    /**
     * Update sensible default values for service provider level.
     *
     * @param serviceProvider_source
     */
    private void updateSensibleDefaults(org.wso2.carbon.solution.model.iam.sp.ServiceProvider serviceProvider_source) {

        boolean isPassiveSTSConfigEmpty = true;
        boolean isOpenIDConfigEmpty = true;
        if (serviceProvider_source != null) {
            InboundAuthenticationConfig inboundAuthenticationConfig = serviceProvider_source
                    .getInboundAuthenticationConfig();
            if (inboundAuthenticationConfig != null) {
                List<InboundAuthenticationRequestConfig> inboundAuthenticationRequestConfigs
                        = inboundAuthenticationConfig
                        .getInboundAuthenticationRequestConfigs();
                for (InboundAuthenticationRequestConfig inboundAuthenticationRequestConfig :
                        inboundAuthenticationRequestConfigs) {
                    if ("passivests".equals(inboundAuthenticationRequestConfig.getInboundAuthType())) {
                        isPassiveSTSConfigEmpty = false;
                    }
                    if ("openid".equals(inboundAuthenticationRequestConfig.getInboundAuthType())) {
                        isOpenIDConfigEmpty = false;
                    }
                }
            } else {
                inboundAuthenticationConfig = new InboundAuthenticationConfig();
                serviceProvider_source.setInboundAuthenticationConfig(inboundAuthenticationConfig);
            }
            if (isPassiveSTSConfigEmpty) {
                InboundAuthenticationRequestConfig inboundAuthenticationRequestConfig = new
                        InboundAuthenticationRequestConfig();
                inboundAuthenticationRequestConfig.setInboundAuthType("passivests");
                inboundAuthenticationRequestConfig.setInboundAuthKey("");
                inboundAuthenticationConfig.getInboundAuthenticationRequestConfigs().add
                        (inboundAuthenticationRequestConfig);
            }

            if (isOpenIDConfigEmpty) {
                InboundAuthenticationRequestConfig inboundAuthenticationRequestConfig = new
                        InboundAuthenticationRequestConfig();
                inboundAuthenticationRequestConfig.setInboundAuthType("openid");
                inboundAuthenticationRequestConfig.setInboundAuthKey("");
                inboundAuthenticationConfig.getInboundAuthenticationRequestConfigs().add
                        (inboundAuthenticationRequestConfig);
            }
        }
    }
}
