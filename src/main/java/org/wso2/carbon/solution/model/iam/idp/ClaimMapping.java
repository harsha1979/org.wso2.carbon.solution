
package org.wso2.carbon.solution.model.iam.idp;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ClaimMapping implements Serializable
{

    private boolean isMandatory;
    private String defaultValue;
    private boolean isRequested;
    private LocalClaim localClaim;
    private RemoteClaim remoteClaim;
    private final static long serialVersionUID = 5414261006867507023L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ClaimMapping() {
    }

    /**
     * 
     * @param localClaim
     * @param remoteClaim
     * @param isMandatory
     * @param isRequested
     * @param defaultValue
     */
    public ClaimMapping(boolean isMandatory, String defaultValue, boolean isRequested, LocalClaim localClaim, RemoteClaim remoteClaim) {
        super();
        this.isMandatory = isMandatory;
        this.defaultValue = defaultValue;
        this.isRequested = isRequested;
        this.localClaim = localClaim;
        this.remoteClaim = remoteClaim;
    }

    public boolean isIsMandatory() {
        return isMandatory;
    }

    public void setIsMandatory(boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isIsRequested() {
        return isRequested;
    }

    public void setIsRequested(boolean isRequested) {
        this.isRequested = isRequested;
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
    public String toString() {
        return new ToStringBuilder(this).append("isMandatory", isMandatory).append("defaultValue", defaultValue).append("isRequested", isRequested).append("localClaim", localClaim).append("remoteClaim", remoteClaim).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(localClaim).append(remoteClaim).append(isMandatory).append(isRequested).append(defaultValue).toHashCode();
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
        return new EqualsBuilder().append(localClaim, rhs.localClaim).append(remoteClaim, rhs.remoteClaim).append(isMandatory, rhs.isMandatory).append(isRequested, rhs.isRequested).append(defaultValue, rhs.defaultValue).isEquals();
    }

}
