package org.wso2.carbon.solution.deployer.tomcat;


import org.apache.commons.io.FileUtils;
import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.deployer.tomcat.config.TomcatServer;
import org.wso2.carbon.solution.installer.impl.TomcatInstaller;
import org.wso2.carbon.solution.model.config.server.Server;
import org.wso2.carbon.solution.model.config.solution.DeployerDependency;
import org.wso2.carbon.solution.model.config.solution.SolutionConfig;
import org.wso2.carbon.solution.util.MultipartUtility;
import org.wso2.carbon.solution.util.ResourceLoader;
import org.wso2.carbon.solution.util.Utility;
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
public class TomcatWebAppDeployer {

    private static final String TRAVELOCITY = "travelocity.com" ;
    private static final String PLAYGROUND = "playground2" ;


    public void deploy(TomcatInstaller.TomcatServerArtifact tomcatServerArtifact, Server server) {


        if(tomcatServerArtifact.getWebApp().indexOf(TRAVELOCITY) != -1){
            deployTravelocityWebApp(tomcatServerArtifact, server);
        }else if(tomcatServerArtifact.getWebApp().indexOf(PLAYGROUND) != -1){
            deployPlaygroundWebApp(tomcatServerArtifact, server);
        }

    }

    private void deployTravelocityWebApp(TomcatInstaller.TomcatServerArtifact tomcatServerArtifact, Server server){

        String solutionWebApp = tomcatServerArtifact.getSolution() + "-" +
                                tomcatServerArtifact.getWebApp().replace(".war", "");
        String basePath = tomcatServerArtifact.getPath().substring(0, tomcatServerArtifact.getPath().lastIndexOf
                (tomcatServerArtifact.getWebApp()));
        String newPath = basePath +  "out" ;
        String dirPath = Utility.getSolutionHome()  + File.separator + newPath + File.separator + TRAVELOCITY + File
                .separator + solutionWebApp ;
        File outFolder = new File(dirPath);
        outFolder.mkdirs();
        File fileTravCityOrig = new File(Utility.getSolutionHome()  + File.separator + basePath + File.separator +
                                                                                                 TRAVELOCITY + File
                .separator +"travelocity.properties");
        try {
            ZipUtility.unzip(Utility.getCommonWebAppHome() + File.separator + TRAVELOCITY + ".war", outFolder
                    .getAbsolutePath());

            FileUtils.copyFile(fileTravCityOrig,
                               new File(dirPath + File.separator + "WEB-INF" + File
                                       .separator + "classes" + File
                                                .separator + "travelocity.properties"));

            Properties spFileout = new Properties();
            String associatedFile = "";
            SolutionConfig solutionConfig = ResourceLoader.getSolutionConfig(tomcatServerArtifact.getSolution());
            List<DeployerDependency> deployerDependencies = solutionConfig.getDeployerDependencies();
            for (DeployerDependency deployerDependency : deployerDependencies) {
                if((tomcatServerArtifact.getSolution() + File.separator + deployerDependency.getDeployer()).equals
                        (tomcatServerArtifact
                                                                                                     .getPath())){
                    String path = deployerDependency.getDependency()
                            .substring(0, deployerDependency.getDependency().lastIndexOf
                                    ("/"));
                    associatedFile  = deployerDependency.getDependency()
                            .substring(deployerDependency.getDependency().lastIndexOf
                                    ("/")+1);
                    String basePathfile = Utility.getSolutionHome() + File.separator + tomcatServerArtifact.getSolution
                            () + File
                            .separator + path
                                      + File.separator + "out.properties" ;
                    FileInputStream fileInputStream = new FileInputStream(basePathfile);
                    spFileout.load(fileInputStream);
                    break;

                }
            }

            Properties traveloctyOutFile = new Properties();
            FileInputStream fileInputStream = new FileInputStream(dirPath + File.separator + "WEB-INF" + File
                    .separator + "classes" + File
                                                                          .separator + "travelocity.properties");
            traveloctyOutFile.load(fileInputStream);



            for (Map.Entry<Object, Object> objectEntry : spFileout.entrySet()) {
                String key = (String)objectEntry.getKey() ;
                String origKey = key.replace(associatedFile + "-", "");
                traveloctyOutFile.replace(origKey, objectEntry.getValue());
            }

            traveloctyOutFile.replace("SkipURIs","/"+solutionWebApp+"/index.jsp");

            FileOutputStream outInputStream = new FileOutputStream(dirPath + File.separator + "WEB-INF" + File
                    .separator + "classes" + File
                                                                          .separator + "travelocity.properties");
            traveloctyOutFile.store(outInputStream, null);


            ZipDir.zip(dirPath, "war");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CarbonSolutionException e) {
            e.printStackTrace();
        }


        deploy(dirPath+".war", server);

    }
    private void deployPlaygroundWebApp(TomcatInstaller.TomcatServerArtifact tomcatServerArtifact, Server server){

        String solutionWebApp = tomcatServerArtifact.getSolution() + "-" +
                                tomcatServerArtifact.getWebApp().replace(".war", "");
        String basePath = tomcatServerArtifact.getPath().substring(0, tomcatServerArtifact.getPath().lastIndexOf
                (tomcatServerArtifact.getWebApp()));
        String newPath = basePath +  "out" ;
        String dirPath = Utility.getSolutionHome()  + File.separator + newPath + File.separator + PLAYGROUND + File
                .separator + solutionWebApp ;
        File outFolder = new File(dirPath);
        outFolder.mkdirs();
        File fileTravCityOrig = new File(Utility.getSolutionHome()  + File.separator + basePath + File.separator +
                                         PLAYGROUND + File
                                                 .separator +"playground2.properties");
        try {
            ZipUtility.unzip(Utility.getCommonWebAppHome() + File.separator + PLAYGROUND + ".war", outFolder
                    .getAbsolutePath());

            FileUtils.copyFile(fileTravCityOrig,
                               new File(dirPath + File.separator + "WEB-INF" + File
                                       .separator + "classes" + File
                                                .separator + "playground2.properties"));

            Properties spFileout = new Properties();
            String associatedFile = "";
            SolutionConfig solutionConfig = ResourceLoader.getSolutionConfig(tomcatServerArtifact.getSolution());
            List<DeployerDependency> deployerDependencies = solutionConfig.getDeployerDependencies();
            for (DeployerDependency deployerDependency : deployerDependencies) {
                if((tomcatServerArtifact.getSolution() + File.separator + deployerDependency.getDeployer()).equals
                        (tomcatServerArtifact
                                 .getPath())){
                    String path = deployerDependency.getDependency()
                            .substring(0, deployerDependency.getDependency().lastIndexOf
                                    ("/"));
                    associatedFile  = deployerDependency.getDependency()
                            .substring(deployerDependency.getDependency().lastIndexOf
                                    ("/")+1);
                    String basePathfile = Utility.getSolutionHome() + File.separator + tomcatServerArtifact.getSolution
                            () + File
                                                  .separator + path
                                          + File.separator + "out.properties" ;
                    FileInputStream fileInputStream = new FileInputStream(basePathfile);
                    spFileout.load(fileInputStream);
                    break;

                }
            }

            Properties traveloctyOutFile = new Properties();
            FileInputStream fileInputStream = new FileInputStream(dirPath + File.separator + "WEB-INF" + File
                    .separator + "classes" + File
                                                                          .separator + "playground2.properties");
            traveloctyOutFile.load(fileInputStream);



            for (Map.Entry<Object, Object> objectEntry : spFileout.entrySet()) {
                String key = (String)objectEntry.getKey() ;
                String origKey = key.replace(associatedFile + "-OAUTH2-", "");
                traveloctyOutFile.replace(origKey, objectEntry.getValue());
            }


            FileOutputStream outInputStream = new FileOutputStream(dirPath + File.separator + "WEB-INF" + File
                    .separator + "classes" + File
                                                                           .separator + "playground2.properties");
            traveloctyOutFile.store(outInputStream, null);


            ZipDir.zip(dirPath, "war");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CarbonSolutionException e) {
            e.printStackTrace();
        }

        deploy(dirPath+".war", server);
    }

    private void deploy(String file,  Server server){



        TomcatServer tomcatServer = new TomcatServer(server);
        String charset = "UTF-8";
        File uploadFile1 = new File(file);

        unDeploy(uploadFile1.getName(), server);

        String requestURL = tomcatServer.getHTTPServerURL() + "/manager/html/upload";
        try {
            MultipartUtility multipart = new MultipartUtility(requestURL, charset);
            multipart.addHeaderField("User-Agent", "CodeJava");
            multipart.addHeaderField("Test-Header", "Header-Value");
            multipart.addFilePart("deployWar", uploadFile1);
            List<String> response = multipart.finish();
            System.out.println("SERVER REPLIED:");
            for (String line : response) {
                 System.out.println(line);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }finally {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void unDeploy(String name,  Server server){
        TomcatServer tomcatServer = new TomcatServer(server);
        String charset = "UTF-8";
        String requestURL = tomcatServer.getHTTPServerURL() + "/manager/html/undeploy?path=/"+ name.replace(".war","");
        try {
            MultipartUtility multipart = new MultipartUtility(requestURL, charset);
            List<String> response = multipart.finish();
            System.out.println("SERVER REPLIED:");
            for (String line : response) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }finally {

        }
    }
}
