package org.wso2.carbon.solution.model.iam.sp;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FederatedIdentityProvider implements Serializable {

    private final static long serialVersionUID = 5745988560322214686L;
    private String identityProviderName;
    private List<FederatedAuthenticatorConfig> federatedAuthenticatorConfigs
            = new ArrayList<FederatedAuthenticatorConfig>();

    /**
     * No args constructor for use in serialization
     */
    public FederatedIdentityProvider() {
    }

    /**
     * @param identityProviderName
     * @param federatedAuthenticatorConfigs
     */
    public FederatedIdentityProvider(String identityProviderName,
                                     List<FederatedAuthenticatorConfig> federatedAuthenticatorConfigs) {
        super();
        this.identityProviderName = identityProviderName;
        this.federatedAuthenticatorConfigs = federatedAuthenticatorConfigs;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof FederatedIdentityProvider) == false) {
            return false;
        }
        FederatedIdentityProvider rhs = ((FederatedIdentityProvider) other);
        return new EqualsBuilder().append(identityProviderName, rhs.identityProviderName)
                .append(federatedAuthenticatorConfigs, rhs.federatedAuthenticatorConfigs).isEquals();
    }

    public List<FederatedAuthenticatorConfig> getFederatedAuthenticatorConfigs() {
        return federatedAuthenticatorConfigs;
    }

    public void setFederatedAuthenticatorConfigs(List<FederatedAuthenticatorConfig> federatedAuthenticatorConfigs) {
        this.federatedAuthenticatorConfigs = federatedAuthenticatorConfigs;
    }

    public String getIdentityProviderName() {
        return identityProviderName;
    }

    public void setIdentityProviderName(String identityProviderName) {
        this.identityProviderName = identityProviderName;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(identityProviderName).append(federatedAuthenticatorConfigs).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("identityProviderName", identityProviderName)
                .append("federatedAuthenticatorConfigs", federatedAuthenticatorConfigs).toString();
    }
}
