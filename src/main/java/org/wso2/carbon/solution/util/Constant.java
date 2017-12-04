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

import java.io.File;

public class Constant {

    public static final String YAML_EXT = "yaml";
    public static final String SERVER_CONFIG_FILE = "server-config.yaml";
    public static final String DATASOURCE_CONFIG_FILE = "datasource-config.yaml";
    public static final String SOLUTION_CONFIG_FILE = "solution-config.yaml";
    public static final String SSO_PROPERTIES = "sso.properties";
    public static final String WAR_EXT = ".war";
    public static final String CLASSES_FOLDER = "classes";
    public static final String OUT_PROPERTIES = "out.properties";
    public static final String WEB_INF = "WEB-INF";


    public static class Tenant {
        public static final String CARBON_SUPER = "carbon.super";
    }

    public static class Solution {
        public static final String SOLUTION_EXECUTION_STATUS_INSTALL = "install";
        public static final String SOLUTION_EXECUTION_STATUS_UNINSTALL = "uninstall";
    }


    public static class ResourcePath {
        public static String RESOURCE_HOME_PATH = System.getProperty("user.dir") + File.separator +
                                                  ResourceFolder.RESOURCE_HOME_FOLDER;
        public static String SOLUTION_HOME_PATH = RESOURCE_HOME_PATH + File.separator +
                                                  ResourceFolder.SOLUTION_HOME_FOLDER;
    }

    public static class CommonResource {
        public static final String H2_DB = "WSO2CARBON_DB.h2.db";
    }

    public static class ResourceFolder {
        public static final String RESOURCE_HOME_FOLDER = "demo-resources";
        public static final String SOLUTION_HOME_FOLDER = "solutions";
        public static final String DATABASE_SERVER_FOLDER = "database-server";
        public static final String IDENTITY_PROVIDERS_FOLDER = "identity-providers";
        public static final String SERVICE_PROVIDERS_FOLDER = "service-providers";
        public static final String SERVERS_HOME_FOLDER = "servers";
        public static final String COMMON_RESOURCE_FOLDER = "common-resources";
        public static final String COMMON_RESOURCE_WEBAPPS_FOLDER = "webapps";
        public static final String COMMON_RESOURCE_DATABASE_FOLDER = "database";
        public static final String ROLE_PROVISION_FOLDER = "role-provision";
        public static final String H2_DATABASE_FOLDER = "h2-database";
    }
}
