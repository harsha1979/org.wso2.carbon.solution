package org.wso2.carbon.solution.installer;


import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.util.Constant;

public class InstallerFactory {
    private static InstallerFactory installerFactory = new InstallerFactory();

    public static InstallerFactory getInstance() {
        return InstallerFactory.installerFactory;
    }

    public Installer getInstaller(String server) throws CarbonSolutionException {
        if (server.equals(Constant.Server.IDENTITY_SERVER)) {
            return new IdentityServerInstaller();
        } else if (server.equals(Constant.Server.TOMCAT)) {
            return new TomcatInstaller();
        }
        throw new CarbonSolutionException("No provider found for " + server);
    }
}
