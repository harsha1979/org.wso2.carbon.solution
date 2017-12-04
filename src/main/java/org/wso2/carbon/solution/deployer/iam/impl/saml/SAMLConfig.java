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

/**
 * SAMLConfig enum hold all the SAML specific configs and default values.
 */
public enum SAMLConfig {

    ISSUER("issuer"),
    ASSERTION_CONSUMER_URL("assertionConsumerUrl"),
    ASSERTION_ENCRYPTION_ALGORITH_URI("assertionEncryptionAlgorithmURI",
                                      "http://www.w3.org/2001/04/xmlenc#aes256-cbc"),
    ATTRIBUTE_CONSUMING_SERVICE_INDEX("attributeConsumingServiceIndex"),
    CERT_ALIAS("certAlias", "wso2carbon"),
    DEFAULT_ASSERTION_CONSUMER_URL("defaultAssertionConsumerUrl"),
    DIGEST_ALGORITH_URI("digestAlgorithmURI", "http://www.w3.org/2000/09/xmldsig#sha1"),
    DO_ENABLE_ENCRYPTED_ASSETION("doEnableEncryptedAssertion"),
    DO_SIGN_ASSERTIONS("doSignAssertions"),
    DO_SIGN_RESPONSE("doSignResponse"),
    DO_SINGLE_LOGOUT("doSingleLogout"),
    DO_VALIDATE_SIGNATURE_IN_REQUEST("doValidateSignatureInRequests"),
    ENABLE_ATTRIBUTE_PROFILE("enableAttributeProfile"),
    ENABLE_ATTRIBUTES_BY_DEFAULT("enableAttributesByDefault"),
    IDP_INIT_SLO_ENABLED("idPInitSLOEnabled"),
    IDP_INIT_SSO_ENABLED("idPInitSSOEnabled"),
    IDP_INIT_SLO_RETURN_TO_URLS("idpInitSLOReturnToURLs"),
    KEY_ENCRYPTION_ALGORITH_URI("keyEncryptionAlgorithmURI", "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p"),
    LOGIN_PAGE_URL("loginPageURL"),
    NAME_ID_FORMAT("nameIDFormat", "urn/oasis/names/tc/SAML/1.1/nameid-format/emailAddress"),
    NAME_ID_CLAIM_URI("nameIdClaimUri"),
    REQUEST_AUDIENCES("requestedAudiences"),
    REQUESTED_CLAIMS("requestedClaims"),
    REQUESTED_RECIPIENTS("requestedRecipients"),
    SIGNING_ALGORITHM_URI("signingAlgorithmURI", "http://www.w3.org/2000/09/xmldsig#rsa-sha1"),
    SLO_REQUEST_URL("sloRequestURL"),
    SLO_RESPONSE_URL("sloResponseURL"),
    SUPPORTED_ASSERTION_QUERY_REQUEST_TYPES("supportedAssertionQueryRequestTypes"),
    IS_ASSERT_QUERY_REQ_PROF_ENABLED("isAssertionQueryRequestProfileEnabled"),
    ATTR_CONSUMER_SERVICE_INDEX("attrConsumServiceIndex");

    private String configName = "";
    private String defaultValue = "";

    private SAMLConfig(String configName, String defaultValue) {
        this.configName = configName;
        this.defaultValue = defaultValue;
    }

    private SAMLConfig(String configName) {
        this.configName = configName;
    }

    public String getConfigName() {
        return configName;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
