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

public class Workflow implements Serializable {

    private final static long serialVersionUID = -793042250455822429L;
    private Object workflowId;
    private Object name;
    private String bpsProfile;
    private String taskSubject;
    private String taskDescription;
    private WorkflowApproval workflowApproval;
    private WorkflowAssociation workflowAssociation;

    /**
     * No args constructor for use in serialization
     */
    public Workflow() {
    }

    /**
     * @param workflowId
     * @param taskSubject
     * @param name
     * @param workflowApproval
     * @param workflowAssociation
     * @param bpsProfile
     * @param taskDescription
     */
    public Workflow(Object workflowId,
                    Object name,
                    String bpsProfile,
                    String taskSubject,
                    String taskDescription,
                    WorkflowApproval workflowApproval,
                    WorkflowAssociation workflowAssociation) {
        super();
        this.workflowId = workflowId;
        this.name = name;
        this.bpsProfile = bpsProfile;
        this.taskSubject = taskSubject;
        this.taskDescription = taskDescription;
        this.workflowApproval = workflowApproval;
        this.workflowAssociation = workflowAssociation;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Workflow) == false) {
            return false;
        }
        Workflow rhs = ((Workflow) other);
        return new EqualsBuilder().append(workflowId, rhs.workflowId).append(taskSubject, rhs.taskSubject)
                .append(name, rhs.name).append(workflowApproval, rhs.workflowApproval)
                .append(workflowAssociation, rhs.workflowAssociation).append(bpsProfile, rhs.bpsProfile)
                .append(taskDescription, rhs.taskDescription).isEquals();
    }

    public String getBpsProfile() {
        return bpsProfile;
    }

    public void setBpsProfile(String bpsProfile) {
        this.bpsProfile = bpsProfile;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskSubject() {
        return taskSubject;
    }

    public void setTaskSubject(String taskSubject) {
        this.taskSubject = taskSubject;
    }

    public WorkflowApproval getWorkflowApproval() {
        return workflowApproval;
    }

    public void setWorkflowApproval(WorkflowApproval workflowApproval) {
        this.workflowApproval = workflowApproval;
    }

    public WorkflowAssociation getWorkflowAssociation() {
        return workflowAssociation;
    }

    public void setWorkflowAssociation(WorkflowAssociation workflowAssociation) {
        this.workflowAssociation = workflowAssociation;
    }

    public Object getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Object workflowId) {
        this.workflowId = workflowId;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(workflowId).append(taskSubject).append(name).append(workflowApproval)
                .append(workflowAssociation).append(bpsProfile).append(taskDescription).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("workflowId", workflowId).append("name", name)
                .append("bpsProfile", bpsProfile).append("taskSubject", taskSubject)
                .append("taskDescription", taskDescription).append("workflowApproval", workflowApproval)
                .append("workflowAssociation", workflowAssociation).toString();
    }
}
