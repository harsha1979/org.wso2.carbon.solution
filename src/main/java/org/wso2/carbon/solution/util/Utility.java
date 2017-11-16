package org.wso2.carbon.solution.util;


public class Utility {

    public static String RESOURCE_HOME = "";

    //#TODO:must change these..
    public static void setKeyStoreProperties() throws Exception {
        System.setProperty("javax.net.ssl.trustStore",
                           "/home/harshat/wso2/demo-suite/project/org.wso2.carbon"
                           + ".solution/demo-resources/servers/identity-server/trust-store/default-client-truststore"
                           + ".jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "wso2carbon");
        System.setProperty("javax.net.ssl.trustStoreType", "JKS");
    }
}
