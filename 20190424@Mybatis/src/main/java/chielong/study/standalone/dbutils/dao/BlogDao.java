package chielong.study.standalone.dbutils.dao;

import chielong.study.standalone.dbutils.HikariUtil;
import chielong.study.standalone.dbutils.dto.BlogDto;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by chielong on 2019-05-09.
 */
public class BlogDao {
    private static QueryRunner queryRunner = HikariUtil.getQueryRunner();

    public static void selectBlog(Integer bid) throws SQLException {
        String sql = "select * from blog where bid = ? ";
        Object[] params = new Object[]{bid};
        BlogDto blogDto = queryRunner.query(sql , new BeanHandler<>(BlogDto.class) , params);
        System.out.println(blogDto);
    }

    public static void selectList() throws SQLException {
        String sql = "select * form blog";
        List<BlogDto> list = queryRunner.query(sql , new BeanListHandler<>(BlogDto.class));
    }
}
