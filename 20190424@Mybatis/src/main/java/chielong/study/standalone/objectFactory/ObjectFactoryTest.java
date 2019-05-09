package chielong.study.standalone.objectFactory;

import chielong.study.standalone.domain.Blog;

/**
 * Created by chielong on 2019-05-09.
 */
public class ObjectFactoryTest {
    public static void main(String[] args) {
        MyObjectFactory factory = new MyObjectFactory();
        Blog myBlog = (Blog) factory.create(Blog.class);
        System.out.println(myBlog);
    }
}
