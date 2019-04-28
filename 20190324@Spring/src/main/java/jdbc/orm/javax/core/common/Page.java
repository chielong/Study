package jdbc.orm.javax.core.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页对象，包含当前页数据及分页信息如总记录数。
 * 能够支持JQuery EasyUI直接对接，能够支持和BootStrap Table直接对接
 *
 * Created by chielong on 2019-04-22.
 */
public class Page<T> implements Serializable {
    private static final long serialVersionUID = 4990861372654139231L;

    private static final int DEFAULT_PAGE_SIZE = 20;

    private int pageSize = DEFAULT_PAGE_SIZE;   // 每页的记录数
    private long start;     // 当前页第一条数据在List中的位置，从0考试
    private List<T> rows;   // 当前页中存放的记录，类型一般为List
    private long total;     //总记录数

    /**
     * 构造方法，只构造空页。
     */
    public Page() {
        this(DEFAULT_PAGE_SIZE , 0 , new ArrayList<T>(), 0);
    }

    public void setTotal(long total) {
        this.total = total;
    }

    /**
     * 默认构造方法
     *
     * @param pageSize
     *              本页容量
     * @param start
     *              本页数据在数据库中的起始位置
     * @param rows
     *              本页包含的数据
     * @param total
     *              数据库中总记录条数
     *
     */
    public Page(int pageSize, long start, List<T> rows, long total) {
        this.pageSize = pageSize;
        this.start = start;
        this.rows = rows;
        this.total = total;
    }

    /**
     * 取得总记录数
     * @return
     */
    public long getTotal() {
        return total;
    }

    /**
     * 取总页数
     * @return
     */
    public long getTotalPageCount() {
        if (total % pageSize == 0) {
            return total / pageSize;
        } else {
            return total / pageSize + 1;
        }
    }

    /**
     * 取每夜数据容量
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 取当前页中记录
     * @return
     */
    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    /**
     * 取得该页当前页吗，页码从1开始。
     * @return
     */
    public long getPageNumber() {
        return  start / pageSize + 1;
    }

    /**
     * 该页是否有下一页
     * @return
     */
    public boolean hasNextPage() {
        return this.getPageNumber() < this.getTotalPageCount() - 1;
    }

    /**
     * 该页是否有上一页
     * @return
     */
    public boolean hasPreviousPage() {
        return this.getPageNumber() > 1;
    }

    /**
     * 获取任一页第一条数据在数据集的位置，每页条数使用默认值
     * @param pageNo
     * @return
     */
    protected static int getStartOfPage(int pageNo) {
        return getStartOfPage(pageNo , DEFAULT_PAGE_SIZE);
    }

    /**
     * 获取任一页第一条数据在数据集的位置
     * @param pageNo
     *              从1开始的页号
     * @param pageSize
     *              每页记录条数
     * @return
     *              该页第一条数据
     */
    private static int getStartOfPage(int pageNo, int pageSize) {
        return (pageNo - 1) * pageSize;
    }
}
