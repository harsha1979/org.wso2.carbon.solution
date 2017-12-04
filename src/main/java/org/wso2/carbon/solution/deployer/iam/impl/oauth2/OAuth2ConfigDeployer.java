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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceStub;
import org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerAppDTO;
import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.deployer.iam.impl.saml.SAMLConfigDeployer;
import org.wso2.carbon.solution.endpoint.iam.IdentityServerAdminClient;
import org.wso2.carbon.solution.endpoint.iam.config.IdentityServer;
import org.wso2.carbon.solution.model.iam.sp.InboundAuthenticationConfig;
import org.wso2.carbon.solution.model.iam.sp.InboundAuthenticationRequestConfig;
import org.wso2.carbon.solution.model.iam.sp.Property;
import org.wso2.carbon.solution.model.server.Server;
import org.wso2.carbon.solution.model.server.iam.IdentityServerArtifact;
import org.wso2.carbon.solution.util.ApplicationUtility;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * OAuth2 application Deployer..
 */
public class OAuth2ConfigDeployer {
    private static Log log = LogFactory.getLog(OAuth2ConfigDeployer.class);
    public static String PROTOCOL_OAUTH2 = "oauth2";

    public static void deploy(org.wso2.carbon.solution.model.iam.sp.ServiceProvider serviceProvider_source,
                              IdentityServerArtifact identityServerArtifact, Server server)
            throws CarbonSolutionException {
        Properties updateProperty = new Properties();

        InboundAuthenticationConfig inboundAuthenticationConfig_source =
                serviceProvider_source.getInboundAuthenticationConfig();
        if (inboundAuthenticationConfig_source != null) {
            List<InboundAuthenticationRequestConfig> inboundAuthenticationRequestConfigs_source
                    = inboundAuthenticationConfig_source.getInboundAuthenticationRequestConfigs();
            if (inboundAuthenticationRequestConfigs_source != null) {
                for (InboundAuthenticationRequestConfig inboundAuthenticationRequestConfig_source :
                        inboundAuthenticationRequestConfigs_source) {

                    if (PROTOCOL_OAUTH2.equals(inboundAuthenticationRequestConfig_source.getInboundAuthType())) {
                        updateSensibleDefaults(inboundAuthenticationRequestConfig_source);
                        OAuthConsumerAppDTO oAuthConsumerAppDTO_dest = getoAuthConsumerAppDTO(
                                inboundAuthenticationRequestConfig_source);
                        OAuthAdminServiceStub oAuthAdminService = IdentityServerAdminClient
                                .getOAuthAdminService(server);
                        try {
                            try {
                                OAuthConsumerAppDTO[] allOAuthApplicationData_server = oAuthAdminService
                                        .getAllOAuthApplicationData();
                                if (allOAuthApplicationData_server != null) {
                                    for (OAuthConsumerAppDTO authConsumerAppDTO_server :
                                            allOAuthApplicationData_server) {
                                        if (authConsumerAppDTO_server.getApplicationName()
                                                .equals(oAuthConsumerAppDTO_dest.getApplicationName())) {
                                            oAuthAdminService.removeOAuthApplicationData(
                                                    authConsumerAppDTO_server.getOauthConsumerKey());
                                            break;
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                throw new CarbonSolutionException("Error occurred while getAllOAuthApplicationData.",e);
                            }
                            oAuthAdminService.registerOAuthApplicationData(oAuthConsumerAppDTO_dest);
                            OAuthConsumerAppDTO[] allOAuthApplicationData_server = oAuthAdminService
                                    .getAllOAuthApplicationData();
                            if (allOAuthApplicationData_server != null) {
                                for (OAuthConsumerAppDTO oAuthConsumerAppDTO_server : allOAuthApplicationData_server) {
                                    if (oAuthConsumerAppDTO_server.getApplicationName().equals(oAuthConsumerAppDTO_dest
                                                                                                       .getApplicationName()

                                    )) {
                                        List<Property> newProperties = new ArrayList<Property>();
                                        Property property = new Property();
                                        property.setName(
                                                OAuth2Config.OAUTH_CONSUMER_SECRET.getConfigName());
                                        property.setValue(oAuthConsumerAppDTO_server.getOauthConsumerSecret());
                                        newProperties.add(property);
                                        inboundAuthenticationRequestConfig_source.setProperties(newProperties);
                                        inboundAuthenticationRequestConfig_source.setInboundAuthKey
                                                (oAuthConsumerAppDTO_server.getOauthConsumerKey());
                                        updateProperty.put(identityServerArtifact.getArtifactFile()
                                                           + "-OIDC.ClientId",
                                                           oAuthConsumerAppDTO_server.getOauthConsumerKey());
                                        updateProperty.put(identityServerArtifact.getArtifactFile()
                                                           + "-OIDC.ClientSecret",
                                                           oAuthConsumerAppDTO_server.getOauthConsumerSecret
                                                                   ());
                                        updateProperty.put(identityServerArtifact.getArtifactFile()
                                                           + "-OIDC.CallBackUrl",
                                                           oAuthConsumerAppDTO_server.getCallbackUrl());
                                        break;
                                    }
                                }
                            }
                        } catch (Exception e) {
                            throw new CarbonSolutionException("Error occurred while getAllOAuthApplicationData.", e);
                        }
                    }
                }
            }
        }

        IdentityServer identityServer = new IdentityServer(server);
        updateProperty.put(identityServerArtifact.getArtifactFile()
                           + "-OIDC.IdentityServerHostName", identityServer.getHost());
        updateProperty.put(identityServerArtifact.getArtifactFile()
                           + "-OIDC.IdentityServerPort", identityServer.getPort() + "");
        ApplicationUtility.updateProperty(identityServerArtifact, updateProperty);
    }

    private static OAuthConsumerAppDTO getoAuthConsumerAppDTO(InboundAuthenticationRequestConfig
                                                                      inboundAuthenticationRequestConfig_source) {
        List<Property> properties = inboundAuthenticationRequestConfig_source.getProperties();
        OAuthConsumerAppDTO oAuthConsumerAppDTO_dest = new OAuthConsumerAppDTO();
        for (Property property : properties) {
            if (property.getName().equals(OAuth2Config.OAUTH_VERSION.getConfigName())) {
                oAuthConsumerAppDTO_dest.setOAuthVersion(property.getValue());
            }
            if (property.getName().equals(OAuth2Config.APPLICATION_NAME.getConfigName())) {
                oAuthConsumerAppDTO_dest.setApplicationName(property.getValue());
            }
            if (property.getName().equals(OAuth2Config.CALLBACK_URL.getConfigName())) {
                oAuthConsumerAppDTO_dest.setCallbackUrl(property.getValue());
            }
            if (property.getName().equals(OAuth2Config.GRANT_TYPES.getConfigName())) {
                oAuthConsumerAppDTO_dest.setGrantTypes(property.getValue());
            }
            if (property.getName().equals(OAuth2Config.OAUTH_CONSUMER_KEY.getConfigName())) {
                oAuthConsumerAppDTO_dest.setOauthConsumerKey(property.getValue());
            }
            if (property.getName().equals(OAuth2Config.OAUTH_CONSUMER_SECRET.getConfigName())) {
                oAuthConsumerAppDTO_dest.setOauthConsumerSecret(property.getValue());
            }
            if (property.getName().equals(OAuth2Config.PKCE_MANDATORY.getConfigName())) {
                oAuthConsumerAppDTO_dest.setPkceMandatory(Boolean.parseBoolean(property.getValue()));
            }
            if (property.getName().equals(OAuth2Config.PKCE_SUPPORT_PLAN.getConfigName())) {
                oAuthConsumerAppDTO_dest.setPkceSupportPlain(Boolean.parseBoolean(property.getValue()));
            }
            if (property.getName().equals(OAuth2Config.STATE.getConfigName())) {
                oAuthConsumerAppDTO_dest.setState(property.getValue());
            }
            if (property.getName().equals(OAuth2Config.REFRESH_TOKEN_EXPIRE_TIME.getConfigName())) {
                oAuthConsumerAppDTO_dest.setRefreshTokenExpiryTime(Long.parseLong(property.getValue()));
            }
            if (property.getName().equals(OAuth2Config.USER_ACCESS_TOKEN_EXPIRE_TIME.getConfigName())) {
                oAuthConsumerAppDTO_dest.setUserAccessTokenExpiryTime(
                        Long.parseLong(property.getValue()));
            }
            if (property.getName().equals(OAuth2Config.APPLICATION_ACCESS_TOKEN_EXPIRE_TIME.getConfigName())) {
                oAuthConsumerAppDTO_dest.setApplicationAccessTokenExpiryTime(
                        Long.parseLong(property.getValue()));
            }
            if (property.getName().equals(OAuth2Config.USER_NAME.getConfigName())) {
                oAuthConsumerAppDTO_dest.setOAuthVersion(property.getValue());
            }
        }
        return oAuthConsumerAppDTO_dest;
    }

    private static void updateSensibleDefaults(InboundAuthenticationRequestConfig
                                                       inboundAuthenticationRequestConfig_source) {

        List<Property> properties = inboundAuthenticationRequestConfig_source.getProperties();
        Set<String> propertyNameSet = new HashSet<>();
        for (Property property : properties) {
            propertyNameSet.add(property.getName());
        }

        updateDefaultIfNotExist(properties, propertyNameSet, OAuth2Config.OAUTH_VERSION);
        updateDefaultIfNotExist(properties, propertyNameSet, OAuth2Config.GRANT_TYPES);
        updateDefaultIfNotExist(properties, propertyNameSet, OAuth2Config.PKCE_MANDATORY);
        updateDefaultIfNotExist(properties, propertyNameSet, OAuth2Config.PKCE_SUPPORT_PLAN);
        updateDefaultIfNotExist(properties, propertyNameSet, OAuth2Config.REFRESH_TOKEN_EXPIRE_TIME);
        updateDefaultIfNotExist(properties, propertyNameSet, OAuth2Config.USER_ACCESS_TOKEN_EXPIRE_TIME);
        updateDefaultIfNotExist(properties, propertyNameSet, OAuth2Config.APPLICATION_ACCESS_TOKEN_EXPIRE_TIME);
    }

    private static void updateDefaultIfNotExist(List<Property> properties, Set<String> propertyNameSet,
                                                OAuth2Config oAuth2Config) {
        if (!propertyNameSet.contains(oAuth2Config)) {
            Property property = new Property();
            property.setName(oAuth2Config.getConfigName());
            property.setValue(oAuth2Config.getDefaultValue());
            properties.add(property);
        }
    }
}
