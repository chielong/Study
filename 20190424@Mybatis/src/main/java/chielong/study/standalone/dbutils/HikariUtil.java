package chielong.study.standalone.dbutils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by chielong on 2019-05-09.
 */
public class HikariUtil {
    private static final String PROPERTY_PATH = "/standalone/hikari.properties";
    private static final Logger LOGGER = LoggerFactory.getLogger(HikariUtil.class);
    private static HikariDataSource dataSource;
    private static QueryRunner queryRunner;

    public static void init() {
        HikariConfig config = new HikariConfig(PROPERTY_PATH);
        dataSource = new HikariDataSource(config);
        queryRunner = new QueryRunner(dataSource);
    }

    public static QueryRunner getQueryRunner() {
        check();
        return null;
    }

    public static Connection getConnection() {
        check();

        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void close(Connection connection) {
        try {
            if(connection != null & !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void check() {
        if (dataSource == null || queryRunner == null) {
            throw new RuntimeException("datasource has not been init");
        }
    }


}
