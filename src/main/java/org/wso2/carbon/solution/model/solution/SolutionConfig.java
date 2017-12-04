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
import java.util.ArrayList;
import java.util.List;

public class SolutionConfig implements Serializable {

    private final static long serialVersionUID = -5566778033476139412L;
    private List<ExecutionStep> executionSteps = new ArrayList<ExecutionStep>();
    private List<DeployerDependency> deployerDependencies = new ArrayList<DeployerDependency>();

    /**
     * No args constructor for use in serialization
     */
    public SolutionConfig() {
    }

    /**
     * @param executionSteps
     * @param deployerDependencies
     */
    public SolutionConfig(List<ExecutionStep> executionSteps, List<DeployerDependency> deployerDependencies) {
        super();
        this.executionSteps = executionSteps;
        this.deployerDependencies = deployerDependencies;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SolutionConfig) == false) {
            return false;
        }
        SolutionConfig rhs = ((SolutionConfig) other);
        return new EqualsBuilder().append(executionSteps, rhs.executionSteps)
                .append(deployerDependencies, rhs.deployerDependencies).isEquals();
    }

    public List<DeployerDependency> getDeployerDependencies() {
        return deployerDependencies;
    }

    public void setDeployerDependencies(List<DeployerDependency> deployerDependencies) {
        this.deployerDependencies = deployerDependencies;
    }

    public List<ExecutionStep> getExecutionSteps() {
        return executionSteps;
    }

    public void setExecutionSteps(List<ExecutionStep> executionSteps) {
        this.executionSteps = executionSteps;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(executionSteps).append(deployerDependencies).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("executionSteps", executionSteps)
                .append("deployerDependencies", deployerDependencies).toString();
    }
}
