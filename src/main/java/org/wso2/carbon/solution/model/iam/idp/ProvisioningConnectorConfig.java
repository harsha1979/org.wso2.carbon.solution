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
import java.util.ArrayList;
import java.util.List;

public class ProvisioningConnectorConfig implements Serializable {

    private final static long serialVersionUID = 8481861028608555505L;
    private boolean isBlocking;
    private boolean isEnabled;
    private String name;
    private boolean isRulesEnabled;
    private boolean isValid;
    private boolean isDefault;
    private List<ProvisioningProperty> provisioningProperties = new ArrayList<ProvisioningProperty>();

    /**
     * No args constructor for use in serialization
     */
    public ProvisioningConnectorConfig() {
    }

    /**
     * @param provisioningProperties
     * @param isDefault
     * @param name
     * @param isBlocking
     * @param isValid
     * @param isRulesEnabled
     * @param isEnabled
     */
    public ProvisioningConnectorConfig(boolean isBlocking,
                                       boolean isEnabled,
                                       String name,
                                       boolean isRulesEnabled,
                                       boolean isValid,
                                       boolean isDefault,
                                       List<ProvisioningProperty> provisioningProperties) {
        super();
        this.isBlocking = isBlocking;
        this.isEnabled = isEnabled;
        this.name = name;
        this.isRulesEnabled = isRulesEnabled;
        this.isValid = isValid;
        this.isDefault = isDefault;
        this.provisioningProperties = provisioningProperties;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ProvisioningConnectorConfig) == false) {
            return false;
        }
        ProvisioningConnectorConfig rhs = ((ProvisioningConnectorConfig) other);
        return new EqualsBuilder().append(provisioningProperties, rhs.provisioningProperties)
                .append(isDefault, rhs.isDefault).append(name, rhs.name).append(isBlocking, rhs.isBlocking)
                .append(isValid, rhs.isValid).append(isRulesEnabled, rhs.isRulesEnabled)
                .append(isEnabled, rhs.isEnabled).isEquals();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProvisioningProperty> getProvisioningProperties() {
        return provisioningProperties;
    }

    public void setProvisioningProperties(List<ProvisioningProperty> provisioningProperties) {
        this.provisioningProperties = provisioningProperties;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(provisioningProperties).append(isDefault).append(name).append(isBlocking)
                .append(isValid).append(isRulesEnabled).append(isEnabled).toHashCode();
    }

    public boolean isIsBlocking() {
        return isBlocking;
    }

    public void setIsBlocking(boolean isBlocking) {
        this.isBlocking = isBlocking;
    }

    public boolean isIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public boolean isIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public boolean isIsRulesEnabled() {
        return isRulesEnabled;
    }

    public void setIsRulesEnabled(boolean isRulesEnabled) {
        this.isRulesEnabled = isRulesEnabled;
    }

    public boolean isIsValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("isBlocking", isBlocking).append("isEnabled", isEnabled)
                .append("name", name).append("isRulesEnabled", isRulesEnabled).append("isValid", isValid)
                .append("isDefault", isDefault).append("provisioningProperties", provisioningProperties).toString();
    }
}
