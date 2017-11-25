package org.wso2.carbon.solution.deployer.database;


import org.wso2.carbon.solution.CarbonSolutionException;
import org.wso2.carbon.solution.deployer.database.h2.H2Deployer;
import org.wso2.carbon.solution.deployer.database.mysql.MYSQLDeployer;
import org.wso2.carbon.solution.deployer.iam.IdentityServerDeployer;
import org.wso2.carbon.solution.deployer.iam.impl.IdentityProviderDeployer;
import org.wso2.carbon.solution.deployer.iam.impl.ServiceProviderDeployer;

import java.util.ArrayList;
import java.util.List;

public class DatabaseDeployerFactory {

    private static DatabaseDeployerFactory databaseDeployerFactory = new DatabaseDeployerFactory();
    private List<DatabaseDeployer> databaseDeployerList = new ArrayList<>();

    private DatabaseDeployerFactory() {
        databaseDeployerList.add(new H2Deployer());
        databaseDeployerList.add(new MYSQLDeployer());
    }

    public static DatabaseDeployerFactory getInstance() {
        return DatabaseDeployerFactory.databaseDeployerFactory;
    }

    public DatabaseDeployer getDeployer(String databaseType)
            throws CarbonSolutionException {

        for (DatabaseDeployer databaseDeployer : databaseDeployerList) {
            if (databaseDeployer.canHandle(databaseType)) {
                return databaseDeployer;
            }
        }

        throw new CarbonSolutionException(
                "No deployer found to the given database type : " + databaseType);
    }
}
