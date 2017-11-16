package org.wso2.carbon.solution.model.config.server;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Properties;

public class Server implements Serializable {

    private final static long serialVersionUID = 3993378306740340142L;
    private String serverName;
    private String instance;
    private Properties properties;

    /**
     * No args constructor for use in serialization
     */
    public Server() {
    }

    /**
     * @param properties
     * @param instance
     * @param serverName
     */
    public Server(String serverName, String instance, Properties properties) {
        super();
        this.serverName = serverName;
        this.instance = instance;
        this.properties = properties;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Server) == false) {
            return false;
        }
        Server rhs = ((Server) other);
        return new EqualsBuilder().append(properties, rhs.properties).append(instance, rhs.instance)
                .append(serverName, rhs.serverName).isEquals();
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(properties).append(instance).append(serverName).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("serverName", serverName).append("instance", instance)
                .append("properties", properties).toString();
    }
}
