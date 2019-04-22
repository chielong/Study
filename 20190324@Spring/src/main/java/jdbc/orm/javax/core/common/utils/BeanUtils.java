package jdbc.orm.javax.core.common.utils;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 拓展apache commons BeanUtils,提供一些反射方面确实功能的封装。
 *
 * Created by chielong on 2019-04-22.
 */
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {
    protected static final Log logger = LogFactory.getLog(BeanUtils.class);

    private BeanUtils() {}

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

    /**
     * 循环想上转型，获取对象的DeclaredField
     *
     * @param object
     * @param propertyName
     * @return
     * @throws NoSuchFieldException
     *              如果没有该Field时抛出。
     */
    public static Field getDeclaredField(Object object , String propertyName) throws NoSuchFieldException {
        return getDeclaredField(object.getClass() , propertyName);
    }

    /**
     * 循环向上转型，获取对象的DeclaredField
     * @param clazz
     * @param propertyName
     * @return
     * @throws NoSuchFieldException
     *              如果没有该Field时抛出
     */
    public static Field getDeclaredField(Class clazz , String propertyName) throws NoSuchFieldException {
        for(Class<?> superClass = clazz ; superClass != Object.class ; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(propertyName);
            } catch (NoSuchFieldException e) {
                // Field 不在当前类定义，继续向上转型
            }
        }

        throw new NoSuchFieldException("No such field: " + clazz.getName() + "." + propertyName);
    }

    /**
     * 暴力获取对象变量值，忽略private，protected修饰符的限制
     * @param object
     * @param propertyName
     * @return
     * @throws NoSuchFieldException
     *              如果没有该Field时抛出。
     */
    public static Object forceGetProperty(Object object , String propertyName) throws NoSuchFieldException {
        Field field = getDeclaredField(object , propertyName);
        boolean accessible = field.isAccessible();
        field.setAccessible(true);

        Object result = null;
        try {
            result = field.get(object);
        } catch (IllegalAccessException e) {
            logger.info("error won't happen");
        }

        field.setAccessible(accessible);
        return  result;
    }

    /**
     * 暴力设置对象变量值，忽略private，protected修饰符的限制。
     * @param object
     * @param propertyName
     * @param newValue
     * @throws NoSuchFieldException
     *              如果没有该Field时抛出
     */
    public static void forceSetProperty(Object object , String propertyName , Object newValue) throws NoSuchFieldException {
        Field field = getDeclaredField(object , propertyName);
        boolean accessible = field.isAccessible();
        field.setAccessible(true);

        try {
            field.set(object , newValue);
        } catch (IllegalAccessException e) {
            logger.info("Error won't happen");
        }
        field.setAccessible(accessible);
    }

    /**
     * 暴力调用对象函数，忽略private，protected修饰符的限制
     * @param object
     * @param methodName
     * @param params
     * @return
     * @throws NoSuchMethodException
     *                  如果没有该Method时抛出
     */
    public static Object invokePrivateMethod(Object object , String methodName , Object... params) throws NoSuchMethodException {
        Class[] types = new Class[params.length];

        for(int i = 0 ; i < params.length ; i++) {
            types[i] = params[i].getClass();
        }

        Class clazz = object.getClass();
        Method method = null;
        for(Class superClass = clazz ; superClass != Object.class ; superClass = superClass.getSuperclass()) {
            try {
                method = superClass.getDeclaredMethod(methodName , types);
                break;
            } catch (NoSuchMethodException e) {
                // 方法不在当前类定义，继续向上转型。
            }
        }

        if(method == null) {
            throw new NoSuchMethodException("No such method " + clazz.getSimpleName() + methodName);
        }

        boolean accessible = method.isAccessible();
        method.setAccessible(true);
        Object result = null;

        try {
            result = method.invoke(object , params);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        method.setAccessible(accessible);
        return result;
    }

    /**
     * 按field的类型获得Field列表。
     */
    public static List<Field> getFieldsByType(Object object , Class type) {
        List<Field> list = new ArrayList<Field>();
        Field[] fields = object.getClass().getDeclaredFields();
        for(Field field : fields) {
            if(field.getType().isAssignableFrom(type)) {
                list.add(field);
            }
        }
        return list;
    }

    /**
     * 按fieldName获得field类型
     */
    public static Class getPropertyType(Class type , String name) throws NoSuchFieldException {
        return getDeclaredField(type , name).getType();
    }

    /**
     * 获得field的getter函数名称
     */
    public static String getGetterName(Class type , String fieldName) {
        if(type.getName().equals("boolean")) {
            return "is" + org.apache.commons.lang.StringUtils.capitalize(fieldName);
        } else {
            return "get" + org.apache.commons.lang.StringUtils.capitalize(fieldName);
        }
    }

    /**
     * 获得field的getter函数，如果找不到该方法，返回null
     * @param type
     * @param fieldName
     * @return
     */
    public static Method getGetterMethod(Class type , String fieldName) {
        try {
            return type.getMethod(getGetterName(type , fieldName));
        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage() , e);
        }
        return null;
    }

    public static Object invoke(String classname , String methodName , Class[] argsClass , Object[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Class clazz = Class.forName(classname);
        Method method = clazz.getMethod(methodName , argsClass);
        return method.invoke(clazz.newInstance() , args);
    }

    public static Object invoke(Object oldObject , String methodName , Class[] argsClass , Object[] args) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Class clazz = oldObject.getClass();
        Method method = clazz.getMethod(methodName , argsClass);
        return method.invoke(oldObject , args);
    }

    public static String[] getFieldsName(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for(int i = 0 ; i < fields.length ; i++) {
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    public static List<String> getAllFieldName(Class cl) {
        List<String> list = new ArrayList<String>();
        Field[] fields = cl.getDeclaredFields();
        for(int i = 0 ; i < fields.length ; i++) {
            Field field = fields[i];
            if(field.getName().equals("serialVersionUID")) {
                continue;
            }
            list.add(field.getName());
        }

        while(true) {
            cl = cl.getSuperclass();
            if(cl == Object.class) {
                break;
            }
            list.addAll(getAllFieldName(cl));
        }
        return list;
    }

    public static List<Method> getSetter(Class cl) {
        List<Method> list = new ArrayList<Method>();
        Method[] methods = cl.getDeclaredMethods();
        for(int i = 0 ; i < methods.length ; i++) {
            Method method = methods[i];
            String methodName = method.getName();
            if(!methodName.startsWith("set")) {
                continue;
            }
            list.add(method);
        }

        while (true) {
            cl = cl.getSuperclass();
            if(cl == Object.class) {
                break;
            }
            list.addAll(getSetter(cl));
        }
        return list;
    }

    public static List<Method> getGetter(Class cl) {
        List<Method> list = new ArrayList<Method>();
        Method[] methods = cl.getDeclaredMethods();
        for(int i = 0 ; i < methods.length ; i++) {
            Method method = methods[i];
            String methodName = method.getName();
            if(!methodName.startsWith("get") && !methodName.startsWith("is")) {
                continue;
            }
            list.add(method);
        }

        while (true) {
            cl = cl.getSuperclass();
            if(cl == Object.class) {
                break;
            }
            list.addAll(getGetter(cl));
        }
        return list;
    }

    /**
     * Returns the classname rather than the package.
     * Example : if the input class is java.lang.String then return value is String.
     *
     * @param cl
     *              the class to inspect
     * @return
     *              the classname
     */
    public static String getClassNameWithoutPackage(Class cl) {
        String className = cl.getName();
        int pos = className.lastIndexOf('.') + 1;
        if(pos == -1) {
            pos = 0;
        }
        String name = className.substring(pos);
        return name;
    }

    /**
     * 把DTO对象转成字符串
     * @param obj
     *              DTO对象
     * @return
     *              带属性名和值的字符串
     */
    public static String beanToString(Object obj) {
        return ToStringBuilder.reflectionToString(obj);
    }
}
