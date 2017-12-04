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

package org.wso2.carbon.solution.model.solution;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class ExecutionOrder implements Serializable {

    private final static long serialVersionUID = -2743531219545197201L;
    private String path;

    /**
     * No args constructor for use in serialization
     */
    public ExecutionOrder() {
    }

    /**
     * @param path
     */
    public ExecutionOrder(String path) {
        super();
        this.path = path;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ExecutionOrder) == false) {
            return false;
        }
        ExecutionOrder rhs = ((ExecutionOrder) other);
        return new EqualsBuilder().append(path, rhs.path).isEquals();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(path).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("path", path).toString();
    }
}
