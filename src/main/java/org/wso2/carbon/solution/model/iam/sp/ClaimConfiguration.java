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
package org.wso2.carbon.solution.model.iam.sp;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClaimConfiguration implements Serializable {

    private final static long serialVersionUID = -7923258236641834202L;
    private boolean alwaysSendMappedLocalSubjectId;
    private boolean localClaimDialect;
    private String roleClaimURI;
    private String userClaimURI;
    private List<ClaimMapping> claimMappings = new ArrayList<ClaimMapping>();

    /**
     * No args constructor for use in serialization
     */
    public ClaimConfiguration() {
    }

    /**
     * @param roleClaimURI
     * @param localClaimDialect
     * @param alwaysSendMappedLocalSubjectId
     * @param userClaimURI
     * @param claimMappings
     */
    public ClaimConfiguration(boolean alwaysSendMappedLocalSubjectId,
                              boolean localClaimDialect,
                              String roleClaimURI,
                              String userClaimURI,
                              List<ClaimMapping> claimMappings) {
        super();
        this.alwaysSendMappedLocalSubjectId = alwaysSendMappedLocalSubjectId;
        this.localClaimDialect = localClaimDialect;
        this.roleClaimURI = roleClaimURI;
        this.userClaimURI = userClaimURI;
        this.claimMappings = claimMappings;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ClaimConfiguration) == false) {
            return false;
        }
        ClaimConfiguration rhs = ((ClaimConfiguration) other);
        return new EqualsBuilder().append(roleClaimURI, rhs.roleClaimURI)
                .append(localClaimDialect, rhs.localClaimDialect)
                .append(alwaysSendMappedLocalSubjectId, rhs.alwaysSendMappedLocalSubjectId)
                .append(userClaimURI, rhs.userClaimURI).append(claimMappings, rhs.claimMappings).isEquals();
    }

    public List<ClaimMapping> getClaimMappings() {
        return claimMappings;
    }

    public void setClaimMappings(List<ClaimMapping> claimMappings) {
        this.claimMappings = claimMappings;
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
        return new HashCodeBuilder().append(roleClaimURI).append(localClaimDialect)
                .append(alwaysSendMappedLocalSubjectId).append(userClaimURI).append(claimMappings).toHashCode();
    }

    public boolean isAlwaysSendMappedLocalSubjectId() {
        return alwaysSendMappedLocalSubjectId;
    }

    public void setAlwaysSendMappedLocalSubjectId(boolean alwaysSendMappedLocalSubjectId) {
        this.alwaysSendMappedLocalSubjectId = alwaysSendMappedLocalSubjectId;
    }

    public boolean isLocalClaimDialect() {
        return localClaimDialect;
    }

    public void setLocalClaimDialect(boolean localClaimDialect) {
        this.localClaimDialect = localClaimDialect;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("alwaysSendMappedLocalSubjectId", alwaysSendMappedLocalSubjectId)
                .append("localClaimDialect", localClaimDialect).append("roleClaimURI", roleClaimURI)
                .append("userClaimURI", userClaimURI).append("claimMappings", claimMappings).toString();
    }
}
