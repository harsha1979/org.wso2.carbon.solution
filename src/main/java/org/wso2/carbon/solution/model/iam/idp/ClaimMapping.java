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

public class ClaimMapping implements Serializable {

    private final static long serialVersionUID = 5414261006867507023L;
    private boolean isMandatory;
    private String defaultValue;
    private boolean isRequested;
    private LocalClaim localClaim;
    private RemoteClaim remoteClaim;

    /**
     * No args constructor for use in serialization
     */
    public ClaimMapping() {
    }

    /**
     * @param localClaim
     * @param remoteClaim
     * @param isMandatory
     * @param isRequested
     * @param defaultValue
     */
    public ClaimMapping(boolean isMandatory,
                        String defaultValue,
                        boolean isRequested,
                        LocalClaim localClaim,
                        RemoteClaim remoteClaim) {
        super();
        this.isMandatory = isMandatory;
        this.defaultValue = defaultValue;
        this.isRequested = isRequested;
        this.localClaim = localClaim;
        this.remoteClaim = remoteClaim;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ClaimMapping) == false) {
            return false;
        }
        ClaimMapping rhs = ((ClaimMapping) other);
        return new EqualsBuilder().append(localClaim, rhs.localClaim).append(remoteClaim, rhs.remoteClaim)
                .append(isMandatory, rhs.isMandatory).append(isRequested, rhs.isRequested)
                .append(defaultValue, rhs.defaultValue).isEquals();
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public LocalClaim getLocalClaim() {
        return localClaim;
    }

    public void setLocalClaim(LocalClaim localClaim) {
        this.localClaim = localClaim;
    }

    public RemoteClaim getRemoteClaim() {
        return remoteClaim;
    }

    public void setRemoteClaim(RemoteClaim remoteClaim) {
        this.remoteClaim = remoteClaim;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(localClaim).append(remoteClaim).append(isMandatory).append(isRequested)
                .append(defaultValue).toHashCode();
    }

    public boolean isIsMandatory() {
        return isMandatory;
    }

    public void setIsMandatory(boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    public boolean isIsRequested() {
        return isRequested;
    }

    public void setIsRequested(boolean isRequested) {
        this.isRequested = isRequested;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("isMandatory", isMandatory).append("defaultValue", defaultValue)
                .append("isRequested", isRequested).append("localClaim", localClaim).append("remoteClaim", remoteClaim)
                .toString();
    }
}
