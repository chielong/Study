package chielong.study.mebatisplus.mapper;

import chielong.study.mebatisplus.annotation.Entity;
import chielong.study.mebatisplus.annotation.Select;

/**
 * Created by chielong on 2019-05-10.
 */
@Entity(Blog.class)
public interface BlogMapper {

    /**
     * 根据主键查询文章
     *
     * @param bid
     * @return
     */
    @Select("select * from blog where bid = ?")
    public Blog selectBlogById(Integer bid);
}
