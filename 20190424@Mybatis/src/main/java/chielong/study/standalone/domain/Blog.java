package chielong.study.standalone.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by chielong on 2019-05-09.
 */
@Data
@ToString
public class Blog implements Serializable {
    private Integer bid;
    private String name;
    private Integer authorId;
}
