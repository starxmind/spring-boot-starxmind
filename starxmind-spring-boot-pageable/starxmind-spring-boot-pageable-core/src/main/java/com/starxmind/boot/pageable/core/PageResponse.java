package com.starxmind.boot.pageable.core;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

/**
 * 分页返回
 *
 * @param <T> 分页结果中列表的元素类型
 * @author pizzalord
 * @since 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class PageResponse<T> {
    /**
     * 页码,从1开始
     */
    private int pageNum;

    /**
     * 每页大小
     */
    private int pageSize;

    /**
     * 总页数
     */
    private int pages;

    /**
     * 总记录数
     */
    private long total;

    /**
     * 本页数据列表
     */
    private List<T> list;

    public List<T> getList() {
        return Optional.ofNullable(list).orElse(Lists.newArrayList());
    }
}
