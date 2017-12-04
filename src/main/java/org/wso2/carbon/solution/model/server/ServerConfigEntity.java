/*
 *  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.wso2.carbon.solution.model.server;

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
