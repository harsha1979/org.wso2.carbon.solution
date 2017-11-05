package org.wso2.carbon.solution.model.iam.sp;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InboundAuthenticationConfig implements Serializable {

    private final static long serialVersionUID = -5005954450518681949L;
    private List<InboundAuthenticationRequestConfig> inboundAuthenticationRequestConfigs
            = new ArrayList<InboundAuthenticationRequestConfig>();

    /**
     * No args constructor for use in serialization
     */
    public InboundAuthenticationConfig() {
    }

    /**
     * @param inboundAuthenticationRequestConfigs
     */
    public InboundAuthenticationConfig(List<InboundAuthenticationRequestConfig> inboundAuthenticationRequestConfigs) {
        super();
        this.inboundAuthenticationRequestConfigs = inboundAuthenticationRequestConfigs;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof InboundAuthenticationConfig) == false) {
            return false;
        }
        InboundAuthenticationConfig rhs = ((InboundAuthenticationConfig) other);
        return new EqualsBuilder().append(inboundAuthenticationRequestConfigs, rhs.inboundAuthenticationRequestConfigs)
                .isEquals();
    }

    public List<InboundAuthenticationRequestConfig> getInboundAuthenticationRequestConfigs() {
        return inboundAuthenticationRequestConfigs;
    }

    public void setInboundAuthenticationRequestConfigs(List<InboundAuthenticationRequestConfig>
                                                               inboundAuthenticationRequestConfigs) {
        this.inboundAuthenticationRequestConfigs = inboundAuthenticationRequestConfigs;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(inboundAuthenticationRequestConfigs).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("inboundAuthenticationRequestConfigs", inboundAuthenticationRequestConfigs).toString();
    }
}
