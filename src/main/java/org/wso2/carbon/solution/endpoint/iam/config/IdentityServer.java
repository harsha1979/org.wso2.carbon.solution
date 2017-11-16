package org.wso2.carbon.solution.endpoint.iam.config;


import org.wso2.carbon.solution.model.config.server.Server;

public class IdentityServer {

    private Server server;

    public IdentityServer(Server server) {
        this.server = server;
    }

    public String getHTTPSServerURL() {
        String url = "https://" + getHost();
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
