package org.wso2.carbon.solution.deployer.tomcat.config;

import org.wso2.carbon.solution.model.config.server.Server;

//Under construction
public class TomcatServer {

    private Server server;

    public TomcatServer(Server server) {
        this.server = server;
    }

    public String getHTTPServerURL() {
        String url = "http://" + getHost();
        if (getPort() != 0) {
            url += ":" + getPort();
        }
        return url;
    }

    public String getHost() {
        return (String) server.getProperties().get("host");
    }

    public String getPassword() {
        return (String) server.getProperties().get("userName");
    }

    public int getPort() {
        return (Integer) server.getProperties().get("port");
    }

    public String getTrustStore() {
        return (String) server.getProperties().get("trustStore");
    }

    public String getUserName() {
        return (String) server.getProperties().get("userName");
    }
}
