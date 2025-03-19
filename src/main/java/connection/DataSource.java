package connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;


public class DataSource {


    private HikariConfig config = new HikariConfig("datasource.properties");
    private HikariDataSource dataSource;


    public DataSource() {
        config.setJdbcUrl(config.getDataSourceProperties().getProperty("jdbcUrl"));
        config.setUsername(config.getDataSourceProperties().getProperty("user"));
        config.setPassword(config.getDataSourceProperties().getProperty("password"));
        config.setDriverClassName(config.getDataSourceProperties().getProperty("driverClassName"));
        dataSource = new HikariDataSource(config);
    }

    public DataSource(String jdbcUrl, String name, String password) {
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(name);
        config.setPassword(password);
        config.setDriverClassName(config.getDataSourceProperties().getProperty("driverClassName"));
        dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }


}
