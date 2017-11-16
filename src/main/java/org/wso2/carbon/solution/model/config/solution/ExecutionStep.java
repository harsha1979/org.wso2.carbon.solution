package org.wso2.carbon.solution.model.config.solution;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class ExecutionStep implements Serializable {

    private final static long serialVersionUID = 2178673577102053608L;
    private boolean enable;
    private String path;

    /**
     * No args constructor for use in serialization
     */
    public ExecutionStep() {
    }

    /**
     * @param path
     * @param enable
     */
    public ExecutionStep(boolean enable, String path) {
        super();
        this.enable = enable;
        this.path = path;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ExecutionStep) == false) {
            return false;
        }
        ExecutionStep rhs = ((ExecutionStep) other);
        return new EqualsBuilder().append(path, rhs.path).append(enable, rhs.enable).isEquals();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(path).append(enable).toHashCode();
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("enable", enable).append("path", path).toString();
    }
}
