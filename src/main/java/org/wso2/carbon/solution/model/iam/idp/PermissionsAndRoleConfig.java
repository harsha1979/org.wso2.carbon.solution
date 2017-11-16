package org.wso2.carbon.solution.model.iam.idp;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PermissionsAndRoleConfig implements Serializable {

    private final static long serialVersionUID = 1490049577124900653L;
    private List<String> idpRoles = new ArrayList<String>();
    private List<Permission> permissions = new ArrayList<Permission>();
    private List<RoleMapping> roleMappings = new ArrayList<RoleMapping>();

    /**
     * No args constructor for use in serialization
     */
    public PermissionsAndRoleConfig() {
    }

    /**
     * @param permissions
     * @param idpRoles
     * @param roleMappings
     */
    public PermissionsAndRoleConfig(List<String> idpRoles,
                                    List<Permission> permissions,
                                    List<RoleMapping> roleMappings) {
        super();
        this.idpRoles = idpRoles;
        this.permissions = permissions;
        this.roleMappings = roleMappings;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PermissionsAndRoleConfig) == false) {
            return false;
        }
        PermissionsAndRoleConfig rhs = ((PermissionsAndRoleConfig) other);
        return new EqualsBuilder().append(permissions, rhs.permissions).append(idpRoles, rhs.idpRoles)
                .append(roleMappings, rhs.roleMappings).isEquals();
    }

    public List<String> getIdpRoles() {
        return idpRoles;
    }

    public void setIdpRoles(List<String> idpRoles) {
        this.idpRoles = idpRoles;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<RoleMapping> getRoleMappings() {
        return roleMappings;
    }

    public void setRoleMappings(List<RoleMapping> roleMappings) {
        this.roleMappings = roleMappings;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(permissions).append(idpRoles).append(roleMappings).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("idpRoles", idpRoles).append("permissions", permissions)
                .append("roleMappings", roleMappings).toString();
    }
}
