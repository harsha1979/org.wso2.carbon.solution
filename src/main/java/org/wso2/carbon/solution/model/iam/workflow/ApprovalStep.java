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
package org.wso2.carbon.solution.model.iam.workflow;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ApprovalStep implements Serializable {

    private final static long serialVersionUID = -1409945989844016824L;
    private List<String> users = new ArrayList<String>();
    private List<String> roles = new ArrayList<String>();

    /**
     * No args constructor for use in serialization
     */
    public ApprovalStep() {
    }

    /**
     * @param users
     * @param roles
     */
    public ApprovalStep(List<String> users, List<String> roles) {
        super();
        this.users = users;
        this.roles = roles;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ApprovalStep) == false) {
            return false;
        }
        ApprovalStep rhs = ((ApprovalStep) other);
        return new EqualsBuilder().append(users, rhs.users).append(roles, rhs.roles).isEquals();
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(users).append(roles).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("users", users).append("roles", roles).toString();
    }
}
