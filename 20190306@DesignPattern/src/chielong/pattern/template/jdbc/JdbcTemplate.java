package chielong.pattern.template.jdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {
    protected DataSource dataSource;

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public JdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<?> executeQuery(String sql , RowMapper<?> rowMapper , Object[] values) {
        try {
            conn = this.getConnection();
            ps = createPrepareStatement(conn , sql);
            rs = executeQuery(ps , values);
            List<?> result = this.paresResultSet(rs , rowMapper);
            closeResultSet();
            closePrepareStatement();
            closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void closeConnection() throws SQLException {
        conn.close();
    }

    private void closePrepareStatement() throws SQLException {
        ps.close();
    }

    private void closeResultSet() throws SQLException {
        rs.close();
    }

    private List<?> paresResultSet(ResultSet rs , RowMapper rowMapper) throws Exception {
        List<Object> result = new ArrayList<Object>();
        int rowNum = 1;
        while(rs.next()) {
            result.add(rowMapper.mapRow(rs , rowNum++));
        }
        return result;
    }

    protected ResultSet executeQuery(PreparedStatement ps, Object[] values) throws SQLException {
        for(int i = 0 ; i < values.length ; i++) {
            ps.setObject(i , values[i]);
        }

        return ps.executeQuery();
    }

    private PreparedStatement createPrepareStatement(Connection connection , String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    private Connection getConnection() throws SQLException {
        return this.dataSource.getConnection();
    }
}
