package chielong.study.mebatisplus.annotation;

import java.lang.annotation.*;

/**
 * Created by chielong on 2019-05-10.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Intercepts {
    String value();
}
