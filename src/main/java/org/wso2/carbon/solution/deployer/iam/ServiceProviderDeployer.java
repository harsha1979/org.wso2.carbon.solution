package org.wso2.carbon.solution.deployer.iam;


import org.apache.axis2.AxisFault;
import org.wso2.carbon.identity.application.common.model.xsd.ServiceProvider;
import org.wso2.carbon.identity.application.mgt.stub.IdentityApplicationManagementServiceStub;
import org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceIdentityOAuthAdminException;
import org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceStub;
import org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerAppDTO;
import org.wso2.carbon.identity.sso.saml.stub.IdentitySAMLSSOConfigServiceIdentityException;
import org.wso2.carbon.identity.sso.saml.stub.IdentitySAMLSSOConfigServiceStub;
import org.wso2.carbon.identity.sso.saml.stub.types.SAMLSSOServiceProviderDTO;
import org.wso2.carbon.identity.sso.saml.stub.types.SAMLSSOServiceProviderInfoDTO;
import org.wso2.carbon.solution.builder.iam.ServiceProviderBuilder;
import org.wso2.carbon.solution.deployer.Deployer;
import org.wso2.carbon.solution.model.config.IdentityServer;
import org.wso2.carbon.solution.model.iam.sp.InboundAuthenticationConfig;
import org.wso2.carbon.solution.model.iam.sp.InboundAuthenticationRequestConfig;
import org.wso2.carbon.solution.model.iam.sp.Property;
import org.wso2.carbon.solution.model.iam.sp.ServiceProviderEntity;
import org.wso2.carbon.solution.util.AuthenticationException;
import org.wso2.carbon.solution.util.ResourceLoader;
import org.wso2.carbon.solution.util.ServiceAuthenticator;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceProviderDeployer implements Deployer {
    private IdentityApplicationManagementServiceStub stub;

    public static String PROTOCOL_SAML = "samlsso";
    public static String PROTOCOL_OAUTH2 = "oauth2";


    public ServiceProviderDeployer() {

        //#TODO: must read from config.
        try {
            stub = new IdentityApplicationManagementServiceStub
                    ("https://localhost:9443/services/IdentityApplicationManagementService");
            ServiceAuthenticator authenticator = ServiceAuthenticator.getInstance();
            authenticator.setAccessPassword("admin");
            authenticator.setAccessUsername("admin");
            authenticator.authenticate(stub._getServiceClient());
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
    }

    public void deploy(String artifactPath, IdentityServer identityServer) {
        Map<String, ServiceProviderEntity> serviceProviderEntityMap = ResourceLoader
                .loadResources(artifactPath, ServiceProviderEntity.class);

        for (ServiceProviderEntity serviceProviderEntity_source : serviceProviderEntityMap
                .values()) {
            try {
                org.wso2.carbon.solution.model.iam.sp.ServiceProvider serviceProvider_source
                        = serviceProviderEntity_source
                        .getServiceProvider();
                ServiceProvider application = null;

                try {
                    application = stub.getApplication(serviceProvider_source.getName());
                    //stub.deleteApplication(application.getApplicationName());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (availableInboundProtocol(PROTOCOL_SAML, serviceProvider_source)) {
                    SAMLApplicationDeployer samlApplicationDeployer = new SAMLApplicationDeployer();
                    samlApplicationDeployer.deploy(serviceProvider_source);
                }
                if (availableInboundProtocol(PROTOCOL_OAUTH2, serviceProvider_source)) {
                    OAuth2ApplicationDeployer oAuth2ApplicationDeployer = new OAuth2ApplicationDeployer();
                    oAuth2ApplicationDeployer.deploy(serviceProvider_source);
                }

                updateDefaults(serviceProvider_source);
                ServiceProvider serviceProvider_dest
                        = ServiceProviderBuilder.getInstance().buildServiceProvider(serviceProvider_source);
                if (application == null) {
                    stub.createApplication(serviceProvider_dest);
                    try {
                        application = stub.getApplication(serviceProvider_source.getName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                serviceProvider_dest.setApplicationID(application.getApplicationID());
                stub.updateApplication(serviceProvider_dest);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

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

    private void updateDefaults(org.wso2.carbon.solution.model.iam.sp.ServiceProvider serviceProvider_source) {

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
        private OAuthAdminServiceStub oAuthAdminServiceStub;

        public OAuth2ApplicationDeployer() {

            //#TODO: must read from config.
            try {
                oAuthAdminServiceStub = new OAuthAdminServiceStub
                        ("https://localhost:9443/services/OAuthAdminService");
                ServiceAuthenticator authenticator = ServiceAuthenticator.getInstance();
                authenticator.setAccessPassword("admin");
                authenticator.setAccessUsername("admin");
                authenticator.authenticate(oAuthAdminServiceStub._getServiceClient());
            } catch (AxisFault axisFault) {
                axisFault.printStackTrace();
            } catch (AuthenticationException e) {
                e.printStackTrace();
            }
        }


        public void deploy(org.wso2.carbon.solution.model.iam.sp.ServiceProvider serviceProvider_source) {
            InboundAuthenticationConfig inboundAuthenticationConfig_source = serviceProvider_source
                    .getInboundAuthenticationConfig();
            if (inboundAuthenticationConfig_source != null) {
                List<InboundAuthenticationRequestConfig> inboundAuthenticationRequestConfigs_source
                        = inboundAuthenticationConfig_source
                        .getInboundAuthenticationRequestConfigs();
                if (inboundAuthenticationRequestConfigs_source != null) {
                    for (InboundAuthenticationRequestConfig inboundAuthenticationRequestConfig_source :
                            inboundAuthenticationRequestConfigs_source) {
                        if (PROTOCOL_OAUTH2.equals(inboundAuthenticationRequestConfig_source.getInboundAuthType())) {
                            List<Property> properties = inboundAuthenticationRequestConfig_source.getProperties();
                            OAuthConsumerAppDTO oAuthConsumerAppDTO_dest = new OAuthConsumerAppDTO();
                            for (Property property : properties) {
                                if (property.getName().equals(SAMLApplicationConstants.OAUTH_VERSION)) {
                                    oAuthConsumerAppDTO_dest.setOAuthVersion(property.getValue());
                                }
                                if (property.getName().equals(SAMLApplicationConstants.APPLICATION_NAME)) {
                                    oAuthConsumerAppDTO_dest.setApplicationName(property.getValue());
                                }
                                if (property.getName().equals(SAMLApplicationConstants.CALLBACK_URL)) {
                                    oAuthConsumerAppDTO_dest.setCallbackUrl(property.getValue());
                                }
                                if (property.getName().equals(SAMLApplicationConstants.GRANT_TYPES)) {
                                    oAuthConsumerAppDTO_dest.setGrantTypes(property.getValue());
                                }
                                if (property.getName().equals(SAMLApplicationConstants.OAUTH_CONSUMER_KEY)) {
                                    oAuthConsumerAppDTO_dest.setOauthConsumerKey(property.getValue());
                                }
                                if (property.getName().equals(SAMLApplicationConstants.OAUTH_CONSUMER_SECRET)) {
                                    oAuthConsumerAppDTO_dest.setOauthConsumerSecret(property.getValue());
                                }
                                if (property.getName().equals(SAMLApplicationConstants.PKCE_MANDATORY)) {
                                    oAuthConsumerAppDTO_dest.setPkceMandatory(Boolean.parseBoolean(property.getValue()));
                                }
                                if (property.getName().equals(SAMLApplicationConstants.PKCE_SUPPORT_PLAN)) {
                                    oAuthConsumerAppDTO_dest.setPkceSupportPlain(Boolean.parseBoolean(property.getValue()));
                                }
                                if (property.getName().equals(SAMLApplicationConstants.STATE)) {
                                    oAuthConsumerAppDTO_dest.setState(property.getValue());
                                }
                                if (property.getName().equals(SAMLApplicationConstants.REFRESH_TOKEN_EXPIRE_TIME)) {
                                    oAuthConsumerAppDTO_dest.setRefreshTokenExpiryTime(Long.parseLong(property.getValue
                                            ()));
                                }
                                if (property.getName().equals(SAMLApplicationConstants.USER_ACCESS_TOKEN_EXPIRE_TIME)) {
                                    oAuthConsumerAppDTO_dest.setUserAccessTokenExpiryTime(Long.parseLong(property.getValue
                                            ()));
                                }
                                if (property.getName().equals(SAMLApplicationConstants.APPLICATION_ACCESS_TOKEN_EXPIRE_TIME)) {
                                    oAuthConsumerAppDTO_dest.setApplicationAccessTokenExpiryTime(Long.parseLong(property.getValue
                                            ()));
                                }
                                if (property.getName().equals(SAMLApplicationConstants.USER_NAME)) {
                                    oAuthConsumerAppDTO_dest.setOAuthVersion(property.getValue());
                                }
                            }

                            try {
                                try {
                                    OAuthConsumerAppDTO[] allOAuthApplicationData = oAuthAdminServiceStub
                                            .getAllOAuthApplicationData();
                                    if(allOAuthApplicationData != null) {
                                        for (OAuthConsumerAppDTO authConsumerAppDTO : allOAuthApplicationData) {
                                            if (authConsumerAppDTO.getApplicationName()
                                                    .equals(oAuthConsumerAppDTO_dest.getApplicationName())) {
                                                oAuthAdminServiceStub.removeOAuthApplicationData(
                                                        oAuthConsumerAppDTO_dest.getOauthConsumerKey());
                                                break;
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                oAuthAdminServiceStub.registerOAuthApplicationData(oAuthConsumerAppDTO_dest);
                                OAuthConsumerAppDTO[] allOAuthApplicationData1 = oAuthAdminServiceStub
                                        .getAllOAuthApplicationData();
                                for (OAuthConsumerAppDTO oAuthConsumerAppDTO : allOAuthApplicationData1) {
                                    if(oAuthConsumerAppDTO.getApplicationName().equals(oAuthConsumerAppDTO_dest
                                                                                               .getApplicationName())){
                                        List<Property> newProperties = new ArrayList<Property>();
                                        Property property = new Property();
                                        property.setName(SAMLApplicationConstants.OAUTH_CONSUMER_SECRET);
                                        property.setValue(oAuthConsumerAppDTO.getOauthConsumerSecret());
                                        newProperties.add(property);
                                        inboundAuthenticationRequestConfig_source.setProperties(newProperties);
                                        inboundAuthenticationRequestConfig_source.setInboundAuthKey
                                                (oAuthConsumerAppDTO.getOauthConsumerKey());
                                        break ;
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
        }

        static class SAMLApplicationConstants {

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
        private IdentitySAMLSSOConfigServiceStub identitySAMLSSOConfigServiceStub;

        public SAMLApplicationDeployer() {

            //#TODO: must read from config.
            try {
                identitySAMLSSOConfigServiceStub = new IdentitySAMLSSOConfigServiceStub
                        ("https://localhost:9443/services/IdentitySAMLSSOConfigService");
                ServiceAuthenticator authenticator = ServiceAuthenticator.getInstance();
                authenticator.setAccessPassword("admin");
                authenticator.setAccessUsername("admin");
                authenticator.authenticate(identitySAMLSSOConfigServiceStub._getServiceClient());
            } catch (AxisFault axisFault) {
                axisFault.printStackTrace();
            } catch (AuthenticationException e) {
                e.printStackTrace();
            }
        }


        public void deploy(org.wso2.carbon.solution.model.iam.sp.ServiceProvider serviceProvider_source) {
            InboundAuthenticationConfig inboundAuthenticationConfig_source = serviceProvider_source
                    .getInboundAuthenticationConfig();
            if (inboundAuthenticationConfig_source != null) {
                List<InboundAuthenticationRequestConfig> inboundAuthenticationRequestConfigs_source
                        = inboundAuthenticationConfig_source
                        .getInboundAuthenticationRequestConfigs();
                if (inboundAuthenticationRequestConfigs_source != null) {
                    for (InboundAuthenticationRequestConfig inboundAuthenticationRequestConfig_source :
                            inboundAuthenticationRequestConfigs_source) {
                        if (PROTOCOL_SAML.equals(inboundAuthenticationRequestConfig_source.getInboundAuthType())) {
                            List<Property> properties = inboundAuthenticationRequestConfig_source.getProperties();
                            SAMLSSOServiceProviderDTO samlssoServiceProviderDTO_dest = new SAMLSSOServiceProviderDTO();
                            for (Property property : properties) {
                                if (property.getName().equals(SAMLApplicationConstants.ISSUER)) {
                                    samlssoServiceProviderDTO_dest.setIssuer(property.getValue());
                                }
                                if (property.getName().equals(SAMLApplicationConstants.ASSERTION_CONSUMER_URL)) {
                                    samlssoServiceProviderDTO_dest.setAssertionConsumerUrl(property.getValue());
                                }
                                if (property.getName().equals(SAMLApplicationConstants.ASSERTION_CONSUMER_URL)) {
                                    String[] assertionConsumerUrls = property.getValue().split(",");
                                    samlssoServiceProviderDTO_dest.setAssertionConsumerUrls(assertionConsumerUrls);
                                }
                                if (property.getName()
                                        .equals(SAMLApplicationConstants.ASSERTION_ENCRYPTION_ALGORITH_URI)) {
                                    samlssoServiceProviderDTO_dest.setAssertionEncryptionAlgorithmURI(property.getValue());
                                }
                                if (property.getName()
                                        .equals(SAMLApplicationConstants.IS_ASSERTION_QUERY_REQUEST_PROFILE_ENABLED)) {
                                    samlssoServiceProviderDTO_dest.setAssertionQueryRequestProfileEnabled(
                                            Boolean.parseBoolean(property.getValue()));
                                }
                                if (property.getName()
                                        .equals(SAMLApplicationConstants.SUPPORTED_ASSERTION_QUERY_REQUEST_TYPES)) {
                                    samlssoServiceProviderDTO_dest
                                            .setSupportedAssertionQueryRequestTypes(property.getValue());
                                }
                                if (property.getName().equals(SAMLApplicationConstants.CERT_ALIAS)) {
                                    samlssoServiceProviderDTO_dest.setCertAlias(property.getValue());
                                }
                                if (property.getName()
                                        .equals(SAMLApplicationConstants.DEFAULT_ASSERTION_CONSUMER_URL)) {
                                    samlssoServiceProviderDTO_dest.setDefaultAssertionConsumerUrl(property.getValue());
                                }
                                if (property.getName().equals(SAMLApplicationConstants.DIGEST_ALGORITH_URI)) {
                                    samlssoServiceProviderDTO_dest.setDigestAlgorithmURI(property.getValue());
                                }
                                if (property.getName().equals(SAMLApplicationConstants.DO_ENABLE_ENCRYPTED_ASSETION)) {
                                    samlssoServiceProviderDTO_dest
                                            .setDoEnableEncryptedAssertion(Boolean.parseBoolean(property.getValue
                                                    ()));
                                }
                                if (property.getName().equals(SAMLApplicationConstants.DO_SIGN_ASSERTIONS)) {
                                    samlssoServiceProviderDTO_dest.setDoSignAssertions(Boolean.parseBoolean(property.getValue
                                            ()));
                                }
                                if (property.getName().equals(SAMLApplicationConstants.DO_SIGN_RESPONSE)) {
                                    samlssoServiceProviderDTO_dest.setDoSignResponse(Boolean.parseBoolean(property.getValue
                                            ()));
                                }
                                if (property.getName().equals(SAMLApplicationConstants.DO_SINGLE_LOGOUT)) {
                                    samlssoServiceProviderDTO_dest.setDoSingleLogout(Boolean.parseBoolean(property.getValue
                                            ()));
                                }
                                if (property.getName()
                                        .equals(SAMLApplicationConstants.DO_VALIDATE_SIGNATURE_IN_REQUEST)) {
                                    samlssoServiceProviderDTO_dest
                                            .setDoValidateSignatureInRequests(Boolean.parseBoolean(property.getValue
                                                    ()));
                                }
                                if (property.getName().equals(SAMLApplicationConstants.ENABLE_ATTRIBUTE_PROFILE)) {
                                    samlssoServiceProviderDTO_dest
                                            .setEnableAttributeProfile(Boolean.parseBoolean(property.getValue
                                                    ()));
                                }
                                if (property.getName().equals(SAMLApplicationConstants.ENABLE_ATTRIBUTES_BY_DEFAULT)) {
                                    samlssoServiceProviderDTO_dest
                                            .setEnableAttributesByDefault(Boolean.parseBoolean(property.getValue
                                                    ()));
                                }
                                if (property.getName().equals(SAMLApplicationConstants.IDP_INIT_SLO_ENABLED)) {
                                    samlssoServiceProviderDTO_dest
                                            .setIdPInitSLOEnabled(Boolean.parseBoolean(property.getValue
                                                    ()));
                                }
                                if (property.getName().equals(SAMLApplicationConstants.IDP_INIT_SSO_ENABLED)) {
                                    samlssoServiceProviderDTO_dest
                                            .setIdPInitSSOEnabled(Boolean.parseBoolean(property.getValue
                                                    ()));
                                }
                                if (property.getName().equals(SAMLApplicationConstants.IDP_INIT_SLO_RETURN_TO_URLS)) {
                                    String[] urls = property.getValue().split(",");
                                    samlssoServiceProviderDTO_dest.setIdpInitSLOReturnToURLs(urls);
                                }
                                if (property.getName().equals(SAMLApplicationConstants.KEY_ENCRYPTION_ALGORITH_URI)) {
                                    samlssoServiceProviderDTO_dest.setKeyEncryptionAlgorithmURI(property.getValue());
                                }
                                if (property.getName().equals(SAMLApplicationConstants.LOGIN_PAGE_URL)) {
                                    samlssoServiceProviderDTO_dest.setLoginPageURL(property.getValue());
                                }
                                if (property.getName().equals(SAMLApplicationConstants.NAME_ID_FORMAT)) {
                                    samlssoServiceProviderDTO_dest.setNameIDFormat(property.getValue());
                                }
                                if (property.getName().equals(SAMLApplicationConstants.NAME_ID_CLAIM_URI)) {
                                    samlssoServiceProviderDTO_dest.setNameIdClaimUri(property.getValue());
                                }
                                if (property.getName().equals(SAMLApplicationConstants.REQUEST_AUDIENCES)) {
                                    String[] audiences = property.getValue().split(",");
                                    samlssoServiceProviderDTO_dest.setRequestedAudiences(audiences);
                                }
                                if (property.getName().equals(SAMLApplicationConstants.REQUESTED_RECIPIENTS)) {
                                    String[] recipients = property.getValue().split(",");
                                    samlssoServiceProviderDTO_dest.setRequestedRecipients(recipients);
                                }
                                if (property.getName().equals(SAMLApplicationConstants.REQUESTED_CLAIMS)) {
                                    String[] claims = property.getValue().split(",");
                                    samlssoServiceProviderDTO_dest.setRequestedClaims(claims);
                                }
                                if (property.getName().equals(SAMLApplicationConstants.SIGNING_ALGORITHM_URI)) {
                                    samlssoServiceProviderDTO_dest.setSigningAlgorithmURI(property.getValue());
                                }
                                if (property.getName().equals(SAMLApplicationConstants.SLO_REQUEST_URL)) {
                                    samlssoServiceProviderDTO_dest.setSloRequestURL(property.getValue());
                                }
                                if (property.getName().equals(SAMLApplicationConstants.SLO_RESPONSE_URL)) {
                                    samlssoServiceProviderDTO_dest.setSloResponseURL(property.getValue());
                                }
                                if (property.getName()
                                        .equals(SAMLApplicationConstants.ATTRIBUTE_CONSUMING_SERVICE_INDEX)) {
                                    samlssoServiceProviderDTO_dest.setAttributeConsumingServiceIndex(property.getValue());
                                }
                            }
                            try {
                                identitySAMLSSOConfigServiceStub.removeServiceProvider(samlssoServiceProviderDTO_dest
                                                                                               .getIssuer());
                                identitySAMLSSOConfigServiceStub.addRPServiceProvider(samlssoServiceProviderDTO_dest);

                                SAMLSSOServiceProviderInfoDTO serviceProviders_dest = identitySAMLSSOConfigServiceStub
                                        .getServiceProviders();
                                SAMLSSOServiceProviderDTO[] serviceProvidersDTOs_dest = serviceProviders_dest
                                        .getServiceProviders();
                                for (SAMLSSOServiceProviderDTO serviceProviderDTO_dest : serviceProvidersDTOs_dest) {
                                    if(serviceProviderDTO_dest.getIssuer().equals(samlssoServiceProviderDTO_dest
                                                                                            .getIssuer())){
                                        String attributeConsumingServiceIndex = samlssoServiceProviderDTO_dest
                                                .getAttributeConsumingServiceIndex();
                                        List<Property> properties_source = new ArrayList<Property>();
                                        Property consumerIndexProperty = new Property();
                                        consumerIndexProperty.setName(SAMLApplicationConstants.ATTR_CONSUMER_SERVICE_INDEX);
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
        }

        static class SAMLApplicationConstants {
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
