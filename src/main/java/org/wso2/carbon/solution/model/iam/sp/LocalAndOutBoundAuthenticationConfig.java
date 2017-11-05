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
