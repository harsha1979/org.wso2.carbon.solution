package org.wso2.carbon.solution.model.iam.sp;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class RemoteClaim implements Serializable {

    private final static long serialVersionUID = -7532498370200737555L;
    private int claimId;
    private String claimUri;

    /**
     * No args constructor for use in serialization
     */
    public RemoteClaim() {
    }

    /**
     * @param claimId
     * @param claimUri
     */
    public RemoteClaim(int claimId, String claimUri) {
        super();
        this.claimId = claimId;
        this.claimUri = claimUri;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof RemoteClaim) == false) {
            return false;
        }
        RemoteClaim rhs = ((RemoteClaim) other);
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
