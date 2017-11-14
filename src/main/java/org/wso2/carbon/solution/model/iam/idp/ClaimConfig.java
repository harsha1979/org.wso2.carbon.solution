
package org.wso2.carbon.solution.model.iam.idp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ClaimConfig implements Serializable
{

    private boolean isAlwaysSendMappedLocalSubjectId;
    private boolean isLocalClaimDialect;
    private String roleClaimURI;
    private String userClaimURI;
    private List<ClaimMapping> claimMappings = new ArrayList<ClaimMapping>();
    private List<IdpClaim> idpClaims = new ArrayList<IdpClaim>();
    private final static long serialVersionUID = 3106405509957807929L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ClaimConfig() {
    }

    /**
     * 
     * @param roleClaimURI
     * @param isLocalClaimDialect
     * @param userClaimURI
     * @param isAlwaysSendMappedLocalSubjectId
     * @param claimMappings
     * @param idpClaims
     */
    public ClaimConfig(boolean isAlwaysSendMappedLocalSubjectId, boolean isLocalClaimDialect, String roleClaimURI, String userClaimURI, List<ClaimMapping> claimMappings, List<IdpClaim> idpClaims) {
        super();
        this.isAlwaysSendMappedLocalSubjectId = isAlwaysSendMappedLocalSubjectId;
        this.isLocalClaimDialect = isLocalClaimDialect;
        this.roleClaimURI = roleClaimURI;
        this.userClaimURI = userClaimURI;
        this.claimMappings = claimMappings;
        this.idpClaims = idpClaims;
    }

    public boolean isIsAlwaysSendMappedLocalSubjectId() {
        return isAlwaysSendMappedLocalSubjectId;
    }

    public void setIsAlwaysSendMappedLocalSubjectId(boolean isAlwaysSendMappedLocalSubjectId) {
        this.isAlwaysSendMappedLocalSubjectId = isAlwaysSendMappedLocalSubjectId;
    }

    public boolean isIsLocalClaimDialect() {
        return isLocalClaimDialect;
    }

    public void setIsLocalClaimDialect(boolean isLocalClaimDialect) {
        this.isLocalClaimDialect = isLocalClaimDialect;
    }

    public String getRoleClaimURI() {
        return roleClaimURI;
    }

    public void setRoleClaimURI(String roleClaimURI) {
        this.roleClaimURI = roleClaimURI;
    }

    public String getUserClaimURI() {
        return userClaimURI;
    }

    public void setUserClaimURI(String userClaimURI) {
        this.userClaimURI = userClaimURI;
    }

    public List<ClaimMapping> getClaimMappings() {
        return claimMappings;
    }

    public void setClaimMappings(List<ClaimMapping> claimMappings) {
        this.claimMappings = claimMappings;
    }

    public List<IdpClaim> getIdpClaims() {
        return idpClaims;
    }

    public void setIdpClaims(List<IdpClaim> idpClaims) {
        this.idpClaims = idpClaims;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("isAlwaysSendMappedLocalSubjectId", isAlwaysSendMappedLocalSubjectId).append("isLocalClaimDialect", isLocalClaimDialect).append("roleClaimURI", roleClaimURI).append("userClaimURI", userClaimURI).append("claimMappings", claimMappings).append("idpClaims", idpClaims).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(roleClaimURI).append(isLocalClaimDialect).append(userClaimURI).append(isAlwaysSendMappedLocalSubjectId).append(claimMappings).append(idpClaims).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ClaimConfig) == false) {
            return false;
        }
        ClaimConfig rhs = ((ClaimConfig) other);
        return new EqualsBuilder().append(roleClaimURI, rhs.roleClaimURI).append(isLocalClaimDialect, rhs.isLocalClaimDialect).append(userClaimURI, rhs.userClaimURI).append(isAlwaysSendMappedLocalSubjectId, rhs.isAlwaysSendMappedLocalSubjectId).append(claimMappings, rhs.claimMappings).append(idpClaims, rhs.idpClaims).isEquals();
    }

}
