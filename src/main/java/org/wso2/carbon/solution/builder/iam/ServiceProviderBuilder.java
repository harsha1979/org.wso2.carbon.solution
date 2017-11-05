package org.wso2.carbon.solution.builder.iam;


import org.wso2.carbon.identity.application.common.model.xsd.*;
import org.wso2.carbon.solution.model.iam.sp.ClaimConfiguration;
import org.wso2.carbon.solution.model.iam.sp.FederatedIdentityProvider;
import org.wso2.carbon.solution.model.iam.sp.LocalAndOutBoundAuthenticationConfig;
import org.wso2.carbon.solution.model.iam.sp.LocalClaim;
import org.wso2.carbon.solution.model.iam.sp.RemoteClaim;

import java.util.ArrayList;
import java.util.List;

public class ServiceProviderBuilder {

    private static ServiceProviderBuilder serviceProviderBuilder = new ServiceProviderBuilder();

    public static ServiceProviderBuilder getInstance() {
        return ServiceProviderBuilder.serviceProviderBuilder;
    }

    public ServiceProvider buildServiceProvider(org.wso2.carbon.solution.model.iam.sp.ServiceProvider
                                                        serviceProvider_source) {
        ServiceProvider serviceProvider_dest = new ServiceProvider();
        buildServiceProvider(serviceProvider_source, serviceProvider_dest);
        return serviceProvider_dest;
    }

    public void buildServiceProvider(org.wso2.carbon.solution.model.iam.sp.ServiceProvider serviceProvider_source,
                                     ServiceProvider serviceProvider_dest) {
        if (serviceProvider_source != null && serviceProvider_dest != null) {
            serviceProvider_dest.setApplicationID(serviceProvider_source.getId());
            serviceProvider_dest.setApplicationName(serviceProvider_source.getName());
            serviceProvider_dest.setDescription(serviceProvider_source.getDescription());
            serviceProvider_dest.setSaasApp(serviceProvider_source.isIsSaasApp());

            ClaimConfiguration claimConfiguration_source = serviceProvider_source.getClaimConfiguration();
            if (claimConfiguration_source != null) {
                ClaimConfig claimConfig_dest = new ClaimConfig();
                serviceProvider_dest.setClaimConfig(claimConfig_dest);
                buildClaimConfig(claimConfiguration_source, claimConfig_dest);
            }

            org.wso2.carbon.solution.model.iam.sp.InboundAuthenticationConfig
                    inboundAuthenticationConfig_source
                    = serviceProvider_source
                    .getInboundAuthenticationConfig();
            if (inboundAuthenticationConfig_source != null) {
                InboundAuthenticationConfig inboundAuthenticationConfig_dest = new InboundAuthenticationConfig();
                serviceProvider_dest.setInboundAuthenticationConfig(inboundAuthenticationConfig_dest);

                buildInboundAuthenticationConfig(inboundAuthenticationConfig_source, inboundAuthenticationConfig_dest);
            }

            org.wso2.carbon.solution.model.iam.sp.InboundProvisioningConfig
                    inboundProvisioningConfig_source
                    = serviceProvider_source
                    .getInboundProvisioningConfig();
            if (inboundProvisioningConfig_source != null) {
                InboundProvisioningConfig inboundProvisioningConfig_dest = new InboundProvisioningConfig();
                serviceProvider_dest.setInboundProvisioningConfig(inboundProvisioningConfig_dest);

                buuldInboundProvisioningConfig(inboundProvisioningConfig_source, inboundProvisioningConfig_dest);
            }

            LocalAndOutBoundAuthenticationConfig localAndOutBoundAuthenticationConfig_source = serviceProvider_source
                    .getLocalAndOutBoundAuthenticationConfig();
            if (localAndOutBoundAuthenticationConfig_source != null) {
                LocalAndOutboundAuthenticationConfig localAndOutboundAuthenticationConfig_dest = new
                        LocalAndOutboundAuthenticationConfig();
                serviceProvider_dest.setLocalAndOutBoundAuthenticationConfig(localAndOutboundAuthenticationConfig_dest);

                buildLocalAndOutBoundAuthenticationConfig(localAndOutBoundAuthenticationConfig_source,
                                                          localAndOutboundAuthenticationConfig_dest);
            }
        }
    }

    private void buildLocalAndOutBoundAuthenticationConfig(LocalAndOutBoundAuthenticationConfig
                                                                   localAndOutBoundAuthenticationConfig_source,
                                                           LocalAndOutboundAuthenticationConfig
                                                                   localAndOutboundAuthenticationConfig_dest) {
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
                    }
                }
            }
        }
    }

    private void buuldInboundProvisioningConfig(org.wso2.carbon.solution.model.iam.sp.InboundProvisioningConfig
                                                        inboundProvisioningConfig_source,
                                                InboundProvisioningConfig inboundProvisioningConfig_dest) {
        inboundProvisioningConfig_dest.setDumbMode(inboundProvisioningConfig_source.isIsDumbMode());
        inboundProvisioningConfig_dest
                .setProvisioningEnabled(inboundProvisioningConfig_source.isIsProvisioningEnabled());
        inboundProvisioningConfig_dest
                .setProvisioningUserStore(inboundProvisioningConfig_source.getProvisioningUserStore());
    }

    private void buildInboundAuthenticationConfig(org.wso2.carbon.solution.model.iam.sp.InboundAuthenticationConfig
                                                          inboundAuthenticationConfig_source,
                                                  InboundAuthenticationConfig inboundAuthenticationConfig_dest) {
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
                            (new InboundAuthenticationRequestConfig[inboundAuthenticationRequestConfigs_dest.size()]));
        }
    }

    private void buildClaimConfig(ClaimConfiguration claimConfiguration_source, ClaimConfig claimConfig_dest) {
        claimConfig_dest.setAlwaysSendMappedLocalSubjectId(claimConfiguration_source.isAlwaysSendMappedLocalSubjectId
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
        }
    }

    private void buildClaimMapping(org.wso2.carbon.solution.model.iam.sp.ClaimMapping claimMapping_source,
                                   ClaimMapping claimMapping_dest) {
        claimMapping_dest.setDefaultValue(claimMapping_source.getDefaultValue());
        claimMapping_dest.setMandatory(claimMapping_source.isIsMandetory());
        claimMapping_dest.setRequested(claimMapping_source.isIsRequested());

        LocalClaim localClaim_source = claimMapping_source.getLocalClaim();
        Claim localClaim_dest = new Claim();
        localClaim_dest.setClaimId(localClaim_source.getClaimId());
        localClaim_dest.setClaimUri(localClaim_source.getClaimUri());

        RemoteClaim remoteClaim_source = claimMapping_source.getRemoteClaim();
        Claim remoteClaim_dest = new Claim();
        remoteClaim_dest.setClaimId(remoteClaim_source.getClaimId());
        remoteClaim_dest.setClaimUri(remoteClaim_source.getClaimUri());
    }
}
