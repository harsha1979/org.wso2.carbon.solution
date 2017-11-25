package org.wso2.carbon.solution.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

public class DBCPDataSourceFactory {

    public static DataSource getDataSource(String dbType){
        Properties props = new Properties();
        FileInputStream fis = null;
        BasicDataSource ds = new BasicDataSource();

        try {
            fis = new FileInputStream("db.properties");
            props.load(fis);
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
        if("mysql".equals(dbType)){
            ds.setDriverClassName(props.getProperty("MYSQL_DB_DRIVER_CLASS"));
            ds.setUrl(props.getProperty("MYSQL_DB_URL"));
            ds.setUsername(props.getProperty("MYSQL_DB_USERNAME"));
            ds.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
        }else if("oracle".equals(dbType)){
            ds.setDriverClassName(props.getProperty("ORACLE_DB_DRIVER_CLASS"));
            ds.setUrl(props.getProperty("ORACLE_DB_URL"));
            ds.setUsername(props.getProperty("ORACLE_DB_USERNAME"));
            ds.setPassword(props.getProperty("ORACLE_DB_PASSWORD"));
        }else{
            return null;
        }

        return ds;
    }

    public static void main(String[] args) {
        try {
            testDBCPDataSource("mysql");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void testDBCPDataSource(String dbType) throws SQLException {
        DataSource ds = DBCPDataSourceFactory.getDataSource(dbType);

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("select empid, name from Employee");
            while(rs.next()){
                System.out.println("Employee ID="+rs.getInt("empid")+", Name="+rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}