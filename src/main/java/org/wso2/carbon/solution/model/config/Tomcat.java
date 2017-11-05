package org.wso2.carbon.solution.model.config;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class Tomcat implements Serializable {

    private final static long serialVersionUID = -714760161337781715L;
    private String instance;
    private String host;
    private int port;

    /**
     * No args constructor for use in serialization
     */
    public Tomcat() {
    }

    /**
     * @param port
     * @param host
     * @param instance
     */
    public Tomcat(String instance, String host, int port) {
        super();
        this.instance = instance;
        this.host = host;
        this.port = port;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Tomcat) == false) {
            return false;
        }
        Tomcat rhs = ((Tomcat) other);
        return new EqualsBuilder().append(port, rhs.port).append(host, rhs.host).append(instance, rhs.instance)
                .isEquals();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(port).append(host).append(instance).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("instance", instance).append("host", host).append("port", port)
                .toString();
    }
}
