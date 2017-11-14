
package org.wso2.carbon.solution.model.iam.idp;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class LocalRole implements Serializable
{

    private String localRoleName;
    private String userStoreId;
    private final static long serialVersionUID = 3560840404566989664L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public LocalRole() {
    }

    /**
     * 
     * @param localRoleName
     * @param userStoreId
     */
    public LocalRole(String localRoleName, String userStoreId) {
        super();
        this.localRoleName = localRoleName;
        this.userStoreId = userStoreId;
    }

    public String getLocalRoleName() {
        return localRoleName;
    }

    public void setLocalRoleName(String localRoleName) {
        this.localRoleName = localRoleName;
    }

    public String getUserStoreId() {
        return userStoreId;
    }

    public void setUserStoreId(String userStoreId) {
        this.userStoreId = userStoreId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("localRoleName", localRoleName).append("userStoreId", userStoreId).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(localRoleName).append(userStoreId).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof LocalRole) == false) {
            return false;
        }
        LocalRole rhs = ((LocalRole) other);
        return new EqualsBuilder().append(localRoleName, rhs.localRoleName).append(userStoreId, rhs.userStoreId).isEquals();
    }

}
