package org.wso2.carbon.solution.model.iam.sp;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class ClaimMapping implements Serializable {

    private final static long serialVersionUID = -2669678425984861407L;
    private String defaultValue;
    private boolean isMandetory;
    private boolean isRequested;
    private LocalClaim localClaim;
    private RemoteClaim remoteClaim;

    /**
     * No args constructor for use in serialization
     */
    public ClaimMapping() {
    }

    /**
     * @param localClaim
     * @param remoteClaim
     * @param isMandetory
     * @param isRequested
     * @param defaultValue
     */
    public ClaimMapping(String defaultValue,
                        boolean isMandetory,
                        boolean isRequested,
                        LocalClaim localClaim,
                        RemoteClaim remoteClaim) {
        super();
        this.defaultValue = defaultValue;
        this.isMandetory = isMandetory;
        this.isRequested = isRequested;
        this.localClaim = localClaim;
        this.remoteClaim = remoteClaim;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ClaimMapping) == false) {
            return false;
        }
        ClaimMapping rhs = ((ClaimMapping) other);
        return new EqualsBuilder().append(localClaim, rhs.localClaim).append(remoteClaim, rhs.remoteClaim)
                .append(isMandetory, rhs.isMandetory).append(isRequested, rhs.isRequested)
                .append(defaultValue, rhs.defaultValue).isEquals();
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public LocalClaim getLocalClaim() {
        return localClaim;
    }

    public void setLocalClaim(LocalClaim localClaim) {
        this.localClaim = localClaim;
    }

    public RemoteClaim getRemoteClaim() {
        return remoteClaim;
    }

    public void setRemoteClaim(RemoteClaim remoteClaim) {
        this.remoteClaim = remoteClaim;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(localClaim).append(remoteClaim).append(isMandetory).append(isRequested)
                .append(defaultValue).toHashCode();
    }

    public boolean isIsMandetory() {
        return isMandetory;
    }

    public void setIsMandetory(boolean isMandetory) {
        this.isMandetory = isMandetory;
    }

    public boolean isIsRequested() {
        return isRequested;
    }

    public void setIsRequested(boolean isRequested) {
        this.isRequested = isRequested;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("defaultValue", defaultValue).append("isMandetory", isMandetory)
                .append("isRequested", isRequested).append("localClaim", localClaim).append("remoteClaim", remoteClaim)
                .toString();
    }
}
