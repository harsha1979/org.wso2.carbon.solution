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
package org.wso2.carbon.solution.model.iam.idp;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClaimConfig implements Serializable {

    private final static long serialVersionUID = 3106405509957807929L;
    private boolean isAlwaysSendMappedLocalSubjectId;
    private boolean isLocalClaimDialect;
    private String roleClaimURI;
    private String userClaimURI;
    private List<ClaimMapping> claimMappings = new ArrayList<ClaimMapping>();
    private List<IdpClaim> idpClaims = new ArrayList<IdpClaim>();

    /**
     * No args constructor for use in serialization
     */
    public ClaimConfig() {
    }

    /**
     * @param roleClaimURI
     * @param isLocalClaimDialect
     * @param userClaimURI
     * @param isAlwaysSendMappedLocalSubjectId
     * @param claimMappings
     * @param idpClaims
     */
    public ClaimConfig(boolean isAlwaysSendMappedLocalSubjectId,
                       boolean isLocalClaimDialect,
                       String roleClaimURI,
                       String userClaimURI,
                       List<ClaimMapping> claimMappings,
                       List<IdpClaim> idpClaims) {
        super();
        this.isAlwaysSendMappedLocalSubjectId = isAlwaysSendMappedLocalSubjectId;
        this.isLocalClaimDialect = isLocalClaimDialect;
        this.roleClaimURI = roleClaimURI;
        this.userClaimURI = userClaimURI;
        this.claimMappings = claimMappings;
        this.idpClaims = idpClaims;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ClaimConfig) == false) {
            return false;
        }
        ClaimConfig rhs = ((ClaimConfig) other);
        return new EqualsBuilder().append(roleClaimURI, rhs.roleClaimURI)
                .append(isLocalClaimDialect, rhs.isLocalClaimDialect).append(userClaimURI, rhs.userClaimURI)
                .append(isAlwaysSendMappedLocalSubjectId, rhs.isAlwaysSendMappedLocalSubjectId)
                .append(claimMappings, rhs.claimMappings).append(idpClaims, rhs.idpClaims).isEquals();
    }

    public List<ClaimMapping> getClaimMappings() {
        return claimMappings;
    }

    public void setClaimMappings(List<ClaimMapping> claimMappings) {
        this.claimMappings = claimMappings;
    }

    public List<IdpClaim> getIdpClaims() {
        return idpClaims;
    }

    public void setIdpClaims(List<IdpClaim> idpClaims) {
        this.idpClaims = idpClaims;
    }

    public String getRoleClaimURI() {
        return roleClaimURI;
    }

    public void setRoleClaimURI(String roleClaimURI) {
        this.roleClaimURI = roleClaimURI;
    }

    public String getUserClaimURI() {
        return userClaimURI;
    }

    public void setUserClaimURI(String userClaimURI) {
        this.userClaimURI = userClaimURI;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(roleClaimURI).append(isLocalClaimDialect).append(userClaimURI)
                .append(isAlwaysSendMappedLocalSubjectId).append(claimMappings).append(idpClaims).toHashCode();
    }

    public boolean isIsAlwaysSendMappedLocalSubjectId() {
        return isAlwaysSendMappedLocalSubjectId;
    }

    public void setIsAlwaysSendMappedLocalSubjectId(boolean isAlwaysSendMappedLocalSubjectId) {
        this.isAlwaysSendMappedLocalSubjectId = isAlwaysSendMappedLocalSubjectId;
    }

    public boolean isIsLocalClaimDialect() {
        return isLocalClaimDialect;
    }

    public void setIsLocalClaimDialect(boolean isLocalClaimDialect) {
        this.isLocalClaimDialect = isLocalClaimDialect;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("isAlwaysSendMappedLocalSubjectId", isAlwaysSendMappedLocalSubjectId)
                .append("isLocalClaimDialect", isLocalClaimDialect).append("roleClaimURI", roleClaimURI)
                .append("userClaimURI", userClaimURI).append("claimMappings", claimMappings)
                .append("idpClaims", idpClaims).toString();
    }
}
