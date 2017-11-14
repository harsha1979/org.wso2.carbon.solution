
package org.wso2.carbon.solution.model.iam.idp;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class IdentityProviderEntity implements Serializable
{

    private IdentityProvider identityProvider;
    private final static long serialVersionUID = -3149586909801964681L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public IdentityProviderEntity() {
    }

    /**
     * 
     * @param identityProvider
     */
    public IdentityProviderEntity(IdentityProvider identityProvider) {
        super();
        this.identityProvider = identityProvider;
    }

    public IdentityProvider getIdentityProvider() {
        return identityProvider;
    }

    public void setIdentityProvider(IdentityProvider identityProvider) {
        this.identityProvider = identityProvider;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("identityProvider", identityProvider).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(identityProvider).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof IdentityProviderEntity) == false) {
            return false;
        }
        IdentityProviderEntity rhs = ((IdentityProviderEntity) other);
        return new EqualsBuilder().append(identityProvider, rhs.identityProvider).isEquals();
    }

}
