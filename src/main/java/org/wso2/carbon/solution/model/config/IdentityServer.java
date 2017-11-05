package org.wso2.carbon.solution.model.config;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class IdentityServer implements Serializable {

    private final static long serialVersionUID = -3258039037748894019L;
    private String instance;
    private String host;
    private int port;
    private String trustore;

    /**
     * No args constructor for use in serialization
     */
    public IdentityServer() {
    }

    /**
     * @param port
     * @param host
     * @param instance
     * @param trustore
     */
    public IdentityServer(String instance, String host, int port, String trustore) {
        super();
        this.instance = instance;
        this.host = host;
        this.port = port;
        this.trustore = trustore;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof IdentityServer) == false) {
            return false;
        }
        IdentityServer rhs = ((IdentityServer) other);
        return new EqualsBuilder().append(port, rhs.port).append(host, rhs.host).append(instance, rhs.instance)
                .append(trustore, rhs.trustore).isEquals();
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

    public String getTrustore() {
        return trustore;
    }

    public void setTrustore(String trustore) {
        this.trustore = trustore;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(port).append(host).append(instance).append(trustore).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("instance", instance).append("host", host).append("port", port)
                .append("trustore", trustore).toString();
    }
}
