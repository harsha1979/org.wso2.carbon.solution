package org.wso2.carbon.solution.model.iam.idp;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class IdpProperty implements Serializable {

    private final static long serialVersionUID = -4893940085212764445L;
    private String displayName;
    private String name;
    private String value;

    /**
     * No args constructor for use in serialization
     */
    public IdpProperty() {
    }

    /**
     * @param name
     * @param value
     * @param displayName
     */
    public IdpProperty(String displayName, String name, String value) {
        super();
        this.displayName = displayName;
        this.name = name;
        this.value = value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof IdpProperty) == false) {
            return false;
        }
        IdpProperty rhs = ((IdpProperty) other);
        return new EqualsBuilder().append(name, rhs.name).append(value, rhs.value).append(displayName, rhs.displayName)
                .isEquals();
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(value).append(displayName).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("displayName", displayName).append("name", name).append("value", value)
                .toString();
    }
}
