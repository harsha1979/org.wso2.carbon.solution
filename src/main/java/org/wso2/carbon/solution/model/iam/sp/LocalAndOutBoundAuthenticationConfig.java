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

public class LocalAndOutBoundAuthenticationConfig implements Serializable {

    private final static long serialVersionUID = 7033044341020883615L;
    private boolean isAlwaysSendBackAuthenticatedListOfIdPs;
    private String authenticationType;
    private boolean isEnableAuthorization;
    private String subjectClaimUri;
    private boolean isUseTenantDomainInLocalSubjectIdentifier;
    private boolean isUseUserstoreDomainInLocalSubjectIdentifier;
    private List<AuthenticationStep> authenticationSteps = new ArrayList<AuthenticationStep>();

    /**
     * No args constructor for use in serialization
     */
    public LocalAndOutBoundAuthenticationConfig() {
    }

    /**
     * @param authenticationSteps
     * @param isAlwaysSendBackAuthenticatedListOfIdPs
     * @param subjectClaimUri
     * @param isUseUserstoreDomainInLocalSubjectIdentifier
     * @param isEnableAuthorization
     * @param authenticationType
     * @param isUseTenantDomainInLocalSubjectIdentifier
     */
    public LocalAndOutBoundAuthenticationConfig(boolean isAlwaysSendBackAuthenticatedListOfIdPs,
                                                String authenticationType,
                                                boolean isEnableAuthorization,
                                                String subjectClaimUri,
                                                boolean isUseTenantDomainInLocalSubjectIdentifier,
                                                boolean isUseUserstoreDomainInLocalSubjectIdentifier,
                                                List<AuthenticationStep> authenticationSteps) {
        super();
        this.isAlwaysSendBackAuthenticatedListOfIdPs = isAlwaysSendBackAuthenticatedListOfIdPs;
        this.authenticationType = authenticationType;
        this.isEnableAuthorization = isEnableAuthorization;
        this.subjectClaimUri = subjectClaimUri;
        this.isUseTenantDomainInLocalSubjectIdentifier = isUseTenantDomainInLocalSubjectIdentifier;
        this.isUseUserstoreDomainInLocalSubjectIdentifier = isUseUserstoreDomainInLocalSubjectIdentifier;
        this.authenticationSteps = authenticationSteps;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof LocalAndOutBoundAuthenticationConfig) == false) {
            return false;
        }
        LocalAndOutBoundAuthenticationConfig rhs = ((LocalAndOutBoundAuthenticationConfig) other);
        return new EqualsBuilder().append(authenticationSteps, rhs.authenticationSteps)
                .append(isAlwaysSendBackAuthenticatedListOfIdPs, rhs.isAlwaysSendBackAuthenticatedListOfIdPs)
                .append(subjectClaimUri, rhs.subjectClaimUri)
                .append(isUseUserstoreDomainInLocalSubjectIdentifier, rhs.isUseUserstoreDomainInLocalSubjectIdentifier)
                .append(isEnableAuthorization, rhs.isEnableAuthorization)
                .append(authenticationType, rhs.authenticationType)
                .append(isUseTenantDomainInLocalSubjectIdentifier, rhs.isUseTenantDomainInLocalSubjectIdentifier)
                .isEquals();
    }

    public List<AuthenticationStep> getAuthenticationSteps() {
        return authenticationSteps;
    }

    public void setAuthenticationSteps(List<AuthenticationStep> authenticationSteps) {
        this.authenticationSteps = authenticationSteps;
    }

    public String getAuthenticationType() {
        return authenticationType;
    }

    public void setAuthenticationType(String authenticationType) {
        this.authenticationType = authenticationType;
    }

    public String getSubjectClaimUri() {
        return subjectClaimUri;
    }

    public void setSubjectClaimUri(String subjectClaimUri) {
        this.subjectClaimUri = subjectClaimUri;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(authenticationSteps).append(isAlwaysSendBackAuthenticatedListOfIdPs)
                .append(subjectClaimUri).append(isUseUserstoreDomainInLocalSubjectIdentifier)
                .append(isEnableAuthorization).append(authenticationType)
                .append(isUseTenantDomainInLocalSubjectIdentifier).toHashCode();
    }

    public boolean isIsAlwaysSendBackAuthenticatedListOfIdPs() {
        return isAlwaysSendBackAuthenticatedListOfIdPs;
    }

    public void setIsAlwaysSendBackAuthenticatedListOfIdPs(boolean isAlwaysSendBackAuthenticatedListOfIdPs) {
        this.isAlwaysSendBackAuthenticatedListOfIdPs = isAlwaysSendBackAuthenticatedListOfIdPs;
    }

    public boolean isIsEnableAuthorization() {
        return isEnableAuthorization;
    }

    public void setIsEnableAuthorization(boolean isEnableAuthorization) {
        this.isEnableAuthorization = isEnableAuthorization;
    }

    public boolean isIsUseTenantDomainInLocalSubjectIdentifier() {
        return isUseTenantDomainInLocalSubjectIdentifier;
    }

    public void setIsUseTenantDomainInLocalSubjectIdentifier(boolean isUseTenantDomainInLocalSubjectIdentifier) {
        this.isUseTenantDomainInLocalSubjectIdentifier = isUseTenantDomainInLocalSubjectIdentifier;
    }

    public boolean isIsUseUserstoreDomainInLocalSubjectIdentifier() {
        return isUseUserstoreDomainInLocalSubjectIdentifier;
    }

    public void setIsUseUserstoreDomainInLocalSubjectIdentifier(boolean isUseUserstoreDomainInLocalSubjectIdentifier) {
        this.isUseUserstoreDomainInLocalSubjectIdentifier = isUseUserstoreDomainInLocalSubjectIdentifier;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("isAlwaysSendBackAuthenticatedListOfIdPs", isAlwaysSendBackAuthenticatedListOfIdPs)
                .append("authenticationType", authenticationType).append("isEnableAuthorization", isEnableAuthorization)
                .append("subjectClaimUri", subjectClaimUri)
                .append("isUseTenantDomainInLocalSubjectIdentifier", isUseTenantDomainInLocalSubjectIdentifier)
                .append("isUseUserstoreDomainInLocalSubjectIdentifier", isUseUserstoreDomainInLocalSubjectIdentifier)
                .append("authenticationSteps", authenticationSteps).toString();
    }
}
