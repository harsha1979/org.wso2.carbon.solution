package org.wso2.carbon.solution.model.iam.sp;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClaimConfiguration implements Serializable {

    private final static long serialVersionUID = -7923258236641834202L;
    private boolean alwaysSendMappedLocalSubjectId;
    private boolean localClaimDialect;
    private String roleClaimURI;
    private String userClaimURI;
    private List<ClaimMapping> claimMappings = new ArrayList<ClaimMapping>();

    /**
     * No args constructor for use in serialization
     */
    public ClaimConfiguration() {
    }

    /**
     * @param roleClaimURI
     * @param localClaimDialect
     * @param alwaysSendMappedLocalSubjectId
     * @param userClaimURI
     * @param claimMappings
     */
    public ClaimConfiguration(boolean alwaysSendMappedLocalSubjectId,
                              boolean localClaimDialect,
                              String roleClaimURI,
                              String userClaimURI,
                              List<ClaimMapping> claimMappings) {
        super();
        this.alwaysSendMappedLocalSubjectId = alwaysSendMappedLocalSubjectId;
        this.localClaimDialect = localClaimDialect;
        this.roleClaimURI = roleClaimURI;
        this.userClaimURI = userClaimURI;
        this.claimMappings = claimMappings;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ClaimConfiguration) == false) {
            return false;
        }
        ClaimConfiguration rhs = ((ClaimConfiguration) other);
        return new EqualsBuilder().append(roleClaimURI, rhs.roleClaimURI)
                .append(localClaimDialect, rhs.localClaimDialect)
                .append(alwaysSendMappedLocalSubjectId, rhs.alwaysSendMappedLocalSubjectId)
                .append(userClaimURI, rhs.userClaimURI).append(claimMappings, rhs.claimMappings).isEquals();
    }

    public List<ClaimMapping> getClaimMappings() {
        return claimMappings;
    }

    public void setClaimMappings(List<ClaimMapping> claimMappings) {
        this.claimMappings = claimMappings;
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

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(roleClaimURI).append(localClaimDialect)
                .append(alwaysSendMappedLocalSubjectId).append(userClaimURI).append(claimMappings).toHashCode();
    }

    public boolean isAlwaysSendMappedLocalSubjectId() {
        return alwaysSendMappedLocalSubjectId;
    }

    public void setAlwaysSendMappedLocalSubjectId(boolean alwaysSendMappedLocalSubjectId) {
        this.alwaysSendMappedLocalSubjectId = alwaysSendMappedLocalSubjectId;
    }

    public boolean isLocalClaimDialect() {
        return localClaimDialect;
    }

    public void setLocalClaimDialect(boolean localClaimDialect) {
        this.localClaimDialect = localClaimDialect;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("alwaysSendMappedLocalSubjectId", alwaysSendMappedLocalSubjectId)
                .append("localClaimDialect", localClaimDialect).append("roleClaimURI", roleClaimURI)
                .append("userClaimURI", userClaimURI).append("claimMappings", claimMappings).toString();
    }
}
