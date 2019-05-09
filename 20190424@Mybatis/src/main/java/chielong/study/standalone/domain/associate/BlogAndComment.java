package chielong.study.standalone.domain.associate;

import chielong.study.standalone.domain.Author;
import chielong.study.standalone.domain.Comment;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by chielong on 2019-05-09.
 */
@Data
@ToString
public class BlogAndComment {
    private Integer bid;
    private String name;
    private Integer authorId;
    List<Comment> commentList;
}
