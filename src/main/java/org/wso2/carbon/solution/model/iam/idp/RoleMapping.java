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
package org.wso2.carbon.solution.model.iam.idp;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class RoleMapping implements Serializable {

    private final static long serialVersionUID = 6630060929336825829L;
    private LocalRole localRole;
    private String remoteRole;

    /**
     * No args constructor for use in serialization
     */
    public RoleMapping() {
    }

    /**
     * @param localRole
     * @param remoteRole
     */
    public RoleMapping(LocalRole localRole, String remoteRole) {
        super();
        this.localRole = localRole;
        this.remoteRole = remoteRole;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof RoleMapping) == false) {
            return false;
        }
        RoleMapping rhs = ((RoleMapping) other);
        return new EqualsBuilder().append(localRole, rhs.localRole).append(remoteRole, rhs.remoteRole).isEquals();
    }

    public LocalRole getLocalRole() {
        return localRole;
    }

    public void setLocalRole(LocalRole localRole) {
        this.localRole = localRole;
    }

    public String getRemoteRole() {
        return remoteRole;
    }

    public void setRemoteRole(String remoteRole) {
        this.remoteRole = remoteRole;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(localRole).append(remoteRole).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("localRole", localRole).append("remoteRole", remoteRole).toString();
    }
}
