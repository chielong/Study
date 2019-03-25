package v1.annotation;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE , ElementType.METHOD})
@Documented
public @interface RequestMapping {
    String value() default "";
}
