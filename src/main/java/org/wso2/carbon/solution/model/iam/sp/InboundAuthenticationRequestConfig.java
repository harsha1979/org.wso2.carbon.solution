package org.wso2.carbon.solution.model.iam.sp;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InboundAuthenticationRequestConfig implements Serializable {

    private final static long serialVersionUID = 628625089107105126L;
    private String friendlyName;
    private String inboundAuthKey;
    private String inboundAuthType;
    private String inboundConfigType;
    private List<Property> properties = new ArrayList<Property>();

    /**
     * No args constructor for use in serialization
     */
    public InboundAuthenticationRequestConfig() {
    }

    /**
     * @param inboundConfigType
     * @param inboundAuthType
     * @param friendlyName
     * @param properties
     * @param inboundAuthKey
     */
    public InboundAuthenticationRequestConfig(String friendlyName,
                                              String inboundAuthKey,
                                              String inboundAuthType,
                                              String inboundConfigType,
                                              List<Property> properties) {
        super();
        this.friendlyName = friendlyName;
        this.inboundAuthKey = inboundAuthKey;
        this.inboundAuthType = inboundAuthType;
        this.inboundConfigType = inboundConfigType;
        this.properties = properties;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof InboundAuthenticationRequestConfig) == false) {
            return false;
        }
        InboundAuthenticationRequestConfig rhs = ((InboundAuthenticationRequestConfig) other);
        return new EqualsBuilder().append(inboundConfigType, rhs.inboundConfigType)
                .append(inboundAuthType, rhs.inboundAuthType).append(friendlyName, rhs.friendlyName)
                .append(properties, rhs.properties).append(inboundAuthKey, rhs.inboundAuthKey).isEquals();
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getInboundAuthKey() {
        return inboundAuthKey;
    }

    public void setInboundAuthKey(String inboundAuthKey) {
        this.inboundAuthKey = inboundAuthKey;
    }

    public String getInboundAuthType() {
        return inboundAuthType;
    }

    public void setInboundAuthType(String inboundAuthType) {
        this.inboundAuthType = inboundAuthType;
    }

    public String getInboundConfigType() {
        return inboundConfigType;
    }

    public void setInboundConfigType(String inboundConfigType) {
        this.inboundConfigType = inboundConfigType;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(inboundConfigType).append(inboundAuthType).append(friendlyName)
                .append(properties).append(inboundAuthKey).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("friendlyName", friendlyName).append("inboundAuthKey", inboundAuthKey)
                .append("inboundAuthType", inboundAuthType).append("inboundConfigType", inboundConfigType)
                .append("properties", properties).toString();
    }
}
