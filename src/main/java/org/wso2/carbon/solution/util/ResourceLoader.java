package org.wso2.carbon.solution.util;

import org.wso2.carbon.config.ConfigProviderFactory;
import org.wso2.carbon.config.ConfigurationException;
import org.wso2.carbon.config.provider.ConfigProvider;
import org.wso2.carbon.solution.CarbonSolutionException;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.BeanAccess;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ResourceLoader {

    public static <T> T loadResource(String fileName, String resourcePath, Class<T> className) {
        Path resourcePathObj = Paths.get(resourcePath, fileName);
        return loadResource(resourcePathObj, className);
    }

    public static <T> Map<String, T> loadResources(String resourcesPath, Class<T> className) {
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

    public static <T> T loadResourcde(Path resourcePath, Class<T> className) {

        T parentConfiguration = null;
        try {
            ConfigProvider configProvider = ConfigProviderFactory.getConfigProvider(resourcePath, null);
            parentConfiguration = configProvider
                    .getConfigurationObject(className);
            return parentConfiguration;
        } catch (ConfigurationException e) {
        }
        return parentConfiguration;
    }

    public static <T> T loadResource(Path resourcePath, Class<T> className) {
        T resource = null;
        try {
            Reader in = new InputStreamReader(Files.newInputStream(resourcePath), StandardCharsets.UTF_8);
            Yaml yaml = new Yaml();
            yaml.setBeanAccess(BeanAccess.FIELD);
            resource = yaml.loadAs(in, className);
            if (resource == null) {
                throw new CarbonSolutionException("Provider is not loaded correctly.");
            }
        } catch (Exception e) {
            String errorMessage = "Error occurred while loading the " + className
                                  + " yaml file, " +
                                  e.getMessage();
            //throw new CarbonSolutionException(errorMessage, e);
        }
        return resource;
    }
}
