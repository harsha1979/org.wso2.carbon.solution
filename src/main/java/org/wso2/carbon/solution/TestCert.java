package org.wso2.carbon.solution;

import org.apache.geronimo.mail.util.StringBufferOutputStream;
import sun.misc.BASE64Encoder;
import sun.security.provider.X509Factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by harshat on 11/25/17.
 */
public class TestCert {
    public static void main(String []args){
        try {
            File file = new File("/home/harshat/wso2/demo-suite/project/org.wso2.carbon.solution/demo-resources/servers/identity-server/wso2outboundconnectorcert.crt");
            StringBuffer stringBuffer = new StringBuffer();
            StringBufferOutputStream out = new StringBufferOutputStream(stringBuffer);
            BASE64Encoder encoder = new BASE64Encoder();
            System.out.println(X509Factory.BEGIN_CERT);
            encoder.encodeBuffer(new FileInputStream(file), out);

            System.out.println(X509Factory.END_CERT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
