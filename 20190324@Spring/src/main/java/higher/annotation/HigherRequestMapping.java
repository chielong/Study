package higher.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE , ElementType.METHOD})
@Documented
public @interface HigherRequestMapping {
    String value() default "";
}
