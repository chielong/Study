package jdbc.orm.javax.core.common.jdbc.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by chielong on 2019-04-22.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    private DynamicDataSourceEntry dataSourceEntry;

    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }

    public DynamicDataSourceEntry getDataSourceEntry() {
        return dataSourceEntry;
    }

    public void setDataSourceEntry(DynamicDataSourceEntry dataSourceEntry) {
        this.dataSourceEntry = dataSourceEntry;
    }
}
