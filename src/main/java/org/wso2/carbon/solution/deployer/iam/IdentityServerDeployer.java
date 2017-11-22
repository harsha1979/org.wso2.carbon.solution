package org.wso2.carbon.solution.deployer.iam;

import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.installer.impl.IdentityServerInstaller;
import org.wso2.carbon.solution.model.config.server.Server;
import org.wso2.carbon.solution.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


public abstract class IdentityServerDeployer {

    public abstract boolean canHandle(String artifactType);

    public  void deploy(IdentityServerInstaller.IdentityServerArtifact identityServerArtifact, Server server)
            throws CarbonSolutionException{
        String base = getPropertyFilePath(identityServerArtifact);

        File outputFile = new File(base);
        if(!outputFile.exists()){
            try {
                outputFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        doDeploy(identityServerArtifact, server);

    }

    public static String getPropertyFilePath(IdentityServerInstaller.IdentityServerArtifact identityServerArtifact) {
        String base = Utility.getSolutionHome() + File.separator + identityServerArtifact.getPath();
        if(identityServerArtifact.getArtifactFile() != null) {
            base = base.replace(identityServerArtifact.getArtifactFile(), "");
        }
        base = base + File.separator + "out.properties" ;
        return base;
    }

    protected abstract void doDeploy(IdentityServerInstaller.IdentityServerArtifact identityServerArtifact, Server
            server)
            throws CarbonSolutionException;

    public static void updateProperty(IdentityServerInstaller.IdentityServerArtifact identityServerArtifact , Properties newProp) {
        String propertyFilePath1 = getPropertyFilePath(identityServerArtifact);
        Properties prop = new Properties();

        FileInputStream fileInputStream = null;
        FileOutputStream output = null;
        try {
            fileInputStream = new FileInputStream(propertyFilePath1);
            prop.load(fileInputStream);

            /*Set<String> propKeys = prop.stringPropertyNames();
            for (String propKey : propKeys) {
                if (propKey.startsWith(identityServerArtifact.getArtifactFile())) {
                    prop.remove(propKey);
                }
            }*/

            Set<String> newPropKeys = newProp.stringPropertyNames();
            for (String newPropKey : newPropKeys) {
                prop.put(newPropKey, newProp.get(newPropKey));
            }

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            output = new FileOutputStream(propertyFilePath1);
            // save properties to project root folder
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
