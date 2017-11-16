package org.wso2.carbon.solution.model.config.solution;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class SolutionConfigEntity implements Serializable {

    private final static long serialVersionUID = 8990744800562065652L;
    private SolutionConfig solutionConfig;

    /**
     * No args constructor for use in serialization
     */
    public SolutionConfigEntity() {
    }

    /**
     * @param solutionConfig
     */
    public SolutionConfigEntity(SolutionConfig solutionConfig) {
        super();
        this.solutionConfig = solutionConfig;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SolutionConfigEntity) == false) {
            return false;
        }
        SolutionConfigEntity rhs = ((SolutionConfigEntity) other);
        return new EqualsBuilder().append(solutionConfig, rhs.solutionConfig).isEquals();
    }

    public SolutionConfig getSolutionConfig() {
        return solutionConfig;
    }

    public void setSolutionConfig(SolutionConfig solutionConfig) {
        this.solutionConfig = solutionConfig;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(solutionConfig).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("solutionConfig", solutionConfig).toString();
    }
}
