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
package org.wso2.carbon.solution.model.iam.sp;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class FederatedAuthenticatorConfig implements Serializable {

    private final static long serialVersionUID = -2971026244651243588L;
    private String name;
    private String displayName;

    /**
     * No args constructor for use in serialization
     */
    public FederatedAuthenticatorConfig() {
    }

    /**
     * @param name
     */
    public FederatedAuthenticatorConfig(String name) {
        super();
        this.name = name;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof FederatedAuthenticatorConfig) == false) {
            return false;
        }
        FederatedAuthenticatorConfig rhs = ((FederatedAuthenticatorConfig) other);
        return new EqualsBuilder().append(name, rhs.name).isEquals();
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", name).toString();
    }
}
