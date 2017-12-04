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

public class WorkflowAssociation implements Serializable {

    private final static long serialVersionUID = 1300798975330293481L;
    private List<Association> associations = new ArrayList<Association>();

    /**
     * No args constructor for use in serialization
     */
    public WorkflowAssociation() {
    }

    /**
     * @param associations
     */
    public WorkflowAssociation(List<Association> associations) {
        super();
        this.associations = associations;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof WorkflowAssociation) == false) {
            return false;
        }
        WorkflowAssociation rhs = ((WorkflowAssociation) other);
        return new EqualsBuilder().append(associations, rhs.associations).isEquals();
    }

    public List<Association> getAssociations() {
        return associations;
    }

    public void setAssociations(List<Association> associations) {
        this.associations = associations;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(associations).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("associations", associations).toString();
    }
}
