package org.wso2.carbon.solution.installer;


import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.installer.impl.DatabaseServerInstaller;
import org.wso2.carbon.solution.installer.impl.IdentityServerInstaller;
import org.wso2.carbon.solution.installer.impl.TomcatInstaller;

import java.util.ArrayList;
import java.util.List;

public class InstallerFactory {

    private static InstallerFactory installerFactory = new InstallerFactory();
    private List<Installer> installerRegistry = new ArrayList<>();

    private InstallerFactory() {
        installerRegistry.add(new IdentityServerInstaller());
        installerRegistry.add(new TomcatInstaller());
        installerRegistry.add(new DatabaseServerInstaller());
    }

    public static InstallerFactory getInstance() {
        return InstallerFactory.installerFactory;
    }

    public Installer getInstaller(String path) throws CarbonSolutionException {

        for (Installer installer : installerRegistry) {
            if (installer.canHandle(path)) {
                return installer;
            }
        }
        throw new CarbonSolutionException("No installer found to the given path : " + path);
    }
}
