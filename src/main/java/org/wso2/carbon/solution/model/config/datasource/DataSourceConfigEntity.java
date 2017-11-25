
package org.wso2.carbon.solution.model.config.datasource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class DataSourceConfigEntity implements Serializable
{

    private List<Datasource> datasources = new ArrayList<Datasource>();
    private final static long serialVersionUID = -2457071837544876906L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DataSourceConfigEntity() {
    }

    /**
     * 
     * @param datasources
     */
    public DataSourceConfigEntity(List<Datasource> datasources) {
        super();
        this.datasources = datasources;
    }

    public List<Datasource> getDatasources() {
        return datasources;
    }

    public void setDatasources(List<Datasource> datasources) {
        this.datasources = datasources;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("datasources", datasources).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(datasources).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DataSourceConfigEntity) == false) {
            return false;
        }
        DataSourceConfigEntity rhs = ((DataSourceConfigEntity) other);
        return new EqualsBuilder().append(datasources, rhs.datasources).isEquals();
    }

}
