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

public class WorkflowApproval implements Serializable {

    private final static long serialVersionUID = 5979309274655986195L;
    private List<ApprovalStep> approvalSteps = new ArrayList<ApprovalStep>();

    /**
     * No args constructor for use in serialization
     */
    public WorkflowApproval() {
    }

    /**
     * @param approvalSteps
     */
    public WorkflowApproval(List<ApprovalStep> approvalSteps) {
        super();
        this.approvalSteps = approvalSteps;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof WorkflowApproval) == false) {
            return false;
        }
        WorkflowApproval rhs = ((WorkflowApproval) other);
        return new EqualsBuilder().append(approvalSteps, rhs.approvalSteps).isEquals();
    }

    public List<ApprovalStep> getApprovalSteps() {
        return approvalSteps;
    }

    public void setApprovalSteps(List<ApprovalStep> approvalSteps) {
        this.approvalSteps = approvalSteps;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(approvalSteps).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("approvalSteps", approvalSteps).toString();
    }
}
