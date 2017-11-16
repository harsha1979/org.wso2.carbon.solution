package org.wso2.carbon.solution.builder.iam;


import org.wso2.carbon.identity.application.common.model.idp.xsd.*;
import org.wso2.carbon.solution.model.iam.idp.AuthenticatorProperty;
import org.wso2.carbon.solution.model.iam.idp.IdpClaim;
import org.wso2.carbon.solution.model.iam.idp.IdpProperty;
import org.wso2.carbon.solution.model.iam.idp.LocalClaim;
import org.wso2.carbon.solution.model.iam.idp.Permission;
import org.wso2.carbon.solution.model.iam.idp.ProvisioningProperty;
import org.wso2.carbon.solution.model.iam.idp.RemoteClaim;

import java.util.ArrayList;
import java.util.List;


public class IdentityProviderBuilder {
    private static IdentityProviderBuilder identityProviderBuilder = new IdentityProviderBuilder();

    public static IdentityProviderBuilder getInstance() {
        return IdentityProviderBuilder.identityProviderBuilder;
    }

    public void buildIdentityProvider(org.wso2.carbon.solution.model.iam.idp.IdentityProvider
                                              identityProvider_source, IdentityProvider identityProvider_dest) {

        identityProvider_dest.setIdentityProviderName(identityProvider_source.getIdentityProviderName());
        identityProvider_dest.setIdentityProviderDescription(identityProvider_source.getIdentityProviderDescription());
        identityProvider_dest.setAlias(identityProvider_source.getAlias());
        identityProvider_dest.setCertificate(identityProvider_source.getCertificate());
        identityProvider_dest.setDisplayName(identityProvider_source.getDisplayName());
        identityProvider_dest.setEnable(identityProvider_source.isIsEnable());
        identityProvider_dest.setFederationHub(identityProvider_source.isIsFederationHub());
        identityProvider_dest.setHomeRealmId(identityProvider_source.getHomeRealmId());
        identityProvider_dest.setPrimary(identityProvider_source.isIsPrimary());

        buildClaimConfig(identityProvider_source, identityProvider_dest);

        buildFederatedAuthenticatorConfig(identityProvider_source, identityProvider_dest);

        buildIdpProperties(identityProvider_source, identityProvider_dest);

        buildJustInTimeProvisionConfig(identityProvider_source);

        buildPermissionAndRoleConfig(identityProvider_source, identityProvider_dest);

        List<org.wso2.carbon.solution.model.iam.idp.ProvisioningConnectorConfig> provisioningConnectorConfigs_source =
                identityProvider_source
                        .getProvisioningConnectorConfigs();
        if (provisioningConnectorConfigs_source != null) {
            List<ProvisioningConnectorConfig> provisioningConnectorConfigs_dest = new ArrayList<>();
            for (org.wso2.carbon.solution.model.iam.idp.ProvisioningConnectorConfig
                    provisioningConnectorConfig_source : provisioningConnectorConfigs_source) {
                ProvisioningConnectorConfig provisioningConnectorConfig_dest = new ProvisioningConnectorConfig();
                provisioningConnectorConfigs_dest.add(provisioningConnectorConfig_dest);

                provisioningConnectorConfig_dest.setName(provisioningConnectorConfig_source.getName());
                provisioningConnectorConfig_dest.setBlocking(provisioningConnectorConfig_source.isIsBlocking());
                provisioningConnectorConfig_dest.setEnabled(provisioningConnectorConfig_source.isIsEnabled());
                provisioningConnectorConfig_dest.setRulesEnabled(provisioningConnectorConfig_source.isIsRulesEnabled());
                provisioningConnectorConfig_dest.setValid(provisioningConnectorConfig_source.isIsValid());

                List<ProvisioningProperty> provisioningProperties_source = provisioningConnectorConfig_source
                        .getProvisioningProperties();
                if (provisioningProperties_source != null) {
                    List<Property> provisioningProperties_dest = new ArrayList<>();
                    for (ProvisioningProperty provisioningProperty_source : provisioningProperties_source) {
                        Property provisioningProperty_dest = new Property();
                        provisioningProperty_dest.setName(provisioningProperty_source.getName());
                        provisioningProperty_dest.setValue(provisioningProperty_source.getValue());
                        provisioningProperty_dest.setType(provisioningProperty_source.getType());
                        provisioningProperty_dest.setRequired(provisioningProperty_source.isIsRequired());
                        provisioningProperty_dest.setDisplayOrder(provisioningProperty_source.getDisplayOrder());
                        provisioningProperty_dest.setAdvanced(provisioningProperty_source.isIsAdvanced());
                        provisioningProperty_dest.setDefaultValue(provisioningProperty_source.getName());
                        provisioningProperty_dest.setDescription(provisioningProperty_source.getName());
                        provisioningProperty_dest.setDisplayName(provisioningProperty_source.getName());
                    }
                    provisioningConnectorConfig_dest.setProvisioningProperties(provisioningProperties_dest.toArray
                            (new Property[provisioningProperties_dest.size()]));
                }
            }
        }

        identityProvider_dest.setProvisioningRole(identityProvider_source.getProvisioningRole());
    }

    public IdentityProvider buildServiceProvider(org.wso2.carbon.solution.model.iam.idp.IdentityProvider
                                                         identityProvider_source) {
        IdentityProvider identityProvider_dest = new IdentityProvider();
        buildIdentityProvider(identityProvider_source, identityProvider_dest);
        return identityProvider_dest;
    }

    private void buildPermissionAndRoleConfig(org.wso2.carbon.solution.model.iam.idp.IdentityProvider
                                                      identityProvider_source,
                                              IdentityProvider identityProvider_dest) {
        org.wso2.carbon.solution.model.iam.idp.PermissionsAndRoleConfig permissionAndRoleConfig_source =
                identityProvider_source.getPermissionsAndRoleConfig();
        if (permissionAndRoleConfig_source != null) {
            PermissionsAndRoleConfig permissionsAndRoleConfig_dest = new PermissionsAndRoleConfig();
            identityProvider_dest.setPermissionAndRoleConfig(permissionsAndRoleConfig_dest);

            List<String> idpRoles_source = permissionAndRoleConfig_source.getIdpRoles();
            if (idpRoles_source != null) {
                permissionsAndRoleConfig_dest.setIdpRoles(idpRoles_source.toArray(new String[idpRoles_source.size()]));
            }

            List<Permission> permissions_source = permissionAndRoleConfig_source.getPermissions();
            if (permissions_source != null) {
                List<ApplicationPermission> applicationPermissions_dest = new ArrayList<>();
                for (Permission applicationPermission_source : permissions_source) {
                    ApplicationPermission applicationPermission_dest = new ApplicationPermission();
                    applicationPermissions_dest.add(applicationPermission_dest);

                    applicationPermission_dest.setValue(applicationPermission_source.getValue());
                }
                permissionsAndRoleConfig_dest.setPermissions(
                        applicationPermissions_dest.toArray(new ApplicationPermission[applicationPermissions_dest
                                .size()]));
            }

            List<org.wso2.carbon.solution.model.iam.idp.RoleMapping> roleMappings_source =
                    permissionAndRoleConfig_source
                            .getRoleMappings();
            if (roleMappings_source != null) {
                List<RoleMapping> roleMappings_dest = new ArrayList<>();
                for (org.wso2.carbon.solution.model.iam.idp.RoleMapping roleMapping_source : roleMappings_source) {
                    RoleMapping roleMapping_dest = new RoleMapping();
                    roleMappings_dest.add(roleMapping_dest);
                    roleMapping_dest.setRemoteRole(roleMapping_source.getRemoteRole());

                    org.wso2.carbon.solution.model.iam.idp.LocalRole localRole_source = roleMapping_source
                            .getLocalRole();
                    if (localRole_source != null) {
                        LocalRole localRole_dest = new LocalRole();
                        roleMapping_dest.setLocalRole(localRole_dest);

                        localRole_dest.setLocalRoleName(localRole_source.getLocalRoleName());
                        localRole_dest.setUserStoreId(localRole_source.getUserStoreId());
                    }
                }
                permissionsAndRoleConfig_dest
                        .setRoleMappings(roleMappings_dest.toArray(new RoleMapping[roleMappings_dest.size()]));
            }
        }
    }

    private void buildJustInTimeProvisionConfig(org.wso2.carbon.solution.model.iam.idp.IdentityProvider
                                                        identityProvider_source) {
        org.wso2.carbon.solution.model.iam.idp.JustInTimeProvisioningConfig justInTimeProvisioningConfig_source =
                identityProvider_source
                        .getJustInTimeProvisioningConfig();
        if (justInTimeProvisioningConfig_source != null) {
            JustInTimeProvisioningConfig justInTimeProvisioningConfig_dest = new JustInTimeProvisioningConfig();
            justInTimeProvisioningConfig_dest
                    .setUserStoreClaimUri(justInTimeProvisioningConfig_source.getUserStoreClaimUri());
            justInTimeProvisioningConfig_dest
                    .setProvisioningUserStore(justInTimeProvisioningConfig_source.getProvisioningUserStore());
            justInTimeProvisioningConfig_dest.setDumbMode(justInTimeProvisioningConfig_source.isIsDumbMode());
            justInTimeProvisioningConfig_dest
                    .setProvisioningEnabled(justInTimeProvisioningConfig_source.isIsProvisioningEnabled());
        }
    }

    private void buildClaimConfig(org.wso2.carbon.solution.model.iam.idp.IdentityProvider identityProvider_source,
                                  IdentityProvider identityProvider_dest) {
        org.wso2.carbon.solution.model.iam.idp.ClaimConfig claimConfig_source = identityProvider_source
                .getClaimConfig();
        if (claimConfig_source != null) {
            ClaimConfig claimConfig_dest = new ClaimConfig();
            identityProvider_dest.setClaimConfig(claimConfig_dest);

            claimConfig_dest.setLocalClaimDialect(claimConfig_source.isIsLocalClaimDialect());
            claimConfig_dest.setAlwaysSendMappedLocalSubjectId(claimConfig_source.isIsAlwaysSendMappedLocalSubjectId());
            claimConfig_dest.setUserClaimURI(claimConfig_source.getUserClaimURI());
            claimConfig_dest.setRoleClaimURI(claimConfig_source.getRoleClaimURI());

            List<org.wso2.carbon.solution.model.iam.idp.ClaimMapping> claimMappings_source = claimConfig_source
                    .getClaimMappings();
            if (claimMappings_source != null) {
                List<ClaimMapping> claimMappings_dest = new ArrayList<>();
                for (org.wso2.carbon.solution.model.iam.idp.ClaimMapping claimMapping_source : claimMappings_source) {
                    ClaimMapping claimMapping_dest = new ClaimMapping();
                    claimMappings_dest.add(claimMapping_dest);

                    buildClaimMapping(claimMapping_source, claimMapping_dest);
                }
            }

            List<IdpClaim> idpClaims_source = claimConfig_source.getIdpClaims();
            if (idpClaims_source != null) {
                List<Claim> idpClaims_dest = new ArrayList<>();
                for (IdpClaim idpClaim_source : idpClaims_source) {
                    Claim idpClaim_dest = new Claim();
                    idpClaims_dest.add(idpClaim_dest);

                    idpClaim_dest.setClaimId(idpClaim_source.getClaimId());
                    idpClaim_dest.setClaimUri(idpClaim_source.getClaimUri());
                }
                claimConfig_dest.setIdpClaims(idpClaims_dest.toArray(new Claim[idpClaims_dest.size()]));
            }
        }
    }

    private void buildFederatedAuthenticatorConfig(org.wso2.carbon.solution.model.iam.idp.IdentityProvider
                                                           identityProvider_source,
                                                   IdentityProvider identityProvider_dest) {
        List<org.wso2.carbon.solution.model.iam.idp.FederatedAuthenticatorConfig>

                federatedAuthenticatorConfigs_source
                = identityProvider_source
                .getFederatedAuthenticatorConfigs();
        if (federatedAuthenticatorConfigs_source != null) {
            List<FederatedAuthenticatorConfig> federatedAuthenticatorConfigs_dest = new ArrayList<>();
            for (org.wso2.carbon.solution.model.iam.idp.FederatedAuthenticatorConfig
                    federatedAuthenticatorConfig_source :
                    federatedAuthenticatorConfigs_source) {
                FederatedAuthenticatorConfig federatedAuthenticatorConfig_dest = new FederatedAuthenticatorConfig();
                federatedAuthenticatorConfigs_dest.add(federatedAuthenticatorConfig_dest);

                federatedAuthenticatorConfig_dest.setDisplayName(federatedAuthenticatorConfig_source.getDisplayName());
                federatedAuthenticatorConfig_dest.setName(federatedAuthenticatorConfig_source.getName());
                federatedAuthenticatorConfig_dest.setValid(federatedAuthenticatorConfig_source.isIsValid());
                federatedAuthenticatorConfig_dest.setEnabled(federatedAuthenticatorConfig_source.isIsEnabled());

                List<AuthenticatorProperty> authenticatorProperties_source = federatedAuthenticatorConfig_source
                        .getAuthenticatorProperties();
                if (authenticatorProperties_source != null) {
                    List<Property> authenticatorProperties_dest = new
                            ArrayList<>();
                    for (AuthenticatorProperty authenticatorProperty_source :
                            authenticatorProperties_source) {
                        Property federatedAuthenticatorProperty_dest = new Property();
                        authenticatorProperties_dest.add(federatedAuthenticatorProperty_dest);

                        federatedAuthenticatorProperty_dest.setName(authenticatorProperty_source.getName());
                        federatedAuthenticatorProperty_dest
                                .setDisplayName(authenticatorProperty_source.getDisplayName());
                        federatedAuthenticatorProperty_dest.setAdvanced(authenticatorProperty_source.isIsAdvanced());
                        federatedAuthenticatorProperty_dest
                                .setConfidential(authenticatorProperty_source.isIsConfidential());
                        federatedAuthenticatorProperty_dest
                                .setDefaultValue(authenticatorProperty_source.getDefaultValue());
                        federatedAuthenticatorProperty_dest
                                .setDescription(authenticatorProperty_source.getDescription());
                        federatedAuthenticatorProperty_dest
                                .setDisplayOrder(authenticatorProperty_source.getDisplayOrder());
                        federatedAuthenticatorProperty_dest.setRequired(authenticatorProperty_source.isIsRequired());
                        federatedAuthenticatorProperty_dest.setType(authenticatorProperty_source.getType());
                        federatedAuthenticatorProperty_dest.setValue(authenticatorProperty_source.getValue());
                    }

                    federatedAuthenticatorConfig_dest.setProperties(
                            authenticatorProperties_dest.toArray(new Property[authenticatorProperties_dest.size()]));
                }

                if (federatedAuthenticatorConfig_source.isIsDefault()) {
                    identityProvider_dest.setDefaultAuthenticatorConfig(federatedAuthenticatorConfig_dest);
                }
            }
            identityProvider_dest.setFederatedAuthenticatorConfigs(federatedAuthenticatorConfigs_dest.toArray(
                    new FederatedAuthenticatorConfig[federatedAuthenticatorConfigs_dest.size()]));
        }
    }

    private void buildIdpProperties(org.wso2.carbon.solution.model.iam.idp.IdentityProvider identityProvider_source,
                                    IdentityProvider identityProvider_dest) {
        List<IdpProperty> idpProperties_source = identityProvider_source.getIdpProperties();
        if (idpProperties_source != null) {
            List<IdentityProviderProperty> identityProviderProperties_dest = new ArrayList<>();
            for (IdpProperty idpProperty_source : idpProperties_source) {
                IdentityProviderProperty identityProviderProperty_dest = new IdentityProviderProperty();
                identityProviderProperties_dest.add(identityProviderProperty_dest);

                identityProviderProperty_dest.setName(idpProperty_source.getName());
                identityProviderProperty_dest.setValue(idpProperty_source.getValue());
                identityProviderProperty_dest.setDisplayName(idpProperty_source.getDisplayName());
            }
            identityProvider_dest.setIdpProperties(identityProviderProperties_dest.toArray(
                    new IdentityProviderProperty[identityProviderProperties_dest.size()]));
        }
    }


    private void buildClaimMapping(org.wso2.carbon.solution.model.iam.idp.ClaimMapping claimMapping_source,
                                   ClaimMapping claimMapping_dest) {
        claimMapping_dest.setRequested(claimMapping_source.isIsRequested());
        claimMapping_dest.setMandatory(claimMapping_source.isIsMandatory());
        claimMapping_dest.setDefaultValue(claimMapping_source.getDefaultValue());

        LocalClaim localClaim_source = claimMapping_source.getLocalClaim();
        Claim localClaim_dest = new Claim();
        claimMapping_dest.setLocalClaim(localClaim_dest);

        localClaim_dest.setClaimId(localClaim_source.getClaimId());
        localClaim_dest.setClaimUri(localClaim_source.getClaimUri());

        RemoteClaim remoteClaim_source = claimMapping_source.getRemoteClaim();
        Claim remoteClaim_dest = new Claim();
        claimMapping_dest.setRemoteClaim(remoteClaim_dest);

        remoteClaim_source.setClaimId(localClaim_source.getClaimId());
        remoteClaim_source.setClaimUri(localClaim_source.getClaimUri());
    }
}
