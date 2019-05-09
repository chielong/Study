package chielong.study.standalone.domain.associate;

import chielong.study.standalone.domain.Author;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by chielong on 2019-05-09.
 */
@Data
public class BlogAndAuthor implements Serializable {
    private Integer bid;
    private String name;
    private Author author;
}
