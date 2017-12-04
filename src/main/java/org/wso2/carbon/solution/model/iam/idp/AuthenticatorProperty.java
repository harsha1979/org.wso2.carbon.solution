/*
 *  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.wso2.carbon.solution.model.iam.idp;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class AuthenticatorProperty implements Serializable {

    private final static long serialVersionUID = -8298599732065999786L;
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

    /**
     * No args constructor for use in serialization
     */
    public AuthenticatorProperty() {
    }

    /**
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
    public AuthenticatorProperty(boolean isAdvanced,
                                 boolean isConfidential,
                                 String defaultValue,
                                 String description,
                                 String displayName,
                                 int displayOrder,
                                 String name,
                                 boolean isRequired,
                                 String type,
                                 String value) {
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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof AuthenticatorProperty) == false) {
            return false;
        }
        AuthenticatorProperty rhs = ((AuthenticatorProperty) other);
        return new EqualsBuilder().append(isRequired, rhs.isRequired).append(isConfidential, rhs.isConfidential)
                .append(description, rhs.description).append(name, rhs.name).append(value, rhs.value)
                .append(displayOrder, rhs.displayOrder).append(type, rhs.type).append(displayName, rhs.displayName)
                .append(defaultValue, rhs.defaultValue).append(isAdvanced, rhs.isAdvanced).isEquals();
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
        return new HashCodeBuilder().append(isRequired).append(isConfidential).append(description).append(name)
                .append(value).append(displayOrder).append(type).append(displayName).append(defaultValue)
                .append(isAdvanced).toHashCode();
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

    public boolean isIsRequired() {
        return isRequired;
    }

    public void setIsRequired(boolean isRequired) {
        this.isRequired = isRequired;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("isAdvanced", isAdvanced).append("isConfidential", isConfidential)
                .append("defaultValue", defaultValue).append("description", description)
                .append("displayName", displayName).append("displayOrder", displayOrder).append("name", name)
                .append("isRequired", isRequired).append("type", type).append("value", value).toString();
    }
}
