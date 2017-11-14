
package org.wso2.carbon.solution.model.iam.idp;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class RoleMapping implements Serializable
{

    private LocalRole localRole;
    private String remoteRole;
    private final static long serialVersionUID = 6630060929336825829L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RoleMapping() {
    }

    /**
     * 
     * @param localRole
     * @param remoteRole
     */
    public RoleMapping(LocalRole localRole, String remoteRole) {
        super();
        this.localRole = localRole;
        this.remoteRole = remoteRole;
    }

    public LocalRole getLocalRole() {
        return localRole;
    }

    public void setLocalRole(LocalRole localRole) {
        this.localRole = localRole;
    }

    public String getRemoteRole() {
        return remoteRole;
    }

    public void setRemoteRole(String remoteRole) {
        this.remoteRole = remoteRole;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("localRole", localRole).append("remoteRole", remoteRole).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(localRole).append(remoteRole).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof RoleMapping) == false) {
            return false;
        }
        RoleMapping rhs = ((RoleMapping) other);
        return new EqualsBuilder().append(localRole, rhs.localRole).append(remoteRole, rhs.remoteRole).isEquals();
    }

}
