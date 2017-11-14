
package org.wso2.carbon.solution.model.iam.idp;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class IdpClaim implements Serializable
{

    private int claimId;
    private String claimUri;
    private final static long serialVersionUID = 8097631168252853771L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public IdpClaim() {
    }

    /**
     * 
     * @param claimId
     * @param claimUri
     */
    public IdpClaim(int claimId, String claimUri) {
        super();
        this.claimId = claimId;
        this.claimUri = claimUri;
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
    public String toString() {
        return new ToStringBuilder(this).append("claimId", claimId).append("claimUri", claimUri).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(claimId).append(claimUri).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof IdpClaim) == false) {
            return false;
        }
        IdpClaim rhs = ((IdpClaim) other);
        return new EqualsBuilder().append(claimId, rhs.claimId).append(claimUri, rhs.claimUri).isEquals();
    }

}
