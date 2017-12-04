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

package org.wso2.carbon.solution.deployer.iam.impl.oauth2;

/**
 * OAuth2 configs and default values.
 */
public enum OAuth2Config {

    OAUTH_VERSION("OAuthVersion", "OAuth-2.0"),
    APPLICATION_NAME("applicationName"),
    CALLBACK_URL("callbackUrl"),
    GRANT_TYPES("grantTypes", "authorization_code implicit password client_credentials refresh_token "
                              + "urn:ietf:params:oauth:grant-type:saml2-bearer "
                              + "urn:ietf:params:oauth:grant-type:jwt-bearer "
                              + "iwa:ntlm"),
    OAUTH_CONSUMER_KEY("oauthConsumerKey"),
    OAUTH_CONSUMER_SECRET("oauthConsumerSecret"),
    PKCE_MANDATORY("pkceMandatory", "false"),
    PKCE_SUPPORT_PLAN("pkceSupportPlain", "false"),
    STATE("state"),
    REFRESH_TOKEN_EXPIRE_TIME("refreshTokenExpiryTime", "84600"),
    USER_ACCESS_TOKEN_EXPIRE_TIME("userAccessTokenExpiryTime", "3600"),
    APPLICATION_ACCESS_TOKEN_EXPIRE_TIME("applicationAccessTokenExpiryTime", "3600"),
    USER_NAME("username");

    private String configName = "";
    private String defaultValue = "";

    private OAuth2Config(String configName, String defaultValue) {
        this.configName = configName;
        this.defaultValue = defaultValue;
    }

    private OAuth2Config(String configName) {
        this.configName = configName;
    }

    public String getConfigName() {
        return configName;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
