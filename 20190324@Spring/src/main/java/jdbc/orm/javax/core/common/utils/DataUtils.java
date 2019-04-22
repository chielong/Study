package jdbc.orm.javax.core.common.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
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
}
