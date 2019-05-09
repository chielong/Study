package chielong.study.standalone.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.datasource.DataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by chielong on 2019-05-09.
 */
public class DruidDataSourceFactory implements DataSourceFactory {
    private Properties properties;

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public DataSource getDataSource() {
        DruidDataSource dds = new DruidDataSource();
        dds.setUrl(this.properties.getProperty("url"));
        dds.setPassword(this.properties.getProperty("password"));
        dds.setUsername(this.properties.getProperty("username"));
        dds.setDriverClassName(this.properties.getProperty("driver"));

        try {
            dds.init();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dds;
    }
}
