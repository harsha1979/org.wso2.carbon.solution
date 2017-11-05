package org.wso2.carbon.solution.model.config;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ServerConfig implements Serializable {

    private final static long serialVersionUID = 5434052023658643786L;
    private List<IdentityServer> identityServer = new ArrayList<IdentityServer>();
    private List<Tomcat> tomcat = new ArrayList<Tomcat>();

    /**
     * No args constructor for use in serialization
     */
    public ServerConfig() {
    }

    /**
     * @param tomcat
     * @param identityServer
     */
    public ServerConfig(List<IdentityServer> identityServer, List<Tomcat> tomcat) {
        super();
        this.identityServer = identityServer;
        this.tomcat = tomcat;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ServerConfig) == false) {
            return false;
        }
        ServerConfig rhs = ((ServerConfig) other);
        return new EqualsBuilder().append(tomcat, rhs.tomcat).append(identityServer, rhs.identityServer).isEquals();
    }

    public List<IdentityServer> getIdentityServer() {
        return identityServer;
    }

    public void setIdentityServer(List<IdentityServer> identityServer) {
        this.identityServer = identityServer;
    }

    public List<Tomcat> getTomcat() {
        return tomcat;
    }

    public void setTomcat(List<Tomcat> tomcat) {
        this.tomcat = tomcat;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(tomcat).append(identityServer).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("identityServer", identityServer).append("tomcat", tomcat).toString();
    }
}
