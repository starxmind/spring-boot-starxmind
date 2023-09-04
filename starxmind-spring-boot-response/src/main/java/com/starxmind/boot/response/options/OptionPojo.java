package com.starxmind.boot.response.options;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 选项pojo
 *
 * @param <I> 节点ID类型
 * @author pizzalord
 * @since 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class OptionPojo<I> {
    @ApiModelProperty(value = "选项ID")
    private I id;

    @ApiModelProperty(value = "选项值")
    private String value;
}
