package chielong.study.standalone.dbutils.dto;

import lombok.Data;
import lombok.ToString;

/**
 * Created by chielong on 2019-05-09.
 */
@Data
@ToString
public class BlogDto {
    private Integer bid;
    private String name;
    private Integer authorId;

    public BlogDto() { }

}
