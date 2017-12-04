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
