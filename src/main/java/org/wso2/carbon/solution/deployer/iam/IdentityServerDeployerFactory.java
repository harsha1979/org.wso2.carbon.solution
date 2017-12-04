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
package org.wso2.carbon.solution.deployer.iam;


import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.deployer.iam.impl.IdentityProviderDeployer;
import org.wso2.carbon.solution.deployer.iam.impl.RoleDeployer;
import org.wso2.carbon.solution.deployer.iam.impl.ServiceProviderDeployer;
import org.wso2.carbon.solution.deployer.iam.impl.UserStoreDeployer;

import java.util.ArrayList;
import java.util.List;

public class IdentityServerDeployerFactory {

    private static IdentityServerDeployerFactory identityServerDeployerFactory = new IdentityServerDeployerFactory();
    private List<IdentityServerDeployer> identityServerDeployerRegistry = new ArrayList<>();

    private IdentityServerDeployerFactory() {
        identityServerDeployerRegistry.add(new ServiceProviderDeployer());
        identityServerDeployerRegistry.add(new IdentityProviderDeployer());
        identityServerDeployerRegistry.add(new UserStoreDeployer());
        identityServerDeployerRegistry.add(new RoleDeployer());
    }

    public static IdentityServerDeployerFactory getInstance() {
        return IdentityServerDeployerFactory.identityServerDeployerFactory;
    }

    public IdentityServerDeployer getDeployer(String artifactType)
            throws CarbonSolutionException {
        for (IdentityServerDeployer identityServerDeployer : identityServerDeployerRegistry) {
            if (identityServerDeployer.canHandle(artifactType)) {
                return identityServerDeployer;
            }
        }
        throw new CarbonSolutionException(
                "No deployer found to the given artifact type : " + artifactType);
    }
}
