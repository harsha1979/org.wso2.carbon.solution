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
package org.wso2.carbon.solution.util;


import org.apache.axiom.om.util.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.model.server.iam.IdentityServerArtifact;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Application Utility Class.
 */
public class ApplicationUtility {
    private static Log log = LogFactory.getLog(ApplicationUtility.class);
    public static List<String> generatedFileList = new ArrayList<>();


    public static void cleanGeneratedFiles(){
        for (String file : generatedFileList) {
            File fileObj = new File(file);
            if(fileObj.exists()){
               /* if(fileObj.isDirectory()){
                    FileUtils.deleteDirectory(new File(currentWebApp));
                }else{

                }*/
            }
        }
    }
    /**
     * Build absolute path for given resources.
     *
     * @param resources
     * @return
     */
    public static String getAbsolutePath(String... resources) {
        StringBuilder fullPath = new StringBuilder();
        fullPath.append(Constant.ResourcePath.RESOURCE_HOME_PATH);
        for (String resource : resources) {
            fullPath.append(File.separator);
            fullPath.append(resource);
        }
        return fullPath.toString();
    }


    public static String getSolutionHome() {
        return Constant.ResourcePath.RESOURCE_HOME_PATH + File.separator + Constant.ResourceFolder.SOLUTION_HOME_FOLDER;
    }

    public static String getCommonWebAppHome() {
        return Constant.ResourcePath.RESOURCE_HOME_PATH + File.separator +
               Constant.ResourceFolder.COMMON_RESOURCE_FOLDER + File.separator
               + Constant.ResourceFolder.COMMON_RESOURCE_WEBAPPS_FOLDER;
    }

    public static String getCommonDataShemaHome() {
        return Constant.ResourcePath.RESOURCE_HOME_PATH + File.separator
               + Constant.ResourceFolder.COMMON_RESOURCE_FOLDER + File.separator
               + Constant.ResourceFolder.COMMON_RESOURCE_DATABASE_FOLDER;
    }

    public static String getBase64EncodedBasicAuthHeader(String userName, String password) {
        String concatenatedCredential = userName + ":" + password;
        byte[] byteValue = concatenatedCredential.getBytes();
        String encodedAuthHeader = Base64.encode(byteValue);
        encodedAuthHeader = "Basic " + encodedAuthHeader;
        return encodedAuthHeader;
    }

    public static String getPropertyFilePath(IdentityServerArtifact identityServerArtifact) {
        String base = getSolutionHome() + File.separator + identityServerArtifact.getResourcePath();
        if (identityServerArtifact.getArtifactFile() != null) {
            base = base.replace(identityServerArtifact.getArtifactFile(), "");
        }
        base = base + File.separator + "out.properties";
        return base;
    }

    public static void updateProperty(IdentityServerArtifact identityServerArtifact,
                                      Properties newProp) throws CarbonSolutionException {
        String propertyFilePath1 = getPropertyFilePath(identityServerArtifact);
        Properties prop = new Properties();

        FileInputStream fileInputStream = null;
        FileOutputStream output = null;
        try {
            fileInputStream = new FileInputStream(propertyFilePath1);
            prop.load(fileInputStream);

            Set<String> newPropKeys = newProp.stringPropertyNames();
            for (String newPropKey : newPropKeys) {
                prop.put(newPropKey, newProp.get(newPropKey));
            }
        } catch (IOException io) {
            throw new CarbonSolutionException("Error occurred while updateProperty.", io);
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    log.warn(e.getMessage());
                }
            }
        }
        try {
            output = new FileOutputStream(propertyFilePath1);
            // save properties to project root folder
            prop.store(output, null);
        } catch (IOException io) {
            throw new CarbonSolutionException("Error occurred while updateProperty.", io);
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    log.warn(e.getMessage());
                }
            }
        }
    }
}
