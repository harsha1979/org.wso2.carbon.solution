package org.wso2.carbon.solution;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.LoginResult;
import com.sforce.soap.metadata.*;
import com.sforce.soap.metadata.Error;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

/**
 * Login utility.
 */
public class MetadataLoginUtil {

    public static void login() throws ConnectionException {
        final String USERNAME = "wso2demosuite@wso2.com";
        // This is only a sample. Hard coding passwords in source files is a bad practice.
        final String PASSWORD = "Samitha_2012XIPSw6bqoVQpdB9bkc6SzHw4I";
        final String URL = "https://login.salesforce.com/services/Soap/c/41.0";
        final LoginResult loginResult = loginToSalesforce(USERNAME, PASSWORD, URL);
        MetadataConnection metadataConnection = createMetadataConnection(loginResult);

        Profile profile = new Profile();
        profile.setFullName("XXXX22");
        profile.setCustom(true);
        profile.setDescription("dfdfdfdfdf");
        SaveResult[] metadata = metadataConnection.createMetadata(new Metadata[] { profile });
        for (SaveResult saveResult : metadata) {
            System.out.print(saveResult.isSuccess());
            if(!saveResult.isSuccess()){
                Error[] errors = saveResult.getErrors();
                for (Error error : errors) {
                    System.out.print(error.getMessage());
                }
            }
        }

        /*ReadResult connectedApp1 = metadataConnection
                .readMetadata("Profile", new String[] { "Chatter External User" });
        String s = connectedApp1.toString();
        System.out.print(s);
        Metadata[] records = connectedApp1.getRecords();
        for (Metadata record : records) {
            System.out.print(record.getFullName());
        }*/

        /*metadataConnection.deleteMetadata("ConnectedApp" , new String[]{"testapp"});
        ConnectedApp connectedApp = new ConnectedApp();
        connectedApp.setFullName("testapp");
        connectedApp.setContactEmail("chamilak1981@gmail.com");
        connectedApp.setLabel("testapp");


        ConnectedAppOauthConfig connectedAppOauthConfig = new ConnectedAppOauthConfig();
        connectedAppOauthConfig.setCallbackUrl("https://login.salesforce.com/services/oauth2/token");
        connectedAppOauthConfig.
                setConsumerKey("3MVG9d8..z.hDcPIgotHn.4NY8e0igXmwXzvrZ8rddHXfhCAdHnvWRpJV9dlGtqmzwxjcwsC2ow==");
        connectedAppOauthConfig.setConsumerSecret("4013380608199274333");
        connectedAppOauthConfig.setCertificate(d);


        ConnectedAppOauthAccessScope connectedAppOauthAccessScope =ConnectedAppOauthAccessScope.Full ;

        connectedAppOauthConfig.setScopes(new ConnectedAppOauthAccessScope[]{connectedAppOauthAccessScope});
        connectedApp.setOauthConfig(connectedAppOauthConfig);



        SaveResult[] metadata = metadataConnection.createMetadata(new Metadata[] { connectedApp });
        for (SaveResult saveResult : metadata) {
            System.out.print(saveResult.isSuccess());
            if(!saveResult.isSuccess()){
                Error[] errors = saveResult.getErrors();
                for (Error error : errors) {
                    System.out.print(error.getMessage());
                }
            }
        }
        */


    }

    private static MetadataConnection createMetadataConnection(
            final LoginResult loginResult) throws ConnectionException {
        final ConnectorConfig config = new ConnectorConfig();
        config.setServiceEndpoint(loginResult.getMetadataServerUrl());
        config.setSessionId(loginResult.getSessionId());
        return new MetadataConnection(config);
    }

    private static LoginResult loginToSalesforce(
            final String username,
            final String password,
            final String loginUrl) throws ConnectionException {
        final ConnectorConfig config = new ConnectorConfig();
        config.setAuthEndpoint(loginUrl);
        config.setServiceEndpoint(loginUrl);
        config.setManualLogin(true);
        return (new EnterpriseConnection(config)).login(username, password);
    }

    public static void main(String []args){
        try {
            login();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String d ="-----BEGIN CERTIFICATE-----\n"
              + "MIIEmTCCA4GgAwIBAgIOAV/yd+uYAAAAADKyCNgwDQYJKoZIhvcNAQELBQAwgYox\n"
              + "IjAgBgNVBAMMGXdzbzJvdXRib3VuZGNvbm5lY3RvcmNlcnQxGDAWBgNVBAsMDzAw\n"
              + "RDdGMDAwMDAzZW03VjEXMBUGA1UECgwOU2FsZXNmb3JjZS5jb20xFjAUBgNVBAcM\n"
              + "DVNhbiBGcmFuY2lzY28xCzAJBgNVBAgMAkNBMQwwCgYDVQQGEwNVU0EwHhcNMTcx\n"
              + "MTI1MDkxNzQ2WhcNMTgxMTI1MDAwMDAwWjCBijEiMCAGA1UEAwwZd3NvMm91dGJv\n"
              + "dW5kY29ubmVjdG9yY2VydDEYMBYGA1UECwwPMDBEN0YwMDAwMDNlbTdWMRcwFQYD\n"
              + "VQQKDA5TYWxlc2ZvcmNlLmNvbTEWMBQGA1UEBwwNU2FuIEZyYW5jaXNjbzELMAkG\n"
              + "A1UECAwCQ0ExDDAKBgNVBAYTA1VTQTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCC\n"
              + "AQoCggEBAJgBIR8y/ANUpMKZ2a8uT/s4ez7grC9JsmZOKx9hDDlG77ZAxhaLkF7E\n"
              + "eF74OaCLj6RSYVy7t6+LvY4c5AQOyGle8HUrg17WiZ20a12822CYm1U9uQp72/H2\n"
              + "xlXFUBjpn9cLwxHDO/KVJOq1RwkDQLV+QRlq+JCH/JYxpTdk5VIsfPzNmapd586a\n"
              + "aLhsnZCI5sPRULkYchTJcezcpqb+/enOEzOPWim1CgpKyy/S85dTZBjO6wfx7K4U\n"
              + "wDpylrmNUpCkhSNWuYCn/WQ4D9b/9poUu2syA4ynz9QInvSRoQDZb47UdL+RY5bc\n"
              + "gCF82+t3GYTsGh2l8PbOnxWOVeeoUVcCAwEAAaOB+jCB9zAdBgNVHQ4EFgQUkUtX\n"
              + "4ydUNJkUTkZ8cSUK3mbjke8wDwYDVR0TAQH/BAUwAwEB/zCBxAYDVR0jBIG8MIG5\n"
              + "gBSRS1fjJ1Q0mRRORnxxJQreZuOR76GBkKSBjTCBijEiMCAGA1UEAwwZd3NvMm91\n"
              + "dGJvdW5kY29ubmVjdG9yY2VydDEYMBYGA1UECwwPMDBEN0YwMDAwMDNlbTdWMRcw\n"
              + "FQYDVQQKDA5TYWxlc2ZvcmNlLmNvbTEWMBQGA1UEBwwNU2FuIEZyYW5jaXNjbzEL\n"
              + "MAkGA1UECAwCQ0ExDDAKBgNVBAYTA1VTQYIOAV/yd+uYAAAAADKyCNgwDQYJKoZI\n"
              + "hvcNAQELBQADggEBABxguYLaMMGFUal8374xSD4JA2Tn3BjCoJm5kEhVGTxK81g+\n"
              + "2Cpbk5Pj7chUxK80tlD22c3IyM0d4HvWs6xWqy31uHEk7Rack0vP1Y4zNYbskTKZ\n"
              + "gTMXJvi0AlygsUMMtVlF9MNKHTFylTpGiMZoRQAwAJ0lJvNreSp3WI9XVTc7IA1I\n"
              + "lzvf4J/EoKbJI5Lb5yukUdrTWoWU/cbbn+ri20fz+ZoctWtsvqDDMPqtrc+eEyMQ\n"
              + "mJ+qKf/6hEKjhhRnWsIn/Sx928nffvE2cZLVTt+yti+AfuJPx3y5V/u577hdr3tP\n"
              + "EHbye66+6FvWYPJQbni/sv05u/FSfG/e9rRHo1M=\n"
              + "-----END CERTIFICATE-----";
}