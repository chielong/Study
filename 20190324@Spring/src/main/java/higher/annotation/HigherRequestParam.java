package higher.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface HigherRequestParam {
    String value() default "";

    boolean required() default true;
}
