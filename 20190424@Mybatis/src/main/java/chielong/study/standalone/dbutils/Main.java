package chielong.study.standalone.dbutils;

import chielong.study.standalone.dbutils.dao.BlogDao;

import java.sql.SQLException;

/**
 * Created by chielong on 2019-05-09.
 */
public class Main {
    public static void main(String[] args) {
        HikariUtil.init();

        try {
            BlogDao.selectBlog(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("=========");

        try {
            BlogDao.selectList();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
