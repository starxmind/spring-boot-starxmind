package com.starxmind.boot.response.options;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 选项vo
 *
 * @param <I> 节点ID类型
 * @author pizzalord
 * @since 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class OptionVo<I> {
    @ApiModelProperty(value = "选项真实值")
    private I value;

    @ApiModelProperty(value = "选项显示值")
    private String label;
}
