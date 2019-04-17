package higher.aop.config;

import lombok.Data;

/**
 * Created by chielong on 2019-04-16.
 */
@Data
public class HigherAopConfig {
    private String pointCut;
    private String aspectClass;
    private String aspectBefore;
    private String aspectAfter;
    private String aspectAfterThrow;
    private String aspectAfterThrowingName;
}
