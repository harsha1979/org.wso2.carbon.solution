package org.wso2.carbon.solution.model.iam.sp;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class LocalAuthenticatorConfig implements Serializable {

    private final static long serialVersionUID = -8465225941798259901L;
    private String name;
    private String displayName;

    /**
     * No args constructor for use in serialization
     */
    public LocalAuthenticatorConfig() {
    }

    /**
     * @param name
     */
    public LocalAuthenticatorConfig(String name) {
        super();
        this.name = name;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof LocalAuthenticatorConfig) == false) {
            return false;
        }
        LocalAuthenticatorConfig rhs = ((LocalAuthenticatorConfig) other);
        return new EqualsBuilder().append(name, rhs.name).isEquals();
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", name).toString();
    }
}
