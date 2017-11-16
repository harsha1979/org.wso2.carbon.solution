package org.wso2.carbon.solution.model.iam.idp;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class JustInTimeProvisioningConfig implements Serializable {

    private final static long serialVersionUID = -5569797892798439762L;
    private boolean isDumbMode;
    private boolean isProvisioningEnabled;
    private String provisioningUserStore;
    private String userStoreClaimUri;

    /**
     * No args constructor for use in serialization
     */
    public JustInTimeProvisioningConfig() {
    }

    /**
     * @param userStoreClaimUri
     * @param provisioningUserStore
     * @param isProvisioningEnabled
     * @param isDumbMode
     */
    public JustInTimeProvisioningConfig(boolean isDumbMode,
                                        boolean isProvisioningEnabled,
                                        String provisioningUserStore,
                                        String userStoreClaimUri) {
        super();
        this.isDumbMode = isDumbMode;
        this.isProvisioningEnabled = isProvisioningEnabled;
        this.provisioningUserStore = provisioningUserStore;
        this.userStoreClaimUri = userStoreClaimUri;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof JustInTimeProvisioningConfig) == false) {
            return false;
        }
        JustInTimeProvisioningConfig rhs = ((JustInTimeProvisioningConfig) other);
        return new EqualsBuilder().append(userStoreClaimUri, rhs.userStoreClaimUri)
                .append(provisioningUserStore, rhs.provisioningUserStore)
                .append(isProvisioningEnabled, rhs.isProvisioningEnabled).append(isDumbMode, rhs.isDumbMode).isEquals();
    }

    public String getProvisioningUserStore() {
        return provisioningUserStore;
    }

    public void setProvisioningUserStore(String provisioningUserStore) {
        this.provisioningUserStore = provisioningUserStore;
    }

    public String getUserStoreClaimUri() {
        return userStoreClaimUri;
    }

    public void setUserStoreClaimUri(String userStoreClaimUri) {
        this.userStoreClaimUri = userStoreClaimUri;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(userStoreClaimUri).append(provisioningUserStore)
                .append(isProvisioningEnabled).append(isDumbMode).toHashCode();
    }

    public boolean isIsDumbMode() {
        return isDumbMode;
    }

    public void setIsDumbMode(boolean isDumbMode) {
        this.isDumbMode = isDumbMode;
    }

    public boolean isIsProvisioningEnabled() {
        return isProvisioningEnabled;
    }

    public void setIsProvisioningEnabled(boolean isProvisioningEnabled) {
        this.isProvisioningEnabled = isProvisioningEnabled;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("isDumbMode", isDumbMode)
                .append("isProvisioningEnabled", isProvisioningEnabled)
                .append("provisioningUserStore", provisioningUserStore).append("userStoreClaimUri", userStoreClaimUri)
                .toString();
    }
}
