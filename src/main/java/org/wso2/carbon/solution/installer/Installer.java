package org.wso2.carbon.solution.installer;

import org.apache.commons.lang.StringUtils;
import org.wso2.carbon.solution.CarbonSolutionException;

import java.io.File;

public abstract class Installer {
    public abstract boolean canHandle(String path) throws CarbonSolutionException;

    public abstract void install(String path) throws CarbonSolutionException;

    protected String getServerName(String path) throws CarbonSolutionException {
        if (StringUtils.isNotEmpty(path)) {
            String[] split = path.split(File.separator);
            if (split != null && split.length > 1) {
                return split[1];
            }
        }
        throw new CarbonSolutionException("Given path is wrong to the installer.");
    }
}
