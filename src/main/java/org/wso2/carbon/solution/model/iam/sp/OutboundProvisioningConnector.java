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
package org.wso2.carbon.solution.model.iam.sp;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class OutboundProvisioningConnector implements Serializable {

    private final static long serialVersionUID = -1104868734320848310L;
    private String identityProviderName;
    private String provisioningConnectorType;
    private boolean jitEnable;
    private boolean enabled;
    private boolean blocking;
    private boolean rulesEnabled;

    /**
     * No args constructor for use in serialization
     */
    public OutboundProvisioningConnector() {
    }

    /**
     * @param enabled
     * @param jitEnable
     * @param blocking
     * @param identityProviderName
     * @param provisioningConnectorType
     * @param rulesEnabled
     */
    public OutboundProvisioningConnector(String identityProviderName,
                                         String provisioningConnectorType,
                                         boolean jitEnable,
                                         boolean enabled,
                                         boolean blocking,
                                         boolean rulesEnabled) {
        super();
        this.identityProviderName = identityProviderName;
        this.provisioningConnectorType = provisioningConnectorType;
        this.jitEnable = jitEnable;
        this.enabled = enabled;
        this.blocking = blocking;
        this.rulesEnabled = rulesEnabled;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof OutboundProvisioningConnector) == false) {
            return false;
        }
        OutboundProvisioningConnector rhs = ((OutboundProvisioningConnector) other);
        return new EqualsBuilder().append(enabled, rhs.enabled).append(jitEnable, rhs.jitEnable)
                .append(blocking, rhs.blocking).append(identityProviderName, rhs.identityProviderName)
                .append(provisioningConnectorType, rhs.provisioningConnectorType).append(rulesEnabled, rhs.rulesEnabled)
                .isEquals();
    }

    public String getIdentityProviderName() {
        return identityProviderName;
    }

    public void setIdentityProviderName(String identityProviderName) {
        this.identityProviderName = identityProviderName;
    }

    public String getProvisioningConnectorType() {
        return provisioningConnectorType;
    }

    public void setProvisioningConnectorType(String provisioningConnectorType) {
        this.provisioningConnectorType = provisioningConnectorType;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(enabled).append(jitEnable).append(blocking).append(identityProviderName)
                .append(provisioningConnectorType).append(rulesEnabled).toHashCode();
    }

    public boolean isBlocking() {
        return blocking;
    }

    public void setBlocking(boolean blocking) {
        this.blocking = blocking;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isJitEnable() {
        return jitEnable;
    }

    public void setJitEnable(boolean jitEnable) {
        this.jitEnable = jitEnable;
    }

    public boolean isRulesEnabled() {
        return rulesEnabled;
    }

    public void setRulesEnabled(boolean rulesEnabled) {
        this.rulesEnabled = rulesEnabled;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("identityProviderName", identityProviderName)
                .append("provisioningConnectorType", provisioningConnectorType).append("jitEnable", jitEnable)
                .append("enabled", enabled).append("blocking", blocking).append("rulesEnabled", rulesEnabled)
                .toString();
    }
}
