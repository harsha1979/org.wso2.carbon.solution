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
import java.util.ArrayList;
import java.util.List;

public class AuthenticationStep implements Serializable {

    private final static long serialVersionUID = 2077506787303117800L;
    private boolean isAttributeStep;
    private int stepOrder;
    private boolean isSubjectStep;
    private List<FederatedIdentityProvider> federatedIdentityProviders = new ArrayList<FederatedIdentityProvider>();
    private List<LocalAuthenticatorConfig> localAuthenticatorConfigs = new ArrayList<LocalAuthenticatorConfig>();

    /**
     * No args constructor for use in serialization
     */
    public AuthenticationStep() {
    }

    /**
     * @param localAuthenticatorConfigs
     * @param federatedIdentityProviders
     * @param isSubjectStep
     * @param stepOrder
     * @param isAttributeStep
     */
    public AuthenticationStep(boolean isAttributeStep,
                              int stepOrder,
                              boolean isSubjectStep,
                              List<FederatedIdentityProvider> federatedIdentityProviders,
                              List<LocalAuthenticatorConfig> localAuthenticatorConfigs) {
        super();
        this.isAttributeStep = isAttributeStep;
        this.stepOrder = stepOrder;
        this.isSubjectStep = isSubjectStep;
        this.federatedIdentityProviders = federatedIdentityProviders;
        this.localAuthenticatorConfigs = localAuthenticatorConfigs;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof AuthenticationStep) == false) {
            return false;
        }
        AuthenticationStep rhs = ((AuthenticationStep) other);
        return new EqualsBuilder().append(localAuthenticatorConfigs, rhs.localAuthenticatorConfigs)
                .append(federatedIdentityProviders, rhs.federatedIdentityProviders)
                .append(isSubjectStep, rhs.isSubjectStep).append(stepOrder, rhs.stepOrder)
                .append(isAttributeStep, rhs.isAttributeStep).isEquals();
    }

    public List<FederatedIdentityProvider> getFederatedIdentityProviders() {
        return federatedIdentityProviders;
    }

    public void setFederatedIdentityProviders(List<FederatedIdentityProvider> federatedIdentityProviders) {
        this.federatedIdentityProviders = federatedIdentityProviders;
    }

    public List<LocalAuthenticatorConfig> getLocalAuthenticatorConfigs() {
        return localAuthenticatorConfigs;
    }

    public void setLocalAuthenticatorConfigs(List<LocalAuthenticatorConfig> localAuthenticatorConfigs) {
        this.localAuthenticatorConfigs = localAuthenticatorConfigs;
    }

    public int getStepOrder() {
        return stepOrder;
    }

    public void setStepOrder(int stepOrder) {
        this.stepOrder = stepOrder;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(localAuthenticatorConfigs).append(federatedIdentityProviders)
                .append(isSubjectStep).append(stepOrder).append(isAttributeStep).toHashCode();
    }

    public boolean isIsAttributeStep() {
        return isAttributeStep;
    }

    public void setIsAttributeStep(boolean isAttributeStep) {
        this.isAttributeStep = isAttributeStep;
    }

    public boolean isIsSubjectStep() {
        return isSubjectStep;
    }

    public void setIsSubjectStep(boolean isSubjectStep) {
        this.isSubjectStep = isSubjectStep;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("isAttributeStep", isAttributeStep).append("stepOrder", stepOrder)
                .append("isSubjectStep", isSubjectStep).append("federatedIdentityProviders", federatedIdentityProviders)
                .append("localAuthenticatorConfigs", localAuthenticatorConfigs).toString();
    }
}
