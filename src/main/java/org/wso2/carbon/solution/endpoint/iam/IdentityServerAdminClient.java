package org.wso2.carbon.solution.endpoint.iam;


import org.apache.axis2.AxisFault;
import org.apache.axis2.client.ServiceClient;
import org.wso2.carbon.identity.application.mgt.stub.IdentityApplicationManagementServiceStub;
import org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceStub;
import org.wso2.carbon.identity.sso.saml.stub.IdentitySAMLSSOConfigServiceStub;
import org.wso2.carbon.idp.mgt.stub.IdentityProviderMgtServiceStub;
import org.wso2.carbon.solution.endpoint.iam.config.IdentityServer;
import org.wso2.carbon.solution.model.config.server.Server;
import org.wso2.carbon.solution.util.AuthenticationException;
import org.wso2.carbon.solution.util.ServiceAuthenticator;

public class IdentityServerAdminClient {

    public static IdentityApplicationManagementServiceStub getApplicationManagementService(Server server) {
        IdentityApplicationManagementServiceStub stub = null;
        IdentityServer identityServer = new IdentityServer(server);
        try {
            String serviceURL = identityServer.getHTTPSServerURL() + "/services/IdentityApplicationManagementService";
            stub = new IdentityApplicationManagementServiceStub(serviceURL);
            ServiceClient serviceClient = stub._getServiceClient();
            doServiceAuthenticate(identityServer, serviceClient);
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        return stub;
    }

    public static IdentityProviderMgtServiceStub getIdentityProviderMgtService(Server server) {
        IdentityProviderMgtServiceStub stub = null;
        IdentityServer identityServer = new IdentityServer(server);
        try {
            String serviceURL = identityServer.getHTTPSServerURL() + "/services/IdentityProviderMgtService";
            stub = new IdentityProviderMgtServiceStub(serviceURL);
            doServiceAuthenticate(identityServer, stub._getServiceClient());
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        return stub;
    }

    public static OAuthAdminServiceStub getOAuthAdminService(Server server) {
        OAuthAdminServiceStub stub = null;
        IdentityServer identityServer = new IdentityServer(server);
        try {
            String serviceURL = identityServer.getHTTPSServerURL() + "/services/OAuthAdminService";
            stub = new OAuthAdminServiceStub(serviceURL);
            doServiceAuthenticate(identityServer, stub._getServiceClient());
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        return stub;
    }

    public static IdentitySAMLSSOConfigServiceStub getSAMLSSOConfigService(Server server) {
        IdentitySAMLSSOConfigServiceStub stub = null;
        IdentityServer identityServer = new IdentityServer(server);
        try {
            String serviceURL = identityServer.getHTTPSServerURL() + "/services/IdentitySAMLSSOConfigService";
            stub = new IdentitySAMLSSOConfigServiceStub(serviceURL);
            doServiceAuthenticate(identityServer, stub._getServiceClient());
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        return stub;
    }

    private static void doServiceAuthenticate(IdentityServer identityServer, ServiceClient serviceClient)
            throws AuthenticationException {
        ServiceAuthenticator authenticator = ServiceAuthenticator.getInstance();
        authenticator.setAccessPassword(identityServer.getUserName());
        authenticator.setAccessUsername(identityServer.getPassword());
        authenticator.authenticate(serviceClient);
    }
}
