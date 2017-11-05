package org.wso2.carbon.solution.model.iam.sp;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class InboundProvisioningConfig implements Serializable {

    private final static long serialVersionUID = 1242040968133011645L;
    private boolean isDumbMode;
    private boolean isProvisioningEnabled;
    private String provisioningUserStore;

    /**
     * No args constructor for use in serialization
     */
    public InboundProvisioningConfig() {
    }

    /**
     * @param provisioningUserStore
     * @param isProvisioningEnabled
     * @param isDumbMode
     */
    public InboundProvisioningConfig(boolean isDumbMode, boolean isProvisioningEnabled, String provisioningUserStore) {
        super();
        this.isDumbMode = isDumbMode;
        this.isProvisioningEnabled = isProvisioningEnabled;
        this.provisioningUserStore = provisioningUserStore;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof InboundProvisioningConfig) == false) {
            return false;
        }
        InboundProvisioningConfig rhs = ((InboundProvisioningConfig) other);
        return new EqualsBuilder().append(provisioningUserStore, rhs.provisioningUserStore)
                .append(isProvisioningEnabled, rhs.isProvisioningEnabled).append(isDumbMode, rhs.isDumbMode).isEquals();
    }

    public String getProvisioningUserStore() {
        return provisioningUserStore;
    }

    public void setProvisioningUserStore(String provisioningUserStore) {
        this.provisioningUserStore = provisioningUserStore;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(provisioningUserStore).append(isProvisioningEnabled).append(isDumbMode)
                .toHashCode();
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
                .append("provisioningUserStore", provisioningUserStore).toString();
    }
}
