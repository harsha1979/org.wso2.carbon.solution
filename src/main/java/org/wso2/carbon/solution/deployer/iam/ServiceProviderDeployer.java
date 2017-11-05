package org.wso2.carbon.solution.deployer.iam;


import org.apache.axis2.AxisFault;
import org.wso2.carbon.identity.application.common.model.xsd.ServiceProvider;
import org.wso2.carbon.identity.application.mgt.stub
        .IdentityApplicationManagementServiceIdentityApplicationManagementException;
import org.wso2.carbon.identity.application.mgt.stub.IdentityApplicationManagementServiceStub;
import org.wso2.carbon.solution.builder.iam.ServiceProviderBuilder;
import org.wso2.carbon.solution.deployer.Deployer;
import org.wso2.carbon.solution.model.config.IdentityServer;
import org.wso2.carbon.solution.model.iam.sp.ServiceProviderEntity;
import org.wso2.carbon.solution.util.AuthenticationException;
import org.wso2.carbon.solution.util.ResourceLoader;
import org.wso2.carbon.solution.util.ServiceAuthenticator;

import java.rmi.RemoteException;
import java.util.Map;

public class ServiceProviderDeployer implements Deployer {
    private IdentityApplicationManagementServiceStub stub;

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
        }
    }

    public void deploy(String artifactPath, IdentityServer identityServer) {
        Map<String, ServiceProviderEntity> stringServiceProviderEntityMap = ResourceLoader
                .loadResources(artifactPath, ServiceProviderEntity.class);

        for (ServiceProviderEntity serviceProviderEntity_source : stringServiceProviderEntityMap
                .values()) {
            try {
                org.wso2.carbon.solution.model.iam.sp.ServiceProvider serviceProvider_source
                        = serviceProviderEntity_source
                        .getServiceProvider();
                ServiceProvider application = stub.getApplication(serviceProvider_source.getName());
                ServiceProvider serviceProvider_dest
                        = ServiceProviderBuilder.getInstance().buildServiceProvider(serviceProvider_source);
                if (application != null) {
                    serviceProvider_dest.setApplicationID(application.getApplicationID());
                    stub.updateApplication(serviceProvider_dest);
                } else {
                    stub.createApplication(serviceProvider_dest);
                }
            } catch (RemoteException e) {
            } catch (IdentityApplicationManagementServiceIdentityApplicationManagementException e) {
            }
        }
    }
}
