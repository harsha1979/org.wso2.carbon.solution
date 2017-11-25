package org.wso2.carbon.solution.model.config.solution;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class ExecutionStep implements Serializable {

    private final static long serialVersionUID = 2178673577102053608L;
    private String action;
    private String path;

    /**
     * No args constructor for use in serialization
     */
    public ExecutionStep() {
    }

    /**
     * @param path
     * @param action
     */
    public ExecutionStep(String action, String path) {
        super();
        this.action = action;
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
        return new EqualsBuilder().append(path, rhs.path).append(action, rhs.action).isEquals();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(path).append(action).toHashCode();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("action", action).append("path", path).toString();
    }
}
