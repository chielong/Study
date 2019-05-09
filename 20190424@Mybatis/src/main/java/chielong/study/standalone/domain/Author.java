package chielong.study.standalone.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by chielong on 2019-05-09.
 */
@Data
public class Author implements Serializable {
    private Integer authorId;
    String authorName;
}
