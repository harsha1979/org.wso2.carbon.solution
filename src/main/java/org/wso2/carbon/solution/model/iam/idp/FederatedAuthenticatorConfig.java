package org.wso2.carbon.solution.model.iam.idp;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FederatedAuthenticatorConfig implements Serializable {

    private final static long serialVersionUID = -6000049908045342527L;
    private String displayName;
    private boolean isEnabled;
    private String name;
    private boolean isValid;
    private boolean isDefault;
    private List<AuthenticatorProperty> authenticatorProperties = new ArrayList<AuthenticatorProperty>();

    /**
     * No args constructor for use in serialization
     */
    public FederatedAuthenticatorConfig() {
    }

    /**
     * @param authenticatorProperties
     * @param isDefault
     * @param name
     * @param displayName
     * @param isValid
     * @param isEnabled
     */
    public FederatedAuthenticatorConfig(String displayName,
                                        boolean isEnabled,
                                        String name,
                                        boolean isValid,
                                        boolean isDefault,
                                        List<AuthenticatorProperty> authenticatorProperties) {
        super();
        this.displayName = displayName;
        this.isEnabled = isEnabled;
        this.name = name;
        this.isValid = isValid;
        this.isDefault = isDefault;
        this.authenticatorProperties = authenticatorProperties;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof FederatedAuthenticatorConfig) == false) {
            return false;
        }
        FederatedAuthenticatorConfig rhs = ((FederatedAuthenticatorConfig) other);
        return new EqualsBuilder().append(authenticatorProperties, rhs.authenticatorProperties)
                .append(isDefault, rhs.isDefault).append(name, rhs.name).append(displayName, rhs.displayName)
                .append(isValid, rhs.isValid).append(isEnabled, rhs.isEnabled).isEquals();
    }

    public List<AuthenticatorProperty> getAuthenticatorProperties() {
        return authenticatorProperties;
    }

    public void setAuthenticatorProperties(List<AuthenticatorProperty> authenticatorProperties) {
        this.authenticatorProperties = authenticatorProperties;
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

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(authenticatorProperties).append(isDefault).append(name).append(displayName)
                .append(isValid).append(isEnabled).toHashCode();
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

    public boolean isIsValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("displayName", displayName).append("isEnabled", isEnabled)
                .append("name", name).append("isValid", isValid).append("isDefault", isDefault)
                .append("authenticatorProperties", authenticatorProperties).toString();
    }
}
