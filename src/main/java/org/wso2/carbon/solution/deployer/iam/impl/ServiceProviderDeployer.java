package org.wso2.carbon.solution.deployer.iam.impl;


import org.apache.commons.lang.StringUtils;
import org.wso2.carbon.identity.application.common.model.xsd.ServiceProvider;
import org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceIdentityOAuthAdminException;
import org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerAppDTO;
import org.wso2.carbon.identity.sso.saml.stub.IdentitySAMLSSOConfigServiceIdentityException;
import org.wso2.carbon.identity.sso.saml.stub.types.SAMLSSOServiceProviderDTO;
import org.wso2.carbon.identity.sso.saml.stub.types.SAMLSSOServiceProviderInfoDTO;
import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.builder.iam.ServiceProviderBuilder;
import org.wso2.carbon.solution.deployer.iam.IdentityServerDeployer;
import org.wso2.carbon.solution.endpoint.iam.IdentityServerAdminClient;
import org.wso2.carbon.solution.endpoint.iam.config.IdentityServer;
import org.wso2.carbon.solution.installer.impl.IdentityServerInstaller;
import org.wso2.carbon.solution.model.config.server.Server;
import org.wso2.carbon.solution.model.iam.idp.IdentityProviderEntity;
import org.wso2.carbon.solution.model.iam.sp.InboundAuthenticationConfig;
import org.wso2.carbon.solution.model.iam.sp.InboundAuthenticationRequestConfig;
import org.wso2.carbon.solution.model.iam.sp.Property;
import org.wso2.carbon.solution.model.iam.sp.ServiceProviderEntity;
import org.wso2.carbon.solution.util.Constant;
import org.wso2.carbon.solution.util.ResourceLoader;
import org.wso2.carbon.solution.util.Utility;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class ServiceProviderDeployer extends IdentityServerDeployer {

    private static final String ARTIFACT_TYPE_SERVICE_PROVIDERS = "service-providers";
    public static String PROTOCOL_SAML = "samlsso";
    public static String PROTOCOL_OAUTH2 = "oauth2";


    public boolean availableInboundProtocol(String authType, org.wso2.carbon.solution.model.iam.sp.ServiceProvider
            serviceProvider) {
        InboundAuthenticationConfig inboundAuthenticationConfig = serviceProvider.getInboundAuthenticationConfig();
        if (inboundAuthenticationConfig != null) {
            List<InboundAuthenticationRequestConfig> inboundAuthenticationRequestConfigs = inboundAuthenticationConfig
                    .getInboundAuthenticationRequestConfigs();
            if (inboundAuthenticationRequestConfigs != null) {
                for (InboundAuthenticationRequestConfig inboundAuthenticationRequestConfig :
                        inboundAuthenticationRequestConfigs) {
                    if (authType.equals(inboundAuthenticationRequestConfig.getInboundAuthType())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean canHandle(String artifactType) {
        if (ARTIFACT_TYPE_SERVICE_PROVIDERS.equals(artifactType)) {
            return true;
        }
        return false;
    }

    @Override
    public void doDeploy(IdentityServerInstaller.IdentityServerArtifact identityServerArtifact, Server server) throws
                                                                                                             CarbonSolutionException {

        Path resourcesPathObj = Paths
                .get(Utility.RESOURCE_HOME, Constant.SOLUTION_HOME, identityServerArtifact.getPath());

        ServiceProviderEntity serviceProviderEntity_source = ResourceLoader.loadResource(resourcesPathObj,
                                                                                           ServiceProviderEntity.class);

            try {
                org.wso2.carbon.solution.model.iam.sp.ServiceProvider serviceProvider_source
                        = serviceProviderEntity_source
                        .getServiceProvider();
                ServiceProvider application = null;

                try {
                    //application = stub.getApplication(serviceProvider_source.getName());
                    IdentityServerAdminClient.getApplicationManagementService(server)
                            .deleteApplication(serviceProvider_source.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (availableInboundProtocol(PROTOCOL_SAML, serviceProvider_source)) {
                    SAMLApplicationDeployer samlApplicationDeployer = new SAMLApplicationDeployer();
                    samlApplicationDeployer.deploy(serviceProvider_source, identityServerArtifact, server);
                }
                if (availableInboundProtocol(PROTOCOL_OAUTH2, serviceProvider_source)) {
                    OAuth2ApplicationDeployer oAuth2ApplicationDeployer = new OAuth2ApplicationDeployer();
                    oAuth2ApplicationDeployer.deploy(serviceProvider_source, identityServerArtifact, server);
                }

                updateSensibleDefaults(serviceProvider_source);
                ServiceProvider serviceProvider_dest
                        = ServiceProviderBuilder.getInstance().buildServiceProvider(serviceProvider_source);
                if (application == null) {
                    IdentityServerAdminClient.getApplicationManagementService(server)
                            .createApplication(serviceProvider_dest);
                    try {
                        application = IdentityServerAdminClient.getApplicationManagementService(server)
                                .getApplication(serviceProvider_source.getName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                serviceProvider_dest.setApplicationID(application.getApplicationID());
                IdentityServerAdminClient.getApplicationManagementService(server)
                        .updateApplication(serviceProvider_dest);
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    private void updateSensibleDefaults(org.wso2.carbon.solution.model.iam.sp.ServiceProvider serviceProvider_source) {

        boolean isPassiveSTSConfigEmpty = true;
        boolean isOpenIDConfigEmpty = true;
        InboundAuthenticationConfig inboundAuthenticationConfig = serviceProvider_source
                .getInboundAuthenticationConfig();
        if (inboundAuthenticationConfig != null) {
            List<InboundAuthenticationRequestConfig> inboundAuthenticationRequestConfigs = inboundAuthenticationConfig
                    .getInboundAuthenticationRequestConfigs();
            for (InboundAuthenticationRequestConfig inboundAuthenticationRequestConfig :
                    inboundAuthenticationRequestConfigs) {
                if (inboundAuthenticationRequestConfig.getInboundAuthType().equals("passivests")) {
                    isPassiveSTSConfigEmpty = false;
                }
                if (inboundAuthenticationRequestConfig.getInboundAuthType().equals("openid")) {
                    isOpenIDConfigEmpty = false;
                }
            }
        } else {
            inboundAuthenticationConfig = new InboundAuthenticationConfig();
            serviceProvider_source.setInboundAuthenticationConfig(inboundAuthenticationConfig);
        }
        if (isPassiveSTSConfigEmpty) {
            InboundAuthenticationRequestConfig inboundAuthenticationRequestConfig = new
                    InboundAuthenticationRequestConfig();
            inboundAuthenticationRequestConfig.setInboundAuthType("passivests");
            inboundAuthenticationRequestConfig.setInboundAuthKey("");
            inboundAuthenticationConfig.getInboundAuthenticationRequestConfigs().add
                    (inboundAuthenticationRequestConfig);
        }

        if (isOpenIDConfigEmpty) {
            InboundAuthenticationRequestConfig inboundAuthenticationRequestConfig = new
                    InboundAuthenticationRequestConfig();
            inboundAuthenticationRequestConfig.setInboundAuthType("openid");
            inboundAuthenticationRequestConfig.setInboundAuthKey("");
            inboundAuthenticationConfig.getInboundAuthenticationRequestConfigs().add
                    (inboundAuthenticationRequestConfig);
        }
    }


    public static class OAuth2ApplicationDeployer {


        public void deploy(org.wso2.carbon.solution.model.iam.sp.ServiceProvider serviceProvider_source,
                           IdentityServerInstaller.IdentityServerArtifact identityServerArtifact,Server server) {
            Properties updateProperty = new Properties();

            InboundAuthenticationConfig inboundAuthenticationConfig_source = serviceProvider_source
                    .getInboundAuthenticationConfig();
            if (inboundAuthenticationConfig_source != null) {
                List<InboundAuthenticationRequestConfig> inboundAuthenticationRequestConfigs_source
                        = inboundAuthenticationConfig_source
                        .getInboundAuthenticationRequestConfigs();
                if (inboundAuthenticationRequestConfigs_source != null) {
                    for (InboundAuthenticationRequestConfig inboundAuthenticationRequestConfig_source :
                            inboundAuthenticationRequestConfigs_source) {
                        if (ServiceProviderDeployer.PROTOCOL_OAUTH2
                                .equals(inboundAuthenticationRequestConfig_source.getInboundAuthType())) {
                            updateSensibleDefaults(inboundAuthenticationRequestConfig_source);
                            List<Property> properties = inboundAuthenticationRequestConfig_source.getProperties();
                            OAuthConsumerAppDTO oAuthConsumerAppDTO_dest = new OAuthConsumerAppDTO();
                            for (Property property : properties) {
                                if (property.getName().equals(
                                        OAuth2ApplicationDeployer
                                                .OAuth2ApplicationConstants.OAUTH_VERSION)) {
                                    oAuthConsumerAppDTO_dest.setOAuthVersion(property.getValue());
                                }
                                if (property.getName().equals(
                                        OAuth2ApplicationDeployer
                                                .OAuth2ApplicationConstants.APPLICATION_NAME)) {
                                    oAuthConsumerAppDTO_dest.setApplicationName(property.getValue());
                                }
                                if (property.getName().equals(
                                        OAuth2ApplicationDeployer
                                                .OAuth2ApplicationConstants.CALLBACK_URL)) {
                                    oAuthConsumerAppDTO_dest.setCallbackUrl(property.getValue());
                                    updateProperty.put(identityServerArtifact.getArtifactFile()
                                                       +"-OAUTH2-CallbackURL", property.getValue());
                                }
                                if (property.getName().equals(
                                        OAuth2ApplicationDeployer
                                                .OAuth2ApplicationConstants.GRANT_TYPES)) {
                                    oAuthConsumerAppDTO_dest.setGrantTypes(property.getValue());
                                }
                                if (property.getName().equals(
                                        OAuth2ApplicationDeployer
                                                .OAuth2ApplicationConstants.OAUTH_CONSUMER_KEY)) {
                                    oAuthConsumerAppDTO_dest.setOauthConsumerKey(property.getValue());
                                }
                                if (property.getName().equals(
                                        OAuth2ApplicationDeployer
                                                .OAuth2ApplicationConstants.OAUTH_CONSUMER_SECRET)) {
                                    oAuthConsumerAppDTO_dest.setOauthConsumerSecret(property.getValue());
                                }
                                if (property.getName().equals(
                                        OAuth2ApplicationDeployer
                                                .OAuth2ApplicationConstants.PKCE_MANDATORY)) {
                                    oAuthConsumerAppDTO_dest
                                            .setPkceMandatory(Boolean.parseBoolean(property.getValue()));
                                }
                                if (property.getName().equals(
                                        OAuth2ApplicationDeployer
                                                .OAuth2ApplicationConstants.PKCE_SUPPORT_PLAN)) {
                                    oAuthConsumerAppDTO_dest
                                            .setPkceSupportPlain(Boolean.parseBoolean(property.getValue()));
                                }
                                if (property.getName().equals(
                                        OAuth2ApplicationDeployer
                                                .OAuth2ApplicationConstants.STATE)) {
                                    oAuthConsumerAppDTO_dest.setState(property.getValue());
                                }
                                if (property.getName().equals(
                                        OAuth2ApplicationDeployer
                                                .OAuth2ApplicationConstants.REFRESH_TOKEN_EXPIRE_TIME)) {
                                    oAuthConsumerAppDTO_dest.setRefreshTokenExpiryTime(Long.parseLong(property.getValue
                                            ()));
                                }
                                if (property.getName().equals(
                                        OAuth2ApplicationDeployer
                                                .OAuth2ApplicationConstants.USER_ACCESS_TOKEN_EXPIRE_TIME)) {
                                    oAuthConsumerAppDTO_dest
                                            .setUserAccessTokenExpiryTime(Long.parseLong(property.getValue
                                                    ()));
                                }
                                if (property.getName()
                                        .equals(OAuth2ApplicationDeployer
                                                        .OAuth2ApplicationConstants
                                                        .APPLICATION_ACCESS_TOKEN_EXPIRE_TIME)) {
                                    oAuthConsumerAppDTO_dest
                                            .setApplicationAccessTokenExpiryTime(Long.parseLong(property.getValue
                                                    ()));
                                }
                                if (property.getName().equals(
                                        OAuth2ApplicationDeployer
                                                .OAuth2ApplicationConstants.USER_NAME)) {
                                    oAuthConsumerAppDTO_dest.setOAuthVersion(property.getValue());
                                }
                            }

                            try {
                                try {
                                    OAuthConsumerAppDTO[] allOAuthApplicationData_server = IdentityServerAdminClient
                                            .getOAuthAdminService(server)
                                            .getAllOAuthApplicationData();
                                    if (allOAuthApplicationData_server != null) {
                                        for (OAuthConsumerAppDTO authConsumerAppDTO_server :
                                                allOAuthApplicationData_server) {
                                            if (authConsumerAppDTO_server.getApplicationName()
                                                    .equals(oAuthConsumerAppDTO_dest.getApplicationName())) {
                                                IdentityServerAdminClient
                                                        .getOAuthAdminService(server).removeOAuthApplicationData(
                                                        authConsumerAppDTO_server.getOauthConsumerKey());
                                                break;
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                IdentityServerAdminClient
                                        .getOAuthAdminService(server)
                                        .registerOAuthApplicationData(oAuthConsumerAppDTO_dest);
                                OAuthConsumerAppDTO[] allOAuthApplicationData_server = IdentityServerAdminClient
                                        .getOAuthAdminService(server)
                                        .getAllOAuthApplicationData();
                                for (OAuthConsumerAppDTO oAuthConsumerAppDTO_server : allOAuthApplicationData_server) {
                                    if (oAuthConsumerAppDTO_server.getApplicationName().equals(oAuthConsumerAppDTO_dest
                                                                                                .getApplicationName()
                                    )) {
                                        List<Property> newProperties = new ArrayList<Property>();
                                        Property property = new Property();
                                        property.setName(
                                                OAuth2ApplicationDeployer
                                                        .OAuth2ApplicationConstants.OAUTH_CONSUMER_SECRET);
                                        property.setValue(oAuthConsumerAppDTO_server.getOauthConsumerSecret());
                                        newProperties.add(property);
                                        inboundAuthenticationRequestConfig_source.setProperties(newProperties);
                                        inboundAuthenticationRequestConfig_source.setInboundAuthKey
                                                (oAuthConsumerAppDTO_server.getOauthConsumerKey());
                                        updateProperty.put(identityServerArtifact.getArtifactFile()
                                                           +"-OAUTH2-ConsumerKey", oAuthConsumerAppDTO_server.getOauthConsumerKey());
                                        updateProperty.put(identityServerArtifact.getArtifactFile()
                                                           +"-OAUTH2-ConsumerSecret", oAuthConsumerAppDTO_server.getOauthConsumerSecret
                                                ());
                                        break;
                                    }
                                }
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            } catch (OAuthAdminServiceIdentityOAuthAdminException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            IdentityServer identityServer = new IdentityServer(server);

            updateProperty.put(identityServerArtifact.getArtifactFile()
                               +"-OAUTH2-IdentityServerHostName", identityServer.getHost());
            updateProperty.put(identityServerArtifact.getArtifactFile()
                               +"-OAUTH2-IdentityServerPort", identityServer.getPort()+"");

            IdentityServerDeployer.updateProperty(identityServerArtifact,updateProperty);
        }

        private void updateSensibleDefaults(InboundAuthenticationRequestConfig
                                                    inboundAuthenticationRequestConfig_source) {

            List<Property> properties = inboundAuthenticationRequestConfig_source.getProperties();
            Set<String> propertyNameSet = new HashSet<>();
            for (Property property : properties) {
                propertyNameSet.add(property.getName());
            }

            if (!propertyNameSet.contains(
                    OAuth2ApplicationDeployer.OAuth2ApplicationConstants
                            .OAUTH_VERSION)) {
                Property property = new Property();
                property.setName(
                        OAuth2ApplicationDeployer
                                .OAuth2ApplicationConstants.OAUTH_VERSION);
                property.setValue("OAuth-2.0");
                properties.add(property);
            }
            if (!propertyNameSet.contains(
                    OAuth2ApplicationDeployer.OAuth2ApplicationConstants
                            .GRANT_TYPES)) {
                Property property = new Property();
                property.setName(
                        OAuth2ApplicationDeployer
                                .OAuth2ApplicationConstants.GRANT_TYPES);
                property.setValue(
                        "authorization_code implicit password client_credentials refresh_token "
                        + "urn:ietf:params:oauth:grant-type:saml2-bearer urn:ietf:params:oauth:grant-type:jwt-bearer "
                        + "iwa:ntlm");
                properties.add(property);
            }
            if (!propertyNameSet.contains(
                    OAuth2ApplicationDeployer.OAuth2ApplicationConstants
                            .PKCE_MANDATORY)) {
                Property property = new Property();
                property.setName(
                        OAuth2ApplicationDeployer
                                .OAuth2ApplicationConstants.PKCE_MANDATORY);
                property.setValue("false");
                properties.add(property);
            }
            if (!propertyNameSet.contains(
                    OAuth2ApplicationDeployer.OAuth2ApplicationConstants
                            .PKCE_SUPPORT_PLAN)) {
                Property property = new Property();
                property.setName(
                        OAuth2ApplicationDeployer
                                .OAuth2ApplicationConstants.PKCE_SUPPORT_PLAN);
                property.setValue("false");
                properties.add(property);
            }
            if (!propertyNameSet.contains(
                    OAuth2ApplicationDeployer.OAuth2ApplicationConstants
                            .REFRESH_TOKEN_EXPIRE_TIME)) {
                Property property = new Property();
                property.setName(
                        OAuth2ApplicationDeployer
                                .OAuth2ApplicationConstants.REFRESH_TOKEN_EXPIRE_TIME);


                property.setValue("84600");
                properties.add(property);
            }
            if (!propertyNameSet.contains(
                    OAuth2ApplicationDeployer.OAuth2ApplicationConstants
                            .USER_ACCESS_TOKEN_EXPIRE_TIME)) {
                Property property = new Property();
                property.setName(
                        OAuth2ApplicationDeployer
                                .OAuth2ApplicationConstants.USER_ACCESS_TOKEN_EXPIRE_TIME);
                property.setValue("3600");
                properties.add(property);
            }
            if (!propertyNameSet.contains(
                    OAuth2ApplicationDeployer.OAuth2ApplicationConstants
                            .APPLICATION_ACCESS_TOKEN_EXPIRE_TIME)) {
                Property property = new Property();
                property.setName(
                        OAuth2ApplicationDeployer
                                .OAuth2ApplicationConstants.APPLICATION_ACCESS_TOKEN_EXPIRE_TIME);
                property.setValue("3600");
                properties.add(property);
            }
        }

        static class OAuth2ApplicationConstants {

            public static final String OAUTH_VERSION = "OAuthVersion";
            public static final String APPLICATION_NAME = "applicationName";
            public static final String CALLBACK_URL = "callbackUrl";
            public static final String GRANT_TYPES = "grantTypes";
            public static final String OAUTH_CONSUMER_KEY = "oauthConsumerKey";
            public static final String OAUTH_CONSUMER_SECRET = "oauthConsumerSecret";
            public static final String PKCE_MANDATORY = "pkceMandatory";
            public static final String PKCE_SUPPORT_PLAN = "pkceSupportPlain";
            public static final String STATE = "state";
            public static final String REFRESH_TOKEN_EXPIRE_TIME = "refreshTokenExpiryTime";
            public static final String USER_ACCESS_TOKEN_EXPIRE_TIME = "userAccessTokenExpiryTime";
            public static final String APPLICATION_ACCESS_TOKEN_EXPIRE_TIME = "applicationAccessTokenExpiryTime";
            public static final String USER_NAME = "username";
        }
    }


    public static class SAMLApplicationDeployer {


        public void deploy(org.wso2.carbon.solution.model.iam.sp.ServiceProvider serviceProvider_source,
                           IdentityServerInstaller.IdentityServerArtifact identityServerArtifact,Server server) {
            Properties updateProperty = new Properties();
            InboundAuthenticationConfig inboundAuthenticationConfig_source = serviceProvider_source
                    .getInboundAuthenticationConfig();
            if (inboundAuthenticationConfig_source != null) {
                List<InboundAuthenticationRequestConfig> inboundAuthenticationRequestConfigs_source
                        = inboundAuthenticationConfig_source
                        .getInboundAuthenticationRequestConfigs();
                if (inboundAuthenticationRequestConfigs_source != null) {
                    for (InboundAuthenticationRequestConfig inboundAuthenticationRequestConfig_source :
                            inboundAuthenticationRequestConfigs_source) {
                        if (ServiceProviderDeployer.PROTOCOL_SAML
                                .equals(inboundAuthenticationRequestConfig_source.getInboundAuthType())) {
                            updateSensibleDefaults(inboundAuthenticationRequestConfig_source);
                            List<Property> properties = inboundAuthenticationRequestConfig_source.getProperties();
                            SAMLSSOServiceProviderDTO samlssoServiceProviderDTO_dest = new SAMLSSOServiceProviderDTO();
                            for (Property property : properties) {
                                if (property.getName().equals(
                                        SAMLApplicationDeployer
                                                .SAMLApplicationConstants.ISSUER)) {
                                    samlssoServiceProviderDTO_dest.setIssuer(property.getValue());
                                    updateProperty.put(identityServerArtifact.getArtifactFile()
                                                       +"-SAML2.SPEntityId", property.getValue());
                                }
                                if (property.getName().equals(
                                        SAMLApplicationDeployer
                                                .SAMLApplicationConstants.ASSERTION_CONSUMER_URL)) {
                                    samlssoServiceProviderDTO_dest.setAssertionConsumerUrl(property.getValue());
                                    updateProperty.put(identityServerArtifact.getArtifactFile()
                                                       +"-SAML2.AssertionConsumerURL", property.getValue());
                                }
                                if (property.getName().equals(
                                        SAMLApplicationDeployer
                                                .SAMLApplicationConstants.ASSERTION_CONSUMER_URL)) {
                                    String[] assertionConsumerUrls = property.getValue().split(",");
                                    samlssoServiceProviderDTO_dest.setAssertionConsumerUrls(assertionConsumerUrls);
                                }
                                if (property.getName()
                                        .equals(SAMLApplicationDeployer
                                                        .SAMLApplicationConstants.ASSERTION_ENCRYPTION_ALGORITH_URI)) {
                                    samlssoServiceProviderDTO_dest
                                            .setAssertionEncryptionAlgorithmURI(property.getValue());
                                }
                                if (property.getName()
                                        .equals(SAMLApplicationDeployer
                                                        .SAMLApplicationConstants
                                                        .IS_ASSERTION_QUERY_REQUEST_PROFILE_ENABLED)) {
                                    samlssoServiceProviderDTO_dest.setAssertionQueryRequestProfileEnabled(
                                            Boolean.parseBoolean(property.getValue()));
                                }
                                if (property.getName()
                                        .equals(SAMLApplicationDeployer
                                                        .SAMLApplicationConstants
                                                        .SUPPORTED_ASSERTION_QUERY_REQUEST_TYPES)) {
                                    samlssoServiceProviderDTO_dest
                                            .setSupportedAssertionQueryRequestTypes(property.getValue());
                                }
                                if (property.getName().equals(
                                        SAMLApplicationDeployer
                                                .SAMLApplicationConstants.CERT_ALIAS)) {
                                    samlssoServiceProviderDTO_dest.setCertAlias(property.getValue());
                                }
                                if (property.getName()
                                        .equals(SAMLApplicationDeployer
                                                        .SAMLApplicationConstants.DEFAULT_ASSERTION_CONSUMER_URL)) {
                                    samlssoServiceProviderDTO_dest.setDefaultAssertionConsumerUrl(property.getValue());
                                }
                                if (property.getName().equals(
                                        SAMLApplicationDeployer
                                                .SAMLApplicationConstants.DIGEST_ALGORITH_URI)) {
                                    samlssoServiceProviderDTO_dest.setDigestAlgorithmURI(property.getValue());
                                }
                                if (property.getName().equals(
                                        SAMLApplicationDeployer
                                                .SAMLApplicationConstants.DO_ENABLE_ENCRYPTED_ASSETION)) {
                                    samlssoServiceProviderDTO_dest
                                            .setDoEnableEncryptedAssertion(Boolean.parseBoolean(property.getValue
                                                    ()));
                                }
                                if (property.getName().equals(
                                        SAMLApplicationDeployer
                                                .SAMLApplicationConstants.DO_SIGN_ASSERTIONS)) {
                                    samlssoServiceProviderDTO_dest
                                            .setDoSignAssertions(Boolean.parseBoolean(property.getValue
                                                    ()));
                                }
                                if (property.getName().equals(
                                        SAMLApplicationDeployer
                                                .SAMLApplicationConstants.DO_SIGN_RESPONSE)) {
                                    samlssoServiceProviderDTO_dest
                                            .setDoSignResponse(Boolean.parseBoolean(property.getValue
                                                    ()));
                                }
                                if (property.getName().equals(
                                        SAMLApplicationDeployer
                                                .SAMLApplicationConstants.DO_SINGLE_LOGOUT)) {
                                    samlssoServiceProviderDTO_dest
                                            .setDoSingleLogout(Boolean.parseBoolean(property.getValue
                                                    ()));
                                }
                                if (property.getName()
                                        .equals(SAMLApplicationDeployer
                                                        .SAMLApplicationConstants.DO_VALIDATE_SIGNATURE_IN_REQUEST)) {
                                    samlssoServiceProviderDTO_dest
                                            .setDoValidateSignatureInRequests(Boolean.parseBoolean(property.getValue
                                                    ()));
                                }
                                if (property.getName().equals(
                                        SAMLApplicationDeployer
                                                .SAMLApplicationConstants.ENABLE_ATTRIBUTE_PROFILE)) {
                                    samlssoServiceProviderDTO_dest
                                            .setEnableAttributeProfile(Boolean.parseBoolean(property.getValue
                                                    ()));
                                }
                                if (property.getName().equals(
                                        SAMLApplicationDeployer
                                                .SAMLApplicationConstants.ENABLE_ATTRIBUTES_BY_DEFAULT)) {
                                    samlssoServiceProviderDTO_dest
                                            .setEnableAttributesByDefault(Boolean.parseBoolean(property.getValue
                                                    ()));
                                }
                                if (property.getName().equals(
                                        SAMLApplicationDeployer
                                                .SAMLApplicationConstants.IDP_INIT_SLO_ENABLED)) {
                                    samlssoServiceProviderDTO_dest
                                            .setIdPInitSLOEnabled(Boolean.parseBoolean(property.getValue
                                                    ()));
                                }
                                if (property.getName().equals(
                                        SAMLApplicationDeployer
                                                .SAMLApplicationConstants.IDP_INIT_SSO_ENABLED)) {
                                    samlssoServiceProviderDTO_dest
                                            .setIdPInitSSOEnabled(Boolean.parseBoolean(property.getValue
                                                    ()));
                                }
                                if (property.getName().equals(
                                        SAMLApplicationDeployer
                                                .SAMLApplicationConstants.IDP_INIT_SLO_RETURN_TO_URLS)) {
                                    String[] urls = property.getValue().split(",");
                                    samlssoServiceProviderDTO_dest.setIdpInitSLOReturnToURLs(urls);
                                }
                                if (property.getName().equals(
                                        SAMLApplicationDeployer
                                                .SAMLApplicationConstants.KEY_ENCRYPTION_ALGORITH_URI)) {
                                    samlssoServiceProviderDTO_dest.setKeyEncryptionAlgorithmURI(property.getValue());
                                }
                                if (property.getName().equals(
                                        SAMLApplicationDeployer
                                                .SAMLApplicationConstants.LOGIN_PAGE_URL)) {
                                    samlssoServiceProviderDTO_dest.setLoginPageURL(property.getValue());
                                }
                                if (property.getName().equals(
                                        SAMLApplicationDeployer
                                                .SAMLApplicationConstants.NAME_ID_FORMAT)) {
                                    samlssoServiceProviderDTO_dest.setNameIDFormat(property.getValue());
                                }
                                if (property.getName().equals(
                                        SAMLApplicationDeployer
                                                .SAMLApplicationConstants.NAME_ID_CLAIM_URI)) {
                                    samlssoServiceProviderDTO_dest.setNameIdClaimUri(property.getValue());
                                }
                                if (property.getName().equals(
                                        SAMLApplicationDeployer
                                                .SAMLApplicationConstants.REQUEST_AUDIENCES)) {
                                    String[] audiences = property.getValue().split(",");
                                    samlssoServiceProviderDTO_dest.setRequestedAudiences(audiences);
                                }
                                if (property.getName().equals(
                                        SAMLApplicationDeployer
                                                .SAMLApplicationConstants.REQUESTED_RECIPIENTS)) {
                                    String[] recipients = property.getValue().split(",");
                                    samlssoServiceProviderDTO_dest.setRequestedRecipients(recipients);
                                }
                                if (property.getName().equals(
                                        SAMLApplicationDeployer
                                                .SAMLApplicationConstants.REQUESTED_CLAIMS)) {
                                    String[] claims = property.getValue().split(",");
                                    samlssoServiceProviderDTO_dest.setRequestedClaims(claims);
                                }
                                if (property.getName().equals(
                                        SAMLApplicationDeployer
                                                .SAMLApplicationConstants.SIGNING_ALGORITHM_URI)) {
                                    samlssoServiceProviderDTO_dest.setSigningAlgorithmURI(property.getValue());
                                }
                                if (property.getName().equals(
                                        SAMLApplicationDeployer
                                                .SAMLApplicationConstants.SLO_REQUEST_URL)) {
                                    samlssoServiceProviderDTO_dest.setSloRequestURL(property.getValue());
                                }
                                if (property.getName().equals(
                                        SAMLApplicationDeployer
                                                .SAMLApplicationConstants.SLO_RESPONSE_URL)) {
                                    samlssoServiceProviderDTO_dest.setSloResponseURL(property.getValue());
                                }
                                if (property.getName()
                                        .equals(SAMLApplicationDeployer
                                                        .SAMLApplicationConstants.ATTRIBUTE_CONSUMING_SERVICE_INDEX)) {
                                    samlssoServiceProviderDTO_dest
                                            .setAttributeConsumingServiceIndex(property.getValue());
                                }
                            }

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
                                                        SAMLApplicationDeployer.SAMLApplicationConstants
                                                                .ATTR_CONSUMER_SERVICE_INDEX);
                                        consumerIndexProperty.setValue(attributeConsumingServiceIndex);

                                        properties_source.add(consumerIndexProperty);
                                        inboundAuthenticationRequestConfig_source.setProperties(properties_source);
                                    }
                                }
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            } catch (IdentitySAMLSSOConfigServiceIdentityException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                    }
                }
            }
            IdentityServer identityServer = new IdentityServer(server);

            updateProperty.put(identityServerArtifact.getArtifactFile()
                               +"-SAML2.IdPEntityId", identityServer.getHost());
            String samlEndpoint = "https://" + identityServer.getHost() ;
            if(identityServer.getPort() > 0){
                samlEndpoint += ":" + identityServer.getPort() ;
            }
            updateProperty.put(identityServerArtifact.getArtifactFile()
                               +"-SAML2.IdPURL", samlEndpoint + "/samlsso");


            IdentityServerDeployer.updateProperty(identityServerArtifact,updateProperty);
        }

        private void updateSensibleDefaults(InboundAuthenticationRequestConfig
                                                    inboundAuthenticationRequestConfig_source) {

            List<Property> properties = inboundAuthenticationRequestConfig_source.getProperties();
            Set<String> propertyNameSet = new HashSet<>();
            for (Property property : properties) {
                propertyNameSet.add(property.getName());
            }

            if (!propertyNameSet.contains(
                    SAMLApplicationDeployer.SAMLApplicationConstants
                            .NAME_ID_FORMAT)) {
                Property property = new Property();
                property.setName(
                        SAMLApplicationDeployer.SAMLApplicationConstants
                                .NAME_ID_FORMAT);
                property.setValue("urn/oasis/names/tc/SAML/1.1/nameid-format/emailAddress");
                properties.add(property);
            }
            if (!propertyNameSet.contains(
                    SAMLApplicationDeployer.SAMLApplicationConstants
                            .SIGNING_ALGORITHM_URI)) {
                Property property = new Property();
                property.setName(
                        SAMLApplicationDeployer.SAMLApplicationConstants
                                .SIGNING_ALGORITHM_URI);
                property.setValue("http://www.w3.org/2000/09/xmldsig#rsa-sha1");
                properties.add(property);
            }
            if (!propertyNameSet.contains(
                    SAMLApplicationDeployer.SAMLApplicationConstants
                            .DIGEST_ALGORITH_URI)) {
                Property property = new Property();
                property.setName(
                        SAMLApplicationDeployer.SAMLApplicationConstants
                                .DIGEST_ALGORITH_URI);
                property.setValue("http://www.w3.org/2000/09/xmldsig#sha1");
                properties.add(property);
            }
            if (!propertyNameSet.contains(
                    SAMLApplicationDeployer.SAMLApplicationConstants
                            .ASSERTION_ENCRYPTION_ALGORITH_URI)) {
                Property property = new Property();
                property.setName(
                        SAMLApplicationDeployer.SAMLApplicationConstants
                                .ASSERTION_ENCRYPTION_ALGORITH_URI);
                property.setValue("http://www.w3.org/2001/04/xmlenc#aes256-cbc");
                properties.add(property);
            }
            if (!propertyNameSet.contains(
                    SAMLApplicationDeployer.SAMLApplicationConstants
                            .KEY_ENCRYPTION_ALGORITH_URI)) {
                Property property = new Property();
                property.setName(
                        SAMLApplicationDeployer.SAMLApplicationConstants
                                .KEY_ENCRYPTION_ALGORITH_URI);
                property.setValue("http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p");
                properties.add(property);
            }
        }


        public static class SAMLApplicationConstants {
            public static final String ISSUER = "issuer";
            public static final String ASSERTION_CONSUMER_URL = "assertionConsumerUrl";
            public static final String ASSERTION_ENCRYPTION_ALGORITH_URI = "assertionEncryptionAlgorithmURI";
            public static final String ATTRIBUTE_CONSUMING_SERVICE_INDEX = "attributeConsumingServiceIndex";
            public static final String CERT_ALIAS = "certAlias";
            public static final String DEFAULT_ASSERTION_CONSUMER_URL = "defaultAssertionConsumerUrl";
            public static final String DIGEST_ALGORITH_URI = "digestAlgorithmURI";
            public static final String DO_ENABLE_ENCRYPTED_ASSETION = "doEnableEncryptedAssertion";
            public static final String DO_SIGN_ASSERTIONS = "doSignAssertions";
            public static final String DO_SIGN_RESPONSE = "doSignResponse";
            public static final String DO_SINGLE_LOGOUT = "doSingleLogout";
            public static final String DO_VALIDATE_SIGNATURE_IN_REQUEST = "doValidateSignatureInRequests";
            public static final String ENABLE_ATTRIBUTE_PROFILE = "enableAttributeProfile";
            public static final String ENABLE_ATTRIBUTES_BY_DEFAULT = "enableAttributesByDefault";
            public static final String IDP_INIT_SLO_ENABLED = "idPInitSLOEnabled";
            public static final String IDP_INIT_SSO_ENABLED = "idPInitSSOEnabled";
            public static final String IDP_INIT_SLO_RETURN_TO_URLS = "idpInitSLOReturnToURLs";
            public static final String KEY_ENCRYPTION_ALGORITH_URI = "keyEncryptionAlgorithmURI";
            public static final String LOGIN_PAGE_URL = "loginPageURL";
            public static final String NAME_ID_FORMAT = "nameIDFormat";
            public static final String NAME_ID_CLAIM_URI = "nameIdClaimUri";
            public static final String REQUEST_AUDIENCES = "requestedAudiences";
            public static final String REQUESTED_CLAIMS = "requestedClaims";
            public static final String REQUESTED_RECIPIENTS = "requestedRecipients";
            public static final String SIGNING_ALGORITHM_URI = "signingAlgorithmURI";
            public static final String SLO_REQUEST_URL = "sloRequestURL";
            public static final String SLO_RESPONSE_URL = "sloResponseURL";
            public static final String SUPPORTED_ASSERTION_QUERY_REQUEST_TYPES = "supportedAssertionQueryRequestTypes";
            public static final String IS_ASSERTION_QUERY_REQUEST_PROFILE_ENABLED =
                    "isAssertionQueryRequestProfileEnabled";
            public static final String ATTR_CONSUMER_SERVICE_INDEX = "attrConsumServiceIndex";
        }
    }
}
