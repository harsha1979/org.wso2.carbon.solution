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

package org.wso2.carbon.solution.builder.iam;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.common.model.xsd.*;
import org.wso2.carbon.solution.model.iam.sp.ClaimConfiguration;
import org.wso2.carbon.solution.model.iam.sp.FederatedIdentityProvider;
import org.wso2.carbon.solution.model.iam.sp.LocalAndOutBoundAuthenticationConfig;
import org.wso2.carbon.solution.model.iam.sp.LocalClaim;
import org.wso2.carbon.solution.model.iam.sp.OutboundProvisioningConnector;
import org.wso2.carbon.solution.model.iam.sp.Owner;
import org.wso2.carbon.solution.model.iam.sp.RemoteClaim;

import java.util.ArrayList;
import java.util.List;

/**
 * Building Service Provider by using YAML file.
 */
public class ServiceProviderBuilder {
    private static ServiceProviderBuilder serviceProviderBuilder = new ServiceProviderBuilder();
    private Log log = LogFactory.getLog(ServiceProviderBuilder.class);

    public static ServiceProviderBuilder getInstance() {
        return ServiceProviderBuilder.serviceProviderBuilder;
    }

    /**
     * buildServiceProvider.
     *
     * @param serviceProvider_source
     * @return
     */
    public ServiceProvider buildServiceProvider(org.wso2.carbon.solution.model.iam.sp.ServiceProvider
                                                        serviceProvider_source) {
        ServiceProvider serviceProvider_dest = new ServiceProvider();
        buildServiceProvider(serviceProvider_source, serviceProvider_dest);
        return serviceProvider_dest;
    }

    /**
     * buildServiceProvider
     *
     * @param serviceProvider_source
     * @param serviceProvider_dest
     */
    private void buildServiceProvider(org.wso2.carbon.solution.model.iam.sp.ServiceProvider serviceProvider_source,
                                      ServiceProvider serviceProvider_dest) {

        if (serviceProvider_source != null) {
            log.debug("Start building ServiceProvider, " + serviceProvider_source.getName());

            serviceProvider_dest.setApplicationID(serviceProvider_source.getId());
            serviceProvider_dest.setApplicationName(serviceProvider_source.getName());
            serviceProvider_dest.setDescription(serviceProvider_source.getDescription());
            serviceProvider_dest.setSaasApp(serviceProvider_source.isIsSaasApp());

            buildClaimConfiguration(serviceProvider_source, serviceProvider_dest);

            buildInboundAuthenticationConfig(serviceProvider_source, serviceProvider_dest);

            buildInboundProvisioningConfig(serviceProvider_source, serviceProvider_dest);

            buldLocalAndOutBoundConfig(serviceProvider_source, serviceProvider_dest);

            buildOutboundProvisionConfig(serviceProvider_source, serviceProvider_dest);

            Owner owner_source = serviceProvider_source.getOwner();
            if (owner_source != null) {
                User owner_dest = new User();
                owner_dest.setTenantDomain(owner_source.getTenantDomain());
                owner_dest.setUserName(owner_source.getUserName());
                owner_dest.setUserStoreDomain(owner_source.getUserStoreDomain());
                serviceProvider_dest.setOwner(owner_dest);
            }

            log.debug("Done building ServiceProvider, " + serviceProvider_source.getName());
        }
    }


    /**
     * buildClaimConfiguration
     *
     * @param serviceProvider_source
     * @param serviceProvider_dest
     */
    private void buildClaimConfiguration(org.wso2.carbon.solution.model.iam.sp.ServiceProvider serviceProvider_source,
                                         ServiceProvider serviceProvider_dest) {
        ClaimConfiguration
                claimConfiguration_source = serviceProvider_source.getClaimConfiguration();
        if (claimConfiguration_source != null) {
            ClaimConfig claimConfig_dest = new ClaimConfig();
            serviceProvider_dest.setClaimConfig(claimConfig_dest);
            claimConfig_dest
                    .setAlwaysSendMappedLocalSubjectId(claimConfiguration_source.isAlwaysSendMappedLocalSubjectId
                            ());
            claimConfig_dest.setLocalClaimDialect(claimConfiguration_source.isLocalClaimDialect());
            claimConfig_dest.setRoleClaimURI(claimConfiguration_source.getRoleClaimURI());
            claimConfig_dest.setUserClaimURI(claimConfiguration_source.getUserClaimURI());

            List<org.wso2.carbon.solution.model.iam.sp.ClaimMapping> claimMappings_source = claimConfiguration_source
                    .getClaimMappings();
            if (claimMappings_source != null) {
                List<ClaimMapping> claimMappings_dest = new ArrayList<ClaimMapping>();
                for (org.wso2.carbon.solution.model.iam.sp.ClaimMapping claimMapping_source : claimMappings_source) {
                    ClaimMapping claimMapping_dest = new ClaimMapping();
                    claimMappings_dest.add(claimMapping_dest);

                    buildClaimMapping(claimMapping_source, claimMapping_dest);
                }
                claimConfig_dest
                        .setClaimMappings(claimMappings_dest.toArray(new ClaimMapping[claimMappings_dest.size()]));
            }
        }
    }


    /**
     * buildInboundAuthenticationConfig
     *
     * @param serviceProvider_source
     * @param serviceProvider_dest
     */
    private void buildInboundAuthenticationConfig(org.wso2.carbon.solution.model.iam.sp.ServiceProvider
                                                          serviceProvider_source,
                                                  ServiceProvider serviceProvider_dest) {
        org.wso2.carbon.solution.model.iam.sp.InboundAuthenticationConfig
                inboundAuthenticationConfig_source
                = serviceProvider_source
                .getInboundAuthenticationConfig();
        if (inboundAuthenticationConfig_source != null) {
            InboundAuthenticationConfig inboundAuthenticationConfig_dest = new InboundAuthenticationConfig();
            serviceProvider_dest.setInboundAuthenticationConfig(inboundAuthenticationConfig_dest);

            List<org.wso2.carbon.solution.model.iam.sp.InboundAuthenticationRequestConfig>

                    inboundAuthenticationRequestConfigs_source
                    = inboundAuthenticationConfig_source.getInboundAuthenticationRequestConfigs();

            if (inboundAuthenticationRequestConfigs_source != null) {
                List<InboundAuthenticationRequestConfig>
                        inboundAuthenticationRequestConfigs_dest = new ArrayList<InboundAuthenticationRequestConfig>();
                for (org.wso2.carbon.solution.model.iam.sp.InboundAuthenticationRequestConfig
                        inboundAuthenticationRequestConfig_source :
                        inboundAuthenticationRequestConfigs_source) {
                    InboundAuthenticationRequestConfig
                            inboundAuthenticationRequestConfig_dest = new InboundAuthenticationRequestConfig();
                    inboundAuthenticationRequestConfigs_dest.add(inboundAuthenticationRequestConfig_dest);

                    inboundAuthenticationRequestConfig_dest.setFriendlyName
                            (inboundAuthenticationRequestConfig_source.getFriendlyName());
                    inboundAuthenticationRequestConfig_dest.setInboundAuthKey
                            (inboundAuthenticationRequestConfig_source.getInboundAuthKey());
                    inboundAuthenticationRequestConfig_dest.setInboundAuthType
                            (inboundAuthenticationRequestConfig_source.getInboundAuthType());
                    inboundAuthenticationRequestConfig_dest.setInboundConfigType
                            (inboundAuthenticationRequestConfig_source.getInboundConfigType());

                    List<org.wso2.carbon.solution.model.iam.sp.Property> properties_source
                            = inboundAuthenticationRequestConfig_source.getProperties();
                    if (properties_source != null) {
                        List<Property> properties_dest = new ArrayList<Property>();
                        for (org.wso2.carbon.solution.model.iam.sp.Property property_source : properties_source) {
                            Property property_dest = new Property();
                            property_dest.setAdvanced(property_source.isAdvanced());
                            property_dest.setConfidential(property_source.isConfidential());
                            property_dest.setDefaultValue(property_source.getDefaultValue());
                            property_dest.setDescription(property_source.getDescription());
                            property_dest.setDisplayName(property_source.getDisplayName());
                            property_dest.setName(property_source.getName());
                            property_dest.setDisplayOrder(property_source.getDisplayOrder());
                            property_dest.setValue(property_source.getValue());
                            property_dest.setType(property_source.getType());
                            property_dest.setRequired(property_source.isIsRequired());
                            properties_dest.add(property_dest);
                        }
                    }
                }
                inboundAuthenticationConfig_dest.setInboundAuthenticationRequestConfigs
                        (inboundAuthenticationRequestConfigs_dest.toArray
                                (new InboundAuthenticationRequestConfig[inboundAuthenticationRequestConfigs_dest
                                        .size()]));
            }
        }
    }


    /**
     * buildInboundProvisioningConfig
     *
     * @param serviceProvider_source
     * @param serviceProvider_dest
     */
    private void buildInboundProvisioningConfig(org.wso2.carbon.solution.model.iam.sp.ServiceProvider
                                                        serviceProvider_source,
                                                ServiceProvider serviceProvider_dest) {
        org.wso2.carbon.solution.model.iam.sp.InboundProvisioningConfig
                inboundProvisioningConfig_source = serviceProvider_source.getInboundProvisioningConfig();
        if (inboundProvisioningConfig_source != null) {
            InboundProvisioningConfig inboundProvisioningConfig_dest = new InboundProvisioningConfig();
            serviceProvider_dest.setInboundProvisioningConfig(inboundProvisioningConfig_dest);

            inboundProvisioningConfig_dest.setDumbMode(inboundProvisioningConfig_source.isIsDumbMode());
            inboundProvisioningConfig_dest
                    .setProvisioningEnabled(inboundProvisioningConfig_source.isIsProvisioningEnabled());
            inboundProvisioningConfig_dest
                    .setProvisioningUserStore(inboundProvisioningConfig_source.getProvisioningUserStore());
        }
    }

    /**
     * buldLocalAndOutBoundConfig
     *
     * @param serviceProvider_source
     * @param serviceProvider_dest
     */
    private void buldLocalAndOutBoundConfig(org.wso2.carbon.solution.model.iam.sp.ServiceProvider
                                                    serviceProvider_source,
                                            ServiceProvider serviceProvider_dest) {
        LocalAndOutBoundAuthenticationConfig
                localAndOutBoundAuthenticationConfig_source = serviceProvider_source
                .getLocalAndOutBoundAuthenticationConfig();
        if (localAndOutBoundAuthenticationConfig_source != null) {
            LocalAndOutboundAuthenticationConfig localAndOutboundAuthenticationConfig_dest = new
                    LocalAndOutboundAuthenticationConfig();
            serviceProvider_dest.setLocalAndOutBoundAuthenticationConfig(localAndOutboundAuthenticationConfig_dest);

            localAndOutboundAuthenticationConfig_dest.setAlwaysSendBackAuthenticatedListOfIdPs
                    (localAndOutBoundAuthenticationConfig_source.isIsAlwaysSendBackAuthenticatedListOfIdPs());
            localAndOutboundAuthenticationConfig_dest.setAuthenticationType
                    (localAndOutBoundAuthenticationConfig_source.getAuthenticationType());
            localAndOutboundAuthenticationConfig_dest.setSubjectClaimUri
                    (localAndOutBoundAuthenticationConfig_source.getSubjectClaimUri());
            localAndOutboundAuthenticationConfig_dest.setEnableAuthorization
                    (localAndOutBoundAuthenticationConfig_source.isIsEnableAuthorization());
            localAndOutboundAuthenticationConfig_dest.setUseUserstoreDomainInLocalSubjectIdentifier
                    (localAndOutBoundAuthenticationConfig_source.isIsUseUserstoreDomainInLocalSubjectIdentifier());
            localAndOutboundAuthenticationConfig_dest.setUseTenantDomainInLocalSubjectIdentifier
                    (localAndOutBoundAuthenticationConfig_source.isIsUseTenantDomainInLocalSubjectIdentifier());


            List<org.wso2.carbon.solution.model.iam.sp.AuthenticationStep> authenticationSteps_source
                    = localAndOutBoundAuthenticationConfig_source
                    .getAuthenticationSteps();
            if (authenticationSteps_source != null) {
                List<AuthenticationStep> authenticationSteps_dest = new ArrayList<AuthenticationStep>();
                for (org.wso2.carbon.solution.model.iam.sp.AuthenticationStep authenticationStep_source :
                        authenticationSteps_source) {
                    AuthenticationStep authenticationStep_dest = new AuthenticationStep();
                    authenticationSteps_dest.add(authenticationStep_dest);

                    buildAuthenticationStep(authenticationStep_source, authenticationStep_dest);
                }
                localAndOutboundAuthenticationConfig_dest.setAuthenticationSteps(
                        authenticationSteps_dest.toArray(new AuthenticationStep[authenticationSteps_dest.size()]));
            }
        }
    }


    /**
     * buildOutboundProvisionConfig
     *
     * @param serviceProvider_source
     * @param serviceProvider_dest
     */
    private void buildOutboundProvisionConfig(org.wso2.carbon.solution.model.iam.sp.ServiceProvider
                                                      serviceProvider_source,
                                              ServiceProvider serviceProvider_dest) {
        org.wso2.carbon.solution.model.iam.sp.OutboundProvisioningConfig outboundProvisioningConfig_source
                = serviceProvider_source.getOutboundProvisioningConfig();

        if (outboundProvisioningConfig_source != null) {
            List<OutboundProvisioningConnector> outboundProvisioningConnectors_source =
                    outboundProvisioningConfig_source
                            .getOutboundProvisioningConnectors();

            if (outboundProvisioningConnectors_source != null) {
                OutboundProvisioningConfig outboundProvisioningConfig_dest = new OutboundProvisioningConfig();
                for (OutboundProvisioningConnector outboundProvisioningConnector_source :
                        outboundProvisioningConnectors_source) {
                    IdentityProvider identityProvider_dest = new IdentityProvider();
                    outboundProvisioningConfig_dest.addProvisioningIdentityProviders(identityProvider_dest);

                    identityProvider_dest.setIdentityProviderName(
                            outboundProvisioningConnector_source.getIdentityProviderName());

                    ProvisioningConnectorConfig defaultProvisioningConnectorConfig_dest = new
                            ProvisioningConnectorConfig();
                    identityProvider_dest
                            .setDefaultProvisioningConnectorConfig(defaultProvisioningConnectorConfig_dest);

                    defaultProvisioningConnectorConfig_dest
                            .setName(outboundProvisioningConnector_source.getProvisioningConnectorType());
                    defaultProvisioningConnectorConfig_dest
                            .setBlocking(outboundProvisioningConnector_source.isBlocking());
                    defaultProvisioningConnectorConfig_dest
                            .setEnabled(outboundProvisioningConnector_source.isEnabled());
                    defaultProvisioningConnectorConfig_dest
                            .setRulesEnabled(outboundProvisioningConnector_source.isRulesEnabled());

                    JustInTimeProvisioningConfig justInTimeProvisioningConfig = new JustInTimeProvisioningConfig();
                    identityProvider_dest.setJustInTimeProvisioningConfig(justInTimeProvisioningConfig);
                    justInTimeProvisioningConfig
                            .setProvisioningEnabled(outboundProvisioningConnector_source.isJitEnable());
                    serviceProvider_dest.setOutboundProvisioningConfig(outboundProvisioningConfig_dest);
                }
            }
        }
    }

    /**
     * buildAuthenticationStep
     *
     * @param authenticationStep_source
     * @param authenticationStep_dest
     */
    private void buildAuthenticationStep(org.wso2.carbon.solution.model.iam.sp.AuthenticationStep
                                                 authenticationStep_source,
                                         AuthenticationStep authenticationStep_dest) {
        authenticationStep_dest.setAttributeStep(authenticationStep_source.isIsAttributeStep());
        authenticationStep_dest.setStepOrder(authenticationStep_source.getStepOrder());
        authenticationStep_dest.setSubjectStep(authenticationStep_source.isIsSubjectStep());


        List<FederatedIdentityProvider> federatedIdentityProviders_source = authenticationStep_source
                .getFederatedIdentityProviders();
        if (federatedIdentityProviders_source != null) {
            List<IdentityProvider> federatedIdentityProviders_dest = new ArrayList<IdentityProvider>();

            for (FederatedIdentityProvider federatedIdentityProvider_source : federatedIdentityProviders_source) {
                IdentityProvider federatedIdentityProvider_dest = new IdentityProvider();
                federatedIdentityProviders_dest.add(federatedIdentityProvider_dest);

                federatedIdentityProvider_dest.setIdentityProviderName(federatedIdentityProvider_source
                                                                               .getIdentityProviderName());

                //#TODO: Why need default one to here.
                /*FederatedAuthenticatorConfig defaultAuthenticatorConfig = new FederatedAuthenticatorConfig();
                defaultAuthenticatorConfig.setName("samlsso");
                defaultAuthenticatorConfig.setDisplayName("samlsso");
                federatedIdentityProvider_dest.setDefaultAuthenticatorConfig(defaultAuthenticatorConfig);*/

                List<org.wso2.carbon.solution.model.iam.sp.FederatedAuthenticatorConfig>
                        federatedAuthenticatorConfigs_source
                        = federatedIdentityProvider_source.getFederatedAuthenticatorConfigs();

                if (federatedAuthenticatorConfigs_source != null) {
                    List<FederatedAuthenticatorConfig> federatedAuthenticatorConfigs_dest = new ArrayList
                            <FederatedAuthenticatorConfig>();
                    for (org.wso2.carbon.solution.model.iam.sp.FederatedAuthenticatorConfig
                            federatedAuthenticatorConfig_source :
                            federatedAuthenticatorConfigs_source) {
                        FederatedAuthenticatorConfig federatedAuthenticatorConfig_dest = new
                                FederatedAuthenticatorConfig();
                        federatedAuthenticatorConfigs_dest.add(federatedAuthenticatorConfig_dest);
                        federatedAuthenticatorConfig_dest.setName(federatedAuthenticatorConfig_source.getName());
                        federatedAuthenticatorConfig_dest
                                .setDisplayName(federatedAuthenticatorConfig_source.getDisplayName());
                    }
                    federatedIdentityProvider_dest.setFederatedAuthenticatorConfigs
                            (federatedAuthenticatorConfigs_dest.toArray(new
                                                                                FederatedAuthenticatorConfig[federatedAuthenticatorConfigs_dest
                                    .size()]));
                }
            }
            authenticationStep_dest.setFederatedIdentityProviders(federatedIdentityProviders_dest.toArray(new
                                                                                                                  IdentityProvider[federatedIdentityProviders_dest
                    .size()]));
        }


        List<org.wso2.carbon.solution.model.iam.sp.LocalAuthenticatorConfig> localAuthenticatorConfigs_source
                = authenticationStep_source.getLocalAuthenticatorConfigs();
        if (localAuthenticatorConfigs_source != null) {
            List<LocalAuthenticatorConfig> localAuthenticatorConfigs_dest = new ArrayList<>();
            for (org.wso2.carbon.solution.model.iam.sp.LocalAuthenticatorConfig localAuthenticatorConfig_source :
                    localAuthenticatorConfigs_source) {
                LocalAuthenticatorConfig localAuthenticatorConfig_dest = new LocalAuthenticatorConfig();
                localAuthenticatorConfigs_dest.add(localAuthenticatorConfig_dest);

                localAuthenticatorConfig_dest.setName(localAuthenticatorConfig_source.getName());
                localAuthenticatorConfig_dest.setDisplayName(localAuthenticatorConfig_source.getDisplayName());
            }
            authenticationStep_dest.setLocalAuthenticatorConfigs(localAuthenticatorConfigs_dest.toArray(new
                                                                                                                LocalAuthenticatorConfig[localAuthenticatorConfigs_dest
                    .size()]));
        }
    }

    /**
     * buildClaimMapping
     *
     * @param claimMapping_source
     * @param claimMapping_dest
     */
    private void buildClaimMapping(org.wso2.carbon.solution.model.iam.sp.ClaimMapping claimMapping_source,
                                   ClaimMapping claimMapping_dest) {
        claimMapping_dest.setDefaultValue(claimMapping_source.getDefaultValue());
        claimMapping_dest.setMandatory(claimMapping_source.isIsMandetory());
        claimMapping_dest.setRequested(claimMapping_source.isIsRequested());

        LocalClaim localClaim_source = claimMapping_source.getLocalClaim();
        Claim localClaim_dest = new Claim();
        localClaim_dest.setClaimId(localClaim_source.getClaimId());
        localClaim_dest.setClaimUri(localClaim_source.getClaimUri());
        claimMapping_dest.setLocalClaim(localClaim_dest);

        RemoteClaim remoteClaim_source = claimMapping_source.getRemoteClaim();
        Claim remoteClaim_dest = new Claim();
        remoteClaim_dest.setClaimId(remoteClaim_source.getClaimId());
        remoteClaim_dest.setClaimUri(remoteClaim_source.getClaimUri());
        claimMapping_dest.setRemoteClaim(remoteClaim_dest);
    }
}

