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
package org.wso2.carbon.solution.model.iam.userstore;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Properties;

public class UserStore implements Serializable {

    private final static long serialVersionUID = -145737589355878984L;
    private String userStoreDomain;
    private String dataSourceName;
    private String userStoreType;
    private Properties properties = new Properties();

    /**
     * No args constructor for use in serialization
     */
    public UserStore() {
    }

    /**
     * @param userStoreDomain
     * @param properties
     * @param dataSourceName
     */
    public UserStore(String userStoreDomain, String dataSourceName, Properties properties) {
        super();
        this.userStoreDomain = userStoreDomain;
        this.dataSourceName = dataSourceName;
        this.properties = properties;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof UserStore) == false) {
            return false;
        }
        UserStore rhs = ((UserStore) other);
        return new EqualsBuilder().append(userStoreDomain, rhs.userStoreDomain).append(userStoreType, rhs.userStoreType)
                .append(properties, rhs.properties)
                .append(dataSourceName, rhs.dataSourceName).isEquals();
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getUserStoreDomain() {
        return userStoreDomain;
    }

    public void setUserStoreDomain(String userStoreDomain) {
        this.userStoreDomain = userStoreDomain;
    }

    public String getUserStoreType() {
        return userStoreType;
    }

    public void setUserStoreType(String userStoreType) {
        this.userStoreType = userStoreType;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(userStoreDomain).append(userStoreType).append(properties)
                .append(dataSourceName).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("userStoreDomain", userStoreDomain)
                .append("dataSourceName", dataSourceName).append("userStoreType", userStoreType)
                .append("properties", properties).toString();
    }
}