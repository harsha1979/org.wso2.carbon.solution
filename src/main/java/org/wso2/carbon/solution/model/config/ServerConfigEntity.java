package org.wso2.carbon.solution.model.config;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class ServerConfigEntity implements Serializable {

    private final static long serialVersionUID = -3293318591229666552L;
    private ServerConfig serverConfig;

    /**
     * No args constructor for use in serialization
     */
    public ServerConfigEntity() {
    }

    /**
     * @param serverConfig
     */
    public ServerConfigEntity(ServerConfig serverConfig) {
        super();
        this.serverConfig = serverConfig;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ServerConfigEntity) == false) {
            return false;
        }
        ServerConfigEntity rhs = ((ServerConfigEntity) other);
        return new EqualsBuilder().append(serverConfig, rhs.serverConfig).isEquals();
    }

    public ServerConfig getServerConfig() {
        return serverConfig;
    }

    public void setServerConfig(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(serverConfig).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("serverConfig", serverConfig).toString();
    }
}
