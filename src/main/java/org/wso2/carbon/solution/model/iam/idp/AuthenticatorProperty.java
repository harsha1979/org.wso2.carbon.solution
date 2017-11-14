
package org.wso2.carbon.solution.model.iam.idp;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class AuthenticatorProperty implements Serializable
{

    private boolean isAdvanced;
    private boolean isConfidential;
    private String defaultValue;
    private String description;
    private String displayName;
    private int displayOrder;
    private String name;
    private boolean isRequired;
    private String type;
    private String value;
    private final static long serialVersionUID = -8298599732065999786L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AuthenticatorProperty() {
    }

    /**
     * 
     * @param isRequired
     * @param isConfidential
     * @param description
     * @param name
     * @param value
     * @param displayOrder
     * @param type
     * @param displayName
     * @param defaultValue
     * @param isAdvanced
     */
    public AuthenticatorProperty(boolean isAdvanced, boolean isConfidential, String defaultValue, String description, String displayName, int displayOrder, String name, boolean isRequired, String type, String value) {
        super();
        this.isAdvanced = isAdvanced;
        this.isConfidential = isConfidential;
        this.defaultValue = defaultValue;
        this.description = description;
        this.displayName = displayName;
        this.displayOrder = displayOrder;
        this.name = name;
        this.isRequired = isRequired;
        this.type = type;
        this.value = value;
    }

    public boolean isIsAdvanced() {
        return isAdvanced;
    }

    public void setIsAdvanced(boolean isAdvanced) {
        this.isAdvanced = isAdvanced;
    }

    public boolean isIsConfidential() {
        return isConfidential;
    }

    public void setIsConfidential(boolean isConfidential) {
        this.isConfidential = isConfidential;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsRequired() {
        return isRequired;
    }

    public void setIsRequired(boolean isRequired) {
        this.isRequired = isRequired;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("isAdvanced", isAdvanced).append("isConfidential", isConfidential).append("defaultValue", defaultValue).append("description", description).append("displayName", displayName).append("displayOrder", displayOrder).append("name", name).append("isRequired", isRequired).append("type", type).append("value", value).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(isRequired).append(isConfidential).append(description).append(name).append(value).append(displayOrder).append(type).append(displayName).append(defaultValue).append(isAdvanced).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof AuthenticatorProperty) == false) {
            return false;
        }
        AuthenticatorProperty rhs = ((AuthenticatorProperty) other);
        return new EqualsBuilder().append(isRequired, rhs.isRequired).append(isConfidential, rhs.isConfidential).append(description, rhs.description).append(name, rhs.name).append(value, rhs.value).append(displayOrder, rhs.displayOrder).append(type, rhs.type).append(displayName, rhs.displayName).append(defaultValue, rhs.defaultValue).append(isAdvanced, rhs.isAdvanced).isEquals();
    }

}
