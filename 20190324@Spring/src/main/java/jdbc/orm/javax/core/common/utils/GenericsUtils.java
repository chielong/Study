package jdbc.orm.javax.core.common.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by chielong on 2019-04-22.
 */
public class GenericsUtils {
    private static final Log logger = LogFactory.getLog(GenericsUtils.class);

    private GenericsUtils() {}


    /**
     * 通过反射，获得定义Class时声明的父类的范型参数类型，比如：public BookManager extends GenricManager<Book>
     *
     * @param clazz
     *              the class to introspect
     * @return
     *              the first generic declaration ,
     *              or <code> Object.class </code> if cannot be determined
     */
    public static Class getSuperClassGenricType(Class clazz) {
        return getSuperClassGenricType(clazz , 0);
    }

    /**
     * 通过反射，获得定义Class时声明的父类的范型参数类型，比如：public BookManager extends GenricManager<Book>
     *
     * @param clazz
     *              the class to introspect
     * @param index
     *              the index of the generic declaration from 0.
     * @return
     *              the first generic declaration ,
     *              or <code> Object.class </code> if cannot be determined
     */
    public static Class getSuperClassGenricType(Class clazz , int index) {
        Type genType = clazz.getGenericSuperclass();

        if(!(genType instanceof ParameterizedType)) {
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if(index >= params.length || index < 0) {
            return Object.class;
        }
        if(!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class)params[index];
    }

}
