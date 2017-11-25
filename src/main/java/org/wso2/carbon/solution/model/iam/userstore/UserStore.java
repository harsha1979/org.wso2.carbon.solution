
package org.wso2.carbon.solution.model.iam.userstore;

import java.io.Serializable;
import java.util.Properties;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class UserStore implements Serializable
{

    private String userStoreDomain;
    private String dataSourceName;
    private Properties properties = new Properties();
    private final static long serialVersionUID = -145737589355878984L;

    /**
     * No args constructor for use in serialization
     *
     */
    public UserStore() {
    }

    /**
     *
     * @param userStoreDomain
     * @param properties
     * @param dataSourceName
     */
    public UserStore(String userStoreDomain, String dataSourceName, Properties properties) {
        super();
        this.userStoreDomain = userStoreDomain;
        this.dataSourceName = dataSourceName;
        this.properties = properties;
    }

    public String getUserStoreDomain() {
        return userStoreDomain;
    }

    public void setUserStoreDomain(String userStoreDomain) {
        this.userStoreDomain = userStoreDomain;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("userStoreDomain", userStoreDomain).append("dataSourceName", dataSourceName).append("properties", properties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(userStoreDomain).append(properties).append(dataSourceName).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof UserStore) == false) {
            return false;
        }
        UserStore rhs = ((UserStore) other);
        return new EqualsBuilder().append(userStoreDomain, rhs.userStoreDomain).append(properties, rhs.properties).append(dataSourceName, rhs.dataSourceName).isEquals();
    }

}