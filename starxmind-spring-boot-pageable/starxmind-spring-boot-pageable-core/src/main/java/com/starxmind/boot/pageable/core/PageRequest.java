package com.starxmind.boot.pageable.core;

import com.starxmind.bass.sugar.Asserts;
import com.starxmind.boot.pageable.core.exceptions.PageSizeTooLargeException;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页请求
 *
 * @author pizzalord
 * @since 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageRequest {
    /**
     * 页码
     */
    @ApiModelProperty(value = "页码,从1开始", required = true)
    private int pageNum = 1;

    /**
     * 页大小
     */
    @ApiModelProperty(value = "页大小", required = true)
    private int pageSize = 20;

    public int getPageSize() {
        // 限制最大页大小
        Asserts.isTrue(this.pageSize <= 1000, new PageSizeTooLargeException());
        return pageSize;
    }
}
