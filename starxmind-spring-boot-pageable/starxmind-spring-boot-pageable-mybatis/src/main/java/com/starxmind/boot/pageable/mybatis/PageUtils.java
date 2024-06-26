package com.starxmind.boot.pageable.mybatis;

import com.github.pagehelper.PageInfo;
import com.starxmind.boot.pageable.core.PageResponse;

import java.util.List;

/**
 * 分页工具
 *
 * @author pizzalord
 * @since 1.0
 */
public abstract class PageUtils {
    /**
     * 获取分页结果
     *
     * @param list 一页数据list
     * @param <T>  元素类型
     * @return
     */
    public static <T> PageResponse<T> getPageResult(List<T> list) {
        PageInfo pageInfo = new PageInfo(list);
        return new PageResponse<T>().toBuilder()
                .pageNum(pageInfo.getPageNum())
                .pageSize(pageInfo.getPageSize())
                .pages(pageInfo.getPages())
                .total(pageInfo.getTotal())
                .list(pageInfo.getList())
                .build();
    }

    public static int offset(int pageNum, int pageSize) {
        return (pageNum - 1) * pageSize;
    }

    /**
     * 计算总页数
     *
     * @param total
     * @param pageSize
     * @return
     */
    public static int calcPages(long total, int pageSize) {
        long pages = total / pageSize;
        if (total % pageSize != 0) {
            pages++;
        }
        return (int) pages;
    }
}
