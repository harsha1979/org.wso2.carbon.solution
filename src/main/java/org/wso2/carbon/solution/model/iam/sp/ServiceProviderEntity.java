package org.wso2.carbon.solution.model.iam.sp;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class ServiceProviderEntity implements Serializable {

    private final static long serialVersionUID = -2686273886101123682L;
    private ServiceProvider serviceProvider;

    /**
     * No args constructor for use in serialization
     */
    public ServiceProviderEntity() {
    }

    /**
     * @param serviceProvider
     */
    public ServiceProviderEntity(ServiceProvider serviceProvider) {
        super();
        this.serviceProvider = serviceProvider;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ServiceProviderEntity) == false) {
            return false;
        }
        ServiceProviderEntity rhs = ((ServiceProviderEntity) other);
        return new EqualsBuilder().append(serviceProvider, rhs.serviceProvider).isEquals();
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(serviceProvider).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("serviceProvider", serviceProvider).toString();
    }
}
