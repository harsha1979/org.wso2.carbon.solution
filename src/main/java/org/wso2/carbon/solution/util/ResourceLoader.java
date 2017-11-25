package org.wso2.carbon.solution.util;

import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.model.config.datasource.DataSourceConfigEntity;
import org.wso2.carbon.solution.model.config.datasource.Datasource;
import org.wso2.carbon.solution.model.config.server.Server;
import org.wso2.carbon.solution.model.config.server.ServerConfigEntity;
import org.wso2.carbon.solution.model.config.solution.SolutionConfig;
import org.wso2.carbon.solution.model.config.solution.SolutionConfigEntity;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.BeanAccess;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceLoader {


    public static Server getServerConfig(String serverName, String instance) throws CarbonSolutionException {
        Path resourcePathObj = Paths.get(Utility.RESOURCE_HOME, Constant.SERVERS_HOME, Constant
                .SERVER_CONFIG_FILE);
        ServerConfigEntity serverConfigEntity = loadResource(resourcePathObj, ServerConfigEntity.class);
        List<Server> servers = serverConfigEntity.getServers();
        for (Server server : servers) {
            if (server.getServerName().equals(serverName) && server.getInstance().equals(instance)) {
                return server;
            }
        }
        throw new CarbonSolutionException("No default server found for " + serverName);
    }

    public static SolutionConfig getSolutionConfig(String solution) throws CarbonSolutionException {
        Path resourcePathObj = Paths.get(Utility.RESOURCE_HOME, Constant.SOLUTION_HOME, solution, Constant
                .SOLUTION_CONFIG_FILE);
        SolutionConfigEntity solutionConfigEntity = loadResource(resourcePathObj, SolutionConfigEntity.class);
        return solutionConfigEntity.getSolutionConfig();
    }

    public static List<Datasource> getDataSourceConfig(String solution) throws CarbonSolutionException {
        Path resourcePathObj = Paths.get(Utility.RESOURCE_HOME, Constant.SOLUTION_HOME, solution, Constant
                .DATASOURCE_CONFIG_FILE);
        DataSourceConfigEntity dataSourceConfigEntity = loadResource(resourcePathObj, DataSourceConfigEntity.class);
        return dataSourceConfigEntity.getDatasources();
    }

    public static <T> T loadResource(String fileName, String resourcePath, Class<T> className)
            throws CarbonSolutionException {
        Path resourcePathObj = Paths.get(resourcePath, fileName);
        return loadResource(resourcePathObj, className);
    }

    public static <T> Map<String, T> loadResources(String resourcesPath, Class<T> className)
            throws CarbonSolutionException {
        Map<String, T> resourceMap = new HashMap<String, T>();
        Path resourcesPathObj = Paths.get(resourcesPath);
        String[] list = resourcesPathObj.toFile().list();
        for (String fileName : list) {
            if (fileName.endsWith("." + Constant.YAML_EXT)) {
                Path aResourcePath = Paths.get(resourcesPath, fileName);
                T resource = loadResource(aResourcePath, className);
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
                T resource = loadResource(aResourcePath, className);
                resourceMap.put(fileName, resource);
            }
        }
        return resourceMap;
    }

    public static <T> T loadResource(Path resourcePath, Class<T> className) throws CarbonSolutionException {
        T resource = null;
        try {
            Reader in = new InputStreamReader(Files.newInputStream(resourcePath), StandardCharsets.UTF_8);
            Yaml yaml = new Yaml();
            yaml.setBeanAccess(BeanAccess.PROPERTY);
            resource = yaml.loadAs(in, className);
            if (resource == null) {
                throw new CarbonSolutionException("Provider is not loaded correctly.");
            }
        } catch (Exception e) {
            String errorMessage = "Error occurred while loading the " + className
                                  + " yaml file, " +
                                  e.getMessage();
            throw new CarbonSolutionException(errorMessage, e);
        }
        return resource;
    }

    public static void main(String[] args) {
        try {
            /*IdentityProvider identityProvider = loadResource(
                    "facebook.yaml", "/home/harshat/wso2/demo-suite/project/org.wso2.carbon"
                                   + ".solution/src/main/resources/samples",
                    IdentityProvider.class);*/
            Utility.RESOURCE_HOME = "/home/harshat/wso2/demo-suite/project/org.wso2.carbon.solution/demo-resources";
            SolutionConfig solutionConfig = getSolutionConfig("solution-02");
            System.out.println(solutionConfig.toString());
        } catch (CarbonSolutionException e) {
            e.printStackTrace();
        }
    }
}
