package chielong.study.mebatisplus;

import chielong.study.four.simple.mapper.Blog;
import chielong.study.four.simple.mapper.BlogMapper;
import chielong.study.mebatisplus.session.DefaultSqlSession;
import chielong.study.mebatisplus.session.SqlSessionFactory;

/**
 * Created by chielong on 2019-05-10.
 */
public class TestMybatis {
    public static void main(String[] args) {
        SqlSessionFactory factory = new SqlSessionFactory();
        DefaultSqlSession sqlSession = factory.build().openSqlSession();
        // 获取MapperProxy代理
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        Blog blog = mapper.selectBlogById(1);

        System.out.println("第一次查询: " + blog);
        System.out.println();
        blog = mapper.selectBlogById(1);
        System.out.println("第二次查询: " + blog);
    }
}
