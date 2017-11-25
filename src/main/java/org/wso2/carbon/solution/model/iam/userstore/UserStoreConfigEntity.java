
package org.wso2.carbon.solution.model.iam.userstore;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class UserStoreConfigEntity implements Serializable
{

    private UserStore userStore;
    private final static long serialVersionUID = -9198734033681144878L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public UserStoreConfigEntity() {
    }

    /**
     * 
     * @param userStore
     */
    public UserStoreConfigEntity(UserStore userStore) {
        super();
        this.userStore = userStore;
    }

    public UserStore getUserStore() {
        return userStore;
    }

    public void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("userStore", userStore).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(userStore).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof UserStoreConfigEntity) == false) {
            return false;
        }
        UserStoreConfigEntity rhs = ((UserStoreConfigEntity) other);
        return new EqualsBuilder().append(userStore, rhs.userStore).isEquals();
    }

}
