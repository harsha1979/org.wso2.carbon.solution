package org.wso2.carbon.solution.model.config.solution;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class DeployerDependency implements Serializable {

    private final static long serialVersionUID = 1280678559814809372L;
    private String deployer;
    private String dependency;

    /**
     * No args constructor for use in serialization
     */
    public DeployerDependency() {
    }

    /**
     * @param dependency
     * @param deployer
     */
    public DeployerDependency(String deployer, String dependency) {
        super();
        this.deployer = deployer;
        this.dependency = dependency;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DeployerDependency) == false) {
            return false;
        }
        DeployerDependency rhs = ((DeployerDependency) other);
        return new EqualsBuilder().append(dependency, rhs.dependency).append(deployer, rhs.deployer).isEquals();
    }

    public String getDependency() {
        return dependency;
    }

    public void setDependency(String dependency) {
        this.dependency = dependency;
    }

    public String getDeployer() {
        return deployer;
    }

    public void setDeployer(String deployer) {
        this.deployer = deployer;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(dependency).append(deployer).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("deployer", deployer).append("dependency", dependency).toString();
    }
}
