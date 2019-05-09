package chielong.study.standalone.domain.associate;

import chielong.study.standalone.domain.Author;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by chielong on 2019-05-09.
 */
@Data
@ToString
public class AuthorAndBlog {
    private Integer author_id;
    private String author_name;
    List<BlogAndComment> blog;
}
