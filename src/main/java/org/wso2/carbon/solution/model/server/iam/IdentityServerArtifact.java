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

package org.wso2.carbon.solution.model.server.iam;

import org.apache.commons.lang.StringUtils;
import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.util.Constant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * IdentityServerArtifact hold the resource path and read the identity server specific context from the path.
 */
public class IdentityServerArtifact {

    private String resourcePath;
    private String solution;
    private String instanceName;
    private String tenantDomain;
    private String artifactType;
    private String artifactFile;

    /**
     * Split the resource path and read the values to fill the IdentityServerArtifact object itself.
     * <p>
     * String ex : solution-01/identity-server/default/carbon.super/user-stores/active-directory.yaml
     * <p>
     * solution-01 => Solution Name.
     * identity-server => Server Type.
     * default => Server Instance.
     * carbon.super => Tenant Domain.
     * user-stores => Artifact Type.
     * active-directory.yaml => Artifact File Name or defined * to all the files.
     *
     * @param resourcePath
     * @throws CarbonSolutionException
     */
    public static List<IdentityServerArtifact> load(String resourcePath) throws CarbonSolutionException {
        List<IdentityServerArtifact> identityServerArtifactList = new ArrayList<>();

        if (StringUtils.isNotEmpty(resourcePath)) {
            resourcePath = resourcePath.trim();
            if (resourcePath.endsWith("*")) {
                resourcePath = resourcePath.substring(0, resourcePath.lastIndexOf("*")).substring(0, resourcePath
                        .lastIndexOf("/"));
            }

            String[] split = resourcePath.split(File.separator);
            if (split != null && split.length >= 5) {
                if (split.length == 5) {
                    String absoluteResourcePath = Constant.ResourcePath.SOLUTION_HOME_PATH + File.separator +
                                                  resourcePath;
                    File resourceFolder = new File(absoluteResourcePath);
                    File[] resourceFileList = resourceFolder.listFiles();
                    for (File resourceFile : resourceFileList) {
                        if (!resourceFile.getName().equals("out.properties")) {
                            String fileResourcePath = resourcePath + File.separator + resourceFile.getName();
                            IdentityServerArtifact identityServerArtifact = loadArtifact(fileResourcePath);
                            identityServerArtifactList.add(identityServerArtifact);
                        }
                    }
                } else {
                    IdentityServerArtifact identityServerArtifact = loadArtifact(resourcePath);
                    identityServerArtifactList.add(identityServerArtifact);
                }
            }
        }
        return identityServerArtifactList;
    }

    /**
     * Load IdentityServerArtifact for a given resource path.
     *
     * @param resourcePath
     * @return
     */
    private static IdentityServerArtifact loadArtifact(String resourcePath) {
        String[] split = resourcePath.split(File.separator);
        IdentityServerArtifact identityServerArtifact = new IdentityServerArtifact();
        identityServerArtifact.setResourcePath(resourcePath);
        identityServerArtifact.setSolution(split[0]);
        identityServerArtifact.setInstanceName(split[2]);
        identityServerArtifact.setTenantDomain(split[3]);
        identityServerArtifact.setArtifactType(split[4]);
        identityServerArtifact.setArtifactFile(split[5]);
        return identityServerArtifact;
    }

    public String getArtifactFile() {
        return artifactFile;
    }

    public void setArtifactFile(String artifactFile) {
        this.artifactFile = artifactFile;
    }

    public String getArtifactType() {
        return artifactType;
    }

    public void setArtifactType(String artifactType) {
        this.artifactType = artifactType;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getTenantDomain() {
        return tenantDomain;
    }

    public void setTenantDomain(String tenantDomain) {
        this.tenantDomain = tenantDomain;
    }
}
