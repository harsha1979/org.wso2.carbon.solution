package org.wso2.carbon.solution;

import org.wso2.carbon.CarbonException;

public class CarbonSolutionException extends CarbonException {
    public CarbonSolutionException() {
    }

    public CarbonSolutionException(String message) {
        super(message);
    }

    public CarbonSolutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public CarbonSolutionException(Throwable cause) {
        super(cause);
    }
}
