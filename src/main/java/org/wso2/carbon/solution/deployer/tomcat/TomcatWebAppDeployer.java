package org.wso2.carbon.solution.deployer.tomcat;


import org.wso2.carbon.solution.deployer.tomcat.config.TomcatServer;
import org.wso2.carbon.solution.installer.impl.TomcatInstaller;
import org.wso2.carbon.solution.model.config.server.Server;
import org.wso2.carbon.solution.util.Constant;
import org.wso2.carbon.solution.util.MultipartUtility;
import org.wso2.carbon.solution.util.Utility;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TomcatWebAppDeployer {

    public void deploy(TomcatInstaller.TomcatServerArtifact tomcatServerArtifact, Server server) {
        TomcatServer tomcatServer = new TomcatServer(server);
        String filePath = Utility.RESOURCE_HOME + File.separator + Constant.SOLUTION_HOME + File.separator +
                          tomcatServerArtifact.getPath();

        String charset = "UTF-8";
        File uploadFile1 = new File(filePath);
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
        }
    }
}
