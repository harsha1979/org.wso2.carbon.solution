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

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.endpoint.iam.config.IdentityServer;
import org.wso2.carbon.solution.model.datasource.DataSourceConfigEntity;
import org.wso2.carbon.solution.model.datasource.Datasource;
import org.wso2.carbon.solution.model.server.Server;
import org.wso2.carbon.solution.model.server.ServerConfigEntity;
import org.wso2.carbon.solution.model.solution.SolutionConfig;
import org.wso2.carbon.solution.model.solution.SolutionConfigEntity;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.BeanAccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ResourceManager is load all the resources for known path or given path.
 */
public class ResourceManager {
    private static Log log = LogFactory.getLog(ResourceManager.class);

    /**
     * Load keystores for give server configuration.
     *
     * @param serverName
     * @param instance
     * @throws CarbonSolutionException
     */
    public static void loadKeyStores(String serverName, String instance) throws CarbonSolutionException {

        Server serverConfig = getServerConfig(serverName, instance);
        IdentityServer identityServer = new IdentityServer(serverConfig);

        Path resourcePath = Paths.get(Constant.ResourcePath.RESOURCE_HOME_PATH, identityServer.getTrustStore());
        log.info("Loading key store path in, " + resourcePath.toString());

        System.setProperty("javax.net.ssl.trustStore", resourcePath.toString());
        System.setProperty("javax.net.ssl.trustStorePassword", identityServer.getTrustStorePassword());
        System.setProperty("javax.net.ssl.trustStoreType", "JKS");
    }

    /**
     * Read the config file under given solution folder.
     *
     * @param solution
     * @return SolutionConfig
     * @throws CarbonSolutionException
     */
    public static SolutionConfig getSolutionConfig(String solution) throws CarbonSolutionException {
        Path resourcePath = Paths.get(Constant.ResourcePath.SOLUTION_HOME_PATH,
                                      solution, Constant.SOLUTION_CONFIG_FILE);
        SolutionConfigEntity solutionConfigEntity = loadYAMLResource(resourcePath, SolutionConfigEntity.class);
        if (solutionConfigEntity.getSolutionConfig() == null) {
            throw new CarbonSolutionException("SolutionConfig was not loaded, " + resourcePath.toString());
        }
        return solutionConfigEntity.getSolutionConfig();
    }

    /**
     * Read a resource under given path and load to the given entity.
     *
     * @param resourcePath
     * @param className
     * @param <T>
     * @return
     * @throws CarbonSolutionException
     */
    public static <T> T loadYAMLResource(Path resourcePath, Class<T> className) throws CarbonSolutionException {

        if (!resourcePath.toFile().exists()) {
            String errorMessage = "Given resource path does not exists, " + resourcePath.toString();
            throw new CarbonSolutionException(errorMessage);
        }
        log.debug("Reading YAML resource in " + resourcePath);
        T resource;
        try {
            Reader in = new InputStreamReader(Files.newInputStream(resourcePath), StandardCharsets.UTF_8);
            Yaml yaml = new Yaml();
            yaml.setBeanAccess(BeanAccess.PROPERTY);
            resource = yaml.loadAs(in, className);
            if (resource == null) {
                throw new CarbonSolutionException("The resource was not loaded successfully, " + resourcePath);
            }
        } catch (Exception e) {
            String errorMessage = "Error occurred while loading the resource, " +
                                  resourcePath + " cause, " + e.getMessage();
            log.error(errorMessage);
            throw new CarbonSolutionException(errorMessage, e);
        }
        return resource;
    }

    /**
     * Read ServerConfig for all the solutions.
     *
     * @param serverName
     * @param instance
     * @return
     * @throws CarbonSolutionException
     */
    public static Server getServerConfig(String serverName, String instance) throws CarbonSolutionException {
        Path resourcePathObj = Paths
                .get(Constant.ResourcePath.RESOURCE_HOME_PATH, Constant.ResourceFolder.SERVERS_HOME_FOLDER,
                     Constant
                             .SERVER_CONFIG_FILE);
        ServerConfigEntity serverConfigEntity = loadYAMLResource(resourcePathObj, ServerConfigEntity.class);
        List<Server> servers = serverConfigEntity.getServers();
        for (Server server : servers) {
            if (StringUtils.isNotEmpty(server.getServerName()) && server.getServerName().equals(serverName) && server
                    .getInstance().equals(instance)) {
                return server;
            }
        }
        throw new CarbonSolutionException("No default server found for " + serverName);
    }


    public static List<Datasource> getDataSourceConfig(String solution) throws CarbonSolutionException {
        Path resourcePathObj = Paths
                .get(Constant.ResourcePath.RESOURCE_HOME_PATH, Constant.ResourceFolder.SOLUTION_HOME_FOLDER, solution,
                     Constant
                             .DATASOURCE_CONFIG_FILE);
        DataSourceConfigEntity dataSourceConfigEntity = loadYAMLResource(resourcePathObj, DataSourceConfigEntity.class);
        return dataSourceConfigEntity.getDatasources();
    }

    public static String getFileContent(String resourcePath) throws CarbonSolutionException {
        String content = "";
        try {
            content = IOUtils.toString(new FileInputStream(new File(resourcePath)));
        } catch (Exception e) {
            throw new CarbonSolutionException("Error occurred while getFileContent.", e);
        }
        return content;
    }

    public static <T> T loadYAMLResource(String fileName, String resourcePath, Class<T> className)
            throws CarbonSolutionException {
        Path resourcePathObj = Paths.get(resourcePath, fileName);
        return loadYAMLResource(resourcePathObj, className);
    }

    public static <T> Map<String, T> loadResources(String resourcesPath, Class<T> className)
            throws CarbonSolutionException {
        Map<String, T> resourceMap = new HashMap<String, T>();
        Path resourcesPathObj = Paths.get(resourcesPath);
        String[] list = resourcesPathObj.toFile().list();
        for (String fileName : list) {
            if (fileName.endsWith("." + Constant.YAML_EXT)) {
                Path aResourcePath = Paths.get(resourcesPath, fileName);
                T resource = loadYAMLResource(aResourcePath, className);
                resourceMap.put(fileName, resource);
            }
        }
        return resourceMap;
    }

    public static <T> Map<String, T> loadResources(Path resourcesPathObj, Class<T> className)
            throws CarbonSolutionException {
        Map<String, T> resourceMap = new HashMap<String, T>();
        String[] list = resourcesPathObj.toFile().list();
        for (String fileName : list) {
            if (fileName.endsWith("." + Constant.YAML_EXT)) {
                Path aResourcePath = Paths.get(resourcesPathObj.toString(), fileName);
                T resource = loadYAMLResource(aResourcePath, className);
                resourceMap.put(fileName, resource);
            }
        }
        return resourceMap;
    }


    public static void main(String[] args) {
        try {
            /*IdentityProvider identityProvider = loadYAMLResource(
                    "facebook.yaml", "/home/harshat/wso2/demo-suite/project/org.wso2.carbon"
                                   + ".solution/src/main/resources/samples",
                    IdentityProvider.class);*/
            Constant.ResourcePath.RESOURCE_HOME_PATH
                    = "/home/harshat/wso2/demo-suite/project/org.wso2.carbon.solution/demo-resources";
            SolutionConfig solutionConfig = getSolutionConfig("solution-02");
            System.out.println(solutionConfig.toString());
        } catch (CarbonSolutionException e) {

        }
    }
}
