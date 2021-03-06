package org.wso2.carbon.solution.model.iam.sp;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class LocalClaim implements Serializable {

    private final static long serialVersionUID = -9171297087831072887L;
    private int claimId;
    private String claimUri;

    /**
     * No args constructor for use in serialization
     */
    public LocalClaim() {
    }

    /**
     * @param claimId
     * @param claimUri
     */
    public LocalClaim(int claimId, String claimUri) {
        super();
        this.claimId = claimId;
        this.claimUri = claimUri;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof LocalClaim) == false) {
            return false;
        }
        LocalClaim rhs = ((LocalClaim) other);
        return new EqualsBuilder().append(claimId, rhs.claimId).append(claimUri, rhs.claimUri).isEquals();
    }

    public int getClaimId() {
        return claimId;
    }

    public void setClaimId(int claimId) {
        this.claimId = claimId;
    }

    public String getClaimUri() {
        return claimUri;
    }

    public void setClaimUri(String claimUri) {
        this.claimUri = claimUri;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(claimId).append(claimUri).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("claimId", claimId).append("claimUri", claimUri).toString();
    }
}
