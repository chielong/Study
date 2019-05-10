package chielong.study.mebatisplus.cache;

/**
 * Created by chielong on 2019-05-10.
 */
public class CacheKey {
    private static final int DEFAULT_HASHCODE = 17;     // 默认哈希值
    private static final int DEFAULT_MULTIPLIER = 37;   // 倍数

    private int hashCode;
    private int count;
    private int multiplier;

    public CacheKey() {
        this.hashCode = DEFAULT_HASHCODE;
        this.multiplier = DEFAULT_MULTIPLIER;
        this.count = 0;
    }

    /**
     * 返回CacheKey的值
     * @return
     */
    public int getCode() {
        return hashCode;
    }

    /**
     * 计算CacheKey中的HashCode
     * @param object
     */
    public void update(Object object) {
        int baseHashCode = (object == null) ? 1 : object.hashCode();
        count++;
        baseHashCode *= count;
        hashCode = multiplier * hashCode + baseHashCode;
    }
}
