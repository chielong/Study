package jdbc.orm.javax.core.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 对象工具类
 *
 * Created by chielong on 2019-04-22.
 */
public class ObjectUtils {
    private ObjectUtils() {}

    /**
     * 是否为空对象，是则返回参数obj1
     *
     * @param obj
     * @param obj1
     * @return
     */
    public static Object notNull(Object obj , Object obj1) {
        return (obj == null || "".equals(obj)) ? obj1 : obj;
    }

    /**
     * 是否为空指针
     *
     * @param object
     * @return boolean
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    public static boolean isInteger(Object value) {
        try{
            Integer.valueOf(value.toString());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean isFloat(Object value) {
        try{
            Float.valueOf(value.toString());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean isLong(Object value) {
        try{
            Long.valueOf(value.toString());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean isBoolean(Object value) {
        try{
            Boolean.valueOf(value.toString());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 对象合并
     *
     * 将后者值合并到前者，最好只用在基础数据类型属性对象
     * @param mergeObject
     * @param object
     * @throws Exception
     */
    public static void merge(Object mergeObject , Object object) throws Exception {
        Class<?> classType = object.getClass();
        Field[] fields = classType.getDeclaredFields();
        for(int i = 0 ; i < fields.length ; i++) {
            Field field = fields[i];
            String fieldName = field.getName();
            String firstLetter = fieldName.substring(0 , 1).toUpperCase();

            String getMethodName = "get" + firstLetter + fieldName.substring(1);
            String setMethodName = "set" + firstLetter + fieldName.substring(1);

            Method getMethod = classType.getMethod(getMethodName , new Class[]{});
            Method setMethod = classType.getMethod(setMethodName , new Class[] {field.getType()});
            Object value = getMethod.invoke(object , new Object[]{});
            if(value != null && value.toString().length() > 0) {
                setMethod.invoke(mergeObject , new Object[]{value});
            }
        }
    }

    /**
     * 反射对象
     *
     * @param path
     * @return class
     * @throws ClassNotFoundException
     */
    public static Class<?> getObject(String path) throws ClassNotFoundException {
        return Class.forName(path);
    }

}
