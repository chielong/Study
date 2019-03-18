package chielong.pattern.template.jdbc;

import javax.sql.DataSource;
import javax.swing.*;
import java.sql.ResultSet;
import java.util.List;

public class MemberDao extends  JdbcTemplate{

    public MemberDao(DataSource dataSource) {
        super(dataSource);
    }

    public List<?> selectAll() {
        String sql = "select * form member";
        return super.executeQuery(sql, new RowMapper<Member>() {
            @Override
            public Member mapRow(ResultSet rs, int rowNum) throws Exception {
                Member member = new Member();

                member.setUserName(rs.getString("username"));
                member.setPassword(rs.getString("password"));
                member.setAge(rs.getInt("age"));

                return member;
            }
        } , null);
    }
}
