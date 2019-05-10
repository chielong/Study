package chielong.study.mebatisplus.mapper;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by chielong on 2019-05-10.
 */
@Data
@ToString
public class Blog implements Serializable {
    Integer bid; // 文章ID
    String name; // 文章标题
    Integer authorId; // 文章作者ID
}