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

public class InboundAuthenticationConfig implements Serializable {

    private final static long serialVersionUID = -5005954450518681949L;
    private List<InboundAuthenticationRequestConfig> inboundAuthenticationRequestConfigs
            = new ArrayList<InboundAuthenticationRequestConfig>();

    /**
     * No args constructor for use in serialization
     */
    public InboundAuthenticationConfig() {
    }

    /**
     * @param inboundAuthenticationRequestConfigs
     */
    public InboundAuthenticationConfig(List<InboundAuthenticationRequestConfig> inboundAuthenticationRequestConfigs) {
        super();
        this.inboundAuthenticationRequestConfigs = inboundAuthenticationRequestConfigs;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof InboundAuthenticationConfig) == false) {
            return false;
        }
        InboundAuthenticationConfig rhs = ((InboundAuthenticationConfig) other);
        return new EqualsBuilder().append(inboundAuthenticationRequestConfigs, rhs.inboundAuthenticationRequestConfigs)
                .isEquals();
    }

    public List<InboundAuthenticationRequestConfig> getInboundAuthenticationRequestConfigs() {
        return inboundAuthenticationRequestConfigs;
    }

    public void setInboundAuthenticationRequestConfigs(List<InboundAuthenticationRequestConfig>
                                                               inboundAuthenticationRequestConfigs) {
        this.inboundAuthenticationRequestConfigs = inboundAuthenticationRequestConfigs;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(inboundAuthenticationRequestConfigs).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("inboundAuthenticationRequestConfigs", inboundAuthenticationRequestConfigs).toString();
    }
}
