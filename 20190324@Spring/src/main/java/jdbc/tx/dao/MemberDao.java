package jdbc.tx.dao;

import jdbc.tx.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by chielong on 2019-04-22.
 */
@Repository
public class MemberDao {
    private JdbcTemplate template;

    @Autowired
    protected void setDataSource(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    public List<Member> selectAll() throws Exception {
        String sql = "select * from study_memeber";
        return template.query(sql, new RowMapper<Member>() {
            @Override
            public Member mapRow(ResultSet resultSet, int i) throws SQLException {
                Member member = new Member();
                member.setId(resultSet.getLong("id"));
                member.setName(resultSet.getString("names"));
                member.setAddr(resultSet.getString("addr"));
                member.setAge(resultSet.getInt("age"));
                return member;
            }
        });
    }

    public boolean insert(Member m) throws Exception {
        String sql = "insert into study_member(id , name , addr , age) value(? , ? , ? , ?)";
        int count = template.update(sql , m.getId() , m.getName() , m.getAddr() , m.getAge());
        return count > 0;
    }

    public boolean delete(long id) throws Exception {
        return template.update("delete from study_member where id = ?" , id) > 0;
    }

    public boolean update(long id , String name) throws Exception {
        return template.update("update study_member set name = ? where id = ?" , name , id) > 0;
    }
}
