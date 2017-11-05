package org.wso2.carbon.solution.installer;

public class TomcatInstaller implements Installer {
    public void install(String solution) {

    }
/*
    public static IdentityServer getIdentityServerConfig(String instance) throws CarbonSolutionException {
        Path tenantPath = Paths.get( Utility.RESOURCE_BASE , Constant.SERVER_CONFIG );
        ServerConfig serverConfig = ResourceLoader
                .loadResource(tenantPath, ServerConfig.class);
        List<IdentityServer> identityServer = serverConfig.getIdentityServer();
        for (IdentityServer server : identityServer) {
            if(server.getInstance().equals(instance)){
                return server ;
            }
        }
        throw new CarbonSolutionException("No Identity Server config found for given instance, " + instance);
    }*/
}
