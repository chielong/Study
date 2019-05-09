package chielong.study.standalone.objectFactory;

import chielong.study.standalone.domain.Blog;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

/**
 * Created by chielong on 2019-05-09.
 */
public class MyObjectFactory extends DefaultObjectFactory {
    @Override
    public Object create(Class type) {
        System.out.println("创建对象方法 : " + type);

        if(type.equals(Blog.class)) {
            Blog blog = (Blog)super.create(type);
            blog.setBid(1111);
            blog.setName("object factroy");
            blog.setAuthorId(2222);
            return blog;
        }

        Object result = super.create(type);
        return result;
    }
}
