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
import org.wso2.carbon.idp.mgt.stub.IdentityProviderMgtServiceStub;
import org.wso2.carbon.solution.builder.iam.IdentityProviderBuilder;
import org.wso2.carbon.solution.builder.iam.ServiceProviderBuilder;
import org.wso2.carbon.solution.deployer.Deployer;
import org.wso2.carbon.solution.model.config.IdentityServer;
import org.wso2.carbon.solution.model.iam.idp.IdentityProvider;
import org.wso2.carbon.solution.model.iam.idp.IdentityProviderEntity;
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

public class IdentityProviderDeployer implements Deployer {

    private IdentityProviderMgtServiceStub identityProviderMgtServiceStub;


    public IdentityProviderDeployer() {

        //#TODO: must read from config.
        try {
            identityProviderMgtServiceStub = new IdentityProviderMgtServiceStub
                    ("https://localhost:9443/services/IdentityProviderMgtService");
            ServiceAuthenticator authenticator = ServiceAuthenticator.getInstance();
            authenticator.setAccessPassword("admin");
            authenticator.setAccessUsername("admin");
            authenticator.authenticate(identityProviderMgtServiceStub._getServiceClient());
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
    }

    public void deploy(String artifactPath, IdentityServer identityServer) {
        Map<String, IdentityProviderEntity> identityProviderEntityMap = ResourceLoader
                .loadResources(artifactPath, IdentityProviderEntity.class);

        for (IdentityProviderEntity identityProviderEntity_source : identityProviderEntityMap
                .values()) {
            try {
                IdentityProvider identityProvider_source = identityProviderEntity_source.getIdentityProvider();
                org.wso2.carbon.identity.application.common.model.idp.xsd.IdentityProvider identityProvider_dest = new org.wso2.carbon.identity.application.common.model.idp.xsd.IdentityProvider();
                IdentityProviderBuilder.getInstance().buildIdentityProvider(identityProvider_source, identityProvider_dest);

                identityProviderMgtServiceStub.addIdP(identityProvider_dest);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}
