package chielong.study.mebatisplus.session;

import chielong.study.mebatisplus.TestMybatis;
import chielong.study.mebatisplus.annotation.Entity;
import chielong.study.mebatisplus.annotation.Select;
import chielong.study.mebatisplus.binding.MapperProxyRegistry;
import chielong.study.mebatisplus.executor.CachingExecutor;
import chielong.study.mebatisplus.executor.Executor;
import chielong.study.mebatisplus.executor.SimpleExecutor;
import chielong.study.mebatisplus.plguin.Interceptor;
import chielong.study.mebatisplus.plguin.InterceptorChain;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by chielong on 2019-05-10.
 */
public class Configuration {
    /**
     * sql映射关系配置，使用注解时不用重复配置
     */
    public static final ResourceBundle sqlMappings;
    /**
     * 全局配置
     */
    public static final ResourceBundle properties;
    /**
     * 维护接口与工厂类关系
     */
    public static final MapperProxyRegistry MAPPER_REGISTRY = new MapperProxyRegistry();
    /**
     * 维护接口方法与sql关系
     */
    public static final Map<String , String> mappedStatements = new HashMap<>();

    /**
     * 插件
     */
    private InterceptorChain interceptorChain = new InterceptorChain();

    /**
     * 所有mapper接口
     */
    private List<Class<?>> mapperList = new ArrayList<>();

    /**
     * 类所有文件
     */
    private List<String> classPaths = new ArrayList<>();

    static {
        sqlMappings = ResourceBundle.getBundle("sql");
        properties = ResourceBundle.getBundle("mebatis");
    }

    /**
     * 初始化时解析全局配置文件
     */
    public Configuration()  {
        // Note：在properties和注解中重复配置SQL会覆盖
        // 1.解析sql.properties
        for(String key : sqlMappings.keySet()){
            Class mapper = null;
            String statement = null;
            String pojoStr = null;
            Class pojo = null;
            // properties中的value用--隔开，第一个是SQL语句
            statement = sqlMappings.getString(key).split("--")[0];
            // properties中的value用--隔开，第二个是需要转换的POJO类型
            pojoStr = sqlMappings.getString(key).split("--")[1];
            try {
                // properties中的key是接口类型+方法
                // 从接口类型+方法中截取接口类型
                mapper = Class.forName(key.substring(0,key.lastIndexOf(".")));
                pojo = Class.forName(pojoStr);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            MAPPER_REGISTRY.addMapper(mapper, pojo); // 接口与返回的实体类关系
            mappedStatements.put(key, statement); // 接口方法与SQL关系
        }

        // 2.解析Mapper接口配置，扫描注册
        String mapperPath = properties.getString("mapper.path");
        scanPackage(mapperPath);
        for (Class<?> mapper : mapperList) {
            parsingClass(mapper);
        }
        // 3.解析插件，可配置多个插件
        String pluginPathValue = properties.getString("plugin.path");
        String[] pluginPaths = pluginPathValue.split(",");
        if (pluginPaths != null) {
            // 将插件添加到interceptorChain中
            for (String plugin : pluginPaths) {
                Interceptor interceptor = null;
                try {
                    interceptor = (Interceptor) Class.forName(plugin).newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                interceptorChain.addInterceptor(interceptor);
            }
        }
    }

    /**
     * 解析Mapper接口上配置的注解(sql语句)
     * @param mapper
     */
    private void parsingClass(Class<?> mapper) {
        // 1.解析类上的注解
        // 如果有@Entity注解，说明是查询数据库的接口
        if (mapper.isAnnotationPresent(Entity.class)) {
            for (Annotation annotation : mapper.getAnnotations()) {
                if (annotation.annotationType().equals(Entity.class)) {
                    // 注册接口与实体类的映射关系
                    MAPPER_REGISTRY.addMapper(mapper, ((Entity)annotation).value());
                }
            }
        }

        // 2.解析方法上的注解
        Method[] methods = mapper.getMethods();
        for (Method method : methods) {
            // 解析@Select注解的SQL语句
            if (method.isAnnotationPresent(Select.class)) {
                for (Annotation annotation : method.getDeclaredAnnotations()) {
                    if (annotation.annotationType().equals(Select.class)) {
                        // 注册接口类型+方法名和SQL语句的映射关系
                        String statement = method.getDeclaringClass().getName() + "." +method.getName();
                        mappedStatements.put(statement, ((Select) annotation).value());
                    }
                }
            }
        }

    }

    /**
     * 根据全局配置文件的Mapper接口路径，扫描所有接口
     * @param mapperPath
     */
    private void scanPackage(String mapperPath) {
        String classPath = TestMybatis.class.getResource("/").getPath();
        mapperPath = mapperPath.replace(".", File.separator);
        String mainPath = classPath + mapperPath;
        doPath(new File(mainPath));
        for (String className : classPaths) {
            className = className.replace(classPath.replace("/","\\").replaceFirst("\\\\",""),"").replace("\\",".").replace(".class","");
            Class<?> clazz = null;
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if(clazz.isInterface()){
                mapperList.add(clazz);
            }

        }
    }

    private void doPath(File file) {
        if(file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                doPath(f);
            }
        } else {
            if(file.getName().endsWith(".class")) {
                classPaths.add(file.getPath());
            }
        }
    }

    /**
     * 根据statement判断是否存在映射的sql
     *
     * @param statementName
     * @return
     */
    public boolean hasStatement(String statementName) {
        return mappedStatements.containsKey(statementName);
    }

    /**
     * 根据statementID获取sql
     *
     * @param id
     * @return
     */
    public String getMappedStatement(String id) {
        return mappedStatements.get(id);
    }

    /**
     *
     * @param clazz
     * @param sqlSession
     * @param <T>
     * @return
     */
    public <T> T getMapper(Class<T> clazz, DefaultSqlSession sqlSession) {
        return MAPPER_REGISTRY.getMapper(clazz , sqlSession);
    }

    /**
     * 创建执行器，当开启缓存的时候使用缓存装饰
     * 当配置插件时，使用插件代理
     * @return
     */
    public Executor newExecutor() {
        Executor executor = null;

        if(properties.getString("cache.enabled").equals("true")) {
            executor = new CachingExecutor(new SimpleExecutor());
        } else {
            executor = new SimpleExecutor();
        }

        // 只拦截Execuor,所有的插件都对Executor进行代理，没有对拦截类和方法签名的判断
        if(interceptorChain.hasPlugin()) {
            return (Executor) interceptorChain.pluginAll(executor);
        }

        return executor;
    }



}
