package org.wso2.carbon.solution.model.config.solution;

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
