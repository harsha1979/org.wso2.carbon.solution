
package org.wso2.carbon.solution.model.config.datasource;

import java.io.Serializable;
import java.util.Properties;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Datasource implements Serializable
{

    private String name;
    private String databaseType;
    private Properties properties;
    private final static long serialVersionUID = 3089645941630933456L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Datasource() {
    }

    /**
     * 
     * @param name
     * @param properties
     */
    public Datasource(String name, Properties properties) {
        super();
        this.name = name;
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", name).append("properties", properties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(properties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Datasource) == false) {
            return false;
        }
        Datasource rhs = ((Datasource) other);
        return new EqualsBuilder().append(name, rhs.name).append(properties, rhs.properties).isEquals();
    }

}
