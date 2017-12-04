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

package org.wso2.carbon.solution.model.datasource;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Properties;

public class Datasource implements Serializable {

    private final static long serialVersionUID = 3089645941630933456L;
    private String name;
    private String databaseType;
    private Properties properties;

    /**
     * No args constructor for use in serialization
     */
    public Datasource() {
    }

    /**
     * @param name
     * @param properties
     */
    public Datasource(String name, Properties properties) {
        super();
        this.name = name;
        this.properties = properties;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Datasource) == false) {
            return false;
        }
        Datasource rhs = ((Datasource) other);
        return new EqualsBuilder().append(name, rhs.name).append(properties, rhs.properties).isEquals();
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(properties).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", name).append("properties", properties).toString();
    }
}
