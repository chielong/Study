package chielong.study.standalone.mapper;

import chielong.study.standalone.domain.Blog;

/**
 * 扩展类继承了MBG生成的接口和Statement
 *
 * Created by chielong on 2019-05-09.
 */
public interface BlogMapperExt extends BlogMapper{
    /**
     * 根据名称查询文章
     * @param name
     * @return
     */
    public Blog selectBlogByName(String name);
}
