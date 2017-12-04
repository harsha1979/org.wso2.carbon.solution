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
package org.wso2.carbon.solution.endpoint.tomcat;

import org.wso2.carbon.solution.model.server.Server;

//Under construction
public class TomcatServer {

    private Server server;

    public TomcatServer(Server server) {
        this.server = server;
    }

    public String getDeployPath() {
        return (String) server.getProperties().get("deployPath");
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
