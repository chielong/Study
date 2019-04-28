package jdbc.orm;


import jdbc.orm.demo.entity.Member;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

/**
 * Created by chielong on 2019-04-26.
 */
public class JdbcTest {
    public static void main(String[] args) {
        Member condition = new Member();
        condition.setName("chielong");
        condition.setAge(22);
        List<?> result = select(condition);
        System.out.println(Arrays.toString(result.toArray()));
    }

    private static List<?> select(Object condition) {
        List<Object> result = new ArrayList<>();
        Class<?> entityClass = condition.getClass();

        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            // 1.加载驱动类
            Class.forName("com.mysql.jdbc.Driver");

            // 2.建立链接
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo?characterEncoding=UTF-8&rewriteBatchedStatements=true" , "root" , "apple");

            // 根据类名找属性名
            Map<String , String> columnMapper = new HashMap<String , String>();
            // 根据属性名找字段名
            Map<String , String> fieldMapper = new HashMap<String , String>();

            Field[] fields = entityClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                if(field.isAnnotationPresent(Column.class)) {
                    Column column = field.getAnnotation(Column.class);
                    String columnName = column.name();
                    columnMapper.put(columnName , fieldName);
                    fieldMapper.put(fieldName , columnName);
                } else {
                    // 默认类的字段名和数据库列名一致
                    columnMapper.put(fieldName , fieldName);
                    fieldMapper.put(fieldName , fieldName);
                }
            }

            // 3.创建语句集
            Table table = entityClass.getAnnotation(Table.class);
            String sql = "select * from " + table.name();

            StringBuffer where = new StringBuffer(" where 1=1 ");
            for (Field field : fields) {
                Object value = field.get(condition);
                if(null != value){
                    if(String.class == field.getType()) {
                        where.append(" and " + fieldMapper.get(field.getName()) + " = '" + value + "'");
                    }else{
                        where.append(" and " + fieldMapper.get(field.getName()) + " = " + value + "");
                    }
                    //其他的，在这里就不一一列举，下半截我们手写ORM框架会完善
                }
            }
            System.out.println(sql + where.toString());

            pstm = connection.prepareStatement(sql + where.toString());
            // 4.执行语句集
            rs = pstm.executeQuery();

            // 元数据 => 保存了处理真正数值以外的所有的附加信息
            int colunmnCounts = rs.getMetaData().getColumnCount();
            while(rs.next()) {
                Object instance = entityClass.newInstance();

                for(int i = 0 ; i < colunmnCounts ; i++) {
                    String columnName = rs.getMetaData().getColumnName(i);
                    Field field = entityClass.getDeclaredField(columnMapper.get(columnName));
                    field.setAccessible(true);
                    field.set(instance , rs.getObject(columnName));
                }
                result.add(instance);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                pstm.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
