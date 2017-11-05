package org.wso2.carbon.solution.model.iam.sp;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class Property implements Serializable {

    private final static long serialVersionUID = 4197991929060180120L;
    private String name;
    private String value;
    private String type;
    private boolean advanced;
    private boolean confidential;
    private String defaultValue;
    private String description;
    private String displayName;
    private int displayOrder;
    private boolean isRequired;

    /**
     * No args constructor for use in serialization
     */
    public Property() {
    }

    /**
     * @param isRequired
     * @param description
     * @param name
     * @param advanced
     * @param value
     * @param displayOrder
     * @param displayName
     * @param defaultValue
     * @param type
     * @param confidential
     */
    public Property(String name,
                    String value,
                    String type,
                    boolean advanced,
                    boolean confidential,
                    String defaultValue,
                    String description,
                    String displayName,
                    int displayOrder,
                    boolean isRequired) {
        super();
        this.name = name;
        this.value = value;
        this.type = type;
        this.advanced = advanced;
        this.confidential = confidential;
        this.defaultValue = defaultValue;
        this.description = description;
        this.displayName = displayName;
        this.displayOrder = displayOrder;
        this.isRequired = isRequired;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Property) == false) {
            return false;
        }
        Property rhs = ((Property) other);
        return new EqualsBuilder().append(isRequired, rhs.isRequired).append(description, rhs.description)
                .append(name, rhs.name).append(advanced, rhs.advanced).append(value, rhs.value)
                .append(displayOrder, rhs.displayOrder).append(displayName, rhs.displayName)
                .append(defaultValue, rhs.defaultValue).append(type, rhs.type).append(confidential, rhs.confidential)
                .isEquals();
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
    public int hashCode() {
        return new HashCodeBuilder().append(isRequired).append(description).append(name).append(advanced).append(value)
                .append(displayOrder).append(displayName).append(defaultValue).append(type).append(confidential)
                .toHashCode();
    }

    public boolean isAdvanced() {
        return advanced;
    }

    public void setAdvanced(boolean advanced) {
        this.advanced = advanced;
    }

    public boolean isConfidential() {
        return confidential;
    }

    public void setConfidential(boolean confidential) {
        this.confidential = confidential;
    }

    public boolean isIsRequired() {
        return isRequired;
    }

    public void setIsRequired(boolean isRequired) {
        this.isRequired = isRequired;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", name).append("value", value).append("type", type)
                .append("advanced", advanced).append("confidential", confidential).append("defaultValue", defaultValue)
                .append("description", description).append("displayName", displayName)
                .append("displayOrder", displayOrder).append("isRequired", isRequired).toString();
    }
}
