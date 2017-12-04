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

public class ServiceProvider implements Serializable {

    private final static long serialVersionUID = -7051233997427626948L;
    private int id;
    private String name;
    private String description;
    private boolean isSaasApp;
    private ClaimConfiguration claimConfiguration;
    private InboundAuthenticationConfig inboundAuthenticationConfig;
    private InboundProvisioningConfig inboundProvisioningConfig;
    private LocalAndOutBoundAuthenticationConfig localAndOutBoundAuthenticationConfig;
    private OutboundProvisioningConfig outboundProvisioningConfig;
    private Owner owner;

    /**
     * No args constructor for use in serialization
     */
    public ServiceProvider() {
    }

    /**
     * @param id
     * @param inboundAuthenticationConfig
     * @param isSaasApp
     * @param description
     * @param outboundProvisioningConfig
     * @param name
     * @param owner
     * @param localAndOutBoundAuthenticationConfig
     * @param inboundProvisioningConfig
     * @param claimConfiguration
     */
    public ServiceProvider(int id,
                           String name,
                           String description,
                           boolean isSaasApp,
                           ClaimConfiguration claimConfiguration,
                           InboundAuthenticationConfig inboundAuthenticationConfig,
                           InboundProvisioningConfig inboundProvisioningConfig,
                           LocalAndOutBoundAuthenticationConfig localAndOutBoundAuthenticationConfig,
                           OutboundProvisioningConfig outboundProvisioningConfig,
                           Owner owner) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.isSaasApp = isSaasApp;
        this.claimConfiguration = claimConfiguration;
        this.inboundAuthenticationConfig = inboundAuthenticationConfig;
        this.inboundProvisioningConfig = inboundProvisioningConfig;
        this.localAndOutBoundAuthenticationConfig = localAndOutBoundAuthenticationConfig;
        this.outboundProvisioningConfig = outboundProvisioningConfig;
        this.owner = owner;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ServiceProvider) == false) {
            return false;
        }
        ServiceProvider rhs = ((ServiceProvider) other);
        return new EqualsBuilder().append(id, rhs.id)
                .append(inboundAuthenticationConfig, rhs.inboundAuthenticationConfig).append(isSaasApp, rhs.isSaasApp)
                .append(description, rhs.description).append(outboundProvisioningConfig, rhs.outboundProvisioningConfig)
                .append(name, rhs.name).append(owner, rhs.owner)
                .append(localAndOutBoundAuthenticationConfig, rhs.localAndOutBoundAuthenticationConfig)
                .append(inboundProvisioningConfig, rhs.inboundProvisioningConfig)
                .append(claimConfiguration, rhs.claimConfiguration).isEquals();
    }

    public ClaimConfiguration getClaimConfiguration() {
        return claimConfiguration;
    }

    public void setClaimConfiguration(ClaimConfiguration claimConfiguration) {
        this.claimConfiguration = claimConfiguration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public InboundAuthenticationConfig getInboundAuthenticationConfig() {
        return inboundAuthenticationConfig;
    }

    public void setInboundAuthenticationConfig(InboundAuthenticationConfig inboundAuthenticationConfig) {
        this.inboundAuthenticationConfig = inboundAuthenticationConfig;
    }

    public InboundProvisioningConfig getInboundProvisioningConfig() {
        return inboundProvisioningConfig;
    }

    public void setInboundProvisioningConfig(InboundProvisioningConfig inboundProvisioningConfig) {
        this.inboundProvisioningConfig = inboundProvisioningConfig;
    }

    public LocalAndOutBoundAuthenticationConfig getLocalAndOutBoundAuthenticationConfig() {
        return localAndOutBoundAuthenticationConfig;
    }

    public void setLocalAndOutBoundAuthenticationConfig(LocalAndOutBoundAuthenticationConfig
                                                                localAndOutBoundAuthenticationConfig) {
        this.localAndOutBoundAuthenticationConfig = localAndOutBoundAuthenticationConfig;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OutboundProvisioningConfig getOutboundProvisioningConfig() {
        return outboundProvisioningConfig;
    }

    public void setOutboundProvisioningConfig(OutboundProvisioningConfig outboundProvisioningConfig) {
        this.outboundProvisioningConfig = outboundProvisioningConfig;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(inboundAuthenticationConfig).append(isSaasApp)
                .append(description).append(outboundProvisioningConfig).append(name).append(owner)
                .append(localAndOutBoundAuthenticationConfig).append(inboundProvisioningConfig)
                .append(claimConfiguration).toHashCode();
    }

    public boolean isIsSaasApp() {
        return isSaasApp;
    }

    public void setIsSaasApp(boolean isSaasApp) {
        this.isSaasApp = isSaasApp;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("name", name).append("description", description)
                .append("isSaasApp", isSaasApp).append("claimConfiguration", claimConfiguration)
                .append("inboundAuthenticationConfig", inboundAuthenticationConfig)
                .append("inboundProvisioningConfig", inboundProvisioningConfig)
                .append("localAndOutBoundAuthenticationConfig", localAndOutBoundAuthenticationConfig)
                .append("outboundProvisioningConfig", outboundProvisioningConfig).append("owner", owner).toString();
    }
}
