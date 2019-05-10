package chielong.study.four.simple.mapper;

import chielong.study.mebatisplus.annotation.Entity;
import chielong.study.mebatisplus.annotation.Select;

/**
 * Created by chielong on 2019-05-10.
 */
@Entity(Blog.class)
public interface BlogMapper {
    @Select("select * from blog whrer id = %d")
    public Blog selectBlogById(Integer bid);
}
