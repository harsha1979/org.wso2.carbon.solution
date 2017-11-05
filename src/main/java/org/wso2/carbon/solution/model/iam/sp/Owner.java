package org.wso2.carbon.solution.model.iam.sp;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class Owner implements Serializable {

    private final static long serialVersionUID = 5332812493324837861L;
    private String tenantDomain;
    private String userName;
    private String userStoreDomain;

    /**
     * No args constructor for use in serialization
     */
    public Owner() {
    }

    /**
     * @param userStoreDomain
     * @param userName
     * @param tenantDomain
     */
    public Owner(String tenantDomain, String userName, String userStoreDomain) {
        super();
        this.tenantDomain = tenantDomain;
        this.userName = userName;
        this.userStoreDomain = userStoreDomain;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Owner) == false) {
            return false;
        }
        Owner rhs = ((Owner) other);
        return new EqualsBuilder().append(userStoreDomain, rhs.userStoreDomain).append(userName, rhs.userName)
                .append(tenantDomain, rhs.tenantDomain).isEquals();
    }

    public String getTenantDomain() {
        return tenantDomain;
    }

    public void setTenantDomain(String tenantDomain) {
        this.tenantDomain = tenantDomain;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserStoreDomain() {
        return userStoreDomain;
    }

    public void setUserStoreDomain(String userStoreDomain) {
        this.userStoreDomain = userStoreDomain;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(userStoreDomain).append(userName).append(tenantDomain).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("tenantDomain", tenantDomain).append("userName", userName)
                .append("userStoreDomain", userStoreDomain).toString();
    }
}
