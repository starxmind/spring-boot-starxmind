package com.starxmind.boot.response.options.autoload;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 选项vo
 *
 * @author pizzalord
 * @since 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class OptionVal {
    @ApiModelProperty(value = "选项真实值")
    private String value;

    @ApiModelProperty(value = "选项显示值")
    private String label;
}
