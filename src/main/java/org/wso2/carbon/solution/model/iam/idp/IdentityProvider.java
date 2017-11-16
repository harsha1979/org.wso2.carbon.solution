package org.wso2.carbon.solution.model.iam.idp;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class IdentityProvider implements Serializable {

    private final static long serialVersionUID = -8468520571812021641L;
    private String alias;
    private String certificate;
    private String displayName;
    private boolean isEnable;
    private boolean isPrimary;
    private boolean isFederationHub;
    private String homeRealmId;
    private String identityProviderDescription;
    private String identityProviderName;
    private String provisioningRole;
    private ClaimConfig claimConfig;
    private List<FederatedAuthenticatorConfig> federatedAuthenticatorConfigs
            = new ArrayList<FederatedAuthenticatorConfig>();
    private List<IdpProperty> idpProperties = new ArrayList<IdpProperty>();
    private JustInTimeProvisioningConfig justInTimeProvisioningConfig;
    private PermissionsAndRoleConfig permissionsAndRoleConfig;
    private List<ProvisioningConnectorConfig> provisioningConnectorConfigs
            = new ArrayList<ProvisioningConnectorConfig>();

    /**
     * No args constructor for use in serialization
     */
    public IdentityProvider() {
    }

    /**
     * @param homeRealmId
     * @param claimConfig
     * @param isEnable
     * @param isPrimary
     * @param alias
     * @param identityProviderName
     * @param provisioningConnectorConfigs
     * @param isFederationHub
     * @param idpProperties
     * @param permissionsAndRoleConfig
     * @param justInTimeProvisioningConfig
     * @param certificate
     * @param identityProviderDescription
     * @param federatedAuthenticatorConfigs
     * @param provisioningRole
     * @param displayName
     */
    public IdentityProvider(String alias,
                            String certificate,
                            String displayName,
                            boolean isEnable,
                            boolean isPrimary,
                            boolean isFederationHub,
                            String homeRealmId,
                            String identityProviderDescription,
                            String identityProviderName,
                            String provisioningRole,
                            ClaimConfig claimConfig,
                            List<FederatedAuthenticatorConfig> federatedAuthenticatorConfigs,
                            List<IdpProperty> idpProperties,
                            JustInTimeProvisioningConfig justInTimeProvisioningConfig,
                            PermissionsAndRoleConfig permissionsAndRoleConfig,
                            List<ProvisioningConnectorConfig> provisioningConnectorConfigs) {
        super();
        this.alias = alias;
        this.certificate = certificate;
        this.displayName = displayName;
        this.isEnable = isEnable;
        this.isPrimary = isPrimary;
        this.isFederationHub = isFederationHub;
        this.homeRealmId = homeRealmId;
        this.identityProviderDescription = identityProviderDescription;
        this.identityProviderName = identityProviderName;
        this.provisioningRole = provisioningRole;
        this.claimConfig = claimConfig;
        this.federatedAuthenticatorConfigs = federatedAuthenticatorConfigs;
        this.idpProperties = idpProperties;
        this.justInTimeProvisioningConfig = justInTimeProvisioningConfig;
        this.permissionsAndRoleConfig = permissionsAndRoleConfig;
        this.provisioningConnectorConfigs = provisioningConnectorConfigs;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof IdentityProvider) == false) {
            return false;
        }
        IdentityProvider rhs = ((IdentityProvider) other);
        return new EqualsBuilder().append(claimConfig, rhs.claimConfig).append(homeRealmId, rhs.homeRealmId)
                .append(isEnable, rhs.isEnable).append(isPrimary, rhs.isPrimary).append(alias, rhs.alias)
                .append(identityProviderName, rhs.identityProviderName)
                .append(provisioningConnectorConfigs, rhs.provisioningConnectorConfigs)
                .append(isFederationHub, rhs.isFederationHub).append(idpProperties, rhs.idpProperties)
                .append(permissionsAndRoleConfig, rhs.permissionsAndRoleConfig)
                .append(justInTimeProvisioningConfig, rhs.justInTimeProvisioningConfig)
                .append(certificate, rhs.certificate)
                .append(identityProviderDescription, rhs.identityProviderDescription)
                .append(federatedAuthenticatorConfigs, rhs.federatedAuthenticatorConfigs)
                .append(provisioningRole, rhs.provisioningRole).append(displayName, rhs.displayName).isEquals();
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public ClaimConfig getClaimConfig() {
        return claimConfig;
    }

    public void setClaimConfig(ClaimConfig claimConfig) {
        this.claimConfig = claimConfig;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<FederatedAuthenticatorConfig> getFederatedAuthenticatorConfigs() {
        return federatedAuthenticatorConfigs;
    }

    public void setFederatedAuthenticatorConfigs(List<FederatedAuthenticatorConfig> federatedAuthenticatorConfigs) {
        this.federatedAuthenticatorConfigs = federatedAuthenticatorConfigs;
    }

    public String getHomeRealmId() {
        return homeRealmId;
    }

    public void setHomeRealmId(String homeRealmId) {
        this.homeRealmId = homeRealmId;
    }

    public String getIdentityProviderDescription() {
        return identityProviderDescription;
    }

    public void setIdentityProviderDescription(String identityProviderDescription) {
        this.identityProviderDescription = identityProviderDescription;
    }

    public String getIdentityProviderName() {
        return identityProviderName;
    }

    public void setIdentityProviderName(String identityProviderName) {
        this.identityProviderName = identityProviderName;
    }

    public List<IdpProperty> getIdpProperties() {
        return idpProperties;
    }

    public void setIdpProperties(List<IdpProperty> idpProperties) {
        this.idpProperties = idpProperties;
    }

    public JustInTimeProvisioningConfig getJustInTimeProvisioningConfig() {
        return justInTimeProvisioningConfig;
    }

    public void setJustInTimeProvisioningConfig(JustInTimeProvisioningConfig justInTimeProvisioningConfig) {
        this.justInTimeProvisioningConfig = justInTimeProvisioningConfig;
    }

    public PermissionsAndRoleConfig getPermissionsAndRoleConfig() {
        return permissionsAndRoleConfig;
    }

    public void setPermissionsAndRoleConfig(PermissionsAndRoleConfig permissionsAndRoleConfig) {
        this.permissionsAndRoleConfig = permissionsAndRoleConfig;
    }

    public List<ProvisioningConnectorConfig> getProvisioningConnectorConfigs() {
        return provisioningConnectorConfigs;
    }

    public void setProvisioningConnectorConfigs(List<ProvisioningConnectorConfig> provisioningConnectorConfigs) {
        this.provisioningConnectorConfigs = provisioningConnectorConfigs;
    }

    public String getProvisioningRole() {
        return provisioningRole;
    }

    public void setProvisioningRole(String provisioningRole) {
        this.provisioningRole = provisioningRole;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(claimConfig).append(homeRealmId).append(isEnable).append(isPrimary)
                .append(alias).append(identityProviderName).append(provisioningConnectorConfigs).append(isFederationHub)
                .append(idpProperties).append(permissionsAndRoleConfig).append(justInTimeProvisioningConfig)
                .append(certificate).append(identityProviderDescription).append(federatedAuthenticatorConfigs)
                .append(provisioningRole).append(displayName).toHashCode();
    }

    public boolean isIsEnable() {
        return isEnable;
    }

    public void setIsEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    public boolean isIsFederationHub() {
        return isFederationHub;
    }

    public void setIsFederationHub(boolean isFederationHub) {
        this.isFederationHub = isFederationHub;
    }

    public boolean isIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("alias", alias).append("certificate", certificate)
                .append("displayName", displayName).append("isEnable", isEnable).append("isPrimary", isPrimary)
                .append("isFederationHub", isFederationHub).append("homeRealmId", homeRealmId)
                .append("identityProviderDescription", identityProviderDescription)
                .append("identityProviderName", identityProviderName).append("provisioningRole", provisioningRole)
                .append("claimConfig", claimConfig)
                .append("federatedAuthenticatorConfigs", federatedAuthenticatorConfigs)
                .append("idpProperties", idpProperties)
                .append("justInTimeProvisioningConfig", justInTimeProvisioningConfig)
                .append("permissionsAndRoleConfig", permissionsAndRoleConfig)
                .append("provisioningConnectorConfigs", provisioningConnectorConfigs).toString();
    }
}
