package jdbc.orm.javax.core.common.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 提供各种对数据进行处理的方法.<br>
 *
 * Created by chielong on 2019-04-22.
 */
public class DataUtils {
    private static final BigDecimal ONE = new BigDecimal("1");
    private static Log logger = LogFactory.getLog(DataUtils.class);

    /**
     * 构造方法，禁止实例化
     */
    private DataUtils() {}

    private static Map<Class , String> supportTypeMap = new HashMap<Class, String>();

    static {
        supportTypeMap.put(Byte.class ,"");
        supportTypeMap.put(Character.class ,"");
        supportTypeMap.put(Short.class ,"");
        supportTypeMap.put(Integer.class ,"");
        supportTypeMap.put(Long.class ,"");
        supportTypeMap.put(Float.class ,"");
        supportTypeMap.put(Double.class ,"");
        supportTypeMap.put(Boolean.class ,"");

        supportTypeMap.put(byte[].class ,"");
        supportTypeMap.put(char.class ,"");
        supportTypeMap.put(short.class ,"");
        supportTypeMap.put(int.class ,"");
        supportTypeMap.put(long.class ,"");
        supportTypeMap.put(float.class ,"");
        supportTypeMap.put(double.class ,"");
        supportTypeMap.put(boolean.class ,"");

        supportTypeMap.put(Date.class ,"");
        supportTypeMap.put(BigDecimal.class ,"");
        supportTypeMap.put(String.class ,"");
    }

    /**
     * 添加mergePO时支持的类型
     *
     * @param clazz
     */
    public static void addSupportType(Class clazz) {
        supportTypeMap.put(clazz , "");
    }

    /**
     * 当整形数值为0时，返回字符串""。否则将整型值转化为字符串返回。<br>
     * <br>
     * <b>示例</b>
     * <code>
     * <br>DataUtils.zeroToEmpty(0) 返回&quot;&quot;
     * <br>DataUtils.zeroToEmpty(1) 返回&quot;1&quot;
     * </code>
     *
     * @param i
     *          输入的整型值
     * @return
     *          返回字符串
     */
    public static String zeroToEmpty(int i) {
        return i == 0 ? "" : String.valueOf(i);
    }

    /**
     * 当浮点型数值为0时，返回字符串""，否则将浮点型值转化为字符串返回。
     * <br>
     * <b>示例</b>
     * <code>
     * <br>DataUtils.zeroToEmpty(0) 返回&quot;&quot;
     * <br>DataUtils.zeroToEmpty(1.2) 返回&quot;1.2&quot;
     * </code>
     * @param d
     *              输入的浮点型值
     * @return
     *              返回的字符串
     */
    public static String zeroToEmpty(double d) {
        return d == 0 ? "" : String.valueOf(d);
    }

    /**
     * 当字符串为null时，返回字符串""
     * <br>
     * <b>示例</b>
     * <code>
     * <br>DataUtils.nullToEmpty(null) 返回&quot;&quot;
     * <br>DataUtils.nullToEmpty(&quot;null&quot;) 返回&quot;null&quot;
     * <br>DataUtils.nullToEmpty(&quot;abc&quot;) 返回&quot;abc&quot;
     * </code>
     * @param str
     *              输入的浮点型值
     * @return
     *              返回的字符串
     */
    public static String nullToEmpty(String str) {
        return str == null ? "" : str;
    }

    /**
     * 当字符串为""时，返回字符串null
     * <br>
     * <b>示例</b>
     * <code>
     * <br>DataUtils.nullToEmpty(null) 返回&quot;null&quot;
     * <br>DataUtils.nullToEmpty(&quot;&quot;) 返回&quot;null&quot;
     * <br>DataUtils.nullToEmpty(&quot;abc&quot;) 返回&quot;abc&quot;
     * </code>
     * @param str
     *              输入的浮点型值
     * @return
     *              返回的字符串
     */
    public static String emptyToNull(String str) {
        if (str == null) {
            return null;
        }
        if(str.trim().length() == 0) {
            return null;
        }
        return str;
    }

    /**
     * 当字符串为""时，返回字符串null
     * <br>
     * <b>示例</b>
     * <code>
     * <br>DataUtils.nullToEmpty(null) 返回&quot;null&quot;
     * <br>DataUtils.nullToEmpty(&quot;&quot;) 返回&quot;null&quot;
     * <br>DataUtils.nullToEmpty(&quot;abc&quot;) 返回&quot;abc&quot;
     * </code>
     * @param str
     *              输入的浮点型值
     * @return
     *              返回的字符串
     */
    public static String dbNullToEmpty(String str) {
        if (str == null || str.equalsIgnoreCase("null")) {
            return "";
        }
        return str;
    }

    /**
     * 当字符串为null或""或全部为空格时，返回字符串"0"，否则将字符串原封不动的返回。<br>
     * <br>
     *
     * <b>示例</b>
     * <code>
     * <br>DataUtils.nullToEmpty(null) 返回&quot;null&quot;
     * <br>DataUtils.nullToEmpty(&quot;&quot;) 返回&quot;null&quot;
     * <br>DataUtils.nullToEmpty(&quot;abc&quot;) 返回&quot;abc&quot;
     * </code>
     *
     * @param str
     * @return
     */
    public static String nullToZero(String str) {
        if(str == null || str.trim().length() == 0) {
            return "0";
        }
        return str;
    }

    /**
     * 对表达布尔型含义的字符串转化为中文的"是"/"否".<br>
     *
     * y , Y , yes , true , 1 => "是"
     * n , N , no , false , 0 => "否"
     *
     * @param str
     *              表达布尔值含义的字符串<br>
     *              合法的输入包括:('y' , 'n' , 'yes' , 'no' , '1' , '0' , 'true' , 'false')及其响应大小写形式
     *              除了上述合法的入参之外，其他均抛出异常
     * @return
     *              结果："是"、"否"、""
     */
    public static String getBooleanDescribe(String str) {
        if(str == null) {
            throw new IllegalArgumentException("argument is null");
        }
        if(str.equalsIgnoreCase("y") || str.equalsIgnoreCase("yes")
            || str.equalsIgnoreCase("true") || str.equalsIgnoreCase("t")
            || str.equalsIgnoreCase("是") || str.equalsIgnoreCase("1")) {
            return "是";
        } else if (str.equalsIgnoreCase("n") || str.equalsIgnoreCase("no")
                || str.equalsIgnoreCase("false") || str.equalsIgnoreCase("f")
                || str.equalsIgnoreCase("否") || str.equalsIgnoreCase("0")) {
            return "否";
        } else if (str.trim().equals("")) {
            return "";
        }
        throw new IllegalArgumentException("argument not in ('y' , 'n' , 'yes' , 'no' , '1' , '0' , 'true' , 'false')");
    }

    /**
     * 对表达布尔型含义的字符串转化为boolean类型的的true / false.<br>
     *
     * y , Y , yes , true , 1 => true
     * n , N , no , false , 0 => false
     *
     * @param str
     *              表达布尔值含义的字符串<br>
     *              合法的输入包括:('y' , 'n' , 'yes' , 'no' , '1' , '0' , 'true' , 'false')及其响应大小写形式
     *              除了上述合法的入参之外，其他均抛出异常
     * @return
     *              结果：true、false
     */
    public static boolean getBoolean(String str) {
        if (str == null) {
            throw new IllegalArgumentException("argument is null");
        }
        if (str.equalsIgnoreCase("y") || str.equalsIgnoreCase("yes")
                || str.equalsIgnoreCase("true") || str.equalsIgnoreCase("t")
                || str.equalsIgnoreCase("是") || str.equalsIgnoreCase("1")) {
            return true;
        } else if (str.equalsIgnoreCase("n") || str.equalsIgnoreCase("no")
                || str.equalsIgnoreCase("false") || str.equalsIgnoreCase("f")
                || str.equalsIgnoreCase("否") || str.equalsIgnoreCase("0")) {
            return false;
        } else if (str.trim().equals("")) {
            return false;
        }
        throw new IllegalArgumentException(
                "argument not in ('y','n','yes','no','true','false','t','f','是','否','1','0','')");
    }

    /**
     * 返回对应boolean类型的字符串型中文描述："是"、"否".<br>
     *
     * 参数true -> "是"
     * 参数false -> "否"
     * @param bln
     *              布尔型变量
     * @return
     *              字符串型中文描述:'是' / '否'
     */
    public static String getBooleanDescribe(boolean bln) {
        if(bln) {
            return getBooleanDescribe("true");
        }
        return getBooleanDescribe("false");
    }

    /**
     * 比较两个存放了数字的字符串的大小，如果不为数字将抛出异常。<br>
     *
     * <b>示例</b>
     * <code>
     * <br>DataUtils.compareByValue(&quot;19&quot;,&quot;2&quot;) 返回 1
     * <br>DataUtils.compareByValue(&quot;0021&quot;,&quot;21&quot;) 返回 0
     * <br>DataUtils.compareByValue(&quot;3001&quot;,&quot;5493&quot;) 返回 -1
     * </code>
     *
     * @param str1
     * @param str2
     * @return
     */
    public static int compareByValue(String str1 , String str2) {
        BigDecimal big1 = new BigDecimal(str1);
        BigDecimal big2 = new BigDecimal(str2);

        return big1.compareTo(big2);
    }

    /**
     * 提供精确的小数位四舍五入处理. <br>
     * <br>
     * <b>示例 </b> <code>
     * <br>DataUtils.round(0.574,2) 返回 0.57
     * <br>DataUtils.round(0.575,2) 返回 0.58
     * <br>DataUtils.round(0.576,2) 返回 0.58
     * </code>
     *
     * @param value
     *            需要四舍五入的数字
     * @param scale
     *            小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double value , int scale) {
        BigDecimal b = new BigDecimal(Double.toString(value));
        return b.divide(ONE , scale , BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 拷贝简单对象.(null也将拷贝)
     *
     * @param source
     *            传入的源对象
     * @param target
     *            传入的目标对象
     */
    public static void copySimpleObject(Object source , Object target) {
        copySimpleObject(source , target , true);
    }

    /**
     * 拷贝简单对象.
     *
     * @param source
     *            传入的源对象
     * @param target
     *            传入的目标对象
     * @param isCopyNull
     *            是否拷贝Null值
     */
    public static void copySimpleObject(Object source, Object target, boolean isCopyNull) {
        if (target == null || source == null) {
            return ;
        }

        List sourceMethodList = BeanUtils.getGetter(source.getClass());
        List targetMethodList = BeanUtils.getSetter(target.getClass());

        Map<String , Method> map = new HashMap<String , Method>();  //    source's-getters
        for (Object o : sourceMethodList) {
            Method method = (Method)o;
            map.put(method.getName() , method);
        }

        for (Object o : targetMethodList) {
            try {
                Method method = (Method)o;
                String fieldName = method.getName().substring(3);

                Method sourceMethod = (Method)map.get("get" + fieldName);
                if(sourceMethod == null) {
                    sourceMethod = (Method) map.get("is" + fieldName);
                }
                if(sourceMethod == null) {
                    continue;
                }
                if(!supportTypeMap.containsKey(sourceMethod.getReturnType())) {
                    continue;
                }
                Object value = sourceMethod.invoke(source , new Object[0]);
                if(isCopyNull) {
                    method.invoke(target , new Object[]{value});
                } else if (value != null){
                    method.invoke(target , new Object[]{value});
                }
            } catch (Exception e) {
                e.printStackTrace();
                if(logger.isDebugEnabled()) {
                    logger.debug(e);
                }
            }
        }
    }

    /**
     * 把通过JdbcTemplate查出的结果集封装到List中<br>
     * 只要字段名和DTO的属性名能对应上的就把值封装进去，对应不上的就不管了
     *
     * @param jdbcResultList
     *            用JdbcTemplate查出的结果集
     * @param clazz
     *            DTO的Class对象
     * @return
     *          把每行数据封装到一个DTO对象中，最后返回DTO的List
     *          可能返回空集合
     */
    public static List generateListFromJdbcResult(List jdbcResultList , Class clazz) {
        List<Object> objectList = new ArrayList<Object>();
        try {
            List methodList = BeanUtils.getSetter(clazz);

            for(int i = 0 ; i < jdbcResultList.size() ; i++) {  // 一行信息 -> 一个类
                Object object = clazz.newInstance();

                Map rowmap = (Map) jdbcResultList.get(i);
                Object[] rowKeys = rowmap.keySet().toArray();
                for(int j = 0 ; j < rowKeys.length ; j++) {     // 一列信息 -> 一个字段
                    String column = (String) rowKeys[i];
                    for(int k = 0 ; k < methodList.size() ; k++) {  // 找对应的set方法
                        Method method = (Method) methodList.get(k);
                        String upperMethodName = method.getName().toUpperCase();
                        if(upperMethodName.equals("SET" + column)) {
                            Class type = method.getParameterTypes()[0];
                            Object value = rowmap.get(column);
                            if(value != null) {
                                if(type == Integer.class) {
                                    value = new Integer(value.toString());
                                } else if(type == Double.class) {
                                    value = new Double(value.toString());
                                } else if (type == Long.class) {
                                    value = new Long(value.toString());
                                }
                            }
                            method.invoke(object , new Object[]{value});
                            break;
                        }
                    }
                }
                objectList.add(object);
            }
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
        return objectList;
    }

    /**
     * 把Object对象转化为Integer对象。
     *
     * @param object
     * @return
     *          Integer对象或null(如果Object是null)
     */
    public static Integer getInteger(Object object) {
        Integer integer = null;
        if(null != object) {
            integer = new Integer(object.toString());
        }
        return integer;
    }
    /**
     * 把Object对象转换为Long对象。
     *
     * @param object
     * @return Long对象或null（如果object是null）。
     */
    public static Long getLong(Object object) {
        Long value = null;
        if (object != null) {
            value = new Long(object.toString());
        }
        return value;
    }

    /**
     * 把Object对象转换为Double对象。
     *
     * @param object
     * @return Double对象或null（如果object是null）。
     */
    public static Double getDouble(Object object) {
        Double value = null;
        if (object != null) {
            value = new Double(object.toString());
        }
        return value;
    }

    /**
     * 把Object对象转换为String对象。
     *
     * @param object
     * @return String对象或null（如果object是null）。
     */
    public static String getString(Object object) {
        String string = null;
        if (object != null) {
            string = new String(object.toString());
        }
        return string;
    }

    public static String getPlainNumber(Integer integer) {
        if(integer == null) {
            return "";
        }
        DecimalFormat df = new DecimalFormat("###0");
        String plainNumber = df.format(integer);
        return plainNumber;
    }

    public static String getPlainNumber(Long value) {
        if (value == null) {
            return "";
        }
        DecimalFormat df = new DecimalFormat("###0");
        String plainNumber = df.format(value);
        return plainNumber;
    }

    public static String getPlainNumber(Double value) {
        if (value == null) {
            return "";
        }
        DecimalFormat df = new DecimalFormat("###0.00");
        String plainNumber = df.format(value);
        return plainNumber;
    }

    /**
     * 判断字符串是不是整型数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if(str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

}
