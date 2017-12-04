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

public class OutboundProvisioningConfig implements Serializable {

    private final static long serialVersionUID = -9102138262229867245L;
    private List<OutboundProvisioningConnector> outboundProvisioningConnectors
            = new ArrayList<OutboundProvisioningConnector>();

    /**
     * No args constructor for use in serialization
     */
    public OutboundProvisioningConfig() {
    }

    /**
     * @param outboundProvisioningConnectors
     */
    public OutboundProvisioningConfig(List<OutboundProvisioningConnector> outboundProvisioningConnectors) {
        super();
        this.outboundProvisioningConnectors = outboundProvisioningConnectors;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof OutboundProvisioningConfig) == false) {
            return false;
        }
        OutboundProvisioningConfig rhs = ((OutboundProvisioningConfig) other);
        return new EqualsBuilder().append(outboundProvisioningConnectors, rhs.outboundProvisioningConnectors)
                .isEquals();
    }

    public List<OutboundProvisioningConnector> getOutboundProvisioningConnectors() {
        return outboundProvisioningConnectors;
    }

    public void setOutboundProvisioningConnectors(List<OutboundProvisioningConnector> outboundProvisioningConnectors) {
        this.outboundProvisioningConnectors = outboundProvisioningConnectors;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(outboundProvisioningConnectors).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("outboundProvisioningConnectors", outboundProvisioningConnectors)
                .toString();
    }
}
