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
import java.util.ArrayList;
import java.util.List;

public class DataSourceConfigEntity implements Serializable {

    private final static long serialVersionUID = -2457071837544876906L;
    private List<Datasource> datasources = new ArrayList<Datasource>();

    /**
     * No args constructor for use in serialization
     */
    public DataSourceConfigEntity() {
    }

    /**
     * @param datasources
     */
    public DataSourceConfigEntity(List<Datasource> datasources) {
        super();
        this.datasources = datasources;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DataSourceConfigEntity) == false) {
            return false;
        }
        DataSourceConfigEntity rhs = ((DataSourceConfigEntity) other);
        return new EqualsBuilder().append(datasources, rhs.datasources).isEquals();
    }

    public List<Datasource> getDatasources() {
        return datasources;
    }

    public void setDatasources(List<Datasource> datasources) {
        this.datasources = datasources;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(datasources).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("datasources", datasources).toString();
    }
}
