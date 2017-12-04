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

package org.wso2.carbon.solution.deployer.iam.impl.saml;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.sso.saml.stub.types.SAMLSSOServiceProviderDTO;
import org.wso2.carbon.identity.sso.saml.stub.types.SAMLSSOServiceProviderInfoDTO;
import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.deployer.iam.impl.UserStoreDeployer;
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
 * SAML Config deployer.
 */
public class SAMLConfigDeployer {
    private static Log log = LogFactory.getLog(SAMLConfigDeployer.class);
    public static String PROTOCOL_SAML = "samlsso";

    public static void deploy(org.wso2.carbon.solution.model.iam.sp.ServiceProvider serviceProvider_source,
                              IdentityServerArtifact identityServerArtifact, Server server)
            throws CarbonSolutionException {
        InboundAuthenticationConfig inboundAuthenticationConfig_source = serviceProvider_source
                .getInboundAuthenticationConfig();
        if (inboundAuthenticationConfig_source != null) {
            log.debug("Error occurred while execute deploy, " + serviceProvider_source.getName());
            List<InboundAuthenticationRequestConfig> inboundAuthenticationRequestConfigs_source
                    = inboundAuthenticationConfig_source.getInboundAuthenticationRequestConfigs();
            if (inboundAuthenticationRequestConfigs_source != null) {
                for (InboundAuthenticationRequestConfig inboundAuthenticationRequestConfig_source :
                        inboundAuthenticationRequestConfigs_source) {
                    if (PROTOCOL_SAML.equals(inboundAuthenticationRequestConfig_source.getInboundAuthType())) {
                        updateSensibleDefaults(inboundAuthenticationRequestConfig_source);
                        SAMLSSOServiceProviderDTO samlssoServiceProviderDTO_dest = getSAMLDTO(
                                inboundAuthenticationRequestConfig_source);

                        if (StringUtils.isEmpty(samlssoServiceProviderDTO_dest.getDefaultAssertionConsumerUrl())) {
                            samlssoServiceProviderDTO_dest.setDefaultAssertionConsumerUrl
                                    (samlssoServiceProviderDTO_dest.getAssertionConsumerUrl());
                        }

                        try {
                            IdentityServerAdminClient.getSAMLSSOConfigService(server).removeServiceProvider
                                    (samlssoServiceProviderDTO_dest
                                             .getIssuer());
                            IdentityServerAdminClient.getSAMLSSOConfigService(server)
                                    .addRPServiceProvider(samlssoServiceProviderDTO_dest);

                            SAMLSSOServiceProviderInfoDTO serviceProviders_dest = IdentityServerAdminClient
                                    .getSAMLSSOConfigService(server)
                                    .getServiceProviders();
                            SAMLSSOServiceProviderDTO[] serviceProvidersDTOs_dest = serviceProviders_dest
                                    .getServiceProviders();
                            for (SAMLSSOServiceProviderDTO serviceProviderDTO_dest : serviceProvidersDTOs_dest) {
                                if (serviceProviderDTO_dest.getIssuer().equals(samlssoServiceProviderDTO_dest
                                                                                       .getIssuer())) {
                                    String attributeConsumingServiceIndex = samlssoServiceProviderDTO_dest
                                            .getAttributeConsumingServiceIndex();
                                    List<Property> properties_source = new ArrayList<Property>();
                                    Property consumerIndexProperty = new Property();
                                    consumerIndexProperty
                                            .setName(
                                                    SAMLConfig
                                                            .ATTR_CONSUMER_SERVICE_INDEX.getConfigName());
                                    consumerIndexProperty.setValue(attributeConsumingServiceIndex);

                                    properties_source.add(consumerIndexProperty);
                                    inboundAuthenticationRequestConfig_source.setProperties(properties_source);
                                }
                            }

                            Properties updateProperty = new Properties();

                            IdentityServer identityServer = new IdentityServer(server);
                            updateProperty.put(identityServerArtifact.getArtifactFile()
                                               + "-SAML2.SPEntityId", samlssoServiceProviderDTO_dest.getIssuer());
                            updateProperty.put(identityServerArtifact.getArtifactFile()
                                               + "-SAML2.AssertionConsumerURL", samlssoServiceProviderDTO_dest
                                                       .getAssertionConsumerUrl());
                            updateProperty.put(identityServerArtifact.getArtifactFile()
                                               + "-SAML2.IdPEntityId", identityServer.getHost());
                            String samlEndpoint = "https://" + identityServer.getHost();
                            if (identityServer.getPort() > 0) {
                                samlEndpoint += ":" + identityServer.getPort();
                            }
                            updateProperty.put(identityServerArtifact.getArtifactFile()
                                               + "-SAML2.IdPURL", samlEndpoint + "/samlsso");
                            ApplicationUtility.updateProperty(identityServerArtifact, updateProperty);
                        } catch (Exception e) {
                            throw new CarbonSolutionException("Error occurred while removeServiceProvider.", e);
                        }
                        break;
                    }
                }
            }
        }
    }

    private static SAMLSSOServiceProviderDTO getSAMLDTO(InboundAuthenticationRequestConfig config_source) {
        List<Property> properties = config_source.getProperties();
        SAMLSSOServiceProviderDTO samlssoServiceProviderDTO_dest = new SAMLSSOServiceProviderDTO();
        for (Property property : properties) {
            if (property.getName().equals(SAMLConfig.ISSUER.getConfigName())) {
                samlssoServiceProviderDTO_dest.setIssuer(property.getValue());
            }
            if (property.getName().equals(SAMLConfig.ASSERTION_CONSUMER_URL.getConfigName())) {
                samlssoServiceProviderDTO_dest.setAssertionConsumerUrl(property.getValue());
            }
            if (property.getName().equals(SAMLConfig.ASSERTION_CONSUMER_URL.getConfigName())) {
                String[] assertionConsumerUrls = property.getValue().split(",");
                samlssoServiceProviderDTO_dest.setAssertionConsumerUrls(assertionConsumerUrls);
            }
            if (property.getName().equals(SAMLConfig.ASSERTION_ENCRYPTION_ALGORITH_URI.getConfigName())) {
                samlssoServiceProviderDTO_dest.setAssertionEncryptionAlgorithmURI(property.getValue());
            }
            if (property.getName().equals(SAMLConfig.IS_ASSERT_QUERY_REQ_PROF_ENABLED.getConfigName())) {
                samlssoServiceProviderDTO_dest.setAssertionQueryRequestProfileEnabled(
                        Boolean.parseBoolean(property.getValue()));
            }
            if (property.getName().equals(SAMLConfig.SUPPORTED_ASSERTION_QUERY_REQUEST_TYPES.getConfigName())) {
                samlssoServiceProviderDTO_dest.setSupportedAssertionQueryRequestTypes(property.getValue());
            }
            if (property.getName().equals(SAMLConfig.CERT_ALIAS.getConfigName())) {
                samlssoServiceProviderDTO_dest.setCertAlias(property.getValue());
            }
            if (property.getName().equals(SAMLConfig.DEFAULT_ASSERTION_CONSUMER_URL.getConfigName())) {
                samlssoServiceProviderDTO_dest.setDefaultAssertionConsumerUrl(property.getValue());
            }
            if (property.getName().equals(SAMLConfig.DIGEST_ALGORITH_URI.getConfigName())) {
                samlssoServiceProviderDTO_dest.setDigestAlgorithmURI(property.getValue());
            }
            if (property.getName().equals(SAMLConfig.DO_ENABLE_ENCRYPTED_ASSETION.getConfigName())) {
                samlssoServiceProviderDTO_dest.
                        setDoEnableEncryptedAssertion(Boolean.parseBoolean(property.getValue()));
            }
            if (property.getName().equals(SAMLConfig.DO_SIGN_ASSERTIONS.getConfigName())) {
                samlssoServiceProviderDTO_dest.setDoSignAssertions(Boolean.parseBoolean(property.getValue()));
            }
            if (property.getName().equals(SAMLConfig.DO_SIGN_RESPONSE.getConfigName())) {
                samlssoServiceProviderDTO_dest.setDoSignResponse(Boolean.parseBoolean(property.getValue()));
            }
            if (property.getName().equals(SAMLConfig.DO_SINGLE_LOGOUT.getConfigName())) {
                samlssoServiceProviderDTO_dest.setDoSingleLogout(Boolean.parseBoolean(property.getValue()));
            }
            if (property.getName().equals(SAMLConfig.DO_VALIDATE_SIGNATURE_IN_REQUEST.getConfigName())) {
                samlssoServiceProviderDTO_dest.setDoValidateSignatureInRequests(
                        Boolean.parseBoolean(property.getValue()));
            }
            if (property.getName().equals(SAMLConfig.ENABLE_ATTRIBUTE_PROFILE.getConfigName())) {
                samlssoServiceProviderDTO_dest.setEnableAttributeProfile(Boolean.parseBoolean(property.getValue()));
            }
            if (property.getName().equals(SAMLConfig.ENABLE_ATTRIBUTES_BY_DEFAULT.getConfigName())) {
                samlssoServiceProviderDTO_dest.setEnableAttributesByDefault(
                        Boolean.parseBoolean(property.getValue()));
            }
            if (property.getName().equals(SAMLConfig.IDP_INIT_SLO_ENABLED.getConfigName())) {
                samlssoServiceProviderDTO_dest.setIdPInitSLOEnabled(Boolean.parseBoolean(property.getValue()));
            }
            if (property.getName().equals(SAMLConfig.IDP_INIT_SSO_ENABLED.getConfigName())) {
                samlssoServiceProviderDTO_dest.setIdPInitSSOEnabled(Boolean.parseBoolean(property.getValue()));
            }
            if (property.getName().equals(SAMLConfig.IDP_INIT_SLO_RETURN_TO_URLS.getConfigName())) {
                String[] urls = property.getValue().split(",");
                samlssoServiceProviderDTO_dest.setIdpInitSLOReturnToURLs(urls);
            }
            if (property.getName().equals(SAMLConfig.KEY_ENCRYPTION_ALGORITH_URI.getConfigName())) {
                samlssoServiceProviderDTO_dest.setKeyEncryptionAlgorithmURI(property.getValue());
            }
            if (property.getName().equals(SAMLConfig.LOGIN_PAGE_URL.getConfigName())) {
                samlssoServiceProviderDTO_dest.setLoginPageURL(property.getValue());
            }
            if (property.getName().equals(SAMLConfig.NAME_ID_FORMAT.getConfigName())) {
                samlssoServiceProviderDTO_dest.setNameIDFormat(property.getValue());
            }
            if (property.getName().equals(SAMLConfig.NAME_ID_CLAIM_URI.getConfigName())) {
                samlssoServiceProviderDTO_dest.setNameIdClaimUri(property.getValue());
            }
            if (property.getName().equals(SAMLConfig.REQUEST_AUDIENCES.getConfigName())) {
                String[] audiences = property.getValue().split(",");
                samlssoServiceProviderDTO_dest.setRequestedAudiences(audiences);
            }
            if (property.getName().equals(SAMLConfig.REQUESTED_RECIPIENTS.getConfigName())) {
                String[] recipients = property.getValue().split(",");
                samlssoServiceProviderDTO_dest.setRequestedRecipients(recipients);
            }
            if (property.getName().equals(SAMLConfig.REQUESTED_CLAIMS.getConfigName())) {
                if (StringUtils.isNotEmpty(property.getValue())) {
                    String[] claims = property.getValue().split(",");
                    samlssoServiceProviderDTO_dest.setRequestedClaims(claims);
                }
            }
            if (property.getName().equals(SAMLConfig.SIGNING_ALGORITHM_URI.getConfigName())) {
                samlssoServiceProviderDTO_dest.setSigningAlgorithmURI(property.getValue());
            }
            if (property.getName().equals(SAMLConfig.SLO_REQUEST_URL.getConfigName())) {
                samlssoServiceProviderDTO_dest.setSloRequestURL(property.getValue());
            }
            if (property.getName().equals(SAMLConfig.SLO_RESPONSE_URL.getConfigName())) {
                samlssoServiceProviderDTO_dest.setSloResponseURL(property.getValue());
            }
            if (property.getName().equals(SAMLConfig.ATTRIBUTE_CONSUMING_SERVICE_INDEX.getConfigName())) {
                samlssoServiceProviderDTO_dest.setAttributeConsumingServiceIndex(property.getValue());
            }
        }
        return samlssoServiceProviderDTO_dest;
    }

    private static void updateDefaultIfNotExist(List<Property> properties, Set<String> propertyNameSet,
                                                SAMLConfig samlConfig) {
        if (!propertyNameSet.contains(samlConfig)) {
            Property property = new Property();
            property.setName(samlConfig.getConfigName());
            property.setValue(samlConfig.getDefaultValue());
            properties.add(property);
        }
    }

    private static void updateSensibleDefaults(InboundAuthenticationRequestConfig
                                                       inboundAuthenticationRequestConfig_source) {

        List<Property> properties = inboundAuthenticationRequestConfig_source.getProperties();
        Set<String> propertyNameSet = new HashSet<>();
        for (Property property : properties) {
            propertyNameSet.add(property.getName());
        }

        updateDefaultIfNotExist(properties, propertyNameSet, SAMLConfig.CERT_ALIAS);
        updateDefaultIfNotExist(properties, propertyNameSet, SAMLConfig.NAME_ID_FORMAT);
        updateDefaultIfNotExist(properties, propertyNameSet, SAMLConfig.SIGNING_ALGORITHM_URI);
        updateDefaultIfNotExist(properties, propertyNameSet, SAMLConfig.DIGEST_ALGORITH_URI);
        updateDefaultIfNotExist(properties, propertyNameSet, SAMLConfig.ASSERTION_ENCRYPTION_ALGORITH_URI);
        updateDefaultIfNotExist(properties, propertyNameSet, SAMLConfig.KEY_ENCRYPTION_ALGORITH_URI);
    }
}
