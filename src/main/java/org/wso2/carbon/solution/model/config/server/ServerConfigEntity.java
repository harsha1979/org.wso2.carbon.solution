package org.wso2.carbon.solution.model.config.server;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ServerConfigEntity implements Serializable {

    private final static long serialVersionUID = -7172477406788462005L;
    private List<Server> servers = new ArrayList<Server>();

    /**
     * No args constructor for use in serialization
     */
    public ServerConfigEntity() {
    }

    /**
     * @param servers
     */
    public ServerConfigEntity(List<Server> servers) {
        super();
        this.servers = servers;
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
        return new EqualsBuilder().append(servers, rhs.servers).isEquals();
    }

    public List<Server> getServers() {
        return servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(servers).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("servers", servers).toString();
    }
}
