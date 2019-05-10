package chielong.study.mebatisplus.executor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by chielong on 2019-05-10.
 */
public class ResultSetHandler {

    public <T> T handle(ResultSet resultSet , Class type) {
        Object pojo = null;

        try {
            pojo = type.newInstance();
            if(resultSet.next()) {
                for (Field field : pojo.getClass().getDeclaredFields()) {
                    setValue(pojo , field , resultSet);
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return (T)pojo;
    }

    private void setValue(Object pojo , Field field , ResultSet rs) {
        Method setMethod = null;
        try {
            setMethod = pojo.getClass().getMethod("set" + upperFirstCase(field.getName()) , field.getType());
            setMethod.invoke(pojo , getResult(rs , field));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Object getResult(ResultSet rs, Field field) throws SQLException {
        Class type = field.getType();
        String dataName = humpToUnderline(field.getName());

        if (Integer.class == type ) {
            return rs.getInt(dataName);
        }else if (String.class == type) {
            return rs.getString(dataName);
        }else if(Long.class == type){
            return rs.getLong(dataName);
        }else if(Boolean.class == type){
            return rs.getBoolean(dataName);
        }else if(Double.class == type){
            return rs.getDouble(dataName);
        }else{
            return rs.getString(dataName);
        }
    }

    /**
     * 数据库下划线转java驼峰式命名
     * @param para
     * @return
     */
    public static String humpToUnderline(String para) {
        StringBuffer sb = new StringBuffer(para);
        int tmp = 0;
        if(!para.contains("_")) {
            for(int i = 0 ; i < para.length() ; i++) {
                if(Character.isUpperCase(para.charAt(i))) {
                    sb.insert(i + tmp , "_");
                    tmp += 1;
                }
            }
        }
        return sb.toString().toUpperCase();
    }

    private String upperFirstCase(String word) {
        char[] chars = word.toCharArray();
        chars[0] |= (1 << 5);
        return chars.toString();
    }
}
