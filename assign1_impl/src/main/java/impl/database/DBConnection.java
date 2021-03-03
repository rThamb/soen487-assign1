package impl.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private String JDBC_DRIVER;
    private String DB_URL;
    private String DB_NAME;
    private String DB_USER;
    private String DB_PASSWORD;
    static Connection conn = null;


    public DBConnection(Properties properties){
        this.JDBC_DRIVER = properties.getProperty("mysql.driver");
        this.DB_URL = properties.getProperty("db.url");
        this.DB_NAME = properties.getProperty("db.name");
        this.DB_USER = properties.getProperty("db.user");
        this.DB_PASSWORD = properties.getProperty("db.pass");
    }

    public Connection getConnection() {

        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL+DB_NAME,DB_USER,DB_PASSWORD);
            return conn;
        } catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException("Error connecting to database",e);
        }
    }

}
