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
package org.wso2.carbon.solution.deployer.tomcat;


import org.apache.commons.io.FileUtils;
import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.endpoint.tomcat.TomcatServer;
import org.wso2.carbon.solution.model.server.Server;
import org.wso2.carbon.solution.model.server.tomcat.TomcatServerArtifact;
import org.wso2.carbon.solution.model.solution.DeployerDependency;
import org.wso2.carbon.solution.model.solution.SolutionConfig;
import org.wso2.carbon.solution.util.ApplicationUtility;
import org.wso2.carbon.solution.util.Constant;
import org.wso2.carbon.solution.util.ResourceManager;
import org.wso2.carbon.solution.util.ZipDir;
import org.wso2.carbon.solution.util.ZipUtility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

//Under construction
public class WebAppDeployer {

    private static final String FRANCESCA = "francesca.com";
    private static final String LEBENS = "lebens.com";


    public void deploy(TomcatServerArtifact tomcatServerArtifact, Server server) throws CarbonSolutionException {

        if (tomcatServerArtifact.getWebApp().indexOf(FRANCESCA) != -1) {
            deployFrancescaWebApp(tomcatServerArtifact, server);
        } else if (tomcatServerArtifact.getWebApp().indexOf(LEBENS) != -1) {
            deployLebensWebApp(tomcatServerArtifact, server);
        }
    }


    private void deployFrancescaWebApp(TomcatServerArtifact tomcatServerArtifact, Server server)
            throws CarbonSolutionException {

        String solutionWebApp = tomcatServerArtifact.getSolution() + "-" +
                                tomcatServerArtifact.getWebApp().replace(Constant.WAR_EXT, "");
        String basePath = tomcatServerArtifact.getPath().substring(0, tomcatServerArtifact.getPath().lastIndexOf
                (tomcatServerArtifact.getWebApp()));
        String newPath = basePath + "out";
        String dirPath = ApplicationUtility.getSolutionHome() + File.separator + newPath + File.separator + FRANCESCA
                         + File
                                 .separator + solutionWebApp;
        try {
            String currentWebApp = ApplicationUtility.getSolutionHome() + File.separator + newPath + File.separator
                                   + FRANCESCA;
            FileUtils.deleteDirectory(new File(currentWebApp));
        } catch (Exception e) {
            throw new CarbonSolutionException("Error occurred while deployFrancescaWebApp,", e);
        }
        File outFolder = new File(dirPath);
        boolean mkdirs = outFolder.mkdirs();

        File fileTravCityOrig = new File(
                ApplicationUtility.getSolutionHome() + File.separator + basePath + File.separator +
                FRANCESCA + File
                        .separator + Constant.SSO_PROPERTIES);
        try {
            ZipUtility.unzip(ApplicationUtility.getCommonWebAppHome() + File.separator + FRANCESCA + Constant.WAR_EXT,
                             outFolder
                                     .getAbsolutePath());

            FileUtils.copyFile(fileTravCityOrig,
                               new File(dirPath + File.separator + Constant.WEB_INF + File
                                       .separator + Constant.CLASSES_FOLDER + File
                                                .separator + Constant.SSO_PROPERTIES));

            Properties spFileout = new Properties();
            String associatedFile = "";
            SolutionConfig solutionConfig = ResourceManager.getSolutionConfig(tomcatServerArtifact.getSolution());
            List<DeployerDependency> deployerDependencies = solutionConfig.getDeployerDependencies();
            for (DeployerDependency deployerDependency : deployerDependencies) {
                if ((tomcatServerArtifact.getSolution() + File.separator + deployerDependency.getDeployer())
                        .equals(tomcatServerArtifact.getPath())) {
                    String path = deployerDependency.getDependency()
                            .substring(0, deployerDependency.getDependency().lastIndexOf("/"));
                    associatedFile = deployerDependency.getDependency()
                            .substring(deployerDependency.getDependency().lastIndexOf("/") + 1);
                    String basePathfile = ApplicationUtility.getSolutionHome() + File.separator + tomcatServerArtifact
                            .getSolution() + File.separator + path + File.separator + Constant.OUT_PROPERTIES;
                    FileInputStream fileInputStream = new FileInputStream(basePathfile);
                    spFileout.load(fileInputStream);
                    break;
                }
            }

            Properties traveloctyOutFile = new Properties();
            FileInputStream fileInputStream = new FileInputStream(
                    dirPath + File.separator + Constant.WEB_INF + File.separator + Constant.CLASSES_FOLDER
                    + File.separator + Constant.SSO_PROPERTIES);
            traveloctyOutFile.load(fileInputStream);


            for (Map.Entry<Object, Object> objectEntry : spFileout.entrySet()) {
                String key = (String) objectEntry.getKey();
                String origKey = key.replace(associatedFile + "-", "");
                traveloctyOutFile.replace(origKey, objectEntry.getValue());
            }

            traveloctyOutFile.replace("SkipURIs", "/" + solutionWebApp + "/index.jsp");

            FileOutputStream outInputStream = new FileOutputStream(dirPath + File.separator + Constant.WEB_INF + File
                    .separator + Constant.CLASSES_FOLDER + File
                                                                           .separator + Constant.SSO_PROPERTIES);
            traveloctyOutFile.store(outInputStream, null);


            ZipDir.zip(dirPath, "war");

            deployToFileSystem(dirPath + Constant.WAR_EXT, server);
        } catch (Exception e) {
            throw new CarbonSolutionException("Error occurred while deployFrancescaWebApp.", e);
        }
    }


    private void deployLebensWebApp(TomcatServerArtifact tomcatServerArtifact, Server server)
            throws CarbonSolutionException {

        String solutionWebApp = tomcatServerArtifact.getSolution() + "-" +
                                tomcatServerArtifact.getWebApp().replace(Constant.WAR_EXT, "");
        String basePath = tomcatServerArtifact.getPath().substring(0, tomcatServerArtifact.getPath().lastIndexOf
                (tomcatServerArtifact.getWebApp()));
        String newPath = basePath + "out";
        String dirPath = ApplicationUtility.getSolutionHome() + File.separator + newPath + File.separator + LEBENS
                         + File
                                 .separator + solutionWebApp;
        try {
            String currentWebApp = ApplicationUtility.getSolutionHome() + File.separator + newPath + File.separator
                                   + LEBENS;
            FileUtils.deleteDirectory(new File(currentWebApp));
        } catch (Exception e) {
            throw new CarbonSolutionException("Error occurred while deployLebensWebApp,", e);
        }
        File outFolder = new File(dirPath);
        boolean mkdirs = outFolder.mkdirs();

        File fileTravCityOrig = new File(
                ApplicationUtility.getSolutionHome() + File.separator + basePath + File.separator +
                LEBENS + File.separator + Constant.SSO_PROPERTIES);
        try {
            ZipUtility.unzip(ApplicationUtility.getCommonWebAppHome() + File.separator + LEBENS +
                             Constant.WAR_EXT, outFolder.getAbsolutePath());

            FileUtils.copyFile(fileTravCityOrig, new File(dirPath + File.separator + Constant.WEB_INF + File.separator +
                                                          Constant.CLASSES_FOLDER + File.separator
                                                          + Constant.SSO_PROPERTIES));
            Properties spFileout = new Properties();
            String associatedFile = "";
            SolutionConfig solutionConfig = ResourceManager.getSolutionConfig(tomcatServerArtifact.getSolution());
            List<DeployerDependency> deployerDependencies = solutionConfig.getDeployerDependencies();
            for (DeployerDependency deployerDependency : deployerDependencies) {
                if ((tomcatServerArtifact.getSolution() + File.separator + deployerDependency.getDeployer()).equals
                        (tomcatServerArtifact.getPath())) {
                    String path = deployerDependency.getDependency()
                            .substring(0, deployerDependency.getDependency().lastIndexOf("/"));
                    associatedFile = deployerDependency.getDependency()
                            .substring(deployerDependency.getDependency().lastIndexOf("/") + 1);
                    String basePathfile = ApplicationUtility.getSolutionHome() + File.separator +
                                          tomcatServerArtifact.getSolution() + File.separator + path
                                          + File.separator + Constant.OUT_PROPERTIES;
                    FileInputStream fileInputStream = new FileInputStream(basePathfile);
                    spFileout.load(fileInputStream);
                    break;
                }
            }
            Properties traveloctyOutFile = new Properties();
            FileInputStream fileInputStream = new FileInputStream(dirPath + File.separator + Constant.WEB_INF + File
                    .separator + Constant.CLASSES_FOLDER + File.separator + Constant.SSO_PROPERTIES);
            traveloctyOutFile.load(fileInputStream);
            for (Map.Entry<Object, Object> objectEntry : spFileout.entrySet()) {
                String key = (String) objectEntry.getKey();
                String origKey = key.replace(associatedFile + "-", "");
                traveloctyOutFile.replace(origKey, objectEntry.getValue());
            }
            FileOutputStream outInputStream = new FileOutputStream(dirPath + File.separator + Constant.WEB_INF + File
                    .separator + Constant.CLASSES_FOLDER + File.separator + Constant.SSO_PROPERTIES);
            traveloctyOutFile.store(outInputStream, null);
            ZipDir.zip(dirPath, "war");
            deployToFileSystem(dirPath + Constant.WAR_EXT, server);
        } catch (Exception e) {
            throw new CarbonSolutionException("Error occurred while deployLebensWebApp.", e);
        }
    }


    private void deployToFileSystem(String file, Server server) throws CarbonSolutionException {
        File uploadFile1 = new File(file);
        TomcatServer tomcatServer = new TomcatServer(server);
        String deployPath = tomcatServer.getDeployPath();
        try {
            FileUtils.copyFile(uploadFile1, new File(deployPath + File.separator + uploadFile1.getName()));
        } catch (IOException e) {
            throw new CarbonSolutionException("Error occurred while copying file, " + file, e);
        }
    }
}
