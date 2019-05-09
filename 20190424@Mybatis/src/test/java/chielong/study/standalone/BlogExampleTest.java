package chielong.study.standalone;

import chielong.study.standalone.domain.Blog;
import chielong.study.standalone.domain.BlogExample;
import chielong.study.standalone.mapper.BlogMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class BlogExampleTest {
    /**
     * 自动生成的Example
     * @throws IOException
     */
    @Test
    public void TestExample() throws IOException {
        String resource = "/standalone/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession session = sqlSessionFactory.openSession();
        try {
            BlogMapper mapper = session.getMapper(BlogMapper.class);
            BlogExample example = new BlogExample();
            BlogExample.Criteria criteria = example.createCriteria();
            criteria.andBidEqualTo(1);
            List<Blog> list = mapper.selectByExample(example);
            System.out.println(list);
        } finally {
            session.close();
        }
    }


}
