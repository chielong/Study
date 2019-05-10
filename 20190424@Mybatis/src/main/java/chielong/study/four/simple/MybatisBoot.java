package chielong.study.four.simple;

import chielong.study.four.simple.mapper.BlogMapper;

/**
 * Created by chielong on 2019-05-10.
 */
public class MybatisBoot {
    public static void main(String[] args) {
        MeSqlSession sqlSession = new MeSqlSession(new MeExecutor() , new MeConfiguration());
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        blogMapper.selectBlogById(1);
    }
}
